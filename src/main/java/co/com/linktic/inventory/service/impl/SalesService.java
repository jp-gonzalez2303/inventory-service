package co.com.linktic.inventory.service.impl;

import co.com.linktic.inventory.entity.SalesEntity;
import co.com.linktic.inventory.exception.OutOfStockException;
import co.com.linktic.inventory.model.Inventory;
import co.com.linktic.inventory.repository.ISalesRepository;
import co.com.linktic.inventory.service.IInventoryService;
import co.com.linktic.inventory.service.ISalesService;
import co.com.linktic.inventory.model.Sales;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesService implements ISalesService {

    private final IInventoryService inventoryService;
    private final ISalesRepository salesRepository;

    /**
     * Creates a new sale record and updates the inventory accordingly.
     * Verifies if inventory has sufficient stock for the requested quantity of the product.
     * If stock is insufficient, throws an {@link OutOfStockException}.
     *
     * @param sales the details of the sale, including product ID and purchase quantity
     * @throws OutOfStockException if the inventory does not have enough stock for the product
     */
    @Override
    @Transactional
    public void createSale(Sales sales) {
        log.info("Init process Sales product to productId {}, quantity {}", sales.getProductoId(), sales.getCantidadCompra());

        Inventory currentInventory = inventoryService.findByProductId(sales.getProductoId());

        if (currentInventory.getCantidad() >= sales.getCantidadCompra()) {
            log.info("The requested quantity is available in inventory {}", inventoryService);

            inventoryService.updateInventoryByProduct(Inventory.builder()
                    .cantidad(currentInventory.getCantidad() - sales.getCantidadCompra())
                    .productoId(sales.getProductoId())
                    .build());

            log.info("Saving the sale {}", sales);
            salesRepository.save(SalesEntity.builder()
                    .productId(sales.getProductoId())
                    .saleQuantity(sales.getCantidadCompra())
                    .saleDate(LocalDateTime.now())
                    .build());

            log.info("Sale saved successfully");
        } else {
            log.error("Insufficient stock to productId {}, quantity to sale{}, actual stock {}", sales.getProductoId(), sales.getCantidadCompra(), currentInventory.getCantidad());

            throw new OutOfStockException("No hay suficiente stock disponible para productId: " + sales.getProductoId());
        }

    }
}