package io.github.haiphamcoder.telegrambot.notifier;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;

import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramApiException;
import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramHttpException;
import io.github.haiphamcoder.telegrambot.notifier.http.TelegramRequestExecutor;
import io.github.haiphamcoder.telegrambot.notifier.model.MessageResponse;
import io.github.haiphamcoder.telegrambot.notifier.model.ParseMode;
import io.github.haiphamcoder.telegrambot.notifier.types.SendMessageRequest;
import io.github.haiphamcoder.telegrambot.notifier.util.ParseModeEscaper;

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
            // ignored
        }
    }

    @Override
    public MessageResponse sendMessage(SendMessageRequest request) throws TelegramApiException, TelegramHttpException {
        Map<String, Object> params = new HashMap<>();
        params.put("chat_id", request.chatId());
        
        // Auto-escape text based on parse mode
        String text = request.text();
        ParseMode parseMode = request.parseMode();
        if (parseMode != null && ParseModeEscaper.needsEscaping(text, parseMode)) {
            text = ParseModeEscaper.escape(text, parseMode);
        }
        params.put("text", text);
        
        if (parseMode != null) {
            params.put("parse_mode", parseMode);
        }
        if (request.messageThreadId() != null) {
            params.put("message_thread_id", request.messageThreadId());
        }
        if (request.directMessagesTopicId() != null) {
            params.put("direct_messages_topic_id", request.directMessagesTopicId());
        }
        if (request.suggestedPostParameters() != null) {
            params.put("suggested_post_parameters", request.suggestedPostParameters());
        }
        if (request.replyMarkup() != null) {
            params.put("reply_markup", request.replyMarkup());
        }
        return TelegramRequestExecutor.postJson(httpClient, baseUrl + "sendMessage", params, MessageResponse.class);
    }

}
