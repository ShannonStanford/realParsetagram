package com.example.shannonyan.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shannonyan.parsetagram.model.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

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
        holder.tvCaption.setText(post.getUser().getUsername() + "\n" + post.getDescription());
        holder.tvCreatedAt.setText(post.getCreatedAt().toString());

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPicture);

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile pic = currentUser.getParseFile("ProfilePicture");

        if( pic != null) {
            String url = pic.getUrl();
            Glide.with(context).load(url).into(holder.ivProfilePic);
        }
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

            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = mPosts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetails.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                // show the activity
                context.startActivity(intent);
            }

        }
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}
