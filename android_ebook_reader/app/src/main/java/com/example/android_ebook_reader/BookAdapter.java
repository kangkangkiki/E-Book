package com.example.android_ebook_reader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.util.Log;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private OnItemClickListener onItemClickListener;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Log.d("BookAdapter", "Book at position " + position + ": " + bookList.get(position));

        Book book = bookList.get(position);
        holder.titleTextView.setText(book.getTitle());
        // 如果有封面图片，可以在此设置封面图片
        // holder.coverImageView.setImageResource(book.getCoverImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (onItemClickListener != null && currentPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(v, currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title_text_view);
        }
    }

}
