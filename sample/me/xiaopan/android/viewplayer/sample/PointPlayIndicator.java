/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.xiaopan.android.viewplayer.sample;

import me.xiaopan.android.viewplayer.R;
import me.xiaopan.android.viewplayer.ViewPlayIndicator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PointPlayIndicator extends ViewPlayIndicator {
	private int lastCheckedPosition;//上次选中的图标的位置
	private LinearLayout pointsLayout;
	
	public PointPlayIndicator(Context context) {
		super(context);
	}
	
	public PointPlayIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onInit(int size) {
		setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		setGravity(Gravity.BOTTOM);
		
		//创建包括所有图标部分的布局
		pointsLayout = new LinearLayout(getContext());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		pointsLayout.setLayoutParams(layoutParams);
		pointsLayout.setGravity(Gravity.CENTER);
		for(int w = 0; w < size; w++){//然后初始化所有的图标并将其放进存放图标的布局中
			ImageView iconImage = new ImageView(getContext());
			iconImage.setImageResource(R.drawable.selector_radio_play_indicator); 
			LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			iconParams.setMargins(5, 5, 5, 5);//设置指示器内图标的外边距
			iconImage.setLayoutParams(iconParams);
			pointsLayout.addView(iconImage);
		}
		
		addView(pointsLayout);
	}

	@Override
	public void onItemSelected(int selectedItemPosition) {
		//先将上一个取消
		((ImageView) (pointsLayout.getChildAt(lastCheckedPosition))).setSelected(false);
		//再将当前的选中
		((ImageView) (pointsLayout.getChildAt(selectedItemPosition))).setSelected(true);
		//记录本次选中的
		lastCheckedPosition = selectedItemPosition;
	}
}
