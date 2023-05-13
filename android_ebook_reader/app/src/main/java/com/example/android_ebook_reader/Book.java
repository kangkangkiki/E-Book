package com.example.android_ebook_reader;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String author;
    public String bookPath; // 添加 bookPath 属性

    public Book() {
    }

    @Ignore
    public Book(String title, String author, String bookPath) { // 修改构造函数
        this.title = title;
        this.author = author;
        this.bookPath = bookPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // 添加 getBookPath() 方法
    public String getBookPath() {
        return bookPath;
    }

    // 添加 setBookPath() 方法
    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }
}
