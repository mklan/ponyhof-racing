package de.ponyhofgang.chat300;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChatActivity extends Activity{

	private EditText chatText;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        Intent intent = getIntent();
        
        chatText = (EditText) findViewById(R.id.chatText);
        
        chatText.setText(intent.getStringExtra("chatBuddy"));
            
    }
	
	
}
