package co.com.linktic.inventory.controller;

import co.com.linktic.inventory.model.Sales;
import co.com.linktic.inventory.service.ISalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SalesControllerTest {

    @Mock
    private ISalesService iSalesService;

    @InjectMocks
    private SalesController salesController;

    private Sales sampleSale;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleSale = Sales.builder()
                .productoId(101L)
                .cantidadCompra(5.0)
                .build();
    }

    @Test
    void testUpdateInventoryProduct() {
        doNothing().when(iSalesService).createSale(sampleSale);

        ResponseEntity<Sales> response = salesController.updateInventoryProduct(sampleSale);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(sampleSale, response.getBody());
        verify(iSalesService, times(1)).createSale(sampleSale);
    }
}
