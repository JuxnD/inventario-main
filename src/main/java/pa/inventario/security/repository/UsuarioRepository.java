package pa.inventario.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pa.inventario.security.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existByNombreUsuario(String nombreUsuario);
    boolean existByEmail(String email);
}
