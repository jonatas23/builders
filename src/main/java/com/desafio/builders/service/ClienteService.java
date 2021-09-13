package com.desafio.builders.service;

import com.desafio.builders.entities.Cliente;
import com.desafio.builders.exception.MensagemException;
import com.desafio.builders.exception.NotFoundException;
import com.desafio.builders.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class ClienteService {

    IClienteRepository clienteRepository;

    @Autowired
    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente salvar(Cliente cli) throws MensagemException {
        try {
            this.validarCliente(cli);
            return this.clienteRepository.save(cli);
        } catch (MensagemException e) {
            throw new MensagemException(e.getMessage());
        }
    }

    public Cliente update(Cliente cli) throws MensagemException {
        try {
            return this.clienteRepository.save(cli);
        } catch (MensagemException e) {
            throw new MensagemException(e.getMessage());
        }
    }

    public List<Cliente> findAll() {
        List<Cliente> ClienteS = new ArrayList<>();
        for (Cliente cliente : clienteRepository.findAll()) {
            ClienteS.add(cliente);
        }
        return ClienteS;
    }

    public List<Cliente> findAllPageable(Long page, Long size) {
        PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue(), Sort.Direction.ASC, "nome");

        List<Cliente> ClienteS = new ArrayList<>();
        for (Cliente cliente : clienteRepository.findAll(pageRequest).getContent()) {
            ClienteS.add(cliente);
        }
        return ClienteS;
    }

    public Cliente findById(Long id) {
        Cliente cliente =  clienteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return cliente;
    }

    private void validarCliente(Cliente cliente) throws MensagemException {
        this.isCPF(cliente.getCpf());

        if (!cliente.camposObrigatorios()){
            throw new MensagemException("Nome, CPF e Data de Nascimento são obrigatorios!");
        }

        if (clienteRepository.buscarCpf(cliente.getCpf()) != null){
            throw new MensagemException("CPF já cadastrado!");
        }
    }

    private void isCPF(String CPF) {
        CPF = removeCaracteresEspeciais(CPF);

        if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") || CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11)) {
            throw new MensagemException("Erro, CPF invalido !!!\\n");
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);

            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if (!((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))) {
                throw new MensagemException("Erro, CPF invalido !!!\\n");
            }
        } catch (InputMismatchException e) {
            throw new MensagemException("Erro, CPF invalido !!!\\n");
        }
    }

    private String removeCaracteresEspeciais(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        return doc;
    }
}
