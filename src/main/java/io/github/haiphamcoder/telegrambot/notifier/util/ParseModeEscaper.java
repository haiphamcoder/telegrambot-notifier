package io.github.haiphamcoder.telegrambot.notifier.util;

import io.github.haiphamcoder.telegrambot.notifier.model.ParseMode;

/**
 * Unified utility class for escaping text based on Telegram parse modes.
 * <p>
 * This class provides a single entry point for escaping text according to
 * the specific requirements of each Telegram parse mode:
 * <ul>
 * <li>{@link ParseMode#MARKDOWN} - Legacy Markdown (limited escaping)</li>
 * <li>{@link ParseMode#MARKDOWN_V2} - MarkdownV2 (comprehensive escaping)</li>
 * <li>{@link ParseMode#HTML} - HTML (entity escaping)</li>
 * </ul>
 */
public final class ParseModeEscaper {

    private ParseModeEscaper() {
        throw new UnsupportedOperationException("This is a utility class and should not be instantiated");
    }

    /**
     * Escapes text according to the specified parse mode.
     * <p>
     * This method automatically selects the appropriate escaping strategy
     * based on the parse mode provided.
     *
     * @param text      the text to escape
     * @param parseMode the parse mode to use for escaping
     * @return the escaped text safe for the specified parse mode
     */
    public static String escape(String text, ParseMode parseMode) {
        if (text == null || parseMode == null) {
            return text;
        }

        return switch (parseMode) {
            case MARKDOWN -> MarkdownEscaper.escape(text);
            case MARKDOWN_V2 -> MarkdownV2Escaper.escape(text);
            case HTML -> HtmlEscaper.escape(text);
        };
    }

    /**
     * Escapes text according to the specified parse mode, preserving existing
     * entities.
     * <p>
     * This method uses more sophisticated escaping that tries to preserve
     * existing markup entities while still escaping special characters.
     *
     * @param text      the text to escape
     * @param parseMode the parse mode to use for escaping
     * @return the escaped text safe for the specified parse mode
     */
    public static String escapePreservingEntities(String text, ParseMode parseMode) {
        if (text == null || parseMode == null) {
            return text;
        }

        return switch (parseMode) {
            case MARKDOWN -> MarkdownEscaper.escapePreservingEntities(text);
            case MARKDOWN_V2 -> MarkdownV2Escaper.escapePreservingEntities(text);
            case HTML -> HtmlEscaper.escapePreservingTags(text);
        };
    }

    /**
     * Checks if the given text needs escaping for the specified parse mode.
     * <p>
     * This method can be used to determine whether escaping is necessary
     * before actually performing the escape operation.
     *
     * @param text      the text to check
     * @param parseMode the parse mode to check against
     * @return true if the text contains characters that need escaping
     */
    public static boolean needsEscaping(String text, ParseMode parseMode) {
        if (text == null || parseMode == null) {
            return false;
        }

        return switch (parseMode) {
            case MARKDOWN -> containsMarkdownSpecialChars(text);
            case MARKDOWN_V2 -> containsMarkdownV2SpecialChars(text);
            case HTML -> containsHtmlSpecialChars(text);
        };
    }

    private static boolean containsMarkdownSpecialChars(String text) {
        return text.contains("_") || text.contains("*") || text.contains("`") || text.contains("[");
    }

    private static boolean containsMarkdownV2SpecialChars(String text) {
        return text.contains("_") || text.contains("*") || text.contains("[") || text.contains("]") ||
                text.contains("(") || text.contains(")") || text.contains("~") || text.contains("`") ||
                text.contains(">") || text.contains("#") || text.contains("+") || text.contains("-") ||
                text.contains("=") || text.contains("|") || text.contains("{") || text.contains("}") ||
                text.contains(".") || text.contains("!");
    }

    private static boolean containsHtmlSpecialChars(String text) {
        return text.contains("<") || text.contains(">") || text.contains("&");
    }
}
