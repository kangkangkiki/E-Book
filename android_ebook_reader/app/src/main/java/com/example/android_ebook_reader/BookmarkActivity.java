package com.example.android_ebook_reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Bookmark> bookmarkList;
    private BookmarkAdapter bookmarkAdapter;
    private Toolbar bookmarkToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        bookmarkToolbar = findViewById(R.id.bookmark_toolbar);
        setSupportActionBar(bookmarkToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("书签");

        recyclerView = findViewById(R.id.recycler_view);

        initData();
        bookmarkAdapter = new BookmarkAdapter(bookmarkList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookmarkAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        // 在这里从数据库中获取书签信息
        bookmarkList = new ArrayList<>();
    }
}
