package co.com.linktic.inventory.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(Long id) {
        super("El producto con id '" + id + "' no cuenta con inventario registrado.");
    }
}