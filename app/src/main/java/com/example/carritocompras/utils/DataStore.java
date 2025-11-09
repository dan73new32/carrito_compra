package com.example.carritocompras.utils;

import com.example.carritocompras.models.Product;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final List<Product> productList = new ArrayList<>();
    private static final List<Product> cartItems = new ArrayList<>();

    // Bloque estático para inicializar los productos una sola vez
    static {
        productList.add(new Product(1, "Mouse Gamer", 80000, 10));
        productList.add(new Product(2, "Teclado Inalámbrico", 300000, 5));
        productList.add(new Product(3, "Case ATX 3 ventiladores", 500000, 3));
        productList.add(new Product(4, "Audífonos Gamer", 60000, 15));
        productList.add(new Product(5, "Procesador Ryzen 7 5700x", 1200000, 8));
    }

    public static List<Product> getProducts() {
        return productList;
    }

    public static void addToCart(Product product) {
        cartItems.add(product);
        // En una app real, aquí se descontaría el stock
        // product.setStock(product.getStock() - 1);
    }

    public static List<Product> getCartItems() {
        return cartItems;
    }
}
