package io.vukotic.flightadvisor.error.exception;

public class CorruptedFileException extends RuntimeException {
    public CorruptedFileException() {
        super("File is corrupted");
    }
}