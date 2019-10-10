package com.example.parcial1germancarvajal.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.parcial1germancarvajal.app.ArithGoApp;
import com.example.parcial1germancarvajal.model.driver.DBDriver;
import com.example.parcial1germancarvajal.model.entity.UserData;

public class CRUDUser {

    public static final String masterUser="masterUser";

    public static void cambiarPuntajeMU(UserData userData){
        DBDriver driver=DBDriver.getInstance(ArithGoApp.getContext());
        SQLiteDatabase db=driver.getWritableDatabase();
        String query= "UPDATE $TABLE SET $COLUMN = $DATA WHERE $SELECTOR='$CONDITION'";
        query=query.replace("$TABLE",DBDriver.TABLE_USER)
                .replace("$COLUMN",DBDriver.USER_POINTS)
                .replace("$DATA",userData.getPoints()+"")
                .replace("$SELECTOR",DBDriver.USER_USERNAME)
                .replace("$CONDITION",userData.getUsername());
        db.execSQL(query);
    }

    public static void insertarUsuario(UserData userData){
        DBDriver driver=DBDriver.getInstance(ArithGoApp.getContext());
        SQLiteDatabase db=driver.getWritableDatabase();
        String query="INSERT INTO $TABLE ($USERNAME,$PUNTAJES) VALUES('$USERDATA',$POINTS)";
        query=query.replace("$TABLE",DBDriver.TABLE_USER)
                .replace("$USERNAME",DBDriver.USER_USERNAME)
                .replace("$PUNTAJES",DBDriver.USER_POINTS)
                .replace("$USERDATA",userData.getUsername())
                .replace("$POINTS",userData.getPoints()+"");
        db.execSQL(query);
    }

    public static UserData getUser(String user){
        DBDriver driver=DBDriver.getInstance(ArithGoApp.getContext());
        SQLiteDatabase db=driver.getReadableDatabase();
        String query="SELECT * FROM $TABLE WHERE $USERNAME='"+user+"'";
        query=query.replace("$TABLE",DBDriver.TABLE_USER).replace("$USERNAME",DBDriver.USER_USERNAME);
        Cursor cursor=db.rawQuery(query,null);
        UserData ud=new UserData(null);
        if(cursor.moveToFirst()){
            ud.setUsername(cursor.getString(cursor.getColumnIndex(DBDriver.USER_USERNAME)));
            ud.setPoints(cursor.getInt(cursor.getColumnIndex(DBDriver.USER_POINTS)));
        }
        return ud;
    }

}
