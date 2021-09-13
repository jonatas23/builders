## Descrição

O projeto é uma API cuja sua função é manter uma rede de Clientes .

### Linguagem utilizada

- Java 8

### Frameworks utilizados

- SpringBoot
- Lombok
- Hibernate

### Banco de Dados

- H2 (Teste)
- MySQL

### DevOPS/Infra Stack

- Git
- Swagger
- Docker

### Testes

Foi utilizado o JUnit para montagem dos testes em backend.

Para rodar os testes unitários da aplicação basta utilizar o comando padrão do maven

```
mvn test
```

ou acesse as classes abaixo e rode a aplicação pelo JUnit na sua IDE de preferência.

```
builders > com > desafio > builders > controller > ClienteControllerTest.java
```

### Instruções para a execução

Para iniciar aplicação basta executar o script run.sh na raiz do projeto e para parar basta executar o script stop.sh

```
sh run.sh
sh stop.sh
```

### Documentação da API

Realizada através do Swagger e disponível em execução na URL **http://localhost:8080**


### Conclusão

Qualquer dúvida, sugestão ou caso queira entrar em contato, basta enviar um e-mail para **jonataseduard23@gmail.com** (Jonatas Edward). 
