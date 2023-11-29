package pa.inventario.servicio;

import pa.inventario.modelo.Recurso;

import java.util.List;

public interface IRecursoServicio {
    public List<Recurso> listarRecursos();

    public Recurso buscarRecursoPorId(Integer IdRecurso);

    public Recurso guardarRecurso(Recurso recurso);

    public void eliminarRecursoPorId(Integer IdRecurso);


}
