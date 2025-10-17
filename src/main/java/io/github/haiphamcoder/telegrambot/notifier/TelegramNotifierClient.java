package io.github.haiphamcoder.telegrambot.notifier;

import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramApiException;
import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramHttpException;
import io.github.haiphamcoder.telegrambot.notifier.model.MessageResponse;
import io.github.haiphamcoder.telegrambot.notifier.types.SendMessageRequest;

public interface TelegramNotifierClient extends AutoCloseable {

    MessageResponse sendMessage(SendMessageRequest request) throws TelegramApiException, TelegramHttpException;

}