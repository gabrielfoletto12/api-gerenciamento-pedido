package br.com.gabriel.spring_boot_souzadev.exeption;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
