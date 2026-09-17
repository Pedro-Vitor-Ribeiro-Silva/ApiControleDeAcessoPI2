package com.example.controledeacesso.entity;

import com.example.controledeacesso.entity.Enum.TipoAcesso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "pessoa_cpf")
   private Pessoa pessoa;

   private TipoAcesso tipoAcesso;
   private LocalDateTime dataHora;
}


