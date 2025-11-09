package com.example.carritocompras;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.carritocompras.R;
import com.example.carritocompras.adapters.CartAdapter;
import com.example.carritocompras.models.CartItem;
import com.example.carritocompras.models.CartItemDetail;
import com.example.carritocompras.models.Product;
import com.example.carritocompras.utils.DataStore;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartActionClickListener {

    private TextView textViewTotal;
    private CartAdapter adapter;
    private List<CartItemDetail> currentCartDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView recyclerView = findViewById(R.id.textViewCartTitle);
        textViewTotal = findViewById(R.id.textViewTotal);
        Button buttonCheckout = findViewById(R.id.buttonCheckout);

        setupRecyclerView(recyclerView);
        loadCartItems();

        buttonCheckout.setOnClickListener(v -> {
            if (adapter.getItemCount() > 0) {
                checkoutProcess();
            } else {
                Toast.makeText(this, "El carrito está vacío.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new CartAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // SIMULACIÓN DE LECTURA (R)
    private void loadCartItems() {
        currentCartDetails.clear();

        for (CartItem item : DataStore.cart) {
            Product product = DataStore.getProductById(item.getProductId());
            if (product != null) {
                currentCartDetails.add(new CartItemDetail(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getStock(),
                        item.getQuantity()
                ));
            }
        }

        adapter.updateList(currentCartDetails);
        updateTotalDisplay();
    }

    // SIMULACIÓN DE ACTUALIZACIÓN (U)
    @Override
    public void onQuantityChanged(final CartItemDetail item, final int newQuantity) {
        if (newQuantity < 1) {
            onRemoveClicked(item); // Si la cantidad es 0 o menos, borramos
            return;
        }

        if (newQuantity > item.getStockAvailable()) {
            Toast.makeText(this, "Máximo stock: " + item.getStockAvailable(), Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualiza el DataStore y recarga
        DataStore.addToCart(item.getProductId(), newQuantity - item.getQuantity());
        loadCartItems();
    }

    // SIMULACIÓN DE BORRADO (D)
    @Override
    public void onRemoveClicked(final CartItemDetail item) {
        DataStore.removeItemFromCart(item.getProductId());
        Toast.makeText(this, item.getName() + " eliminado.", Toast.LENGTH_SHORT).show();
        loadCartItems();
    }

    private void updateTotalDisplay() {
        double total = adapter.calculateTotal();
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        format.setMaximumFractionDigits(0);
        textViewTotal.setText("Total: " + format.format(total));
    }

    // SIMULACIÓN DE Checkout (U de Stock y D del Carrito)
    private void checkoutProcess() {
        // 1. Actualizar Stock (U)
        for (CartItemDetail item : currentCartDetails) {
            Product product = DataStore.getProductById(item.getProductId());
            if (product != null) {
                int newStock = product.getStock() - item.getQuantity();
                product.setStock(newStock);
            }
        }
        // 2. Vaciar el carrito (D)
        DataStore.clearCart();

        Toast.makeText(this, "Compra finalizada y stock actualizado.", Toast.LENGTH_LONG).show();
        finish();
    }
}