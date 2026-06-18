package com.diyalo.mangoDiyalo.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Builder.Default
    private Instant timestamp = Instant.now();

    private int status;
    private String message;
    private T data;

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return of(HttpStatus.OK, message, data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return of(HttpStatus.CREATED, message, data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> of(HttpStatus status, String message, T data) {
        ApiResponse<T> body = ApiResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
