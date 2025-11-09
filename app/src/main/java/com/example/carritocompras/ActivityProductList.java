package com.example.carritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// =================== IMPORTS IMPORTANTES ===================
// Asegúrate de que las rutas a estas clases sean correctas en tu proyecto.
// Este es el import de TU clase Product, no la de Google.
import com.example.carritocompras.models.Product;
import com.example.carritocompras.adapters.ProductAdapter;
import com.example.carritocompras.utils.DataStore;
// =========================================================

/**
 * Esta Actividad muestra la lista de productos disponibles.
 * Implementa la interfaz OnAddToCartListener para poder recibir los clics
 * del botón "Añadir" que están dentro del adaptador.
 */
 class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnAddToCartListener {

    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // --- 1. Inicializar el RecyclerView ---
        // Buscamos el RecyclerView en nuestro XML por su ID.
        // ¡IMPORTANTE! Tu RecyclerView en el archivo 'activity_product_list.xml'
        // debe tener el id 'recyclerViewProducts' para que esto funcione.
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts); // ID CORREGIDO
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        // --- 2. Preparar el Adaptador y los Datos ---
        // Creamos el adaptador, le pasamos la lista de productos de nuestro "almacén" (DataStore)
        // y le decimos que esta misma actividad (this) se encargará de gestionar los clics
        // del botón "Añadir" a través de la función onAddToCart.
        productAdapter = new ProductAdapter(DataStore.getProducts(), this);
        recyclerViewProducts.setAdapter(productAdapter);

        // --- 3. Configurar el botón para ir al carrito ---
        // Este botón es para navegar a la pantalla del carrito.
        // Asegúrate de tener un botón con el id 'buttonGoToCart' en tu 'activity_product_list.xml'.
        Button btnGoToCart = findViewById(R.id.buttonGoToCart);
        if (btnGoToCart != null) {
            btnGoToCart.setOnClickListener(v -> {
                startActivity(new Intent(ProductListActivity.this, CartActivity.class));
            });
        }
    }

    /**
     * Esta función es obligatoria porque la clase "implements ProductAdapter.OnAddToCartListener".
     * Se ejecuta automáticamente cuando haces clic en el botón "Añadir" de cualquier producto en la lista.
     * Aquí va la lógica para añadir el producto al carrito (la 'C' de Crear en el CRUD del carrito).
     * @param product El objeto del producto en el que se hizo clic.
     */
    @Override
    public void onAddToCart(Product product) {
        // Verificamos si hay stock disponible.
        if (product.getStock() <= 0) {
            Toast.makeText(this, "Producto agotado", Toast.LENGTH_SHORT).show();
            return; // No continuamos si no hay stock.
        }

        // Lógica para añadir al carrito (usamos nuestro DataStore como simulación de base de datos).
        DataStore.addToCart(product);
        Toast.makeText(this, product.getName() + " añadido al carrito.", Toast.LENGTH_SHORT).show();

        // Opcional: Si quisieras que el stock visible se actualice en la lista al añadir,
        // aquí es donde notificarías al adaptador que los datos han cambiado.
        // productAdapter.notifyDataSetChanged();
    }
}