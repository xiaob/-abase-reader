package com.jayqqaa12.reader.ui.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


public class BatteryView  
{
	private float level = 50;
	private Paint linePaint = new Paint();
	private Paint batteyPaint = new Paint();
	
	public void setPowser(int level){
		
		this.level=level;
	}

	public BatteryView()
	{
		linePaint.setColor(Color.BLACK);
		linePaint.setStrokeWidth(1);
		batteyPaint.setColor(Color.BLACK);
		batteyPaint.setStrokeWidth(2);
		batteyPaint.setTextSize(2);
	}
	private void drawLinesExt(Canvas canvas, float[] pts, Paint paint)
	{

		float[] points = new float[pts.length * 2 - 4];
		for (int i = 0, j = 0; i < pts.length; i = i + 2)
		{
			points[j++] = pts[i];
			points[j++] = pts[i + 1];

			if (i > 1 && i < pts.length - 2)
			{
				points[j++] = pts[i];
				points[j++] = pts[i + 1];
			}

		}

		canvas.drawLines(points, paint);
	}

	public void draw(Canvas canvas,float left,float top,float width)
	{
		float high = width/2.5f;
		
		
		float right = left + width;
		float bottom = top + high;
		float padding = 2;
		
		canvas.save();
		drawLinesExt(canvas, new float[] { left - padding, top - padding, right + padding, top - padding,
				right + padding, top + (high-padding-1) / 3, // battery
				right + padding * 2, top + (high -padding-1) /3,
				right + padding * 2, top + (high +padding) /3 * 2,
				right + padding, top + (high + padding) / 3 * 2,
				right + padding, bottom + padding, left - padding, bottom + padding, left - padding,
				top - padding }, linePaint);

		drawLinesExt(canvas, new float[] { left, top, right, top, right, bottom, left, bottom, left, top }, linePaint);
		float leave = (width * level / 100) + left;
		RectF rectF = new RectF(left, top, leave, bottom);
		canvas.drawRect(rectF, batteyPaint);
		canvas.restore();
	}

}
