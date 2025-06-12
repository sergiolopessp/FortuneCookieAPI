# Fortune Cookie API 🍪

Uma aplicação Spring Boot moderna que entrega frases de biscoito da sorte — tanto locais quanto geradas por IA (OpenAI) — com suporte a geração de imagens, toggles via FF4j, monitoramento com Prometheus e testes robustos com Testcontainers, JaCoCo e PITest.

## 🔧 Tecnologias Utilizadas

- **Java 24**
- **Spring Boot 3.4.2**
- **Spring Web, Spring Data JPA**
- **PostgreSQL**
- **Testcontainers**
- **JUnit 5**
- **Langchain4j + OpenAI**
- **FF4j (Feature Toggles)**
- **Micrometer + Prometheus**
- **JaCoCo** (cobertura)
- **PITest** (testes de mutação)

---

## 🚀 Como Executar

### Pré-requisitos

- Java 24
- Docker + Docker Compose
- [Kind (Kubernetes in Docker)](https://kind.sigs.k8s.io/)
- Variáveis de ambiente:
  ```env
  CHATGPT_APIKEY=<sua_chave_OpenAI>
  SPRING_DATASOURCE_URL=<url_do_banco>
  SPRING_DATASOURCE_USERNAME=<username_banco>
  SPRING_DATASOURCE_PASSWORD=<senha_banco>
  ```
### ▶️ Executar com Kind + Kubernetes (recomendado para testes locais)

1. **Crie o cluster local com Kind**:

```bash
kind create cluster --config infrastructure/kind-config.yaml
```

2. **Build da imagem da aplicação e carregamento no Kind**:

```bash
docker build -t fortunecookie-k8s-app:2.0.0 .
kind load docker-image fortunecookie-k8s-app:2.0.0
```

3. **Aplique os manifestos Kubernetes**:

```bash
kubectl apply -f infrastructure/postgres.yaml
kubectl apply -f infrastructure/java-app.yaml
```

4. **Verifique os pods**:

```bash
kubectl get pods
```

5. **Acesse a aplicação via navegador**:

A aplicação será exposta em:  
👉 http://localhost:8080/sorteiaFrase
👉 http://localhost:8080/v1/db/sorteiaFrase
---

### ❌ Parar e remover o cluster:

```bash
kind delete cluster
```

---
### Rodando localmente com Maven

```bash
./mvnw clean spring-boot:run
```

### Rodando com Docker Compose

```bash
docker-compose up --build
```

### Ver logs da aplicação

```bash
docker-compose logs -f app
```

---

## 📁 Estrutura da pasta `infrastructure/`

```
infrastructure/
├── kind-config.yaml     # Configuração do cluster Kind
├── postgres-deployment.yaml        # PVC + Service + Deployment do PostgreSQL
└── java-app-deployment.yaml        # Service + Deployment da aplicação Java
```

---
## 📡 Endpoints REST

### 🔮 Frases

| Método | Endpoint                  | Descrição                                        |
|--------|---------------------------|--------------------------------------------------|
| GET    | `/sorteiaFrase`           | Retorna frase local ou IA, dependendo do toggle |
| GET    | `/sorteiaFraseOpenAi`     | Sempre retorna uma frase gerada via OpenAI       |
| POST   | `/v1/db/frase`            | Salva uma nova frase no banco                    |
| GET    | `/v1/db/frase/{id}`       | Busca uma frase específica por ID                |
| GET    | `/v1/db/sorteiaFrase`     | Sorteia uma frase da base de dados               |

### 🎨 Geração de imagem

| Método | Endpoint         | Descrição                                         |
|--------|------------------|---------------------------------------------------|
| GET    | `/geraImagem`    | Retorna imagem gerada com base na `frase` enviada via query param (`?frase=...`) |

### 🎛️ Feature Toggles

| Método | Endpoint                  | Descrição                            |
|--------|---------------------------|--------------------------------------|
| GET    | `/ligar-ia/{ligarIA}`     | Ativa ou desativa uso de IA (true/false) |

### 🎲 Extras

| Método | Endpoint                  | Descrição                            |
|--------|---------------------------|--------------------------------------|
| GET    | `/sorteiaNumero/{numero}` | Retorna um número "da sorte" baseado na entrada |

---

## 📈 Métricas e Observabilidade

- Micrometer configurado com **Prometheus**
- Ative o Actuator em `application.properties`
- Acesse métricas: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

---

## 🧪 Testes e Cobertura

### Rodar testes unitários e integração:

```bash
./mvnw clean test
```

### Geração de relatório de cobertura com JaCoCo:

```bash
./mvnw jacoco:report
# Resultado: target/site/jacoco/index.html
```

### Análise de mutação com PITest:

```bash
./mvnw org.pitest:pitest-maven:mutationCoverage
```

---

## 🧠 Integração com OpenAI

- Geração de frases e imagens baseada em linguagem natural com uso de `Langchain4j`.
- O serviço `OpenAIService` encapsula chamadas para `enviaQueryModel()` e `enviaImagemModel()`.

---

## 🧰 FF4j – Feature Toggle

FF4j permite alternar entre geração de frase local e via IA:

- Toggle: `IA_FEATURE`
- Ative/desative com:
  ```
  GET /ligar-ia/true  # Usa IA
  GET /ligar-ia/false # Usa frases locais
  ```

---

## 🧑‍💻 Estrutura do Projeto

```
src/
├── controller/       # REST endpoints
├── service/          # Lógicas de negócio
├── data/             # JPA entities
├── dto/              # Objetos de transporte
├── configuration/    # Configurações do FF4j e outros beans
├── exceptions/       # Exceções customizadas
└── test/             # Testes com JUnit 5 e Testcontainers
```

---

## 📦 Compilar para Produção

```bash
./mvnw clean package
```

O artefato será gerado em: `target/fortunecookie-2.0.0.jar`

---

## 📄 Licença

Este projeto é apenas para fins educacionais. Para uso comercial, consulte o autor.

---

> Criado com ❤️ usando Spring Boot + OpenAI + Feature Toggles
