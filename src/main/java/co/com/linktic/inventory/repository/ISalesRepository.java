package co.com.linktic.inventory.repository;

import co.com.linktic.inventory.entity.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalesRepository extends JpaRepository<SalesEntity,Long> {
}
