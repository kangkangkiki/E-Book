package com.example.android_ebook_reader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DownloadActivity extends AppCompatActivity {

    private EditText etBookUrl;
    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etBookUrl = findViewById(R.id.et_book_url);
        btnDownload = findViewById(R.id.btn_download);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookUrl = etBookUrl.getText().toString();
                if (!bookUrl.isEmpty()) {
                    downloadAndSaveBook(bookUrl);
                } else {
                    Toast.makeText(DownloadActivity.this, "请输入书籍链接", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downloadAndSaveBook(String bookUrl) {
        // 实现下载功能，下载完成后将书籍保存到设备的存储中
        // 这里需要您根据实际情况编写下载逻辑

        // 书籍下载完成后，获取书籍的信息（如路径、标题、作者等）
        // 这里需要您根据实际情况获取书籍信息

        // 将书籍信息保存到数据库中
        Book book = new Book("书籍标题", "书籍作者", "书籍文件路径");
        // 设置书籍文件路径
        book.setBookPath("书籍文件路径");
        AppDatabase db = AppDatabase.getInstance(this);
        db.appDao().insertBook(book);

        Toast.makeText(DownloadActivity.this, "书籍下载成功", Toast.LENGTH_SHORT).show();
    }
}
