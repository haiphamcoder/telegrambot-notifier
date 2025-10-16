package io.github.haiphamcoder.telegrambot.notifier.model;

/**
 * Represents the {@code chat_id} parameter used by the Telegram Bot API.
 * <p>
 * According to the official specification, {@code chat_id} can be either:
 * <ul>
 * <li>a numeric unique identifier of the target chat (may be negative for
 * groups and supergroups), or</li>
 * <li>a public username of the target channel/chat in the form
 * {@code @channelusername}.</li>
 * </ul>
 * This sealed interface models both variants so that requests can express the
 * exact type of identifier they carry.
 * <p>
 * Serialization note: when serialized to JSON for Bot API requests, a numeric
 * ID must be emitted as a JSON number, while a username must be emitted as a
 * JSON string including the leading {@code @}. This project provides utilities
 * to handle serialization consistently.
 * 
 * @author Hai Pham Ngoc
 * @version 1.0.0
 * @since 1.0.0
 */
public sealed interface ChatId permits ChatId.LongId, ChatId.Username {
    /**
     * Numeric unique identifier of the target chat.
     * <p>
     * Telegram uses negative values for groups and supergroups; callers should
     * pass the value as provided by Telegram without modification.
     */
    record LongId(long value) implements ChatId {
    }

    /**
     * Public username of the target chat/channel in the form {@code @username}.
     * <p>
     * The value should include the leading {@code @} to comply with the Bot API.
     */
    record Username(String value) implements ChatId {
    }

    /**
     * Creates a {@link ChatId} from a numeric unique identifier.
     *
     * @param id the chat identifier; may be negative for groups/supergroups
     * @return a {@link LongId} wrapper for the given numeric identifier
     */
    static ChatId of(long id) {
        return new LongId(id);
    }

    /**
     * Creates a {@link ChatId} from a public username.
     * <p>
     * The {@code username} should be provided in the form {@code @username}.
     *
     * @param username the public chat/channel username including the leading
     *                 {@code @}
     * @return a {@link Username} wrapper for the given username
     */
    static ChatId of(String username) {
        return new Username(username);
    }

}
