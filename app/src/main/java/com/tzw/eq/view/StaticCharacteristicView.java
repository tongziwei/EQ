package com.tzw.eq.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tzw.eq.DensityUtils;
import com.tzw.eq.R;

import androidx.annotation.Nullable;

public class StaticCharacteristicView extends View {
    private Context mContext;
    /**
     * 网格线画笔
     */
    private Paint paintLine;
    /**
     * 矩形笔
     */
    private Paint recPaint; //写字画笔

    private Paint dashUPaint; //u虚线画笔

    private Paint dataYPaint;  //数据线画笔

    private Paint dataTyPaint; //Ty拐点画笔

    /**
     * 上下文边距
     */
    private ContextMargin margin = new ContextMargin();


    /**
     * X轴刻度数
     */
    private int xLineCount = 7;
    /**
     * Y轴刻度数
     */
    private int yLineCount = 7;
    /**
     * Y轴最大值
     */
    private int yMaxValue = 0;
    /**
     * Y轴最小值
     */
    private int yMinValue = -60;
    /**
     * X轴刻度点数组
     */
    private int[] xAxisPointArr = null;
    /**
     * Y轴刻度点数组
     */
    private int[] yAxisPointArr = null;
    //X轴的刻度
    private int[] xStr;

    /**
     * 视图的宽
     */
    private float viewWidth;
    /**
     * 视图的高
     */
    private float viewHeight;

    private double[] uData;
    private double[] yData;
    private double ty;
    private double t;


