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
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.Gallery;

/**
 * 视图播放器，用于循环播放视图，至于播放什么，你可以提供一个PlayAdapter来提供播放的内容
 */
@SuppressWarnings("deprecation")
public class ViewPlayer extends FrameLayout{
	public static final int GALLERY_ID = 214789677;
	private int switchSpace = 4000;//切换间隔
	private int animationDurationMillis = 600;//动画持续时间
	private ViewPlayAdapter viewPlayAdapter;//为画廊提供视图的适配器
	private ViewPlayIndicator viewPlayIndicator;//播放指示器
	private OnItemClickListener onItemClickListener;//项点击监听器
	private OnItemSelectedListener onItemSelectedListener;//项选中监听器
	private PlayWay playWay = PlayWay.CIRCLE_LEFT_TO_RIGHT;//播放方式，默认是从左往右转圈
	
	private ViewGallery viewGallery;//画廊
	private boolean loadFinish;//加载成功
	private boolean currentTowardsTheRight;//当前向右播放
	private Handler switchHandler;//切换处理器
	private SwitchHandle switchHandle;//切换处理
	
	public ViewPlayer(Context context) {
		super(context);
	}
	
	public ViewPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 开始播放
	 */
	public void startPaly(){
		//如果之前加载失败了
		if(!loadFinish){
			if(viewPlayAdapter != null && viewPlayAdapter.getList() != null && viewPlayAdapter.getList().size() > 0){
				loadFinish = true;
				removeAllViews();
				
				//初始化自动切换处理器
				switchHandler = new Handler();
				switchHandle = new SwitchHandle();
				
				//初始化画廊
				viewGallery = new ViewGallery(getContext());
				viewGallery.setId(GALLERY_ID);
				viewGallery.setAnimationDuration(animationDurationMillis);//设置动画持续时间，默认是600毫秒
				viewGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						if(onItemSelectedListener != null){		//回调
							onItemSelectedListener.onNothingSelected(parent);
						}
					}
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						int realSelectedItemPosition = viewPlayAdapter.getRealSelectedItemPosition(position);		//获取真实的位置，
						if(viewPlayIndicator != null){
							viewPlayIndicator.onItemSelected(realSelectedItemPosition);		//修改指示器的选中项
						}
						if(onItemSelectedListener != null){		//回调
							onItemSelectedListener.onItemSelected(parent, view, realSelectedItemPosition, id);
						}
					}
				});
				viewGallery.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if(onItemClickListener != null){		//回调
							onItemClickListener.onItemClick(parent, view, viewPlayAdapter.getRealSelectedItemPosition(position), id);
						}
					}
				});
				viewGallery.setAdapter(viewPlayAdapter);
				
				//初始化默认选中项
				int defaultPosition = 0;
				if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT){//如果播放方式是从左向右转圈
					defaultPosition = viewPlayAdapter.getList().size() > 1?((Integer.MAX_VALUE/viewPlayAdapter.getList().size())/2)*viewPlayAdapter.getList().size():0;//那么默认选中项是最中间那一组的第一张
				}else if(playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){//如果播放方式是从右向左转圈
					defaultPosition = viewPlayAdapter.getList().size() > 1?((Integer.MAX_VALUE/viewPlayAdapter.getList().size())/2)*viewPlayAdapter.getList().size() + viewPlayAdapter.getList().size() -1:0;//那么默认选中项是最中间那一组的最后一张
				}else if(playWay == PlayWay.SWING){//如果播放方式是从左向右摇摆
					defaultPosition = 0;//那么默认选中项是第一组的第一张
					currentTowardsTheRight = true;//播放方向将是向右
				}
				viewGallery.setSelection(defaultPosition);
				
				//将画廊和指示器放进布局中
				addView(viewGallery);
				if(viewPlayIndicator != null){
					viewPlayIndicator.onInit(viewPlayAdapter.getList().size());
					addView(viewPlayIndicator);
				}
			}else{
				loadFinish = false;
			}
		}
		
		//如果加载成功了
		if(loadFinish){
			switchHandler.removeCallbacks(switchHandle);
			switchHandler.postDelayed(switchHandle, switchSpace);
		}
	}
	
	/**
	 * 停止播放
	 */
	public void stopPaly(){
		//如果加载成功了
		if(loadFinish){
			switchHandler.removeCallbacks(switchHandle);
		}
	}
	
	/**
	 * 获取切换间隔
	 * @return 切换间隔
	 */
	public int getSwitchSpace() {
		return switchSpace;
	}

	/**
	 * 设置切换间隔
	 * @param switchSpace 切换间隔
	 */
	public void setSwitchSpace(int switchSpace) {
		this.switchSpace = switchSpace;
	}

	/**
	 * 获取项选择监听器
	 * @return 项选择监听器
	 */
	public OnItemSelectedListener getOnItemSelectedListener() {
		return onItemSelectedListener;
	}

	/**
	 * 设置项选择监听器
	 * @param onItemSelectedListener 项选择监听器
	 */
	public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}

	/**
	 * 获取项点击监听器
	 * @return 项点击监听器
	 */
	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	/**
	 * 设置项点击监听器
	 * @param onItemClickListener 项点击监听器
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	/**
	 * 获取播放方式
	 * @return 播放方式，默认：PlayWay.CIRCLE_RIGHT_TO_LEFT
	 */
	public PlayWay getPlayWay() {
		return playWay;
	}

	/**
	 * 设置播放方式
	 * @param playWay 播放方式，默认：PlayWay.CIRCLE_RIGHT_TO_LEFT
	 */
	public void setPlayWay(PlayWay playWay) {
		this.playWay = playWay;
		if(viewPlayAdapter != null){
			viewPlayAdapter.setPlayWay(playWay);
		}
	}

	/**
	 * 获取动画持续时间
	 * @return 动画持续时间
	 */
	public int getAnimationDurationMillis() {
		return animationDurationMillis;
	}

	/**
	 * 设置动画持续时间
	 * @param animationDurationMillis 动画持续时间
	 */
	public void setAnimationDurationMillis(int animationDurationMillis) {
		this.animationDurationMillis = animationDurationMillis;
	}

	/**
	 * 获取视图播放适配器
	 * @return 视图播放适配器
	 */
	public ViewPlayAdapter getViewPlayAdapter() {
		return viewPlayAdapter;
	}

	/**
	 * 设置视图播放适配器
	 * @param viewPlayAdapter 视图播放适配器
	 */
	public void setViewPlayAdapter(ViewPlayAdapter viewPlayAdapter) {
		this.viewPlayAdapter = viewPlayAdapter;
		if(this.viewPlayAdapter != null){
			this.viewPlayAdapter.setPlayWay(playWay);
		}
	}

	/**
	 * 获取视图播放指示器
	 * @return 视图播放指示器
	 */
	public ViewPlayIndicator getViewPlayIndicator() {
		return viewPlayIndicator;
	}

	/**
	 * 设置视图播放指示器
	 * @param viewPlayIndicator 视图播放指示器
	 */
	public void setViewPlayIndicator(ViewPlayIndicator viewPlayIndicator) {
		this.viewPlayIndicator = viewPlayIndicator;
	}

	/**
	 * 默认的画廊
	 * @author xiaopan
	 */
	public class ViewGallery extends Gallery {
		public ViewGallery(Context context) {
			super(context);
			setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
			setSoundEffectsEnabled(false);//切换的时候不播放音效
			setSpacing(-1);//设置间距为-1，因为使用按键自动切换的时候间距大于等于0会导致切换失败，所以间距只能是-1
			setFadingEdgeLength(0);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e2.getX() > e1.getX()) {
				onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else {
				onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}
			return false;
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if(loadFinish){
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: stopPaly(); break;
					case MotionEvent.ACTION_UP: startPaly(); break;
					case MotionEvent.ACTION_CANCEL: startPaly(); break;
					default: break;
				}
			}
			return super.onTouchEvent(event);
		}
	}
	
	/**
	 * 默认的画廊适配器
	 */
	/**
	 * 切换处理
	 */
	private class SwitchHandle implements Runnable{
		@Override
		public void run() {
			if(viewGallery.getCount() > 1){
				//从左向右转圈
				if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT){
					viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}else if(playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){
					viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
				}else if(playWay == PlayWay.SWING){
					//如果当前是向右播放
					if(currentTowardsTheRight){
						//如果到最后一个了
						if(viewGallery.getSelectedItemPosition() == viewPlayAdapter.getList().size() -1){
							currentTowardsTheRight = false;//标记为向左
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
						}else{
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
						}
					}else{
						//如果到第一个了
						if(viewGallery.getSelectedItemPosition() == 0){
							currentTowardsTheRight = true;
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
						}else{
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
						}
					}
				}
			}
			switchHandler.postDelayed(switchHandle, switchSpace);
		}
	}
	
	public ViewGallery getViewGallery() {
		return viewGallery;
	}

	public void setViewGallery(ViewGallery viewGallery) {
		this.viewGallery = viewGallery;
	}
}
