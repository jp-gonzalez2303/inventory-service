package co.com.linktic.inventory.exception;


import co.com.linktic.inventory.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for {@link MethodArgumentNotValidException} that processes validation errors
     * and returns a detailed error response.
     *
     * @param ex the exception containing validation error details
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with error code "VALIDATION_ERROR"
     * and a message describing the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Datos inválidos");

        return ResponseEntity.badRequest().body(new ErrorResponse("VALIDATION_ERROR", error));
    }

    /**
     * Handles exceptions of type {@code InventoryNotFoundException} and generates
     * a response with an error message and a "Not Found" HTTP status.
     *
     * @param ex the {@code InventoryNotFoundException} that was thrown
     * @return a {@code ResponseEntity} containing an {@code ErrorResponse} with
     * error code "INVENTORY_NOT_FOUND" and a message describing the error
     */
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryNotFound(InventoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("INVENTORY_NOT_FOUND", ex.getMessage()));
    }

    /**
     * Handles exceptions of type {@code ProductNotFoundException} and generates a response
     * with an error message and an HTTP status of "Not Found".
     *
     * @param ex the {@code ProductNotFoundException} that was thrown
     * @return a {@code ResponseEntity} containing an {@code ErrorResponse} with
     *         error code "PRODUCT_NOT_EXISTS" and a message describing the error
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("PRODUCT_NOT_EXISTS", ex.getMessage()));
    }

    /**
     * Handles exceptions of type {@code ConnectionException} and generates a response
     * with an error message and an HTTP status of "Conflict".
     *
     * @param ex the {@code ConnectionException} that was thrown
     * @return a {@code ResponseEntity} containing an {@code ErrorResponse} with
     *         error code "ERROR_CONNECTION" and a message describing the error
     */
    @ExceptionHandler(ConnectionException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ConnectionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("ERROR_CONNECTION", ex.getMessage()));
    }

    /**
     * Handles exceptions of type {@code OutOfStockException} and generates a response
     * with an error message and an HTTP status of "Conflict".
     *
     * @param ex the {@code OutOfStockException} that was thrown, containing details about insufficient stock
     * @return a {@code ResponseEntity} containing an {@code ErrorResponse} with
     *         error code "INSUFFICIENT_STOCK" and a message describing the error
     */
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorResponse> handleProductInventoryOutOfStock(OutOfStockException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("INSUFFICIENT_STOCK", ex.getMessage()));
    }

}
