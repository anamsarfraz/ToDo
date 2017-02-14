package com.example.usarfraz.todo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by usarfraz on 2/12/17.
 */

@Table(database = TodoDatabase.class)
public class Todo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 5177222050535318633L;

    @Column(name="_id")
    @PrimaryKey
    int id;

    @Column
    String taskName;

    @Column
    long dueDate;

    // low = 0, medium = 1, high = 2;
    @Column
    int priority;

    // to_do = 0, inprogress = 1, done = 2
    @Column
    int status;

    @Column
    String notes;
}