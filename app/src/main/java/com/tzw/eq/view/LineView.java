package com.tzw.eq.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;
import java.util.List;

/**
 * 折线图
 */
public class LineView extends View {
    private static final int[] colors ={Color.BLUE,Color.RED,Color.YELLOW,0xFF800080,Color.GREEN,Color.GRAY,0xFFFF9800,0xFF90EE90,0xFFFF7F24,0xFFFF7F00};

    private GraphicalView mChartView;
    /**
     * 数据集合
     */
    private XYMultipleSeriesDataset mDataset;
    /**
     * 时间序列
     */
    private TimeSeries timeSeries;

    private Context mContext;
    private XYSeries mXYSeries;
    private XYMultipleSeriesRenderer xyMultipleSeriesRenderer;


    /**
     * 最大缓存数
     */
    public int maxCacheNum = 100;


    public LineView(Context context) {
        super(context);
        mContext = context;
    }

   /* public GraphicalView execute() {
        if (mChartView == null) {
            // 时间戳样式
            mChartView = ChartFactory.getTimeChartView(mContext,
                    getTimeSeriesDataset(), getDemoRenderer(), "mm:ss");
        }
        return mChartView;
    }*/

    public GraphicalView execute(String xTitle,String yTitle) {
        if (mChartView == null) {
            // 时间戳样式
        /*    mChartView = ChartFactory.getTimeChartView(mContext,
                    getTimeSeriesDataset(), getDemoRenderer(), "mm:ss");*/
            mDataset = buildDataset();
            double[] xValues = new double[1];
            xValues[0] = 0;
            double[] yValues = new double[1];
            yValues[0] = 0;
            mXYSeries = getXYSeries(mDataset,"",xValues,yValues,0);
            mDataset.addSeries(mXYSeries);
            XYMultipleSeriesRenderer renderer = getDemoRenderer(xTitle,yTitle);
            mChartView = ChartFactory.getLineChartView(mContext,mDataset,renderer);
        }
        return mChartView;
    }

    /**
     * @return 绘制多条曲线调用
     */
    public GraphicalView execute2(String xTitle,String yTitle) {
        if (mChartView == null) {
            // 时间戳样式
        /*    mChartView = ChartFactory.getTimeChartView(mContext,
                    getTimeSeriesDataset(), getDemoRenderer(), "mm:ss");*/
            mDataset = buildDataset();
            double[] xValues = new double[1];
            xValues[0] = 0;
            double[] yValues = new double[1];
            yValues[0] = 0;
            mXYSeries = getXYSeries(mDataset,"",xValues,yValues,0);
            mDataset.addSeries(mXYSeries);
            xyMultipleSeriesRenderer = getDemoRenderer2(xTitle,yTitle);
            mChartView = ChartFactory.getLineChartView(mContext,mDataset,xyMultipleSeriesRenderer);
        }
        return mChartView;
    }

    /**
     * 获取时间戳的数据
     *
     * @return
     */
    public XYMultipleSeriesDataset getTimeSeriesDataset() {
        mDataset = new XYMultipleSeriesDataset();
        timeSeries = new TimeSeries("无用标题");
        // 初始化一部分数据
        for (int k = 0; k < maxCacheNum; k++) {
            timeSeries.add(new Date(), 0);
        }
        mDataset.addSeries(timeSeries);
        return mDataset;
    }

    /**
     * 更新视图
     *
     * @param shorts
     */
    public void updateDate(double[] shorts) {
        mDataset.removeSeries(timeSeries);
        timeSeries.clear();
        for (int i = 0; i < shorts.length; i++) {
            timeSeries.add(new Date(), shorts[i]);
        }
        // 在数据集中添加新的点集
        mDataset.addSeries(timeSeries);
        // 曲线更新
        mChartView.invalidate();
    }

