package io.github.haiphamcoder.telegrambot.notifier.model;

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
    MARKDOWN,
    /** The recommended Markdown V2 syntax with full entity coverage. */
    MARKDOWN_V2,
    /** HTML tags subset supported by Telegram. */
    HTML
}
