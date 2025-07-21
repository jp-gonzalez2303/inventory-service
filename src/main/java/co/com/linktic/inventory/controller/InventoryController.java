package co.com.linktic.inventory.controller;

import co.com.linktic.inventory.model.Inventory;
import co.com.linktic.inventory.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final IInventoryService inventoryService;


    /**
     * Updates the inventory for a specific product based on the provided inventory details.
     *
     * @param inventory the inventory object containing the product ID and updated quantity
     * @return a ResponseEntity containing the updated Inventory object
     */
    @PutMapping()
    public ResponseEntity<Inventory> updateInventoryProduct(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.updateInventoryByProduct(inventory));
    }

    /**
     * Retrieves the inventory details for a specific product based on its product ID.
     *
     * @param productId the unique identifier of the product whose inventory is to be retrieved
     * @return a ResponseEntity containing the Inventory object associated with the specified product ID
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.findByProductId(productId));
    }



}