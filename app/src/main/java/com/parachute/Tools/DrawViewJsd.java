package com.parachute.Tools;

import java.text.DecimalFormat;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;

public class DrawViewJsd extends View {
    DecimalFormat df4 = new DecimalFormat("####00.00");
    DecimalFormat df2 = new DecimalFormat("####00");
    DecimalFormat df3 = new DecimalFormat("####0.0");
    DecimalFormat df5 = new DecimalFormat("####0.00");
    boolean SpeedDrawOrNot = false;
    boolean DisplacementDrawOrNot = false;

    Point mpoint = new Point();// 定位点（左上角）

    int CurveWidth = 430;// 曲线区域宽度
    int CurveHeight = 260;// 曲线区域高度
    int LabelOffSet = 10;// 坐标轴文本偏移量
    float XMax = 600;// X取值上限
    float XMin = 0;// X取值下限
    float LMax = 20;// 左侧坐标轴取值上限
    float LMin = 0;// 左侧坐标轴取值下限
    float RUMax = 30;// 右上坐标轴取值上限
    float RUMin = 0;// 右下坐标轴取值下限
    float RDMax = 20;// 右下坐标轴取值上限
    float RDMin = 0;// 右下坐标轴取值下限
    int XNum = 10;// X坐标段数
    int YNum = 10;// Y坐标段数
    int YUNum = 5;// 右上坐标段数
    int YDNum = 5;// 右下坐标段数

    float XDivision = 42;// x坐标分度
    float YDivision = 26;// y坐标分度
    float YUDivision = 22;// 右上Y坐标分度
    float YDDivision = 22;// 右下Y坐标分度
    float[] Xaxis = new float[(XNum + 1) * 4 + 4];// X坐标轴
    // 前部为标线，后部为轴线，4个数据一条线
    float[] Yaxis = new float[(YNum + 1) * 4 + 4];// Y坐标轴
    // 前部为标线，后部为轴线，4个数据一条线
    float[] YUaxis = new float[(YUNum + 1) * 4 + 4];// 右上坐标轴
    // 前部为标线，后部为轴线，4个数据一条线
    float[] YDaxis = new float[(YDNum + 1) * 4 + 4];// 右下坐标轴
    // 前部为标线，后部为轴线，4个数据一条线


    float[] Xlines = new float[(XNum + 1) * 4 + 4];// 竖线
    float[] Ylines = new float[(XNum + 1) * 4 + 4];//横线
    float XaxisLine = 5;// X坐标单位线长度
    float YaxisLine = -5;// Y坐标单位线长度，负值为向左
    float YUaxisLine = 5;// 右上坐标单位线长度
    float YDaxisLine = 5;// 右下坐标单位线长度
    int XColor = Color.BLACK;// X坐标颜色
    int YColor = Color.BLUE;// Y坐标颜色
    int YUColor = Color.YELLOW;// 右上坐标颜色
    int YDColor = Color.RED;// 右下坐标颜色
    float[] SourceData1 = null;// Y原始数据
    float[] SourceData2 = null;// 右上原始数据
    float[] SourceData3 = null;// 右下原始数据
    float bca = 0.25f;// 第一控制点偏移量
    float bcb = 0.25f;// 第二控制点偏移量
    float XScale = 10f;// X坐标最大扩展系数 用法为最大值减最小值除此系数，然后最大值最小值按得到的扩展量扩展
    float YScale = 10f;// Y坐标最大扩展系数
    float YUScale = 10f;// YU坐标最大扩展系数
    float YDScale = 10f;// YD坐标最大扩展系数
    Boolean MaxOrMinIsSet = false;// 是否预设最大最小值

    public DrawViewJsd(Context context) {
        super(context);
    }

    // private class Point {
    // int X;
    // int Y;
    // }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        Paint p = new Paint();


        Point[] SerialData1 = null;

        SerialData1 = new Point[SourceData1.length];

        Point[] SerialData2 = null;

        SerialData2 = new Point[SourceData2.length];

        Point[] SerialData3 = null;

        SerialData3 = new Point[SourceData3.length];

