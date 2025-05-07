package TriviaUCAB.models;

public class Usuario {

    public String userName;
    private String password;

    public Usuario(String userName, String password) {
        this.userName = userName;
        this.password = Validator.calcularSha256(password);
    }


    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = Validator.calcularSha256(password);
    }


}

