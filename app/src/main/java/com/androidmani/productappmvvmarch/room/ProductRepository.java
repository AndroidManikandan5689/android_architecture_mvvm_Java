package com.androidmani.productappmvvmarch.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {

    private ProductDao productDao;
    public LiveData<List<Product>> allProduct;

    public ProductRepository(Application application)
    {
        ProductDatabase productDatabase = ProductDatabase.getInstance(application);
        productDao = productDatabase.productDao();
        allProduct = productDao.getAllProduct();
    }

    public void insertProduct(Product product)
    {
        new InsertProductAsyncTask(productDao).execute(product);
    }

    public void updateProduct(Product product)
    {
        new UpdateProductAsyncTask(productDao).execute(product);
    }

    public void deleteProduct(Product product)
    {
        new DeleteProductAsyncTask(productDao).execute(product);
    }

    public void deleteAllProduct()
    {
        new DeleteAllProductAsyncTask(productDao).execute();
    }

    public LiveData<List<Product>> getAllProduct() {
        return allProduct;
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void>
    {
        private ProductDao productDao;
        public InsertProductAsyncTask(ProductDao productDao)
        {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insertProduct(products[0]);
            return null;
        }
    }


    private static class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void>
    {
        private ProductDao productDao;
        public UpdateProductAsyncTask(ProductDao productDao)
        {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.updateProduct(products[0]);
            return null;
        }
    }


    private static class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void>
    {
        private ProductDao productDao;
        public DeleteProductAsyncTask(ProductDao productDao)
        {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.deleteProduct(products[0]);
            return null;
        }
    }


    private static class DeleteAllProductAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private ProductDao productDao;
        public DeleteAllProductAsyncTask(ProductDao productDao)
        {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.deleteAllProduct();
            return null;
        }
    }
}
