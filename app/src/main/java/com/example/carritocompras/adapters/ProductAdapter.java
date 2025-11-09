package com.example.carritocompras.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.carritocompras.R;
import com.example.carritocompras.models.Product;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;
    private final OnAddToCartListener listener;

    public ProductAdapter(List<Product> productList, OnAddToCartListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.format(Locale.US, "COP %,.0f", product.getPrice()));
        holder.textViewStock.setText("Stock: " + product.getStock());
        holder.buttonAddToCart.setOnClickListener(v -> listener.onAddToCart(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice, textViewStock;
        Button buttonAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewProductName);
            textViewPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewStock = itemView.findViewById(R.id.textViewProductStock);
            buttonAddToCart = itemView.findViewById(R.id.buttonAddToCart);
        }
    }

    public interface OnAddToCartListener {
        void onAddToCart(Product product);
    }
}
