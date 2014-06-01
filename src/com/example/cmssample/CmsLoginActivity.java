package com.example.cmssample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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

		private String member;// 会員NO
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
					
					member = memberET.getText().toString();// 入力された会員NOの文字列を取得します。
					password = passwordET.getText().toString();// 入力されたパスワードの文字列を取得します。
					
					Toast.makeText(getActivity(), member, Toast.LENGTH_LONG).show();

					getFragmentManager().beginTransaction()
							.replace(R.id.container, new MemberFragment())
							.addToBackStack(null)// バックキーを押下すると一つ前のフラグメントに戻ります。
							.commit();

				}
			});

			return rootView;
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
