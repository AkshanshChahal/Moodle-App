package com.example.akshanshchahal.moodle_app_aaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amareshiitd on 24-02-2016.
 */
public class thread_customadapter extends ArrayAdapter<String[]> {
    public thread_customadapter(Context context,ArrayList<String[]> list) {
        super(context,R.layout.thread_customrow , list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext()) ;
        //LayoutInflator is a way to tell the function to get ready for rendering

        final View customView_marks = myInflator.inflate(R.layout.thread_customrow, parent, false);
        // customView_marks is the reference to the file custom_row

        // Now we need references to data items which are on the custom_row

        String[] strings = getItem(position);
        TextView button=(TextView) customView_marks.findViewById(R.id.button);
        TextView date=(TextView) customView_marks.findViewById(R.id.date);




        String s[]=strings[2].split(" ");
        String g=s[1]+" "+s[0];
        button.setText(strings[1]);
        date.setText(g);

        return customView_marks;
    }
}
