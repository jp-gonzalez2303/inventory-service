package co.com.linktic.inventory.service;

import co.com.linktic.inventory.entity.SalesEntity;
import co.com.linktic.inventory.exception.OutOfStockException;
import co.com.linktic.inventory.model.Inventory;
import co.com.linktic.inventory.model.Sales;
import co.com.linktic.inventory.repository.ISalesRepository;
import co.com.linktic.inventory.service.impl.SalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalesServiceTest {

    @Mock
    private IInventoryService inventoryService;

    @Mock
    private ISalesRepository salesRepository;

    @InjectMocks
    private SalesService salesService;

    private Sales saleRequest;
    private Inventory inventoryWithStock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        saleRequest = Sales.builder()
                .productoId(101L)
                .cantidadCompra(5.0)
                .build();

        inventoryWithStock = Inventory.builder()
                .productoId(101L)
                .cantidad(10.0)
                .build();
    }

    @Test
    void createSaleSuccess() {
        Inventory updatedInventory = Inventory.builder()
                .productoId(101L)
                .cantidad(5.0)
                .build();

        when(inventoryService.findByProductId(101L)).thenReturn(inventoryWithStock);
        when(inventoryService.updateInventoryByProduct(any())).thenReturn(updatedInventory);
        when(salesRepository.save(any(SalesEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> salesService.createSale(saleRequest));

        verify(inventoryService).findByProductId(101L);
        verify(inventoryService).updateInventoryByProduct(argThat(inv ->
                inv.getCantidad() == 5.0 && inv.getProductoId().equals(101L)
        ));
        verify(salesRepository).save(argThat(entity ->
                entity.getProductId().equals(101L)
                        && entity.getSaleQuantity() == 5.0
                        && entity.getSaleDate() != null
        ));
    }


    @Test
    void createSaleThrowsOutOfStockException() {
        Inventory inventoryLowStock = Inventory.builder()
                .productoId(101L)
                .cantidad(2.0)
                .build();

        when(inventoryService.findByProductId(101L)).thenReturn(inventoryLowStock);

        OutOfStockException ex = assertThrows(OutOfStockException.class,
                () -> salesService.createSale(saleRequest));

        assertEquals("No hay suficiente stock disponible para productId: 101", ex.getMessage());

        verify(inventoryService).findByProductId(101L);
        verify(inventoryService, never()).updateInventoryByProduct(any());
        verify(salesRepository, never()).save(any());
    }
}
