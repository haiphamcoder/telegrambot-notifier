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
}
