package itmo.anasteshap.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new Error(404, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

//    // остальные ексепшены
//    @ExceptionHandler(RuntimeException.class)
//    protected ResponseEntity<?> requestEntityTooLarge(RuntimeException ex) {
//        return new ResponseEntity<>(new Error(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(PayloadTooLargeException.class)
    protected ResponseEntity<?> payloadTooLargeException(PayloadTooLargeException ex) {
        return new ResponseEntity<>(new Error(413, ex.getMessage()), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    // если тип аргумента неверный
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpStatus status) {

        var message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'\n '%s",
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName(), ex.getMessage());
        return new ResponseEntity<>(new Error(400, message), status);
    }

//    // если нет подходящего обработчика
//    @ExceptionHandler(NoHandlerFoundException.class)
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
//        return new ResponseEntity<>(new Error(404, "No Handler Found"), ex.getStatusCode());
//    }

    // если некорректный JSON
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return new ResponseEntity<>(new Error(400, "Validation failed"), headers, status
        );
    }

    // если корректный JSON, но условие @Valid не выполняется: например, поле name имело бы значение null
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return new ResponseEntity<>(new Error(400, "Validation failed"), headers, status);
    }
}


