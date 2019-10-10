package com.example.parcial1germancarvajal.model.driver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.parcial1germancarvajal.model.data.CRUDPremio;
import com.example.parcial1germancarvajal.model.data.CRUDUser;
import com.example.parcial1germancarvajal.model.entity.UserData;

public class DBDriver extends SQLiteOpenHelper {

    private static DBDriver instance;
    public static synchronized DBDriver getInstance(Context c){
        if(instance==null){
            instance=new DBDriver(c);
        }
        return instance;
    }

    public static final String DBNAME="ArithGo";
    public static final int DBVERSION=2;

    //TABLE USER
    public static final String TABLE_USER="user";
    public static final String USER_USERNAME="username";
    public static final String USER_POINTS="points";

    //TABLE TIENDA
    public static final String TABLE_PRIZE="premios";
    public static final String PRIZE_NAME="name";
    public static final String PRIZE_PRICE="price";



    public DBDriver(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //crear tablas
        String CREATE_TABLE_USER="CREATE TABLE $TABLE($USERNAME TEXT PRIMARY KEY, $POINTS INTEGER)";
        CREATE_TABLE_USER=CREATE_TABLE_USER
                .replace("$TABLE", TABLE_USER)
                .replace("$USERNAME", USER_USERNAME)
                .replace("$POINTS", USER_POINTS);

        String CREATE_TABLE_PRIZE="CREATE TABLE $TABLE($NAME TEXT PRIMARY KEY, $PRICE INTEGER)";
        CREATE_TABLE_PRIZE=CREATE_TABLE_PRIZE
                .replace("$TABLE",TABLE_PRIZE)
        .replace("$NAME",PRIZE_NAME)
        .replace("$PRICE",PRIZE_PRICE);

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PRIZE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRIZE);
        onCreate(db);


    }
}

