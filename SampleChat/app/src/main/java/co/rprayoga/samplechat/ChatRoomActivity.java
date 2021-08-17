package co.rprayoga.samplechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import com.qiscus.sdk.chat.core.QiscusCore;
import com.qiscus.sdk.chat.core.data.model.QiscusAccount;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
import com.qiscus.sdk.chat.core.data.model.QiscusComment;
import com.qiscus.sdk.chat.core.data.model.QiscusRoomMember;
import com.qiscus.sdk.chat.core.data.remote.QiscusApi;
import com.qiscus.sdk.chat.core.event.QiscusCommentReceivedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static co.rprayoga.samplechat.Helper.getOpponent;

public class ChatRoomActivity extends AppCompatActivity {
    private static final String TAG = "ChatRoomActivity";
    private QiscusChatRoom qiscusChatRoom;
    private String opponent = null;
    private EditText etMessage;
    private Button btnSend;
    private RecyclerView rvMessages;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        rvMessages = findViewById(R.id.rvChat);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(this);
        rvMessages.setAdapter(adapter);

        qiscusChatRoom = getIntent().getParcelableExtra("chat_room");
        String user = QiscusCore.getQiscusAccount().getEmail();
        opponent = getOpponent(user, qiscusChatRoom.getMember());

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(opponent);
        }

        btnSend.setOnClickListener(view -> {
            if(!etMessage.getText().toString().isEmpty()){
//                Log.d(TAG, "onCreate() returned: " + "send ");
                String message = etMessage.getText().toString();
                QiscusComment qiscusComment = QiscusComment
                        .generateMessage(qiscusChatRoom.getId() , message);
                QiscusApi.getInstance()
                        .sendMessage(qiscusComment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(qiscusComment1 -> {
                            etMessage.getText().clear();
                        }, throwable -> {

                        });
            }
        });

        loadMessages(qiscusChatRoom);
    }

    public void loadMessages(QiscusChatRoom qiscusChatRoom){
        QiscusApi.getInstance()
                .getChatRoomWithMessages(qiscusChatRoom.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chatRoomListPair -> {
                    List<QiscusComment> c = chatRoomListPair.second;
                    Collections.reverse(c);
                    adapter.setData(c);
                });
    }

    @Subscribe
    public void onReceiveComment(QiscusCommentReceivedEvent event) {
        QiscusComment c = event.getQiscusComment();
        adapter.
        Log.e(TAG, "onReceiveComment: " + c.getSenderEmail() );
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}