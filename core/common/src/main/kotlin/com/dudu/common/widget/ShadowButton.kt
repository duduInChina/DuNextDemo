package com.dudu.common.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.dudu.common.R

/**
 * <pre>
 *     author : dzc
 *     time   : 2024/08/01
 *     desc   : 点击阴影按钮
 * </pre>
 */
class ShadowButton : AppCompatButton {

    private var COVER_ALPHA = 10
    private var SHAPE_TYPE_RECTANGLE = 1
    private var mPressedColor = 0
    private var mShapeType = 0
    private var mRadius = 0

    private val mPressedPaint: Paint = Paint()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.MiniLoadingStyle) {
        initAttrs(context, attrs, defStyleAttr)
        initPaint()

        setWillNotDraw(false)
        isDrawingCacheEnabled = true
        isClickable = true
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.ShadowButton, defStyleAttr, 0)
        COVER_ALPHA = array.getInteger(R.styleable.ShadowButton_sb_alpha_pressed, COVER_ALPHA)
        mPressedColor = array.getColor(
            R.styleable.ShadowButton_sb_color_pressed,
            getResources().getColor(R.color.default_shadow_button_color_pressed)
        )
        mShapeType = array.getInt(R.styleable.ShadowButton_sb_shape_type, SHAPE_TYPE_RECTANGLE)
        mRadius = array.getDimensionPixelSize(
            R.styleable.ShadowButton_sb_radius,
            getResources().getDimensionPixelSize(R.dimen.default_shadow_button_radius)
        )
        array.recycle()
    }

    private fun initPaint() {
        mPressedPaint.style = Paint.Style.FILL
        mPressedPaint.color = mPressedColor
        mPressedPaint.alpha = 0
        mPressedPaint.isAntiAlias = true
    }

    private var mWidth = 0
    private var mHeight = 0
    private var mRectF: RectF? = null
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mRectF = RectF(0f,0f, mWidth.toFloat(), mHeight.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(mShapeType == 0){
            canvas.drawCircle(
                mWidth / 2f,
                mHeight / 2f,
                mWidth / 1.9f,
                mPressedPaint
            )
        } else {
            canvas.drawRoundRect(
                mRectF!!,
                mRadius.toFloat(),
                mRadius.toFloat(),
                mPressedPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action){
                MotionEvent.ACTION_DOWN -> {
                    mPressedPaint.alpha = COVER_ALPHA
                    invalidate()
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    mPressedPaint.alpha = 0
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }

}