package com.ruichaoqun.kotlinstudyapplication.kotlin.basic

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.ruichaoqun.kotlinstudyapplication.R

/**
 * @author Rui Chaoqun
 * @date :2019/11/5 10:16
 * description:
 */
class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    enum class ShapeType {
        SHAPE_CIRCLE,
        SHAPE_ROUND
    }

    private var mBorderWidth: Float = 20f
        set(value) {
            field = value
            invalidate()
        }

    private var mBorderColor: Int = Color.parseColor("#ff9900")
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftTopRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftTopRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightTopRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightTopRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftBottomRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mLeftBottomRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightBottomRadiusX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightBottomRadiusY: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightTopPointRadius: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var mRightTopPointColor: Int = Color.parseColor("#ff0000")
        set(value) {
            field = value
            invalidate()
        }

    private var mShowPoint: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    private var mShapeType: ShapeType = ShapeType.SHAPE_CIRCLE
        set(value) {
            field = value
            invalidate()
        }

    private var mShowBorder: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    private lateinit var mShapePath: Path
    private lateinit var mBorderPath: Path
    private lateinit var mBitmapPaint: Paint
    private lateinit var mBorderPaint: Paint
    private lateinit var mPointPaint: Paint
    private lateinit var mMatrix: Matrix

    private var mWidth = 200
    private var mHeight = 200
    private var mRadius = 100f

    init {
        initAttrs(context, attrs, defStyleAttr)
        initPaint()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        var array =
            context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0)
        for (index in 0..array.indexCount) {
            array.getIndex(index)?.let {
                when (it) {
                    R.styleable.RoundImageView_shape_type ->
                        mShapeType = when {
                            array.getInt(it, 0) == 0 -> ShapeType.SHAPE_CIRCLE
                            array.getInt(it, 0) == 1 -> ShapeType.SHAPE_ROUND
                            else -> ShapeType.SHAPE_CIRCLE
                        }
                    R.styleable.RoundImageView_border_width ->
                        mBorderWidth = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_border_color ->
                        mBorderColor = array.getColor(it, Color.parseColor("#ff9900"))
                    R.styleable.RoundImageView_show_border ->
                        mShowBorder = array.getBoolean(it, false)
                    R.styleable.RoundImageView_left_top_radius_x ->
                        mLeftTopRadiusX = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_left_top_radius_y ->
                        mLeftTopRadiusY = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_right_top_radius_x ->
                        mRightTopRadiusX = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_right_top_radius_y ->
                        mRightTopRadiusY = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_left_bottom_radius_x ->
                        mLeftBottomRadiusX = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_left_bottom_radius_y ->
                        mLeftBottomRadiusY = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_right_bottom_radius_x ->
                        mRightBottomRadiusX = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_right_bottom_radius_y ->
                        mRightBottomRadiusY = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_circle_dot_color ->
                        mRightTopPointColor = array.getColor(it, Color.parseColor("#FF0000"))
                    R.styleable.RoundImageView_circle_dot_radius ->
                        mRightTopPointRadius = array.getDimension(it, 0f)
                    R.styleable.RoundImageView_show_dot ->
                        mShowPoint = array.getBoolean(it, false)
                }
            }
        }
        array.recycle()
    }

    private fun initPaint() {
        mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }

        mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = mBorderColor
            strokeWidth = mBorderWidth
            strokeCap = Paint.Cap.ROUND
        }

        mPointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = mRightTopPointColor
        }

        mShapePath = Path()
        mBorderPath = Path()
        mMatrix = Matrix()
        scaleType = ScaleType.CENTER_CROP
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mShapeType == ShapeType.SHAPE_CIRCLE) {
            mWidth = Math.min(measuredWidth, measuredHeight)
            mRadius = mWidth / 2.0f
            setMeasuredDimension(mWidth, mWidth)
        } else {
            mWidth = measuredWidth
            mHeight = measuredHeight
            setMeasuredDimension(mWidth, mHeight)
        }
    }

    /**
     * 当所有子View都实例化之后，调用父View的onFinishInflate
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        //如果该View是view，就是自己被完全加载实例化之后调用（必须从XML实例化）
        //如果该View是viewGroup，就是子View被完全加载实例化之后调用（必须从XML实例化）
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    /**
     * 调用时机，在onMeasure之后，onLayout之前
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBorderPath.reset()
        mShapePath.reset()
        when (mShapeType) {
            ShapeType.SHAPE_CIRCLE -> {
                buildCirclePath()
            }

            ShapeType.SHAPE_ROUND -> {
                mWidth = w
                mHeight = h
                buildRoundPath()
            }
        }
    }

    private fun buildRoundPath() {
        if (mShowBorder) {
            floatArrayOf(
                mLeftTopRadiusX - mBorderWidth / 2,
                mLeftTopRadiusY - mBorderWidth / 2,
                mRightTopRadiusX - mBorderWidth / 2,
                mRightTopRadiusY - mBorderWidth / 2,
                mRightBottomRadiusX - mBorderWidth / 2,
                mRightBottomRadiusY - mBorderWidth / 2,
                mLeftBottomRadiusX - mBorderWidth / 2,
                mLeftBottomRadiusY - mBorderWidth / 2
            )
                .run {
                    mBorderPath.addRoundRect(
                        RectF(
                            mBorderWidth / 2,
                            mBorderWidth / 2,
                            mWidth.toFloat() - mBorderWidth / 2,
                            mHeight.toFloat() - mBorderWidth / 2
                        ), this, Path.Direction.CCW
                    )
                }

            floatArrayOf(
                mLeftTopRadiusX - mBorderWidth,
                mLeftTopRadiusY - mBorderWidth,
                mRightTopRadiusX - mBorderWidth,
                mRightTopRadiusY - mBorderWidth,
                mRightBottomRadiusX - mBorderWidth,
                mRightBottomRadiusY - mBorderWidth,
                mLeftBottomRadiusX - mBorderWidth,
                mLeftBottomRadiusY - mBorderWidth
            )
                .run {
                    mShapePath.addRoundRect(
                        RectF(
                            mBorderWidth,
                            mBorderWidth,
                            mWidth.toFloat() - mBorderWidth,
                            mHeight.toFloat() - mBorderWidth
                        ), this, Path.Direction.CCW
                    )
                }
        } else {
            floatArrayOf(
                mLeftTopRadiusX, mLeftTopRadiusY, mRightTopRadiusX, mRightTopRadiusY,
                mRightBottomRadiusX, mRightBottomRadiusY, mLeftBottomRadiusX, mLeftBottomRadiusY
            )
                .run {
                    mShapePath.addRoundRect(
                        RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
                        this,
                        Path.Direction.CCW
                    )
                }
        }
    }

    private fun buildCirclePath() {
        if (mShowBorder) {
            mBorderPath.addCircle(mRadius, mRadius, mRadius - mBorderWidth / 2, Path.Direction.CW)
            mShapePath.addCircle(mRadius, mRadius, mRadius - mBorderWidth, Path.Direction.CW)
        } else {
            mShapePath.addCircle(mRadius, mRadius, mRadius, Path.Direction.CW)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        drawable ?: return
        mBitmapPaint.shader = getBitmapShader()
        when (mShapeType) {
            ShapeType.SHAPE_CIRCLE -> {
                if (mShowBorder) {
                    canvas?.drawPath(mBorderPath, mBorderPaint)
                }
                canvas?.drawPath(mShapePath, mBitmapPaint)
                if(mShowPoint){
                    drawCirclePoint(canvas)
                }
            }

            ShapeType.SHAPE_ROUND -> {
                if (mShowBorder) {
                    canvas?.drawPath(mBorderPath, mBorderPaint)
                }
                canvas?.drawPath(mShapePath, mBitmapPaint)
            }
        }
    }

    private fun drawCirclePoint(canvas: Canvas?) {
        canvas?.run {
            drawCircle((mRadius + mRadius * (Math.sqrt(2.0) / 2.0f)).toFloat(),(mRadius - mRadius * (Math.sqrt(2.0) / 2.0f)).toFloat(),mRightTopPointRadius,mPointPaint)
        }
    }

    private fun getBitmapShader(): Shader? {
        val bitmap = drawableToBitmap(drawable)
        return BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            .apply {
                var scale = 1.0f
                if (mShapeType == ShapeType.SHAPE_CIRCLE) {
                    scale = (mWidth * 1.0f / Math.min(bitmap.width, bitmap.height))
                } else {
                    if (width != bitmap.width || height != bitmap.height) {
                        scale = Math.max(width * 1.0f / bitmap.width, height * 1.0f / bitmap.height)
                    }
                }
                mMatrix.setScale(scale, scale)
                setLocalMatrix(mMatrix)
            }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        return Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
            .apply {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                drawable.draw(Canvas(this))
            }
    }


}