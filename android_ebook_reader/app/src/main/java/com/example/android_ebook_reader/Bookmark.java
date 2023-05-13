package com.example.android_ebook_reader;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Index;

@Entity(tableName = "bookmarks",
        foreignKeys = {
                @ForeignKey(entity = Book.class,
                        parentColumns = "id",
                        childColumns = "bookId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("bookId")})
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int bookId;
    public int pageNumber;
    public int userId;
    public String bookTitle;
    public String bookAuthor;

    public Bookmark() {
    }

    @Ignore
    public Bookmark(int userId, int bookId, int pageNumber, String bookTitle, String bookAuthor) {
        this.userId = userId;
        this.bookId = bookId;
        this.pageNumber = pageNumber;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
