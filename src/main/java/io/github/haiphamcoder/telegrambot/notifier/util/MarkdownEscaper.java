package io.github.haiphamcoder.telegrambot.notifier.util;

/**
 * Utility class for escaping special characters in legacy Markdown format.
 * <p>
 * According to Telegram Bot API documentation, the following characters must be escaped
 * with a preceding '\' character in legacy Markdown:
 * '_', '*', '`', '['
 * <p>
 * Note: This is the legacy mode, retained for backward compatibility.
 * For new bots, it's recommended to use MarkdownV2 instead.
 */
public final class MarkdownEscaper {

    private static final String[] SPECIAL_CHARS = {
        "_", "*", "`", "["
    };

    private MarkdownEscaper() {
        throw new UnsupportedOperationException("This is a utility class and should not be instantiated");
    }

    /**
     * Escapes special characters in the given text for legacy Markdown format.
     * <p>
     * This method escapes characters that have special meaning in legacy Markdown
     * by prefixing them with a backslash.
     *
     * @param text the text to escape
     * @return the escaped text safe for legacy Markdown parsing
     */
    public static String escape(String text) {
        if (text == null) {
            return null;
        }

        String result = text;
        
        // First escape backslashes to avoid double-escaping
        result = result.replace("\\", "\\\\");
        
        // Then escape all other special characters
        for (String specialChar : SPECIAL_CHARS) {
            result = result.replace(specialChar, "\\" + specialChar);
        }
        
        return result;
    }

    /**
     * Escapes special characters in the given text for legacy Markdown format,
     * but preserves existing markdown entities.
     * <p>
     * This is a more sophisticated escaping that tries to avoid escaping
     * characters that are part of valid markdown entities.
     *
     * @param text the text to escape
     * @return the escaped text safe for legacy Markdown parsing
     */
    public static String escapePreservingEntities(String text) {
        if (text == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < text.length()) {
            char current = text.charAt(index);

            // Case 1: already escaped special ("\\" + special)
            if (current == '\\' && index + 1 < text.length() && isSpecialChar(text.charAt(index + 1))) {
                result.append('\\').append(text.charAt(index + 1));
                index += 2;
            } else {
                // Case 2: existing entity
                EntityMatch match = findEntity(text, index);
                if (match != null) {
                    result.append(match.content);
                    index = match.nextIndex;
                } else {
                    // Case 3: escape special or append normal char
                    if (isSpecialChar(current)) {
                        result.append('\\').append(current);
                    } else {
                        result.append(current);
                    }
                    index++;
                }
            }
        }

        return result.toString();
    }

    private static class EntityMatch {
        final String content;
        final int nextIndex;

        EntityMatch(String content, int nextIndex) {
            this.content = content;
            this.nextIndex = nextIndex;
        }
    }

    private static EntityMatch findEntity(String text, int start) {
        char c = text.charAt(start);
        return switch (c) {
            case '*' -> isBoldEntity(text, start)
                    ? new EntityMatch(extractBoldEntity(text, start), skipBoldEntity(text, start))
                    : null;
            case '_' -> isItalicEntity(text, start)
                    ? new EntityMatch(extractItalicEntity(text, start), skipItalicEntity(text, start))
                    : null;
            case '`' -> isCodeEntity(text, start)
                    ? new EntityMatch(extractCodeEntity(text, start), skipCodeEntity(text, start))
                    : null;
            case '[' -> isLinkEntity(text, start)
                    ? new EntityMatch(extractLinkEntity(text, start), skipLinkEntity(text, start))
                    : null;
            default -> null;
        };
    }
    
    private static boolean isBoldEntity(String text, int start) {
        if (start + 1 >= text.length() || text.charAt(start) != '*') return false;
        int end = text.indexOf("*", start + 1);
        return end > start + 1;
    }
    
    private static String extractBoldEntity(String text, int start) {
        int end = text.indexOf("*", start + 1);
        return text.substring(start, end + 1);
    }
    
    private static int skipBoldEntity(String text, int start) {
        int end = text.indexOf("*", start + 1);
        return end + 1;
    }
    
    private static boolean isItalicEntity(String text, int start) {
        if (start + 1 >= text.length() || text.charAt(start) != '_') return false;
        int end = text.indexOf("_", start + 1);
        return end > start + 1;
    }
    
    private static String extractItalicEntity(String text, int start) {
        int end = text.indexOf("_", start + 1);
        return text.substring(start, end + 1);
    }
    
    private static int skipItalicEntity(String text, int start) {
        int end = text.indexOf("_", start + 1);
        return end + 1;
    }
    
    private static boolean isCodeEntity(String text, int start) {
        if (start + 1 >= text.length() || text.charAt(start) != '`') return false;
        int end = text.indexOf("`", start + 1);
        return end > start + 1;
    }
    
    private static String extractCodeEntity(String text, int start) {
        int end = text.indexOf("`", start + 1);
        return text.substring(start, end + 1);
    }
    
    private static int skipCodeEntity(String text, int start) {
        int end = text.indexOf("`", start + 1);
        return end + 1;
    }
    
    private static boolean isLinkEntity(String text, int start) {
        if (start + 1 >= text.length() || text.charAt(start) != '[') return false;
        int endBracket = text.indexOf("]", start + 1);
        if (endBracket == -1) return false;
        int startParen = text.indexOf("(", endBracket);
        int endParen = text.indexOf(")", startParen);
        return startParen > endBracket && endParen > startParen;
    }
    
    private static String extractLinkEntity(String text, int start) {
        int endParen = text.indexOf(")", start);
        while (endParen != -1 && text.charAt(endParen - 1) != ')') {
            endParen = text.indexOf(")", endParen + 1);
        }
        return text.substring(start, endParen + 1);
    }
    
    private static int skipLinkEntity(String text, int start) {
        int endParen = text.indexOf(")", start);
        while (endParen != -1 && text.charAt(endParen - 1) != ')') {
            endParen = text.indexOf(")", endParen + 1);
        }
        return endParen + 1;
    }
    
    private static boolean isSpecialChar(char c) {
        return c == '_' || c == '*' || c == '`' || c == '[';
    }
}
