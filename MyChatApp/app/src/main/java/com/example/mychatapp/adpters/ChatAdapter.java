package com.example.mychatapp.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp.R;
import com.example.mychatapp.models.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatMessage> messageList = new ArrayList<>();
    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECEIVER = 2;
    private RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    // Constructor
    public ChatAdapter(List<ChatMessage> messageList, RecyclerView recyclerView) {
        this.messageList = messageList;
        this.recyclerView = recyclerView;

    }

    public ChatAdapter() {
    }


    // ViewHolder classes
    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView tvSenderMessage;
        TextView tvMessageTime;

        public SenderViewHolder(View itemView) {
            super(itemView);
            tvSenderMessage = itemView.findViewById(R.id.tvSender);
            tvMessageTime = itemView.findViewById(R.id.tvMessageTime);
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView tvReceiverMessage;
        TextView tvMessageTime;

        public ReceiverViewHolder(View itemView) {
            super(itemView);
            tvReceiverMessage = itemView.findViewById(R.id.tvReceiver);
            tvMessageTime = itemView.findViewById(R.id.tvMessageTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messageList.get(position);
        if (message.getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_RECEIVER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_SENDER) {
            View view = inflater.inflate(R.layout.item_sender_message, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_receiver_message, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        if (holder instanceof SenderViewHolder) {
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.tvSenderMessage.setText(message.getText());
            senderViewHolder.tvMessageTime.setText(message.getMessageTime());
        } else if (holder instanceof ReceiverViewHolder) {
            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.tvReceiverMessage.setText(message.getText());
            receiverViewHolder.tvMessageTime.setText(message.getMessageTime());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // Method to add a new message to the list
    public void addMessage(ChatMessage message) {
        messageList.add(message);
        notifyItemInserted(messageList.size()-1);
        scrollToLastPosition();
    }

    // Method to clear the message list
    public void clearMessages() {
        int size = messageList.size();
        messageList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public List<ChatMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    // Method to scroll to the last position
    private void scrollToLastPosition() {
        if (recyclerView != null && messageList != null && messageList.size() > 0) {
            int lastPosition = messageList.size() - 1;
            recyclerView.scrollToPosition(lastPosition);
        }
    }
}
