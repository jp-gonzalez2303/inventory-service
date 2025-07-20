package co.com.linktic.inventory.service;

import co.com.linktic.inventory.client.ProductsClient;
import co.com.linktic.inventory.entity.InventoryEntity;
import co.com.linktic.inventory.exception.InventoryNotFoundException;
import co.com.linktic.inventory.mapper.InventoryMapper;
import co.com.linktic.inventory.model.Inventory;
import co.com.linktic.inventory.model.Product;
import co.com.linktic.inventory.repository.IInventoryRepository;
import co.com.linktic.inventory.service.impl.InventoryService;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private IInventoryRepository productRepository;

    @Mock
    private InventoryMapper inventoryMapper;

    @Mock
    private ProductsClient productsClient;

    @InjectMocks
    private InventoryService inventoryService;

    private Product sampleProduct;
    private InventoryEntity sampleEntity;
    private Inventory sampleInventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(inventoryService, "apiKeyProduct", "mock-api-key");

        sampleProduct = Product.builder()
                .id(1L)
                .nombre("Test")
                .precio(9.99)
                .descripcion("Descripción")
                .build();

        sampleEntity = InventoryEntity.builder()
                .id(1L)
                .productId(1L)
                .quantity(10.0)
                .build();

        sampleInventory = Inventory.builder()
                .productoId(1L)
                .cantidad(10.0)
                .build();
    }

    @Test
    void findByProductIdSuccess() {
        when(productsClient.getById(1L, "mock-api-key")).thenReturn(sampleProduct);
        when(productRepository.findByProductId(1L)).thenReturn(Optional.of(sampleEntity));
        when(inventoryMapper.toDTO(sampleEntity)).thenReturn(sampleInventory);

        Inventory result = inventoryService.findByProductId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getProductoId());
        assertEquals(sampleProduct, result.getProduct());
        verify(productsClient).getById(1L, "mock-api-key");
        verify(productRepository).findByProductId(1L);
        verify(inventoryMapper).toDTO(sampleEntity);
    }

    @Test
    void findByProductIdInventoryNotFound() {
        when(productsClient.getById(1L, "mock-api-key")).thenReturn(sampleProduct);
        when(productRepository.findByProductId(1L)).thenReturn(Optional.empty());

        InventoryNotFoundException ex = assertThrows(InventoryNotFoundException.class,
                () -> inventoryService.findByProductId(1L));

        assertEquals("El producto con id '1' no cuenta con inventario registrado.", ex.getMessage());
        verify(productsClient).getById(1L, "mock-api-key");
        verify(productRepository).findByProductId(1L);
    }

    @Test
    void updateInventoryProductWhenExists() {
        InventoryEntity updatedEntity = sampleEntity;
        updatedEntity.setQuantity(20.0);

        when(productsClient.getById(1L, "mock-api-key")).thenReturn(sampleProduct);
        when(productRepository.findByProductId(1L)).thenReturn(Optional.of(sampleEntity));
        when(productRepository.save(sampleEntity)).thenReturn(updatedEntity);
        when(inventoryMapper.toDTO(updatedEntity)).thenReturn(sampleInventory);

        sampleInventory.setCantidad(20.0);
        Inventory result = inventoryService.updateInventoryByProduct(sampleInventory);

        assertNotNull(result);
        assertEquals(sampleProduct, result.getProduct());
        verify(productRepository).save(sampleEntity);
    }

    @Test
    void updateInventoryProductWhenNotExists() {
        InventoryEntity newEntity = InventoryEntity.builder()
                .productId(1L)
                .quantity(5.0)
                .build();

        InventoryEntity savedEntity = InventoryEntity.builder()
                .productId(1L)
                .quantity(5.0)
                .build();

        when(productsClient.getById(1L, "mock-api-key")).thenReturn(sampleProduct);
        when(productRepository.findByProductId(1L)).thenReturn(Optional.empty());
        when(inventoryMapper.toEntity(sampleInventory)).thenReturn(newEntity);
        when(productRepository.save(newEntity)).thenReturn(savedEntity);
        when(inventoryMapper.toDTO(savedEntity)).thenReturn(sampleInventory);

        Inventory result = inventoryService.updateInventoryByProduct(sampleInventory);

        assertNotNull(result);
        assertEquals(sampleProduct, result.getProduct());
        verify(productRepository).save(newEntity);
    }

    @Test
    void getProductByIdThrowsFeignException() {
        FeignException fakeFeignException = new FeignException.BadRequest(
                "Bad request", Request.create(Request.HttpMethod.GET, "url", Collections.emptyMap(), null, StandardCharsets.UTF_8, null), null, null);

        when(productsClient.getById(1L, "mock-api-key")).thenThrow(fakeFeignException);

        FeignException thrown = assertThrows(FeignException.class, () -> inventoryService.findByProductId(1L));
        assertEquals("Bad request", thrown.getMessage());
    }
}
