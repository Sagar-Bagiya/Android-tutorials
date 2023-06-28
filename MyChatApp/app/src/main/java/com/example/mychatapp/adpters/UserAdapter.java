package com.example.mychatapp.adpters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp.R;
import com.example.mychatapp.models.ChatMessage;
import com.example.mychatapp.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> userList;
    private Context context;

    String lastMessageTime;
    String lastMessage;

    private OnItemClickListener clickListener;


    public UserAdapter( Context context, List<UserModel> userList) {
        this.userList = userList;
        this.context = context;
    }


    public UserAdapter(List<UserModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.bind(user);
        lastMessage(position , holder.tvLastMessage, holder.tvLastMessageTime);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName;
        private TextView tvLastMessageTime;
        private TextView tvLastMessage;
        private ConstraintLayout myCard;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvName);
            tvLastMessageTime = itemView.findViewById(R.id.tvLastMessageTime);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            myCard = itemView.findViewById(R.id.myCard);
            myCard.setOnClickListener(this);

        }

        public void bind(UserModel user) {
            textViewName.setText(user.getName());
            tvLastMessage.setText(user.getLastMessage());
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.myCard)
                clickListener.onItemClick(userList.get(getAdapterPosition()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }

    public  void lastMessage(int position , TextView tvLastMessage, TextView tvLastMessageTime){
        String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverID = userList.get(position).getuId();
        FirebaseDatabase.getInstance().getReference("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ChatMessage message = snapshot1.getValue(ChatMessage.class);
                    if ((message.getSenderId().equals(senderId)&&message.getReceiverId().equals(receiverID)) || (message.getSenderId().equals(receiverID)&&message.getReceiverId().equals(senderId))){
                        lastMessage = message.getText();
                        lastMessageTime = message.getMessageTime();
                    }
                }

                if (lastMessage != null){
                    tvLastMessage.setText(lastMessage);
                    lastMessage = null;
                }
                else
                    tvLastMessage.setVisibility(View.GONE);

                if (lastMessageTime != null){
                    tvLastMessageTime.setText(lastMessageTime);
                    lastMessageTime = null;
                }
                else
                    tvLastMessageTime.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
