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

public class AddEditProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_ID = "com.androidmani.productappmvvmarch.view.id";
    public static final String EXTRA_PRODUCT_NAME = "com.androidmani.productappmvvmarch.view.name";
    public static final String EXTRA_PRODUCT_PRICE = "com.androidmani.productappmvvmarch.view.price";
    public static final String EXTRA_PRODUCT_DESC = "com.androidmani.productappmvvmarch.view.description";

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

        Intent intent = getIntent();

        editTextProductName = findViewById(R.id.edit_name);
        editTextPrice = findViewById(R.id.edit_price);
        editTextDescription = findViewById(R.id.edit_description);

        if(intent.hasExtra(EXTRA_PRODUCT_ID))
        {
            setTitle("Edit Product");
            editTextProductName.setText(getIntent().getExtras().getString(EXTRA_PRODUCT_NAME));
            editTextPrice.setText(getIntent().getExtras().getString(EXTRA_PRODUCT_PRICE));
            editTextDescription.setText(getIntent().getExtras().getString(EXTRA_PRODUCT_DESC));
        }
        else {
            setTitle("Add Product");
        }

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
        intent.putExtra(EXTRA_PRODUCT_NAME, name);
        intent.putExtra(EXTRA_PRODUCT_PRICE, price);
        intent.putExtra(EXTRA_PRODUCT_DESC, desc);

        Intent intent1 = getIntent();
        if(intent1.hasExtra(EXTRA_PRODUCT_ID))
        {
            intent.putExtra(EXTRA_PRODUCT_ID, intent1.getExtras().getString(EXTRA_PRODUCT_ID));
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
