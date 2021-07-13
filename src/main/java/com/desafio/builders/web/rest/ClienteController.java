package com.desafio.builders.web.rest;

import com.desafio.builders.domain.Cliente;
import com.desafio.builders.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Cadastrar cliente",
            description = "Recebe as informações do cliente e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "O cliente foi salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Retorna uma mensagem de erro detalhada"),
    })
    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Cliente cliente) {
        clienteService.salvar(cliente);
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Alterar cliente",
            description = "Altera todos os dados do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O cliente alterado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Retorna uma mensagem de erro detalhada"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        clienteService.alterar(id, cliente);
        return ResponseEntity.ok(clienteService.alterar(id, cliente));
    }

    @Operation(summary = "Listar todos os clientes",
            description = "Lista todos os clientes e apresenta suas informações em páginas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma página com as informações dos clientes"),
            @ApiResponse(responseCode = "500", description = "Retorna uma mensagem de erro detalhada"),
    })
    @GetMapping("/listarClientes")
    public Page<Cliente> listarClientesPage(
            @RequestParam @NotNull Integer page,
            @RequestParam @NotNull Integer size) {

        return clienteService.buscarClientesPage(page, size);
    }

    @Operation(summary = "Pesquisar por parâmetros",
            description = "Recebe uma lista de parametros através do atributo 'buscar' e pesquisa os clientes que " +
                    "atendem aos critérios da pesquisa. Os parêmtros devem seguir o padrão [nome_paramtro]:[valor]")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de clientes"),
            @ApiResponse(responseCode = "404", description = "Pesquisa sem resultado"),
            @ApiResponse(responseCode = "500", description = "Retorna uma mensagem de erro detalhada"),
    })
    @RequestMapping(method = RequestMethod.GET, params = {"buscar"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Cliente> pesquisarPorParametros(@RequestParam(value = "buscar") String buscar) {
        return clienteService.pesquisarPorCriterios(buscar);
    }
}