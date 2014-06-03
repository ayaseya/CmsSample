package com.example.cmssample;

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
import android.widget.TextView;
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cms_login, menu);

		menu.findItem(R.id.action_db).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

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
		} else if (id == R.id.action_db) {
			Log.v("CMS", "action_db");

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private String id;// 会員NO
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

					// SQLiteHelperのコンストラクターを呼び出します。
					MemberSQLiteOpenHelper dbHelper = new MemberSQLiteOpenHelper(getActivity());
					SQLiteDatabase db = dbHelper.getReadableDatabase();
					// Daoクラスのコンストラクターを呼び出します。
					Dao dao = new Dao(db);

					MemberInformation member = dao.findById(id);// 入力された会員NOと一致するレコードを検索します。

					db.close();

					if (member == null) {
						Toast.makeText(getActivity(), "ID:" + id + "は存在しません", Toast.LENGTH_LONG).show();
					} else {

						Log.v("CMS", member.get_id() + "|"
								+ member.getName() + "|"
								+ member.getKana() + "|"
								+ member.getAddress() + "|"
								+ member.getTel() + "|"
								+ member.getDate() + "|"
								+ member.getPassword());

						if (member.getPassword().equals(password)) {

							Bundle bundle = new Bundle();// フラグメントにデータを添付するためBundleクラスのインスタンスを取得します。
							bundle.putSerializable("MEMBER", member);// 取得した情報をMemberInformationクラスの形で添付します。

							MemberFragment memberFragment = new MemberFragment();
							memberFragment.setArguments(bundle);// フラグメントにデータを添付します。

							getFragmentManager().beginTransaction()
									.replace(R.id.container, memberFragment)
									.addToBackStack(null)// バックキーを押下すると一つ前のフラグメントに戻ります。
									.commit();

						} else {
							Toast.makeText(getActivity(), "パスワードが一致しません", Toast.LENGTH_LONG).show();
						}

					}
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
			Log.v("CMS", "MemberFragment");

			// フラグメントに添付されたデータを取り出します。
			MemberInformation member = (MemberInformation) getArguments().getSerializable("MEMBER");

			// 各TextViewに会員情報を設定し画面に表示します。
			TextView welcomeTV = (TextView) rootView.findViewById(R.id.welcomeTV);
			String welcomeMsg = getString(R.string.welcome_message, member.getName());
			welcomeTV.setText(welcomeMsg);

			TextView memberTV = (TextView) rootView.findViewById(R.id.memberTV);
			memberTV.setText(member.get_id());

			TextView nameTV = (TextView) rootView.findViewById(R.id.nameTV);
			nameTV.setText(member.getName());

			TextView kanaTV = (TextView) rootView.findViewById(R.id.kanaTV);
			kanaTV.setText(member.getKana());

			TextView addressTV = (TextView) rootView.findViewById(R.id.addressTV);
			addressTV.setText(member.getAddress());

			TextView telTV = (TextView) rootView.findViewById(R.id.telTV);
			telTV.setText(member.getTel());

			TextView dateTV = (TextView) rootView.findViewById(R.id.dateTV);
			dateTV.setText(member.getDate());

			TextView passwordTV = (TextView) rootView.findViewById(R.id.passwordTV);
			passwordTV.setText(member.getPassword());

			return rootView;
		}
	}

}
