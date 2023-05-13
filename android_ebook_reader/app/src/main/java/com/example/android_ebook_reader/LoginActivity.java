package com.example.android_ebook_reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginUser(username, password);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(final String username, final String password) {
        new LoginTask(LoginActivity.this).execute(username, password);
    }

    private static class LoginTask extends AsyncTask<String, Void, User> {
        private WeakReference<LoginActivity> activityReference;

        LoginTask(LoginActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected User doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            LoginActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;

            return activity.appDatabase.appDao().getUser(username, password);
        }

        @Override
        protected void onPostExecute(User user) {
            LoginActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            if (user != null) {
                // 登录成功，执行相应的操作
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            } else {
                // 登录失败，提示用户
                Toast.makeText(activity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
