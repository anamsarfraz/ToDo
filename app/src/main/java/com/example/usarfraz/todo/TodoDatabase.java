package com.example.usarfraz.todo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by usarfraz on 2/12/17.
 */

@Database(name = TodoDatabase.NAME, version = TodoDatabase.VERSION)
public class TodoDatabase {
    public static final String NAME = "TodoDatabase";

    public static final int VERSION = 1;
}