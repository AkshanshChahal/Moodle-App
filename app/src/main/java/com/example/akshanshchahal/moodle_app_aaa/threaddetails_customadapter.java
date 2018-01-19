package com.example.akshanshchahal.moodle_app_aaa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by amareshiitd on 22-02-2016.
 */
public class threaddetails_customadapter extends ArrayAdapter<String[]>{
    public threaddetails_customadapter(Context context,ArrayList<String[]> list) {
        super(context,R.layout.threaddetail_customrow , list);
    }

    @Override // This where the data passed is turned into the layout
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext()) ;
        //LayoutInflator is a way to tell the function to get ready for rendering

        final View customView_marks = myInflator.inflate(R.layout.threaddetail_customrow, parent, false);
        // customView_marks is the reference to the file custom_row

        // Now we need references to data items which are on the custom_row

        String[] strings = getItem(position);
        TextView name=(TextView) customView_marks.findViewById(R.id.textView23);
        TextView desc=(TextView) customView_marks.findViewById(R.id.textView24);
        TextView created=(TextView) customView_marks.findViewById(R.id.textView25);




        name.setText(strings[0]);
        desc.setText(strings[1]);
        created.setText(strings[2]);

        return customView_marks;
    }
}
