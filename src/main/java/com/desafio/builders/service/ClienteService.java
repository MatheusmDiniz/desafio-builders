package com.desafio.builders.service;

import com.desafio.builders.domain.Cliente;
import com.desafio.builders.repository.BuscarCriteria;
import com.desafio.builders.repository.ClienteRepository;
import com.desafio.builders.repository.ClienteSpecification;
import com.desafio.builders.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static org.springframework.data.jpa.domain.Specification.where;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente alterar(Integer id, Cliente cliente){
        cliente.setId(id);
        return this.salvar(cliente);
    }

    public Page<Cliente> buscarClientesPage(Integer page, Integer size) {
        Pageable pagina = PageRequest.of(page, size);
        return clienteRepository.findAll(pagina);
    }



    public List<Cliente> pesquisarPorCriterios(String parametros) {
        Pattern pattern = Pattern.compile("([\\w\\[.\\]\\\\]+)(:|>|<)([\\w\\[ \\]\\\\]+),");
        Matcher matcher = pattern.matcher(parametros + ",");
        List<BuscarCriteria> criterios = new ArrayList<>();

        while (matcher.find()) {
            criterios.add(new BuscarCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }

        if (criterios == null || criterios.size() == 0) {
            return clienteRepository.findAll();
        }
        Specification<Cliente> condicoes = where(new ClienteSpecification(criterios.get(0)));
        for (int i = 1; i < criterios.size(); i++) {
            condicoes = Specification.where(condicoes).and(new ClienteSpecification(criterios.get(i)));
        }
        List<Cliente> clientes = clienteRepository.findAll(condicoes);
        if (clientes.size() == 0) {
            throw new EntityNotFoundException(Cliente.class);
        }
        return clientes;
    }
}
