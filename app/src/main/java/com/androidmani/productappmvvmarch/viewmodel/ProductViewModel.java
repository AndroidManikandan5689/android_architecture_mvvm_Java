package com.androidmani.productappmvvmarch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.androidmani.productappmvvmarch.room.Product;
import com.androidmani.productappmvvmarch.room.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> allProduct;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allProduct = productRepository.getAllProduct();
    }

    public void insertProduct(Product product)
    {
        productRepository.insertProduct(product);
    }

    public void updateProduct(Product product)
    {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(Product product)
    {
        productRepository.deleteProduct(product);
    }

    public void deleteAllProduct() {
        productRepository.deleteAllProduct();
    }

    public LiveData<List<Product>> getAllProduct(){
        return allProduct;
    }
}
