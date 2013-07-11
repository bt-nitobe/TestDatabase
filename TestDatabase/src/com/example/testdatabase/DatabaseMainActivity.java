package com.example.testdatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DatabaseMainActivity extends Activity implements OnClickListener, OnFocusChangeListener{
	
	
	protected SQLiteDatabase _mydb;
	
	protected Button _submiBtn;
	protected Button _selectBtn;
	
	protected EditText _nameEdit;
	protected EditText _ageEdit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_main);
		
		Logger.startLogger(this, "TEST");
		
		MySQLiteOpenHelper hlpr = new MySQLiteOpenHelper(getApplicationContext());
		_mydb = hlpr.getWritableDatabase();
		
		_submiBtn = (Button)findViewById(R.id.SubmitButton);
		_submiBtn.setOnClickListener(this);
		
		_selectBtn = (Button)findViewById(R.id.SelectButton);
		_selectBtn.setOnClickListener(this);
		
		_nameEdit = (EditText)findViewById(R.id.NameEditText);
		_nameEdit.setOnFocusChangeListener(this);
		
		_ageEdit = (EditText)findViewById(R.id.AgeEditText);
		_ageEdit.setOnFocusChangeListener(this);

		
//		checkAnything();
		
	}
	
	/**
	 * 
	 * */
	protected void checkAnything() {
		Logger.d(this, "checkAnything", " ");
		
		try {
			Calendar cal01 = Calendar.getInstance();			// (1)オブジェクトの生成
			Logger.w(this, "checkAnything", "cal01 = " + cal01);
			
			Date date01 = cal01.getTime();
			Logger.w(this, "checkAnything", "date = " + date01);
			
			// 
			Thread.sleep(1500);
			long now = System.currentTimeMillis();				// 現在時刻を取得する
			Date data02 = new Date(now);
			Logger.w(this, "checkAnything", "date = " + data02);

			
			Calendar cal03 = Calendar.getInstance();
			cal03.set(2013, 6, 6);
			Logger.w(this, "checkAnything", "cal03 = " + cal03);
			

			cal03.roll(Calendar.DATE, 1);
			Logger.w(this, "checkAnything", "cal03 = " + cal03);
			
			
			
			
		}catch (Exception e) {
			
			Logger.e(this, "checkAnything", "" + e);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.database_main, menu);
		return true;
	}

	
	@Override
	public void onClick(View view) {
		
		
		if(view.getId() == R.id.SubmitButton) {
			Logger.d(this, "onClick", "SubmitButton");
			// submitボタン
		
			String inputName = _nameEdit.getText().toString();
			
			if(inputName != null && inputName.length() > 0) {
				// データ列に入力値を設定し、インサートする
//				ContentValues values = new ContentValues();
//				values.put("name", inputName);
//				values.put("age", _ageEdit.getText().toString());
//				long id = _mydb.insert("mytable", null, values);
//				Logger.w(this, "onClick", "insert id = " + id);

				try{
					// 挿入（insert）文の別表記はこんな感じ
//					_mydb.execSQL("insert into mytable(name, age, regist_date) values ('" + inputName + "', " + _ageEdit.getText().toString() + ", datetime('now', 'localtime') );");
					_mydb.execSQL("insert into mytable(name, age, regist_date) values ('" + inputName + "', " + _ageEdit.getText().toString() + ", '2013-08-01' );");
				}catch(SQLException e) {
					Logger.e(this, "onClick", "" + e);
				}
				
				Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
			}
			
			// 登録したので入力欄を初期化
			_nameEdit.setText("");
			_ageEdit.setText("");
			
			
		}else {
			Logger.d(this, "onClick", "SelectButton");
			
			TextView textView = (TextView)findViewById(R.id.ResultTextView);
			String tmp = "";
			
			/* 検索の処理 */
			// queryを使用
			// query(table名,field名(String[]),検索語,検索語に?がある場合に使用,GROUP BY，HAVING，ORDER BY)
//			Cursor mCursor = _mydb.query("mytable",
//					new String[] { /*"_id",*/ "data" }, /*"_id = ?"*/null
//					, /*new String[]{"a*"}*/null, null, null, null);

			// TODO Select文見やすく出来ないらしい
			Cursor mCursor = _mydb.query("mytable",
					new String[] { "_id", "name", "age", "regist_date" }, /*"_id = ?"*/null
					, /*new String[]{"a*"}*/null, null, null, null);
			
			
			// rawQueryを使用
			// String SQL_SELECT = "SELECT " + DBSampleActivity.FIELD_ID + ","
			// + DBSampleActivity.FIELD_DATA + " FROM "
			// + DBSampleActivity.TABLE_NAME + " WHERE "
			// + DBSampleActivity.FIELD_ID + "=" + rgex + ";";
			// selectionArgs : WHERE句を使用するときに指定する。
			// Cursor mCursor = mydb.rawQuery(SQL_SELECT, null); 
			// Cursorを先頭に移動する 検索結果が0件の場合にはfalseが返る

//			if (mCursor.moveToFirst()) {
//				String text = mCursor.getString(mCursor
//						.getColumnIndex("data"));
//				
//				// TextViewに表示する
//				textView.setText(text);
//			} else {
//				// 検索結果が無いのでTextViewをクリアする    
//				textView.setText(null);
//			}
			
			if(mCursor != null) {
				Logger.i(this, "onClick", "mCursor != null");
				int rowCount = mCursor.getCount();
				
				if(rowCount < 1) {
					return;
				}
				Logger.d(this, "onClick", "rowCount = " + rowCount);

				mCursor.moveToFirst();
				
				for(int i= 0; i < rowCount; i++) {
//					Logger.v(this, "onClick", "i = " + i);
					int id = mCursor.getInt(0);
					String name = mCursor.getString(1);
					int age = mCursor.getInt(2);
					String date = mCursor.getString(3);

//                    Logger.d(this, "onClick", "data = " + data);
                    Logger.d(this, "onClick", "id = " + id + ", name = " + name + ", age = " + age + ", date = " + date);
                    
//                    tmp += data + "\n";
                    tmp += id + " " + name + " " + age + " " + date + "\n";
                    
    				mCursor.moveToNext();
    				Logger.w(this, "onClick", "tmp = " + tmp);
    				
    				
    				formatDate(date);
				}
			}
			
			textView.setText(tmp);
			
		}
		
		_selectBtn.setFocusable(true);
		_selectBtn.setFocusableInTouchMode(true);
		_selectBtn.requestFocus();
	}

	
	protected void formatDate(String regist_date) {
		
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format1.parse(regist_date);
			
			Logger.i(this, "formatDate", "データベースから取得した日付をDate型へ => " + date);
			
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
			String rs = format2.format(date);
				
			Logger.i(this, "formatDate", "Date型にした日付を指定のFormatへ変更 => " + rs);
			
			// XXX Date型は非推奨のメソッドが多いのでCalendar型を推奨されている
			
			Calendar tmpcal = Calendar.getInstance();
			tmpcal.setTime(date);

			Logger.d(this, "formatDate", "カレンダー型 " + tmpcal);
			Logger.d(this, "formatDate", "カレンダー型：年 " + tmpcal.get(Calendar.YEAR));
			// 月は0～11の値で表されます。そのため、実際の月は1加える必要があります。
			Logger.d(this, "formatDate", "カレンダー型：月 " + tmpcal.get(Calendar.MONTH));
			Logger.d(this, "formatDate", "カレンダー型：日 " + tmpcal.get(Calendar.DATE));

			tmpcal.add(Calendar.DATE, -3);
			Logger.d(this, "formatDate", "カレンダー型：3日前 " + tmpcal.get(Calendar.DATE));
			
			tmpcal.add(Calendar.MONTH, 2);
			Logger.d(this, "formatDate", "カレンダー型：2ヶ月後 " + tmpcal.get(Calendar.MONTH));
			
			
			
			
			
		}catch (Exception e) {
			Logger.e(this, "formatDate", "" + e);
		}
		
		
		
		
		
	}
	
	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus == false) {
			// ソフトキーボードを非表示にする                
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
