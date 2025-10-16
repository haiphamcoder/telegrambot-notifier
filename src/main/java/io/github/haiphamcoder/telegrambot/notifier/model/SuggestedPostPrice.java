package io.github.haiphamcoder.telegrambot.notifier.model;

/**
 * Describes the price of a suggested post (Telegram Bot API v9.2).
 * <p>
 * currency: must be one of "XTR" (Telegram Stars) or "TON" (toncoins).
 * amount: the amount in the smallest units (Stars or nanotoncoins).
 * <p>
 * Current constraints from the Bot API:
 * <ul>
 * <li>For XTR (Stars): 5 to 100,000</li>
 * <li>For TON (nanotoncoins): 10,000,000 to 10,000,000,000,000</li>
 * </ul>
 * Validation is expected to be enforced by higher-level logic.
 *
 * @param currency currency code, "XTR" or "TON"
 * @param amount   amount in smallest units of the specified currency
 * 
 * @author Hai Pham Ngoc
 * @version 1.0.0
 * @since 1.0.0
 */
public record SuggestedPostPrice(String currency, long amount) {
}
