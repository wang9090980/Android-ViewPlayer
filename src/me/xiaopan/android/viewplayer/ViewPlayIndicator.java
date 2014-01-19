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

package me.xiaopan.android.viewplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 视图播放指示器
 */
public abstract class ViewPlayIndicator extends LinearLayout{
	public ViewPlayIndicator(Context context) {
		super(context);
	}
	
	public ViewPlayIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 初始化
	 * @param size 视图播放器要播放的视图个数
	 */
	public abstract void onInit(int size);

	/**
	 * 当视图播放器的选项被选中时，指示器要同步更改其选中项
	 * @param selectedItemPosition 选中项的位置
	 */
	public abstract void onItemSelected(int selectedItemPosition);
}
