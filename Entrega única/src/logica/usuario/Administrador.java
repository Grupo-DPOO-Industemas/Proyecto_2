package logica.usuario;
import java.io.Serializable;

import logica.tiquete.Tiquete;
import logica.evento.Venue;
import logica.reventa.Marketplace;

public class Administrador extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private double porcentajeGanancia;

    public Administrador(String nombreUsuario, String contrasena, String nombreCompleto) {
        super(nombreUsuario, contrasena, nombreCompleto);
        this.porcentajeGanancia = 10.0; // valor por defecto, puede modificarse
    }

    public double getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public void setPorcentajeGanancia(double porcentajeGanancia) {
        if (porcentajeGanancia < 0 || porcentajeGanancia > 100)
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public void aprobarVenue(Venue venue) {
        if (venue == null)
            throw new IllegalArgumentException("El venue no puede ser nulo.");
        if (venue.isAprobado())
            throw new IllegalStateException("El venue ya fue aprobado.");
        venue.aprobar(this);
    }

    public void revocarAprobacionVenue(Venue venue) {
        if (venue == null)
            throw new IllegalArgumentException("El venue no puede ser nulo.");
        if (!venue.isAprobado())
            throw new IllegalStateException("El venue no está aprobado.");
        venue.revocarAprobacion();
    }
    
    public boolean aprobarReembolsoCliente(Tiquete tiquete, String motivo) {
        if (tiquete == null)
            throw new IllegalArgumentException("El tiquete no puede ser nulo.");
        if (motivo == null)
            return false;
        // Use equalsIgnoreCase for string comparison and return the decision.
        if ("Calamidad".equalsIgnoreCase(motivo)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nombre='" + getNombreCompleto() + '\'' +
                ", porcentajeGanancia=" + porcentajeGanancia + "%" +
                '}';
    }
    
    public void consultarLogRegistros(Marketplace marketplace) {
    	System.out.println("Log de Registros del Marketplace:");
    	for (var r : marketplace.getLogRegistros()) {
    		System.out.println("ID: " + r.getId() + ", FechaHora: " + r.getFechaHora() + ", Precio: " + r.getPrecio() +
					", Detalle: " + r.getDetalle() + ", Usuario: " + r.getUsuario().getNombreCompleto() +
					", Tiquete ID: " + r.getTiqueteInvolucrado().getIdentificador());
    	}
    }
    
    public void eliminarContraOferta(Marketplace marketplace, int registroId) {
        if (marketplace == null)
            throw new IllegalArgumentException("El marketplace no puede ser nulo.");

        marketplace.eliminarContraOfertaPorId(registroId, this);
    }
    
    public void aprobarOrganizador(OrganizadorDeEventos organizador) {
        if (organizador == null) {
            throw new IllegalArgumentException("El organizador no puede ser nulo.");
        }
        if (organizador.isAprobado()) {
            throw new IllegalStateException("El organizador ya está aprobado.");
        }
        organizador.setAprobado(true);
        System.out.println("El organizador " + organizador.getNombreCompleto() + " ha sido aprobado.");
    }       
}