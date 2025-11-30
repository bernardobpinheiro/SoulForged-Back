# Soulforged Backend - FULL

Backend multi-módulo em Java Spring Boot 3.3.2, com três microsserviços funcionais:

- user-collection-service (porta 8081)
- catalog-deck-service (porta 8082)
- duel-service (porta 8083)

Todos usam H2 em memória para facilitar testes.

## Como rodar

Na raiz do projeto:

```bash
mvn clean install
```

Depois, em três terminais:

```bash
cd user-collection-service
mvn spring-boot:run
```

```bash
cd catalog-deck-service
mvn spring-boot:run
```

```bash
cd duel-service
mvn spring-boot:run
```

## Fluxos básicos

### 1) Registro e login

**Registro**

POST http://localhost:8081/auth/register

```json
{
  "username": "player1",
  "email": "player1@example.com",
  "password": "123456"
}
```

**Login**

POST http://localhost:8081/auth/login

```json
{
  "username": "player1",
  "password": "123456"
}
```

Resposta:

```json
{
  "token": "JWT_AQUI"
}
```

Use o token com:

`Authorization: Bearer JWT_AQUI`

### 2) Ver dados do jogador logado

GET http://localhost:8081/users/me  
Header: `Authorization: Bearer <token>`

### 3) Ver coleção do jogador

GET http://localhost:8081/users/{id}/collection

### 4) Adicionar carta na coleção

POST http://localhost:8081/collections/{userId}/cards

```json
{
  "cardTemplateCode": "FIRE_DRAGON",
  "quantity": 1
}
```

### 5) Criar cartas no catálogo

POST http://localhost:8082/cards

```json
{
  "code": "FIRE_DRAGON",
  "name": "Fire Dragon",
  "description": "Ancient dragon of flames",
  "type": "HERO",
  "rarity": "EPIC",
  "manaCost": 7,
  "attack": 10,
  "health": 8
}
```

### 6) Criar deck

POST http://localhost:8082/decks

```json
{
  "ownerUserId": 1,
  "name": "My First Deck",
  "cards": [
    { "cardTemplateCode": "FIRE_DRAGON", "quantity": 2 }
  ]
}
```

### 7) Iniciar duelo

POST http://localhost:8083/duels/start?playerOneId=1&playerTwoId=2

### 8) Jogar turno

POST http://localhost:8083/duels/{id}/turn?action=Attack

### 9) WebSocket

Conectar em: `ws://localhost:8083/ws/duel`  
Mensagem enviada volta como echo: `Echo from duel-service: <sua mensagem>`.
