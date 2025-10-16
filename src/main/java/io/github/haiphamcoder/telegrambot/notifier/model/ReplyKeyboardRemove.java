package io.github.haiphamcoder.telegrambot.notifier.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to remove the custom reply keyboard.
 * <p>
 * Setting {@code remove_keyboard} to true hides the current custom keyboard
 * and restores the default system keyboard.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ReplyKeyboardRemove implements ReplyMarkup {
    @JsonProperty("remove_keyboard")
    private boolean removeKeyboard = true;
    private Boolean selective;

    public boolean isRemoveKeyboard() {
        return removeKeyboard;
    }

    public void setRemoveKeyboard(boolean removeKeyboard) {
        this.removeKeyboard = removeKeyboard;
    }

    public Boolean getSelective() {
        return selective;
    }

    public void setSelective(Boolean selective) {
        this.selective = selective;
    }
}
