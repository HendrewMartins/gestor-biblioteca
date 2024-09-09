
# Projeto de Biblioteca

Este projeto é uma API de gerenciamento de biblioteca, desenvolvida em **Java 21** utilizando **Spring Boot**, **Hibernate**, **JPA**, **Swagger**, **Postgres** e testes com **JUnit** e **H2**. A aplicação permite o cadastro de usuários, livros, empréstimos e integração com a API do **Google Books** para busca e inserção de livros no sistema. O projeto também inclui uma aplicação web em Angular 18 que utiliza Angular Material.

## Funcionalidades

### API

A API realiza as seguintes operações:

- Cadastro de usuário
- Cadastro de livro
- Geração de empréstimos
- Consulta na API do Google Books com inserção no sistema
- Endpoint de recomendação que retorna uma lista de livros recomendados

### Frontend

A aplicação web em Angular 18 inclui:

- Tabelas utilizando Angular Material
- Integração com a API do backend
- Ajustes no environment para apontar para a URL do serviço

## Estrutura do Projeto

### Backend

- **Java 21**
- **Spring Boot**
- **Hibernate**
- **JPA**
- **PostgreSQL**
- **Testes com JUnit e H2**

### Frontend

- **Angular 18**
- **Angular Material**

## Configuração

Para rodar o projeto, você pode abrir na IDE (IntelliJ IDEA, por exemplo) ou gerar o `.jar`. Antes de iniciar, é necessário configurar as seguintes variáveis de ambiente:

- `PASSWORD_API=0102`
- `DATABASE_API=localhost:5433/gestorbiblioteca`

### Arquivo `application.yml`

Também podem ser alterado direto no arquivo `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DATABASE_API}
    username: user
    password: ${PASSWORD_API}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Configuração do Frontend

Para rodar a aplicação Angular, você deve ajustar o arquivo de environment do projeto apontando para a URL do serviço:

```typescript
export const environment = {
  production: false,
  apiUrl: 'URL_DO_SERVIÇO_AQUI'
};
```

```cmd
git clone [URL_DO_REPOSITORIO](https://github.com/HendrewMartins/gestor-biblioteca.git)
cd gestor-biblioteca-api
./mvnw spring-boot:run
```
Acesse a API em http://localhost:8080.
```cmd
cd gestor-biblioteca-web
npm install
ng serve
```
Acesse a aplicação web em http://localhost:4200.

## Funcionalidades

A API oferece as seguintes funcionalidades principais:

1. **Cadastro de Usuário** (`/api/v1/usuario/`)
2. **Cadastro de Livro** (`/api/v1/livro/`)
3. **Gerenciamento de Empréstimos** (`/api/v1/emprestimo/`)
4. **Consulta ao Google Books** (`/v1/google-books/`)

### Consulta ao Google Books

A API permite realizar buscas de livros por título diretamente na API do Google Books e adicionar o livro ao sistema.

## Endpoints

### 1. Usuários (`/api/v1/usuario/`)

| Método | Rota         | Descrição                        | Parâmetros               | Retorno                      |
|--------|--------------|----------------------------------|--------------------------|------------------------------|
| `GET`  | `/api/v1/usuario/`     | Retorna todos os usuários          | N/A                        | Lista de usuários             |
| `GET`  | `/api/v1/usuario/{id}` | Busca usuário por ID               | `id`                       | Detalhes do usuário           |
| `POST` | `/api/v1/usuario/`     | Cadastra um novo usuário           | Dados do usuário (JSON)    | Confirmação de cadastro       |
| `PUT`  | `/api/v1/usuario/{id}` | Atualiza usuário existente         | `id`, Dados do usuário (JSON) | Confirmação de atualização    |
| `DELETE` | `/api/v1/usuario/{id}` | Remove um usuário                  | `id`                       | Confirmação de exclusão       |

### 2. Livros (`/api/v1/livro/`)

| Método | Rota         | Descrição                        | Parâmetros               | Retorno                      |
|--------|--------------|----------------------------------|--------------------------|------------------------------|
| `GET`  | `/api/v1/livro/`     | Retorna todos os livros            | N/A                        | Lista de livros               |
| `GET`  | `/api/v1/livro/{id}` | Busca livro por ID                 | `id`                       | Detalhes do livro             |
| `POST` | `/api/v1/livro/`     | Cadastra um novo livro             | Dados do livro (JSON)      | Confirmação de cadastro       |
| `PUT`  | `/api/v1/livro/{id}` | Atualiza livro existente           | `id`, Dados do livro (JSON) | Confirmação de atualização    |
| `DELETE` | `/api/v1/livro/{id}` | Remove um livro                    | `id`                       | Confirmação de exclusão       |

### 3. Empréstimos (`/api/v1/emprestimo/`)

| Método | Rota                       | Descrição                                      | Parâmetros               | Retorno                      |
|--------|----------------------------|------------------------------------------------|--------------------------|------------------------------|
| `GET`  | `/api/v1/emprestimo/`       | Retorna todos os empréstimos                   | N/A                        | Lista de empréstimos          |
| `GET`  | `/api/v1/emprestimo/{id}`   | Busca empréstimo por ID                        | `id`                       | Detalhes do empréstimo        |
| `POST` | `/api/v1/emprestimo/`       | Realiza um novo empréstimo                     | Dados do empréstimo (JSON) | Confirmação de cadastro       |
| `PUT`  | `/api/v1/emprestimo/{id}`   | Atualiza um empréstimo existente               | `id`, Dados do empréstimo (JSON) | Confirmação de atualização    |
| `DELETE` | `/api/v1/emprestimo/{id}` | Remove um empréstimo                           | `id`                       | Confirmação de exclusão       |
| `GET`  | `/api/v1/emprestimo/recomendacao/{id}` | Retorna uma lista de livros recomendados para um usuário | `id` (usuário)             | Lista de livros recomendados  |

### 4. Google Books (`/v1/google-books/`)

| Método | Rota                | Descrição                                      | Parâmetros               | Retorno                      |
|--------|---------------------|------------------------------------------------|--------------------------|------------------------------|
| `GET`  | `/v1/google-books/search` | Realiza busca de livros no Google Books por título | `title` (String)           | Lista de livros               |
| `POST` | `/v1/google-books/add`    | Adiciona um livro encontrado na biblioteca   | Dados do livro (JSON)     | Confirmação de adição         |


## Implementação com JpaRepository e Classes Genéricas

O código foi projetado para otimizar o desenvolvimento através do uso de **JpaRepository** e **classes genéricas** tanto no **service** quanto no **controller**. Esse padrão permite uma implementação mais ágil dos métodos CRUD, eliminando a necessidade de duplicação de código.

Além disso, utilizamos **DTOs** (Data Transfer Objects) para estruturar as respostas dos endpoints de forma mais clara e concisa.

### Benefícios:
- **Manutenção Simplificada:** Com o uso de classes genéricas, a adição de novos endpoints e serviços é mais rápida e exige menos código.
- **Separação de Responsabilidades:** DTOs garantem que a camada de API apenas exponha os dados necessários, sem revelar diretamente as entidades do domínio.
- **Facilidade de Reutilização:** As implementações de CRUD são facilmente reutilizáveis para outras entidades, permitindo expansões futuras de forma mais simples.

