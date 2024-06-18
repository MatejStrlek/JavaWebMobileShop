package hr.algebra.javawebmobileshop.exceptions;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(String message) {
        super(message);
    }
}

