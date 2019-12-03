package com.androidmani.productappmvvmarch.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int PRODUCT_ADD_REQUEST_CODE = 1;
    public static final int PRODUCT_EDIT_REQUEST_CODE = 2;

    ProductViewModel productViewModel;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);


        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.submitList(products);
            }
        });

        productAdapter.ItemClickEvent(new ProductAdapter.ProductItemClickListener() {
            @Override
            public void updateProductItem(Product product) {
                Intent editProduct = new Intent(MainActivity.this, AddEditProductActivity.class);
                editProduct.putExtra(AddEditProductActivity.EXTRA_PRODUCT_ID, product.getId());
                editProduct.putExtra(AddEditProductActivity.EXTRA_PRODUCT_NAME, product.getName());
                editProduct.putExtra(AddEditProductActivity.EXTRA_PRODUCT_PRICE, String.valueOf(product.getPrice()));
                editProduct.putExtra(AddEditProductActivity.EXTRA_PRODUCT_DESC, product.getDescription());
                startActivityForResult(editProduct, PRODUCT_EDIT_REQUEST_CODE);
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

                    Product product = productAdapter.getProductAt(viewHolder.getAdapterPosition());
                    productViewModel.deleteProduct(product);
                    Toast.makeText(MainActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_note) {
            startActivityForResult(new Intent(MainActivity.this, AddEditProductActivity.class), PRODUCT_ADD_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PRODUCT_ADD_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            String name = data.getStringExtra(AddEditProductActivity.EXTRA_PRODUCT_NAME);
            String price = data.getStringExtra(AddEditProductActivity.EXTRA_PRODUCT_PRICE);
            String desc = data.getStringExtra(AddEditProductActivity.EXTRA_PRODUCT_DESC);

            Product product = new Product(name, Integer.valueOf(price), desc);
            productViewModel.insertProduct(product);
            Toast.makeText(this, "Product saved...", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == PRODUCT_EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            String name = data.getStringExtra(AddEditProductActivity.EXTRA_PRODUCT_NAME);
            String price = data.getStringExtra(AddEditProductActivity.EXTRA_PRODUCT_PRICE);
            String desc = data.getStringExtra(AddEditProductActivity.EXTRA_PRODUCT_DESC);

            String id = data.getStringExtra(AddEditProductActivity.EXTRA_PRODUCT_ID);
            if(id != null) {
                if (Integer.parseInt(id) == -1) {
                    Toast.makeText(this, "Product not updated", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            else
            {
                return;
            }

            Product product = new Product(name, Integer.valueOf(price), desc);
            product.setId(Integer.parseInt(id));
            productViewModel.updateProduct(product);
            Toast.makeText(this, "Product updated...", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Product not saved...", Toast.LENGTH_SHORT).show();
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
                productAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Deleted all product", Toast.LENGTH_SHORT).show();
                return true;
                default:
            return super.onOptionsItemSelected(item);
        }
    }
}