        // 判断最大最小
        for (int i = 0; i < SourceData1.length; i++) {
            // /min=(a<b)?a:b;
            if (i == 0 && !MaxOrMinIsSet)// 初始不设最大最小值
            {
                // XMax = SourceData1[i];
                // XMin = SourceData1[i];
                LMax = SourceData1[i];
                LMin = SourceData1[i];
                if (SpeedDrawOrNot) {
                    RUMax = SourceData2[i];
                    RUMin = SourceData2[i];
                }
                if (DisplacementDrawOrNot) {
                    RDMax = SourceData3[i + 1];
                    RDMin = SourceData3[i + 1];
                }
            }
            // XMax = (XMax < SourceData1[i]) ? SourceData1[i] : XMax;
            // XMin = (XMin > SourceData1[i]) ? SourceData1[i] : XMin;
            LMax = (LMax < SourceData1[i]) ? SourceData1[i] : LMax;
            LMin = (LMin > SourceData1[i]) ? SourceData1[i] : LMin;
            if (SpeedDrawOrNot) {
                RUMax = (RUMax < SourceData2[i]) ? SourceData2[i] : RUMax;
                RUMin = (RUMin > SourceData2[i]) ? SourceData2[i] : RUMin;
            }
            if (DisplacementDrawOrNot) {
                RDMax = (RDMax < SourceData3[i]) ? SourceData3[i] : RDMax;
                RDMin = (RDMin > SourceData3[i]) ? SourceData3[i] : RDMin;
            }

        }
        // float dx = (XMax - XMin) / XScale;
        // XMax = XMax + dx;
        // XMin = XMin - dx;
        float dY = (LMax - LMin) / YScale;
        LMax = LMax + dY;
        LMin = LMin - dY;
        float dU = (RUMax - RUMin) / YUScale;
        RUMax = RUMax + dU;
        RUMin = RUMin - dU;
        float dD = (RDMax - RDMin) / YDScale;
        RDMax = RDMax + dD;
        RDMin = RDMin - dD;

        // 转换绘图点数据
        for (int i = 0; i < SourceData1.length; i++) {
            // 横坐标位置，三个数组的奇数位为X坐标
            // 基值+实际值/（最大-最小）*绘图区宽度
            float tmpx = (float) (mpoint.x + ((i * 0.25) - 0) / (600 - 0)
                    * CurveWidth);
            // 左侧纵坐标
            float tmpy = mpoint.y + CurveHeight - (SourceData1[i] - LMin)
                    / (LMax - LMin) * CurveHeight;
            SerialData1[i] = new Point((int) tmpx, (int) tmpy);
            // 右侧纵坐标
            if (SpeedDrawOrNot) {
                float tmpyu = mpoint.y + CurveHeight / 2 - YUDivision
                        - (SourceData2[i] - RUMin) / (RUMax - RUMin) * 5
                        * YUDivision;
                SerialData2[i] = new Point((int) tmpx, (int) tmpyu);
            }
            // 右侧纵坐标
            if (DisplacementDrawOrNot) {
                float tmpyd = mpoint.y + CurveHeight - YDDivision
                        - (SourceData3[i] - RDMin) / (RDMax - RDMin) * 5
                        * YDDivision;
                SerialData3[i] = new Point((int) tmpx, (int) tmpyd);
            }

        }

