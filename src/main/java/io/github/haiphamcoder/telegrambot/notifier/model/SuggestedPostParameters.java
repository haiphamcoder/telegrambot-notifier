package io.github.haiphamcoder.telegrambot.notifier.model;

/**
 * Parameters describing a suggested post, as introduced in Telegram Bot API
 * v9.2.
 * <p>
 * Both fields are optional; omit a field if you do not want to constrain it.
 * <ul>
 * <li>{@code price}: Proposed price for the post (in Stars).</li>
 * <li>{@code sendDate}: Proposed time to send the post, expressed as Unix time
 * (UTC seconds). Telegram requires this to be within a future window (e.g.,
 * at least 300 seconds and up to 30 days ahead).</li>
 * </ul>
 * Validation of the time window should be performed by higher-level logic.
 *
 * @since 1.0.0
 */
public record SuggestedPostParameters(
    SuggestedPostPrice price,
    Long sendDate) {
}
