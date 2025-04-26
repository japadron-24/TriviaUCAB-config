package TriviaUCAB;

import java.util.Scanner;
import java.util.ArrayList;

import TriviaUCAB.models.Usuario;
import TriviaUCAB.models.Validator;

public class Main {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {

            System.out.println("Bienvenido a la trivia UCAB configuration!");

        }
        Main aplicacion = new Main();
        aplicacion.agregarUsuarios();

    }

    public void agregarUsuarios() {
        System.out.println("Cuantos  usuario deseas agregar: ");
        int cantidadUsuarios = scanner.nextInt();
        while (cantidadUsuarios > 6 - listaUsuarios.size() || cantidadUsuarios <= 0) {
            System.out.println("Exidiste la cantidad de usuarios, El maximos de usuario es "+(6-listaUsuarios.size()));
            cantidadUsuarios = scanner.nextInt();
        }
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario usuarioNuevo= new Usuario(pedirNombre(), pedirPassword());
            listaUsuarios.add(usuarioNuevo);
        }
    }

    public String pedirNombre() {
        System.out.println("Presione enter para continuar");
        scanner.nextLine();
        System.out.println("Ingrese su usuario");
        String usuario = scanner.nextLine();
        while (Validator.validorCorreo(usuario)) {
            System.out.println("El usuario que ingreso no es valido, por favor ingrese un correo valido");
            usuario = scanner.nextLine();
        }

        return usuario;
    }

    public String pedirPassword() {
        System.out.println("Presione enter para continuar");
        scanner.nextLine();
        System.out.println("Ingrese su password");
        String password = scanner.nextLine();
        while (password.length() != 6) {
            System.out.println("La clave no cumple con los 6 caracteres");
            System.out.println("Ingrese clave valida");
            password = scanner.nextLine();
        }
        return password;
    }


}