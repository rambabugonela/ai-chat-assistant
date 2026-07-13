# Enterprise AI Assistant

> 🚀 Production-inspired AI application built using **Spring AI**, **Google Gemini**, **PostgreSQL + PGVector**, and **Redis**.

---

# 🚀 Project Overview

Enterprise AI Assistant is a production-inspired AI application built using **Spring AI**, **Google Gemini**, **PostgreSQL + PGVector**, and **Redis**.

The project demonstrates how to build an enterprise-grade **Retrieval-Augmented Generation (RAG)** system capable of:

- Conversational AI
- Long-term Conversation Memory
- Semantic Document Search
- Intelligent Retrieval Routing
- Query Rewriting
- Dynamic Prompt Generation

Unlike a traditional chatbot, this application can decide whether a user question should be answered from:

- 🧠 Conversation Memory
- 📚 Enterprise Knowledge Base

This significantly reduces unnecessary vector searches and improves response quality.

---

# ✨ Features

## 🤖 AI Chat

- Google Gemini Integration
- Spring AI ChatModel
- Prompt Templates

---

## 📚 Retrieval-Augmented Generation (RAG)

- PDF Ingestion
- Chunking
- Embeddings
- PGVector
- Semantic Search

---

## 🧠 Memory

- Redis Conversation Store
- Session-based Conversations
- Conversation Transcript Generation

---

## 🎯 Intelligent Retrieval Routing

Supports two retrieval modes.

### DOCUMENTS

Questions answered from the enterprise knowledge base.

**Example**

```text
What is Spring AI?
```

---

### MEMORY_ONLY

Questions answered from conversation history.

**Example**

```text
What is my name?
```

---

## 🔄 Query Rewriting

Transforms follow-up questions into standalone questions.

### Example

**Conversation**

```text
User:
My name is Rambabu.
```

**Question**

```text
Who am I?
```

↓

**Rewritten**

```text
Who is the user mentioned in the conversation?
```

---

## 📝 Dynamic Prompt Builder

The application automatically selects the correct prompt.

```text
Document Question
        ↓
DOCUMENT_PROMPT
```

or

```text
Memory Question
        ↓
MEMORY_PROMPT
```

---

# 🏗 Architecture

```text
                    User
                      │
                      ▼
                REST Controller
                      │
                      ▼
             DefaultRagChatService
                      │
      ┌───────────────┼────────────────┐
      │               │                │
      ▼               ▼                ▼
Conversation      Query Rewriter   Retrieval Service
Store (Redis)                       (PGVector)
      │               │                │
      │               ▼                ▼
      │        Rewritten Query    Relevant Documents
      │               │                │
      └───────────────┼────────────────┘
                      ▼
               Prompt Builder
                      │
      ┌───────────────┴───────────────┐
      │                               │
MEMORY_PROMPT                 DOCUMENT_PROMPT
      │                               │
      └───────────────┬───────────────┘
                      ▼
               Google Gemini
                      │
                      ▼
                 Chat Response
                      │
                      ▼
          Save Conversation to Redis
```

---

# 📂 Project Structure

```text
src
├── controller
│      ChatController
│
├── dto
│      ChatRequest
│      ChatResponse
│
├── rag
│   ├── service
│   ├── config
│   └── model
│
├── memory
│      Conversation
│      Message
│
├── service
│      RedisConversationStore
│
├── config
│      RedisConfig
│
└── exception
```

---

# 🛠 Technologies Used

| Technology | Purpose |
|------------|----------|
| Java 21 | Programming Language |
| Spring Boot 3 | Application Framework |
| Spring AI | AI Integration |
| Google Gemini | Large Language Model |
| PostgreSQL | Document Storage |
| PGVector | Vector Database |
| Redis | Conversation Memory |
| Docker | Infrastructure |
| Maven | Build Tool |
| Lombok | Boilerplate Reduction |
| Jackson | JSON Serialization |

---

# 📋 Prerequisites

Install:

- Java 21
- Maven 3.9+
- Docker Desktop
- PostgreSQL
- Redis
- Google Gemini API Key

---

# 🚀 Local Setup

## Clone Repository

```bash
git clone https://github.com/<username>/enterprise-ai-assistant.git
```

---

## Start PostgreSQL

```bash
docker compose up postgres
```

---

## Start Redis

```bash
docker compose up redis
```

---

## Configure `application.yml`

```yaml
spring:
  datasource:
    url:
    username:
    password:

  ai:
    google:
      api-key:
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

# 🌐 REST API

## Chat Endpoint

```http
POST /api/chat
```

### Request

```json
{
  "sessionId": "user123",
  "message": "What is Spring AI?"
}
```

### Response

```json
{
  "response": "Spring AI is...",
  "sources": [
    "SpringAI.pdf"
  ]
}
```

---

## Memory Question

### Request

```json
{
  "sessionId": "user123",
  "message": "Who am I?"
}
```

### Response

```json
{
  "response": "Rambabu"
}
```

---

# 🔄 Conversation Flow

```text
User Question
      │
      ▼
Load Conversation
      │
      ▼
Query Rewrite
      │
      ▼
Determine Retrieval Mode
      │
      ├───────────────┐
      ▼               ▼
DOCUMENTS        MEMORY_ONLY
      │               │
Vector Search     Skip Search
      │               │
      └───────┬───────┘
              ▼
Prompt Builder
              ▼
Gemini
              ▼
Save Conversation
              ▼
Return Response
```

---

# 🎯 Design Principles

The application follows:

- SOLID Principles
- Dependency Injection
- Strategy Pattern
- Builder Pattern
- Repository Pattern
- Separation of Concerns

---

# ✅ Current Capabilities

- ✅ Chat with Gemini
- ✅ Redis Memory
- ✅ Semantic Search
- ✅ Query Rewriting
- ✅ Intelligent Retrieval Routing
- ✅ Dynamic Prompt Selection
- ✅ Session Management

---

# 🚀 Future Roadmap

## Version 1.2

- Conversation Summarization
- Memory Compression
- Token Optimization

---

## Version 1.3

- Spring AI Advisors
- Chat Observability
- Metrics
- Tracing

---

## Version 1.4

- Function Calling
- Tool Calling
- External APIs

---

## Version 1.5

- AI Agents
- Planner Agent
- Executor Agent

---

## Version 2.0

- Multi-Agent Enterprise AI Platform
- MCP Integration
- Knowledge Graph
- Hybrid Search
- Agent Memory
- Multi-Tenant Support

---

# 🎓 What This Project Demonstrates

This project showcases enterprise AI engineering practices, including:

- Retrieval-Augmented Generation (RAG)
- Conversational AI
- Vector Databases
- Prompt Engineering
- AI System Design
- Spring AI Integration
- Redis-backed Memory
- Production-oriented Software Architecture