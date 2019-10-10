package com.example.parcial1germancarvajal.model.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.parcial1germancarvajal.app.ArithGoApp;
import com.example.parcial1germancarvajal.model.driver.DBDriver;
import com.example.parcial1germancarvajal.model.entity.Premio;

import java.util.ArrayList;

public class CRUDPremio {

    public static void insertarPremio(Premio premio){
        DBDriver driver=DBDriver.getInstance(ArithGoApp.getContext());
        SQLiteDatabase db=driver.getWritableDatabase();
        String query= "INSERT INTO $TABLE ($NAME,$PRICE) VALUES('$NOMBRE',$PRECIO)";
        query=query.replace("$TABLE", DBDriver.TABLE_PRIZE)
                .replace("$NAME",DBDriver.PRIZE_NAME)
                .replace("$PRICE",DBDriver.PRIZE_PRICE)
                .replace("$NOMBRE",premio.getName())
                .replace("$PRECIO",premio.getPrice()+"");
        db.execSQL(query);
    }

    public static void insertarTodosLosPremios(){
        Premio p1=new Premio(20,"Lapicero");
        Premio p2=new Premio(30,"Cuaderno");
        Premio p3=new Premio(40,"Libreta");
        Premio p4=new Premio(80,"Camiseta");
        Premio p5=new Premio(100,"Saco");
        CRUDPremio.insertarPremio(p1);
        CRUDPremio.insertarPremio(p2);
        CRUDPremio.insertarPremio(p3);
        CRUDPremio.insertarPremio(p4);
        CRUDPremio.insertarPremio(p5);
    }

    public static ArrayList<Premio> darPremios(){
        DBDriver driver=DBDriver.getInstance(ArithGoApp.getContext());
        SQLiteDatabase db=driver.getWritableDatabase();
        String query="SELECT * FROM "+DBDriver.TABLE_PRIZE;
        ArrayList<Premio> premios=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                premios.add(new Premio(cursor.getInt(cursor.getColumnIndex(DBDriver.PRIZE_PRICE)),cursor.getString(cursor.getColumnIndex(DBDriver.PRIZE_NAME))));
            }
            while (cursor.moveToNext());
        }
        return premios;

    }
}
