package com.android.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.myapplication.R
import imageSlide.ImageSlideActivity
import kotlinx.android.synthetic.main.activity_main3.bt_map_activity
import kotlinx.android.synthetic.main.activity_main3.bt_net_activity
import kotlinx.android.synthetic.main.activity_main3.bt_slidimage_activity
import map.MapActivity
import net.NetActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        initListener()
    }

    fun initListener(){
        bt_net_activity.setOnClickListener{
            startActivity(Intent(this,NetActivity::class.java))
        }

        bt_map_activity.setOnClickListener{
            startActivity(Intent(this,MapActivity::class.java))
        }

        bt_slidimage_activity.setOnClickListener{
            startActivity(Intent(this,ImageSlideActivity::class.java))
        }
    }
}