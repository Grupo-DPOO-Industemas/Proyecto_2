package consola;

import java.util.List;
import java.util.Scanner;
import logica.persistencia.PersistenciaBinaria;
import logica.usuario.Administrador;
import logica.evento.Venue;

public class ConsolaAdministrador {

    private static final String RUTA_VENUES = "data/venues.dat";
    private Scanner sc;
    private Administrador admin;
    private List<Venue> venues;

    public ConsolaAdministrador(Administrador admin) {
        this.admin = admin;
        this.sc = new Scanner(System.in);
        try {
            this.venues = PersistenciaBinaria.cargarDatos(RUTA_VENUES);
        } catch (Exception e) {
            this.venues = new java.util.ArrayList<>();
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
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> verVenuesPendientes();
                case 2 -> aprobarVenue();
                case 3 -> revocarVenue();
                case 4 -> modificarGanancia();
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
        } catch (Exception e) {
            System.out.println("Error guardando datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Administrador admin = new Administrador("admin", "1234", "Administrador General");
        ConsolaAdministrador consola = new ConsolaAdministrador(admin);
        consola.ejecutarAplicacion();
    }
}
