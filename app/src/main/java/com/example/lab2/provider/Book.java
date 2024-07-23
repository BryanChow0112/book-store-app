package com.example.lab2.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Each entity is a Java class that defines a table in the database.

@Entity(tableName = Book.TABLE_NAME)
public class Book {

    public static final String TABLE_NAME = "books";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    private int cardID;

    @ColumnInfo(name = "Author")
    private String cardAuthor;

    @ColumnInfo(name = "Description")
    private String cardDesc;
    @ColumnInfo(name = "Title")
    private String cardTitle;

    @ColumnInfo(name = "ISBN")
    private String cardISBN;

    @ColumnInfo(name = "Price")
    private String cardPrice;


    public Book(){}

    public Book(String author, String description, String title, String ISBN, String price) {

        this.cardAuthor = author;
        this.cardDesc = description;
        this.cardTitle = title;
        this.cardISBN = ISBN;
        this.cardPrice = price;
    }

    @NonNull
    public int getCardID() {
        return cardID;
    }

    public void setCardID(@NonNull int cardID) {
        this.cardID = cardID;
    }

    public String getCardAuthor() {
        return cardAuthor;
    }

    public void setCardAuthor(String cardAuthor) {
        this.cardAuthor = cardAuthor;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardISBN() {
        return cardISBN;
    }

    public void setCardISBN(String cardISBN) {
        this.cardISBN = cardISBN;
    }

    public String getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(String cardPrice) {
        this.cardPrice = cardPrice;
    }
}
