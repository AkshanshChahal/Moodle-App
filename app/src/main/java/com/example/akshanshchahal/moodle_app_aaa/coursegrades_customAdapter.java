package com.example.akshanshchahal.moodle_app_aaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amareshiitd on 22-02-2016.
 */
public class coursegrades_customAdapter extends ArrayAdapter<String[]>{
    public coursegrades_customAdapter(Context context,ArrayList<String[]> list) {
        super(context,R.layout.grades_customrow , list);
    }

    @Override // This where the data passed is turned into the layout
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext()) ;
        //LayoutInflator is a way to tell the function to get ready for rendering

        View customView_marks = myInflator.inflate(R.layout.coursesgrades_customrow, parent, false);
        // customView_marks is the reference to the file custom_row

        // Now we need references to data items which are on the custom_row

        String[] strings = getItem(position);
        TextView gradeItem = (TextView) customView_marks.findViewById(R.id.marks_gradeItem);
        TextView score = (TextView) customView_marks.findViewById(R.id.marks_score);
        TextView weight = (TextView) customView_marks.findViewById(R.id.marks_weight);
        TextView absMarks = (TextView) customView_marks.findViewById(R.id.marks_absoluteMarks);



        score.setText(strings[1]);
        weight.setText(strings[2]);
        absMarks.setText(strings[3]);
        gradeItem.setText(strings[0]);

        return customView_marks;
    }
}
