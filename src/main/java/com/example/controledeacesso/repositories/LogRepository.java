package com.example.controledeacesso.repositories;

import com.example.controledeacesso.entity.Enum.TipoAcesso;
import com.example.controledeacesso.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByPessoaCpf(String cpf);
    List<Log> findByPessoaCpfAndTipoAcesso(String cpf, TipoAcesso tipoAcesso);
    List<Log> findByPessoaCpfAndDataHora(String cpf, LocalDateTime dataHora);
}
