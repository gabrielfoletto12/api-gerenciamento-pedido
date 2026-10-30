package br.com.gabriel.spring_boot_souzadev.exeption;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
