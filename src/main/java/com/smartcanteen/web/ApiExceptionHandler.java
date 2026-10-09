package com.smartcanteen.web;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    ResponseEntity<Map<String,String>> api(ApiException e) { return ResponseEntity.status(e.getStatus()).body(Collections.singletonMap("error", e.getMessage())); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,String>> validation() { return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid request")); }
}