    /**
     * 获取渲染器
     *
     * @return
     */
    public XYMultipleSeriesRenderer getDemoRenderer(String xTitle,String yTitle) {
        /**
         * 渲染器
         */
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        // mRenderer.setChartTitle("随机数据");// 标题
         mRenderer.setXTitle(xTitle); // x轴说明
        mRenderer.setYTitle(yTitle);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setAxisTitleTextSize(30);
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setLabelsTextSize(25); // 数轴刻度字体大小
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setLegendTextSize(15); // 曲线说明
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0, Color.BLACK);
        mRenderer.setShowLegend(false);
        mRenderer.setYLabelsAlign(Paint.Align.LEFT);
        mRenderer.setMargins(new int[]{5, 50, 0, 5});// 上左下右{ 20, 30, 100, 0})
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setChartValuesTextSize(15);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillBelowLine(true);
        r.setFillBelowLineColor(Color.WHITE);
        r.setFillPoints(true);
        mRenderer.addSeriesRenderer(r);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setShowGrid(true);
        // mRenderer.setYLabels(10);// 设置Y轴默认显示个数
/*        mRenderer.setXLabels(0);// 设置X轴默认显示个数
        mRenderer.setYAxisMax(90);// 纵坐标最大值
        mRenderer.setYAxisMin(0);// 纵坐标最小值*/
        mRenderer.setInScroll(true);
        return mRenderer;
    }

    /**
     * 获取渲染器
     *
     * @return
     */
    public XYMultipleSeriesRenderer getDemoRenderer2(String xTitle,String yTitle) {
        /**
         * 渲染器
         */
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        // mRenderer.setChartTitle("随机数据");// 标题
        mRenderer.setXTitle(xTitle); // x轴说明 //"Frequency(Hz)"
        mRenderer.setYTitle(yTitle); //"Magnitude(dB)"
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setAxisTitleTextSize(30);
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setLabelsTextSize(25); // 数轴刻度字体大小
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setLegendTextSize(20); // 曲线说明
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0, Color.BLACK);
        mRenderer.setXLabels(10);//设置10个XLabels
        mRenderer.setShowLegend(true);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setYLabelsPadding(10);
        mRenderer.setMargins(new int[]{5, 80, 70, 5});// 上左下右{ 20, 30, 100, 0})
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setChartValuesTextSize(15);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setFillBelowLine(true);
        r.setFillBelowLineColor(Color.WHITE);
        r.setFillPoints(true);
        mRenderer.addSeriesRenderer(r);

        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setShowGrid(true);
        // mRenderer.setYLabels(10);// 设置Y轴默认显示个数
