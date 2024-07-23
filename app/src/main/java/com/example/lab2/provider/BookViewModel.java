package com.example.lab2.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// It provides the data for a specific UI component, such as a fragment or activity,
// and contains data-handling business logic to communicate with the model.
// all the methods (that will be invoked later by the activity or fragment)
// call their counterpart methods that are implemented in the Repository.
public class BookViewModel extends AndroidViewModel {
    private final BookRepository mRepository;
    private final LiveData<List<Book>> mAllBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BookRepository(application);
        mAllBooks = mRepository.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }

    public void insert(Book book) {
        mRepository.insert(book);
    }

    public void removeLastBook(){mRepository.removeLastBook();}

    public void deleteExpensiveBook(){mRepository.deleteExpensiveBook();}

    public void deleteAll(){
        mRepository.deleteAll();
    }

}

