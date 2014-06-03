package com.example.cmssample;

import static com.example.cmssample.Dao.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemberSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "member_db"; // データベース名
	private static final int DATABASE_VERSION = 1; // スキーマバージョン

	// テーブル作成用SQL文です。
	private static final String CREATE_TABLE_SQL =
			" create table " + TABLE_NAME + " ("
					+ COLUMN_ID + " text(5) primary key,"
					+ COLUMN_NAME + " text(20),"
					+ COLUMN_KANA + " text(20),"
					+ COLUMN_ADDRESS + " text(60),"
					+ COLUMN_TEL + " text(13),"
					+ COLUMN_DATE + " text,"
					+ COLUMN_PASSWORD + " text(5))";
	/**
	 * SQL
	 * create tgable member (
	 * _id text(5) primary key,
	 * name text(20),
	 * kana text(20),
	 * address text(60),
	 * tel text(13)
	 * date text
	 * password text(5));
	 *
	 */



	static final String DROP_TABLE = "drop table " + TABLE_NAME + ";";

	public MemberSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE_SQL); // テーブル作成用SQL実行します。

	}

	// onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれます。
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
