package co.com.linktic.inventory.client;

import co.com.linktic.inventory.exception.ConnectionException;
import co.com.linktic.inventory.exception.ProductNotFoundException;
import co.com.linktic.inventory.model.Product;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductsClientTest {

    private final ProductsClient client = new ProductsClient() {
        @Override
        public Product getById(Long id, String token) {
            return null;
        }
    };

    @Test
    void fallbackGetById_WhenNotFound_ShouldThrowProductNotFoundException() {
        FeignException notFound = new FeignException.NotFound(
                "Not found",
                Request.create(Request.HttpMethod.GET, "url", Collections.emptyMap(), null, StandardCharsets.UTF_8, null),
                null,
                null
        );

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class, () ->
                client.fallbackGetById(99L, "dummy", notFound));

        assertEquals("El producto con id '99' no existe.", ex.getMessage());
    }

    @Test
    void fallbackGetById_WhenOtherError_ShouldThrowConnectionException() {
        Throwable generic = new RuntimeException("Internal server error");

        ConnectionException ex = assertThrows(ConnectionException.class, () ->
                client.fallbackGetById(42L, "dummy", generic));

        assertEquals("Error de conexión al obtener producto con ID: 42", ex.getMessage());
    }
}
