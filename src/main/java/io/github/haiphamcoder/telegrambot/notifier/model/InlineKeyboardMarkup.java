package io.github.haiphamcoder.telegrambot.notifier.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Inline keyboard markup for messages.
 * <p>
 * Attach an array of button rows to a message. Buttons can open URLs, send
 * callback data, switch inline query, or initiate payments.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class InlineKeyboardMarkup implements ReplyMarkup {
    /** The button layout: a list of rows, each a list of buttons. */
    @JsonProperty("inline_keyboard")
    private List<List<InlineKeyboardButton>> inlineKeyboard;

    public InlineKeyboardMarkup() {
    }

    public InlineKeyboardMarkup(List<List<InlineKeyboardButton>> inlineKeyboard) {
        this.inlineKeyboard = inlineKeyboard;
    }

    public List<List<InlineKeyboardButton>> getInlineKeyboard() {
        return inlineKeyboard;
    }

    public void setInlineKeyboard(List<List<InlineKeyboardButton>> inlineKeyboard) {
        this.inlineKeyboard = inlineKeyboard;
    }

    /** Button model for {@link InlineKeyboardMarkup}. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class InlineKeyboardButton {
        /** Label of the button. */
        private String text;
        @JsonProperty("url")
        private String url;
        @JsonProperty("callback_data")
        private String callbackData;
        @JsonProperty("switch_inline_query")
        private String switchInlineQuery;
        @JsonProperty("switch_inline_query_current_chat")
        private String switchInlineQueryCurrentChat;
        @JsonProperty("pay")
        private Boolean pay;

        public InlineKeyboardButton() {
        }

        public InlineKeyboardButton(String text) {
            this.text = text;
        }

        public static InlineKeyboardButton ofText(String text) {
            return new InlineKeyboardButton(text);
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCallbackData() {
            return callbackData;
        }

        public void setCallbackData(String callbackData) {
            this.callbackData = callbackData;
        }

        public String getSwitchInlineQuery() {
            return switchInlineQuery;
        }

        public void setSwitchInlineQuery(String switchInlineQuery) {
            this.switchInlineQuery = switchInlineQuery;
        }

        public String getSwitchInlineQueryCurrentChat() {
            return switchInlineQueryCurrentChat;
        }

        public void setSwitchInlineQueryCurrentChat(String switchInlineQueryCurrentChat) {
            this.switchInlineQueryCurrentChat = switchInlineQueryCurrentChat;
        }

        public Boolean getPay() {
            return pay;
        }

        public void setPay(Boolean pay) {
            this.pay = pay;
        }
    }
}
