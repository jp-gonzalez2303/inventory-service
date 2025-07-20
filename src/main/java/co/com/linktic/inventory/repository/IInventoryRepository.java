package co.com.linktic.inventory.repository;

import co.com.linktic.inventory.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IInventoryRepository extends JpaRepository<InventoryEntity, Long> {

    Optional<InventoryEntity> findByProductId(Long id);
}