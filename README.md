<<<<<<< HEAD
# parking-estapar
=======
# 🚗 Estapar Parking System

Sistema de gerenciamento de estacionamento inteligente, desenvolvido para o teste técnico de Desenvolvedor Java/Kotlin da Estapar. A aplicação simula entrada e saída de veículos, controle de vagas e cálculo de faturamento por setor.

---

## 📋 Descrição

Este projeto implementa um sistema backend que consome dados de um simulador de garagem (via container Docker), processa eventos em tempo real (entrada e saída) e aplica regras de negócio como preços dinâmicos, controle de lotação e cálculo de cobrança com carência.

---

## 🧠 Requisitos Atendidos

- [x] Integração com simulador via `/garage`
- [x] Processamento de webhooks para entrada e saída de veículos
- [x] Cálculo de preço com carência e valores pró-rata
- [x] Preço dinâmico baseado em ocupação
- [x] Encerramento de setor quando 100% ocupado
- [x] Armazenamento de configuração da garagem em banco relacional
- [x] API REST documentada com OpenAPI

---

## 🧱 Arquitetura

O sistema adota princípios da **Clean Architecture**:
```css
src/
├── domain/
│   ├── entity/
│   └── repository/
├── application/
│   └── usecases/
├── adapters/
│   ├── controllers/
│   ├── presenters/
│   └── gateways/
├── infrastructure/
│   └── persistence/
└── main/
└── EstaparParkingApplication.java
```

---

## ⚙️ Tecnologias

- Java 17 + Spring Boot
- PostgreSQL
- JPA / Hibernate
- Docker & Docker Compose
- Flyway (versionamento de banco)
- Testes com JUnit e Mockito
- OpenAPI (Swagger UI)

---

## 🚦 Regras de Negócio

### 🎫 Cobrança
- 15 minutos de carência
- 1ª hora cheia, após isso cobrança a cada 15 min
- Pagamento na saída

### 📈 Preço Dinâmico (na entrada)
- < 25% lotado: **-10%**
- 25–50%: **0%**
- 50–75%: **+10%**
- 75–100%: **+25%**

### 🚫 Lotação
- Com 100% ocupado, o setor é fechado até uma saída liberar espaço.

---

## 🔐 Considerações de Segurança

- Validação de dados com `@Valid`
- Controle de concorrência com `@Transactional` e pessimistic locking
- Prevenção de race conditions em entradas simultâneas
- Endpoints REST preparados para autenticação com JWT (como melhoria futura)
- Resiliência a duplicações (idempotência de eventos)

---

## ⚖️ Escalabilidade e Performance

- Lógica desacoplada por camadas (facilita migração para microsserviços)
- Eventos de entrada/saída preparados para filas (RabbitMQ/Kafka)
- Cache para dados estáticos (ex: config de garagem)
- Banco indexado e transações atômicas

---

## 🧪 Testes

- Testes unitários para lógica de cobrança e regras dinâmicas
- Testes de integração com banco usando Testcontainers
- Simulação de concorrência e race conditions

---

## ▶️ Como Executar

### Requisitos

- Docker + Docker Compose
- Java 17
- PostgreSQL

### Passos

1. **Inicie o simulador de garagem:**
   ```bash
   docker run -d --network="host" cfontes0estapar/garage-sim:1.0.0
2. **Inicie a aplicação:**
   ```bash
   ./gradlew bootRun
3. **Acesse a documentação:**
   ```bash
   http://localhost:8080/swagger-ui.html

## 📦 Melhorias Futuras
- Autenticação com JWT
- Monitoramento com Grafana + Prometheus
- Painel administrativo com metrics e logs

👨‍💻 Autor
Desenvolvido por Marcelo Toyoda como parte do processo seletivo da Estapar 🚘.
>>>>>>> 9799aacb00acc66a3e83de7f3f4e92a12027e2b7
