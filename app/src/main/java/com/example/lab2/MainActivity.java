package com.example.lab2;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab2.provider.Book;
import com.example.lab2.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    EditText Title, Price, ID, ISBN, Author, Description;
    String bookTitle, bookISBN, bookAuthor, bookDescription, bookID, bookPrice;
    int counter = 0;

//    ArrayList<String> listItems = new ArrayList<>();
//     adapt arraylist to listview component
//    ArrayAdapter<String> adapter;
//    private ListView myListView;
//    ArrayList<Book> listItems = new ArrayList<>();
//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
    MyRecyclerViewAdapter adapter;
    DrawerLayout drawer;
    DatabaseReference myRef;
    DatabaseReference mTable;
    View myFrame;
    int initial_x, initial_y;
    GestureDetector gestureDetector;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private BookViewModel mBookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        Log.i("lab3", "onCreate");


        Toolbar toolbar = findViewById(R.id.toolbar);
        // set toolbar as action bar so we can hook things to it
        setSupportActionBar(toolbar);

        adapter = new MyRecyclerViewAdapter();

        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        mBookViewModel.getAllBooks().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        /* getSupportFragmentManager(): This method retrieves the support fragment manager for the current activity
           beginTransaction(): This method starts a new transaction to modify the fragment state.
           replace(R.id.frame_layout, new BookListFragment()): This method replaces the content of the R.id.frame_layout container with a new instance of the BookListFragment
           addToBackStack("f1"): This method adds the transaction to the back stack, which allows the user to navigate back to the previous fragment by pressing the back button
        */
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BookListFragment()).addToBackStack("f1").commit();

        
//        myListView =  findViewById(R.id.list_view);
//        // android.R.layout.simple_list_item_1 is just layout with one text view
//        // ArrayAdapter(context, layout, data source)
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
//        // set adapter to listview
//        myListView.setAdapter(adapter);

//        recyclerView = findViewById(R.id.recycler_view);
//
//        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
//        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
//
//
//        adapter = new MyRecyclerAdapter();
//        adapter.setData(listItems);
//        recyclerView.setAdapter(adapter);


        // hook the drawer and toolbar
        drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        //Alternative to assigning function to onClick event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                // use getApplicationContext() to get context as we are now in OnClickListener class
//                Toast.makeText(getApplicationContext(), "Added Book (" + bookTitle + ") with price (" + bookPrice + ")" , Toast.LENGTH_SHORT ).show();
                showToast();
                Book item = new Book(bookAuthor, bookDescription, bookTitle, bookISBN, bookPrice);

