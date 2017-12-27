package com.wzh.circlepercentview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by WZH on 2017/12/27.
 * 圆形百分比自定义View
 */

public class CirclePercentView extends View {
    /**
     * 圆背景色
     */
    private int mCircleColor;
    /**
     * 圆弧的颜色
     */
    private int mArcColor;
    /**
     * 圆弧的宽度
     */
    private int mArcWidth;
    /**
     * 百分比字体颜色
     */
    private int mPercentTextColor;
    /**
     * 百分比字体大小
     */
    private int mPercentTextSize;
    /**
     * 圆角
     */
    private int mRadius;
    /**
     * 当前值
     */
    private float mCurPercent = 0.0f;

    /**
     * 圆画笔
     */
    private Paint mCirclePaint;
    /**
     * 圆弧画笔
     */
    private Paint mArcPaint;
    /**
     * 百分比字体画笔
     */
    private Paint mPercentTextPaint;
    /**
     * 文本的范围矩形
     */
    private Rect mTextBound;
    /**
     * 圆弧的外接矩形
     */
    private RectF mArcRectF;


    private OnClickListener mOnClickListener;

    public CirclePercentView(Context context) {
        this(context, null);

    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CirclePercentView,
                defStyleAttr,
                0);

        mCircleColor = typedArray.getColor(R.styleable.CirclePercentView_circleBg, 0xff8e29fa);

        mArcColor = typedArray.getColor(R.styleable.CirclePercentView_arcColor, 0xffffee00);

        mArcWidth = typedArray.getDimensionPixelSize(R.styleable.CirclePercentView_arcWidth,
                DensityUtils.dp2px(context, 16));

        mPercentTextColor = typedArray.getColor(R.styleable.CirclePercentView_percentTextColor, 0xffffee00);

        mPercentTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentView_percentTextSize,
                DensityUtils.sp2px(context, 16));

        mRadius = typedArray.getDimensionPixelSize(R.styleable.CirclePercentView_radius,
                DensityUtils.dp2px(context, 100));
        typedArray.recycle(); //回收


        init();


    }

    private void init() {
        /**
         * 画圆
         */
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mCircleColor);


        /**
         * 画圆弧
         */
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcWidth);
        mArcPaint.setColor(mArcColor);
        //是圆弧两头圆滑
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);


        /**
         * 画百分比字体
         */
        mPercentTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPercentTextPaint.setStyle(Paint.Style.STROKE);
        mPercentTextPaint.setColor(mPercentTextColor);
        mPercentTextPaint.setTextSize(mPercentTextSize);


        //圆弧的外界矩形
        mArcRectF = new RectF();

        //文本的范围矩形
        mTextBound = new Rect();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(CirclePercentView.this);
                }
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            //精确的，代表宽高为定值或者 match_parent 时
            result = specSize;
        } else {
            result = 2 * mRadius;
            if (specMode == MeasureSpec.AT_MOST) {
                // 最大的，代表宽高为 wrap_content时
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //画圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mCirclePaint);
        //画圆弧

        mArcRectF.set(getWidth() / 2 - mRadius + mArcWidth / 2, getHeight() / 2 - mRadius + mArcWidth / 2,
                getRight() / 2 + mRadius - mArcWidth / 2, getHeight() / 2 + mRadius - mArcWidth / 2);
        canvas.drawArc(mArcRectF, 270, 360 * mCurPercent / 100, false, mArcPaint);
        String text = mCurPercent + "%";
        //计算文本宽高
        mPercentTextPaint.getTextBounds(text, 0, String.valueOf(text).length(), mTextBound);
        //画文本百分比
        canvas.drawText(text, getWidth() / 2 - mTextBound.width() / 2,
                getHeight() / 2 + mTextBound.height() / 2, mPercentTextPaint);
    }


    /**
     * 给这个view设置点击事件，暴露一个动态设置百分比的方法
     */

    public void setCurPercent(float curPercent) {
         ValueAnimator animator = ValueAnimator.ofFloat(mCurPercent, curPercent);
        //动画时长由百分比大小决定
        animator.setDuration((long) (Math.abs(mCurPercent - curPercent) * 20));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                //四舍五入保留到小数点后两位

                mCurPercent = (float) (Math.round(value * 10)) / 10;
                //重绘，重走 onDraw() 方法，这也是不能在 onDraw() 中创建对象的原因
                invalidate();
            }
        });
        //开启动画
        animator.start();
    }



    public void setOnCircleClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }


}
