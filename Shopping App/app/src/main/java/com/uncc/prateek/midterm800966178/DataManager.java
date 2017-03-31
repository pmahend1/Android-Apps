package com.uncc.prateek.midterm800966178;
/*
        a. Assignment : InClass 7
        b. File Name. : MainActivity.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {
	Context mContext;
	DatabaseHelper dbOpenHelper;
	SQLiteDatabase db;
	ProductDAO dao;
	
	public DataManager(Context mContext){
		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelper(mContext);
		db = dbOpenHelper.getWritableDatabase();
		dbOpenHelper.onCreate(db);
		dao = new ProductDAO(db);
	}
	
	/*public void close(){
		db.close();
	}
	
	public long saveNote(Product note){
		return dao.save(note);
	}
	
	public boolean updateNote(Product note){
		return dao.update(note);
	}
	
	public boolean deleteNote(Product note){
		return dao.delete(note);
	}
	
	public Product getNote(long id){
		return dao.get(id);
	}
	
	public List<Product> getAllNotes(){
		return dao.getAll();
	}*/
}
