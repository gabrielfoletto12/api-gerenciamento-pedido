# 🛒 Product Management API

API RESTful desenvolvida em Java com Spring Boot para gerenciamento de produtos (CRUD), com paginação, filtros, validação e documentação Swagger.

---

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot** (Spring Web + Validation)
* **Springdoc OpenAPI / Swagger UI**
* **Lombok**
* **Maven**

---

## ▶️ Como rodar

```bash
./mvnw spring-boot:run
```

* API: `http://localhost:8080/v1/produtos`
* Swagger UI: `http://localhost:8080/swagger-ui.html`

Os dados ficam em memória (3 produtos iniciais) e reiniciam a cada execução.

---

## 📌 Endpoints

| Verbo | Rota | Ação | Sucesso | Erros |
|-------|------|------|---------|-------|
| GET | `/v1/produtos` | Lista com filtros e paginação | 200 | 400 |
| GET | `/v1/produtos/{id}` | Busca por id | 200 | 400, 404 |
| POST | `/v1/produtos` | Cria produto | 201 (+ `Location`) | 400, 409 |
| PUT | `/v1/produtos/{id}` | Substitui o produto inteiro | 200 | 400, 404, 409 |
| PATCH | `/v1/produtos/{id}` | Atualiza só os campos enviados | 200 | 400, 404, 409 |
| DELETE | `/v1/produtos/{id}` | Remove (idempotente) | 204 | - |

### Filtros e paginação (GET /v1/produtos)

* `nome` - contém o texto (sem diferenciar maiúsculas)
* `precoMin` e `precoMax` - faixa de preço
* `page` - começa em 0 (padrão 0)
* `size` - de 1 a 100 (padrão 10)

Exemplo: `GET /v1/produtos?nome=note&precoMin=1000&page=0&size=5`

### Exemplos de corpo

POST / PUT:

```json
{ "nome": "Teclado", "preco": 250.00, "quantidade": 15 }
```

PATCH (só o que mudou):

```json
{ "quantidade": 20 }
```

### Formato de erro

```json
{ "message": "Produto com id 99 não encontrado", "status": 404 }
```

---

## 🏗️ Arquitetura

```text
src/main/java/br/com/gabriel/spring_boot_souzadev/
├── controller/     # Endpoints REST
├── service/        # Regras de negócio (lista em memória)
├── dto/            # DTOs, validações e resposta paginada
├── database/model/ # Entidade
├── exeption/       # Exceções de negócio (404, 409, 400)
└── handler/        # Tratamento global de erros
```