//                listItems.add(item);
                mBookViewModel.insert(item);
                myRef.push().setValue(item);
                counter++;
                // must include this line to notify change when adding item
                // Similar to commit / apply in SharedPreferences
                adapter.notifyDataSetChanged();
            }
        });


        // Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://bookslibrary-7d57f-default-rtdb.firebaseio.com/");
        myRef = database.getReference();
        mTable = myRef.child("books");
        mTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Touch Gestures
        myFrame = findViewById(R.id.touch_frame);

        gestureDetector = new GestureDetector(this, new MyGestureDetector());

        myFrame.setOnTouchListener((view, motionEvent) -> {

//                // returns an integer number (constant) that represents the type of the current event
//                int action = motionEvent.getActionMasked();
//
//                switch(action) {
//
//                    // this event is generated when the first touch on a view occurs
//                    case (MotionEvent.ACTION_DOWN):
//                        initial_x = (int) motionEvent.getX();
//                        initial_y = (int) motionEvent.getY();
//
//                        if (initial_x <= 120 && initial_y <= 120) { // Touch event is within top left area
//
//                            Author = findViewById(R.id.editTextTextPersonName4);
//                            bookAuthor = Author.getText().toString();
//                            Author.setText(bookAuthor.toUpperCase());
//                        }
//
//                        return true;
//
//                    // Any motion of the touch between the ACTION_DOWN and ACTION_UP events will be represented by this event
//                    case (MotionEvent.ACTION_MOVE):
//                        if (Math.abs(initial_y - motionEvent.getY()) < 40) {
//
//                            if (initial_x < motionEvent.getX()) { // moving left to right
//
//                                Price = findViewById(R.id.editTextNumberDecimal);
//                                bookPrice = Price.getText().toString();
//
//                                double doublePrice = Double.parseDouble(bookPrice);
//                                Price.setText(Double.toString(doublePrice + 1));
//
//                            }
//                        }
//                        return true;
//
//                    // this event is generated when the touch is lifted from the screen
//                    case (MotionEvent.ACTION_UP):
//                        if (Math.abs(initial_y - motionEvent.getY()) < 40) {
//
//                            if (initial_x > motionEvent.getX()) { // right to left
//
//                                showToast();
//                                Book item = new Book(bookAuthor, bookDescription, bookTitle, bookISBN, bookPrice);
//
//                                mBookViewModel.insert(item);
//                                myRef.push().setValue(item);
//                                counter++;
//                                adapter.notifyDataSetChanged();
//
//                            }
//
//                        } else if (Math.abs(initial_x - motionEvent.getX()) < 40) {
//
//                            if (initial_y > motionEvent.getY()) { // bottom to top
//                                clearText();
//
//                            }
//                            else{ // top to bottom
//                                finish();
//                            }
//                        }
//
//                        return true;
//
//                    default :
//                        return false;
//                }
            gestureDetector.onTouchEvent(motionEvent);

            return true;
        });

        //Login and register feature
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user == null){  // if user is null, open login activity and close main activity
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
            String currentUserEmail, message;
            currentUserEmail = user.getEmail();
            message = String.format("Welcome %s !", currentUserEmail);

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }


        // references to editTexts
        ID = findViewById(R.id.editTextTextPersonName);
        Title = findViewById(R.id.editTextTextPersonName2);
        ISBN = findViewById(R.id.editTextTextPersonName3);
        Author = findViewById(R.id.editTextTextPersonName4);
        Description = findViewById(R.id.editTextTextPersonName5);
        Price = findViewById(R.id.editTextNumberDecimal);


        // Request permissions to access SMS
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lab3", "onStart");

