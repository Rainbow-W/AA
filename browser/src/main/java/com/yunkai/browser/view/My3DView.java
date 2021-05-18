package com.yunkai.browser.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * @Explain：
 * @Author:LYL
 * @Version:
 * @Time:2017/6/26
 */

public class My3DView extends View {
    private int mViewWidth, mViewHeight;//My3DView的宽高；
    private Paint mPaint;//在Canvas上绘制图片时用到的画笔；
    private Camera mCamera;//实现旋转的Camera；
    private Matrix matrix;//实现移动的matrix；
    private float mRotateDegree = 0;//当前动画旋转的程度值；
    private float mAxisX = 0, mAxisY = 0;//X方向旋转轴   Y方向旋转轴
    private int mPartNumber = 1;//在百叶窗中每张图片需要分成的份数；
    private List<Bitmap> bitmapList;//存放所有要展示的图片的List；
    private Bitmap[][] bitmaps;//百叶窗中每张图片被分成的小图片；
    private int direction = 1;//滚动方向:1竖直方向 其他为水平方向
    int averageWidth = 0, averageHeight = 0;//分成小图片的平均宽高；


    private RollMode rollMode = RollMode.Jalousie;//默认滚动模式为百叶窗；
    private int preIndex = 0, currIndex = 0, nextIndex = 0;//当前展示的图片下标及前一张后一张的下标；
    private ValueAnimator valueAnimator;//属性动画；
    private int rollDuration = 1 * 1000;//动画执行时间；
    //正在翻转
    private boolean rolling;//是否正在旋转中；


    //滚动模式
    public enum RollMode {
        //  没有效果 3D整体滚动 百叶窗
        Roll2D, Whole3D, Jalousie;
    }

    public My3DView(Context context) {
        this(context, null);
    }

    public My3DView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public My3DView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化工作；
     */
    private void init() {
        bitmapList = new ArrayList<>();
        matrix = new Matrix();
        mCamera = new Camera();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    //获取当前View宽高，根据宽高缩放图片；
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        if (mViewWidth != 0 && mViewHeight != 0) {
            for (int i = 0; i < bitmapList.size(); i++) {
                bitmapList.set(i, zoomBitmap(bitmapList.get(i)));
            }
        }
        //设置当前图片的上一张与下一站；
        setBitmapIndex();
        //需要分割；
        if (rollMode == RollMode.Jalousie)
            partitionBitmaps();
    }

