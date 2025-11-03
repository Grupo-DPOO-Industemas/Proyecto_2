
package logica.usuario;
import java.io.Serializable;
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String nombreUsuario;
    protected String contrasena;
    protected String nombreCompleto;
    protected String correo;

    public Usuario(String nombreUsuario, String contrasena, String nombreCompleto) {
        this(nombreUsuario, contrasena, nombreCompleto, null);
    }

    public Usuario(String nombreUsuario, String contrasena, String nombreCompleto, String correo) {
        if (nombreUsuario == null || nombreUsuario.isBlank())
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        if (contrasena == null || contrasena.isBlank())
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean validarContrasena(String contrasenaIngresada) {
        return this.contrasena.equals(contrasenaIngresada);
    }

    public void cambiarContrasena(String contrasenaActual, String nuevaContrasena) {
        if (!validarContrasena(contrasenaActual))
            throw new IllegalArgumentException("Contraseña actual incorrecta.");
        if (nuevaContrasena == null || nuevaContrasena.isBlank())
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía.");
        this.contrasena = nuevaContrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correo='" + (correo != null ? correo : "sin correo") + '\'' +
                '}';
    }
}
