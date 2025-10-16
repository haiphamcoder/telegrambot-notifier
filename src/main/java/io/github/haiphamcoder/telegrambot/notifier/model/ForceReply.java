package io.github.haiphamcoder.telegrambot.notifier.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Forces the user to reply to a message.
 * <p>
 * When shown, the client will display a reply interface focused on the input
 * field, encouraging the user to reply to the original message.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ForceReply implements ReplyMarkup {
    @JsonProperty("force_reply")
    private boolean forceReplyEnabled = true;
    private String inputFieldPlaceholder;
    private Boolean selective;

    public boolean isForceReply() {
        return forceReplyEnabled;
    }

    public void setForceReply(boolean forceReply) {
        this.forceReplyEnabled = forceReply;
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
}
