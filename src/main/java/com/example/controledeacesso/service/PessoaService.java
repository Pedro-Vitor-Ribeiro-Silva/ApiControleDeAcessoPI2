package com.example.controledeacesso.service;

import com.example.controledeacesso.dto.PessoaDTO;
import com.example.controledeacesso.entity.Pessoa;
import com.example.controledeacesso.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public void cadastroPessoa(PessoaDTO data) throws IOException {

        String fotoBase64 = Base64.getEncoder()
                .encodeToString(data.getFoto().getBytes());

        Pessoa pessoa = Pessoa.builder()
                .cpf(data.getCpf())
                .nome(data.getNome())
                .foto(fotoBase64)
                .build();

        pessoaRepository.save(pessoa);
    }

    public Pessoa buscarPessoaPorCpf(String cpf) {
        return pessoaRepository.findById(cpf).orElse(null);
    }

    public void atualizarPessoa(PessoaDTO data) throws IOException {
        Pessoa pessoa = pessoaRepository.findById(data.getCpf()).orElse(null);
        if (pessoa == null) {
            return;
        }

        pessoa.setNome(data.getNome());

        if (data.getFoto() != null && !data.getFoto().isEmpty()) {
            String fotoBase64 = Base64.getEncoder()
                    .encodeToString(data.getFoto().getBytes());
            pessoa.setFoto(fotoBase64);
        }

        pessoaRepository.save(pessoa);
    }

    public void deletarPessoa(String cpf) {
        pessoaRepository.deleteById(cpf);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa buscarPessoaPorId(String id) {
        return pessoaRepository.findById(id).orElse(null);
    }
}
