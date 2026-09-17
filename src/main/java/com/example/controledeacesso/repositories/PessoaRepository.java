package com.example.controledeacesso.repositories;

import com.example.controledeacesso.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, String> {
}
