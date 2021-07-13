package com.desafio.builders.repository;

import com.desafio.builders.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {
}