        p.setColor(Color.CYAN);
        canvas.drawLine(mpoint.x, mpoint.y + CurveHeight - (0 - LMin)
                / (LMax - LMin) * CurveHeight, mpoint.x + CurveWidth, mpoint.y + CurveHeight - (0 - LMin)
                / (LMax - LMin) * CurveHeight, p);
        canvas.drawText("0", mpoint.x + LabelOffSet, mpoint.y + CurveHeight - (0 - LMin)
                / (LMax - LMin) * CurveHeight + 2 * LabelOffSet, p);
        // p.setStyle(Style.STROKE);
        p.setTextAlign(Align.LEFT);
        // X轴坐标标签及坐标线坐标
        for (int i = 0; i <= Math.max(XNum, YNum); i++) {
            if (i <= XNum) {
                p.setColor(XColor);
                p.setTextAlign(Align.CENTER);
                canvas.drawText(df3.format(i * (XMax - XMin) / XNum + XMin),
                        mpoint.x + i * XDivision, mpoint.y + CurveHeight
                                + LabelOffSet + LabelOffSet, p);
                Xaxis[i * 4] = mpoint.x + i * XDivision;
                Xaxis[i * 4 + 1] = mpoint.y + CurveHeight;
                Xaxis[i * 4 + 2] = mpoint.x + i * XDivision;
                Xaxis[i * 4 + 3] = mpoint.y + CurveHeight + XaxisLine;

                Xlines[i * 4] = mpoint.x + i * XDivision;
                Xlines[i * 4 + 1] = mpoint.y + CurveHeight;
                Xlines[i * 4 + 2] = mpoint.x + i * XDivision;
                Xlines[i * 4 + 3] = mpoint.y;

            }
            // Y轴坐标线
            if (i <= YNum) {
                p.setColor(YColor);
                p.setTextAlign(Align.CENTER);
                canvas.drawText(df4.format(i * (LMax - LMin) / YNum + LMin),
                        mpoint.x - 2 * LabelOffSet, mpoint.y + CurveHeight
                                - YDivision * i, p);
                Yaxis[i * 4] = mpoint.x;
                Yaxis[i * 4 + 1] = mpoint.y + CurveHeight - YDivision * i;
                Yaxis[i * 4 + 2] = mpoint.x + YaxisLine;
                Yaxis[i * 4 + 3] = mpoint.y + CurveHeight - YDivision * i;

                Ylines[i * 4] = mpoint.x;
                Ylines[i * 4 + 1] = mpoint.y + CurveHeight - YDivision * i;
                Ylines[i * 4 + 2] = mpoint.x + CurveWidth + 2 * YaxisLine;
                Ylines[i * 4 + 3] = mpoint.y + CurveHeight - YDivision * i;

            }
            // 右上
            if (i <= YUNum && SpeedDrawOrNot) {
                p.setColor(YUColor);
                p.setTextAlign(Align.LEFT);
                canvas.drawText(
                        df5.format(i * (RUMax - RUMin) / YUNum + RUMin),
                        mpoint.x + CurveWidth + LabelOffSet, mpoint.y
                                + CurveHeight / 2 - 2 * LabelOffSet
                                - YUDivision * i, p);
                YUaxis[i * 4] = mpoint.x + CurveWidth;
                YUaxis[i * 4 + 1] = mpoint.y + CurveHeight / 2 - YUDivision
                        * (i + 1);
                YUaxis[i * 4 + 2] = mpoint.x + CurveWidth + YUaxisLine;
                YUaxis[i * 4 + 3] = mpoint.y + CurveHeight / 2 - YUDivision
                        * (i + 1);

            }
            if (i <= YDNum && DisplacementDrawOrNot) {
                p.setColor(YDColor);
                canvas.drawText(
                        df4.format(i * (RDMax - RDMin) / YDNum + RDMin),
                        mpoint.x + CurveWidth + LabelOffSet, (float) (mpoint.y
                                + CurveHeight - 1.5 * LabelOffSet - YDDivision
                                * i), p);
                YDaxis[i * 4] = mpoint.x + CurveWidth;
                YDaxis[i * 4 + 1] = mpoint.y + CurveHeight - YDDivision
                        * (i + 1);
                YDaxis[i * 4 + 2] = mpoint.x + CurveWidth + YDaxisLine;
                YDaxis[i * 4 + 3] = mpoint.y + CurveHeight - YDDivision
                        * (i + 1);

            }
        }
        //背景横线
        p.setColor(Color.LTGRAY);
        canvas.drawLines(Xlines, p);
        canvas.drawLines(Ylines, p);
        p.setColor(YColor);
        canvas.drawText("加速度(m/s²)", mpoint.x - LabelOffSet, mpoint.y - 2
                * LabelOffSet, p);
        // Y轴轴线
        Yaxis[(YNum + 1) * 4] = mpoint.x;
        Yaxis[(YNum + 1) * 4 + 1] = mpoint.y;
        Yaxis[(YNum + 1) * 4 + 2] = mpoint.x;
        Yaxis[(YNum + 1) * 4 + 3] = mpoint.y + CurveHeight;
        canvas.drawLines(Yaxis, p);
        // Y曲线
        for (int i = 0; i < SerialData1.length - 1; i++) {

            canvas.drawLine(SerialData1[i].x, SerialData1[i].y,
                    SerialData1[i + 1].x, SerialData1[i + 1].y, p);
        }

