package com.example.usarfraz.todo;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.value;

/**
 * Created by usarfraz on 2/13/17.
 */

public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvTaskName = (TextView) view.findViewById(R.id.tvTaskName);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        // Extract properties from cursor
        String taskName = cursor.getString(cursor.getColumnIndexOrThrow("taskName"));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        long dueDate = cursor.getLong(cursor.getColumnIndexOrThrow("dueDate"));
        // Populate fields with extracted properties
        String priorityStr;
        int color;
        switch(priority) {
            case 0:
                priorityStr = "LOW";
                color = Color.GREEN;
                break;
            case 1:
                priorityStr = "MEDIUM";
                color = Color.rgb(255, 215, 0);
                break;
            case 2:
                priorityStr = "HIGH";
                color = Color.RED;
                break;
            default:
                priorityStr = "LOW";
                color = Color.GREEN;
                break;
        }
        tvTaskName.setText(taskName);
        tvPriority.setText(priorityStr);
        tvPriority.setTextColor(color);

        DateFormat df = new SimpleDateFormat("MMM, dd yyyy");
        tvDate.setText("Due: "+df.format(dueDate));




    }
}
