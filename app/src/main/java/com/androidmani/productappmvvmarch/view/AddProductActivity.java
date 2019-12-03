package com.androidmani.productappmvvmarch.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.androidmani.productappmvvmarch.R;
import com.androidmani.productappmvvmarch.room.Product;

public class AddProductActivity extends AppCompatActivity {

    public static final String KEY_PRODUCT_NAME = "name";
    public static final String KEY_PRODUCT_PRICE = "price";
    public static final String KEY_PRODUCT_DESC = "description";

    EditText editTextProductName;
    EditText editTextPrice;
    EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initView();


    }

    private void initView() {

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Product");

        editTextProductName = findViewById(R.id.edit_name);
        editTextPrice = findViewById(R.id.edit_price);
        editTextDescription = findViewById(R.id.edit_price);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.product_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_product_menu:
                saveProduct();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void saveProduct() {
        String name = editTextProductName.getText().toString().trim();
        String price = editTextPrice.getText().toString();
        String desc = editTextDescription.getText().toString().trim();

        if(name.isEmpty() || desc.isEmpty())
        {
            Toast.makeText(this, "Please enter product title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(KEY_PRODUCT_NAME, name);
        intent.putExtra(KEY_PRODUCT_PRICE, price);
        intent.putExtra(KEY_PRODUCT_DESC, desc);
        setResult(RESULT_OK, intent);
        finish();
    }
}
