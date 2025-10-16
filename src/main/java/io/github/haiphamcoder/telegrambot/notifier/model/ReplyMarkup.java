package io.github.haiphamcoder.telegrambot.notifier.model;

/**
 * Marker interface for Telegram {@code reply_markup} objects.
 * <p>
 * Supported variants:
 * <ul>
 * <li>{@link InlineKeyboardMarkup}</li>
 * <li>{@link ReplyKeyboardMarkup}</li>
 * <li>{@link ReplyKeyboardRemove}</li>
 * <li>{@link ForceReply}</li>
 * </ul>
 * Instances of this type can be attached to send/edit methods to show inline
 * keyboards, display or remove a custom reply keyboard, or force a user reply.
 *
 * @author Hai Pham Ngoc
 * @version 1.0.0
 * @since 1.0.0
 */
public sealed interface ReplyMarkup
                permits InlineKeyboardMarkup, ReplyKeyboardMarkup, ReplyKeyboardRemove, ForceReply {
}
