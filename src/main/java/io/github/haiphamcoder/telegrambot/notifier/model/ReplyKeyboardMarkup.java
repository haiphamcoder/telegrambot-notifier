package io.github.haiphamcoder.telegrambot.notifier.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Custom reply keyboard to be shown to the user.
 * <p>
 * Displays a keyboard with predefined buttons as a replacement for the
 * system keyboard. The keyboard can be resized, made persistent, or shown
 * only once.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ReplyKeyboardMarkup implements ReplyMarkup {
    /** The button layout: a list of rows, each a list of buttons. */
    @JsonProperty("keyboard")
    private List<List<KeyboardButton>> keyboard;
    @JsonProperty("is_persistent")
    private Boolean isPersistent;
    @JsonProperty("resize_keyboard")
    private Boolean resizeKeyboard;
    @JsonProperty("one_time_keyboard")
    private Boolean oneTimeKeyboard;
    /** Placeholder to be shown in the input field when the keyboard is active. */
    private String inputFieldPlaceholder;
    /** Show the keyboard to specific users only. */
    private Boolean selective;

    public ReplyKeyboardMarkup() {
    }

    public ReplyKeyboardMarkup(List<List<KeyboardButton>> keyboard) {
        this.keyboard = keyboard;
    }

    public List<List<KeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<KeyboardButton>> keyboard) {
        this.keyboard = keyboard;
    }

    public Boolean getPersistent() {
        return isPersistent;
    }

    public void setPersistent(Boolean persistent) {
        isPersistent = persistent;
    }

    public Boolean getResizeKeyboard() {
        return resizeKeyboard;
    }

    public void setResizeKeyboard(Boolean resizeKeyboard) {
        this.resizeKeyboard = resizeKeyboard;
    }

    public Boolean getOneTimeKeyboard() {
        return oneTimeKeyboard;
    }

    public void setOneTimeKeyboard(Boolean oneTimeKeyboard) {
        this.oneTimeKeyboard = oneTimeKeyboard;
    }

    public String getInputFieldPlaceholder() {
        return inputFieldPlaceholder;
    }

    public void setInputFieldPlaceholder(String inputFieldPlaceholder) {
        this.inputFieldPlaceholder = inputFieldPlaceholder;
    }

    public Boolean getSelective() {
        return selective;
    }

    public void setSelective(Boolean selective) {
        this.selective = selective;
    }

    /** Button model for {@link ReplyKeyboardMarkup}. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class KeyboardButton {
        /** Label of the button. */
        private String text;
        @JsonProperty("request_contact")
        private Boolean requestContact;
        @JsonProperty("request_location")
        private Boolean requestLocation;

        public KeyboardButton() {
        }

        public KeyboardButton(String text) {
            this.text = text;
        }

        public static KeyboardButton of(String text) {
            return new KeyboardButton(text);
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Boolean getRequestContact() {
            return requestContact;
        }

        public void setRequestContact(Boolean requestContact) {
            this.requestContact = requestContact;
        }

        public Boolean getRequestLocation() {
            return requestLocation;
        }

        public void setRequestLocation(Boolean requestLocation) {
            this.requestLocation = requestLocation;
        }
    }
}
