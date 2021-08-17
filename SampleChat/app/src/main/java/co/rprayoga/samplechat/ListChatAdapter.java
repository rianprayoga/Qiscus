package co.rprayoga.samplechat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qiscus.sdk.chat.core.QiscusCore;
import com.qiscus.sdk.chat.core.data.model.QiscusAccount;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
import com.qiscus.sdk.chat.core.data.remote.QiscusApi;

import java.util.List;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> {
    private static final String TAG = "ListChatAdapter";
    private List<QiscusChatRoom> chatRoomList;
    private Context context;
    private SetOnClickListener listener;

    public interface SetOnClickListener{
        void onItemClickListener(int position);
    }

    public ListChatAdapter( List<QiscusChatRoom> data, Context context, SetOnClickListener listener  ) {
        this.context = context;
        chatRoomList = data;
        this.listener = listener;
    }

    public List<QiscusChatRoom> getData(){
        return chatRoomList;
    }

    public void updateAdapterData(List<QiscusChatRoom> data){
        chatRoomList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_list_chat, parent, false);
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull  ListChatAdapter.ViewHolder holder, int position) {
        QiscusChatRoom q = chatRoomList.get(position);
        String userEmail = QiscusCore.getQiscusAccount().getEmail();
        String opponent = Helper.getOpponent(userEmail, q.getMember());
        Log.e(TAG, "onBindViewHolder: " + q.getMember().toString() );
        holder.tvLastChat.setText(q.getLastComment().getTime().toString());
        holder.tvUsername.setText(opponent);
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUsername, tvLastChat;
        SetOnClickListener listener;
        public ViewHolder(@NonNull  View itemView, SetOnClickListener listener) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvItemUserName);
            tvLastChat = itemView.findViewById(R.id.tvItemLastChat);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClickListener(getAdapterPosition());
        }


    }

}
