package io.github.haiphamcoder.telegrambot.notifier.util;

/**
 * Utility class for escaping special characters in HTML format.
 * <p>
 * According to Telegram Bot API documentation, the following characters must be escaped
 * in HTML format:
 * <ul>
 *   <li>&lt; (less than) → &amp;lt;</li>
 *   <li>&gt; (greater than) → &amp;gt;</li>
 *   <li>&amp; (ampersand) → &amp;amp;</li>
 * </ul>
 * <p>
 * Note: Only the tags mentioned in the Bot API documentation are supported.
 * All other HTML tags will be ignored.
 */
public final class HtmlEscaper {

    private HtmlEscaper() {
        throw new UnsupportedOperationException("This is a utility class and should not be instantiated");
    }

    /**
     * Escapes special characters in the given text for HTML format.
     * <p>
     * This method escapes characters that have special meaning in HTML
     * by replacing them with their corresponding HTML entities.
     *
     * @param text the text to escape
     * @return the escaped text safe for HTML parsing
     */
    public static String escape(String text) {
        if (text == null) {
            return null;
        }

        String result = text;
        
        // Escape in order: & first, then < and >
        result = result.replace("&", "&amp;");
        result = result.replace("<", "&lt;");
        result = result.replace(">", "&gt;");
        
        return result;
    }

    /**
     * Escapes special characters in the given text for HTML format,
     * but preserves existing HTML tags.
     * <p>
     * This method only escapes characters that are not part of valid HTML tags.
     * It's more sophisticated than simple escaping and tries to preserve
     * legitimate HTML markup.
     *
     * @param text the text to escape
     * @return the escaped text safe for HTML parsing
     */
    public static String escapePreservingTags(String text) {
        if (text == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        int i = 0;
        
        while (i < text.length()) {
            char c = text.charAt(i);
            
            if (c == '&' && isHtmlEntity(text, i)) {
                int entityEnd = findEntityEnd(text, i);
                result.append(text, i, entityEnd);
                i = entityEnd;
                continue;
            }

            if (c == '<' && isHtmlTag(text, i)) {
                // Preserve HTML tag
                int tagEnd = findTagEnd(text, i);
                result.append(text.substring(i, tagEnd));
                i = tagEnd;
            } else if (c == '<' || c == '>' || c == '&') {
                // Escape special character
                result.append(escapeChar(c));
                i++;
            } else {
                result.append(c);
                i++;
            }
        }
        
        return result.toString();
    }

    /**
     * Escapes only the content within HTML tags, leaving the tags themselves intact.
     * <p>
     * This is useful when you have HTML markup that you want to preserve
     * but need to escape the text content.
     *
     * @param text the text with HTML tags to escape
     * @return the text with escaped content but preserved tags
     */
    public static String escapeContentOnly(String text) {
        if (text == null) {
            return null;
        }

        // Use a different approach: find all tags and escape content between them
        StringBuilder result = new StringBuilder();
        int lastTagEnd = 0;
        int i = 0;
        
        while (i < text.length()) {
            if (text.charAt(i) == '<' && isHtmlTag(text, i)) {
                // Escape content before this tag
                if (i > lastTagEnd) {
                    result.append(escape(text.substring(lastTagEnd, i)));
                }
                
                // Preserve the tag
                int tagEnd = findTagEnd(text, i);
                result.append(text.substring(i, tagEnd));
                lastTagEnd = tagEnd;
                i = tagEnd;
            } else {
                i++;
            }
        }
        
        // Escape any remaining content after the last tag
        if (lastTagEnd < text.length()) {
            result.append(escape(text.substring(lastTagEnd)));
        }
        
        return result.toString();
    }
    
    private static boolean isHtmlTag(String text, int start) {
        if (start + 1 >= text.length() || text.charAt(start) != '<') return false;
        int end = text.indexOf('>', start);
        if (end == -1) return false;
        
        // Check if it looks like a valid HTML tag
        String tagContent = text.substring(start + 1, end);
        return tagContent.matches("^/?[a-zA-Z][a-zA-Z0-9]*\\s*.*$");
    }
    
    private static int findTagEnd(String text, int start) {
        int end = text.indexOf('>', start);
        return end == -1 ? text.length() : end + 1;
    }
    
    private static boolean isHtmlEntity(String text, int start) {
        if (start + 1 >= text.length() || text.charAt(start) != '&') return false;
        int end = text.indexOf(';', start);
        if (end == -1) return false;
        
        String entity = text.substring(start + 1, end);
        // Check for named entities or numeric entities
        return entity.matches("^[a-zA-Z]+$") || entity.matches("^#\\d+$") || entity.matches("^#x[0-9a-fA-F]+$");
    }
    
    private static int findEntityEnd(String text, int start) {
        int end = text.indexOf(';', start);
        return end == -1 ? text.length() : end + 1;
    }
    
    private static String escapeChar(char c) {
        return switch (c) {
            case '<' -> "&lt;";
            case '>' -> "&gt;";
            case '&' -> "&amp;";
            default -> String.valueOf(c);
        };
    }
}
