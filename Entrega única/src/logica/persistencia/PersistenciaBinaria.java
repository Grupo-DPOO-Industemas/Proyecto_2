package logica.persistencia;

import java.io.*;
import java.util.List;

public class PersistenciaBinaria {

	public static <T> void guardarDatos(List<T> lista, String nombreArchivo) throws IOException {
	    File archivo = new File(nombreArchivo);
	    archivo.getParentFile().mkdirs(); // crea carpeta si no existe
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
	        oos.writeObject(lista);
	    }
	}

    @SuppressWarnings("unchecked")
    public static <T> List<T> cargarDatos(String nombreArchivo) throws IOException, ClassNotFoundException {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return new java.util.ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        }
    }
}
