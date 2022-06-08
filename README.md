# ApiDictonary

Aplicativo para listar palavras em inglês, utilizando como base a API Free Dictionary API. A partir desta api é possível favoritar palavras, manter e acessar o histórico de palavras e outras funcionalidades

This is a challenge by [Coodesh ](https://coodesh.com/)

## Tecnologias
- [Java 11 ](https://www.java.com/pt-BR/)
- [SpringBoot 2.7.0 ](https://spring.io/projects/spring-boot)
- [Postgresql 14.3 ](https://www.postgresql.org/)

## Como rodar a aplicação

- Crie um banco de dados postgresql
- Configure o arquivo application.properties alterando a url, username e password de acordo com sua configuração
		
		```
		#Spring Boot server configuration
		spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/api-dictionary?autoReconnect=true
		spring.datasource.username=postgres
		spring.datasource.password=admin
		spring.datasource.driver-class-name= org.postgresql.Driver
		spring.jpa.hibernate.ddl-auto=update
		spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false
		spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL9Dialect
		server.servlet.context-path=/api-dictionary
		
		```
- Execute o projeto

Se você usa maven execute o comando `mvn spring-boot:run` no terminal para inicializar o servidor de desenvolvimento.

Caso deseje gerar um arquivo jar execute o comando `mvn clean package` e em seguida execute o comando `java -jar target/api-dictionary-0.0.1-SNAPSHOT.jar`

Acesse a url `http://localhost:8080/`.

## Utilização

<details open>
<summary>[POST] /auth/signup</summary>
</details>
<details open>
<summary>[POST] /auth/signin</summary>
</details>
<details open>
<summary>[GET] /entries/en</summary>

<p>
Retornar a lista de palavras do dicionário, com paginação e suporte a busca. O endpoint de paginação de uma busca hipotética deve retornar a seguinte estrutura:
</p>
<br/>
</details>

<details open>
<summary>[GET]/entries/en?search=fire&limit=4</summary>
</details>

<details open>
<summary>[GET] /entries/en/:word</summary>
<p>
Retornar as informações da palavra especificada e registra o histórico de acesso.
</p>
</details>

<details open>
<summary>[POST] /entries/en/:word/favorite</summary>
<p>
Salva a palavra na lista de favoritas (retorno opcional)
</p> 
</details>

<details open>
<summary>[DELETE] /entries/en/:word/unfavorite</summary>
<p>
Remover a palavra da lista de favoritas (retorno opcional)
</p>
</details> 

<details open>
<summary>[GET] /user/me</summary>
<p>
Retornar o perfil do usúario
</p>
</details> 

<details open>
<summary>[GET] /user/me/history</summary>
<p>
Retornar a lista de palavras visitadas
</p>
</details> 

<details open>
<summary>[GET] /user/me/favorites</summary>
<p>
Retornar a lista de palavras marcadas como favoritas
</p>
</details>

## Frontend:

[Repositório frontend](https://github.com/Matheuspsilva/frontend-dictonary)

