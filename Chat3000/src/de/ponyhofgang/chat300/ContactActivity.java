package de.ponyhofgang.chat300;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactActivity extends ListActivity{

	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        
        
        
            String[] values = new String[] {"Matze", "Jonas", "Aaron", "Gordon", "Schantal", "Zettrik"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
        listView = (ListView)findViewById(R.id.listView1);  
        
        
        listView.setOnItemClickListener(new OnItemClickListener()
        {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), getPackageName()+".ChatActivity");
        intent.putExtra("chatBuddy", listView.getAdapter().getItem(position).toString());
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
