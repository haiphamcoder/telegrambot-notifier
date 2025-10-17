package io.github.haiphamcoder.telegrambot.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramApiException;
import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramHttpException;
import io.github.haiphamcoder.telegrambot.notifier.model.ChatId;
import io.github.haiphamcoder.telegrambot.notifier.model.MessageResponse;
import io.github.haiphamcoder.telegrambot.notifier.model.ParseMode;
import io.github.haiphamcoder.telegrambot.notifier.types.SendMessageRequest;

public class Main {

    private static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    private static final long CHAT_ID = Long.parseLong(System.getenv("TELEGRAM_CHAT_ID", "0"));

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        TelegramNotifierClient client = new TelegramNotifierClientBuilder()
                .botToken(BOT_TOKEN)
                .build();

        SendMessageRequest request = SendMessageRequest.builder()
                .chatId(CHAT_ID)
                .text("Hello, world!")
                .parseMode(ParseMode.MARKDOWN_V2)
                .build();
        try {
            MessageResponse response = client.sendMessage(request);
            System.out.println("Response: " + response);
        } catch (TelegramApiException e) {
            System.err.println("Telegram API error: " + e.getMessage());
        } catch (TelegramHttpException e) {
            System.err.println("HTTP error: " + e.getMessage());
        }
    }

}