    public StaticCharacteristicView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public StaticCharacteristicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext =context;
        init();
    }

    public StaticCharacteristicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    //设置x轴刻度
    public void setxStr(int[] xStr) {
        this.xStr = xStr;
        this.xLineCount = xStr.length;
        this.yLineCount = xLineCount;
    }

    /**
     * 设置Y轴最大值,最小值
     */
    public void setYScope(int maxValue, int minValue) {
        yMaxValue = maxValue;
        yMinValue = minValue;
    }


    public void setData(double[] uData,double[] yData,double ty,double t){
        this.uData = uData;
        this.yData = yData;
        this.ty = ty;
        this.t = t;
        invalidate();
    }

    public double[] getuData() {
        return uData;
    }

    public void setuData(double[] uData) {
        this.uData = uData;
    }

    public double[] getyData() {
        return yData;
    }

    public void setyData(double[] yData) {
        this.yData = yData;
    }

    public double getTy() {
        return ty;
    }

    public void setTy(double ty) {
        this.ty = ty;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        measureMargin();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFrame(canvas);
        drawPoint(canvas);
    }

    private void init() {
        Log.d("tzw", "init: ");
        // 网格线画笔
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.BLACK);
        paintLine.setFakeBoldText(true);
        paintLine.setStrokeWidth(1f);
        // 矩形画笔
        recPaint = new Paint();
        recPaint.setColor(Color.BLACK);
        recPaint.setTextAlign(Paint.Align.RIGHT);
        recPaint.setTextSize(DensityUtils.sp2px(mContext, 10));// 设置字体大小
        recPaint.setTypeface(Typeface.SANS_SERIF);// 设置字体样式

        dashUPaint = new Paint();
        dashUPaint.setAntiAlias(true);
        dashUPaint.setColor(Color.YELLOW);
        dashUPaint.setFakeBoldText(true);
        dashUPaint.setStrokeWidth(1.5f);

        dataYPaint = new Paint();
        dataYPaint.setAntiAlias(true);
        dataYPaint.setColor(Color.BLACK);
        dataYPaint.setFakeBoldText(true);
        dataYPaint.setStrokeWidth(3f);
        dataYPaint.setStyle(Paint.Style.STROKE);

        dataTyPaint = new Paint();
        dataTyPaint.setAntiAlias(true);
        dataTyPaint.setColor(Color.RED);
        dataTyPaint.setFakeBoldText(true);
        dataTyPaint.setStrokeWidth(3f);

        margin.updateMargin(DensityUtils.dip2px(mContext, 20),
                DensityUtils.dip2px(mContext, 30),
                DensityUtils.dip2px(mContext, 12),
                DensityUtils.dip2px(mContext, 60));// 初始化视图边距
    }

    /**
     * 计算边距值
     */
    private void measureMargin() {
        // 计算X轴刻度
        viewWidth = this.getWidth() - margin.getLeft() - margin.getRight();
        xAxisPointArr = new int[xLineCount];
        for (int i = 0; i < xAxisPointArr.length; i++) {
            xAxisPointArr[i] = (int) (viewWidth / (xLineCount - 1) * i + margin.getLeft());
        }
        // 计算Y轴刻度
        viewHeight = this.getHeight() - margin.getTop() - margin.getBottom();
        yAxisPointArr = new int[yLineCount];
        for (int i = 0; i < yAxisPointArr.length; i++) {
            yAxisPointArr[i] = (int) (viewHeight / (yLineCount - 1) * i + margin.getTop());
        }
    }

    /**
     * 绘制坐标系
     */
    private void drawFrame(Canvas canvas) {
        Log.d("tzw", "drawFrame: ");
        paintLine.setColor(Color.LTGRAY);
        paintLine.setStrokeWidth(1.5f);

        if (yAxisPointArr != null && xAxisPointArr != null && xStr != null) {
            // 绘制横线
            float yVol = yMaxValue - yMinValue;
            recPaint.setTextAlign(Paint.Align.RIGHT);// 右边是线
            for (int i = 0; i < yLineCount; i++) {
                canvas.drawLine(margin.getLeft(), yAxisPointArr[i], getWidth()
                        - margin.getRight(), yAxisPointArr[i], paintLine);

              //  int yVal = (int) (yMinValue + (yVol / (yLineCount - 1)) * i);
                int yVal = (int) (yMaxValue - (yVol / (yLineCount - 1)) * i);
                canvas.drawText(yVal + "",
                        margin.getLeft() - DensityUtils.dip2px(mContext, 2),
                        yAxisPointArr[i] + DensityUtils.dip2px(mContext, 4),
                        recPaint);

            }
            // 画竖线
            recPaint.setTextAlign(Paint.Align.CENTER);// 线中间
            for (int j = 0; j < xLineCount; j++) {
                canvas.drawLine(xAxisPointArr[j], margin.getTop(), xAxisPointArr[j],
                        this.getHeight() - margin.getBottom(), paintLine);
                // Hz 刻度
//            canvas.drawText(xStr[j], xAxisPointArr[j], margin.getTop()
//                    - DensityUtils.dip2px(mContext, 2), recPaint);
                canvas.drawText(String.valueOf(xStr[j]), xAxisPointArr[j], this.getHeight() - margin.getBottom()
                        + DensityUtils.dip2px(mContext, 10), recPaint);
            }
             //画Y轴标题
            canvas.drawText(mContext.getString(R.string.output_db),
                    margin.getLeft() + DensityUtils.dip2px(mContext, 20),
                    margin.getTop() - DensityUtils.dip2px(mContext, 10),
                    recPaint);
            //画X轴标题
            canvas.drawText(mContext.getString(R.string.input_db),
                    margin.getLeft() + viewWidth / 2,
                    this.getHeight() - margin.getBottom() + DensityUtils.dip2px(mContext, 25),
                    recPaint);
        }
    }


    private void drawPoint(Canvas canvas) {
        Log.d("tzw", "drawPoint: ");

        if (xAxisPointArr != null && uData!=null && yData != null) {
            int uLen = uData.length-1;
          //  Log.d("tzw", "drawPoint: xAxisPointArr"+ Arrays.toString(xAxisPointArr));
            for(int i=0;i<uLen;i++){
                canvas.drawLine(getXValue((float)uData[i]),getYValue((float) uData[i]),getXValue((float) uData[i+1]),getYValue((float) uData[i+1]),dashUPaint);
                canvas.drawLine(getXValue((float) uData[i]),getYValue((float) yData[i]),getXValue((float) uData[i+1]),getYValue((float) yData[i+1]),dataYPaint);
            }
            canvas.drawCircle(getXValue((float) t),getYValue((float)ty),10,dataTyPaint);

        }

    }


   /* *
     *  根据数据返回Y轴位置
     * @param yData 数据
     * @return y轴位置
     * */
    private float getYValue(float yData){
        // Y轴坐标 从上往下，递减
        // Y轴1像素对应的数值
         float tempInterval = (yMaxValue - yMinValue) / viewHeight; //

         float yPoint = (viewHeight - (Math.abs(yData - yMinValue))
                       / tempInterval + margin.getTop());
         return yPoint;
      /*  // Y轴坐标 从上往下，递增

        float tempInterval = (yMaxValue - yMinValue) / viewHeight; // Y轴1像素对应的数值
        int yPoint = (int) (viewHeight - (Math.abs(yMaxValue - data))
                / tempInterval + margin.getTop());
        return yPoint;*/
    }

/*    *
     *根据数据返回X轴位置
     * @param xData  数据
     * @return  x轴位置
     *
     * */
    private float getXValue(float xData){
        int len =xStr.length;
        //X轴1像素对应的数值
        float tempInterval = (xStr[len-1] - xStr[0]) / viewWidth; //

        float xPoint = margin.getLeft()+ (Math.abs(xData -xStr[0]))/ tempInterval;
        return xPoint;
    }

}
