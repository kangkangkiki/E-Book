package com.example.android_ebook_reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private AppDao appDao;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();
        appDao = appDatabase.appDao();

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (validateCredentials(username, password, confirmPassword)) {
                    User newUser = new User(username, password);
                    new RegisterUserTask().execute(newUser);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败，请检查输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateCredentials(String username, String password, String confirmPassword) {
        // 验证用户名和密码的逻辑
        // 示例：判断用户名和密码是否为空，以及两次输入的密码是否一致
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && password.equals(confirmPassword);
    }

    private class RegisterUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            appDao.insertUser(users[0]);
            return null;
        }
    }
}
