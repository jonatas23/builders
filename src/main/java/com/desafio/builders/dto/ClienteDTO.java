package com.desafio.builders.dto;

import com.desafio.builders.entities.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO implements Serializable {

    private String nome;
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    public Cliente transformaParaObjeto() {
        return new Cliente(nome, cpf, dataNascimento);
    }

}
