package pa.inventario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pa.inventario.modelo.Recurso;

public interface RecursoRepositorio extends JpaRepository<Recurso, Integer> {

}

