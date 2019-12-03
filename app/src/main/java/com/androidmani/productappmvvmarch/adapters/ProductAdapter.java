package com.androidmani.productappmvvmarch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.androidmani.productappmvvmarch.R;
import com.androidmani.productappmvvmarch.room.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductHolder> {

    ProductItemClickListener listener;

    public ProductAdapter() {
        super(DIFF_PRODUCT_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Product> DIFF_PRODUCT_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getPrice() == newItem.getPrice() &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = getItem(position);
        holder.tv_name.setText(product.getName());
        holder.tv_price.setText(String.valueOf(product.getPrice()));
        holder.tv_desc.setText(product.getDescription());

    }


    public Product getProductAt(int position)
    {
        return getItem(position);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.updateProductItem(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface ProductItemClickListener {
        void updateProductItem(Product product);
    }

    public void ItemClickEvent(ProductItemClickListener productItemClickListener)
    {
        this.listener = productItemClickListener;
    }


}
