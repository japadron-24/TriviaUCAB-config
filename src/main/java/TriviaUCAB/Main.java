package TriviaUCAB;

import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

import TriviaUCAB.models.*;

public class Main {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
    Questions questions = new Questions();

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {

            System.out.println("Bienvenido a la trivia UCAB configuration!");

        }
        Main aplicacion = new Main();
        aplicacion.agregarUsuarios();
        aplicacion.agregarPreguntas(aplicacion.listaUsuarios.getFirst());

    }

    public void agregarUsuarios() {
        System.out.println("Cuantos  usuario deseas agregar: ");
        int cantidadUsuarios = scanner.nextInt();
        while (cantidadUsuarios > 6 - listaUsuarios.size() || cantidadUsuarios <= 0) {
            System.out.println("Exidiste la cantidad de usuarios, El maximos de usuario es " + (6 - listaUsuarios.size()));
            cantidadUsuarios = scanner.nextInt();
        }
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario usuarioNuevo = new Usuario(pedirNombre(), pedirPassword());
            listaUsuarios.add(usuarioNuevo);
        }
    }

    public void agregarPreguntas(Usuario usuario) {
        System.out.println("Cuantas preguntas deseas agregar: ");
        int cantidadPreguntas = scanner.nextInt();
        String question;
        String answer;
        Category categoriaactual;
        for (int i = 0; i < cantidadPreguntas; i++) {
            scanner.nextLine();
            System.out.println("Ingrese el nombre del pregunta: ");
            question = scanner.nextLine();
            System.out.println("Ingrese la respuesta de la pregunta: ");
            answer = scanner.nextLine();
            System.out.println("Ingrese la categoria de la pregunta: ");
            for(Category category: Category.values()){
                System.out.println(category);
            }
            int num=scanner.nextInt();
            categoriaactual=Category.values()[num];
            questions.addWaitApproved(new Question(question, answer, usuario.userName, categoriaactual));
        }
    }

    public String pedirNombre() {
        System.out.println("Presione enter para continuar");
        scanner.nextLine();
        System.out.println("Ingrese su usuario");
        String usuario = scanner.nextLine();
        while (Validator.validorCorreo(usuario)) {      //no toma el "@" corregir
            System.out.println("El usuario que ingreso no es valido, por favor ingrese un correo valido");
            usuario = scanner.nextLine();
        }

        return usuario;
    }

    public String verifyPassword() {
        System.out.println("Presione enter para continuar");
        scanner.nextLine();
        System.out.println("Ingrese su password");
        String password = scanner.nextLine();
        while (password.length() != 6) {
            System.out.println("La clave no cumple con los 6 caracteres");
            System.out.println("Ingrese una clave valida");
            password = scanner.nextLine();
        }
        return password;
    }

    //
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


}