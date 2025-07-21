package co.com.linktic.inventory.client;


import co.com.linktic.inventory.exception.ConnectionException;
import co.com.linktic.inventory.exception.ProductNotFoundException;
import co.com.linktic.inventory.model.Product;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "productClient", url = "${products.host}")
public interface ProductsClient {


    /**
     * Retrieves the product details by its unique identifier. If the product service call fails,
     * a fallback method is invoked to handle the error scenario.
     *
     * @param id the unique identifier of the product to retrieve
     * @param token the API key required for authorization
     * @return the product details mapped to a {@link Product} object
     */
    @GetMapping("/api/v1/products/{id}")
    @Retry(name = "productRetry")
    @CircuitBreaker(name = "productCircuitBreaker", fallbackMethod = "fallbackGetById")
    Product getById(@PathVariable("id") Long id, @RequestHeader("x-api-key") String token);


    /**
     * Handles the fallback mechanism when the product retrieval fails.
     * This method is invoked when an error occurs during a call to the product service.
     *
     * @param id the unique identifier of the product that was being retrieved
     * @param token the API key used for authentication and authorization
     * @param ex the throwable exception that was raised during the product retrieval attempt
     * @return does not return a value, as this method either throws an exception or handles an error scenario
     * @throws ProductNotFoundException if the exception indicates the product was not found
     * @throws ConnectionException if the exception is due to a connection-related issue
     */
    default Product fallbackGetById(Long id, String token, Throwable ex) {
        if (ex instanceof FeignException.NotFound) {
            throw new ProductNotFoundException(id);
        }

        throw new ConnectionException("Error de conexión al obtener producto con ID: " + id);
    }

}
