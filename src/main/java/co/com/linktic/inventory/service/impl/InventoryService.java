package co.com.linktic.inventory.service.impl;

import co.com.linktic.inventory.client.ProductsClient;
import co.com.linktic.inventory.entity.InventoryEntity;
import co.com.linktic.inventory.exception.InventoryNotFoundException;
import co.com.linktic.inventory.exception.ProductNotFoundException;
import co.com.linktic.inventory.mapper.InventoryMapper;
import co.com.linktic.inventory.model.Inventory;
import co.com.linktic.inventory.model.Product;
import co.com.linktic.inventory.repository.IInventoryRepository;
import co.com.linktic.inventory.service.IInventoryService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService implements IInventoryService {

    private final IInventoryRepository productRepository;
    private final InventoryMapper inventoryMapper;
    private final ProductsClient productsClient;

    @Value("${products.api.key}")
    private String apiKeyProduct;

    /**
     * Retrieves the inventory details for a given product ID, including the associated product information.
     *
     * @param id the unique identifier of the product for which the inventory needs to be fetched
     * @return the inventory details mapped to an {@link Inventory} object, including the product information
     * @throws InventoryNotFoundException if there is no inventory found for the provided product ID
     */
    @Override
    public Inventory findByProductId(Long id) {
        log.info("Fetching product and inventory by id {}", id);
        Product product = productsClient.getById(id, apiKeyProduct);

        InventoryEntity entity = productRepository.findByProductId(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));

        Inventory dto = inventoryMapper.toDTO(entity);
        dto.setProduct(product);
        return dto;
    }

    /**
     * Updates the inventory information for a given product. If an inventory entry for the product already exists, it
     * updates the quantity. If no entry exists, a new inventory record is created. Associates the resulting inventory
     * with the product details retrieved from an external service.
     *
     * @param inventory the inventory information to be updated, containing the product ID and the new quantity
     * @return the updated inventory mapped to an {@link Inventory} object, including product information
     */
    @Override
    public Inventory updateInventoryByProduct(Inventory inventory) {
        log.info("Updating inventory for product id {}", inventory.getProductoId());
        Product product = productsClient.getById(inventory.getProductoId(), apiKeyProduct);

        InventoryEntity updated = productRepository.findByProductId(inventory.getProductoId())
                .map(entity -> {
                    entity.setQuantity(inventory.getCantidad());
                    return productRepository.save(entity);
                })
                .orElseGet(() -> productRepository.save(inventoryMapper.toEntity(inventory)));

        Inventory response = inventoryMapper.toDTO(updated);
        response.setProduct(product);
        return response;
    }


}