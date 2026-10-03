package com.example.controledeacesso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Credenciais e identificadores do banco Cloudflare D1.
 * Valores vem de application.properties / variaveis de ambiente (ver .env.example).
 */
@ConfigurationProperties(prefix = "cloudflare.d1")
public record CloudflareD1Properties(
        String accountId,
        String databaseId,
        String apiToken,
        boolean verificarConexao
) {
}
