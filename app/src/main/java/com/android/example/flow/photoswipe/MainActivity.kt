package com.android.example.flow.photoswipe

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recker.photoswipeview.models.Photo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val photos: MutableList<Photo> = mutableListOf()

        for (i in 0..10) {
            photos.add(object : Photo() {
                override val url: String?
                    get() = ""

            })
        }

        photoSwipeView.setPhotos(photos)
    }

}
