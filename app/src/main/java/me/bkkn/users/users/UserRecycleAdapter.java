package me.bkkn.users.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.bkkn.users.R;

public class UserRecycleAdapter extends RecyclerView.Adapter<UserRecycleAdapter.UserViewHolder> {
    private ArrayList<User>userList = new ArrayList<>();
    private LoadMoreListener loadMoreListener;


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        Glide.with(holder.itemView.getContext())
            .load(user.getAvatar())
            .apply(RequestOptions.circleCropTransform()
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_placeholder))
            .into(holder.avatar);
        if(position == getItemCount() - 1 && loadMoreListener != null){
            loadMoreListener.loadMoreUsers(user.getUserId());
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUsers(List<User> users){
        int lastPosition = getItemCount() - 1;
        userList.addAll(users);
        notifyItemRangeInserted(lastPosition,users.size());
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView avatar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            avatar = itemView.findViewById(R.id.image_avatar);
        }
    }

    public interface LoadMoreListener {
        public void loadMoreUsers(long userId);

    }
}
