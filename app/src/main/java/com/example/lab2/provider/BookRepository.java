package com.example.lab2.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

// It is a Java class that provides an easy and clean API
// so that the application can access different data sources
// It contains all of the code necessary for directly handling all data sources used by the application.
// This avoids the need for the UI controller and ViewModel to contain code
// that directly accesses sources such as databases or web services.

public class BookRepository {
    private final BookDao mBookDao;
    private final LiveData<List<Book>> mAllBooks;

    // constructor
    BookRepository(Application application) {
        // create instance of database and initialize it using getDatabase()
        BookDatabase db = BookDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        mAllBooks = mBookDao.getAllBook();
    }

    LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }

    void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.addBook(book));
    }

    void removeLastBook(){
        BookDatabase.databaseWriteExecutor.execute(()->{
            mBookDao.removeLastBook();
        });
    }

    void deleteExpensiveBook(){
        BookDatabase.databaseWriteExecutor.execute(()->{
            mBookDao.deleteExpensiveBook();
        });
    }

    void deleteAll(){
        BookDatabase.databaseWriteExecutor.execute(()->{
            mBookDao.deleteAllBooks();
        });
    }
}

