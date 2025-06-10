# Documentação da API: Microsserviço book-service

## 1. Visão Geral e Execução

O `book-service` é um microsserviço responsável pelo cadastro e gerenciamento de informações de livros, incluindo título, autor e status de disponibilidade.

### **Para executar o serviço:**

1.  Navegue até o diretório raiz do projeto `book-service`.
2.  Execute o seguinte comando Maven:
    ```bash
    mvn spring-boot:run
    ```
3.  O serviço será executado em `http://localhost:8080`.

### **Acesso ao Banco de Dados H2:**

* **URL do Console**: `http://localhost:8080/h2-console`
* **JDBC URL**: `jdbc:h2:mem:bookdb`
* **Username**: `sa`
* **Password**: `password`

---

## 2. Modelo de Dados: `Book`

| Campo    | Tipo                     | Descrição                                         |
| :------- | :----------------------- | :-------------------------------------------------- |
| `id`     | `UUID`                   | Identificador único universal do livro (gerado automaticamente). |
| `titulo` | `String`                 | Título do livro.                                    |
| `autor`  | `String`                 | Autor do livro.                                     |
| `status` | `Enum` (`DISPONIVEL`, `RESERVADO`) | Status atual de disponibilidade do livro.           |

---

## 3. Endpoints da API

### **Listar todos os livros**
* **`GET /books`**
* **Descrição:** Retorna uma lista de todos os livros cadastrados no sistema.
* **Respostas de Sucesso:**
    * `200 OK`: Retorna um array de objetos `Book`.

### **Cadastrar novo livro**
* **`POST /books`**
* **Descrição:** Cria um novo registro de livro.
* **Corpo da Requisição:** `application/json`
    ```json
    {
      "titulo": "The Phoenix Project",
      "autor": "Gene Kim",
      "status": "DISPONIVEL"
    }
    ```
* **Respostas de Sucesso:**
    * `201 Created`: Retorna o objeto `Book` completo, incluindo o `id` (UUID) gerado.
* **Respostas de Erro:**
    * `400 Bad Request`: Se os dados de validação falharem (campos em branco ou status inválido).

### **Detalhar um livro**
* **`GET /books/{id}`**
* **Descrição:** Retorna os detalhes de um livro específico.
* **Parâmetros de URL:**
    * `id` (UUID): O ID do livro a ser detalhado.
* **Respostas de Sucesso:**
    * `200 OK`: Retorna o objeto `Book` correspondente.
* **Respostas de Erro:**
    * `404 Not Found`: Se nenhum livro for encontrado com o ID fornecido.

### **Atualizar informações do livro**
* **`PUT /books/{id}`**
* **Descrição:** Atualiza todas as informações de um livro existente.
* **Parâmetros de URL:**
    * `id` (UUID): O ID do livro a ser atualizado.
* **Corpo da Requisição:** `application/json`
    ```json
    {
      "titulo": "Novo Título",
      "autor": "Novo Autor",
      "status": "RESERVADO"
    }
    ```
* **Respostas de Sucesso:**
    * `200 OK`: Retorna o objeto `Book` atualizado.
* **Respostas de Erro:**
    * `404 Not Found`: Se nenhum livro for encontrado com o ID fornecido.

### **Atualizar status de disponibilidade (Endpoint Interno)**
* **`PATCH /books/{id}/status`**
* **Descrição:** Atualiza apenas o status de um livro. Este endpoint é protegido e deve ser chamado apenas por outros serviços internos.
* **Parâmetros de URL:**
    * `id` (UUID): O ID do livro a ser atualizado.
* **Cabeçalhos (Headers):**
    * `X-Internal-Request`: `reservation-service` (Obrigatório)
* **Corpo da Requisição:** `application/json`
    ```json
    {
      "status": "reservado"
    }
    ```
* **Respostas de Sucesso:**
    * `200 OK`: Retorna o objeto `Book` com o status atualizado.
* **Respostas de Erro:**
    * `403 Forbidden`: Se o cabeçalho `X-Internal-Request` estiver ausente ou for inválido.
    * `404 Not Found`: Se nenhum livro for encontrado com o ID fornecido.
    * `409 Conflict`: Se o livro já estiver no estado para o qual se está tentando alterar.
             
---

* **Alunos:**
    * Gabriel Alexandre Marassi Sitta, RA: 223020912
    * Kauan Henrique Bertalha, RA: 222620742
    * Matheus Toscano Rossini, RA: 222122622
    * João Antônio Rolo, RA: 220143992
    * Andre Luis Jacob Roman, RA: 220145512
    * Willyan Santos Tomaz e Silva, RA: 220141282
