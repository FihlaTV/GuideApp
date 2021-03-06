package com.guideapp.ui.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class DividerItemDecoration : RecyclerView.ItemDecoration {
    private val mDivider: Drawable
    private var mOrientation: Int = 0
    private var mDividerColor: Int = 0
    private var mDividerStyle = DividerStyle.Dark
    private var mDividerHeight: Int = 0
    private var mMarginLeft: Int = 0

    constructor(context: Context, orientation: Int, dividerStyle: DividerStyle) {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        this.mDividerStyle = dividerStyle
        a.recycle()
        setOrientation(orientation)
    }

    constructor(context: Context, orientation: Int) {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        this.mDividerStyle = DividerStyle.Dark
        a.recycle()
        setOrientation(orientation)
    }

    constructor(context: Context, orientation: Int, marginLeft: Int) : this(context, orientation) {
        this.mMarginLeft = marginLeft
    }

    enum class DividerStyle {
        Light,
        Dark,
        NoneColor,
        Default
    }

    fun setDeviderColor(color: String) {
        mDividerColor = Color.parseColor(color)
    }


    fun setDeviderColor(color: Int) {
        mDividerColor = color
    }

    fun setDividerHeight(height: Int) {
        mDividerHeight = height

    }


    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + mMarginLeft
        val right = parent.width - parent.paddingRight

        if (mDividerStyle != DividerStyle.Default) {
            when (mDividerStyle) {
                DividerItemDecoration.DividerStyle.Dark -> mDividerColor = Color.argb(13, 0, 0, 0)
                DividerItemDecoration.DividerStyle.Light -> mDividerColor = Color.WHITE
                DividerItemDecoration.DividerStyle.NoneColor -> mDividerColor = Color.TRANSPARENT
                else -> mDividerColor = Color.TRANSPARENT
            }
            mDivider.setColorFilter(mDividerColor, PorterDuff.Mode.SRC_OUT)
        }

        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val v = RecyclerView(parent.context)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.intrinsicHeight + mDividerHeight)
        } else {
            outRect.set(0, 0, mDivider.intrinsicWidth, 0)
        }
    }

    companion object {
        val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        val VERTICAL_LIST = LinearLayoutManager.VERTICAL
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
