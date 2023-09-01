package com.example.myproject;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_memory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_memory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "imagePath";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String imagePath;
    private String mParam2;
    private Button confirmButton;
    private Button cameraButton;
    private Button galleryButton;
    private ImageView imageView;
    private EditText title;
    private EditText description;

    // Registers a photo picker activity launcher in single-select mode.
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                    imageView.setImageURI(uri);
                    imagePath = uri.toString();
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });




    public add_memory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_memory.
     */
    // TODO: Rename and change types and number of parameters
    public static add_memory newInstance(String param1, String param2) {
        add_memory fragment = new add_memory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            imagePath = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_memory, container, false);
        confirmButton = view.findViewById(R.id.confirm_add);
        cameraButton = view.findViewById(R.id.camera_button);
        galleryButton = view.findViewById(R.id.gallery_button);
        imageView = view.findViewById(R.id.imageView_add);
        title = view.findViewById(R.id.title_add);
        description = view.findViewById(R.id.description_add);
        if(imagePath != null)
        {
            imageView.setImageURI(Uri.parse(imagePath));
        } else {
            if(savedInstanceState != null) {
                imagePath = savedInstanceState.getString("iPath");
                imageView.setImageURI(Uri.parse(imagePath));
            }
        }


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == confirmButton.getId()) {
                    Navigation.findNavController(view).navigate(R.id.navigate_to_memory_from_add);
                }
            }
        };

        View.OnClickListener cameraListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == cameraButton.getId()) {
                    Navigation.findNavController(view).navigate(R.id.navigate_to_camera_from_add);
                }
            }
        };

        View.OnClickListener galleryListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


// Include only one of the following calls to launch(), depending on the types
// of media that you want to let the user choose from.

// Launch the photo picker and let the user choose only images.
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        };

        TextWatcher titleWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        title.addTextChangedListener(titleWatcher);
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        confirmButton.setOnClickListener(listener);
        cameraButton.setOnClickListener(cameraListener);
        galleryButton.setOnClickListener(galleryListener);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("iPath", imagePath);
        super.onSaveInstanceState(outState);
    }

}