package com.example.cmssample;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class CmsLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cms_login);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}


		// SQLiteHelperのコンストラクターを呼び出します。
		MemberSQLiteOpenHelper dbHelper = new MemberSQLiteOpenHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// Daoクラスのコンストラクターを呼び出します。
		Dao dao = new Dao(db);

		List<MemberInformation> list = dao.findAll();

		MemberInformation member = dao.findById("00002");

		db.close();

		StringBuilder lines = new StringBuilder();
		for (MemberInformation tmp : list) {
			lines.append(tmp.get_id());
			lines.append("|");
			lines.append(tmp.getName());
			lines.append("|");
			lines.append(tmp.getKana());
			lines.append("|");
			lines.append(tmp.getAddress());
			lines.append("|");
			lines.append(tmp.getTel());
			lines.append("|");
			lines.append(tmp.getDate());
			lines.append("|");
			lines.append(tmp.getPassword());
			lines.append(System.getProperty("line.separator"));
		}

//		Log.v("CMS", lines.toString());


		Log.v("CMS", member.get_id() + "|"
				+ member.getName() + "|"
				+ member.getKana() + "|"
				+ member.getAddress() + "|"
				+ member.getTel() + "|"
				+ member.getDate() + "|"
				+ member.getPassword());




//		// SQLiteHelperのコンストラクターを呼び出します。
//		MemberSQLiteOpenHelper dbHelper = new MemberSQLiteOpenHelper(this);
//		SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//		// Daoクラスのコンストラクターを呼び出します。
//		Dao dao = new Dao(db);
//
//		List<MemberInformation> list = dao.findAll();
//
//		db.close();
//
//		StringBuilder lines = new StringBuilder();
//		for (MemberInformation tmp : list) {
//			lines.append(tmp.get_id());
//			lines.append("|");
//			lines.append(tmp.getName());
//			lines.append("|");
//			lines.append(tmp.getKana());
//			lines.append("|");
//			lines.append(tmp.getAddress());
//			lines.append("|");
//			lines.append(tmp.getTel());
//			lines.append("|");
//			lines.append(tmp.getDate());
//			lines.append("|");
//			lines.append(tmp.getPassword());
//			lines.append(System.getProperty("line.separator"));
//		}
//
//		Log.v("CMS", lines.toString());

		//		// SQLiteHelperのコンストラクターを呼び出します。
		//		MemberSQLiteOpenHelper dbHelper = new MemberSQLiteOpenHelper(this);
		//		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//
		//		// Daoクラスのコンストラクターを呼び出します。
		//		Dao dao = new Dao(db);
		//
		//		MemberInformation member = new MemberInformation();
		//		member.set_id("00001");
		//		member.setName("花田裕二");
		//		member.setKana("ハナダユウジ");
		//		member.setAddress("神奈川県");
		//		member.setTel("090-4754-2198");
		//		member.setDate("2014-06-01");
		//		member.setPassword("00000");
		//		dao.insert(member);
		//
		//		member = new MemberInformation();
		//		member.set_id("00002");
		//		member.setName("山田花子");
		//		member.setKana("ヤマダハナコ");
		//		member.setAddress("東京都");
		//		member.setTel("090-1234-5678");
		//		member.setDate("2014-06-02");
		//		member.setPassword("11111");
		//		dao.insert(member);
		//
		//		List<MemberInformation> list = dao.findAll();
		//
		//		db.close();
		//
		//		StringBuilder lines = new StringBuilder();
		//		for (MemberInformation tmp : list) {
		//			lines.append(tmp.get_id());
		//			lines.append("|");
		//			lines.append(tmp.getName());
		//			lines.append("|");
		//			lines.append(tmp.getKana());
		//			lines.append("|");
		//			lines.append(tmp.getAddress());
		//			lines.append("|");
		//			lines.append(tmp.getTel());
		//			lines.append("|");
		//			lines.append(tmp.getDate());
		//			lines.append("|");
		//			lines.append(tmp.getPassword());
		//			lines.append(System.getProperty("line.separator"));
		//		}
		//
		//		Log.v("CMS", lines.toString());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cms_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private String id="00001";// 会員NO
		private EditText memberET;
		private String password;// パスワード
		private EditText passwordET;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cms_login,
					container, false);

			// 会員NO、パスワードのインスタンスを取得します。
			memberET = (EditText) rootView.findViewById(R.id.memberET);
			passwordET = (EditText) rootView.findViewById(R.id.passwordET);

			// ログインボタンのリスナーを登録します。
			rootView.findViewById(R.id.loginBtn).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					id = memberET.getText().toString();// 入力された会員NOの文字列を取得します。
					password = passwordET.getText().toString();// 入力されたパスワードの文字列を取得します。



					Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();

					getFragmentManager().beginTransaction()
							.replace(R.id.container, new MemberFragment())
							.addToBackStack(null)// バックキーを押下すると一つ前のフラグメントに戻ります。
							.commit();

				}
			});

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

		}

	}

	public static class MemberFragment extends Fragment {

		public MemberFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cms_member,
					container, false);

			return rootView;
		}
	}

}
