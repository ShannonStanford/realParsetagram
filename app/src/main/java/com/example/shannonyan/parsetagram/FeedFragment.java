package com.example.shannonyan.parsetagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shannonyan.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;


public class FeedFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("feed", "worked");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Define the class we would like to query
        loadTopPosts();



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_fragment, container, false);


    }


    private void loadTopPosts() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null){
                    for(int i = 0; i < objects.size(); ++i){
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername());
                    }

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
