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
# Copy example config
cp config.example.properties config.properties

# Edit config.properties with your values
# telegram.bot.token=your_actual_bot_token
# telegram.chat.id=your_actual_chat_id
```

### 4. Run
```bash
mvn compile exec:java -Dexec.mainClass="io.github.haiphamcoder.telegrambot.notifier.Main"
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

## Security Note

⚠️ **Never commit your bot token or chat ID to version control!**
- Use environment variables or config files
- Add `config.properties` to `.gitignore`
- Use `config.example.properties` as a template
