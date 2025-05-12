package TriviaUCAB.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class Validator {

    static public int validarInt(String pregunta, Scanner entrada) {
        boolean entradaValida;
        int opcion = -1;
        do {
            System.out.println(pregunta);
            if (entrada.hasNextInt()) {
                opcion = entrada.nextInt();
                entrada.nextLine(); // Limpiar buffer
                entradaValida = true;
            } else {
                System.out.println("Entrada no válida. Por favor, introduce un número entero.");
                entrada.nextLine(); // Descartar la entrada incorrecta
                entradaValida = false;
            }
        } while (!entradaValida);
        return opcion;
    }

    // Método para convertir un objeto a JSON
    static public String convertirAJson(Object objeto) {
        Gson gson = new Gson();
        return gson.toJson(objeto);
    }

    static public String calcularSha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesAHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 no disponible", e);
        }
    }

    /**
     * Convierte un array de bytes a una cadena hexadecimal.
     */
    static private String bytesAHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

/*   static public boolean validorCorreo(String correo) {
        final String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        Pattern patronDelCorreo = Pattern.compile(regex);
        Matcher matcher = patronDelCorreo.matcher(correo);
        return matcher.matches();

    }*/

    public static boolean validorCorreo(String correo) {
        final String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern patronDelCorreo = Pattern.compile(regex);
        Matcher matcher = patronDelCorreo.matcher(correo);
        return matcher.matches();
    }


    public static int realPosition(int position) {
        return position - 1;
    }


}

