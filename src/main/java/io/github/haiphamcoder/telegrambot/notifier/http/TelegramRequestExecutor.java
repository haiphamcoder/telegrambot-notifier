package io.github.haiphamcoder.telegrambot.notifier.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramApiException;
import io.github.haiphamcoder.telegrambot.notifier.exception.TelegramHttpException;
import io.github.haiphamcoder.telegrambot.notifier.util.JsonUtils;

public final class TelegramRequestExecutor {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=utf-8";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; charset=utf-8";

    private TelegramRequestExecutor() {
        throw new UnsupportedOperationException("This is a utility class and should not be instantiated");
    }

    public static <T> T postJson(CloseableHttpClient httpClient, String url, Map<String, Object> payload,
            Class<T> responseType) throws TelegramApiException, TelegramHttpException {
        HttpPost request = new HttpPost(url);
        request.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        try {
            request.setEntity(new StringEntity(JsonUtils.toJson(payload), StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new TelegramApiException("Failed to serialize request payload: " + e.getMessage());
        }
        return execute(httpClient, request, responseType);
    }

    public static <T> T postForm(CloseableHttpClient httpClient, String url, Map<String, String> fields,
            Class<T> responseType) {
        HttpPost request = new HttpPost(url);
        request.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_FORM);
        List<NameValuePair> params = fields.entrySet().stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        request.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        return execute(httpClient, request, responseType);
    }

    public static <T> T postMultipart(CloseableHttpClient httpClient, String url, Map<String, String> fields,
            List<MultipartBuilder.Part> files, Class<T> responseType) {
        HttpPost request = new HttpPost(url);
        request.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_MULTIPART);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .setCharset(StandardCharsets.UTF_8)
                .setMode(HttpMultipartMode.STRICT);
        fields.forEach(multipartEntityBuilder::addTextBody);
        for (MultipartBuilder.Part file : files) {
            multipartEntityBuilder.addBinaryBody(file.fieldName(), file.bytes(), ContentType.DEFAULT_BINARY,
                    file.filename());
        }
        request.setEntity(multipartEntityBuilder.build());
        return execute(httpClient, request, responseType);
    }

    private static <T> T execute(CloseableHttpClient httpClient, ClassicHttpRequest request, Class<T> responseType) {
        try {
            return httpClient.execute(request, response -> {
                int statusCode = response.getCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                if (statusCode / 100 != 2) {
                    throw new TelegramHttpException(statusCode, responseBody);
                }
                JsonNode tree = JsonUtils.getObjectMapper().readTree(responseBody);
                if (!tree.path("ok").asBoolean(false)) {
                    throw new TelegramApiException(responseBody);
                }
                return JsonUtils.getObjectMapper().treeToValue(tree.path("result"), responseType);
            });
        } catch (IOException e) {
            throw new TelegramHttpException("I/O error", e);
        }
    }

}