//        SharedPreferences myData = getPreferences(0);
////        Alternative if you need multiple preferences files identified by name
////        SharedPreferences myData = getSharedPreferences("f1",0);
//
//        //2 arguments - key and default value (used when value is null)
//        int bookID = myData.getInt("bookID",0);
//        String bookTitle = myData.getString("bookTitle","");
//        String bookISBN = myData.getString("bookISBN","");
//        String bookAuthor = myData.getString("bookAuthor","");
//        String bookDescription = myData.getString("bookDes","");
//        int bookPrice = myData.getInt("bookPrice",0);
//
//
//        ID = findViewById(R.id.editTextTextPersonName);
//        ID.setText(bookID);
//
//        Title = findViewById(R.id.editTextTextPersonName2);
//        Title.setText(bookTitle);
//
//        ISBN = findViewById(R.id.editTextTextPersonName3);
//        ISBN.setText(bookISBN);
//
//        Author = findViewById(R.id.editTextTextPersonName4);
//        Author.setText(bookAuthor);
//
//        Description = findViewById(R.id.editTextTextPersonName5);
//        Description.setText(bookDescription);
//
//        Price = findViewById(R.id.editTextNumberDecimal);
//        Price.setText(bookPrice);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lab3", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lab3", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lab3", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lab3", "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);

        Log.i("lab3", "onSaveInstanceState");

        Title = findViewById(R.id.editTextTextPersonName2);
        bookTitle = Title.getText().toString();

        ISBN = findViewById(R.id.editTextTextPersonName3);
        bookISBN = ISBN.getText().toString();

        //non-view data will not be automatically saved into bundle like view data
        outState.putString("bookTitle", bookTitle);
        outState.putString("bookISBN", bookISBN);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // force not saving view data during device reorientation
        if (false)
            super.onRestoreInstanceState(savedInstanceState);

        Log.i("lab3", "onRestoreInstanceState");

        Title = findViewById(R.id.editTextTextPersonName2);
        ISBN = findViewById(R.id.editTextTextPersonName3);

        //retrieves the saved data in bundle by providing corresponding key
        //and assigning it to bookTitle variable
        bookTitle = savedInstanceState.getString("bookTitle");
        bookISBN = savedInstanceState.getString("bookISBN");

        Title.setText(bookTitle);
        ISBN.setText(bookISBN);

        // empty other fields
        ID = findViewById(R.id.editTextTextPersonName);
        ID.setText(" ");

        Author = findViewById(R.id.editTextTextPersonName4);
        Author.setText(" ");

        Description = findViewById(R.id.editTextTextPersonName5);
        Description.setText(" ");

        Price = findViewById(R.id.editTextNumberDecimal);
        Price.setText(" ");
    }

    public void showToast(){

        Title = findViewById(R.id.editTextTextPersonName2);
        bookTitle = Title.getText().toString();

        Price = findViewById(R.id.editTextNumberDecimal);
        bookPrice = Price.getText().toString();

//        Toast myMessage = Toast.makeText(this, "Added Book (" + bookTitle + ") with price (" + bookPrice + ")" , Toast.LENGTH_SHORT );
//        myMessage.show();


        ID = findViewById(R.id.editTextTextPersonName);
        bookID = ID.getText().toString();

        ISBN = findViewById(R.id.editTextTextPersonName3);
        bookISBN = ISBN.getText().toString();

        Author = findViewById(R.id.editTextTextPersonName4);
        bookAuthor = Author.getText().toString();

        Description = findViewById(R.id.editTextTextPersonName5);
        bookDescription = Description.getText().toString();

        //Used to store data persistently
        //2 arguments, filename & mode (0 - private) if use getSharedPreferences
        SharedPreferences myData = getPreferences(0);

        //Create editor and bind to myData
        SharedPreferences.Editor myEditor = myData.edit();

        //store data - dictionary key-value concept
        myEditor.putString("bookID", bookID);
        myEditor.putString("bookTitle", bookTitle);
        myEditor.putString("bookISBN", bookISBN);
        myEditor.putString("bookAuthor", bookAuthor);
        myEditor.putString("bookDes", bookDescription);
        myEditor.putString("bookPrice", bookPrice);

        myEditor.apply();
    }

    public void clearText(){
        ID = findViewById(R.id.editTextTextPersonName);
        ID.setText(" ");

        Title = findViewById(R.id.editTextTextPersonName2);
        Title.setText(" ");

        ISBN = findViewById(R.id.editTextTextPersonName3);
        ISBN.setText(" ");

        Author = findViewById(R.id.editTextTextPersonName4);
        Author.setText(" ");

        Description = findViewById(R.id.editTextTextPersonName5);
        Description.setText(" ");

        Price = findViewById(R.id.editTextNumberDecimal);
        Price.setText(" ");
    }

    public void doublePrice(View v){

        Price = findViewById(R.id.editTextNumberDecimal);
        bookPrice = Price.getText().toString();

        double doublePrice = Double.parseDouble(bookPrice);

        Price.setText(Double.toString(doublePrice*2));
    }

    public void loadBook(){

        SharedPreferences myData = getPreferences(0);
//
        //2 arguments - key and default value (used when value is null)
        String bookID = myData.getString("bookID","");
        String bookTitle = myData.getString("bookTitle","");
        String bookISBN = myData.getString("bookISBN","");
        String bookAuthor = myData.getString("bookAuthor","");
        String bookDescription = myData.getString("bookDes","");
        String bookPrice = myData.getString("bookPrice","");


        ID = findViewById(R.id.editTextTextPersonName);
        ID.setText(bookID);

        Title = findViewById(R.id.editTextTextPersonName2);
        Title.setText(bookTitle);

        ISBN = findViewById(R.id.editTextTextPersonName3);
        ISBN.setText(bookISBN);

        Author = findViewById(R.id.editTextTextPersonName4);
        Author.setText(bookAuthor);

        Description = findViewById(R.id.editTextTextPersonName5);
        Description.setText(bookDescription);

        Price = findViewById(R.id.editTextNumberDecimal);
        Price.setText(bookPrice);
    }

    public void setISBN(View v){

        bookISBN = "00112233";

        ISBN = findViewById(R.id.editTextTextPersonName3);

        SharedPreferences myData = getPreferences(0);
        SharedPreferences.Editor myEditor = myData.edit();

        myEditor.putString("bookISBN", bookISBN);
        myEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // get id of selected menu item
        int id = item.getItemId();
        if (id == R.id.clearfields) {
            clearText();

        }
        else if (id == R.id.loaddata) {
            loadBook();

        }
        else if (id == R.id.totalbooks) {
//            int num = listItems.size();

            Toast.makeText(getApplicationContext(),"Total books: "+ counter, Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    // SimpleOnGestureListener is convenience class
    // a class that doesn't do anything itself, but provides
    // simplified access to other classes or groups of classes
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override  // Notified when a single-tap occurs.
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            ISBN = findViewById(R.id.editTextTextPersonName3);
            ISBN.setText(RandomString.generateNewRandomString(10));

            return super.onSingleTapConfirmed(e);
        }
        @Override  // Notified when a double-tap occurs.
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            clearText();

            return super.onDoubleTap(e);
        }

        @Override  // Notified when a scroll occurs with the initial on down MotionEvent and the current move MotionEvent.
        public boolean onScroll(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            /*
            e1: initial event (ACTION_DOWN) that triggered the scroll gesture
            e2: current motion event during the scroll gesture
            distanceX: X of previous e2 - X of current e2
            distanceY: Y of previous e2 - Y of current e2
            */
            if (Math.abs(e1.getY() - e2.getY()) < 40) { // horizontal scrolling
                Price = findViewById(R.id.editTextNumberDecimal);
                bookPrice = Price.getText().toString();
                double doublePrice = Double.parseDouble(bookPrice);

                Price.setText(Double.toString(doublePrice -= (int)distanceX));

            } else if (Math.abs(e1.getX() - e2.getX()) < 40) { // vertical scrolling
                Title = findViewById(R.id.editTextTextPersonName2);
                Title.setText("untitled");
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override  // Notified of a fling event when it occurs with the initial on down MotionEvent and the matching up MotionEvent.
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > 1000 || Math.abs(velocityY) > 1000){
                moveTaskToBack(true);
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override  // Notified when a long press occurs with the initial on down MotionEvent that triggered it.
        public void onLongPress(@NonNull MotionEvent e) {
            super.onLongPress(e);
            loadBook();
        }


    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get id of selected menu item
            int id = item.getItemId();
            if (id == R.id.addbook){
                showToast();
                Book item1 = new Book(bookAuthor, bookDescription, bookTitle, bookISBN, bookPrice);

//                listItems.add(item1);
                mBookViewModel.insert(item1);
                myRef.push().setValue(item1);
                counter++;
                adapter.notifyDataSetChanged();

            }
            else if (id == R.id.removelastbook){
//                listItems.remove(listItems.size() - 1);
                mBookViewModel.removeLastBook();
                counter--;
                adapter.notifyDataSetChanged();

            }
            else if (id == R.id.removeallbooks){
//                listItems.clear();
                mBookViewModel.deleteAll();
                myRef.removeValue();
                counter = 0;
                adapter.notifyDataSetChanged();

            }
            else if (id == R.id.listall){
                // launches the BookListActivity from the current activity using an intent.
                Intent i = new Intent(getApplicationContext(), BookListActivity.class);
                startActivity(i);

            }
            else if (id == R.id.deleteexpensivebook){
                mBookViewModel.deleteExpensiveBook();
                adapter.notifyDataSetChanged();

            }
            else if (id == R.id.logout){
                // sign out and move to login activity
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();

            }
            else if (id == R.id.closeapp){
                finish();

            }
            // close the drawer
            drawer.closeDrawers();
            // tell the OS
            return true;
        }
    }

    class MyBroadCastReceiver extends BroadcastReceiver {


        // This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
        @Override
        public void onReceive(Context context, Intent intent) {

            // Retrieve the message from the intent using key
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the values separated by |
             * */
            StringTokenizer sT = new StringTokenizer(msg, "|");

            String bookID = sT.nextToken();
            String bookTitle = sT.nextToken();
            String bookISBN = sT.nextToken();
            String bookAuthor = sT.nextToken();
            String bookDescription = sT.nextToken();
            String bookPrice = sT.nextToken();
            // convert string to its boolean value
            boolean priceIncrease = Boolean.parseBoolean(sT.nextToken());

            double doublePrice = Double.valueOf(bookPrice);

            if (priceIncrease){
                Price.setText(Double.toString(doublePrice + 100));
            }
            else {
                Price.setText(Double.toString(doublePrice + 5));
            }


            //Now, its time to update the UI
            ID.setText(bookID);
            Title.setText(bookTitle);
            ISBN.setText(bookISBN);
            Author.setText(bookAuthor);
            Description.setText(bookDescription);
        }
    }
}