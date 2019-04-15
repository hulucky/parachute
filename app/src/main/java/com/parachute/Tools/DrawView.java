package com.parachute.Tools;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Point;
import android.view.View;

public class DrawView extends View {
	DecimalFormat df4 = new DecimalFormat("####00.00");
	DecimalFormat df2 = new DecimalFormat("####00");
	DecimalFormat df3 = new DecimalFormat("####0.0");

	Point mpoint = new Point();// 定位点（左上角）

	public Boolean getShowGKD() {
		return showGKD;
	}

	public void setShowGKD(Boolean showGKD) {
		this.showGKD = showGKD;
	}

	Boolean showGKD=false;

	int CurveWidth = 400;// 绘制区域宽度
	int CurveHeight = 400;// 绘制区域高度
	int LabelOffSet = 10;// 坐标轴文本便宜量
	float XMax = 20;// X取值上限
	float XMin = 20;// X取值下限
	float LMax = 20;// 左侧坐标轴取值上限
	float LMin = 20;// 左侧坐标轴取值下限
	float RUMax = 30;// 右上坐标轴取值上限
	float RUMin = 30;// 右下坐标轴取值下限
	float RDMax = 20;// 右下坐标轴取值上限
	float RDMin = 20;// 右下坐标轴取值下限
	int XNum = 10;// X坐标段数
	int YNum = 10;// Y坐标段数
	int YUNum = 5;// 右上坐标段数

	public Point getMpoint() {
		return mpoint;
	}

	public void setMpoint(Point mpoint) {
		this.mpoint = mpoint;
	}

	public int getCurveWidth() {
		return CurveWidth;
	}

	public void setCurveWidth(int curveWidth) {
		CurveWidth = curveWidth;
	}

	public int getCurveHeight() {
		return CurveHeight;
	}

	public void setCurveHeight(int curveHeight) {
		CurveHeight = curveHeight;
	}

	public int getLabelOffSet() {
		return LabelOffSet;
	}

	public void setLabelOffSet(int labelOffSet) {
		LabelOffSet = labelOffSet;
	}

	public float getLMax() {
		return LMax;
	}

	public void setLMax(float LMax) {
		this.LMax = LMax;
	}

	public float getLMin() {
		return LMin;
	}

	public void setLMin(float LMin) {
		this.LMin = LMin;
	}

	public float getRUMax() {
		return RUMax;
	}

	public void setRUMax(float RUMax) {
		this.RUMax = RUMax;
	}

	public float getRUMin() {
		return RUMin;
	}

	public void setRUMin(float RUMin) {
		this.RUMin = RUMin;
	}

	public float getRDMax() {
		return RDMax;
	}

	public void setRDMax(float RDMax) {
		this.RDMax = RDMax;
	}

	public float getRDMin() {
		return RDMin;
	}

	public void setRDMin(float RDMin) {
		this.RDMin = RDMin;
	}

	public Boolean getMaxOrMinIsSet() {
		return MaxOrMinIsSet;
	}

	public void setMaxOrMinIsSet(Boolean maxOrMinIsSet) {
		MaxOrMinIsSet = maxOrMinIsSet;
	}

	int YDNum = 5;// 右下坐标段数

	float XDivision = 40;// x坐标分度
	float YDivision = 40;// y坐标分度
	float YUDivision = 30;// 右上Y坐标分度
	float YDDivision = 30;// 右下Y坐标分度
	float[] Xaxis = new float[(XNum + 1) * 4 + 4];// X坐标轴
													// 前部为标线，后部为轴线，4个数据一条线
	float[] Yaxis = new float[(YNum + 1) * 4 + 4];// Y坐标轴
													// 前部为标线，后部为轴线，4个数据一条线
	float[] YUaxis = new float[(YUNum + 1) * 4 + 4];// 右上坐标轴
													// 前部为标线，后部为轴线，4个数据一条线
	float[] YDaxis = new float[(YDNum + 1) * 4 + 4];// 右下坐标轴
													// 前部为标线，后部为轴线，4个数据一条线
	float XaxisLine = 5;// X坐标单位线长度
	float YaxisLine = -5;// Y坐标单位线长度，负值为向左
	float YUaxisLine = 5;// 右上坐标单位线长度
	float YDaxisLine = 5;// 右下坐标单位线长度
	int XColor = Color.BLACK;// X坐标颜色
	int YColor = Color.BLUE;// Y坐标颜色
	int YUColor = Color.GREEN;// 右上坐标颜色
	int YDColor = Color.RED;// 右下坐标颜色
	float[] SourceData1 = null;// Y原始数据
	float[] SourceData2 = null;// 右上原始数据

