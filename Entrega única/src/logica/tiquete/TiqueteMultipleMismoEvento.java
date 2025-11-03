package logica.tiquete;
import java.io.Serializable;

import logica.evento.Evento;
import logica.evento.Localidad;

public class TiqueteMultipleMismoEvento extends TiqueteMultiple implements Serializable {
    private static final long serialVersionUID = 1L;

	public TiqueteMultipleMismoEvento(String identificador, double precioBase, double cargoPorcentual, double cuotaAdicionalEmision, Localidad localidad, Evento evento,
			String fecha, String hora, String estado, int cantidadAccesos) {
		super(identificador, precioBase, cargoPorcentual, cuotaAdicionalEmision, localidad, evento, fecha, hora, estado, cantidadAccesos);
	}

	@Override
	public double calcularPrecioFinal() {
		// TODO Auto-generated method stub
		return cantidadAccesos * (precioBase + (precioBase * cargoPorcentual) + cuotaAdicionalEmision);
	}

}
