package TriviaUCAB.models;
import java.util.Scanner;

public class Usuario {

    public String userName;
    private String password;

    public Usuario(String userName, String password) {
        this.userName = userName;
        this.password = password;

    }

    public void setUserName(String userName) {
        this.userName=userName;
    }

    public void setPassword(String password) {
        this.password=Validator.calcularSha256(password);
    }


}

