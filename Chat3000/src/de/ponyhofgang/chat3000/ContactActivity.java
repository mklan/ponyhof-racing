package de.ponyhofgang.chat3000;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class ContactActivity extends ListActivity{

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setContactTitle();
        
        if(Build.VERSION.SDK_INT >= 11) hideActionBarIcon();
        
            //TODO getUserList();
            String[] values = new String[] {"Matze", "Jonas", "Aaron", "Gordon", "Schantal", "Zettrik"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
          
        
        
        getListView().setOnItemClickListener(new OnItemClickListener()
        {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), getPackageName()+".ChatActivity");
        intent.putExtra("chatBuddy", getListView().getAdapter().getItem(position).toString());
        startActivity(intent);
        
        	
        }

		
        });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    public void setContactTitle() {
    	Intent intent = getIntent();
		this.setTitle(intent.getStringExtra("loginName") + getString(R.string.contact_title));
	}
    

	@TargetApi(11)
	public void hideActionBarIcon(){
    	
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
    	
    }
    
	
}
