package com.example.android_ebook_reader;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.folioreader.FolioReader;
import java.lang.ref.WeakReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import nl.siegmann.epublib.epub.EpubReader;
import com.example.android_ebook_reader.Book;
import java.io.FileInputStream;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Book> bookList;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

        initData();

        //bookAdapter = new BookAdapter(bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Book book = bookList.get(position);

                // 使用 FolioReader 打开 EPUB 文件
                FolioReader folioReader = FolioReader.get();
                folioReader.openBook(book.getBookPath());

                // 启动 ReaderActivity
                Intent intent = new Intent(MainActivity.this, ReaderActivity.class);
                intent.putExtra("bookPath", book.getBookPath());
                startActivity(intent);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DownloadActivity.class));
            }
        });

        Log.d("MainActivity", "RecyclerView: " + recyclerView);
        Log.d("MainActivity", "BookAdapter: " + bookAdapter);

    }

    private void initData() {
        // 初始化 bookList 为空 ArrayList
        bookList = new ArrayList<>();

        bookAdapter = new BookAdapter(bookList);

        recyclerView.setAdapter(bookAdapter);
        // 使用 LoadBooksTask 从数据库中获取本地书籍信息
        new LoadBooksTask(this).execute();
        // 从assets文件夹中获取电子书文件

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Log.d("MainActivity", "Profile menu item clicked");
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            case R.id.action_bookmarks:
                Log.d("MainActivity", "Bookmarks menu item clicked");
                startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static class LoadBooksTask extends AsyncTask<Void, Void, List<Book>> {
        private WeakReference<MainActivity> activityReference;

        LoadBooksTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Book> doInBackground(Void... voids) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;

            AppDatabase db = AppDatabase.getInstance(activity);
            List<Book> books = db.appDao().getAllBooks();

            // 从assets文件夹中获取电子书文件
            activity.getEbookFilesFromAssets();

            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            if (books != null) {
                activity.bookList = books;
                // 更新 UI，例如刷新书籍列表
                activity.bookAdapter.setBookList(activity.bookList);
                activity.bookAdapter.notifyDataSetChanged();
            }
            Log.d("MainActivity", "bookList: " + activity.bookList.toString());

        }
    }



    private void getEbookFilesFromAssets() {
        AssetManager assetManager = getAssets();
        try {
            String[] files = assetManager.list("ebooks");
            if (files != null) {
                for (String file : files) {
                    Log.i("MainActivity", "电子书文件: " + file);
                    // 将电子书文件从assets文件夹复制到本地存储，并获取本地路径
                    String localPath = copyAssetToLocal(file);

                    // 使用epublib读取电子书的元数据
                    EpubReader epubReader = new EpubReader();
                    try {
                        InputStream inputStream = new FileInputStream(localPath);
                        nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(inputStream);

                        // 创建新的Book对象，并将其添加到书籍列表中
                        Book book = new Book();
                        book.setBookPath(localPath);

                        // 设置书籍对象的标题
                        String title = epubBook.getTitle();
                        book.setTitle(title);

                        // 设置其他Book对象的属性，如作者等（如果有）
                        // ...
                        // 添加到数据库
                        AppDatabase db = AppDatabase.getInstance(this);
                        db.appDao().insertBook(book);

                        bookList.add(book);
                        Log.d("MainActivity", "bookList: " + bookList.toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 更新UI，例如刷新书籍列表
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bookAdapter.notifyDataSetChanged();
                    }

                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("MainActivity", "bookList: " + bookList.toString());

    }


    private String copyAssetToLocal(String fileName) {
        String localPath = null;
        try {
            // 获取文件输入流
            InputStream inputStream = getAssets().open("ebooks/" + fileName);
            // 在内部存储中创建一个与assets中同名的文件
            File outputFile = new File(getFilesDir(), fileName);
            localPath = outputFile.getAbsolutePath();
            // 获取文件输出流
            OutputStream outputStream = new FileOutputStream(outputFile);
            // 从输入流读取数据并写入到输出流
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            // 关闭输入输出流
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localPath;
    }
}
