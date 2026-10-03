package com.example.controledeacesso.db;

import com.example.controledeacesso.config.CloudflareD1Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Valida a conexao com o Cloudflare D1 na subida da aplicacao (falha rapido se
 * o token estiver ausente ou invalido). Desative com cloudflare.d1.verificar-conexao=false.
 */
@Component
@ConditionalOnProperty(prefix = "cloudflare.d1", name = "verificar-conexao", havingValue = "true", matchIfMissing = true)
public class D1ConnectionCheck implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(D1ConnectionCheck.class);

    private final D1Client d1Client;
    private final CloudflareD1Properties props;

    public D1ConnectionCheck(D1Client d1Client, CloudflareD1Properties props) {
        this.d1Client = d1Client;
        this.props = props;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (props.apiToken() == null || props.apiToken().isBlank()) {
            throw new IllegalStateException(
                    "CLOUDFLARE_API_TOKEN nao configurado. Crie um arquivo .env a partir de .env.example.");
        }
        d1Client.query("SELECT 1");
        log.info("Conexao com o Cloudflare D1 estabelecida (database {})", props.databaseId());
    }
}
