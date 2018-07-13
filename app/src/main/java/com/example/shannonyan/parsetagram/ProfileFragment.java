package com.example.shannonyan.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    private Button btSignOut;
    private Button btProfPic;
    private ImageView ivProfilePicture;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("profile", "worked");
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btSignOut = view.findViewById(R.id.btSignOut);
        btProfPic = view.findViewById(R.id.btProfPic);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile pic = currentUser.getParseFile("ProfilePicture");

        if( pic != null) {
            String url = pic.getUrl();
            Glide.with(getActivity()).load(url).into(ivProfilePicture);
        } else {
            ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        }


        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Toast.makeText( getActivity(),"Signed out", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });




        btProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera();
            }
        });

//        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
//        StaggeredGridLayoutManager gridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        // Attach the layout manager to the recycler view
//        recyclerView.setLayoutManager(gridLayoutManager);


        return view;
    }

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public String photoFileName = "photo.jpg";
    File photoFile;

    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getActivity(), "com.codepath.fileprovider", photoFile); //changed this to getActivitity()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                ImageView ivProfilePicture = (ImageView) getView().findViewById(R.id.ivProfilePicture);
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(takenImage, ivProfilePicture.getWidth() , ivProfilePicture.getHeight(), false);
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivProfilePicture.setImageBitmap(resizedBitmap);

                final ParseUser user = ParseUser.getCurrentUser();
                final File file = getPhotoFileUri(photoFileName);
                final ParseFile parseFile = new ParseFile(file);

                user.put("ProfilePicture", parseFile);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        System.out.println("done");
                    }
                });

            } else { // Result was a failure
                Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                System.out.println("did not work");
            }
        }
    }






}
