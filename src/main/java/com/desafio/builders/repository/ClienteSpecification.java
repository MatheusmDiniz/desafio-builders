package com.desafio.builders.repository;
import com.desafio.builders.domain.Cliente;
import com.desafio.builders.domain.Sexo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Locale;

@AllArgsConstructor
public class ClienteSpecification implements Specification<Cliente> {

    private final BuscarCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getKey().startsWith("endereco")) {
            String nomeAtributo = criteria.getKey().replace("endereco.", "");

            if (criteria.getOperation().equals(":")) {
                return builder.like(builder.upper(root.get("endereco").get(nomeAtributo)), "%" + criteria.getValue().toUpperCase(Locale.ROOT) + "%");
            }
        }

        if (criteria.getKey().startsWith("telefone")) {
            String nomeAtributo = criteria.getKey().replace("telefone.", "");

            if (criteria.getOperation().equals(":")) {
                return builder.like(builder.upper(root.get("telefone").get(nomeAtributo)), "%" + criteria.getValue().toUpperCase(Locale.ROOT) + "%");
            }
        }

        if (criteria.getKey().equalsIgnoreCase("sexo")) {
            Sexo sexo = Sexo.valueOf(criteria.getValue());
            return builder.equal(root.get("sexo"), sexo);
        }

        if (criteria.getKey().equalsIgnoreCase("idade")) {
            Integer idade = Integer.valueOf((criteria.getValue()));
            LocalDate dataNascimento = LocalDate.now().minusYears(idade);
            return builder.between(root.get("idade"), dataNascimento, LocalDate.now());
        }

        if (criteria.getOperation().equals(":")) {
            return builder.like(builder.upper(root.get(criteria.getKey())), "%" + criteria.getValue().toUpperCase(Locale.ROOT) + "%");
        }

        return null;
    }

}
