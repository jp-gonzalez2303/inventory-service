package co.com.linktic.inventory.controller;

import co.com.linktic.inventory.model.Inventory;
import co.com.linktic.inventory.service.IInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class InventoryControllerTest {

    @Mock
    private IInventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private Inventory sampleInventory;

    @BeforeEach
    void setUp() {
        openMocks(this);
        sampleInventory = Inventory.builder()
                .productoId(1L)
                .cantidad(50.0)
                .build();
    }

    @Test
    void testUpdateInventoryProduct() {
        when(inventoryService.updateInventoryByProduct(sampleInventory)).thenReturn(sampleInventory);

        ResponseEntity<Inventory> response = inventoryController.updateInventoryProduct(sampleInventory);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(sampleInventory, response.getBody());
        verify(inventoryService, times(1)).updateInventoryByProduct(sampleInventory);
    }

    @Test
    void testGetInventoryByProductId() {
        when(inventoryService.findByProductId(101L)).thenReturn(sampleInventory);

        ResponseEntity<Inventory> response = inventoryController.getInventoryByProductId(101L);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(sampleInventory, response.getBody());
        verify(inventoryService, times(1)).findByProductId(101L);
    }
}
