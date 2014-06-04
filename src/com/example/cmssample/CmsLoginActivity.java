package com.example.cmssample;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CmsLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("CMS", "-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
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
		menu.findItem(R.id.action_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

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

			return true;
		} else if (id == R.id.action_search) {

			getFragmentManager().beginTransaction()
					.replace(R.id.container, new SearchFragment())
					.addToBackStack(null)// バックキーを押下すると一つ前のフラグメントに戻ります。
					.commit();

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

		private AlertDialog dialog;
		private View dialogView;
		private MemberInformation member;
		private TextView nameTV;
		private TextView welcomeTV;

		public MemberFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cms_member,
					container, false);
			Log.v("CMS", "MemberFragment");

			// フラグメントに添付されたデータを取り出します。
			member = (MemberInformation) getArguments().getSerializable("MEMBER");

			// 各TextViewに会員情報を設定し画面に表示します。
			welcomeTV = (TextView) rootView.findViewById(R.id.welcomeTV);
			String welcomeMsg = getString(R.string.welcome_message, member.getName());
			welcomeTV.setText(welcomeMsg);

			TextView memberTV = (TextView) rootView.findViewById(R.id.memberTV);
			memberTV.setText(member.get_id());

			nameTV = (TextView) rootView.findViewById(R.id.nameTV);
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

			// 編集用ダイアログを表示します。
			// レイアウトのxmlファイルからビューを生成します。
			dialogView = inflater.inflate(R.layout.edit_dialog_layout, (ViewGroup) rootView.findViewById(R.id.dialog));

			// ダイアログのインスタンスを生成します。
			dialog = new AlertDialog.Builder(getActivity())
					.setView(dialogView)
					.setCancelable(false)
					.create();

			// ダイアログ上のUpdateボタンとCancelボタンのリスナーを設定します。
			((Button) dialogView.findViewById(R.id.updateBtn)).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					String name = ((EditText) dialogView.findViewById(R.id.editTV)).getText().toString();
					member.setName(name);

					//					Log.v("CMS", member.get_id() + "|"
					//							+ member.getName() + "|"
					//							+ member.getKana() + "|"
					//							+ member.getAddress() + "|"
					//							+ member.getTel() + "|"
					//							+ member.getDate() + "|"
					//							+ member.getPassword());
					//
					//					Log.v("CMS", "name=" + name);

					// SQLiteHelperのコンストラクターを呼び出します。
					MemberSQLiteOpenHelper dbHelper = new MemberSQLiteOpenHelper(getActivity());
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					// Daoクラスのコンストラクターを呼び出します。
					Dao dao = new Dao(db);

					dao.update(member);

					db.close();

					// TextViewを更新します。
					String welcomeMsg = getString(R.string.welcome_message, name);
					welcomeTV.setText(welcomeMsg);

					nameTV.setText(name);

					dialog.dismiss();// ダイアログを消去します。

				}
			});

			((Button) dialogView.findViewById(R.id.cancelBtn)).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();// ダイアログを消去します。
				}
			});

			// 名前などをクリックした時のリスナーを設定します。
			nameTV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 現在のカラムの値をEditTextに表示させます。
					((EditText) dialogView.findViewById(R.id.editTV)).setText(member.getName());
					dialog.show();// 編集用のダイアログを表示します。

				}
			});

			return rootView;
		}
	}

	public static class SearchFragment extends Fragment {

		private EditText kanaET;
		private EditText startDateET;
		private EditText endDateET;

		private String kanaCriteria;// カナ検索の抽出条件を格納する変数です。
		private String startDateCriteria;// 期間検索の開始日の指定を格納する変数です。
		private String endDateCriteria;// 期間検索の終了日の指定を格納する変数です。
		private TextView resultTV;

		public SearchFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cms_search, container, false);

			// EditTextのインスタンスを取得します。
			kanaET = (EditText) rootView.findViewById(R.id.kanaET);

			startDateET = (EditText) rootView.findViewById(R.id.startDateET);
			endDateET = (EditText) rootView.findViewById(R.id.endDateET);

			resultTV = (TextView) rootView.findViewById(R.id.resultTV);

			// 検索ボタンのクリックリスナーを設定します。
			((Button) rootView.findViewById(R.id.searchBtn)).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// EditTextに入力された内容を取得します。

					kanaCriteria = kanaET.getText().toString();

					startDateCriteria = startDateET.getText().toString();
					endDateCriteria = endDateET.getText().toString();

					// SQLiteHelperのコンストラクターを呼び出します。
					MemberSQLiteOpenHelper dbHelper = new MemberSQLiteOpenHelper(getActivity());
					SQLiteDatabase db = dbHelper.getReadableDatabase();
					// Daoクラスのコンストラクターを呼び出します。
					Dao dao = new Dao(db);

					if ("".equals(kanaCriteria)) {// カナ条件が未入力のケースです。

						if ("".equals(startDateCriteria) || "".equals(endDateCriteria)) {
							Toast.makeText(getActivity(), "検索条件を入力してください", Toast.LENGTH_LONG).show();

						} else if (!"".equals(startDateCriteria) && !"".equals(endDateCriteria)) {
							Toast.makeText(getActivity(), "期間検索を実行します", Toast.LENGTH_LONG).show();

							List<MemberInformation> list = dao.findByDate(startDateCriteria, endDateCriteria);// 入力された期間と一致するレコードを検索します。

							if (list.size() != 0) {
								StringBuilder lines = new StringBuilder();
								for (MemberInformation tmp : list) {
									lines.append(tmp.get_id());
									lines.append("|");
									lines.append(tmp.getName());
									lines.append("|");
									lines.append(tmp.getDate());
									lines.append(System.getProperty("line.separator"));
								}

								resultTV.setText(lines.toString());
							} else {
								resultTV.setText("条件に一致するレコードはありません");
							}

						}

					} else {// カナ条件が入力されているケースです。

						if ("".equals(startDateCriteria) || "".equals(endDateCriteria)) {
							Toast.makeText(getActivity(), "カナ検索を実行します", Toast.LENGTH_LONG).show();

							List<MemberInformation> list = dao.findByKana(kanaCriteria);// 入力された条件と前方一致するレコードを検索します。

							if (list.size() != 0) {
								StringBuilder lines = new StringBuilder();
								for (MemberInformation tmp : list) {
									lines.append(tmp.get_id());
									lines.append("|");
									lines.append(tmp.getName());
									lines.append("|");
									lines.append(tmp.getKana());
									lines.append(System.getProperty("line.separator"));
								}

								resultTV.setText(lines.toString());
							} else {
								resultTV.setText("条件に一致するレコードはありません");
							}

						} else if (!"".equals(startDateCriteria) && !"".equals(endDateCriteria)) {
							Toast.makeText(getActivity(), "カナ検索と期間検索を同時に実行します", Toast.LENGTH_LONG).show();

							List<MemberInformation> list = dao.findByKanaDate(kanaCriteria,startDateCriteria, endDateCriteria);// 入力された条件と前方一致し、期間と一致するレコードを検索します。

							if (list.size() != 0) {
								StringBuilder lines = new StringBuilder();
								for (MemberInformation tmp : list) {
									lines.append(tmp.get_id());
									lines.append("|");
									lines.append(tmp.getName());
									lines.append("|");
									lines.append(tmp.getKana());
									lines.append("|");
									lines.append(tmp.getDate());
									lines.append(System.getProperty("line.separator"));
								}

								resultTV.setText(lines.toString());
							} else {
								resultTV.setText("条件に一致するレコードはありません");
							}
						}

					}
					db.close();
				}

			});

			return rootView;
		}
	}

}
