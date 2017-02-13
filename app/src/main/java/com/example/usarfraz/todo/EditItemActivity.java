package com.example.usarfraz.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditText;
    int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditText = (EditText) findViewById(R.id.etEditText);
        etEditText.setText(getIntent().getStringExtra("editItem"));
        editPosition = getIntent().getIntExtra("position", 0);
        etEditText.setSelection(etEditText.getText().length());
    }


    public void onSave(View view) {
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("saveItem", etEditText.getText().toString());
        data.putExtra("position", editPosition);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
