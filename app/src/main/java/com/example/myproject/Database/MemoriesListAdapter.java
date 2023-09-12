package com.example.myproject.Database;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myproject.R;

public class MemoriesListAdapter extends BaseAdapter {
    Context context;
    MemoryClass memories[];
    LayoutInflater inflater;

    public MemoriesListAdapter(Context context, MemoryClass[] memories) {
        this.context = context;
        this.memories = memories;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return memories.length;
    }

    @Override
    public MemoryClass getItem(int i) {
        return memories[i];
    }

    @Override
    public long getItemId(int i) {
        return memories[i].getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.listview, null);
        TextView title = (TextView) view.findViewById(R.id.title_listview);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_listview);
        title.setText(memories[i].getTitle());
        imageView.setImageURI(Uri.parse(memories[i].getPhotoPath()));
        return view;
    }
}
