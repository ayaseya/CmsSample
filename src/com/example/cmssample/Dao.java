package com.example.cmssample;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {

	public static final String TABLE_NAME = "member";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_KANA = "kana";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_TEL = "tel";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_PASSWORD = "password";

	private static final String[] COLUMNS = { COLUMN_ID, COLUMN_NAME,
			COLUMN_KANA, COLUMN_ADDRESS, COLUMN_TEL, COLUMN_DATE,
			COLUMN_PASSWORD };

	private SQLiteDatabase db;

	/**
	 * コンストラクターでSQLiteDatabaseを引数とします。
	 *
	 * @param db
	 */
	public Dao(SQLiteDatabase db) {
		this.db = db;
	}

	// データを挿入するメソッドです。
	public long insert(MemberInformation member) {
		// ContentValuesはカラム名をキーとして、値を保存するクラスです。
		ContentValues values = new ContentValues();

		values.put(COLUMN_ID, member.get_id());
		values.put(COLUMN_NAME, member.getName());
		values.put(COLUMN_KANA, member.getKana());
		values.put(COLUMN_ADDRESS, member.getAddress());
		values.put(COLUMN_TEL, member.getTel());
		values.put(COLUMN_DATE, member.getDate());
		values.put(COLUMN_PASSWORD, member.getPassword());

		return db.insert(TABLE_NAME, null, values);
	}

	public long update(MemberInformation member) {
		ContentValues values = new ContentValues();

		values.put(COLUMN_ID, member.get_id());
		values.put(COLUMN_NAME, member.getName());
		values.put(COLUMN_KANA, member.getKana());
		values.put(COLUMN_ADDRESS, member.getAddress());
		values.put(COLUMN_TEL, member.getTel());
		values.put(COLUMN_DATE, member.getDate());
		values.put(COLUMN_PASSWORD, member.getPassword());

		String whereClause = "_id = '" + member.get_id() + "'";// 更新条件に_idを設定します。
		return db.update(TABLE_NAME, values, whereClause, null);
	}

	public List<MemberInformation> findAll() {
		List<MemberInformation> list = new ArrayList<MemberInformation>();

		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
		while (cursor.moveToNext()) {
			MemberInformation member = new MemberInformation();
			member.set_id(cursor.getString(0));
			member.setName(cursor.getString(1));
			member.setKana(cursor.getString(2));
			member.setAddress(cursor.getString(3));
			member.setTel(cursor.getString(4));
			member.setDate(cursor.getString(5));
			member.setPassword(cursor.getString(6));

			list.add(member);
		}
		return list;
	}

	public MemberInformation findById(String _id) {

		//		Cursor cursor = db.query(TABLE_NAME, COLUMNS, "_id like ? ", new String[]{_id}, null, null, null,null);

		String sql = "select * from member where _id ='" + _id + "';";

		// 第一引数SQL文、第二引数はSQL文内に埋め込まれた「?」にはめ込むString配列です。
		//
		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			MemberInformation member = new MemberInformation();
			member.set_id(cursor.getString(0));
			member.setName(cursor.getString(1));
			member.setKana(cursor.getString(2));
			member.setAddress(cursor.getString(3));
			member.setTel(cursor.getString(4));
			member.setDate(cursor.getString(5));
			member.setPassword(cursor.getString(6));

			Log.v("CMS", "ID:" + _id + "の会員情報を返します。");

			return member;
		}
		return null;
	}

	public int delete(String _id) {
		return db.delete(TABLE_NAME, " _id = '" + _id + "'", null);
	}

}
