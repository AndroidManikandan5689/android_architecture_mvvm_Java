package com.androidmani.productappmvvmarch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.androidmani.productappmvvmarch.adapters.ProductAdapter;
import com.androidmani.productappmvvmarch.room.Product;
import com.androidmani.productappmvvmarch.viewmodel.ProductViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ProductAdapter productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);


        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setProducts(products);
            }
        });
    }
}
