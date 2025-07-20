package co.com.linktic.inventory.service;

import co.com.linktic.inventory.model.Inventory;

public interface IInventoryService {

    Inventory findByProductId(Long id);


    Inventory updateInventoryByProduct(Inventory inventory);
}
