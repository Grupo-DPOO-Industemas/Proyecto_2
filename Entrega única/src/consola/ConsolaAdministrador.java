package consola;

import java.util.List;
import java.util.Scanner;

import logica.persistencia.PersistenciaBinaria;
import logica.usuario.Administrador;
import logica.evento.Venue;
import logica.reventa.Marketplace;
import logica.reventa.Registro;
import logica.tiquete.Tiquete;

public class ConsolaAdministrador {

    private static final String RUTA_VENUES = "data/venues.dat";
    private static final String RUTA_MARKET = "data/marketplace.dat";
    private static final String RUTA_REGISTROS = "data/registros.dat";

    private Scanner sc;
    private Administrador admin;
    private Marketplace marketplace;
    private List<Venue> venues;
    private List<Registro> registros;

    public ConsolaAdministrador(Administrador admin, Marketplace marketplace) {
        this.admin = admin;
        this.marketplace = marketplace;
        this.sc = new Scanner(System.in);

        try {
            this.venues = PersistenciaBinaria.cargarDatos(RUTA_VENUES);
        } catch (Exception e) {
            this.venues = new java.util.ArrayList<>();
        }

        try {
            this.registros = PersistenciaBinaria.cargarDatos(RUTA_REGISTROS);
        } catch (Exception e) {
            this.registros = new java.util.ArrayList<>();
        }
    }

    public void ejecutarAplicacion() {
        System.out.println("=== SISTEMA DE ADMINISTRADOR ===");
        if (!autenticar()) {
            System.out.println("Credenciales incorrectas. Saliendo del sistema...");
            return;
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Ver venues pendientes");
            System.out.println("2. Aprobar venue");
            System.out.println("3. Revocar aprobación de venue");
            System.out.println("4. Modificar porcentaje de ganancia");
            System.out.println("5. Ver tiquetes en venta (Marketplace)");
            System.out.println("6. Eliminar tiquete del Marketplace");
            System.out.println("7. Ver registros del Marketplace");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> verVenuesPendientes();
                case 2 -> aprobarVenue();
                case 3 -> revocarVenue();
                case 4 -> modificarGanancia();
                case 5 -> verTiquetesEnVenta();
                case 6 -> eliminarTiqueteDeVenta();
                case 7 -> verRegistrosMarketplace();
                case 0 -> {
                    guardarDatos();
                    continuar = false;
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private boolean autenticar() {
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();
        return admin.getNombreUsuario().equals(usuario) && admin.getContrasena().equals(contrasena);
    }

    private void verVenuesPendientes() {
        System.out.println("\n--- VENUES PENDIENTES ---");
        boolean hayPendientes = false;
        for (Venue v : venues) {
            if (!v.isAprobado()) {
                System.out.println(v);
                hayPendientes = true;
            }
        }
        if (!hayPendientes)
            System.out.println("No hay venues pendientes de aprobación.");
    }

    private void aprobarVenue() {
        System.out.print("Ingrese el nombre del venue a aprobar: ");
        String nombre = sc.nextLine();
        Venue v = buscarVenue(nombre);
        if (v != null) {
            try {
                admin.aprobarVenue(v);
                System.out.println("Venue aprobado correctamente.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el venue.");
        }
    }

    private void revocarVenue() {
        System.out.print("Ingrese el nombre del venue a revocar: ");
        String nombre = sc.nextLine();
        Venue v = buscarVenue(nombre);
        if (v != null) {
            try {
                admin.revocarAprobacionVenue(v);
                System.out.println("Aprobación revocada correctamente.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el venue.");
        }
    }

    private void modificarGanancia() {
        System.out.print("Ingrese el nuevo porcentaje de ganancia: ");
        double nuevo = Double.parseDouble(sc.nextLine());
        try {
            admin.setPorcentajeGanancia(nuevo);
            System.out.println("Nuevo porcentaje: " + admin.getPorcentajeGanancia() + "%");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verTiquetesEnVenta() {
        System.out.println("\n--- TIQUETES EN MARKETPLACE ---");
        if (marketplace.getTiquetesEnVenta().isEmpty()) {
            System.out.println("No hay tiquetes en venta actualmente.");
        } else {
            for (Tiquete t : marketplace.getTiquetesEnVenta().values()) {
                System.out.println(t);
            }
        }
    }

    private void eliminarTiqueteDeVenta() {
        System.out.print("Ingrese el ID del tiquete a eliminar del Marketplace: ");
        int id = Integer.parseInt(sc.nextLine());
        Tiquete t = marketplace.getTiquetesEnVenta().get(id);
        if (t == null) {
            System.out.println("No existe un tiquete con ese ID en venta.");
            return;
        }
        marketplace.eliminarTiqueteDeVenta(t);
        System.out.println("Tiquete eliminado correctamente del Marketplace.");
    }

    private void verRegistrosMarketplace() {
        System.out.println("\n--- HISTORIAL DE REGISTROS DEL MARKETPLACE ---");
        if (marketplace.getLogRegistros().isEmpty()) {
            System.out.println("No hay registros de actividades todavía.");
        } else {
            for (Registro r : marketplace.getLogRegistros()) {
                System.out.println("[" + r.getFechaHora() + "] " + r.getDetalle() + " - Precio: $" + r.getPrecio());
            }
        }
    }

    private Venue buscarVenue(String nombre) {
        for (Venue v : venues) {
            if (v.getNombre().equalsIgnoreCase(nombre)) {
                return v;
            }
        }
        return null;
    }

    private void guardarDatos() {
        try {
            PersistenciaBinaria.guardarDatos(venues, RUTA_VENUES);
            PersistenciaBinaria.guardarDatos(List.of(marketplace), RUTA_MARKET);
            PersistenciaBinaria.guardarDatos(marketplace.getLogRegistros(), RUTA_REGISTROS);
        } catch (Exception e) {
            System.out.println("Error guardando datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Administrador admin = new Administrador("admin", "1234", "Administrador General");
        Marketplace market = new Marketplace(new java.util.ArrayList<>(), admin);
        ConsolaAdministrador consola = new ConsolaAdministrador(admin, market);
        consola.ejecutarAplicacion();
    }
}
