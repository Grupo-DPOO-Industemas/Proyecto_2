
package logica.usuario;
import java.io.Serializable;
public abstract class UsuarioConSaldo extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    protected double saldo;

    public UsuarioConSaldo(String nombreUsuario, String contrasena, String nombreCompleto, double saldo) {
        super(nombreUsuario, contrasena, nombreCompleto);
        if (saldo < 0)
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void recargarSaldo(double monto) {
        if (monto <= 0)
            throw new IllegalArgumentException("El monto de recarga debe ser positivo.");
        saldo += monto;
    }

    public void descontarSaldo(double monto) {
        if (monto <= 0)
            throw new IllegalArgumentException("El monto de descuento debe ser positivo.");
        if (monto > saldo)
            throw new IllegalStateException("Saldo insuficiente.");
        saldo -= monto;
    }

    public void aumentarSaldo(double monto) {
        if (monto <= 0)
            throw new IllegalArgumentException("El monto debe ser positivo.");
        saldo += monto;
    }

    public boolean tieneFondosSuficientes(double monto) {
        return saldo >= monto;
    }

    @Override
    public String toString() {
        return "UsuarioConSaldo{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
