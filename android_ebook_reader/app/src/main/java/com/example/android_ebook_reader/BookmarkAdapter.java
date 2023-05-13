package com.example.android_ebook_reader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private List<Bookmark> bookmarkList;

    public BookmarkAdapter(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = bookmarkList.get(position);
        holder.titleTextView.setText(bookmark.getBookTitle());
        holder.authorTextView.setText(bookmark.getBookAuthor());

    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView authorTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title_text_view);
            authorTextView = itemView.findViewById(R.id.book_author_text_view);
        }
    }
}
