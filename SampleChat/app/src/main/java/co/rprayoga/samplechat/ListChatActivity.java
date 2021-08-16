package co.rprayoga.samplechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.qiscus.sdk.chat.core.QiscusCore;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
import com.qiscus.sdk.chat.core.data.remote.QiscusApi;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ListChatActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String TAG = "ListChatActivity";
    private RecyclerView rvChatRooms;
    private ListChatAdapter listChatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);

        rvChatRooms = findViewById(R.id.rvChatRooms);

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE);
        String tmp = sharedPreferences.getString(getString(R.string.user_email), null);
        getSupportActionBar().setTitle(tmp);

        listChatAdapter = new ListChatAdapter(new ArrayList<>());
        rvChatRooms.setLayoutManager(new LinearLayoutManager(this));
        rvChatRooms.setAdapter(listChatAdapter);

        loadAllChatRooms();
    }

    public void loadAllChatRooms(){
        QiscusApi.getInstance()
                .getAllChatRooms(true,false,true,1,100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<QiscusChatRoom>>() {
                    @Override
                    public void call(List<QiscusChatRoom> qiscusChatRooms) {
                        listChatAdapter.updateAdapterData(qiscusChatRooms);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            QiscusCore.clearUser();
            sharedPreferences.edit().clear().apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else if(item.getItemId() == R.id.action_create_room){
            chatUser();
        }
        return super.onOptionsItemSelected(item);
    }

    public void chatUser(){
//        String tmpEmail = "john.baum@none.mail.com";
        String tmpEmail = "john.doe@none.mail.com";
        QiscusApi.getInstance()
                .chatUser(tmpEmail,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<QiscusChatRoom>() {
                    @Override
                    public void call(QiscusChatRoom qiscusChatRoom) {
                        Log.e(TAG, "call: " + qiscusChatRoom.getId() );
                    }
                });
    }
}