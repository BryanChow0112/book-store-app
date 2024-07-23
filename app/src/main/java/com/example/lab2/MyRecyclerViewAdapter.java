package com.example.lab2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.provider.Book;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<Book> data = new ArrayList<Book>();

    public MyRecyclerViewAdapter(){

    }

    public void setData(List<Book> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("week6App","onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("week6App","onBindViewHolder");

        holder.count.setText("Position: " + position);
        holder.cardID.setText("ID: " + data.get(position).getCardID());
        holder.cardAuthor.setText("Author: " + data.get(position).getCardAuthor());
        holder.cardDesc.setText("Desc: " + data.get(position).getCardDesc());
        holder.cardTitle.setText("Title: " + data.get(position).getCardTitle());
        holder.cardISBN.setText("ISBN: " + data.get(position).getCardISBN());
        holder.cardPrice.setText("Price: " + data.get(position).getCardPrice());

//        final int fPosition = position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView count, cardID, cardAuthor, cardDesc, cardTitle, cardISBN, cardPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            count = itemView.findViewById(R.id.count);
            cardID = itemView.findViewById(R.id.card_bookID);
            cardAuthor = itemView.findViewById(R.id.card_bookAuthor);
            cardDesc = itemView.findViewById(R.id.card_bookDesc);
            cardTitle = itemView.findViewById(R.id.card_bookTitle);
            cardISBN = itemView.findViewById(R.id.card_bookISBN);
            cardPrice = itemView.findViewById(R.id.card_bookPrice);
        }
    }
}