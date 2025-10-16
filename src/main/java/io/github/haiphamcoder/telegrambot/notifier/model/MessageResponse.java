package io.github.haiphamcoder.telegrambot.notifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Maps the {@code result} object returned by Telegram when {@code ok=true} for
 * message-sending methods. This is a simplified subset of the full Message
 * object focused on common fields used by this library.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MessageResponse {
    @JsonProperty("message_id")
    private Integer messageId;

    private User from;
    private Chat chat;
    private Integer date;

    // Text/caption
    private String text;
    private String caption;

    // Entities for text/caption
    @JsonProperty("entities")
    private List<MessageEntity> entities;
    @JsonProperty("caption_entities")
    private List<MessageEntity> captionEntities;

    // Media group identifier (if applicable)
    @JsonProperty("media_group_id")
    private String mediaGroupId;

    @Override
    public String toString() {
        return "MessageResponse{id=" + messageId + ", chat=" + (chat != null ? chat.id : null)
                + ", text=" + text + "}";
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<MessageEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<MessageEntity> entities) {
        this.entities = entities;
    }

    public List<MessageEntity> getCaptionEntities() {
        return captionEntities;
    }

    public void setCaptionEntities(List<MessageEntity> captionEntities) {
        this.captionEntities = captionEntities;
    }

    public String getMediaGroupId() {
        return mediaGroupId;
    }

    public void setMediaGroupId(String mediaGroupId) {
        this.mediaGroupId = mediaGroupId;
    }

    // ====== Sub-DTOs (subset of Telegram types) ======

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class User {
        private Long id;
        @JsonProperty("is_bot")
        private Boolean isBot;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String username;
        @JsonProperty("language_code")
        private String languageCode;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Boolean getBot() { return isBot; }
        public void setBot(Boolean bot) { isBot = bot; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getLanguageCode() { return languageCode; }
        public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class Chat {
        private Long id;
        /** one of: "private", "group", "supergroup", "channel" */
        private String type;
        private String title;
        private String username;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class MessageEntity {
        /** e.g., "bold", "italic", "code", "text_link", ... */
        private String type;
        private Integer offset;
        private Integer length;
        /** present for text_link */
        private String url;
        /** present for pre/code */
        @JsonProperty("language")
        private String language;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Integer getOffset() { return offset; }
        public void setOffset(Integer offset) { this.offset = offset; }
        public Integer getLength() { return length; }
        public void setLength(Integer length) { this.length = length; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
    }
}
