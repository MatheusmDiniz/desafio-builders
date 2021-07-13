package com.desafio.builders.web.rest;

import com.desafio.builders.domain.*;
import com.desafio.builders.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ClienteControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClienteController clienteController;

	@MockBean
	private ClienteService clienteService;

	@Test
	public void contextLoads() {
		assertThat(clienteController).isNotNull();
	}

	@Test
	public void inserirCliente() throws Exception {
		Cliente clienteMock = getClienteMock();
		String clienteJSon = getClienteJSon(clienteMock);

		when(clienteService.salvar(clienteMock)).thenReturn(clienteMock);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/clientes/")
				.accept(MediaType.APPLICATION_JSON).content(clienteJSon)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertNotNull(response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void alterarCliente() throws Exception {
		Cliente clienteMock = getClienteMock();
		String clienteJSon = getClienteJSon(clienteMock);

		when(clienteService.alterar(clienteMock.getId(), clienteMock)).thenReturn(clienteMock);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/clientes/1")
				.accept(MediaType.APPLICATION_JSON).content(clienteJSon)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void listarClientesPage() throws Exception {
		Integer pageMock = 0;
		Integer sizeMock = 10;

		when(clienteService.buscarClientesPage(pageMock, sizeMock)).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/clientes/listarClientes?page=0&size=10");

		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void pesquisarPorParametros() throws Exception {
		String paramsMock = new String("buscar=nome:Annabelle, sexo:FEM, nascimento:1983-01-02, endereco.bairro:Sudoeste");

		when(clienteService.pesquisarPorCriterios(paramsMock)).thenReturn(Arrays.asList(getClienteMock()));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/clientes?buscar=nome:Annabelle, sexo:FEM, nascimento:1983-01-02, endereco.bairro:Sudoeste");

		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private Cliente getClienteMock() {
		Cliente clienteMock = new Cliente() ;
		clienteMock.setId(1);
		clienteMock.setNome("Cliente de teste");
		clienteMock.setEmail("cliente@mail.com");
		clienteMock.setNascimento(LocalDate.of(2000, 1, 1));
		clienteMock.setSexo(Sexo.MASC);

		Endereco endereco = new Endereco("Rua 1", "100", "Casa", "Centro", "Cocalzinho", Uf.GO, "12345678");
		Telefone telefone = new Telefone("61", "5551234");
		clienteMock.setEndereco(endereco);
		clienteMock.setTelefone(telefone);
		return clienteMock;
	}

	private String getClienteJSon(Cliente cliente) {
		String clienteJSon = "{" +
				"\"nome\":\"" + cliente.getNome() + "\"," +
				"\"email\":\""+ cliente.getEmail() + "\"," +
				"\"nascimento\":\"" + cliente.getNascimento() + "\"," +
				"\"sexo\":\"" + cliente.getSexo().name() + "\"}";
		return clienteJSon;
	}

}
