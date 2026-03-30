# Agro API

Backend de contratação de crédito desenvolvido em Java com Spring Boot como parte do desafio técnico Sicredi.

## Sobre

A API contempla a primeira etapa do processo de aquisição de crédito: a contratação. O associado escolhe um produto e o crédito é registrado na base de dados. A API valida as regras de negócio, consulta o serviço de produtos e persiste o título de crédito.

## Requisitos

- Java 21
- Docker e Docker Compose

## Como rodar

Sobe os serviços de infraestrutura:

```bash
docker-compose up -d
```

Roda a aplicação:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8002`.

Documentação Swagger: `http://localhost:8002/swagger-ui.html`

## Variáveis de ambiente

| Variável        | Padrão                                          |
|-----------------|-------------------------------------------------|
| `DB_URL`        | `jdbc:postgresql://localhost:5432/sicredi_db`   |
| `DB_USERNAME`   | `sicredi`                                       |
| `DB_PASSWORD`   | `sicredi123`                                    |
| `REDIS_HOST`    | `localhost`                                     |
| `REDIS_PORT`    | `6379`                                          |
| `REDIS_PASSWORD`| `redis123`                                      |

## Endpoints

### POST /operacoes-credito
Contrata uma operação de crédito.

**Request:**
```json
{
  "idAssociado": 1,
  "valorOperacao": 5000,
  "segmento": "PF",
  "codigoProdutoCredito": "101A",
  "codigoConta": "1234567890",
  "areaBeneficiadaHa": null
}
```

**Response 201:**
```json
{
  "idOperacaoCredito": "uuid-gerado"
}
```

| Status | Situação |
|--------|----------|
| 201 | Operação contratada com sucesso |
| 409 | Operação duplicada |
| 422 | Produto não permite contratação / AGRO sem área beneficiada |
| 503 | Serviço de produtos indisponível |

---

### GET /operacoes-credito/{idOperacaoCredito}
Consulta uma operação de crédito pelo identificador.

**Response 200:**
```json
{
  "idOperacaoCredito": "uuid",
  "idAssociado": 1,
  "valorOperacao": 5000,
  "segmento": "PF",
  "codigoProdutoCredito": "101A",
  "codigoConta": "1234567890",
  "areaBeneficiadaHa": null,
  "dataHoraContratacao": "2024-01-15T10:30:00"
}
```

| Status | Situação |
|--------|----------|
| 200 | Operação encontrada |
| 404 | Operação não encontrada |

## Regras de negócio

- Operações do segmento **AGRO** exigem `areaBeneficiadaHa` preenchido e maior que zero
- Operações do segmento **PJ** geram um registro adicional na tabela `socio_beneficiario`
- A contratação só é permitida se o serviço de produtos confirmar que o produto aceita o segmento e valor informados
- Idempotência garantida por janela de 5 minutos: mesma combinação de associado, produto, conta e valor dentro da janela retorna conflito

## Arquitetura

O projeto segue arquitetura hexagonal (Ports & Adapters). O core de domínio não tem dependência de nenhum framework — Spring, JPA e Redis ficam na camada de adapters.

```
core/
  domain/        — entidades e regras de negócio
  ports/in/      — contratos dos casos de uso
  ports/out/     — contratos das dependências externas
  usecase/       — implementação dos casos de uso

adapters/
  in/            — controllers, mappers, filtros
  out/           — persistência, redis, feign, logging
  configs/       — beans, swagger
```

## Resiliência

A comunicação com o serviço de produtos usa Resilience4j com:
- **Retry**: 3 tentativas com backoff exponencial de 500ms
- **Circuit Breaker**: abre com 50% de falha em janela de 10 chamadas
- **Timeout**: 2 segundos por chamada
