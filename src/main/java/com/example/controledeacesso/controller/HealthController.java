package com.example.controledeacesso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Healthcheck para monitores de uptime (ex.: UptimeRobot) e para o Render.
 * Não consulta o D1: só confirma que a aplicação está no ar. O Spring responde HEAD
 * automaticamente em rotas GET.
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
