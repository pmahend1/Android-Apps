package com.uncc.prateek.midterm800966178;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praneeth on 2/27/2017.
 */

public class ProductDAO {
    private SQLiteDatabase db;
    public ProductDAO(SQLiteDatabase db){
        this.db = db;
    }
    /*public long save(ProductDAO product){
        ContentValues values = new ContentValues();
        values.put(CartTable.COLUMN_NOTE,product.getNote());
        values.put(CartTable.COLUMN_PRIORITY,product.getPriority());
        values.put(CartTable.COLUMN_TIME,product.getTime());
        values.put(CartTable.COLUMN_STATUS, String.valueOf(product.getStatus()));
        return db.insert(CartTable.TABLE_NAME,null,values);
    }
    public boolean update(ProductDAO product){
        Log.d("update NoteDao ",product.toString());
        ContentValues values = new ContentValues();
        values.put(CartTable.COLUMN_NOTE,product.getNote());
        values.put(CartTable.COLUMN_PRIORITY,product.getPriority());
        values.put(String.valueOf(CartTable.COLUMN_TIME),product.getTime());
        values.put(CartTable.COLUMN_STATUS, String.valueOf(product.getStatus()));
        return db.update(CartTable.TABLE_NAME,values,CartTable.COLUMN_ID + "=?",new String[]{product.get_id()+""})>0;
    }
    public boolean delete(ProductDAO product){
        return db.delete(CartTable.TABLE_NAME,CartTable.COLUMN_ID + "=?", new String[]{product.get_id()+""})>0;
    }
    @TargetApi(16)
    public ProductDAO get(long id){
        ProductDAO product= null;
        Cursor c=db.query(true,CartTable.TABLE_NAME,new String[]{CartTable.COLUMN_ID,CartTable.COLUMN_NOTE,CartTable.COLUMN_PRIORITY,CartTable.COLUMN_TIME,CartTable.COLUMN_STATUS},CartTable.COLUMN_ID + "=?", new String[]{id+""},null,null,null,null,null);
        if (c!=null && c.moveToFirst()) {
            product = buildNoteFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return product;
    }
    public List<Product> getAll() {
        List<Product> notes = new ArrayList<Product>();
        try{
            Cursor c = db.query(CartTable.TABLE_NAME, new String[]{CartTable.COLUMN_ID,CartTable.COLUMN_NOTE,CartTable.COLUMN_PRIORITY,CartTable.COLUMN_TIME,CartTable.COLUMN_STATUS}, null, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    ProductDAO product= buildNoteFromCursor(c);
                    if (product!=null){
                        notes.add(product);
                    }
                }while (c.moveToNext());
                if (!c.isClosed()) {
                    c.close();
                }
            }
        }catch (Exception e){
            Log.d("exc",e.toString());
        }

        return notes;
    }
    private ProductDAO buildNoteFromCursor(Cursor c){
        ProductDAO product = null;
        if (c!=null){
            product = new ProductDAO();
            product.set_id(c.getLong(0));
            product.setNote(c.getString(1));
            product.setPriority(c.getString(2));
            product.setTime(c.getString(3));
            product.setStatus(c.getString(4));
        }
        return product;
    }*/
}
