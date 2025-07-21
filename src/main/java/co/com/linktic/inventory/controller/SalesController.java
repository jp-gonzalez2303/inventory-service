package co.com.linktic.inventory.controller;

import co.com.linktic.inventory.model.Sales;
import co.com.linktic.inventory.service.ISalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@Slf4j
public class SalesController {

    private final ISalesService iSalesService;

    /**
     * Handles the update of an inventory product based on the provided sales information.
     * This method registers a new sale and processes it through the sales service.
     *
     * @param sales the Sales object containing the details of the sale, including the product ID
     *              and the purchase quantity. The input must be valid and adhere to the constraints
     *              specified within the Sales model.
     * @return a ResponseEntity containing the Sales object that was processed and created.
     */
    @PostMapping()
    public ResponseEntity<Sales> updateInventoryProduct(@RequestBody @Valid  Sales sales) {
        iSalesService.createSale(sales);

        return ResponseEntity.ok(sales);
    }

}
