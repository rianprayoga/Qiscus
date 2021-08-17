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
import com.qiscus.sdk.chat.core.data.model.QiscusComment;

import java.util.ArrayList;
import java.util.List;



public class ChatAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<QiscusComment> chatList;
    private Context context;

    private static final String TAG = "ChatAdapter";

    public void setData(List<QiscusComment> data){
        chatList = data;
        notifyDataSetChanged();
    }

    public ChatAdapter(Context context) {
        chatList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        String userEmail = QiscusCore.getQiscusAccount().getEmail();
        if(chatList.get(position).getSenderEmail().equals(userEmail)){
            return VIEW_TYPE_MESSAGE_SENT;
        }else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_send, parent, false);
            return new SentMessageHolder(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_receive, parent, false);
            return new ReceivedMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        boolean setVisibleSender = false;
        if(getItemCount() > 1 && position > 0){
            if(chatList.get(position - 1).getSenderEmail().equals(chatList.get(position).getSenderEmail())){
                setVisibleSender = true;
            }
        }

        if(holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT){
            ((SentMessageHolder)holder).bind(chatList.get(position));
        }else{
            ((ReceivedMessageHolder)holder).bind(chatList.get(position), setVisibleSender);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    private static class SentMessageHolder extends RecyclerView.ViewHolder{
        private TextView tvSender, tvMessage;
        public SentMessageHolder(View itemView) {
            super(itemView);
//            tvSender = itemView.findViewById(R.id.tvSender);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }

        void bind(QiscusComment chat){
//            if(setVisibleSender) tvSender.setVisibility(View.GONE);
            tvMessage.setText(chat.getMessage());
//            tvSender.setText(chat.getSender());
        }
    }

    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder{
        private TextView tvSender, tvMessage;
        public ReceivedMessageHolder( View itemView) {
            super(itemView);
            tvSender = itemView.findViewById(R.id.tvSender);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }

        void bind(QiscusComment chat, boolean setVisibleSender){
            if(setVisibleSender) tvSender.setVisibility(View.GONE);
            tvMessage.setText(chat.getMessage());
            tvSender.setText(chat.getSender());
        }
    }
}
