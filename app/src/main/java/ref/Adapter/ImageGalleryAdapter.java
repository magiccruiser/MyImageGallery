package ref.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp3.R;

import java.util.List;

import ref.Utilities.DisplayUtility;

public class ImageGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<String> images;
    private int gridItemWidth;
    private int gridItemHeight;
    private OnImageClickListener onImageClickListener;
    private ImageThumbnailLoader imageThumbnailLoader;

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    public interface ImageThumbnailLoader {
        void loadImageThumbnail(ImageView iv, String imageUrl, int dimension);
    }

    public ImageGalleryAdapter(Context context, List<String> images) {
        this.images = images;

        int screenWidth = DisplayUtility.getScreenWidth(context);
        int numOfColumns;
        if (DisplayUtility.isInLandscapeMode(context)) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        gridItemWidth = screenWidth / numOfColumns;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_thumbnail, viewGroup, false);
        v.setLayoutParams(getGridItemLayoutParams(v));

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        String image = images.get(position);

        imageThumbnailLoader.loadImageThumbnail(holder.imageView, image, gridItemWidth);

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClick(adapterPos);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (images != null) {
            return images.size();
        } else {
            return 0;
        }
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.onImageClickListener = listener;
    }

    public void setImageThumbnailLoader(ImageThumbnailLoader loader) {
        this.imageThumbnailLoader = loader;
    }

    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.width = gridItemWidth;
        layoutParams.height = gridItemWidth;

        return layoutParams;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final FrameLayout frameLayout;

        public ImageViewHolder(final View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.iv);
            frameLayout = (FrameLayout) view.findViewById(R.id.fl);
        }
    }
}