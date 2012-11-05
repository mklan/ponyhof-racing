package de.ponyhofgang.chat3000;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomChatListAdapter extends ArrayAdapter<ChatStrings>{

	private ArrayList<ChatStrings> items;
	private LayoutInflater vi;
	private Context context;
	
	

    public CustomChatListAdapter(Context context, int textViewResourceId, ArrayList<ChatStrings> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
            	LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            	v = inflater.inflate(R.layout.message_item, parent, false);
            }
            ChatStrings chatStrings = items.get(position);
            
			TextView lt = (TextView) v.findViewById(R.id.messageTextLeft);
			TextView rt = (TextView) v.findViewById(R.id.messageTextRight);
			
			if (chatStrings.getStringLeft() != null) {
				lt.setText(chatStrings.getStringLeft());
				rt.setText("");
			}
			if (chatStrings.getStringRight() != null) {
				rt.setText(chatStrings.getStringRight());
				lt.setText("");
			}
           
            return v;
    }

}
