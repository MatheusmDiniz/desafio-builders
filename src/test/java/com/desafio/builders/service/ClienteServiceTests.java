package com.desafio.builders.service;

import com.desafio.builders.domain.Cliente;
import com.desafio.builders.repository.ClienteRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ClienteServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void contextLoads() {
        assertThat(clienteService).isNotNull();
        assertThat(clienteRepository).isNotNull();
    }


    @Test
    public void deveCadastrarCliente() {
        Cliente cliente = mock(Cliente.class);
        when(clienteRepository.save(any())).thenReturn(cliente);
        clienteService.salvar(cliente);

        verify(clienteRepository,times(1)).save(any());

    }

    @Test
    public void deveAtualizarCliente() {
        Cliente cliente = mock(Cliente.class);
        Integer idMock = 1;
        when(clienteService.alterar(idMock, cliente)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        clienteService.alterar(idMock,cliente);

        verify(clienteRepository,times(1)).save(any());
    }

    @Test
    public void deveBuscarClientesPage() {
        Integer pageMock = 0;
        Integer sizeMock = 10;

        when(clienteRepository.findAll(PageRequest.of(pageMock, sizeMock))).thenReturn(null);
        clienteService.buscarClientesPage(pageMock,sizeMock);

        verify(clienteRepository,times(1)).findAll((Pageable) any());

    }

    @Test
    public void pesquisarPorCriterios() {
        String paramsMock = new String("buscar=nome:Annabelle, sexo:FEM, nascimento:1983-01-02, endereco.bairro:Sudoeste");
        List<Cliente> cliente = Arrays.asList(mock(Cliente.class), mock(Cliente.class));
        when(clienteRepository.findAll((Specification<Cliente>) any())).thenReturn(cliente);
        clienteService.pesquisarPorCriterios(paramsMock);

        verify(clienteRepository,times(1)).findAll((Specification<Cliente>) any());
    }

    @Test
    public void pesquisarPorCriteriosSemCriterio() {
        String paramsMock = new String("buscar=");
        List<Cliente> cliente = Arrays.asList(mock(Cliente.class), mock(Cliente.class));
        when(clienteRepository.findAll()).thenReturn(cliente);
        clienteService.pesquisarPorCriterios(paramsMock);

        verify(clienteRepository,times(1)).findAll();
    }
}
