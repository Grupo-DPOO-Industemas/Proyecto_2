package logica.tiquete;
import java.io.Serializable;

import logica.evento.Evento;
import logica.evento.Localidad;

public abstract class TiqueteMultiple extends Tiquete implements Serializable {
    private static final long serialVersionUID = 1L;
	
	protected int cantidadAccesos;
	
	public TiqueteMultiple(String identificador, double precioBase, double cargoPorcentual, double cuotaAdicionalEmision, Localidad localidad, Evento evento, String fecha, String hora, String estado, int cantidadAccesos) {
		super(identificador, precioBase, cargoPorcentual, cuotaAdicionalEmision, localidad, evento, fecha, hora, estado);
		this.cantidadAccesos = cantidadAccesos;
	}
	

	public int getCantidadAccesos() {
		return cantidadAccesos;
	}


	public void setCantidadAccesos(int cantidadAccesos) {
		this.cantidadAccesos = cantidadAccesos;
	}
	
	
	@Override
	public abstract double calcularPrecioFinal();

}
