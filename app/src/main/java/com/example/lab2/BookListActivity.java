package com.example.lab2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.provider.BookViewModel;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_list);

        // replaces the contents of a FrameLayout (identified by its ID R.id.frameLayout) with a new instance of a BookListFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new BookListFragment()).addToBackStack("f1").commit();

    }
}
