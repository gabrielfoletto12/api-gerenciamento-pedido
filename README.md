# 🛒 Product Management API

Uma API RESTful desenvolvida em Java com Spring Boot para gerenciamento de catálogo de produtos, controle de estoque e precificação.

---

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot** (Spring Web)
* **Lombok** (Injeção de código boilerplate / Pattern Builder)
* **Maven** (Gerenciador de dependências)

---

## 📌 Funcionalidades

- [x] **Listar produtos:** Consulta o catálogo completo de produtos cadastrados.
- [x] **Cadastrar produto:** Adiciona novos produtos com cálculo automático de identificador dinâmico.
- [x] **Buscar por ID:** 
- [x] **Atualizar produto:** 
- [x] **Remover produto:** 

---

## 🏗️ Arquitetura e Padrões de Projeto

O projeto foi estruturado seguindo os princípios de arquitetura em camadas no Spring Boot:

```text
src/main/java/com/api/produto/
├── controller/     # Camada de exposição dos endpoints REST
├── service/        # Regras de negócio e manipulação de dados
├── dto/            # Objetos de transferência de dados (Data Transfer Objects)
└── model/          # Entidades do domínio