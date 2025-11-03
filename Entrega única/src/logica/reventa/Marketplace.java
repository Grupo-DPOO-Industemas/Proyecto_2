package logica.reventa;

import java.util.ArrayList;
import java.util.List;
import logica.usuario.Cliente;
import logica.usuario.Administrador;
import logica.tiquete.Tiquete;

public class Marketplace {
	private List<Cliente> clientesInscritos;
	private Administrador administrador;
	private List<Registro> logRegistros;
	private List<Tiquete> tiquetesEnVenta;
	
	public Marketplace(List<Cliente> clientesInscritos, Administrador administrador) {
		super();
		this.clientesInscritos = clientesInscritos;
		this.administrador = administrador;
		this.logRegistros = new ArrayList<>();
		this.tiquetesEnVenta = new ArrayList<>();
	}

	public List<Cliente> getClientesInscritos() {
		return clientesInscritos;
	}

	public void setClientesInscritos(List<Cliente> clientesInscritos) {
		this.clientesInscritos = clientesInscritos;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public List<Registro> getLogRegistros() {
		return logRegistros;
	}

	public void setLogRegistros(List<Registro> logRegistros) {
		this.logRegistros = logRegistros;
	}

	public List<Tiquete> getTiquetesEnVenta() {
		return tiquetesEnVenta;
	}

	public void setTiquetesEnVenta(List<Tiquete> tiquetesEnVenta) {
		this.tiquetesEnVenta = tiquetesEnVenta;
	}
	
	public void registrarCliente(Cliente cliente) {
		clientesInscritos.add(cliente);
	}
	
	public void agregarTiqueteEnVenta(Tiquete tiquete, double precio) {
		tiquetesEnVenta.add(tiquete);
		Registro registro = new Registro(logRegistros.size() + 1, precio, "Poner tiquete en venta a un precio de " + precio + ".", tiquete.getDueno(), tiquete);
		logRegistros.add(registro);
	}
	
	public void eliminarTiqueteDeVenta(Tiquete tiquete) {
		tiquetesEnVenta.remove(tiquete);
		Registro registro = new Registro(logRegistros.size() + 1, 0, "Eliminar tiquete de la venta.", tiquete.getDueno(), tiquete);
	}
}
