package com.example.myproject;

import static android.os.SystemClock.sleep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myproject.Database.MemoriesDatabase;
import com.example.myproject.Database.MemoriesExecutors;
import com.example.myproject.Database.MemoriesListAdapter;
import com.example.myproject.Database.MemoryClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link memory_list_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class memory_list_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private MemoryClass memories[];
    private MemoriesDatabase memoriesDatabase;
    private Button backButton;

    public memory_list_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment memory_list_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static memory_list_fragment newInstance(String param1, String param2) {
        memory_list_fragment fragment = new memory_list_fragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        MemoriesExecutors.getInstance().getDiskIO().submit(new Runnable() {
            @Override
            public void run() {
                memories = memoriesDatabase.memoriesDao().loadAllMemories();
                System.out.println("Pobrano wspomnienia z bazy danych");
                //notifyAll();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memory_list_fragment, container, false);

        listView = view.findViewById(R.id.list);
        backButton = view.findViewById(R.id.backButton_list);
        while(memories == null) {
            sleep(2);
        }

        if(memories != null) {
            MemoriesListAdapter memoriesListAdapter = new MemoriesListAdapter(getContext(), memories);
            listView.setAdapter(memoriesListAdapter);
        } else {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        wait(100);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//            thread.start();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putLong("memoryID", l);
                Navigation.findNavController(view).navigate(R.id.navigate_to_memory_from_list, bundle);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == backButton.getId()) {
                    Navigation.findNavController(view).navigate(R.id.navigate_to_home_from_list);
                }
            }
        });

        return view;
    }
}