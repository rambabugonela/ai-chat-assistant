# 🚀 Enterprise AI Assistant Platform

> Production-ready Enterprise AI Platform built using **Spring AI**, **Google Gemini**, **PostgreSQL + PGVector**, **Redis**, **Docker**, **Spring Security**, **Prometheus**, and **Grafana**.

---

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5-green)
![Spring AI](https://img.shields.io/badge/Spring-AI-success)
![Gemini](https://img.shields.io/badge/Google-Gemini-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-PGVector-blue)
![Redis](https://img.shields.io/badge/Redis-ConversationMemory-red)
![JWT](https://img.shields.io/badge/Security-JWT-orange)
![Grafana](https://img.shields.io/badge/Monitoring-Grafana-orange)
![Prometheus](https://img.shields.io/badge/Monitoring-Prometheus-E6522C)

---

# 📖 Project Overview

Enterprise AI Assistant Platform is a production-inspired AI platform demonstrating how to build secure, scalable, enterprise-grade AI applications using **Spring AI**.

The platform combines:

- Conversational AI
- Enterprise RAG
- Semantic Search
- Intelligent Routing
- Redis Conversation Memory
- JWT Authentication
- Role-Based Authorization
- Docker Deployment
- Observability
- Production Profiles

The project is designed following enterprise software engineering practices and serves as a reference implementation for modern AI applications.

---

# ✨ Enterprise Features

## 🤖 AI Chat

- Google Gemini Integration
- Spring AI ChatModel
- Prompt Templates
- Dynamic Prompt Builder

---

## 🧠 Intelligent Routing

Supports multiple AI processing strategies.

- Conversation Memory
- Enterprise Knowledge Base

The platform automatically determines whether a query should be answered from:

- Redis Conversation Memory
- Enterprise Documents

---

## 📚 Retrieval-Augmented Generation (RAG)

- PDF Ingestion
- Document Chunking
- Vector Embeddings
- PGVector
- Semantic Search
- Query Rewriting

---

## 💾 Conversation Memory

- Redis-backed Conversation Store
- Session-based Memory
- Conversation Transcript
- Long Conversation Support

---

## 🔐 Enterprise Security

- JWT Authentication
- Stateless Security
- Spring Security
- Role-Based Authorization

Supported Roles

- USER
- ADMIN

---

## 🐳 Production Deployment

- Docker
- Docker Compose
- Spring Profiles
- Environment Variables
- Production Configuration

---

## 📊 Monitoring

- Spring Boot Actuator
- Prometheus
- Grafana
- JVM Metrics
- HTTP Metrics
- Health Monitoring

---

## 🔌 MCP Foundation

Project contains the foundation required for future Model Context Protocol (MCP) integration.

---

# 🏗 Enterprise Architecture

```text
                              User
                                │
                                ▼
                       Spring Boot REST API
                                │
          ┌─────────────────────┼─────────────────────┐
          │                     │                     │
          ▼                     ▼                     ▼
     JWT Security         Chat Controller       Actuator
          │                     │
          ▼                     ▼
                    Enterprise Chat Service
                              │
       ┌──────────────────────┼───────────────────────┐
       │                      │                       │
       ▼                      ▼                       ▼
Conversation Memory     Intelligent Routing      Query Rewriter
     (Redis)                                        │
       │                                            ▼
       │                                   Enterprise RAG
       │                                      (PGVector)
       │                                            │
       └──────────────────────────┬─────────────────┘
                                  ▼
                           Prompt Builder
                                  ▼
                           Google Gemini
                                  ▼
                           AI Chat Response
