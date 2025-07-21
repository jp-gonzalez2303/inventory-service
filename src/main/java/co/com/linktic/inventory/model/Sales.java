package co.com.linktic.inventory.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
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
public class Sales {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad a comprar es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La cantidad a comprar debe ser mayor a 0")
    private Double cantidadCompra;
}
