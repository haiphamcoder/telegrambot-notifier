package io.github.haiphamcoder.telegrambot.notifier.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.haiphamcoder.telegrambot.notifier.model.ChatId;
import io.github.haiphamcoder.telegrambot.notifier.model.ParseMode;
import io.github.haiphamcoder.telegrambot.notifier.model.ReplyMarkup;
import io.github.haiphamcoder.telegrambot.notifier.model.SuggestedPostParameters;

/**
 * Request payload for Telegram {@code sendMessage}.
 * <p>
 * Required: {@code chat_id}, {@code text}.
 * Optional (subset): {@code parse_mode}, {@code message_thread_id},
 * {@code direct_messages_topic_id}, {@code suggested_post_parameters},
 * {@code reply_markup}.
 * <p>
 * Note: Other optional fields like {@code entities},
 * {@code link_preview_options},
 * {@code disable_notification}, {@code protect_content}, and
 * {@code reply_parameters}
 * can be added later as needed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendMessageRequest(
        @JsonProperty("chat_id") ChatId chatId,
        @JsonProperty("text") String text,
        @JsonProperty("parse_mode") ParseMode parseMode,
        @JsonProperty("message_thread_id") Integer messageThreadId,
        @JsonProperty("direct_messages_topic_id") Integer directMessagesTopicId,
        @JsonProperty("suggested_post_parameters") SuggestedPostParameters suggestedPostParameters,
        @JsonProperty("reply_markup") ReplyMarkup replyMarkup) {

    /**
     * Builder for creating SendMessageRequest instances.
     * <p>
     * This builder provides a fluent API for constructing SendMessageRequest
     * with only the required fields and any optional fields that are needed.
     * <p>
     * Example usage:
     * <pre>{@code
     * SendMessageRequest request = SendMessageRequest.builder()
     *     .chatId(ChatId.of(123456789L))
     *     .text("Hello, world!")
     *     .parseMode(ParseMode.MARKDOWN_V2)
     *     .build();
     * }</pre>
     */
    public static class Builder {
        private ChatId chatId;
        private String text;
        private ParseMode parseMode;
        private Integer messageThreadId;
        private Integer directMessagesTopicId;
        private SuggestedPostParameters suggestedPostParameters;
        private ReplyMarkup replyMarkup;

        /**
         * Sets the chat ID (required).
         *
         * @param chatId the chat ID
         * @return this builder instance
         */
        public Builder chatId(ChatId chatId) {
            this.chatId = chatId;
            return this;
        }

        /**
         * Sets the chat ID using a numeric ID (required).
         *
         * @param chatId the numeric chat ID
         * @return this builder instance
         */
        public Builder chatId(long chatId) {
            this.chatId = ChatId.of(chatId);
            return this;
        }

        /**
         * Sets the chat ID using a username (required).
         *
         * @param username the username (with or without @)
         * @return this builder instance
         */
        public Builder chatId(String username) {
            this.chatId = ChatId.of(username);
            return this;
        }

        /**
         * Sets the message text (required).
         *
         * @param text the message text
         * @return this builder instance
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the parse mode for the message text.
         *
         * @param parseMode the parse mode (MARKDOWN, MARKDOWN_V2, or HTML)
         * @return this builder instance
         */
        public Builder parseMode(ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        /**
         * Sets the message thread ID for forum topics.
         *
         * @param messageThreadId the message thread ID
         * @return this builder instance
         */
        public Builder messageThreadId(Integer messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        /**
         * Sets the direct messages topic ID.
         *
         * @param directMessagesTopicId the direct messages topic ID
         * @return this builder instance
         */
        public Builder directMessagesTopicId(Integer directMessagesTopicId) {
            this.directMessagesTopicId = directMessagesTopicId;
            return this;
        }

        /**
         * Sets the suggested post parameters for channels.
         *
         * @param suggestedPostParameters the suggested post parameters
         * @return this builder instance
         */
        public Builder suggestedPostParameters(SuggestedPostParameters suggestedPostParameters) {
            this.suggestedPostParameters = suggestedPostParameters;
            return this;
        }

        /**
         * Sets the reply markup (inline keyboard, custom keyboard, etc.).
         *
         * @param replyMarkup the reply markup
         * @return this builder instance
         */
        public Builder replyMarkup(ReplyMarkup replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        /**
         * Builds the SendMessageRequest instance.
         * <p>
         * Validates that required fields are present.
         *
         * @return the constructed SendMessageRequest
         * @throws IllegalArgumentException if required fields are missing
         */
        public SendMessageRequest build() {
            if (chatId == null) {
                throw new IllegalArgumentException("chatId is required");
            }
            if (text == null || text.trim().isEmpty()) {
                throw new IllegalArgumentException("text is required and cannot be empty");
            }
            
            return new SendMessageRequest(
                chatId,
                text,
                parseMode,
                messageThreadId,
                directMessagesTopicId,
                suggestedPostParameters,
                replyMarkup
            );
        }
    }

    /**
     * Creates a new builder instance.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}