/*        mRenderer.setXLabels(0);// 设置X轴默认显示个数
        mRenderer.setYAxisMax(90);// 纵坐标最大值
        mRenderer.setYAxisMin(0);// 纵坐标最小值*/
        mRenderer.setInScroll(true);
        return mRenderer;
    }


    public XYMultipleSeriesDataset buildDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();/* 创建图表数据集 */
       // addXYSeries(dataset, titles, xValues, yValues, 0);              /* 添加单条曲线数据到图表数据集中 */
        return dataset;
    }

    /*   *//**
     * 曲线图(数据集) : 创建曲线图图表数据集
     *
     * @param title 赋予的标题
     * @param xValues x轴的数据
     * @param yValues y轴的数据
     * @return XY轴数据集
     */
    public void updateLine(String title,double[] xValues,double[] yValues){
        mDataset.removeSeries(mXYSeries);

        mXYSeries = getXYSeries(mDataset,title,xValues,yValues,0);
        mDataset.addSeries(mXYSeries);
        // 曲线更新
        mChartView.invalidate();
    }

    /**
     * 更新多条曲线
     * @param titles 赋予的标题组,数组大小为线条数
     * @param xValues x轴的数据组,list大小为线条数
     * @param yValues y轴的数据组，list大小为线条数
     */
    public void updateLines(String[] titles, List<double[]> xValues,
                            List<double[]> yValues){
        mDataset.clear();
        xyMultipleSeriesRenderer.removeAllRenderers();
        int length = titles.length;                         /* 获取标题个数 */
        for (int i = 0; i < length-1; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setChartValuesTextSize(15);
            r.setChartValuesSpacing(3);
            r.setPointStyle(PointStyle.POINT);
            r.setLineWidth(1);
      /*      r.setFillBelowLine(true);
            r.setFillBelowLineColor(Color.WHITE);*/
            r.setFillPoints(true);
            r.setStroke(BasicStroke.DASHED);
             xyMultipleSeriesRenderer.addSeriesRenderer(r);
        }
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLACK);
        r.setChartValuesTextSize(15);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setLineWidth(2);
      /*      r.setFillBelowLine(true);
            r.setFillBelowLineColor(Color.WHITE);*/
        r.setFillPoints(true);
        r.setStroke(BasicStroke.SOLID);
        xyMultipleSeriesRenderer.addSeriesRenderer(r);
        addXYSeries(mDataset,titles,xValues,yValues,0);
        // 曲线更新
        mChartView.invalidate();
    }

    /**
     * @param titles 赋予的标题组,数组大小为线条数
     * @param xValues x轴的数据组,list大小为线条数
     * @param yValues y轴的数据组，list大小为线条数
     * @param isSemilogDraw 是否进行半对数绘制
     */
    public void updateLines(String[] titles, List<double[]> xValues,
                            List<double[]> yValues,boolean isSemilogDraw){
        mDataset.clear();
        xyMultipleSeriesRenderer.removeAllRenderers();
        int length = titles.length;                         /* 获取标题个数 */
        for (int i = 0; i < length-1; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setChartValuesTextSize(15);
            r.setChartValuesSpacing(3);
            r.setPointStyle(PointStyle.POINT);
            r.setLineWidth(1);
      /*      r.setFillBelowLine(true);
            r.setFillBelowLineColor(Color.WHITE);*/
            r.setFillPoints(true);
            r.setStroke(BasicStroke.DASHED);
            xyMultipleSeriesRenderer.addSeriesRenderer(r);
        }
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLACK);
        r.setChartValuesTextSize(15);
        r.setChartValuesSpacing(3);
        r.setPointStyle(PointStyle.POINT);
        r.setLineWidth(2);
      /*      r.setFillBelowLine(true);
            r.setFillBelowLineColor(Color.WHITE);*/
        r.setFillPoints(true);
        r.setStroke(BasicStroke.SOLID);
        xyMultipleSeriesRenderer.addSeriesRenderer(r);

        addXYSeries(mDataset,titles,xValues,yValues,0); //
        // 曲线更新
        if(isSemilogDraw){
            xyMultipleSeriesRenderer.setXLabels(0);
            double[] xV = xValues.get(0);    //针对xV范围为【20,20000】,按对数进行划分
            xyMultipleSeriesRenderer.addXTextLabel(xV[0],"20");
            xyMultipleSeriesRenderer.addXTextLabel(xV[47],"100");
            xyMultipleSeriesRenderer.addXTextLabel(xV[93],"500");
            xyMultipleSeriesRenderer.addXTextLabel(xV[115],"1k");
            xyMultipleSeriesRenderer.addXTextLabel(xV[159],"5k");
            xyMultipleSeriesRenderer.addXTextLabel(xV[180],"10k");
            xyMultipleSeriesRenderer.addXTextLabel(xV[199],"20k");
        }


        mChartView.invalidate();
    }



    /**
     * 曲线图(被调用方法) : 添加 XY 轴坐标数据 到 XYMultipleSeriesDataset 数据集中
     *
     * @param dataset 最后的 XY 数据集结果, 相当与返回值在参数中
     * @param titles  要赋予的标题
     * @param xValues x轴数据集合
     * @param yValues y轴数据集合
     * @param scale   缩放
     *
     * titles 数组个数 与 xValues, yValues 个数相同
     * tittle 与 一个图标可能有多条曲线, 每个曲线都有一个标题
     * XYSeries 是曲线图中的 一条曲线, 其中封装了 曲线名称, X轴和Y轴数据
     */
    public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
                            List<double[]> yValues, int scale) {
        int length = titles.length;/* 获取标题个数 */

        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale); /* 单条曲线数据 */
            double[] xV = xValues.get(i);                     /* 获取该条曲线的x轴坐标数组 */
            double[] yV = yValues.get(i);                     /* 获取该条曲线的y轴坐标数组 */
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);                       /* 将该条曲线的 x,y 轴数组存放到 单条曲线数据中 */
            }
            dataset.addSeries(series);                        /* 将单条曲线数据存放到 图表数据集中 */
        }
    }



    public XYSeries getXYSeries(XYMultipleSeriesDataset dataset, String title, double[] xValues,
                           double[] yValues, int scale) {

            XYSeries series = new XYSeries(title, scale); /* 单条曲线数据 */
                               /* 获取该条曲线的y轴坐标数组 */
            int seriesLength = xValues.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xValues[k], yValues[k]);                       /* 将该条曲线的 x,y 轴数组存放到 单条曲线数据中 */
            }
           // dataset.addSeries(series);                        /* 将单条曲线数据存放到 图表数据集中 */
         return  series;
    }

}
