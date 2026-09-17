package com.example.controledeacesso.repositories;

import com.example.controledeacesso.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByPessoaId(String pessoaId);
    List<Log> findByPessoaIdAndTipoAcesso(String pessoaId, String tipoAcesso);
    List<Log> findByPessoaIdAndDataHora(String pessoaId, String dataHora);
}
