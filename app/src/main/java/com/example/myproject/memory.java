package com.example.myproject;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Database.MemoriesDatabase;
import com.example.myproject.Database.MemoriesExecutors;
import com.example.myproject.Database.MemoryClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link memory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class memory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "memoryID";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Long memoryID;
    private String mParam2;
    private TextView title;
    private TextView description;
    private TextView meta;
    private TextView quote;
    private ImageView imageView;
    private Button leaveButton;
    private MemoriesDatabase memoriesDatabase;
    private MemoryClass memory;
    public memory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment memory.
     */
    // TODO: Rename and change types and number of parameters
    public static memory newInstance(String param1, String param2) {
        memory fragment = new memory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoriesDatabase = MemoriesDatabase.getInstance(this.getContext());
        if (getArguments() != null) {
            memoryID = getArguments().getLong(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(memoryID != null)
        {
            MemoriesExecutors.getInstance().getDiskIO().submit(new Runnable() {
                @Override
                public void run() {
                    memory = memoriesDatabase.memoriesDao().loadMemoryWithID(memoryID);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        title = view.findViewById(R.id.title_memory);
        description = view.findViewById(R.id.description_memory);
        meta = view.findViewById(R.id.meta_memory);
        imageView = view.findViewById(R.id.imageView_memory);
        quote = view.findViewById(R.id.quote_memory);

        leaveButton = view.findViewById(R.id.leave_memory);


        if(memory != null) {
            title.setText(memory.getTitle());
            description.setText(memory.getDescription());
            imageView.setImageURI(Uri.parse(memory.getPhotoPath()));
            quote.setText(memory.getQoute());
            meta.setText(memory.getMetaData());
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == leaveButton.getId()) {
                    Navigation.findNavController(view).navigate(R.id.navigate_to_list_from_memory);
                }
            }
        };

        leaveButton.setOnClickListener(listener);

        return view;
    }
}