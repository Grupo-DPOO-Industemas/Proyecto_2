
package logica.usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import logica.tiquete.Tiquete;

public class Cliente extends UsuarioConSaldo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Tiquete> tiquetesComprados;

    public Cliente(String nombreUsuario, String contrasena, String nombreCompleto, double saldo) {
        super(nombreUsuario, contrasena, nombreCompleto, saldo);
        this.tiquetesComprados = new ArrayList<>();
    }

    public List<Tiquete> getTiquetesComprados() {
        return tiquetesComprados;
    }

    public void agregarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetesComprados.add(tiquete);
            tiquete.setDueno(this);
        }
    }

    public boolean comprarTiquete(Tiquete tiquete) {
        if (tiquete == null)
            throw new IllegalArgumentException("El tiquete no puede ser nulo.");

        double precio = tiquete.calcularPrecioFinal();

        if (precio > getSaldo()) {
            System.out.println("Saldo insuficiente para comprar el tiquete.");
            return false;
        }

        if (tiquete.isVendido()) {
            System.out.println("El tiquete ya fue vendido.");
            return false;
        }

        descontarSaldo(precio);
        tiquete.marcarComoVendido(this);
        tiquetesComprados.add(tiquete);
        return true;
    }

    public String solicitarReembolso(Tiquete tiquete, Administrador administrador, String motivo) {
        if (tiquete == null || !tiquetesComprados.contains(tiquete)) {
            return "El tiquete no pertenece al cliente.";
        }

        boolean aprobado = administrador.aprobarReembolsoCliente(tiquete, motivo);
        if (!aprobado) {
            return "Solicitud de reembolso denegada para el tiquete " + tiquete.getIdentificador() + ".";
        }
        aumentarSaldo(tiquete.getPrecioBase());
        tiquete.marcarComoReembolsado();
        return "Solicitud de reembolso para el tiquete " + tiquete.getIdentificador() + " ha sido aprobada.";
    }

    public boolean transferirTiquete(Tiquete tiquete, Cliente nuevoDueno) {
        if (tiquete == null || nuevoDueno == null)
            return false;
        if (!tiquetesComprados.contains(tiquete))
            return false;

        tiquetesComprados.remove(tiquete);
        nuevoDueno.agregarTiquete(tiquete);
        tiquete.setDueno(nuevoDueno);
        return true;
    }

    public void mostrarTiquetes() {
        System.out.println("Tiquetes de " + getNombreCompleto() + ":");
        for (Tiquete t : tiquetesComprados) {
            System.out.println(" - " + t);
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + getNombreCompleto() + '\'' +
                ", saldo=" + getSaldo() +
                ", tiquetesComprados=" + tiquetesComprados.size() +
                '}';
    }
}