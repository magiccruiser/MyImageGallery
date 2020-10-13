package ref.Adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.myapp3.R;
import ref.Utilities.DisplayUtility;

public class FullScreenImageGalleryAdapter extends PagerAdapter {
    private final List<String> images = null;
    private FullScreenImageLoader fullScreenImageLoader;

    public FullScreenImageGalleryAdapter(ArrayList<String> imageList) {
    }

    public interface FullScreenImageLoader {
        void loadFullScreenImage(ImageView iv, String imageUrl, int width, LinearLayout bglinearLayout);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fullscreen_image, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll);

        String image = images.get(position);

        Context context = imageView.getContext();
        int width = DisplayUtility.getScreenWidth(context);

        fullScreenImageLoader.loadFullScreenImage(imageView, image, width, linearLayout);

        container.addView(view, 0);

        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setFullScreenImageLoader(FullScreenImageLoader loader) {
        this.fullScreenImageLoader = loader;
    }
}
