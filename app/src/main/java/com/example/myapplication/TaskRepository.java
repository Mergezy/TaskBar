package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private TaskDatabase taskDatabase;

    public TaskRepository(Context context) {
        taskDatabase = new TaskDatabase(context);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = taskDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("date", task.getDate());
        db.insert("tasks", null, values);
        db.close();
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = taskDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                taskList.add(new Task(id, title, description, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }
}

