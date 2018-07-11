package com.example.shannonyan.parsetagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shannonyan.parsetagram.model.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>  {

    private List<Post> mPosts;
    Context context;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;

    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        final Post post = mPosts.get(position);

        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getDescription());
        holder.tvCreatedAt.setText(post.getCreatedAt().toString());
        Log.d("PostAdapter", "gets here");

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPicture);

        //addProfilePicLater







    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.ivPicture) ImageView ivPicture;
        @BindView(R.id.ivProfilePic) ImageView ivProfilePic;
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.btLike) Button btLike;
        @BindView(R.id.btComment) Button btComment;
        @BindView(R.id.btShare) Button btShare;
        @BindView(R.id.tvCaption) TextView tvCaption;
        @BindView(R.id.tvCreatedAt) TextView tvCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
