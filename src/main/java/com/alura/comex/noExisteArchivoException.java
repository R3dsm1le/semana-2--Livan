package com.alura.comex;

public class noExisteArchivoException extends RuntimeException {

    public noExisteArchivoException(String errorMessage) {
        super(errorMessage);
    }

}
