package com.example.lab2.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Create a database with entities from item class
@Database(entities = {Book.class}, version = 1)

public abstract class BookDatabase extends RoomDatabase {

    public static final String BOOK_DATABASE_NAME = "book_database";

    public abstract BookDao bookDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile BookDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    // manipulate database
    static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // check whether database exists in app
    static BookDatabase getDatabase(final Context context) {
        // if no database exist, create new one
        if (INSTANCE == null) {
                synchronized (BookDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        BookDatabase.class, BOOK_DATABASE_NAME)
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

    }

