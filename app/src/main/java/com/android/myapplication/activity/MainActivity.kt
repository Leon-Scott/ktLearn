package com.android.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.myapplication.R
import com.android.myapplication.databinding.ActivityMain3Binding
import imageSlide.ImageSlideActivity
import map.MapActivity
import net.NetActivity

class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMain3Binding by lazy {
        ActivityMain3Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main3)
        setContentView(mBinding.root)
        initListener()
    }

    private fun initListener(){
        mBinding.btNetActivity.setOnClickListener {
            startActivity(Intent(this, NetActivity::class.java))
        }

        mBinding.btMapActivity.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        mBinding.btSlidimageActivity.setOnClickListener {
            startActivity(Intent(this, ImageSlideActivity::class.java))
        }

        mBinding.btScanviewActivity.setOnClickListener {
            startActivity(Intent(this, ScanViewActivity::class.java))
        }
    }
}