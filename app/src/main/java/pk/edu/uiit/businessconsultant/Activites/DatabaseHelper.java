package pk.edu.uiit.businessconsultant.Activites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String database_name="My Database";
    private static final String table_name="SignUp";
    private static final String col_name="SignUp";
    private static final int dp_version=2;
    SQLiteDatabase sqLiteDatabase;
    public DatabaseHelper(Context context){
        super(context,database_name,null,dp_version);
        sqLiteDatabase=this.getReadableDatabase();
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+table_name+"(user_id INTEGER PRIMARY KEY AUTOINCREMENT, user_name VARCHAR,user_email VARCHAR,password VARCHAR,phone VARCHAR)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int Oldversion,int NewVersion){
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate(db);
    }
    public long signup(String name,String email,String pass,String phone){
        ContentValues contentValues=new ContentValues();
        contentValues.put("user_name",name);
        contentValues.put("user_email",email);
        contentValues.put("password",pass);
        contentValues.put("phone",phone);
        long user_data=sqLiteDatabase.insert(table_name,null,contentValues);
        return user_data;
    }
    public Cursor get_register(String name, String email,String pass,String phone){
        Cursor user_data= sqLiteDatabase.rawQuery("SELECT* from "+table_name+"WHERE user_name='"+name+"'"+"AND user_email='" + email + "'" + "AND password='"+pass+"'"+"AND phone='"+phone+"'",null);
        return user_data;
    }
    public boolean login(String name,String password){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor user_data = MyDB.rawQuery("Select * from "+ table_name+" where  user_name=? and password=?",new String[] {name,password});
        if(user_data.getCount()>0)
            return true;
        else
            return false;
    }


}


