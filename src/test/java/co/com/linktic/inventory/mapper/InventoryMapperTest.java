package co.com.linktic.inventory.mapper;

import co.com.linktic.inventory.entity.InventoryEntity;
import co.com.linktic.inventory.model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryMapperTest {

    private InventoryMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = InventoryMapper.INSTANCE;
    }

    @Test
    void shouldMapEntityToDto() {
        InventoryEntity entity = InventoryEntity.builder()
                .id(1L)
                .productId(101L)
                .quantity(20.0)
                .build();

        Inventory dto = mapper.toDTO(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getProductoId()).isEqualTo(101L);
        assertThat(dto.getCantidad()).isEqualTo(20.0);
    }

    @Test
    void shouldMapDtoToEntity() {
        Inventory dto = Inventory.builder()
                .productoId(202L)
                .cantidad(50.0)
                .build();

        InventoryEntity entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getProductId()).isEqualTo(202L);
        assertThat(entity.getQuantity()).isEqualTo(50.0);
    }

    @Test
    void shouldReturnNullIfEntityIsNull() {
        assertThat(mapper.toDTO(null)).isNull();
    }

    @Test
    void shouldReturnNullIfDtoIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }
}
