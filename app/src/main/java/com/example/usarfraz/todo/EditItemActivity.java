package com.example.usarfraz.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;
import java.util.Date;

import static com.raizlabs.android.dbflow.config.FlowLog.Level.D;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditText;
    EditText etTaskNotes;
    DatePicker datePicker;
    Spinner prioritySpinner;
    Spinner statusSpinner;

    Todo todo;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_modify_todo);

        etEditText = (EditText) findViewById(R.id.etEditText);
        etTaskNotes = (EditText) findViewById(R.id.etEditNotes);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        prioritySpinner = (Spinner) findViewById(R.id.spinnerPriority);
        statusSpinner = (Spinner) findViewById(R.id.spinnerStatus);

        todo = (Todo) getIntent().getSerializableExtra("todo");
        etEditText.setText(todo.taskName);
        etEditText.setSelection(etEditText.getText().length());

        etTaskNotes.setText(todo.notes);
        etTaskNotes.setSelection(etTaskNotes.getText().length());

        cal = Calendar.getInstance();
        cal.setTime(todo.dueDate == null ? new Date() : todo.dueDate);
        datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        prioritySpinner.setAdapter(priorityAdapter);
        prioritySpinner.setSelection(todo.priority);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setSelection(todo.status);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:

                Intent data = new Intent();
                // Pass relevant data back as a result
                todo.taskName = etEditText.getText().toString();
                todo.notes = etTaskNotes.getText().toString();
                cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                todo.dueDate = cal.getTime();
                todo.priority = prioritySpinner.getSelectedItemPosition();
                todo.status = statusSpinner.getSelectedItemPosition();

                data.putExtra("todo", todo);
                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
                return true;
            case R.id.item_cancel:
                // Close the dialog and return back to the parent activity
                finish();
                return true;

        }
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void onSave(View view) {
        Intent data = new Intent();
        // Pass relevant data back as a result
        todo.taskName = etEditText.getText().toString();
        data.putExtra("todo", todo);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