	public DecimalFormat getDf4() {
		return df4;
	}

	public void setDf4(DecimalFormat df4) {
		this.df4 = df4;
	}

	public DecimalFormat getDf2() {
		return df2;
	}

	public void setDf2(DecimalFormat df2) {
		this.df2 = df2;
	}

	public DecimalFormat getDf3() {
		return df3;
	}

	public void setDf3(DecimalFormat df3) {
		this.df3 = df3;
	}

	public float getXMax() {
		return XMax;
	}

	public void setXMax(float XMax) {
		this.XMax = XMax;
	}

	public float getXMin() {
		return XMin;
	}

	public void setXMin(float XMin) {
		this.XMin = XMin;
	}

	public int getXNum() {
		return XNum;
	}

	public void setXNum(int XNum) {
		this.XNum = XNum;
	}

	public int getYNum() {
		return YNum;
	}

	public void setYNum(int YNum) {
		this.YNum = YNum;
	}

	public int getYUNum() {
		return YUNum;
	}

	public void setYUNum(int YUNum) {
		this.YUNum = YUNum;
	}

	public int getYDNum() {
		return YDNum;
	}

	public void setYDNum(int YDNum) {
		this.YDNum = YDNum;
	}

	public float getXDivision() {
		return XDivision;
	}

	public void setXDivision(float XDivision) {
		this.XDivision = XDivision;
	}

	public float getYDivision() {
		return YDivision;
	}

	public void setYDivision(float YDivision) {
		this.YDivision = YDivision;
	}

	public float getYUDivision() {
		return YUDivision;
	}

	public void setYUDivision(float YUDivision) {
		this.YUDivision = YUDivision;
	}

	public float getYDDivision() {
		return YDDivision;
	}

	public void setYDDivision(float YDDivision) {
		this.YDDivision = YDDivision;
	}

	public float[] getXaxis() {
		return Xaxis;
	}

	public void setXaxis(float[] xaxis) {
		Xaxis = xaxis;
	}

	public float[] getYaxis() {
		return Yaxis;
	}

	public void setYaxis(float[] yaxis) {
		Yaxis = yaxis;
	}

	public float[] getYUaxis() {
		return YUaxis;
	}

	public void setYUaxis(float[] YUaxis) {
		this.YUaxis = YUaxis;
	}

	public float[] getYDaxis() {
		return YDaxis;
	}

	public void setYDaxis(float[] YDaxis) {
		this.YDaxis = YDaxis;
	}

	public float getXaxisLine() {
		return XaxisLine;
	}

	public void setXaxisLine(float xaxisLine) {
		XaxisLine = xaxisLine;
	}

	public float getYaxisLine() {
		return YaxisLine;
	}

	public void setYaxisLine(float yaxisLine) {
		YaxisLine = yaxisLine;
	}

	public float getYUaxisLine() {
		return YUaxisLine;
	}

	public void setYUaxisLine(float YUaxisLine) {
		this.YUaxisLine = YUaxisLine;
	}

	public float getYDaxisLine() {
		return YDaxisLine;
	}

	public void setYDaxisLine(float YDaxisLine) {
		this.YDaxisLine = YDaxisLine;
	}

	public int getXColor() {
		return XColor;
	}

	public void setXColor(int XColor) {
		this.XColor = XColor;
	}

	public int getYColor() {
		return YColor;
	}

	public void setYColor(int YColor) {
		this.YColor = YColor;
	}

	public int getYUColor() {
		return YUColor;
	}

	public void setYUColor(int YUColor) {
		this.YUColor = YUColor;
	}

	public int getYDColor() {
		return YDColor;
	}

	public void setYDColor(int YDColor) {
		this.YDColor = YDColor;
	}

	public float[] getSourceData1() {
		return SourceData1;
	}

	public void setSourceData1(float[] sourceData1) {
		SourceData1 = sourceData1;
	}

