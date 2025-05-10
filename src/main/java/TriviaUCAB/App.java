package TriviaUCAB;

import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

import TriviaUCAB.models.*;

public class App {

    Scanner scanner = new Scanner(System.in);
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    Questions questions = new Questions();
    Usuario usuarioActual;

    public static void main(String[] args) {

        System.out.println("Bienvenido a la trivia UCAB configuration!");
        int opcion = 1;
        App aplicacion = new App();
        while (opcion != 0) {
            opcion = aplicacion.menuprincipal();
        }
    }

    public void agregarUsuarios() {
        System.out.println("Cuantos  usuario deseas registrar: ");
        int cantidadUsuarios = scanner.nextInt();
        scanner.nextLine();
        while (cantidadUsuarios > 9999 - listaUsuarios.size() || cantidadUsuarios <= 0) {
            System.out.println("Exidiste la cantidad de usuarios, El maximos de usuario es " + (6 - listaUsuarios.size()));
            cantidadUsuarios = scanner.nextInt();
            scanner.nextLine();
        }
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario usuarioNuevo = new Usuario(pedirNombre(), pedirPassword());
            listaUsuarios.add(usuarioNuevo);
        }
    }

    public void agregarPreguntas(Usuario usuario) {
        System.out.println("Cuantas preguntas deseas agregar: ");
        int cantidadPreguntas = scanner.nextInt();
        scanner.nextLine();

        String question;
        String answer;
        Category categoriaActual;
        int cont = 1;
        for (int i = 0; i < cantidadPreguntas; i++) {
            cont = 1;
            System.out.println("Ingrese el nombre de la pregunta: ");
            question = scanner.nextLine();
            System.out.println("Ingrese la respuesta de la pregunta: ");
            answer = scanner.nextLine();
            System.out.println("Ingrese la categoria de la pregunta: ");
            for (Category category : Category.values()) {
                System.out.println(cont + ") " + category);
                cont++;
            }
            int num = scanner.nextInt();
            scanner.nextLine();
            categoriaActual = Category.values()[num - 1];
            questions.addWaitApproved(new Question(question, answer, usuario.userName, categoriaActual));
        }
    }

    public String pedirNombre() {
        System.out.println("Ingrese su usuario");
        String usuario = scanner.nextLine();
        while (!Validator.validorCorreo(usuario)) {      //no toma el "@" corregir
            System.out.println("El usuario que ingreso no es valido, por favor ingrese un correo valido");
            usuario = scanner.nextLine();
        }

        return usuario;
    }

    public String verifyPassword() {
        System.out.println("Ingrese su password");
        String password = scanner.nextLine();
        while (password.length() != 6) {
            System.out.println("La clave no cumple con los 6 caracteres");
            System.out.println("Ingrese una clave valida");
            password = scanner.nextLine();
        }
        return password;
    }

    public String pedirPassword() {
        String password = verifyPassword();
        String repeatPassword;
        System.out.println("Repita la clave con su password");
        repeatPassword = scanner.nextLine();
        while (!Objects.equals(repeatPassword, password)) {
            System.out.println("La clave no es identica a la anterior");
            System.out.println("Por favor ingrese la clave");
            repeatPassword = scanner.nextLine();
        }
        return password;
    }

    public int joinSesion() {
        int opcion = 0;
        System.out.println("Inciar sesión");
        String searchName = pedirNombre();
        ArrayList<Usuario> usuariosFiltrados = this.listaUsuarios.stream()
                .filter(usuario -> searchName.equals(usuario.getUserName()))
                .collect(Collectors.toCollection(ArrayList<Usuario>::new));
        if (!usuariosFiltrados.isEmpty()) {
            String password = this.pedirPassword();
            for (Usuario usuario : usuariosFiltrados) {
                if (usuario.getPassword().equals(Validator.calcularSha256(password))) {
                    this.usuarioActual = usuario;
                    opcion = 0;
                } else {
                    System.out.println("La contraseña ingresada no es correcta");
                    System.out.println("1) Desea iniciar sesion\n0) Salir");
                    opcion = scanner.nextInt();
                }
            }
        } else {
            System.out.println("La contraseña ingresada no es correcta");
            System.out.println("1) Desea iniciar sesion\n0) Salir");
            opcion = scanner.nextInt();
        }
        return opcion;
    }

    public int menuprincipal() {
        System.out.println("Desea iniciar sesion o registrar un usuario?\n1) Iniciar Sesión.\n2) Registrar Usuario.\n0) Salir");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese su usuario");
                usuarioActual = new Usuario(pedirNombre(), pedirPassword());
                listaUsuarios.add(usuarioActual);
                break;
            case 2:
                do {
                    opcion = joinSesion();
                } while (0 != opcion);
                break;

            case 0:
                System.out.println("Hasta la próxima");
                return opcion;

            default:
                System.out.println("Opcion no valida");
                break;

        }
        do {
        opcion  =menuDePreguntas();
        } while (0 != opcion);
        return opcion;
    }

    public int menuDePreguntas() {
        int opcion=0;
        //crear el nuevo menu
        return opcion;
    }
}