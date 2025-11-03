package logica.evento;
import logica.tiquete.Tiquete;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Localidad implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private double precioBase;
    private int tiquetesDisponibles;
    private boolean numerada;
    private List<Tiquete> tiquetes;
    private List<Oferta> ofertas;

    public Localidad(String nombre, double precioBase, int cantidadTiquetesDisponibles, boolean numerada) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.tiquetesDisponibles = cantidadTiquetesDisponibles;
        this.numerada = numerada;
        this.tiquetes = new ArrayList<>();
        this.ofertas = new ArrayList<>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public void setTiquetesDisponibles(int tiquetesDisponibles) {
        this.tiquetesDisponibles = tiquetesDisponibles;
    }

    public void setNumerada(boolean numerada) {
        this.numerada = numerada;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getTiquetesDisponibles() {
        return tiquetesDisponibles;
    }

    public boolean isNumerada() {
        return numerada;
    }

    public List<Tiquete> getTiquetes() {
        return tiquetes;
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public void agregarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetes.add(tiquete);
        }
    }

    public void agregarOferta(Oferta oferta) {
        if (oferta != null) {
            ofertas.add(oferta);
        }
    }

    public boolean venderTiquetes(int cantidad) {
        if (cantidad > 0 && cantidad <= tiquetesDisponibles) {
            tiquetesDisponibles -= cantidad;
            return true;
        }
        return false;
    }

    public void devolverTiquetes(int cantidad) {
        if (cantidad > 0) {
            tiquetesDisponibles += cantidad;
        }
    }

    @Override
    public String toString() {
        return nombre + " - Precio base: " + precioBase +
               " - Disponibles: " + tiquetesDisponibles +
               " - Numerada: " + numerada;
    }
}
