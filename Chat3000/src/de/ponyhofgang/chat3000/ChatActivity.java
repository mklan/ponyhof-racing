package de.ponyhofgang.chat3000;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ChatActivity extends Activity implements OnClickListener{

	private EditText chatText;
	private ArrayAdapter	<String>messages;
	private ListView listView;
	private TextView textView;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        if(Build.VERSION.SDK_INT >= 11) 
        	hideActionBarIcon();
        
        Intent intent = getIntent();
        
        chatText = (EditText) findViewById(R.id.chatText);
        
        findViewById(R.id.sendButton).setOnClickListener(this);
        listView = (ListView)findViewById(R.id.messageList);
        textView = (TextView)findViewById(R.id.messageTextLeft);
        
        
        this.setTitle("Chat mit "+ intent.getStringExtra("chatBuddy"));
        
        
        messages = new ArrayAdapter<String>(this, R.layout.message_item_left);
        listView.setAdapter(messages);
            
    }
	
	
	public void onClick(View v) {
		if (v.getId() == R.id.sendButton) {
			if(!isEmpty(chatText)){
				
				sendMessage();
				
			
			}
		
		}
	}
	
	
	public void sendMessage(){
		
		messages.add("Du: "+ chatText.getText().toString() );
		receiveMessage();
		chatText.setText("");
		
	}
	
	public void receiveMessage(){
		
		Intent intent = getIntent();
		textView.setGravity(Gravity.RIGHT);
		messages.add(intent.getStringExtra("chatBuddy")+": "+ chatText.getText().toString() );
		
		
	}
	
	
	public boolean isEmpty(EditText chatText){
		
		if(!chatText.getText().toString().equals("")){
			
			return false;
		}
		
		
		return true;
	}
	
	@TargetApi(11)
	public void hideActionBarIcon(){
    	
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
    	
    }
	
	
	
	
}
