package com.example.myproject;

import static android.os.SystemClock.sleep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myproject.Database.MemoriesDatabase;
import com.example.myproject.Database.MemoriesExecutors;
import com.example.myproject.Database.MemoryClass;

import java.util.concurrent.Future;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button addButton;
    private Button showAllButton;
    private MemoryClass memories[];
    private MemoriesDatabase memoriesDatabase;
    private ViewPager2 viewPager2;
    private Future future;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoriesDatabase = MemoriesDatabase.getInstance(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        future = MemoriesExecutors.getInstance().getDiskIO().submit(new Runnable() {
            @Override
            public void run() {
                memories = memoriesDatabase.memoriesDao().loadLast5Memories();
                System.out.println("Pobrano wspomnienia z bazy danych");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        addButton = view.findViewById(R.id.button);
        showAllButton = view.findViewById(R.id.button2);
        viewPager2 = view.findViewById(R.id.viewpager_home);
        ViewPager2Adapter viewPager2Adapter;

        while(!future.isDone())
        {
            sleep(1);
        }
        if(memories != null) {
            viewPager2Adapter = new ViewPager2Adapter(memories, getContext());
            viewPager2.setAdapter(viewPager2Adapter);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
        }

        viewPager2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = viewPager2.getCurrentItem();
                Bundle bundle = new Bundle();
                bundle.putLong("memoryID", memories[item].getId());
                Navigation.findNavController(view).navigate(R.id.navigate_to_memory_from_home, bundle);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == addButton.getId()) {
                    Navigation.findNavController(view).navigate(R.id.navigate_to_add);
                }
                if(v.getId() == showAllButton.getId()) {
                    Navigation.findNavController(view).navigate(R.id.navigate_to_showAll);
                }
            }
        };

        addButton.setOnClickListener(listener);
        showAllButton.setOnClickListener(listener);

        return view;

    }
}