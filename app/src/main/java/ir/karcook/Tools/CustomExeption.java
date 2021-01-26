package ir.karcook.Tools;

public class CustomExeption extends Exception {


    public CustomExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExeption(String message) {
        super(message);
    }
}
