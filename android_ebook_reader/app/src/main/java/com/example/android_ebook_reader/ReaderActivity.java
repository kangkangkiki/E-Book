package com.example.android_ebook_reader;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.folioreader.FolioReader;


public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        // 获取传递过来的 bookPath
        String bookPath = getIntent().getStringExtra("bookPath");

        try {
            // 使用 FolioReader 打开电子书
            FolioReader folioReader = FolioReader.get();
            folioReader.openBook(bookPath);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常，例如显示错误消息或返回到上一个 Activity
        }
    }
}