	public float[] getSourceData2() {
		return SourceData2;
	}

	public void setSourceData2(float[] sourceData2) {
		SourceData2 = sourceData2;
	}

	public float[] getSourceData3() {
		return SourceData3;
	}

	public void setSourceData3(float[] sourceData3) {
		SourceData3 = sourceData3;
	}

	public float getBca() {
		return bca;
	}

	public void setBca(float bca) {
		this.bca = bca;
	}

	public float getBcb() {
		return bcb;
	}

	public void setBcb(float bcb) {
		this.bcb = bcb;
	}

	public float getXScale() {
		return XScale;
	}

	public void setXScale(float XScale) {
		this.XScale = XScale;
	}

	public float getYScale() {
		return YScale;
	}

	public void setYScale(float YScale) {
		this.YScale = YScale;
	}

	public float getYUScale() {
		return YUScale;
	}

	public void setYUScale(float YUScale) {
		this.YUScale = YUScale;
	}

	public float getYDScale() {
		return YDScale;
	}

	public void setYDScale(float YDScale) {
		this.YDScale = YDScale;
	}

	float[] SourceData3 = null;// 右下原始数据
	float bca = 0.25f;// 第一控制点偏移量
	float bcb = 0.25f;// 第二控制点偏移量
	float XScale = 10f;// X坐标最大扩展系数 用法为最大值减最小值除此系数，然后最大值最小值按得到的扩展量扩展
	float YScale = 10f;// Y坐标最大扩展系数
	float YUScale = 10f;// YU坐标最大扩展系数
	float YDScale = 10f;// YD坐标最大扩展系数
	Boolean MaxOrMinIsSet = false;// 是否预设最大最小值

	public DrawView(Context context) {
		super(context);
	}

	// private class Point {
	// int X;
	// int Y;
	// }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*
		 * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
		 * drawLine 绘制直线 drawPoin 绘制点
		 */
		// 创建画笔
		Paint p = new Paint();
//		p.setColor(Color.RED);
//		p.setStyle(Paint.Style.STROKE);
//		Point[] tmpa = new Point[5];
//		// for (int i = 0; i < 5; i++) {
//		// tmpa[i].x = 100 + i * 100;
//		// tmpa[i].y = 150 + i % 2 * 40 - i % 3 * (30);
//		// }
//		tmpa[0] = new Point(100, 120);
//
//		tmpa[1] = new Point(200, 320);
//		tmpa[2] = new Point(300, 150);
//		tmpa[3] = new Point(400, 420);
//		tmpa[4] = new Point(500, 120);
//
//		Point[] tmpC = getCtrlPoint(tmpa, 0.25f, 0.25f);
//		for (int i = 0; i < 4; i++) {
//			p.setColor(Color.RED);
//			p.setStyle(Paint.Style.STROKE);
//			Path pathtmp = new Path();
//			pathtmp.moveTo(tmpa[i].x, tmpa[i].y);
//			pathtmp.cubicTo(tmpC[i * 2].x, tmpC[i * 2].y, tmpC[i * 2 + 1].x,
//					tmpC[i * 2 + 1].y, tmpa[i + 1].x, tmpa[i + 1].y);
//			canvas.drawPath(pathtmp, p);
//			p.setColor(Color.BLUE);
//
//			canvas.drawCircle(tmpa[i].x + 1, tmpa[i].y + 1, 10, p);
//
//		}
//		p.setColor(Color.BLUE);
//		p.setStyle(Paint.Style.FILL);
//		canvas.drawCircle(tmpa[4].x + 1, tmpa[4].y + 1, 10, p);
//		p.setColor(Color.GREEN);
//		p.setStyle(Paint.Style.STROKE);
//		for (int i = 0; i < tmpC.length; i++) {
//			canvas.drawCircle(tmpC[i].x, tmpC[i].y, 10, p);
//		}

