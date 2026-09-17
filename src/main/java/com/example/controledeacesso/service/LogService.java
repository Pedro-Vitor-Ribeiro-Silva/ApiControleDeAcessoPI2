package com.example.controledeacesso.service;

import com.example.controledeacesso.entity.Log;
import com.example.controledeacesso.repositories.LogRepository;
import com.example.controledeacesso.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final PessoaRepository pessoaRepository;
    private final LogRepository logRepository;

    public List<Log> listarLogs() {
        return logRepository.findAll();
    }

    public Log buscarLogPorId(Long id) {
        return logRepository.findById(id).orElse(null);
    }

    public List<Log> buscarLogsDeUmaPessoaPorCpf(String cpf) {
        return logRepository.findByPessoaCpf(cpf);
    }


}
