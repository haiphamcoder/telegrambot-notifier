package io.github.haiphamcoder.telegrambot.notifier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

/**
 * Text parsing mode for message {@code text} and media {@code caption} fields
 * as defined by the Telegram Bot API.
 * <p>
 * The API supports legacy Markdown, MarkdownV2, and HTML. Telegram recommends
 * using {@link #MARKDOWN_V2} or {@link #HTML}. When a parse mode is set, the
 * Bot API will parse entities accordingly; otherwise, entities may be provided
 * explicitly via {@code message_entities} / {@code caption_entities}.
 *
 * @author Hai Pham Ngoc
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ParseMode {
    /** Legacy Markdown syntax. Not recommended for new bots. */
    MARKDOWN("Markdown"),
    /** The recommended Markdown V2 syntax with full entity coverage. */
    MARKDOWN_V2("MarkdownV2"),
    /** HTML tags subset supported by Telegram. */
    HTML("HTML");

    private final String value;

    private ParseMode(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Creates a {@link ParseMode} from a string value.
     *
     * @param value the string value of the parse mode
     * @return the {@link ParseMode}
     * @throws IllegalArgumentException if the value is invalid
     */
    @JsonCreator
    public static ParseMode fromValue(String value) {
        return Arrays.stream(ParseMode.values())
                .filter(mode -> mode.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid parse mode: " + value));
    }
}
