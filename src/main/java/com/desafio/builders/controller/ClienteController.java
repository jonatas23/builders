package com.desafio.builders.controller;

import com.desafio.builders.dto.ClienteDTO;
import com.desafio.builders.entities.Cliente;
import com.desafio.builders.service.ClienteService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/builders/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @ApiOperation(value = "Está operação salva um novo Cliente.")
    @PostMapping(value = "/")
    public ResponseEntity<?> salvar(@RequestBody ClienteDTO dto) {
        Cliente cliente = this.clienteService.salvar(dto.transformaParaObjeto());
        log.info("Cliente Salvo: " + cliente);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @ApiOperation(value = "Está operação salva um novo Cliente.")
    @PutMapping(value = "/")
    public ResponseEntity<?> update(@RequestBody Cliente cli) {
        Cliente cliente = this.clienteService.update(cli);
        log.info("Cliente atualizado: " + cliente);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @ApiOperation(value = "Retornara de todos Clientes cadastrados sem paginação.")
    @GetMapping(value = "/")
    public ResponseEntity<?> findAll() {
        List<Cliente> clientes = this.clienteService.findAll();
        log.info("Lista de todos os clientes");
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @ApiOperation(value = "Consulta de clientes com paginação.")
    @GetMapping(value = "/{page}/{size}")
    public ResponseEntity<?> findAll(@PathVariable Long page, @PathVariable Long size) {
        List<Cliente> clientes = this.clienteService.findAllPageable(page, size);
        log.info("Lista de todos os clientes de forma paginada");
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @ApiOperation(value = "Consulta realizada pelo ID do Cliente.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Cliente cliente = this.clienteService.findById(id);
        log.info("Busca de Cliente por ID");
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }
}
