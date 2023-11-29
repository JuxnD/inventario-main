package pa.inventario.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.inventario.security.entity.Rol;
import pa.inventario.security.enums.RolNombre;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Integer> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
