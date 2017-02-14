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
    Date dueDate;

    // low = 1, medium = 2, high = 3;
    @Column
    int priority;

    // to_do = 1, inprogress = 2, done = 3
    @Column
    int status;

    @Column
    String notes;
}