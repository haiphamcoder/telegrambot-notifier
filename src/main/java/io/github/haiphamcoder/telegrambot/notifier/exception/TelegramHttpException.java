package io.github.haiphamcoder.telegrambot.notifier.exception;

public class TelegramHttpException extends RuntimeException {

    private final int statusCode;
    private final String responseBody;

    public TelegramHttpException(int statusCode, String responseBody) {
        super("HTTP " + statusCode + " from Telegram API: " + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public TelegramHttpException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
        this.responseBody = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
