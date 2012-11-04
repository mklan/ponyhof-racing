package de.ponyhofgang.chat3000;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ContactActivity extends ListActivity{

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        
        
        
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
    
    
    
	
}
