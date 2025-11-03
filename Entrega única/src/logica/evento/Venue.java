package logica.evento;
import logica.usuario.Administrador;
import logica.usuario.OrganizadorDeEventos;
import java.io.Serializable;

public class Venue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ubicacion;
    private int capacidadMaxima;
    private String restriccionesUso;
    private boolean aprobado;
    private Administrador aprobadoPor;
    private OrganizadorDeEventos sugeridoPor;
    private boolean sugeridoPorOrganizador;
    private Evento eventoActual;

    public Venue() {
    }

    public Venue(String ubicacion, int capacidadMaxima, String restriccionesUso,
                 OrganizadorDeEventos sugeridoPor, boolean sugeridoPorOrganizador) {
        if (capacidadMaxima <= 0)
            throw new IllegalArgumentException("La capacidad máxima debe ser positiva.");
        this.ubicacion = ubicacion;
        this.capacidadMaxima = capacidadMaxima;
        this.restriccionesUso = restriccionesUso;
        this.sugeridoPor = sugeridoPor;
        this.sugeridoPorOrganizador = sugeridoPorOrganizador;
        this.aprobado = false;
        this.aprobadoPor = null;
        this.eventoActual = null;
    }

    public void aprobar(Administrador admin) {
        if (this.aprobado)
            throw new IllegalStateException("El venue ya fue aprobado previamente.");
        this.aprobado = true;
        this.aprobadoPor = admin;
    }

    public void revocarAprobacion() {
        this.aprobado = false;
        this.aprobadoPor = null;
    }

    public void asignarEvento(Evento evento) {
        if (this.eventoActual != null)
            throw new IllegalStateException("El venue ya tiene un evento asignado actualmente.");
        this.eventoActual = evento;
    }

    public void liberarEvento() {
        this.eventoActual = null;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        if (capacidadMaxima <= 0)
            throw new IllegalArgumentException("La capacidad máxima debe ser positiva.");
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getRestriccionesUso() {
        return restriccionesUso;
    }

    public void setRestriccionesUso(String restriccionesUso) {
        this.restriccionesUso = restriccionesUso;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Administrador getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(Administrador aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }

    public OrganizadorDeEventos getSugeridoPor() {
        return sugeridoPor;
    }

    public void setSugeridoPor(OrganizadorDeEventos sugeridoPor) {
        this.sugeridoPor = sugeridoPor;
    }

    public boolean isSugeridoPorOrganizador() {
        return sugeridoPorOrganizador;
    }

    public void setSugeridoPorOrganizador(boolean sugeridoPorOrganizador) {
        this.sugeridoPorOrganizador = sugeridoPorOrganizador;
    }

    public Evento getEventoActual() {
        return eventoActual;
    }

    public void setEventoActual(Evento eventoActual) {
        this.eventoActual = eventoActual;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "ubicacion='" + ubicacion + '\'' +
                ", capacidadMaxima=" + capacidadMaxima +
                ", restriccionesUso='" + restriccionesUso + '\'' +
                ", aprobado=" + aprobado +
                ", sugeridoPorOrganizador=" + sugeridoPorOrganizador +
                '}';
    }
}
