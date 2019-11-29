package com.androidmani.productappmvvmarch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidmani.productappmvvmarch.R;
import com.androidmani.productappmvvmarch.room.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Product> products = new ArrayList<>();

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = products.get(position);
        holder.tv_name.setText(product.getName());
        holder.tv_price.setText(String.valueOf(product.getPrice()));
        holder.tv_desc.setText(product.getDescription());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> product)
    {
        this.products = product;
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_price;
        private TextView tv_desc;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_desc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
