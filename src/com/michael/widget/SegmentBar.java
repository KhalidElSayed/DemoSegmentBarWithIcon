package com.michael.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.michael.widget.segmentbarwithicon.R;

/**
 * ��Сͼ��ķֶοؼ�
 * 
 * @author MichaelYe
 * @since 2012-8-21
 * 
 * */
public class SegmentBar extends LinearLayout implements OnClickListener
{
	private String[] stringArray;
	private Context mContext;
	
	public SegmentBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
	}

	public SegmentBar(Context context) 
	{
		super(context);
		mContext = context;
	}
	
	/**
	 * determine the number of segment bar items by the string array.
	 * 
	 * ���ݴ��ݽ��������飬�����ֶε�����
	 * 
	 * 
	 * */
	public void setValue(Context context, String[] stringArray)
	{
		this.stringArray = stringArray;
		if(stringArray.length <= 1)
		{
			throw new RuntimeException("the length of String array must bigger than 1");
		}
		this.stringArray = stringArray;
		resolveStringArray(context);
	}
	
	/**
	 * resolve the array and generate the items.
	 * 
	 * ��������н��������ɾ����ÿ���ֶ�
	 * 
	 * */
	private void resolveStringArray(Context context)
	{
		int length = this.stringArray.length;
		for(int i = 0; i < length; i++)
		{
			Button button = new Button(context);
			button.setText(stringArray[i]);
			button.setGravity(Gravity.CENTER);
//			button.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.icon_arraw_bottom), null, null, null);
			button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
			button.setTag(i);
			button.setOnClickListener(this);
			
			if(i == 0)//��ߵİ�ť
			{
				button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.tab_bar_selector));
				//button.setBackgroundResource(R.drawable.left_button_bg_selector);
			}
			else if(i != 0 && i < (length-1))//�ұߵİ�ť
			{
				button.setBackgroundResource(R.drawable.tab_bar_selector);
			}
			else//�м�İ�ť
			{
				button.setBackgroundResource(R.drawable.tab_bar_selector);
			}
			
			this.addView(button);
		}
	}

	private int lastIndex = 0;//��¼��һ�ε��������
	public void onClick(View v)
	{
		int index = (Integer)v.getTag();
		onSegmentBarChangedListener.onBarItemChanged(index);
		
		Button lastButton = (Button)this.getChildAt(lastIndex);
		lastButton.setSelected(false);
		lastButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);//android:drawableTop
		
		Button currButton = (Button)this.getChildAt(index);
		currButton.setSelected(true);
		currButton.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_arraw_bottom), null, null, null);
		
		lastIndex = index;
	}
	
	/**
	 * set the default bar item of selected
	 * 
	 * ����Ĭ��ѡ�е�SegmentBar
	 * 
	 * */
	public void setDefaultBarItem(int index)
	{
		if(index > stringArray.length)
		{
			throw new RuntimeException("the value of default bar item can not bigger than string array's length");
		}
		lastIndex = index;
		Button btn = (Button)this.getChildAt(index);
		btn.setSelected(true);
		btn.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_arraw_bottom), null, null, null);
		if(onSegmentBarChangedListener != null)
		{
			onSegmentBarChangedListener.onBarItemChanged(index);
		}
	}
	
	/**
	 * set the text size of every item
	 * 
	 * �������ֵĴ�С
	 * 
	 * */
	public void setTextSize(float sizeValue)
	{
		int index = this.getChildCount();
		for(int i = 0; i < index; i++)
		{
			((Button)this.getChildAt(i)).setTextSize(sizeValue);
		}
	}
	
	/**
	 * set the text color of every item
	 * 
	 * �������ֵ���ɫ
	 * 
	 * */
	public void setTextColor(int color)
	{
		int index = this.getChildCount();
		for(int i = 0; i < index; i++)
		{
			((Button)this.getChildAt(i)).setTextColor(color);
		}
	}
	
	private OnSegmentBarChangedListener onSegmentBarChangedListener;
	
	/**
	 * define a interface used for listen the change event of Segment bar
	 * 
	 * ����һ���ӿڣ�����ʵ�ֶַοؼ�Item�ļ���
	 * 
	 * */
	public interface OnSegmentBarChangedListener
	{
		public void onBarItemChanged(int segmentItemIndex);
	}
	
	/**
	 * set the segment bar item changed listener,if the bar item changed, 
	 * the method onBarItemChanged()will be called.
	 * 
	 * ���÷ֶοؼ��ļ����������ֶθı��ʱ��onBarItemChanged()�ᱻ����
	 * 
	 * */
	public void setOnSegmentBarChangedListener(OnSegmentBarChangedListener onSegmentBarChangedListener)
	{
		this.onSegmentBarChangedListener = onSegmentBarChangedListener;
	}
}