    /**
     * 缩放图片；
     *
     * @param bit
     * @return
     */
    private Bitmap zoomBitmap(Bitmap bit) {
        if (bit == null)
            return null;
        int bitmapW = bit.getWidth();
        int bitmapH = bit.getHeight();
        float scaleW = (float) mViewWidth / bitmapW;
        float scaleH = (float) mViewHeight / bitmapH;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH);
        return Bitmap.createBitmap(bit, 0, 0, bitmapW, bitmapH, matrix, false);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        switch (rollMode) {
            case Whole3D:
            case Roll2D:
                draw3D(canvas);
                break;
            case Jalousie:
                drawJalousie(canvas);
                break;
        }
    }

    /**
     * 绘制3D效果；
     *
     * @param canvas
     */
    private void draw3D(Canvas canvas) {
        //初始
        Bitmap currBitmap = bitmapList.get(currIndex);
        Bitmap nextBitmap = bitmapList.get(nextIndex);
        canvas.save();
        //竖向转动
        if (direction == 1) {
            mCamera.save();
            //我们首先设置当前正在显示的图片的转动动画效果，如果是3D效果，设置旋转角度，
            //因为竖向转动，我们需要绕X轴旋转，rotateX（float deg）中deg参数为正时，会向屏幕外转动，
            // 当前需要向屏幕内测转动当前显示的图片，所以设置为-mRotateDegree;2D效果没有转动效果，所以设置转动效果为0；
            if (rollMode == RollMode.Whole3D)
                mCamera.rotateX(-mRotateDegree);
            else
                mCamera.rotateX(0);
            //获取camera中的matrix；
            mCamera.getMatrix(matrix);
            //不在使用camera需要复原；
            mCamera.restore();
            //使用matrix前乘移动，也就是在Camera转动前移动matrix，因为Camera转动是以View左上角为原点转动的，
            // 我们旋转时是以当前图片的上边缘为旋转轴来向屏幕内正着转动，所以在转动前我们需要先将矩阵向左移动View的1/2宽度，
            // 因为是以上边缘为轴，所以高度不用动；当旋转完后，我们需要将camera所在矩阵还原到原来位置，
            // 所以我们需要向右移动1/2View宽度；因为在旋转的过程中我们当前的View需要有一下向下移动的程度，因为之前我们已经计算好，
            // 这个度会随着我们的滚动而逐渐变大，也就是我们之前算好的mAxisY，最后在我们的canvas上边绘制我们当前图片就好。
            matrix.preTranslate(-mViewWidth / 2, 0);
            matrix.postTranslate(mViewWidth / 2, mAxisY);
            canvas.drawBitmap(currBitmap, matrix, mPaint);

            matrix.reset();
            mCamera.save();
            //如果是3D效果，因为竖向滚动，下一张图片，是从90度开始一直旋转到0度的一个过程，所以这里需要90-mRotateDegree；
            if (rollMode == RollMode.Whole3D)
                mCamera.rotateX(90 - mRotateDegree);
            else
                mCamera.rotateX(0);
            mCamera.getMatrix(matrix);
            mCamera.restore();
            //View宽度针对X轴的移动与上边是一样的，看一下高度，因为我们将要出现的这张图片是从当前View的上部分出来的，
            // 所以我们需要现将即将显示的图片移动到-mViewHeight的高度，然后在一点一点向下移动，最终到达当前View的高度；
            //最后绘制我们即将显示的图片；
            matrix.preTranslate(-mViewWidth / 2, -mViewHeight);//0
            matrix.postTranslate(mViewWidth / 2, mAxisY);//mViewHeight / 2 + mAxisY
            canvas.drawBitmap(nextBitmap, matrix, mPaint);
        } else {
            //以下为横向滚动的2D、3D效果实现，与竖向滚动原理一样；
            mCamera.save();
            if (rollMode == RollMode.Whole3D)
                mCamera.rotateY(mRotateDegree);
            else
                mCamera.rotateY(0);
            mCamera.getMatrix(matrix);
            mCamera.restore();

            matrix.preTranslate(0, -mViewHeight / 2);
            matrix.postTranslate(mAxisX, mViewHeight / 2);
            canvas.drawBitmap(currBitmap, matrix, mPaint);


            mCamera.save();
            if (rollMode == RollMode.Whole3D)
                mCamera.rotateY(-(90 - mRotateDegree));
            else
                mCamera.rotateY(0);
            mCamera.getMatrix(matrix);
            mCamera.restore();

            matrix.preTranslate(-mViewWidth, -mViewHeight / 2);
            matrix.postTranslate(mAxisX, mViewHeight / 2);
            canvas.drawBitmap(nextBitmap, matrix, mPaint);

        }

        canvas.restore();
    }

    /**
     * 绘制百叶窗效果；
     *
     * @param canvas
     */
    private void drawJalousie(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < mPartNumber; i++) {
            //获取当前显示与即将显示的图片的每一部分图片
            Bitmap currBitmap = bitmaps[currIndex][i];
            Bitmap nextBitmap = bitmaps[nextIndex][i];

            //小于90度只操作当前显示的图片；
            if (mRotateDegree < 90) {
                //纵向小于90度，因为没有显示出即将显示的图片，所以只需旋转第一张图片；
                if (direction == 1) {
                    mCamera.save();
                    //竖向的时候，为竖向分割，绕Y轴旋转；
                    mCamera.rotateY(mRotateDegree);
                    mCamera.getMatrix(matrix);
                    mCamera.restore();
                    //matrix向左向上是负的；因为旋转是按自身的中轴转动，所以首先移动自己的中心点到Camera原点，
                    //最后移动恢复原位置，为了并排显示，需要加上每一份的宽度；最终绘制当前显示图片的每一小部分图片；
                    matrix.preTranslate(-currBitmap.getWidth() / 2, -currBitmap.getHeight() / 2);
                    matrix.postTranslate(currBitmap.getWidth() / 2 + i * averageWidth, currBitmap.getHeight() / 2);
                    canvas.drawBitmap(currBitmap, matrix, mPaint);
                } else {
                    mCamera.save();
                    mCamera.rotateX(mRotateDegree);
                    mCamera.getMatrix(matrix);
                    mCamera.restore();
                    //matrix向左向上是负的；
                    matrix.preTranslate(-currBitmap.getWidth() / 2, -currBitmap.getHeight() / 2);
                    matrix.postTranslate(currBitmap.getWidth() / 2, currBitmap.getHeight() / 2 + i * averageHeight);
                    canvas.drawBitmap(currBitmap, matrix, mPaint);
                }

            } else {
                //当大于90度时，因为第一张图片已经不在显示，所以不用再管，只需旋转将要显示的图片就好；
                if (direction == 1) {
                    mCamera.save();
                    //从180最终旋转到0度；
                    mCamera.rotateY(180 - mRotateDegree);
                    mCamera.getMatrix(matrix);
                    mCamera.restore();
                    //matrix向左向上是负的；
                    matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight() / 2);
                    matrix.postTranslate(nextBitmap.getWidth() / 2 + i * averageWidth, nextBitmap.getHeight() / 2);
                    canvas.drawBitmap(nextBitmap, matrix, mPaint);
                } else {
                    mCamera.save();
                    mCamera.rotateX(180 - mRotateDegree);
                    mCamera.getMatrix(matrix);
                    mCamera.restore();
                    //matrix向左向上是负的；
                    matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight() / 2);
                    matrix.postTranslate(nextBitmap.getWidth() / 2, nextBitmap.getHeight() / 2 + i * averageHeight);
                    canvas.drawBitmap(nextBitmap, matrix, mPaint);
                }
            }

        }
        canvas.restore();

    }

    /**
     * 处理分割bitmap；
     */
    private void partitionBitmaps() {
        if (bitmapList == null || bitmapList.size() == 0) return;
        //下边在分割时会用到viewWidth与viewHeigth，所以需要在这里进行判断；
        if (mViewWidth == 0 || mViewHeight == 0) return;
        averageHeight = mViewHeight / mPartNumber;
        averageWidth = mViewWidth / mPartNumber;
        bitmaps = new Bitmap[bitmapList.size()][mPartNumber];
        for (int i = 0; i < bitmapList.size(); i++) {
            Bitmap bit = bitmapList.get(i);
            for (int j = 0; j < mPartNumber; j++) {
                Bitmap bitDebris;
                //百叶窗；
                if (rollMode == RollMode.Jalousie) {
                    //如果是竖向的百叶窗，需要竖向分割，最终绕Y轴转动；
                    if (direction == 1) {
                        bitDebris = Bitmap.createBitmap(bit, j * averageWidth, 0, averageWidth, mViewHeight);
                    } else
                        bitDebris = Bitmap.createBitmap(bit, 0, j * averageHeight, mViewWidth, averageHeight);
                } else {
                    if (direction == 1)
                        bitDebris = Bitmap.createBitmap(bit, j * averageWidth, 0, averageWidth, mViewHeight);
                    else
                        bitDebris = Bitmap.createBitmap(bit, 0, j * averageHeight, mViewWidth, averageHeight);
                }
                bitmaps[i][j] = bitDebris;
            }
        }
        setBitmapIndex();

    }


    private void setBitmapIndex() {
        preIndex = currIndex - 1;
        if (preIndex < 0) {
            preIndex = bitmapList.size() - 1;
        }
        nextIndex = currIndex + 1;
        if (nextIndex > bitmapList.size() - 1)
            nextIndex = 0;
    }

    /**
     * 设置滚动方向；
     */
    public void setDirection(int directionType) {
        direction = directionType;
    }

    /**
     * 添加图片；
     *
     * @param bitmap
     */
    public void addBitmap(Bitmap bitmap) {
        bitmapList.add(bitmap);
    }

    /**
     * 设置分割成的分数；
     *
     * @param num
     */
    public void setPartitionNums(int num) {
        mPartNumber = num;
        partitionBitmaps();
    }

    /**
     * 设置滚动样式；
     *
     * @param type
     */
    public void set3DType(RollMode type) {
        rollMode = type;
    }

    /**
     * 设置当前旋转程度；
     *
     * @param degreeValue
     */
    public void setDegreeValue(float degreeValue) {
        this.mRotateDegree = degreeValue;
        if (direction != 1)
            mAxisX = degreeValue / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * mViewWidth;
        else
            mAxisY = degreeValue / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * mViewHeight;
        invalidate();
    }

    /**
     * 改变滚动方向；
     */
    public void changeDirection() {
        direction = direction == 0 ? 1 : 0;
        partitionBitmaps();
    }

    public void changeModeType(RollMode roll) {
        rollMode = roll;
        partitionBitmaps();
    }

    public void toNext() {
        if (rolling) return;
        if (rollMode == RollMode.Jalousie) {
            valueAnimator = ValueAnimator.ofFloat(0, 180);
        } else if (rollMode == RollMode.Whole3D || rollMode == RollMode.Roll2D) {
            valueAnimator = ValueAnimator.ofFloat(0, 90);
        }
        rolling = true;
        valueAnimator.setDuration(rollDuration);
        valueAnimator.addUpdateListener(aniUpdateListener);
        valueAnimator.addListener(aniEndListener);
        valueAnimator.start();

    }

    private ValueAnimator.AnimatorUpdateListener aniUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            setDegreeValue((Float) animation.getAnimatedValue());
        }
    };
    private AnimatorListenerAdapter aniEndListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            currIndex++;
            if (currIndex > bitmapList.size() - 1)
                currIndex = 0;
            setBitmapIndex();
            setDegreeValue(0);
            rolling = false;

        }
    };


}
