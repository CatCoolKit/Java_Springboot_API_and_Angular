package com.coocle.vinnast.Exceptions;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.coocle.vinnast.Response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(Exception exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException runtimeException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(1001);
        apiResponse.setMessage(runtimeException.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiResponse apiResponse = new ApiResponse();
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation =
                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

        } catch (IllegalArgumentException e) {

        }
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attribute) {

        StringBuilder mes = new StringBuilder();
        String minValue = String.valueOf(attribute.get(MIN_ATTRIBUTE));

        //        attribute.forEach((key, value) -> {
        //            mes.append(key + ":" + value.toString() + " ");
        //        });

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }

    //    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    //    ResponseEntity<String> handlingMethodArgumentNotValidException(
    //            MethodArgumentNotValidException methodArgumentNotValidException
    //    ){
    //        return
    // ResponseEntity.badRequest().body(methodArgumentNotValidException.getFieldError().getDefaultMessage());
    //    }

}
