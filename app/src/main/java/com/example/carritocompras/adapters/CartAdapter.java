package com.example.carritocompras.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carritocompras.R;
import com.example.carritocompras.models.CartItem;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItem> cartItems;
    private final CartListener listener;

    public CartAdapter(List<CartItem> cartItems, CartListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usa un nuevo diseño XML para la fila del carrito: 'item_cart.xml'
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.textViewName.setText(cartItem.getProduct().getName());
        holder.textViewPrice.setText(String.format(Locale.US, "COP %,.0f", cartItem.getProduct().getPrice()));
        holder.textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Programar los botones
        holder.buttonIncrease.setOnClickListener(v -> {
            listener.onUpdateQuantity(cartItem, cartItem.getQuantity() + 1);
        });

        holder.buttonDecrease.setOnClickListener(v -> {
            // Solo disminuir si la cantidad es mayor que 0
            if (cartItem.getQuantity() > 1) {
                listener.onUpdateQuantity(cartItem, cartItem.getQuantity() - 1);
            } else {
                // Si la cantidad es 1 y se presiona "-", se elimina el ítem.
                listener.onRemoveItem(cartItem);
            }
        });

        holder.buttonRemove.setOnClickListener(v -> {
            listener.onRemoveItem(cartItem);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // Clase interna para la vista de cada fila
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice, textViewQuantity;
        Button buttonIncrease, buttonDecrease, buttonRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewCartItemName);
            textViewPrice = itemView.findViewById(R.id.textViewCartItemPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewCartItemQuantity);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }

    // Interfaz para comunicar las acciones (CRUD) a la CartActivity
    public interface CartListener {
        void onUpdateQuantity(CartItem item, int newQuantity);
        void onRemoveItem(CartItem item);
    }
}
