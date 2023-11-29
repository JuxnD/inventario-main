package pa.inventario.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IdRecurso;
    String descripcion;
    String Sede;
    Integer cantidad;

    public Integer getIdRecurso() {
        return IdRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        IdRecurso = idRecurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSede() {
        return Sede;
    }

    public void setSede(String sede) {
        Sede = sede;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
