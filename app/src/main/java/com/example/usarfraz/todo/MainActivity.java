package com.example.usarfraz.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    List<Todo> todoList;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    private static int idCounter;
    private final int REQUEST_CODE = 20;
    private final int ADD = 1;
    private final int EDIT = 2;
    private final int DELETE = 3;

    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems() {
        todoList = SQLite.select().from(Todo.class).queryList();
        todoItems = new ArrayList<String>();
        for (Todo todo: todoList) {
            todoItems.add(todo.taskName);
        }
    }

    private void writeItems(int position, int op){
        if (op == ADD) {
            Todo todo = new Todo();
            todo.id = idCounter++;
            todo.taskName = todoItems.get(position);
            todoList.add(todo);
            todo.save();
        } else if (op == EDIT) {
            Todo todo = todoList.get(position);
            todo.taskName = todoItems.get(position);
            todo.save();
        } else {
            todoList.get(position).delete();
            todoList.remove(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        idCounter = todoList.size();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

                    public boolean onItemLongClick(AdapterView<?>parent, View view, int position, long id) {
                        todoItems.remove(position);
                        aToDoAdapter.notifyDataSetChanged();
                        writeItems(position, DELETE);
                        return true;
                    }
                });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?>parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("editItem", todoItems.get(position)); // pass item data to edit activity
                i.putExtra("position", position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
        etEditText = (EditText) findViewById(R.id.etEditText);
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems(todoItems.size()-1, ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            int position =  data.getExtras().getInt("position");
            todoItems.set(position, data.getExtras().getString("saveItem"));
            aToDoAdapter.notifyDataSetChanged();
            writeItems(position, EDIT);
        }
    }
}
