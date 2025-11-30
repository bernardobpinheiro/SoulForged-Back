package com.soulforged.common.exceptions;

// Uma exceção de Runtime é suficiente para a maioria dos casos de APIs REST.
public class ResourceNotFoundException extends RuntimeException {

    // Construtor que aceita uma mensagem de erro
    public ResourceNotFoundException(String message) {
        // Passa a mensagem para o construtor da classe pai (RuntimeException)
        super(message);
    }

    // Você pode adicionar outros construtores, se necessário
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}