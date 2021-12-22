package com.study.viewpagerindicator.presentation

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.study.viewpagerindicator.R
import com.study.viewpagerindicator.databinding.FragmentCardBinding

//
//  CardFragment.kt
//  ViewpagerIndicator
//
//  Created by Seomoon Choi on 12/22/21
//

class CardFragment: Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    var accountList: List<String> = listOf("page1", "page2", "page3", "page4")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initIndicator()
    }

    private fun initViewPager() {
        val adapter = CardAdapter()
        adapter.items = accountList
        binding.cardViewPager.apply {

            this.adapter = adapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipToPadding = false
            offscreenPageLimit = 1
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val cardOffset = resources.getDimension(R.dimen.card_offset)
            val nextItemVisiblePx = resources.getDimension(R.dimen.card_margin)
            val pageTranslationX = (cardOffset * VIEWPAGER_PRE_VIEW) + nextItemVisiblePx

            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State,
                ) {
                    outRect.right = cardOffset.toInt() + nextItemVisiblePx.toInt()
                    outRect.left = cardOffset.toInt() + nextItemVisiblePx.toInt()
                }
            })

            setPageTransformer { page, position -> page.translationX = -pageTranslationX * (position) }

            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.textIndicatorCurrentLocation.text = (position + 1).toString()
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    binding.pageIndicator.translationX = (position + positionOffset) * binding.pageIndicator.width
                }
            })
        }
    }

    private fun initIndicator() {
        binding.pageIndicatorBg.post {
            val width = binding.pageIndicatorBg.measuredWidth
            val layoutParam = binding.pageIndicator.layoutParams

            layoutParam.width = if (accountList.isNotEmpty()) {
                width / accountList.size
            } else {
                width
            }
            binding.pageIndicator.layoutParams = layoutParam
        }

        binding.textIndicatorTotalSize.text = accountList.size.toString()
        binding.textIndicatorCurrentLocation.text = "1"
    }

    companion object {
        val VIEWPAGER_PRE_VIEW = 3
    }
}