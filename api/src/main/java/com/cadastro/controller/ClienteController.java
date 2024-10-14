package com.cadastro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadastro.Repository.ClienteRepository;
import com.cadastro.model.Clientes;

@RestController
@RequestMapping("/clientes") //todas a requisições com essa URI será controlado por essa classe
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping()
    public ResponseEntity<List<Clientes>> listar() {
        List<Clientes> clientes =  clienteRepository.findAll();

        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }


    @PostMapping() //Toda requisição que contém o Verbo Post, será executada por esse método
    public ResponseEntity<Clientes> adicionar(@RequestBody Clientes cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
    }

    @PutMapping // Copo da requisição deve ser vinculado a este parâmetro
    public ResponseEntity<Clientes> atualizar(@RequestBody Clientes cliente) {
        Optional<Clientes> clienteOptional = clienteRepository.findById(cliente.getCpf());

        if (clienteOptional.isPresent()) {
            BeanUtils.copyProperties(cliente, clienteOptional.get(),"id");   
            cliente = clienteRepository.save(clienteOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping
    public ResponseEntity<Clientes> remover(@RequestBody Clientes cliente) {
        Optional<Clientes> clienteOptional = clienteRepository.findById(cliente.getCpf());

        if(clienteOptional.isPresent()){
            clienteRepository.delete(clienteOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
}
