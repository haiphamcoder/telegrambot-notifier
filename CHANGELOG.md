# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Nothing yet

### Changed

- Nothing yet

### Deprecated

- Nothing yet

### Removed

- Nothing yet

### Fixed

- Nothing yet

### Security

- Nothing yet

## [1.0.0] - 2025-10-17

### Added

- Core models: `ChatId`, `ParseMode`, `InputFile` (sealed), `ReplyMarkup` variants (`InlineKeyboardMarkup`, `ReplyKeyboardMarkup`, `ReplyKeyboardRemove`, `ForceReply`)
- Responses: `MessageResponse`, `ErrorResponse`
- Types: `SendMessageRequest` with fluent Builder
- Escapers: `MarkdownEscaper`, `MarkdownV2Escaper`, `HtmlEscaper`, `ParseModeEscaper`
- HTTP: `TelegramRequestExecutor` built on Apache HttpClient5
- Client: `TelegramNotifierClient`, `DefaultTelegramNotifierClient`, `TelegramNotifierClientBuilder`
- JUnit test: env-driven `SendMessageTest` reading `TELEGRAM_BOT_TOKEN` and `TELEGRAM_CHAT_ID`
- CI: GitHub Actions workflows for CI and release

### Changed

- Auto-escape text inside `sendMessage` using entity-preserving strategy
- Refactor for lints: lower cognitive complexity, avoid deprecated HttpClient API, no counter mutation

### Fixed

- Avoid double-escaping for pre-escaped inputs in all escapers

### Security

- Removed hardcoded token and chat ID; switched to env vars