		// p.setColor(Color.RED);// 设置红色
		//
		// canvas.drawText("画圆：", 10, 20, p);// 画文本
		// canvas.drawCircle(60, 20, 10, p);// 小圆
		// p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
		// canvas.drawCircle(120, 20, 20, p);// 大圆
		//
		// canvas.drawText("画线及弧线：", 10, 60, p);
		// p.setColor(Color.GREEN);// 设置绿色
		// canvas.drawLine(60, 40, 100, 40, p);// 画线
		// canvas.drawLine(110, 40, 190, 80, p);// 斜线
		// float[] aa = new float[] { 20, 400, 20, 410, 30, 500, 30, 510, 40,
		// 500,
		// 40, 510, 50, 500, 50, 510 };
		//
		// canvas.drawLines(aa, 0, aa.length, p);
		// // 画笑脸弧线
		// p.setStyle(Paint.Style.STROKE);// 设置空心
		// RectF oval1 = new RectF(150, 20, 180, 40);
		// canvas.drawArc(oval1, 180, 180, false, p);// 小弧形
		// oval1.set(190, 20, 220, 40);
		// canvas.drawArc(oval1, 180, 180, false, p);// 小弧形
		// oval1.set(160, 30, 210, 60);
		// canvas.drawArc(oval1, 0, 180, false, p);// 小弧形
		//
		// canvas.drawText("画矩形：", 10, 80, p);
		// p.setColor(Color.GRAY);// 设置灰色
		// p.setStyle(Paint.Style.FILL);// 设置填满
		// canvas.drawRect(60, 60, 80, 80, p);// 正方形
		// canvas.drawRect(60, 90, 160, 100, p);// 长方形
		//
		// canvas.drawText("画扇形和椭圆:", 10, 120, p);
		// /* 设置渐变色 这个正方形的颜色是改变的 */
		// Shader mShader = new LinearGradient(0, 0, 100, 100,
		// new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
		// Color.LTGRAY }, null, Shader.TileMode.REPEAT); //
		// 一个材质,打造出一个线性梯度沿著一条线。
		// p.setShader(mShader);
		// // p.setColor(Color.BLUE);
		// RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
		// canvas.drawArc(oval2, 200, 130, true, p);
		// // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
		// // 画椭圆，把oval改一下
		// oval2.set(210, 100, 250, 130);
		// canvas.drawOval(oval2, p);
		//
		// canvas.drawText("画三角形：", 10, 200, p);
		// // 绘制这个三角形,你可以绘制任意多边形
		// Path path = new Path();
		// path.moveTo(80, 200);// 此点为多边形的起点
		// path.lineTo(120, 250);
		// path.lineTo(80, 250);
		// path.close(); // 使这些点构成封闭的多边形
		// canvas.drawPath(path, p);
		//
		// // 你可以绘制很多任意多边形，比如下面画六连形
		// p.reset();// 重置
		// p.setColor(Color.LTGRAY);
		// p.setStyle(Paint.Style.STROKE);// 设置空心
		// Path path1 = new Path();
		// path1.moveTo(180, 200);
		// path1.lineTo(200, 200);
		// path1.lineTo(210, 210);
		// path1.lineTo(200, 220);
		// path1.lineTo(180, 220);
		// path1.lineTo(170, 210);
		// path1.close();// 封闭
		// canvas.drawPath(path1, p);
		// /*
		// * Path类封装复合(多轮廓几何图形的路径
		// * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
		// * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
		// */
		//
		// // 画圆角矩形
		// p.setStyle(Paint.Style.FILL);// 充满
		// p.setColor(Color.LTGRAY);
		// p.setAntiAlias(true);// 设置画笔的锯齿效果
		// canvas.drawText("画圆角矩形:", 10, 260, p);
		// RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
		// canvas.drawRoundRect(oval3, 20, 15, p);// 第二个参数是x半径，第三个参数是y半径
		//
		// // 画贝塞尔曲线
		// canvas.drawText("画贝塞尔曲线:", 10, 310, p);
		// p.reset();
		// p.setStyle(Paint.Style.STROKE);
		// p.setColor(Color.GREEN);
		//
		// Path path2 = new Path();
		// path2.moveTo(100, 320);// 设置Path的起点
		// path2.quadTo(150, 310, 170, 400); // 设置贝塞尔曲线的控制点坐标和终点坐标
		// canvas.drawPath(path2, p);// 画出贝塞尔曲线
		//
		// // 画点
		// p.setColor(Color.RED);
		// p.setStyle(Paint.Style.FILL);
		// canvas.drawText("画点：", 10, 390, p);
		// canvas.drawPoint(100, 320, p);// 画一个点
		// canvas.drawPoints(new float[] { 150, 310, 170, 400 }, p);// 画多个点
		//
		// // 画图片，就是贴图
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher);
		// canvas.drawBitmap(bitmap, 250, 360, p);

		// 获取数据后，判断最大最小值
		// 测试值
