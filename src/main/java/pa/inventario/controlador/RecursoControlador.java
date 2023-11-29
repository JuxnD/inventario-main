package pa.inventario.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pa.inventario.excepcion.RecursoNoEncontradoExcepcion;
import pa.inventario.modelo.Recurso;
import pa.inventario.servicio.RecursoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//http://localhost:8080/inventario-app
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/inventario-app")

public class RecursoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(RecursoControlador.class);
    @Autowired

    private RecursoServicio recursoServicio;
//http://localhost:8080/inventario-app/recursos

    @GetMapping("/recursos")
    public List<Recurso> obtenerRecursos(){
        List<Recurso> recursos = this.recursoServicio.listarRecursos();
        logger.info("Recursos Obtenidos");
        recursos.forEach((recurso -> logger.info(recurso.toString())));
    return recursos;
    }
   /** @PreAuthorize("hasRole('ADMIN')") **/
    @PostMapping( "/recursos")
    public Recurso agregarRecurso(@RequestBody Recurso recurso) {
        logger.info("Recurso a agregar: "+recurso);
            return this.recursoServicio.guardarRecurso(recurso);
    }

    @GetMapping("/recursos/{id}")
        public ResponseEntity<Recurso> obtenerRecursoPorId(
            @PathVariable int id){
         Recurso recurso = this.recursoServicio.buscarRecursoPorId(id);
         if(recurso != null)
         return  ResponseEntity.ok(recurso);
         else
            throw  new RecursoNoEncontradoExcepcion("No se encontró el recurso: "+id);
    }

    /** @PreAuthorize("hasRole('ADMIN')") **/
    @PutMapping("/recursos/{id}")
    public ResponseEntity<Recurso> actualizarRecurso(
            @PathVariable int id,
            @RequestBody Recurso recursoRecibido){
        Recurso recurso = this.recursoServicio.buscarRecursoPorId(id);
        recurso.setDescripcion(recursoRecibido.getDescripcion());
        recurso.setSede(recursoRecibido.getSede());
        recurso.setCantidad(recursoRecibido.getCantidad());
        this.recursoServicio.guardarRecurso(recurso);
        return ResponseEntity.ok(recurso);
    }
    /** @PreAuthorize("hasRole('ADMIN')") **/
    @DeleteMapping("/recursos/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarRecurso(
            @PathVariable int id){
        Recurso recurso = recursoServicio.buscarRecursoPorId(id);
        if (recurso == null)
            throw new RecursoNoEncontradoExcepcion("Recurso no encontrado"+ id);
        this.recursoServicio.eliminarRecursoPorId(recurso.getIdRecurso());
        Map<String, Boolean> respuesta= new HashMap<>();
        respuesta.put("Eliminado", Boolean.TRUE);
         return  ResponseEntity.ok(respuesta);
    }

}
