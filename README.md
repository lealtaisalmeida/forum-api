# Forum API 
Este projeto consiste em uma **API REST de um Fórum Online** construída com Java e alguns módulos do Spring Framework. Todo o código foi escrito como prática e registro do meu aprendizado durante os cursos [Spring Boot API REST](https://cursos.alura.com.br/course/spring-boot-api-rest) da Alura, conduzidos pelo instrutor [Rodrigo Ferreira](https://www.linkedin.com/in/rcaneppele).

## Como utilizar
Para testar o projeto, primeiramente devemos utilizar o **Maven** para instalar as dependências declaradas no `pom.xml`. Em seguida, devemos indicar com quais configurações de ambiente queremos subir a aplicação: *dev*, *test* ou *prd*. Se estivermos rodando o projeto diretamente de uma IDE, podemos indicar isso da seguinte forma:

```
// No Eclipse: Run Canfigurations -> “VM arguments”
-Dspring.profiles.active.dev

// No IntelliJ: Run -> Edit Configurations -> "Program arguments"
--spring.profiles.active=dev
```

Além disso, se as configurações de *prd* forem indicadas, devemos fornecer as variáveis de ambiente para o banco de dados e para o *secret* da geração de tokens JWT (essas variáveis estarão listadas logo abaixo). Assim, uma outra opção é fazer o *build* do projeto e rodar a aplicação a partir de um arquivo `.jar`, bastando executar `mvn clean package` e em seguida:

```bash
java \  
  -DFORUM_DATABASE_URL=DATABASE:h2:mem:forumdb \ 
  -DFORUM_DB_USERNAME=sa \
  -DFORUM_DB_PASSWORD= \
  -DFORUM_JWT_SECRET=123456 \
  -Dspring.profiles.active.prd \
  -jar forum-api-0.0.1-SNAPSHOT.jar \
  /
```

Por fim, o projeto também conta com um `Dockerfile` que permite construir uma imagem e subir a aplicação em um container com **Docker**:

```bash
docker build . -t forum/forum-api

docker run \
  -p 8080:8080 \
  -e FORUM_DATABASE_URL='DATABASE:h2:mem:forumdb' \
  -e FORUM_DB_USERNAME='sa' \
  -e FORUM_DB_PASSWORD='' \
  -e FORUM_JWT_SECRET='123456' \
  -e SPRING_PROFILES_ACTIVE='prd' forum \
  /forumapi \
  /
```

Para conhecer todos os endpoints e como montar as requisições, basta acessar `localhost:8080/swagger-ui.html`. Em *dev*, todos os endpoints estão abertos e não exigem autenticação. Em *prd*, no entanto, é preciso gerar um token JWT em `POST /auth` para que seja possível fazer outras requisições.

## Tópicos
Alguns dos tópicos estudados durante o curso e implementados neste código:
- Exposição de endpoints com `@RestController`
- Uso dos padrões `DTO` e `Form` para entrada e saída de dados
- `Spring Data` e `Hibernate` para persistência, busca e manipulação dos dados
- Montagem de respostas com `ResponseEntity`
- Validação de dados com `Bean Validation`
- Tratamento de exceções com `@RestControllerAdvice`
- Paginação e ordenação com `Pageable`, `Direction` e `SpringDataWebSupport`
- Cache com `@Cacheable` e `@CacheEvict`
- Configurações de autenticação e autorização com `Spring Security`
- Geração e validação de JWT com a lib `jjwt` e filtro `OncePerRequestFilter`
- Monitoramento com `Spring Boot Actuator` e `Spring Boot Admin`
- Documentação com `SpringFox Swagger`
- Configurações de ambientes com `@Profile`
- Testes automatizados dos *beans* do Spring com `@SpringBootTest`, `@DataJpaTest` e `MockMvc`
- Build e deploy da aplicação com `Maven` e `Docker`

### Feedback
Estou em processo de formação e aprendizagem. Qualquer dica ou correção que você tenha, ficarei feliz em ouvir e implementar nos meus projetos.
