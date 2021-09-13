package com.desafio.builders.repository;

import com.desafio.builders.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c from Cliente c where c.cpf = :cpf")
    public Cliente buscarCpf(@Param("cpf") String cpf);
}
