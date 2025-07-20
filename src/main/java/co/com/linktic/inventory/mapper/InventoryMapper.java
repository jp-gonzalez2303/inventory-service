package co.com.linktic.inventory.mapper;

import co.com.linktic.inventory.entity.InventoryEntity;
import co.com.linktic.inventory.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(source = "productId", target = "productoId")
    @Mapping(source = "quantity", target = "cantidad")
    Inventory toDTO(InventoryEntity product);

    @Mapping(source = "productoId", target = "productId")
    @Mapping(source = "cantidad", target = "quantity")
    InventoryEntity toEntity(Inventory productoDTO);
}