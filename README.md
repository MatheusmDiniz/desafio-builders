# Cadastro de clientes

Este projeto define a API para um MVP de cadastro de clientes.

## O Projeto
O projeto foi construído usando Java e Spring Boot. Os dados são armazenados no H2 Database do proprio Spring ou Postgresql que é gerenciado via Docker.

### A API
Os endpoints estão definidos no pacote `com.desafio.builders.web.rest`.
A documentação da API está acessível a partir de:
http://localhost:8080/swagger-ui/

Os requisitos passados foram:

Desenvolva uma REST API que:

1. Permita criação de novos clientes;
- Acessível através de uma chamada HTTP através do método POST. O método recebe como parâmetro um arquivo JSON com os dados de usuário e a validação é feita através das anotações do Hibernate Validator. O tratamento de erro é feito de forma geral para todos os endpoints do projeto através da configuração de um aspecto definido na classe RestExceptionHandler. Em caso de erro da validação é apresentada uma mensagem detalhada e com o status de resposta correto. 
    
2. Permita a atualização de clientes existentes;
- **Alteração completa**: Acessível através do método PUT e permite a atualização do cadastro completo do cliente. Segue as mesmas definições do POST para a validação das entradas e dados de retorno.

3. Permita que seja possível listar os clientes de forma paginada;
-  A consulta paginada foi implementada no endpoint que retorna todos os clientes. É acessível através do metodo GET e recebe como parâmetro de entrada a pagina atual (page) e o tamanho da página (size). A partir daí, na camada de serviço, é criado um objeto PageRequest e feita a consulta através do Spring Data JPA que retorna um objeto do tipo Page. 

4. Permita que buscas por atributos cadastrais do cliente;
- Foi criada uma especificação (ClienteSpecification) para que fosse possível encadear vários atributos de pesquisa passados para o endpoint via parâmetros queryString. Dessa forma, a camada de serviço pode chamar a camada de repositório passando como parâmetro as especificações recebidas.  

5. É necessário também que cada elemento retornado pela api de clientes informe a idade;
- Como o modelo é bastante simples, para evitar a criação de objetos desnecessários, a própria entidade está sendo enviada como retorno para o chamador do endpoint. Nesse caso, para retornar a idade do cliente foi implementado um método getIdade() na entidade Cliente.

6. Documente sua API e também disponibilize um arquivo Postman para fácil utilização da API.
- A documentação da API está disponível em `http://localhost:8080/swagger-ui/`
- O arquivo `bulders project.postman_collection.json` que se encontra na raiz do projeto pode ser usado para fazer os testes da API.

### Testes
Foram feitos testes em alguns métodos da API apenas para demonstrar como os testes devem ser executados.

Execute os comandos: 
`mvn clean test`

### Dados
A criação das tabelas e a carga dos dados iniciais da aplicação está sob responsabilidade do liquibase. Dessa forma é possível manter um controle fino de cada alteração do banco de dados. Os arquivos de configuração do liquibase estão no diretório `src/main/resources/db/changelog`. 

O liquibase será executado no startup da aplicação e executará todos os scripts configurados no diretório mencionando acima.

### Docker

O Docker é usado no projeto para instanciar um container do Postgresql.

Também é usado o Docker-compose para permitir que a aplicação seja disponibilizada através do docker e nesse caso o container de banco dados também é configurado e instanciado através de uma única configuração.

## Executar a aplicação
Para executar a aplicação vá para o diretório raiz do projeto e rode o seguinte comando
```
mvn clean install -U
```
Caso ache necessário, exclua a imagem docker criada automaticamente pelo spring-docker-spotify
```
docker rmi fexx182/spring-docker-spotify:0.0.1-SNAPSHOT
```

* **Executar via maven:** 

1. **Executar usando H2 Database**

    1.1. Execute a aplicação através do comando:     
            `mvn spring-boot:run`
        
2. **Executar usando postgres**

    2.1.  Se você já tiver executado alguma vez esses comandos, é acoselhável excluir o container docker para evitar confitos. Execute o comando: 
                     `docker rm cliente-postgresql`
    
    2.1. A aplicação depende do banco de dados postgresql, para iniciá-lo execute o comando:
       `docker-compose -f src/main/docker/postgres.yml up -d`

    2.3. Execute a aplicação através do comando:     
        `mvn spring-boot:run -Dspring-boot.run.profiles=postgres-docker`

    2.4. Acesse a aplicação via postman, importe o arquivo de testes e execute alguns comandos.

    2.5. Remova o container docker:
       `docker-compose -f src/main/docker/postgres.yml down`

* **Executar via docker-compose:**

1. **Executar usando H2 Database**

    1.1. Inicie a aplicação via docker-compose:   
       `docker-compose -f docker-compose-dbh2.yml up -d`

    1.2. Acesse a aplicação via postman, importe o arquivo de testes e execute alguns comandos.


2. **Executar usando postgres**

    2.1. Se você já tiver executado alguma vez esses comandos, é acoselhável excluir o container docker para evitar confitos. Execute o comando: 
        `docker rm cadastro-postgresql`

    2.2. Inicie a aplicação via docker-compose:   
        `docker-compose -f docker-compose-dbpostgres.yml up -d`

    2.3. Acesse a aplicação via postman, importe o arquivo de testes e execute alguns comandos.

    2.4. Remova o container docker:
        `docker-compose -f src/main/docker/postgres.yml down`
 
