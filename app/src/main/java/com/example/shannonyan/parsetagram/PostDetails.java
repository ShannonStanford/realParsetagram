package com.example.shannonyan.parsetagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shannonyan.parsetagram.model.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetails extends AppCompatActivity {
    Post post;
    Context context;

    @BindView(R.id.ivPicture) ImageView ivPicture;
    @BindView(R.id.ivProfilePic) ImageView ivProfilePic;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.btLike) Button btLike;
    @BindView(R.id.btComment) Button btComment;
    @BindView(R.id.btShare) Button btShare;
    @BindView(R.id.tvCaption) TextView tvCaption;
    @BindView(R.id.tvCreatedAt) TextView tvCreatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        context = getBaseContext();

        ButterKnife.bind(this );

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        tvUsername.setText(post.getUser().getUsername());
        tvCaption.setText(post.getUser().getUsername() + "\n" + post.getDescription());
        tvCreatedAt.setText(post.getCreatedAt().toString());

        Glide.with(getBaseContext()).load(post.getImage().getUrl()).into(ivPicture);

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile pic = currentUser.getParseFile("ProfilePicture");

        if(pic != null) {
            String url = pic.getUrl();
            Glide.with(getBaseContext()).load(url).into(ivProfilePic);
        }
    }
}
