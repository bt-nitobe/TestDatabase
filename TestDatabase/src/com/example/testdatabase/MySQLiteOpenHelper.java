package com.example.testdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	static final String DB = "sqlite_sample.db";
	static final int DB_VERSION = 1;
//	static final String CREATE_TABLE = "create table mytable ( _id integer primary key autoincrement, name text not null, age integer );";
	static final String DROP_TABLE = "drop table mytable;";

	/**
	 * コンストラクタ
	 * 
	 * */
    public MySQLiteOpenHelper(Context c) {
        super(c, DB, null, DB_VERSION);
    }
    
    /**
     * データベース作成<br>
     * （初回のみ呼び出し）
     * */
    public void onCreate(SQLiteDatabase db) {
    	// 
    	db.execSQL("create table mytable (" +
    				" _id INTEGER primary key autoincrement," +
    				" name TEXT not null," +
    				" age INTEGER," +
    				" regist_date TEXT" +
    				" );"
    			);
    	
//        db.execSQL(CREATE_TABLE);
    }
    
    /**
     * データベース更新<br>
     * 
     * */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}