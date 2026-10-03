package com.example.controledeacesso.repositories;

import com.example.controledeacesso.db.D1Client;
import com.example.controledeacesso.db.SqliteDateTime;
import com.example.controledeacesso.entity.Enum.TipoAcesso;
import com.example.controledeacesso.entity.Log;
import com.example.controledeacesso.entity.Pessoa;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class LogRepository {

    private static final String SELECT = """
            SELECT l.id, l.pessoa_cpf, l.tipo, l.data_hora, p.nome, p.foto
            FROM logs l
            LEFT JOIN pessoa p ON p.cpf = l.pessoa_cpf
            """;

    private static final String ORDENACAO = " ORDER BY l.data_hora DESC, l.id DESC";

    private final D1Client d1;

    public LogRepository(D1Client d1) {
        this.d1 = d1;
    }

    public List<Log> findAll() {
        return mapear(d1.query(SELECT + ORDENACAO));
    }

    public Optional<Log> findById(Long id) {
        return mapear(d1.query(SELECT + " WHERE l.id = ?", id)).stream().findFirst();
    }

    public List<Log> findByPessoaCpf(String cpf) {
        return mapear(d1.query(SELECT + " WHERE l.pessoa_cpf = ?" + ORDENACAO, cpf));
    }

    public List<Log> findByPessoaCpfAndTipoAcesso(String cpf, TipoAcesso tipoAcesso) {
        return mapear(d1.query(SELECT + " WHERE l.pessoa_cpf = ? AND l.tipo = ?" + ORDENACAO,
                cpf, tipoAcesso.name()));
    }

    public List<Log> findByPessoaCpfAndDataHora(String cpf, LocalDateTime dataHora) {
        return mapear(d1.query(SELECT + " WHERE l.pessoa_cpf = ? AND l.data_hora = ?" + ORDENACAO,
                cpf, SqliteDateTime.format(dataHora)));
    }

    /** Insere um novo log. Se dataHora for nula, o banco usa CURRENT_TIMESTAMP. */
    public Log save(Log log) {
        List<Map<String, Object>> rows;
        if (log.getDataHora() == null) {
            rows = d1.query("""
                    INSERT INTO logs (pessoa_cpf, tipo) VALUES (?, ?)
                    RETURNING id, data_hora
                    """, log.getPessoa().getCpf(), log.getTipoAcesso().name());
        } else {
            rows = d1.query("""
                    INSERT INTO logs (pessoa_cpf, tipo, data_hora) VALUES (?, ?, ?)
                    RETURNING id, data_hora
                    """, log.getPessoa().getCpf(), log.getTipoAcesso().name(),
                    SqliteDateTime.format(log.getDataHora()));
        }
        if (!rows.isEmpty()) {
            Map<String, Object> row = rows.get(0);
            log.setId(((Number) row.get("id")).longValue());
            log.setDataHora(SqliteDateTime.parse(row.get("data_hora")));
        }
        return log;
    }

    public void deleteById(Long id) {
        d1.update("DELETE FROM logs WHERE id = ?", id);
    }

    private static List<Log> mapear(List<Map<String, Object>> rows) {
        return rows.stream().map(LogRepository::mapear).toList();
    }

    private static Log mapear(Map<String, Object> row) {
        Pessoa pessoa = Pessoa.builder()
                .cpf((String) row.get("pessoa_cpf"))
                .nome((String) row.get("nome"))
                .foto((String) row.get("foto"))
                .build();

        Object tipo = row.get("tipo");
        return new Log(
                ((Number) row.get("id")).longValue(),
                pessoa,
                tipo == null ? null : TipoAcesso.valueOf(tipo.toString()),
                SqliteDateTime.parse(row.get("data_hora"))
        );
    }
}
