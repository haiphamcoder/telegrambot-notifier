package io.github.haiphamcoder.telegrambot.notifier.exception;

import io.github.haiphamcoder.telegrambot.notifier.model.ErrorResponse;

public class TelegramApiException extends RuntimeException {

    private final int errorCode;
    private final String description;
    private final ErrorResponse errorResponse;

    public TelegramApiException(int errorCode, String description) {
        super("Telegram API error " + errorCode + ": " + description);
        this.errorCode = errorCode;
        this.description = description;
        this.errorResponse = null;
    }

    public TelegramApiException(ErrorResponse response) {
        super("Telegram API error " + (response.getErrorCode() != null ? response.getErrorCode() : "?")
                + ": " + response.getDescription());
        this.errorCode = response.getErrorCode() != null ? response.getErrorCode() : -1;
        this.description = response.getDescription();
        this.errorResponse = response;
    }

    public TelegramApiException(String rawBody) {
        super("Telegram API error response: " + rawBody);
        this.errorCode = -1;
        this.description = rawBody;
        this.errorResponse = null;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

}
