package logica.tiquete;
import java.io.Serializable;

import logica.evento.Localidad;
import logica.evento.Evento;
import logica.usuario.Cliente;

public abstract class Tiquete implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String identificador;
    protected double precioBase;
    protected double cargoPorcentual;
    protected double cuotaAdicionalEmision;
    protected Localidad localidad;
    protected Evento evento;
    protected Cliente dueno;
    protected String fecha;
    protected String hora;
    protected String estado;

    public Tiquete(String identificador, double precioBase, double cargoPorcentual,
                   double cuotaAdicionalEmision, Localidad localidad, Evento evento,
                   String fecha, String hora, String estado) {

        this.identificador = identificador;
        this.precioBase = precioBase;
        this.cargoPorcentual = cargoPorcentual;
        this.cuotaAdicionalEmision = cuotaAdicionalEmision;
        this.localidad = localidad;
        this.evento = evento;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.dueno = null;
    }

    public String getIdentificador() {
        return identificador;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public double getCargoPorcentual() {
        return cargoPorcentual;
    }

    public double getCuotaAdicionalEmision() {
        return cuotaAdicionalEmision;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public Evento getEvento() {
        return evento;
    }

    public Cliente getDueno() {
        return dueno;
    }

    public void setDueno(Cliente dueno) {
        this.dueno = dueno;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	public boolean isVendido() {
        return "vendido".equalsIgnoreCase(estado);
    }

    public void marcarComoVendido(Cliente comprador) {
        this.dueno = comprador;
        this.estado = "vendido";
    }

    public void marcarComoReembolsado() {
        this.estado = "reembolsado";
    }

    public void marcarComoTransferido(Cliente nuevoDueno) {
        this.dueno = nuevoDueno;
        this.estado = "transferido";
    }

    public abstract double calcularPrecioFinal();

    @Override
    public String toString() {
        return "Tiquete{" +
                "id='" + identificador + '\'' +
                ", localidad=" + (localidad != null ? localidad.getNombre() : "sin localidad") +
                ", evento=" + (evento != null ? evento.getNombre() : "sin evento") +
                ", estado='" + estado + '\'' +
                '}';
    }
}
