package com.example.android_ebook_reader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;


public class ProfileActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private Button updateButton;
    private Toolbar profileToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d("ProfileActivity", "onCreate called");

        profileToolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人资料");

        usernameEditText = findViewById(R.id.username_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        updateButton = findViewById(R.id.update_button);

        initData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里获取用户修改后的信息，将其更新到 SharedPreferences 和数据库中，并提示用户更新成功
            }
        });
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
        // 在这里从 SharedPreferences 中获取用户信息并显示
    }
}
