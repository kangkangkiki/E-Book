package com.example.android_ebook_reader;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();

    @Insert
    void insert(Book book);

    // 如有需要，可以添加其他数据访问方法，例如更新和删除
}
