package exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String ean) {
        super("Can't find product with ean=" + ean);
    }
}
