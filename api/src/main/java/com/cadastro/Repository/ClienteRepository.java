package com.cadastro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cadastro.model.Clientes;

@Repository
public interface  ClienteRepository extends  JpaRepository<Clientes, Long> {}
