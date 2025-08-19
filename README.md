# Forum API

Projeto de uma **API REST para Fórum Online**, construída em Java com Spring Boot e módulos do Spring Framework.  
Todo o código foi desenvolvido como prática dos cursos da [Alura](https://cursos.alura.com.br/course/spring-boot-api-rest), ministrados por [Rodrigo Ferreira](https://www.linkedin.com/in/rcaneppele).

---

## 🚀 Como rodar o projeto

### 1. Instalar dependências
Utilize o **Maven** para baixar todas as dependências declaradas no `pom.xml`.

### 2. Escolher o perfil de execução
A aplicação pode ser iniciada com três perfis de ambiente: **dev**, **test** e **prd**.  

Se estiver executando pela IDE:

```bash
# Eclipse
Run Configurations -> VM arguments
-Dspring.profiles.active=dev

# IntelliJ
Run -> Edit Configurations -> Program arguments
--spring.profiles.active=dev
que você tenha, ficarei feliz em ouvir e implementar nos meus projetos.
