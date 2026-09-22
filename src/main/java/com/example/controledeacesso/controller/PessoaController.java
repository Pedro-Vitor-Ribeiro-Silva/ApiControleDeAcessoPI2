package com.example.controledeacesso.controller;

import com.example.controledeacesso.dto.PessoaDTO;
import com.example.controledeacesso.entity.Pessoa;
import com.example.controledeacesso.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping("/listar")
    public List<Pessoa> listarPessoas() {
        return pessoaService.listarPessoas();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastroPessoa(
            @ModelAttribute PessoaDTO data
    ) throws IOException {

        pessoaService.cadastroPessoa(data);

        return ResponseEntity.ok(
                "Acesso de " + data.getNome() + " registrado!"
        );
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarPessoa(@ModelAttribute PessoaDTO data) throws IOException {
        pessoaService.atualizarPessoa(data);
        return ResponseEntity.ok("Informações de Acesso atualizado com sucesso!");
    }
}
