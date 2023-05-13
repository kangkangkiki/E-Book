package com.example.android_ebook_reader;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {
    // User
    @Insert
    long insertUser(User user);

    @Update
    int updateUser(User user);

    @Delete
    int deleteUser(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User getUser(String username, String password);

    // Book
    @Insert
    long insertBook(Book book);

    @Update
    int updateBook(Book book);

    @Delete
    int deleteBook(Book book);

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();

    // Bookmark
    @Insert
    long insertBookmark(Bookmark bookmark);

    @Update
    int updateBookmark(Bookmark bookmark);

    @Delete
    int deleteBookmark(Bookmark bookmark);

    @Query("SELECT * FROM bookmarks WHERE userId = :userId")
    List<Bookmark> getBookmarksByUserId(int userId);

}
