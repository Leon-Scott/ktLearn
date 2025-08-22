package imageSlide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.myapplication.R;

public class ImageSlideActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide2);

//        viewPager = findViewById(R.id.viewPager);

//        // 设置适配器，添加图片数据
//        ImagePagerAdapter adapter = new ImagePagerAdapter(getSupportFragmentManager());
//        adapter.addImage(R.drawable.image1);
//        adapter.addImage(R.drawable.image2);
//        adapter.addImage(R.drawable.image3);
//        viewPager.setAdapter(adapter);
//
//        // 设置PageTransformer
//        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//                // 根据页面位置设置透明度
//                page.setAlpha(Math.max(0f, 1 - Math.abs(position)));
//            }
//        });
    }

}