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
        int i = 0;
        
        while (i < text.length()) {
            char c = text.charAt(i);
            
            // Check for markdown entities
            if (c == '*' && isBoldEntity(text, i)) {
                // Preserve bold entity
                result.append(extractBoldEntity(text, i));
                i = skipBoldEntity(text, i);
            } else if (c == '_' && isItalicEntity(text, i)) {
                // Preserve italic entity
                result.append(extractItalicEntity(text, i));
                i = skipItalicEntity(text, i);
            } else if (c == '`' && isCodeEntity(text, i)) {
                // Preserve code entity
                result.append(extractCodeEntity(text, i));
                i = skipCodeEntity(text, i);
            } else if (c == '[' && isLinkEntity(text, i)) {
                // Preserve link entity
                result.append(extractLinkEntity(text, i));
                i = skipLinkEntity(text, i);
            } else if (isSpecialChar(c)) {
                // Escape special character
                result.append('\\').append(c);
                i++;
            } else {
                result.append(c);
                i++;
            }
        }
        
        return result.toString();
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
