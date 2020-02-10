package com.recker.photoswipeview

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import com.recker.photoswipeview.models.Photo
import kotlin.math.abs


/**
 * Created by Santanu 😁 on 2020-02-08.
 */
class PhotoSwipeView(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs), View.OnTouchListener {
    private val _mPhotos: MutableList<Photo> = mutableListOf()
    private val _photosViewList: MutableList<View> = mutableListOf()
    private var _currentPosition = 0

    fun setPhotos(photos: List<Photo>) {
        _mPhotos.apply {
            clear()
            addAll(photos)
        }
        notifyPhotosAdded()
        notifyPhotosAdded()
    }

    private fun notifyPhotosAdded() {
        if (_mPhotos.size > _currentPosition) {
            val view = LayoutInflater
                .from(context)
                .inflate(R.layout.photos_root_layout, this, false)
            val ivPhoto = view.findViewById<View>(R.id.ivPhoto) as ImageView
            val model = _mPhotos[_currentPosition]

            if (_currentPosition % 3 == 0) {
                ivPhoto.setImageResource(R.drawable.britney)
            } else if (_currentPosition % 2 == 0) {
                ivPhoto.setImageResource(R.drawable.kim_kardashian)
            }

            // TODO set the image to the imageView
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            view.layoutParams = layoutParams

            view.setOnTouchListener(this)
            addView(view, 0)
            _currentPosition++

            _photosViewList.add(view)
        }
    }

    private var dX = 0f
    private var dY: Float = 0f

    override fun onTouch(view: View, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = view.x - event.rawX
                dY = view.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                view.animate()
                    .x(event.rawX + dX)
                    .y(event.rawY + dY)
                    .setDuration(0)
                    .start()

                val percentage = (abs(event.rawX + dX) * 100) / view.width
                val rotate = when {
                    (event.rawX + dX) > 0 -> {
                        (percentage / 100.0f) * 1.0f
                    }
                    (event.rawX + dX) < 0 -> {
                        -((percentage / 100.0f) * 1.0f)
                    }
                    else -> {
                        0.0f
                    }
                }

                view.animate()
                    .rotation(rotate * 25)
                    .setDuration(0)
                    .start()

                if (_photosViewList.size > 1) {
                    _photosViewList[0].animate()
                        .rotation(rotate * 12)
                        .setDuration(0)
                        .start()
                }
            }

            MotionEvent.ACTION_UP -> {
                if (abs((event.rawX + dX)) > 400) {
                    when {
                        (event.rawX + dX) > 0 -> {
                            valueAnim(event.rawX + dX, width.toFloat(), view, "X")
                        }
                        (event.rawX + dX) < 0 -> {
                            valueAnim(event.rawX + dX, -(width.toFloat()), view, "X")
                        }
                    }
                } else {
                    valueAnim(view.rotation, 0.0f, view, "R")
                    valueAnim(event.rawX + dX, 0.0f, view, "X")
                    valueAnim(event.rawY + dY, 0.0f, view, "Y")
                }
            }
            else -> return false
        }
        return true
    }

    private fun valueAnim(initialX: Float, finalX: Float, view: View, type: String) {
        val anim = ValueAnimator.ofFloat(initialX, finalX)
        anim.apply {
            addUpdateListener {
                when (type) {
                    "X" -> view.x = it.animatedValue as Float
                    "R" -> view.rotation = it.animatedValue as Float
                    "Y" -> view.y = it.animatedValue as Float
                }
            }
            doOnEnd {
                if (abs(finalX) >= width) {
                    removeView(view)
                    _photosViewList.remove(view)
                    notifyPhotosAdded()
                }

                // TODO MOVE IT TO SMOOTH ANIMATION -- FOR THE VIEW BEHIND
                if (_photosViewList.size > 1) {
                    _photosViewList[0].animate()
                        .rotation(0.0f)
                        .setDuration(0)
                        .start()
                }
            }
            duration = 250
            start()
        }
    }

    fun Float.convertDpToPx(): Float {
        val dip = 14f
        val r: Resources = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            r.displayMetrics
        )
    }
}