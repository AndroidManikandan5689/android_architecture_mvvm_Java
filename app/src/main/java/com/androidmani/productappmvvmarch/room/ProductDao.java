package com.androidmani.productappmvvmarch.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM product_table")
    void deleteAllProduct();

    @Query("SELECT * FROM product_table Order By price DESC")
    LiveData<List<Product>> getAllProduct();
}
