# Telegram Bot Notifier

A Java library for sending notifications via Telegram Bot API.

## Quick Start

### 1. Get Bot Token

1. Message [@BotFather](https://t.me/BotFather) on Telegram
2. Create a new bot with `/newbot`
3. Copy the bot token

### 2. Get Chat ID

1. Add your bot to a group/channel or start a private chat
2. Send a message to the bot
3. Visit: `https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates`
4. Find your chat ID in the response

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
    .botToken("your_bot_token")
    .build();

SendMessageRequest request = SendMessageRequest.builder()
    .chatId(123456789L)
    .text("Hello, world!")
    .parseMode(ParseMode.MARKDOWN_V2)
    .build();

MessageResponse response = client.sendMessage(request);
```
