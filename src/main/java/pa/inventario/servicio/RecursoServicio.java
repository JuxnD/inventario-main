package pa.inventario.servicio;
    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pa.inventario.modelo.Recurso;
import pa.inventario.repositorio.RecursoRepositorio;

import java.util.List;
@Service

public class RecursoServicio implements IRecursoServicio {

    @Autowired
    private RecursoRepositorio recursoRepositorio;

    @Override
    public List<Recurso> listarRecursos() {

        return this.recursoRepositorio.findAll();
    }

    @Override
    public Recurso buscarRecursoPorId(Integer IdRecurso) {
        Recurso recurso=
        this.recursoRepositorio.findById(IdRecurso).orElse(null);
        return recurso;
    }

    @Override
    public Recurso guardarRecurso(Recurso recurso) {
    return this.recursoRepositorio.save(recurso);
    }

    @Override
    public void eliminarRecursoPorId(Integer IdRecurso) {
    this.recursoRepositorio.deleteById(IdRecurso);
    }
}
    