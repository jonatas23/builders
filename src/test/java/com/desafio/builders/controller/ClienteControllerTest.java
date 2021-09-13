package com.desafio.builders.controller;

import com.desafio.builders.entities.Cliente;
import com.desafio.builders.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteControllerTest {

    private static final String CLIENTE_CONTROLLER = "/builders/clientes/";
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve criar um Cliente")
    public void shouldCreateANewCliente() throws Exception {
        Cliente cliente = this.createCliente(1l);
        String jsonCliente = mapper.registerModule(new JavaTimeModule()).writeValueAsString(cliente);

        BDDMockito.when(this.clienteService.salvar(cliente)).thenReturn(cliente);

        this.mvc.perform(post(CLIENTE_CONTROLLER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCliente)
        ).andExpect(status().isOk());

        verify(clienteService, times(1)).salvar(any(Cliente.class));
    }

    @Test
    @DisplayName("Lista todos os Clientes")
    public void shouldFindAll() throws Exception {
        this.mvc.perform(get(CLIENTE_CONTROLLER)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Retorna cliente pela Busca do ID")
    public void shouldFindById() throws Exception {
        this.mvc.perform(get(CLIENTE_CONTROLLER + 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private Cliente createCliente(Long id) {
        Cliente cliente = new Cliente("Fulano Teste", "123.456.789-11", LocalDate.parse("1995-11-23"));
        cliente.setId(id);
        return cliente;
    }

}