        // 右上坐标轴
        if (SpeedDrawOrNot) {
            p.setColor(YUColor);
            canvas.drawText("速度(m/s)", mpoint.x + CurveWidth + 4 * LabelOffSet,
                    mpoint.y - 2 * LabelOffSet, p);
            YUaxis[(YUNum + 1) * 4] = mpoint.x + CurveWidth;
            YUaxis[(YUNum + 1) * 4 + 1] = mpoint.y + CurveHeight / 2
                    - (YUNum + 1) * YUDivision;
            YUaxis[(YUNum + 1) * 4 + 2] = mpoint.x + CurveWidth;
            YUaxis[(YUNum + 1) * 4 + 3] = mpoint.y + CurveHeight / 2
                    - YUDivision;
            canvas.drawLines(YUaxis, p);
            // 右上曲线
            for (int i = 0; i < SerialData2.length - 1; i++) {

                canvas.drawLine(SerialData2[i].x, SerialData2[i].y,
                        SerialData2[i + 1].x, SerialData2[i + 1].y, p);
            }
        }
        // 右下坐标轴
        if (DisplacementDrawOrNot) {
            p.setColor(YDColor);
            canvas.drawText("位移(mm)", mpoint.x + CurveWidth + 4 * LabelOffSet,
                    mpoint.y + CurveHeight / 2 - LabelOffSet, p);
            YDaxis[(YDNum + 1) * 4] = mpoint.x + CurveWidth;
            YDaxis[(YDNum + 1) * 4 + 1] = mpoint.y + CurveHeight - (YDNum + 1)
                    * YDDivision;
            YDaxis[(YDNum + 1) * 4 + 2] = mpoint.x + CurveWidth;
            YDaxis[(YDNum + 1) * 4 + 3] = mpoint.y + CurveHeight - YDDivision;
            canvas.drawLines(YDaxis, p);
            // 右下曲线
            for (int i = 0; i < SerialData3.length - 1; i++) {

                canvas.drawLine(SerialData3[i].x, SerialData3[i].y,
                        SerialData3[i + 1].x, SerialData3[i + 1].y, p);
            }
        }
        p.setColor(XColor);

        canvas.drawText("时间(ms)", mpoint.x + CurveWidth + 3 * LabelOffSet,
                mpoint.y + CurveHeight + LabelOffSet, p);
        Xaxis[(XNum + 1) * 4] = mpoint.x;
        Xaxis[(XNum + 1) * 4 + 1] = mpoint.y + CurveHeight;
        Xaxis[(XNum + 1) * 4 + 2] = mpoint.x + CurveWidth;
        Xaxis[(XNum + 1) * 4 + 3] = mpoint.y + CurveHeight;
        canvas.drawLines(Xaxis, p);

        // 曲线数据转换

    }

    private Point[] getCtrlPoint(Point[] ps, float OffSetA, float OffSetB) {
        Point[] mres = new Point[ps.length * 2 - 2];
        int PreIndex = 0;
        int AftIndex1 = 0;
        int AftIndex2 = 0;
        for (int i = 0; i < ps.length - 1; i++) {
            PreIndex = i - 1;
            AftIndex1 = i + 1;
            AftIndex2 = i + 2;
            if (PreIndex < 0) {
                PreIndex = 0;
            }
            if (AftIndex1 >= ps.length) {
                AftIndex1 = ps.length - 1;
            }
            if (AftIndex2 >= ps.length) {
                AftIndex2 = ps.length - 1;
            }

            mres[i * 2] = new Point((int) (ps[i].x + OffSetA
                    * (ps[AftIndex1].x - ps[PreIndex].x)),
                    (int) (ps[i].y + OffSetA
                            * (ps[AftIndex1].y - ps[PreIndex].y)));
            mres[i * 2 + 1] = new Point((int) (ps[AftIndex1].x - OffSetB
                    * (ps[AftIndex2].x - ps[i].x)),
                    (int) (ps[AftIndex1].y - OffSetB
                            * (ps[AftIndex2].y - ps[i].y)));
        }

        return mres;
    }

    public void SetPoint(int a, int b) {
        mpoint = new Point(a, b);
    }

    public void SetResourceDataY(float[] mr) {
        SourceData1 = new float[mr.length];
        System.arraycopy(mr, 0, SourceData1, 0, mr.length);
    }

    public void SetResourceDataU(float[] mr) {
        SourceData2 = new float[mr.length];
        System.arraycopy(mr, 0, SourceData2, 0, mr.length);
    }

    public void SetResourceDataD(float[] mr) {
        SourceData3 = new float[mr.length];
        System.arraycopy(mr, 0, SourceData3, 0, mr.length);
    }

    public void SetSpeedDrawOrNot(boolean t) {
        SpeedDrawOrNot = t;
    }

    public void SetDisplacementDrawOrNot(boolean t) {
        DisplacementDrawOrNot = t;
    }
}
