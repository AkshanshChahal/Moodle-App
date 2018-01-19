package com.example.akshanshchahal.moodle_app_aaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by amareshiitd on 24-02-2016.
 */
public class notification_customadapter extends ArrayAdapter<String[]> {
    public notification_customadapter(Context context,ArrayList<String[]> list) {
        super(context,R.layout.notifications_customrow , list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext()) ;
        //LayoutInflator is a way to tell the function to get ready for rendering

        View customView_marks = myInflator.inflate(R.layout.notifications_customrow, parent, false);
        // customView_marks is the reference to the file custom_row

        // Now we need references to data items which are on the custom_row

        String[] strings = getItem(position);
        TextView notification = (TextView) customView_marks.findViewById(R.id.button);
        TextView date = (TextView) customView_marks.findViewById(R.id.textView8);


        assignment assignment = new assignment();
        notification.setText(assignment.func2(strings[0]));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        date.setText(assignment.func1(currentDateandTime,strings[1])+ " ago");

        return customView_marks;
    }

}
