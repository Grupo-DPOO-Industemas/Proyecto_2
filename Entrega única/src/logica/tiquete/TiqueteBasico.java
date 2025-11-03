package logica.tiquete;
import java.io.Serializable;

import logica.evento.Evento;
import logica.evento.Localidad;

public class TiqueteBasico extends Tiquete implements Serializable {
    private static final long serialVersionUID = 1L;
	
	public TiqueteBasico(String identificador, double precioBase, double cargoPorcentual, double cuotaAdicionalEmision, Localidad localidad, Evento evento, String fecha, String hora, String estado) {
		super(identificador, precioBase, cargoPorcentual, cuotaAdicionalEmision, localidad, evento, fecha, hora, estado);
	}
	

	@Override
	public double calcularPrecioFinal() {
		return precioBase + (precioBase * cargoPorcentual) + cuotaAdicionalEmision;
	}
	

}
