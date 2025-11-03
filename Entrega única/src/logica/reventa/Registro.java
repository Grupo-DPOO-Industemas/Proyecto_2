package logica.reventa;

import java.time.LocalDateTime;
import logica.tiquete.Tiquete;
import logica.usuario.Usuario;

public class Registro {
	private int id;
	private LocalDateTime fechaHora;
	private double precio;
	private String detalle;
	private Usuario usuario;
	private Tiquete tiqueteInvolucrado;
	
	public Registro(int id, double precio, String detalle, Usuario usuario, Tiquete tiqueteInvolucrado) {
		this.id = id;
		this.fechaHora = LocalDateTime.now();
		this.precio = precio;
		this.detalle = detalle;
		this.usuario = usuario;
		this.tiqueteInvolucrado = tiqueteInvolucrado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tiquete getTiqueteInvolucrado() {
		return tiqueteInvolucrado;
	}

	public void setTiqueteInvolucrado(Tiquete tiqueteInvolucrado) {
		this.tiqueteInvolucrado = tiqueteInvolucrado;
	}
}
