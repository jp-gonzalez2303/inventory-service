package co.com.linktic.inventory.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Inventory {


    @NotBlank(message = "El id del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad del inventario es obligatoria")
    private Double cantidad;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private  Product product;

}