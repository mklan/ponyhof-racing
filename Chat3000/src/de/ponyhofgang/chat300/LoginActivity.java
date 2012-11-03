package de.ponyhofgang.chat300;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	private EditText loginText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.loginButton).setOnClickListener(this);
        loginText = (EditText) findViewById(R.id.loginField);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }


	public void onClick(View v) {
		if (v.getId() == R.id.loginButton) {
			if(isNotEmpty(loginText) == true){
				
				startActivity(new Intent(this, ContactActivity.class));
			
			}
		 Log.e("jojo", "bier");
		}
	}
	
	public boolean isNotEmpty(EditText loginText){
		
		if(!loginText.getText().toString().equals("")){
			return true;
		}
		Toast.makeText(this, "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
		
		return false;
	}
    
}
