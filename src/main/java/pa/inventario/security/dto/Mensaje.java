package pa.inventario.security.dto;

public class Mensaje extends Throwable {
    private String mensaje;

    public Mensaje(String mensaje){this.mensaje = mensaje;}
    public String getMensaje(){return mensaje;}
    public void setMensaje(String mensaje){this.mensaje = mensaje;}
}
