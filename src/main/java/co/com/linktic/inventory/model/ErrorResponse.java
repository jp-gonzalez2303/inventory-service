package co.com.linktic.inventory.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ErrorResponse {

    private String code;
    private String message;
}
