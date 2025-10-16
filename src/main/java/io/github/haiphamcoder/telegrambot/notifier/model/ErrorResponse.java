package io.github.haiphamcoder.telegrambot.notifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error payload returned by Telegram when {@code ok=false}.
 * Includes optional {@code parameters} with guidance like retry-after and
 * migrate-to-chat-id.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ErrorResponse {
    private boolean ok;
    @JsonProperty("error_code")
    private Integer errorCode;
    @JsonProperty("description")
    private String description;
    /** Optional error parameters providing recovery hints. */
    @JsonProperty("parameters")
    private ResponseParameters parameters;

    @Override
    public String toString() {
        return "ErrorResponse{ok=" + ok + ", code=" + errorCode + ", desc='" + description + "'}";
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResponseParameters getParameters() {
        return parameters;
    }

    public void setParameters(ResponseParameters parameters) {
        this.parameters = parameters;
    }

    /** Sub-object documented by Telegram for certain error conditions. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class ResponseParameters {
        /**
         * The group has been migrated to a supergroup with the specified identifier.
         * This number may be negative.
         */
        @JsonProperty("migrate_to_chat_id")
        private Long migrateToChatId;
        /**
         * In case of exceeding flood control, the number of seconds left to wait
         * before the request can be repeated.
         */
        @JsonProperty("retry_after")
        private Integer retryAfter;

        public Long getMigrateToChatId() {
            return migrateToChatId;
        }

        public void setMigrateToChatId(Long migrateToChatId) {
            this.migrateToChatId = migrateToChatId;
        }

        public Integer getRetryAfter() {
            return retryAfter;
        }

        public void setRetryAfter(Integer retryAfter) {
            this.retryAfter = retryAfter;
        }
    }
}
