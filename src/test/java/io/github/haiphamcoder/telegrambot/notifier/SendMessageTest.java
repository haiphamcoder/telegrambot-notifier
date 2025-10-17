package io.github.haiphamcoder.telegrambot.notifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramApiException;
import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramHttpException;
import io.github.haiphamcoder.telegrambot.notifier.model.MessageResponse;
import io.github.haiphamcoder.telegrambot.notifier.model.ParseMode;
import io.github.haiphamcoder.telegrambot.notifier.types.SendMessageRequest;

/**
 * Integration-like test that sends a real message if TELEGRAM_BOT_TOKEN and
 * TELEGRAM_CHAT_ID are present in the environment. Skips otherwise.
 */
@DisplayName("sendMessage integration when env present")
class SendMessageTest {

    private static final String TELEGRAM_BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    private static final String TELEGRAM_CHAT_ID_RAW = System.getenv("TELEGRAM_CHAT_ID");

    @Test
    @DisplayName("should send message using TELEGRAM_* env vars")
    void sendMessage_whenEnvPresent() throws TelegramApiException, TelegramHttpException {
        assumeTrue(TELEGRAM_BOT_TOKEN != null && !TELEGRAM_BOT_TOKEN.isBlank(), "TELEGRAM_BOT_TOKEN not set; skipping");
        assumeTrue(TELEGRAM_CHAT_ID_RAW != null && !TELEGRAM_CHAT_ID_RAW.isBlank(),
                "TELEGRAM_CHAT_ID not set; skipping");

        try (TelegramNotifierClient client = new TelegramNotifierClientBuilder()
                .botToken(TELEGRAM_BOT_TOKEN)
                .build()) {
            SendMessageRequest.Builder builder = SendMessageRequest.builder();
            if (isNumeric(TELEGRAM_CHAT_ID_RAW)) {
                builder.chatId(Long.parseLong(TELEGRAM_CHAT_ID_RAW));
            } else {
                builder.chatId(TELEGRAM_CHAT_ID_RAW);
            }
            SendMessageRequest request = builder
                    .text("Hello from test!")
                    .parseMode(ParseMode.MARKDOWN_V2)
                    .build();

            MessageResponse response = client.sendMessage(request);
            assertNotNull(response);
        }
    }

    private static boolean isNumeric(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (i == 0 && (c == '-' || c == '+'))
                continue;
            if (c < '0' || c > '9')
                return false;
        }
        return !value.isEmpty() && !(value.length() == 1 && (value.charAt(0) == '-' || value.charAt(0) == '+'));
    }
}
