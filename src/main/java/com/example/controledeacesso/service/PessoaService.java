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

    public void atualizarPessoa(String cpf, String nome, String foto) {
        Pessoa pessoa = pessoaRepository.findById(cpf).orElse(null);
        if (pessoa != null) {
            pessoa.setNome(nome);
            pessoa.setFoto(foto);
            pessoaRepository.save(pessoa);
        }
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
