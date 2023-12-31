package com.khalid.hms.DoctorProfile.exceptions;
import com.khalid.hms.DoctorProfile.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handlerEmailAlreadyExistsException(AlreadyExistsException ex){
        String message = ex.getMessage();
        ApiResponse response = ApiResponse.builder().message(message).success(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationExceptionFound.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationExceptionFound ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEmailNotFoundException(EmailNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SlotIsBookedException.class)
    public ResponseEntity<ApiResponse> handleSlotIsBookedException(SlotIsBookedException ex) {
        String message = ex.getMessage();
        ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);
    }
}
