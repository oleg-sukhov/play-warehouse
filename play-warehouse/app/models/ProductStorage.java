package models;


import exceptions.ProductNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ProductStorage {

    private static final List<Product> storage = initializeStorage();

    public static List<Product> products() {
        return storage;
    }

    private static List<Product> initializeStorage() {
        return IntStream.range(0, 10)
                .mapToObj(index -> Product.builder()
                        .ean(UUID.randomUUID().toString())
                        .name("Product_" + index)
                        .description("Description_" + index)
                        .build())
                .collect(toList());
    }

    public static List<Product> findByName(String name) {
        return storage.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .collect(toList());
    }

    public static Product findByEan(String ean) {
        return storage.stream()
                .filter(product -> product.getEan().equals(ean))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ean));
    }

    public static boolean remove(Product product) {
        return storage.remove(product);
    }

    public static void save() {

    }

}
