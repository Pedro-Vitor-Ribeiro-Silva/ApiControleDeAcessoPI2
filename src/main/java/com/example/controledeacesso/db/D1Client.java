package com.example.controledeacesso.db;

import com.example.controledeacesso.config.CloudflareD1Properties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Cliente HTTP para a API REST do Cloudflare D1.
 * Docs: https://developers.cloudflare.com/api/resources/d1/subresources/database/methods/query/
 */
@Component
public class D1Client {

    private static final String BASE_URL = "https://api.cloudflare.com/client/v4";

    private final RestClient restClient;
    private final String queryPath;

    public D1Client(RestClient.Builder builder, CloudflareD1Properties props) {
        this.restClient = builder
                .baseUrl(BASE_URL)
                .defaultHeader("Authorization", "Bearer " + props.apiToken())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.queryPath = "/accounts/" + props.accountId()
                + "/d1/database/" + props.databaseId() + "/query";
    }

    /**
     * Executa um comando SQL (com placeholders "?") e devolve as linhas retornadas.
     * Para INSERT/UPDATE/DELETE a lista vem vazia (ou com a linha de RETURNING).
     */
    public List<Map<String, Object>> query(String sql, Object... params) {
        D1Result result = execute(sql, params);
        return result.results() == null ? List.of() : result.results();
    }

    /** Executa um comando SQL e devolve a quantidade de linhas afetadas. */
    public int update(String sql, Object... params) {
        D1Result result = execute(sql, params);
        Object changes = result.meta() == null ? null : result.meta().get("changes");
        return changes instanceof Number n ? n.intValue() : 0;
    }

    private D1Result execute(String sql, Object... params) {
        D1QueryRequest body = new D1QueryRequest(sql, Arrays.asList(params));
        D1Response response;
        try {
            response = restClient.post()
                    .uri(queryPath)
                    .body(body)
                    .retrieve()
                    .body(D1Response.class);
        } catch (RestClientResponseException e) {
            throw new D1Exception("Cloudflare D1 respondeu HTTP " + e.getStatusCode().value()
                    + ": " + e.getResponseBodyAsString(), e);
        } catch (RestClientException e) {
            throw new D1Exception("Falha ao se comunicar com o Cloudflare D1: " + e.getMessage(), e);
        }

        if (response == null || !response.success()) {
            throw new D1Exception("Cloudflare D1 retornou erro: " + describeErrors(response));
        }
        if (response.result() == null || response.result().isEmpty()) {
            throw new D1Exception("Cloudflare D1 retornou resposta sem resultados");
        }
        D1Result result = response.result().get(0);
        if (!result.success()) {
            throw new D1Exception("Comando SQL falhou no Cloudflare D1: " + describeErrors(response));
        }
        return result;
    }

    private static String describeErrors(D1Response response) {
        if (response == null || response.errors() == null || response.errors().isEmpty()) {
            return "sem detalhes";
        }
        return response.errors().stream()
                .map(e -> e.code() + " - " + e.message())
                .reduce((a, b) -> a + "; " + b)
                .orElse("sem detalhes");
    }

    // ----- Contratos JSON da API -----

    record D1QueryRequest(String sql, List<Object> params) {
    }

    record D1Response(boolean success, List<D1Result> result, List<D1Error> errors) {
    }

    record D1Result(boolean success, List<Map<String, Object>> results, Map<String, Object> meta) {
    }

    record D1Error(int code, String message) {
    }
}
