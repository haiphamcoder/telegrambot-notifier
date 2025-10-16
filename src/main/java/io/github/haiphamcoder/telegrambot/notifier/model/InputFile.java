package io.github.haiphamcoder.telegrambot.notifier.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an input file value accepted by the Telegram Bot API.
 * <p>
 * An input file can be provided in three forms:
 * <ul>
 * <li>{@link #fileId(String)} — existing file on Telegram servers (by
 * {@code file_id})</li>
 * <li>{@link #url(String)} — an HTTP URL from which Telegram will fetch the
 * file</li>
 * <li>{@link #upload(String, String, byte[])} — a new file uploaded via
 * {@code multipart/form-data}</li>
 * </ul>
 * For uploads, the JSON field for the method should reference the part using
 * {@code attach://<attachName>}, and the multipart body must include a binary
 * part whose name equals {@code attachName}.
 * 
 * @author Hai Pham Ngoc
 * @version 1.0.0
 * @since 1.0.0
 */
public sealed interface InputFile permits InputFile.FileId, InputFile.Url, InputFile.Upload {
    /** Existing file by {@code file_id} already stored on Telegram servers. */
    record FileId(String fileId) implements InputFile {
    }

    /** HTTP URL that Telegram will fetch. Should be accessible by Telegram. */
    record Url(String url) implements InputFile {
    }

    /**
     * New file content to be uploaded via multipart.
     * <p>
     * {@code attachName} is the identifier used in the JSON payload via
     * {@code attach://attachName} and must match the multipart part name.
     */
    record Upload(String attachName, String filename, byte[] bytes) implements InputFile {
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof Upload other))
                return false;
            return Objects.equals(this.attachName, other.attachName)
                    && Objects.equals(this.filename, other.filename)
                    && Arrays.equals(this.bytes, other.bytes);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(this.attachName, this.filename);
            result = 31 * result + Arrays.hashCode(this.bytes);
            return result;
        }

        @Override
        public String toString() {
            return "Upload[attachName=" + this.attachName
                    + ", filename=" + this.filename
                    + ", bytes.length=" + (this.bytes == null ? 0 : this.bytes.length)
                    + "]";
        }
    }

    /** Creates an {@link InputFile} from a Telegram {@code file_id}. */
    static InputFile fileId(String id) {
        return new FileId(id);
    }

    /** Creates an {@link InputFile} from an HTTP URL. */
    static InputFile url(String url) {
        return new Url(url);
    }

    /**
     * Creates an {@link InputFile} representing a new multipart upload with a
     * custom {@code attachName}.
     *
     * @param attachName the name to be referenced from JSON via
     *                   {@code attach://attachName}
     * @param filename   the filename to present to Telegram
     * @param bytes      the file content
     */
    static InputFile upload(String attachName, String filename, byte[] bytes) {
        return new Upload(attachName, filename, bytes);
    }

    /** Convenience factory using default {@code attachName} = {@code file}. */
    static InputFile upload(String filename, byte[] bytes) {
        return new Upload("file", filename, bytes);
    }
}
