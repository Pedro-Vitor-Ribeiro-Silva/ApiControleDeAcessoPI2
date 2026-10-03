package com.example.controledeacesso.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Tabela "pessoa" no Cloudflare D1 (colunas: cpf, nome, foto). */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    private String cpf;

    private String nome;

    private String foto;

}
