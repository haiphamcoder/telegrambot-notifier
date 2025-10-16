package io.github.haiphamcoder.telegrambot.notifier.http;

public final class MultipartBuilder {

    public record Part(String fieldName, String filename, byte[] bytes) {
    }

}
