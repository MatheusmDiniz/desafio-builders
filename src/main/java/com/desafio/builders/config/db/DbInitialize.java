//package com.desafio.builders.config.db;
//
//import com.desafio.builders.domain.*;
//import com.desafio.builders.repository.ClienteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.Period;
//import java.util.Arrays;
//
//@Service
//public class DbInitialize {
//
//    @Autowired
//    ClienteRepository clienteRepository;
//
//
//    public void instantieateTestDatabase() {
//
//        Cliente cliente1 = new Cliente();
//        cliente1.setId(1);
//        cliente1.setNome("Lucas Carmo");
//        cliente1.setEmail("lucas@gmail.com");
//        cliente1.setNascimento(LocalDate.of(2000, 1, 1));
//        cliente1.setSexo(Sexo.MASC);
//        cliente1.setIdade(Period.between(cliente1.getNascimento(), LocalDate.now()).getYears());
//
//        Endereco endereco1 = new Endereco("Rua 1", "100", "Casa", "Centro", "Brasilia", Uf.DF, "12345678");
//        Telefone telefone2 = new Telefone("61", "55511234");
//        cliente1.setEndereco(endereco);
//        cliente1.setTelefone(telefone);
//
//        Cliente cliente2 = new Cliente();
//        cliente2.setId(1);
//        cliente2.setNome("Lucas Carmo");
//        cliente2.setEmail("lucas@gmail.com");
//        cliente2.setNascimento(LocalDate.of(2000, 1, 1));
//        cliente2.setSexo(Sexo.MASC);
//        cliente2.setIdade(Period.between(cliente2.getNascimento(), LocalDate.now()).getYears());
//
//        Endereco endereco = new Endereco("Rua 1", "100", "Casa", "Centro", "Brasilia", Uf.DF, "12345678");
//        Telefone telefone = new Telefone("61", "55511234");
//        cliente2.setEndereco(endereco);
//        cliente2.setTelefone(telefone);
//
//
//
//
//        clienteRepository.saveAll(Arrays.asList(cliente1));
//
//
//
//    }
//}
