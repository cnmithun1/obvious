package task.com.obvious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;

public class SwipeActivity extends Activity {

    MainActivity ma = new MainActivity();
    String imageUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        // get intent data
        Intent i = getIntent();

        // Selected id
        int position = i.getExtras().getInt("position");

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    private class ImagePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return ma.nasaModelArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //return view == ((ImageView) object);
            return view == ((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = SwipeActivity.this;

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.nasaImage);

            TextView date = (TextView) view.findViewById(R.id.date);

            TextView title = (TextView) view.findViewById(R.id.title);

            TextView description = (TextView) view.findViewById(R.id.description);

            //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            String imageURL = ma.nasaModelArrayList.get(position).getUrl();
            Glide.with(SwipeActivity.this).load(imageURL).apply(new RequestOptions()
                    .placeholder(R.drawable.image)).into(imageView);
            //((ViewPager) container).addView(imageView, 0);

            date.setText(ma.nasaModelArrayList.get(position).getDate());

            title.setText(ma.nasaModelArrayList.get(position).getTitle());

            description.setText(ma.nasaModelArrayList.get(position).getExplanation());



            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //((ViewPager) container).removeView((ImageView) object);
            container.removeView((View) object);
        }
    }
}
