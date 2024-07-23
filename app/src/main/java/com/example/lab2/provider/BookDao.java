package com.example.lab2.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Data Access Object - an interface that defines the database operations
// that should be performed on the database.

// LiveData - a data holder that allows a value to become observable
// It can notify other objects when changes to its data occur, thereby
// solving the problem of ensuring that the user interface always matches the data within the ViewModel.
@Dao
public interface BookDao {

    @Query("select * from books")
    LiveData<List<Book>> getAllBook();

    @Query("select * from books where Title =:name")
    List<Book> getBook(String name);

    @Insert
    void addBook(Book book);

    @Query("delete from books where Title = :name")
    void deleteBook(String name);

    @Query("delete from books where ID = (select MAX(ID) from books)")
    void removeLastBook();

    @Query("delete from books where Price > 50")
    void deleteExpensiveBook();

    @Query("delete from books")
    void deleteAllBooks();

}
