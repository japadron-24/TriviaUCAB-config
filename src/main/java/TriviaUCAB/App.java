package TriviaUCAB;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

import TriviaUCAB.models.*;
import com.google.gson.reflect.TypeToken;
import com.sun.source.tree.TryTree;

public class App {

    Scanner scanner = new Scanner(System.in);
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    Questions questions = new Questions();
    Usuario usuarioActual;
    String homeFolder = System.getProperty("user.home");

    public static void main(String[] args) {

        System.out.println("Bienvenido a la trivia UCAB configuration!");
        int opcion = 1;
        App aplicacion = new App();
        aplicacion.loadJson();
        aplicacion.loadUsuariosJson();
        do {
            opcion = aplicacion.menuprincipal();
        } while (opcion != 0);
        aplicacion.saveJson();
        try {
            aplicacion.saveUsersJson();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void agregarUsuarios() {
        int cantidadUsuarios = Validator.validarInt("Cuantos  usuario deseas registrar: ", scanner);
        while (cantidadUsuarios > 9999 - listaUsuarios.size() || cantidadUsuarios <= 0) {
            cantidadUsuarios = Validator.validarInt("Exidiste la cantidad de usuarios, El maximos de usuario es " + (6 - listaUsuarios.size()), scanner);
        }
        for (int i = 0; i < cantidadUsuarios; i++) {
            Usuario usuarioNuevo = new Usuario(pedirNombre(), pedirPassword());
            listaUsuarios.add(usuarioNuevo);
        }
    }

    public void agregarPreguntas(Usuario usuario) {
        int cantidadPreguntas = Validator.validarInt("Cuantas preguntas deseas agregar: ", scanner);

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
            int num = Validator.validarInt("", scanner);

            categoriaActual = Category.values()[num - 1];
            questions.addWaitApproved(new Question(question, answer, usuario.userName, categoriaActual));
        }
    }

    public String pedirNombre() {
        System.out.println("Ingrese su nombre de usuario");
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
                    opcion = Validator.validarInt("1) Desea iniciar sesion\n0) Salir", scanner);
                    if (opcion == 0) return -1;
                }
            }
        } else {
            System.out.println("El usuario no se encuentra registrado");
            opcion = Validator.validarInt("1) Desea iniciar sesion\n0) Salir", scanner);
            if (opcion == 0) return -1;
        }
        return opcion;
    }

    public int menuprincipal() {
        int opcion = Validator.validarInt("Desea iniciar sesion o registrar un usuario?\n1) Registrar Usuario.\n2) Iniciar Sesión.\n0) Salir", scanner);

        switch (opcion) {
            case 1:
                System.out.println("Ingrese su usuario");
                usuarioActual = new Usuario(pedirNombre(), pedirPassword());
                listaUsuarios.add(usuarioActual);
                opcion = this.menuDePreguntas();

                break;
            case 2:
                do {
                    opcion = joinSesion();
                } while ((0 != opcion) && (opcion != -1));
                opcion = this.menuDePreguntas();
                break;

            case 0:
                System.out.println("Hasta la próxima");
                return opcion;

            default:
                System.out.println("Opcion no valida");
                break;

        }

        return opcion;
    }

    public int questionSelector(int option) {
        int selected = -1;
        do {
            if (option != 1) questions.visualQuestions(option, usuarioActual);
            else questions.visualAproved(usuarioActual);
            selected = Validator.validarInt("Ingrese el número de la pregunta: ", scanner);

        } while (selected > questions.getSize(option) || selected < 1);

        return selected - 1;
    }

    public int waitingOrDeleted(int option) {
        int selected = -1;
        if (option == 1) {
            selected = questionSelector(option);
        } else if (option == 2) {
            selected = questionSelector(option);
        }
        return selected;
    }

    public int menuDePreguntas() {
        int opcion = 0;
        do {
            opcion = Validator.validarInt("""
                    \\n--- Menú de Preguntas ---
                    1. Aprobar pregunta
                    2. Rechazar pregunta
                    3. Ver todas las preguntas
                    4. Modificar preguntas
                    5. Eliminar preguntas
                    6. Modicar tiempo de la pregunta
                    7. Agregar preguntas
                    0. Cerrar sesion
                    """, scanner);
            switch (opcion) {
                case 1:
                    System.out.println("Aprobar una pregunta.");
                    questions.addApproved(questionSelector(1), usuarioActual);
                    break;
                case 2:
                    System.out.println(" Rechazar una pregunta.");
                    questions.addRejeter(questionSelector(1));
                    break;
                case 3:
                    System.out.println("Visualizacion de todas las preguntas.");

                    System.out.println("Preguntas en espera");
                    questions.visualQuestions(1, usuarioActual);
                    System.out.println("Preguntas aprobadas");
                    questions.visualQuestions(2, usuarioActual);
                    System.out.println("Preguntas rechazadas");
                    questions.visualQuestions(3, usuarioActual);

                    System.out.println("Enter para continuar");
                    scanner.nextLine();
                    // Aquí podríamos agregar la lógica para agregar las  preguntas del JSON o base de datos
                    break;
                case 4:
                    System.out.println("De que lista desea modificar la pregunta.");
                    int lista = (Validator.validarInt("1) En espera.\n2) Rechazadas", scanner));
                    int position = waitingOrDeleted(lista);
                    System.out.println("Modificar una pregunta.");
                    int modification = Validator.validarInt("1)Desea modificar las preguntas\n,2)Desea modificar las categorias\n,3)Desea Cambiar las Respuestas", scanner);
                    String modificar;
                    switch (modification) {
                        case 1:
                            System.out.println("Escriba su modificacion.");
                            modificar = scanner.nextLine();
                            questions.modifyQuestion(position, modificar);
                            break;
                        case 2:
                            int cont = 0;
                            Category categoriaActual;
                            System.out.println("Ingrese la categoria de la pregunta: ");
                            for (Category category : Category.values()) {
                                System.out.println(cont + ") " + category);
                                cont++;
                            }
                            int num = Validator.validarInt("", scanner);

                            categoriaActual = Category.values()[num - 1];
                            questions.modifyCategory(position, categoriaActual);
                            break;
                        case 3:
                            System.out.println("Escriba su modificacion.");
                            modificar = scanner.nextLine();
                            questions.modifyAnswer(position, modificar);
                            break;
                    }
                    break;

                case 5:
                    System.out.println("De que lista desea eliminar la pregunta.");
                    int listaEliminar = Validator.validarInt("1) En espera.\n2) Aprovadas.\n3) Rechazadas", scanner);
                    questions.delete(questionSelector(listaEliminar), listaEliminar);
                    break;
                case 6:
                    questions.setTime();
                    break;
                case 7:
                    agregarPreguntas(usuarioActual);
                    break;

                case 0:
                    System.out.println("Saliendo del menú...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 0);
        return -1;
    }

    public void saveUsersJson() throws IOException {
        String destinyFolder = homeFolder + File.separator + ".config";
        File destinyFolderFile = new File(destinyFolder);
        if (!destinyFolderFile.exists()) {
            boolean created = destinyFolderFile.mkdir();
            if (!created) throw new IOException();

        }
        Gson gson = new Gson();
        String json = gson.toJson(this.listaUsuarios);
        File data = new File(destinyFolder + File.separator + "users.json");

        try (FileWriter writer = new FileWriter(data)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveJson() {
        String destinyFolder = homeFolder + File.separator + ".config";
        File destinyFolderFile = new File(destinyFolder);
        if (!destinyFolderFile.exists()) {
            boolean created = destinyFolderFile.mkdir();
            if (!created) {
                throw new RuntimeException();
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(questions);
        File data = new File(destinyFolder + File.separator + "data.json");

        try (FileWriter writer = new FileWriter(data)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadUsuariosJson() {
        Gson gson = new Gson();
        String destinyFolder = homeFolder + File.separator + ".config";
        File destinyFolderFile = new File(destinyFolder);
        if (!destinyFolderFile.exists()) {
            boolean created = destinyFolderFile.mkdir();
            if (!created) {
                throw new RuntimeException();
            }
        }
        var a = new File(destinyFolderFile + File.separator + "users.json");
        if (!(a.exists())) {
            try {
                boolean created = a.createNewFile();
                if (!created) throw new IOException();
                this.listaUsuarios = new ArrayList<Usuario>();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (FileReader r = new FileReader(destinyFolderFile + File.separator + "users.json")) {
                // Se recomienda usar BufferedReader para mejorar el rendimiento según un post en stack overflow
                BufferedReader bufferedReader = new BufferedReader(r);
                Type listType = new TypeToken<ArrayList<Usuario>>() {
                }.getType();
                listaUsuarios = gson.fromJson(bufferedReader, listType);
            } catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo JSON", e);
            }
        }

    }


    public void loadJson() {
        Gson gson = new Gson();
        String destinyFolder = homeFolder + File.separator + ".config";
        File destinyFolderFile = new File(destinyFolder);
        if (!destinyFolderFile.exists()) {
            boolean created = destinyFolderFile.mkdir();
            if (!created) {
                throw new RuntimeException();
            }
        }
        var a = new File(destinyFolderFile + File.separator + "data.json");
        if (!(a.exists())) {
            try {
                boolean created = a.createNewFile();
                if (!created) throw new IOException();
                this.questions = new Questions();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (FileReader reader = new FileReader(destinyFolderFile + File.separator + "data.json")) {
                // Se recomienda usar BufferedReader para mejorar el rendimiento según un post en stack overflow
                BufferedReader bufferedReader = new BufferedReader(reader);
                questions = gson.fromJson(bufferedReader, Questions.class);
            } catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo JSON", e);
            }
        }
    }
}