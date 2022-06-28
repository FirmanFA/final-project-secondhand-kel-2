package com.binar.secondhand.kel2.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.binar.secondhand.kel2.R

class OnBoardingViewPagerAdapter(private var context: Context, private var onBoardingDataList: List<OnBoardingData>) : PagerAdapter() {
    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.onboarding_screen_layout, null)
        val imageView : ImageView
        val desc : TextView

        imageView = view.findViewById(R.id.imageView)
        desc = view.findViewById(R.id.tv_desc)

        imageView.setImageResource(onBoardingDataList[position].img)
        desc.text = onBoardingDataList[position].desc

        container.addView(view)
        return view
    }
}