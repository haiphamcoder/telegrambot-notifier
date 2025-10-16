package io.github.haiphamcoder.telegrambot.notifier;

import java.time.Duration;

public final class TelegramNotifierClientBuilder {

    private String botToken;
    private String baseUrl = "https://api.telegram.org";
    private Duration connectionTimeout = Duration.ofSeconds(10);
    private Duration responseTimeout = Duration.ofSeconds(30);

}
