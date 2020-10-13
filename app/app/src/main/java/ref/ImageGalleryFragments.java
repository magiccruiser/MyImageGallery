package ref;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

import com.example.myapp3.R;
import ref.Activities.FullScreenImageGalleryActivity;
import ref.Adapter.ImageGalleryAdapter;
import ref.Utilities.DisplayUtility;

public class ImageGalleryFragments extends Fragment implements ImageGalleryAdapter.OnImageClickListener, ImageGalleryAdapter.ImageThumbnailLoader {
    public static final String KEY_IMAGES = "KEY_IMAGES";
    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_TITLE = "KEY_TITLE";

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private ArrayList<String> images;
    private String title;
    private static ImageGalleryAdapter.ImageThumbnailLoader imageThumbnailLoader;

    public void ImageGalleryFragment() {
    }

    public static ImageGalleryFragments newInstance(Bundle extras) {
        ImageGalleryFragments fragment = new ImageGalleryFragments();
        fragment.setArguments(extras);
        return fragment;
    }

    public static ImageGalleryFragments newInstance() {
        ImageGalleryFragments fragment = new ImageGalleryFragments();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            images = getArguments().getStringArrayList(KEY_IMAGES);
            title = getArguments().getString(KEY_TITLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews();

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }

        setUpRecyclerView();

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setUpRecyclerView();
    }
    @Override
    public void onImageClick(int position) {
        Intent intent = new Intent(getContext(), FullScreenImageGalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(FullScreenImageGalleryActivity.KEY_IMAGES, images);
        bundle.putInt(FullScreenImageGalleryActivity.KEY_POSITION, position);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void loadImageThumbnail(ImageView iv, String imageUrl, int dimension) {
        imageThumbnailLoader.loadImageThumbnail(iv, imageUrl, dimension);
    }

    private void bindViews() {
        recyclerView = (RecyclerView) Objects.requireNonNull(getActivity()).findViewById(R.id.rv);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    private void setUpRecyclerView() {
        int numOfColumns;
        if (DisplayUtility.isInLandscapeMode(Objects.requireNonNull(getActivity()))) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numOfColumns);

        recyclerView.setLayoutManager(layoutManager);

        ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(getContext(), images);
        imageGalleryAdapter.setOnImageClickListener(this);
        imageGalleryAdapter.setImageThumbnailLoader(this);

        recyclerView.setAdapter(imageGalleryAdapter);
    }

    public static void setImageThumbnailLoader(ImageGalleryAdapter.ImageThumbnailLoader loader) {
        imageThumbnailLoader = loader;
    }
}
