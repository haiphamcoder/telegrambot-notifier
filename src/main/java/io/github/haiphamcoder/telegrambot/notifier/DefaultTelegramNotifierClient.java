package io.github.haiphamcoder.telegrambot.notifier;

import java.io.IOException;
import java.time.Duration;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;

import io.github.haiphamcoder.telegrambot.notifier.model.MessageResponse;
import io.github.haiphamcoder.telegrambot.notifier.types.SendMessageRequest;

public final class DefaultTelegramNotifierClient implements TelegramNotifierClient {

    private final CloseableHttpClient httpClient;
    private final String baseUrl;

    public DefaultTelegramNotifierClient(String botToken, String baseUrl, Duration connectionTimeout,
            Duration responseTimeout) {

        this.baseUrl = baseUrl.endsWith("/") ? baseUrl + "bot" + botToken + "/" : baseUrl + "/bot" + botToken + "/";

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.of(connectionTimeout))
                .build();

        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setResponseTimeout(Timeout.of(responseTimeout))
                .build();

        this.httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    @Override
    public void close() {
        try {
            httpClient.close();
        } catch (IOException ignored) {

        }
    }

    @Override
    public MessageResponse sendMessage(SendMessageRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
    }

}
