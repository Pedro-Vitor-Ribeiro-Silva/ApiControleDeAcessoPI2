package com.example.controledeacesso.entity;

import com.example.controledeacesso.entity.Enum.TipoAcesso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** Tabela "logs" no Cloudflare D1 (colunas: id, pessoa_cpf, tipo, data_hora). */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {

   private Long id;

   private Pessoa pessoa;

   private TipoAcesso tipoAcesso;
   private LocalDateTime dataHora;
}
