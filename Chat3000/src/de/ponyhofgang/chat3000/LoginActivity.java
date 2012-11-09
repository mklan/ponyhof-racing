package de.ponyhofgang.chat3000;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText loginText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById(R.id.loginButton).setOnClickListener(this);
		loginText = (EditText) findViewById(R.id.loginField);
		loadLoginName();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public void onClick(View v) {
		if (v.getId() == R.id.loginButton) {
			if (!isEmpty(loginText) && !consistsOfWhitespace(loginText)) {
				safeLoginName();
				//TODO registerAtServer()
				Intent intent = new Intent(this, ContactActivity.class);
				intent.putExtra("loginName", loginText.getText().toString());
				startActivity(intent);

			}else{
				Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show();
				
			}

		}
	}

	public boolean isEmpty(EditText loginText) {

		if (!loginText.getText().toString().equals("")) {

			return false;
		}
		

		return true;
	}

	public boolean consistsOfWhitespace(EditText loginText) {

		String txt = loginText.getText().toString();

		for (int i = 0; i < txt.length(); i++) {

			if (txt.charAt(i) != ' ')
				return false;
		}

		return true;

	}

	public void safeLoginName() {

		SharedPreferences settings = getPreferences(0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("nickname", loginText.getText().toString());
		editor.commit();

	}

	public void loadLoginName() {
		SharedPreferences settings = getPreferences(0);
		loginText.setText(settings.getString("nickname", ""));
	}

}
