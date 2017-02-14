package com.example.usarfraz.todo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;


public class MainActivity extends AppCompatActivity {
    Cursor todoCursor;
    TodoCursorAdapter todoAdapter;
    ListView lvItems;
    EditText etEditText;

    private static int idCounter;
    private final int REQUEST_CODE = 20;

    public void populateToDoItems() {
        todoCursor = SQLite.select().from(Todo.class).query();
        todoAdapter = new TodoCursorAdapter(this, todoCursor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateToDoItems();
        if (todoCursor.getCount() == 0) {
            idCounter = 0;
        } else {
            todoCursor.moveToLast();
            int colIndex = todoCursor.getColumnIndex("_id");
            idCounter = todoCursor.getInt(colIndex)+1;
        }
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

                    public boolean onItemLongClick(AdapterView<?>parent, View view, int position, long id) {
                        todoCursor.moveToPosition(position);
                        int colIndex = todoCursor.getColumnIndex("_id");
                        int itemId = todoCursor.getInt(colIndex);
                        Todo todo = SQLite.select().from(Todo.class).where(Todo_Table._id.eq(itemId)).querySingle();
                        todo.delete();
                        todoCursor = SQLite.select().from(Todo.class).query();
                        todoAdapter.changeCursor(todoCursor);
                        //todoAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?>parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                todoCursor.moveToPosition(position);
                int colIndex = todoCursor.getColumnIndex("_id");
                int itemId = todoCursor.getInt(colIndex);
                Todo todo = SQLite.select().from(Todo.class).where(Todo_Table._id.eq(itemId)).querySingle();
                i.putExtra("todo", todo); // pass item data to edit activity
                startActivityForResult(i, REQUEST_CODE);
            }
        });
        etEditText = (EditText) findViewById(R.id.etEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void onAddItem(View view) {
        todoCursor.moveToLast();
        Todo todo = new Todo();
        todo.id = idCounter++;
        todo.taskName = etEditText.getText().toString();
        etEditText.setText("");
        todo.save();
        todoCursor = SQLite.select().from(Todo.class).query();
        todoAdapter.changeCursor(todoCursor);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract edited item from result extras
            Todo todo = (Todo) data.getSerializableExtra("todo");
            todo.save();
            todoCursor = SQLite.select().from(Todo.class).query();
            todoAdapter.changeCursor(todoCursor);
            //todoAdapter.notifyDataSetChanged();
        }
    }
}
