package co.rprayoga.samplechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;

import java.util.List;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> {
    private List<DummyData> dummyDataList;
    private List<QiscusChatRoom> chatRoomList;
    public ListChatAdapter( List<QiscusChatRoom> data ) {
        chatRoomList = data;
    }

    public void updateAdapterData(List<QiscusChatRoom> data){
        chatRoomList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_chat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  ListChatAdapter.ViewHolder holder, int position) {

        QiscusChatRoom q = chatRoomList.get(position);
        holder.tvLastChat.setText(q.getLastComment().getTime().toString());
        holder.tvUsername.setText(q.getName());
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvLastChat;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvItemUserName);
            tvLastChat = itemView.findViewById(R.id.tvItemLastChat);
        }
    }
}
