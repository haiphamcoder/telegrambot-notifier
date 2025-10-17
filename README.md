# Telegram Bot Notifier

[![Maven Central](https://img.shields.io/maven-central/v/io.github.haiphamcoder/telegrambot-notifier)](https://search.maven.org/artifact/io.github.haiphamcoder/telegrambot-notifier)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Build Status](https://github.com/haiphamcoder/telegrambot-notifier/workflows/CI/badge.svg)](https://github.com/haiphamcoder/telegrambot-notifier/actions)

A lightweight Java library to send messages via the Telegram Bot API, built on Apache HttpClient 5. It provides a clean, fluent API, automatic entity-safe escaping for Markdown/MarkdownV2/HTML, and robust HTTP handling.

## Features

- 🚀 **Fluent API**: Builder pattern for `SendMessageRequest`
- 🛡️ **Safe Escaping**: Auto escape text per `ParseMode` (Markdown, MarkdownV2, HTML) while preserving entities
- 📦 **Clean Models**: Core models (`ChatId`, `ReplyMarkup`, etc.) with Jackson mappings
- 🌐 **HTTP 5**: Apache HttpClient 5 with JSON/form/multipart helpers
- ⚠️ **Error Handling**: Custom exceptions (`TelegramHttpException`, `TelegramApiException`)
- 🧪 **Env-based Test**: Optional JUnit test driven by env vars

## Installation

### Maven

```xml
<dependency>
  <groupId>io.github.haiphamcoder</groupId>
  <artifactId>telegrambot-notifier</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation "io.github.haiphamcoder:telegrambot-notifier:1.0.0"
```

## Quick Start

### 1. Get Bot Token

1. Message [@BotFather](https://t.me/BotFather) on Telegram
2. Create a new bot with `/newbot`
3. Copy the bot token

### 2. Get Chat ID

1. Add your bot to a group/channel or start a private chat
2. Send a message to the bot
3. Visit: `https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates`
4. Find your chat ID (can be negative) or use `@username`

### 3. Setup Environment

```bash
export TELEGRAM_BOT_TOKEN="your_actual_bot_token"
export TELEGRAM_CHAT_ID="your_actual_chat_id_or_@username"
```

### 4. Run tests (optional)

```bash
mvn -q -Dtest=SendMessageTest test
```

## Usage Example

```java
TelegramNotifierClient client = new TelegramNotifierClientBuilder()
    .botToken(System.getenv("TELEGRAM_BOT_TOKEN"))
    .build();

SendMessageRequest request = SendMessageRequest.builder()
    .chatId(System.getenv("TELEGRAM_CHAT_ID"))
    .text("Hello, world!")
    .parseMode(ParseMode.MARKDOWN_V2)
    .build();

MessageResponse response = client.sendMessage(request);
```
