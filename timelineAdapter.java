package com.kly.mjstargram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class timelineAdapter extends RecyclerView.Adapter<timelineAdapter.ViewHolder> {

    List<Post> mPostList;
    Context mContext;

    public timelineAdapter(Context context,List<Post> postList){
        this.mContext=context;
        this.mPostList=postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_timeline,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post=mPostList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profileIv;
        TextView emailTv;
        ImageView imageIv;
        TextView messageTv;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            profileIv=itemView.findViewById(R.id.profile_iv);
            emailTv=itemView.findViewById(R.id.email_tv);
            messageTv=itemView.findViewById(R.id.message_tv);
            imageIv=itemView.findViewById(R.id.image_iv);
        }

        public void bind(Post post){
            emailTv.setText(post.getWriterId());
            messageTv.setText(post.getMessage());
            Glide.with(imageIv).load(post.getImageUrl()).into(imageIv);
        }

    }
}
