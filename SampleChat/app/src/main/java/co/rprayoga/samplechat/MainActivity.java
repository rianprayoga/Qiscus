package co.rprayoga.samplechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qiscus.sdk.chat.core.QiscusCore;
import com.qiscus.sdk.chat.core.data.model.QiscusAccount;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnLogin;
    private static final String TAG = "MainActivity";
//    private Context context = getApplicationContext();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name),
                MODE_PRIVATE);
        if (sharedPreferences.getString(getString(R.string.user_email), null) != null){
            startActivity(new Intent(this, ListChatActivity.class));
            finish();
//            Log.d(TAG, "onCreate: " + sharedPreferences.getString(getString(R.string.user_email),null));
//            Log.d(TAG, "onCreate: " + sharedPreferences.getString(getString(R.string.user_name),null));
        }
        btnLogin.setOnClickListener(view -> {
            if (!etEmail.getText().toString().isEmpty() &&
                    !etPassword.getText().toString().isEmpty() &&
                    !etUsername.getText().toString().isEmpty()) {

                String tmpEmail = "john.baum@none.mail.com";
                String tmpUserName = "JohnBaum";
                String tmpPswd = "12345";

//                String tmpEmail = "john.doe@none.mail.com";
//                String tmpUserName = "JohnDoe";
//                String tmpPswd = "12345";
//                chatroomId 49310180
//                QiscusCore.setUser(etEmail.getText().toString(), etPassword.getText().toString())
                QiscusCore.setUser(tmpEmail, tmpPswd)
                        .withUsername(tmpUserName)
                        .save(new QiscusCore.SetUserListener() {
                            @Override
                            public void onSuccess(QiscusAccount qiscusAccount) {
                                setUserToPreference(qiscusAccount);
                                startActivity(new Intent(getBaseContext(), ListChatActivity.class));
                                finish();
                            }

                            @Override
                            public void onError(Throwable throwable) {
//                                Log.e(TAG, "onError: ", throwable);
                            }
                        });
            }

        });

    }
    void setUserToPreference(QiscusAccount qa){

        sharedPreferences.edit()
                .putString(getString(R.string.user_avatar), qa.getAvatar())
                .putString(getString(R.string.user_email), qa.getEmail())
                .putString(getString(R.string.user_name), qa.getUsername()).apply();
    }
    void checkUser(){
//        sharedPreferences.getString(getString(R.string.user_email), null);
    }
}