//		mpoint.x = 50;
//		mpoint.y = 50;
//		SourceData1 = new float[] { 5.0f, 7.0f, 15.0f, 55.0f, 25.0f, 75.0f,
//				35.0f, 55.0f, 45.0f, 65.0f };
//		SourceData2 = new float[] { 5.0f, 20.0f, 15.0f, 30.0f, 25.0f, 32.0f,
//				35.0f, 25.0f, 45.0f, 45.0f };
//		SourceData3 = new float[] { 5.0f, 20f, 15.0f, 50f, 25.0f, 70f, 35.0f,
//				80f, 45.0f, 90f };
		Point[] SerialData1 = null;
		if (SourceData1.length % 2 == 0) {
			SerialData1 = new Point[SourceData1.length / 2];
		}
		Point[] SerialData2 = null;
		if (SourceData2.length % 2 == 0) {
			SerialData2 = new Point[SourceData2.length / 2];
		}
		Point[] SerialData3 = null;
		if (SourceData3.length % 2 == 0) {
			SerialData3 = new Point[SourceData3.length / 2];
		}
		// 判断最大最小
		for (int i = 0; i < SourceData1.length; i += 2) {
			// /min=(a<b)?a:b;
			if (i == 0 && !MaxOrMinIsSet)// 初始不设最大最小值
			{
				XMax = SourceData1[i];
				XMin = SourceData1[i];
				LMax = SourceData1[i + 1];
				LMin = SourceData1[i + 1];
				RUMax = SourceData2[i + 1];
				RUMin = SourceData2[i + 1];
				RDMax = SourceData3[i + 1];
				RDMin = SourceData3[i + 1];
			}
			XMax = (XMax < SourceData1[i]) ? SourceData1[i] : XMax;
			XMin = (XMin > SourceData1[i]) ? SourceData1[i] : XMin;
			LMax = (LMax < SourceData1[i + 1]) ? SourceData1[i + 1] : LMax;
			LMin = (LMin > SourceData1[i + 1]) ? SourceData1[i + 1] : LMin;
			RUMax = (RUMax < SourceData2[i + 1]) ? SourceData2[i + 1] : RUMax;
			RUMin = (RUMin > SourceData2[i + 1]) ? SourceData2[i + 1] : RUMin;
			RDMax = (RDMax < SourceData3[i + 1]) ? SourceData3[i + 1] : RDMax;
			RDMin = (RDMin > SourceData3[i + 1]) ? SourceData3[i + 1] : RDMin;

		}
		float dx = (XMax - XMin) / XScale;
		XMax = XMax + dx;
		XMin = XMin - dx;
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
		for (int i = 0; i < SourceData1.length; i += 2) {
			// 横坐标位置，三个数组的奇数位为X坐标
			// 基值+实际值/（最大-最小）*绘图区宽度
			float tmpx = mpoint.x + (SourceData1[i] - XMin) / (XMax - XMin)
					* CurveWidth;

			// 左侧纵坐标
			float tmpy = mpoint.y + CurveHeight - (SourceData1[i + 1] - LMin)
					/ (LMax - LMin) * CurveHeight;
			SerialData1[i / 2] = new Point((int) tmpx, (int) tmpy);
			// 右侧纵坐标
			float tmpyu = mpoint.y + CurveHeight / 2 - YUDivision
					- (SourceData2[i + 1] - RUMin) / (RUMax - RUMin) * 5
					* YUDivision;
			SerialData2[i / 2] = new Point((int) tmpx, (int) tmpyu);
			// 右侧纵坐标
			float tmpyd = mpoint.y + CurveHeight - YDDivision
					- (SourceData3[i + 1] - RDMin) / (RDMax - RDMin) * 5
					* YDDivision;
			SerialData3[i / 2] = new Point((int) tmpx, (int) tmpyd);

		}

		p.setColor(Color.DKGRAY);
		// p.setStyle(Style.STROKE);
		p.setTextAlign(Align.LEFT);

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

			}
			if (i <= YNum) {
				p.setColor(YColor);
				p.setTextAlign(Align.CENTER);
				canvas.drawText(df3.format(i * (LMax - LMin) / YNum + LMin),
						mpoint.x - 2 * LabelOffSet, mpoint.y + CurveHeight
								- YDivision * i, p);
				Yaxis[i * 4] = mpoint.x;
				Yaxis[i * 4 + 1] = mpoint.y + CurveHeight - YDivision * i;
				Yaxis[i * 4 + 2] = mpoint.x + YaxisLine;
				Yaxis[i * 4 + 3] = mpoint.y + CurveHeight - YDivision * i;

			}
			if (i <= YUNum) {
				p.setColor(YUColor);
				p.setTextAlign(Align.LEFT);
				canvas.drawText(
						df3.format(i * (RUMax - RUMin) / YUNum + RUMin),
						mpoint.x + CurveWidth + LabelOffSet, mpoint.y
								+ CurveHeight / 2 - 3 * LabelOffSet
								- YUDivision * i, p);
				YUaxis[i * 4] = mpoint.x + CurveWidth;
				YUaxis[i * 4 + 1] = mpoint.y + CurveHeight / 2 - YUDivision
						* (i + 1);
				YUaxis[i * 4 + 2] = mpoint.x + CurveWidth + YUaxisLine;
				YUaxis[i * 4 + 3] = mpoint.y + CurveHeight / 2 - YUDivision
						* (i + 1);

			}
			if (i <= YDNum) {
				p.setColor(YDColor);
				canvas.drawText(
						df3.format(i * (RDMax - RDMin) / YDNum + RDMin),
						mpoint.x + CurveWidth + LabelOffSet, mpoint.y
								+ CurveHeight - 3 * LabelOffSet - YDDivision
								* i, p);
				YDaxis[i * 4] = mpoint.x + CurveWidth;
				YDaxis[i * 4 + 1] = mpoint.y + CurveHeight - YDDivision
						* (i + 1);
				YDaxis[i * 4 + 2] = mpoint.x + CurveWidth + YDaxisLine;
				YDaxis[i * 4 + 3] = mpoint.y + CurveHeight - YDDivision
						* (i + 1);

			}
		}
		p.setColor(YColor);
		canvas.drawText("h(Pa)", mpoint.x - LabelOffSet, mpoint.y - 2
				* LabelOffSet, p);
		// Y轴轴线
		Yaxis[(YNum + 1) * 4] = mpoint.x;
		Yaxis[(YNum + 1) * 4 + 1] = mpoint.y;
		Yaxis[(YNum + 1) * 4 + 2] = mpoint.x;
		Yaxis[(YNum + 1) * 4 + 3] = mpoint.y + CurveHeight;
		canvas.drawLines(Yaxis, p);
		Point[] tmpY = getCtrlPoint(SerialData1, 0.2f, 0.2f);
		for (int i = 0; i < SerialData1.length - 1; i++) {

			p.setStyle(Paint.Style.STROKE);
			Path pathtmp = new Path();
			pathtmp.moveTo(SerialData1[i].x, SerialData1[i].y);
			pathtmp.cubicTo(tmpY[i * 2].x, tmpY[i * 2].y, tmpY[i * 2 + 1].x,
					tmpY[i * 2 + 1].y, SerialData1[i + 1].x,
					SerialData1[i + 1].y);
			canvas.drawPath(pathtmp, p);

		}
		if(showGKD){
		for (int i = 0; i < SerialData1.length; i++) {
			canvas.drawCircle(SerialData1[i].x, SerialData1[i].y,3,p);

		}}

		p.setColor(YUColor);
		canvas.drawText("p(kW)", mpoint.x + CurveWidth + 2 * LabelOffSet,
				mpoint.y - LabelOffSet, p);
		YUaxis[(YUNum + 1) * 4] = mpoint.x + CurveWidth;
		YUaxis[(YUNum + 1) * 4 + 1] = mpoint.y + CurveHeight / 2 - (YUNum + 1)
				* YUDivision;
		YUaxis[(YUNum + 1) * 4 + 2] = mpoint.x + CurveWidth;
		YUaxis[(YUNum + 1) * 4 + 3] = mpoint.y + CurveHeight / 2 - YUDivision;
		canvas.drawLines(YUaxis, p);
		Point[] tmpYU = getCtrlPoint(SerialData2, 0.2f, 0.2f);
		for (int i = 0; i < SerialData2.length - 1; i++) {

			p.setStyle(Paint.Style.STROKE);
			Path pathtmp = new Path();
			pathtmp.moveTo(SerialData2[i].x, SerialData2[i].y);
			pathtmp.cubicTo(tmpYU[i * 2].x, tmpYU[i * 2].y, tmpYU[i * 2 + 1].x,
					tmpYU[i * 2 + 1].y, SerialData2[i + 1].x,
					SerialData2[i + 1].y);
			canvas.drawPath(pathtmp, p);

		}
		if(showGKD){
			for (int i = 0; i < SerialData2.length; i++) {
				canvas.drawCircle(SerialData2[i].x, SerialData2[i].y,3,p);

			}}
		p.setColor(YDColor);
		canvas.drawText("η(%)", mpoint.x + CurveWidth + 2 * LabelOffSet,
				mpoint.y + CurveHeight / 2 - LabelOffSet, p);
		YDaxis[(YDNum + 1) * 4] = mpoint.x + CurveWidth;
		YDaxis[(YDNum + 1) * 4 + 1] = mpoint.y + CurveHeight - (YDNum + 1)
				* YDDivision;
		YDaxis[(YDNum + 1) * 4 + 2] = mpoint.x + CurveWidth;
		YDaxis[(YDNum + 1) * 4 + 3] = mpoint.y + CurveHeight - YDDivision;
		canvas.drawLines(YDaxis, p);
		Point[] tmpYD = getCtrlPoint(SerialData3, 0.2f, 0.2f);
		for (int i = 0; i < SerialData3.length - 1; i++) {

			p.setStyle(Paint.Style.STROKE);
			Path pathtmp = new Path();
			pathtmp.moveTo(SerialData3[i].x, SerialData3[i].y);
			pathtmp.cubicTo(tmpYD[i * 2].x, tmpYD[i * 2].y, tmpYD[i * 2 + 1].x,
					tmpYD[i * 2 + 1].y, SerialData3[i + 1].x,
					SerialData3[i + 1].y);
			canvas.drawPath(pathtmp, p);

		}
		if(showGKD){
			for (int i = 0; i < SerialData3.length; i++) {
				canvas.drawCircle(SerialData3[i].x, SerialData3[i].y,3,p);
			}}

		p.setColor(XColor);
		canvas.drawText("Q(m³/s)", mpoint.x + CurveWidth + LabelOffSet,
				mpoint.y + CurveHeight - LabelOffSet, p);
		Xaxis[(XNum + 1) * 4] = mpoint.x;
		Xaxis[(XNum + 1) * 4 + 1] = mpoint.y + CurveHeight;
		Xaxis[(XNum + 1) * 4 + 2] = mpoint.x + CurveWidth;
		Xaxis[(XNum + 1) * 4 + 3] = mpoint.y + CurveHeight;
		canvas.drawLines(Xaxis, p);
		// 曲线数据转换

	}

	private Point[] getCtrlPoint(Point[] ps, float OffSetA, float OffSetB) {
		Point[] mres=new Point[1];

		try {
			mres= new Point[ps.length * 2 - 2];
			int PreIndex = 0;
			int AftIndex1 = 0;
			int AftIndex2 = 0;
			for (int i = 0; i < ps.length - 1; i++) {
                if(i>0)
                {
                    float tmpOffa=(float) (Math.sqrt(Math.pow(ps[i].x-ps[i-1].x,2)+
                            Math.pow(ps[i].y-ps[i-1].y,2))/100);
                    OffSetA=(float) Math.min(OffSetA,tmpOffa);
                    float tmpOffb=(float)	Math.sqrt(Math.pow(ps[i+1].x-ps[i].x,2)+
                            Math.pow(ps[i+1].y-ps[i].y,2))/100;
                    OffSetB=(float) Math.min(OffSetB,tmpOffb);


                }

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
		} catch (Exception e) {
			e.printStackTrace();
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

}
