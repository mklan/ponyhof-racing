package de.ponyhofgang.chat3000;

import java.util.Vector;

import com.google.android.gcm.GCMRegistrar;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class ContactActivity extends ListActivity{

	private  Vector<String[]> contacts;
	private String nickname;
	private String regId;
	private static String SENDER_ID = "76131326678";
	private Context context;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setContactTitle();
        ServerUtilities.setUsername(nickname);
        
        if(Build.VERSION.SDK_INT >= 11) hideActionBarIcon();
        
        context = this;
        
        registerAtServer();
        
            
          
        
        
        getListView().setOnItemClickListener(new OnItemClickListener()
        {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), getPackageName()+".ChatActivity");
        intent.putExtra("fromId", (contacts.get(position))[0]);
        intent.putExtra("reg_id", regId);
        intent.putExtra("fromName", getListView().getAdapter().getItem(position).toString());
        startActivity(intent);
        
        	
        }

		
        });
        
    }


 
    
    public void setContactTitle() {
    	Intent intent = getIntent();
    	nickname = intent.getStringExtra("loginName");
		this.setTitle(intent.getStringExtra("loginName") + getString(R.string.contact_title));
	}
    

	@TargetApi(11)
	public void hideActionBarIcon(){
    	
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
    	
    }
	
	public void getContacts(){
		String[] liste = new String[contacts.size()];
		for(int i = 0; i<contacts.size(); i++){
			liste[i] = (contacts.get(i))[1];
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, liste);
            setListAdapter(adapter);
	}
	
public void registerAtServer(){
		
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
		  GCMRegistrar.register(this, SENDER_ID);
			
		  regId = GCMRegistrar.getRegistrationId(this);
	      Log.v("test", "Succesfull registered id:" + regId);
        } else {
        	  Log.v("test", "Already registered");
        }
		
		
		if (GCMRegistrar.isRegisteredOnServer(this)) {  
		new GetThread().execute();
		}
	}
	
	public class GetThread extends AsyncTask<String, Void, Vector <String[]>>{
		
		
		
public Vector<String[]> doInBackground(String... strings){
	
	
	ServerUtilities.register(context, regId);
	
	return ServerUtilities.getContacts();
			
}

		
		protected void onPostExecute(Vector<String[]> Result){
			contacts = Result;
			getContacts();
			
		}
	}
	
	

    
	
}
