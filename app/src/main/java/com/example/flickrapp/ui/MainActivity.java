package com.example.flickrapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.flickrapp.R;
import com.example.flickrapp.data.models.Image;
import com.example.flickrapp.databinding.ActivityMainBinding;
import com.example.flickrapp.ui.adapters.ImageAdapter;
import com.example.flickrapp.viewmodel.ImagesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Image> imageList = new ArrayList<>();
    ImagesViewModel imagesViewModel;
    ImageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();

        imagesViewModel = ViewModelProviders.of(this).get(ImagesViewModel.class);
        imagesViewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                imageList.clear();
                imageList.addAll(images);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        adapter = new ImageAdapter(imageList);
        binding.recyclerView.setAdapter(adapter);

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = binding.edit.getText().toString().trim();
                if (!query.isEmpty()) {
                    imagesViewModel.fetchImages(query);
                }
            }
        });
    }
}




