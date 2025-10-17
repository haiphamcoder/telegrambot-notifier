package io.github.haiphamcoder.telegrambot.notifier.util;

/**
 * Utility class for escaping special characters in MarkdownV2 format.
 * <p>
 * According to Telegram Bot API documentation, the following characters must be escaped
 * with a preceding '\' character in MarkdownV2:
 * '_', '*', '[', ']', '(', ')', '~', '`', '>', '#', '+', '-', '=', '|', '{', '}', '.', '!'
 * <p>
 * Additionally, the '\' character itself must be escaped as '\\'.
 */
public final class MarkdownV2Escaper {

    private static final String[] SPECIAL_CHARS = {
        "_", "*", "[", "]", "(", ")", "~", "`", ">", "#", "+", "-", "=", "|", "{", "}", ".", "!"
    };

    private MarkdownV2Escaper() {
        throw new UnsupportedOperationException("This is a utility class and should not be instantiated");
    }

    /**
     * Escapes special characters in the given text for MarkdownV2 format.
     * <p>
     * This method escapes all characters that have special meaning in MarkdownV2
     * by prefixing them with a backslash.
     *
     * @param text the text to escape
     * @return the escaped text safe for MarkdownV2 parsing
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
     * Escapes special characters in the given text for MarkdownV2 format,
     * but preserves existing markdown entities.
     * <p>
     * This is a more sophisticated escaping that tries to avoid escaping
     * characters that are part of valid markdown entities.
     *
     * @param text the text to escape
     * @return the escaped text safe for MarkdownV2 parsing
     */
    public static String escapePreservingEntities(String text) {
        if (text == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        int i = 0;
        
        while (i < text.length()) {
            EntityMatch match = findEntity(text, i);
            if (match != null) {
                result.append(match.content);
                i = match.nextIndex;
            } else {
                char c = text.charAt(i);
                if (isSpecialChar(c)) {
                    result.append('\\').append(c);
                } else {
                    result.append(c);
                }
                i++;
            }
        }
        
        return result.toString();
    }
    
    private static EntityMatch findEntity(String text, int start) {
        char c = text.charAt(start);
        
        return switch (c) {
            case '*' -> isBoldEntity(text, start) ? 
                new EntityMatch(extractBoldEntity(text, start), skipBoldEntity(text, start)) : null;
            case '_' -> {
                if (isItalicEntity(text, start)) {
                    yield new EntityMatch(extractItalicEntity(text, start), skipItalicEntity(text, start));
                }
                if (isUnderlineEntity(text, start)) {
                    yield new EntityMatch(extractUnderlineEntity(text, start), skipUnderlineEntity(text, start));
                }
                yield null;
            }
            case '~' -> isStrikethroughEntity(text, start) ? 
                new EntityMatch(extractStrikethroughEntity(text, start), skipStrikethroughEntity(text, start)) : null;
            case '|' -> isSpoilerEntity(text, start) ? 
                new EntityMatch(extractSpoilerEntity(text, start), skipSpoilerEntity(text, start)) : null;
            case '`' -> isCodeEntity(text, start) ? 
                new EntityMatch(extractCodeEntity(text, start), skipCodeEntity(text, start)) : null;
            case '[' -> isLinkEntity(text, start) ? 
                new EntityMatch(extractLinkEntity(text, start), skipLinkEntity(text, start)) : null;
            case '>' -> isBlockquoteEntity(text, start) ? 
                new EntityMatch(extractBlockquoteEntity(text, start), skipBlockquoteEntity(text, start)) : null;
            default -> null;
        };
    }
    
    private static class EntityMatch {
        final String content;
        final int nextIndex;
        
        EntityMatch(String content, int nextIndex) {
            this.content = content;
            this.nextIndex = nextIndex;
        }
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
    
    private static boolean isUnderlineEntity(String text, int start) {
        if (start + 3 >= text.length()) return false;
        return text.substring(start, start + 2).equals("__") && 
               text.indexOf("__", start + 2) > start + 2;
    }
    
    private static String extractUnderlineEntity(String text, int start) {
        int end = text.indexOf("__", start + 2);
        return text.substring(start, end + 2);
    }
    
    private static int skipUnderlineEntity(String text, int start) {
        int end = text.indexOf("__", start + 2);
        return end + 2;
    }
    
    private static boolean isStrikethroughEntity(String text, int start) {
        if (start + 1 >= text.length() || text.charAt(start) != '~') return false;
        int end = text.indexOf("~", start + 1);
        return end > start + 1;
    }
    
    private static String extractStrikethroughEntity(String text, int start) {
        int end = text.indexOf("~", start + 1);
        return text.substring(start, end + 1);
    }
    
    private static int skipStrikethroughEntity(String text, int start) {
        int end = text.indexOf("~", start + 1);
        return end + 1;
    }
    
    private static boolean isSpoilerEntity(String text, int start) {
        if (start + 3 >= text.length()) return false;
        return text.substring(start, start + 2).equals("||") && 
               text.indexOf("||", start + 2) > start + 2;
    }
    
    private static String extractSpoilerEntity(String text, int start) {
        int end = text.indexOf("||", start + 2);
        return text.substring(start, end + 2);
    }
    
    private static int skipSpoilerEntity(String text, int start) {
        int end = text.indexOf("||", start + 2);
        return end + 2;
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
    
    private static boolean isBlockquoteEntity(String text, int start) {
        return start + 1 < text.length() && text.charAt(start) == '>';
    }
    
    private static String extractBlockquoteEntity(String text, int start) {
        int end = start + 1;
        while (end < text.length() && text.charAt(end) == '>') {
            end++;
        }
        return text.substring(start, end);
    }
    
    private static int skipBlockquoteEntity(String text, int start) {
        int end = start + 1;
        while (end < text.length() && text.charAt(end) == '>') {
            end++;
        }
        return end;
    }
    
    private static boolean isSpecialChar(char c) {
        return c == '_' || c == '*' || c == '[' || c == ']' || c == '(' || c == ')' ||
               c == '~' || c == '`' || c == '>' || c == '#' || c == '+' || c == '-' ||
               c == '=' || c == '|' || c == '{' || c == '}' || c == '.' || c == '!';
    }
}
