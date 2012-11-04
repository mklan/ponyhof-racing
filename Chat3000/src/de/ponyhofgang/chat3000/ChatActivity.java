package de.ponyhofgang.chat3000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ChatActivity extends Activity implements OnClickListener{

	private EditText chatText;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        Intent intent = getIntent();
        
        chatText = (EditText) findViewById(R.id.chatText);
        
        findViewById(R.id.sendButton).setOnClickListener(this);
        
        
        chatText.setText(intent.getStringExtra("chatBuddy"));
            
    }
	
	
	public void onClick(View v) {
		if (v.getId() == R.id.sendButton) {
			if(!isEmpty(chatText)){
				
				//sendMessage();
				Toast.makeText(this, "Senden gedrückt", Toast.LENGTH_SHORT).show();
			
			}
		
		}
	}
	
	
	public boolean isEmpty(EditText chatText){
		
		if(!chatText.getText().toString().equals("")){
			
			return false;
		}
		
		
		return true;
	}
	
	
	
	
}
