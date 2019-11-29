package com.androidmani.productappmvvmarch.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase instance;
    public abstract ProductDao productDao();

    public static synchronized ProductDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateProductData(instance).execute();
        }
    };

    private static class PopulateProductData extends AsyncTask<Void, Void, Void>
    {

        ProductDao productDao;
        public PopulateProductData(ProductDatabase productDatabase){
            this.productDao = productDatabase.productDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.insertProduct(new Product("Smart phone", 1000, "Mobile"));
            productDao.insertProduct(new Product("Television", 10000, "TV"));
            productDao.insertProduct(new Product("Smart phone", 1000, "Mobile"));
            return null;
        }
    }
}
