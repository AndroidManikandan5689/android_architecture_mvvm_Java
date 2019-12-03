package com.androidmani.productappmvvmarch.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidmani.productappmvvmarch.R;
import com.androidmani.productappmvvmarch.adapters.ProductAdapter;
import com.androidmani.productappmvvmarch.room.Product;
import com.androidmani.productappmvvmarch.viewmodel.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int PRODUCT_REQUEST_CODE = 1;

    ProductViewModel productViewModel;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);


        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setProducts(products);
            }
        });


        findViewById(R.id.add_note).setOnClickListener(this);

        deleteItem();
    }

    private void deleteItem() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(viewHolder.getAdapterPosition() != 0) {
                    Product product = productAdapter.getProductAt(viewHolder.getAdapterPosition());
                    productViewModel.deleteProduct(product);
                    productAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                }

            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_note:
                startActivityForResult(new Intent(MainActivity.this, AddProductActivity.class), PRODUCT_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PRODUCT_REQUEST_CODE && resultCode == RESULT_OK)
        {
            String name = data.getStringExtra(AddProductActivity.KEY_PRODUCT_NAME);
            int price = data.getIntExtra(AddProductActivity.KEY_PRODUCT_PRICE, 1);
            String desc = data.getStringExtra(AddProductActivity.KEY_PRODUCT_DESC);

            Product product = new Product(name, price, desc);
            productViewModel.insertProduct(product);
            Toast.makeText(this, "Product saved...", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_all_menu:
                productViewModel.deleteAllProduct();
                Toast.makeText(this, "Deleted all product", Toast.LENGTH_SHORT).show();
                return true;
                default:
            return super.onOptionsItemSelected(item);
        }
    }
}
