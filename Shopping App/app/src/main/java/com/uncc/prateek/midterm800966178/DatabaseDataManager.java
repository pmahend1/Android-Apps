package com.uncc.prateek.midterm800966178;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;


public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private ProductDAO productDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext= mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db= dbOpenHelper.getWritableDatabase();

        productDAO= new ProductDAO(db);
    }
    public void close(){
        if (db!=null){
            db.close();
        }
    }
   /* public ProductDAO getNoteDAO(){
        return this.productDAO;
    }
    public long saveNote(Product product){
        return this.productDAO.save(product);
    }
    public boolean updateNote(Product product){
        return this.productDAO.update(product);
    }
    public boolean deleteNote(Product product){
        return this.productDAO.delete(product);
    }
    public Product getNote(long id){
        return this.productDAO.get(id);
    }
    public List<Product> getAllNotes(){
        return this.productDAO.getAll();
    }*/



}
