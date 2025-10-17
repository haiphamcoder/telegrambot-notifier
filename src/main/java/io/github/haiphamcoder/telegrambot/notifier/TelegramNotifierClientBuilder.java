package io.github.haiphamcoder.telegrambot.notifier;

import java.time.Duration;

public final class TelegramNotifierClientBuilder {

    private String botToken;
    private String baseUrl = "https://api.telegram.org";
    private Duration connectionTimeout = Duration.ofSeconds(10);
    private Duration responseTimeout = Duration.ofSeconds(30);

    public TelegramNotifierClientBuilder botToken(String botToken) {
        this.botToken = botToken;
        return this;
    }

    public TelegramNotifierClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public TelegramNotifierClientBuilder connectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public TelegramNotifierClientBuilder responseTimeout(Duration responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public TelegramNotifierClient build() {
        return new DefaultTelegramNotifierClient(botToken, baseUrl, connectionTimeout, responseTimeout);
    }

}
