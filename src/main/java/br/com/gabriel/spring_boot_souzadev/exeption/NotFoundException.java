package br.com.gabriel.spring_boot_souzadev.exeption;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
