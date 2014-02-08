package me.xiaopan.android.viewplayer;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ViewPagePlayer extends ViewPager {
	private PlayController playController;	//播放控制器

	public ViewPagePlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewPagePlayer(Context context) {
		super(context);
	}
	
	@Override
	public void setAdapter(PagerAdapter arg0) {
		super.setAdapter(arg0);
		if(arg0 != null && arg0.getCount() > 0){
			removeAllViews();
			if(playController == null){
				playController = new PlayController(this);
			}else{
				playController.reset();
			}
			
			//初始化默认选中项
			int defaultPosition = 0;
			if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT){//如果播放方式是从左向右转圈
				defaultPosition = viewPlayAdapter.getList().size() > 1?((Integer.MAX_VALUE/viewPlayAdapter.getList().size())/2)*viewPlayAdapter.getList().size():0;//那么默认选中项是最中间那一组的第一张
			}else if(playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){//如果播放方式是从右向左转圈
				defaultPosition = viewPlayAdapter.getList().size() > 1?((Integer.MAX_VALUE/viewPlayAdapter.getList().size())/2)*viewPlayAdapter.getList().size() + viewPlayAdapter.getList().size() -1:0;//那么默认选中项是最中间那一组的最后一张
			}else if(playWay == PlayWay.SWING_LEFT_TO_RIGHT){//如果播放方式是从左向右摇摆
				defaultPosition = 0;//那么默认选中项是第一组的第一张
				currentTowardsTheRight = true;//播放方向将是向右
			}else if(playWay == PlayWay.SWING_RIGHT_TO_LEFT){//如果播放方式是从右向左摇摆
				defaultPosition = viewPlayAdapter.getList().size() -1;//那么默认选中项是第一组的最后一张
				currentTowardsTheRight = false;//播放方向将是向左
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

	/**
	 * 启动
	 */
	public void start(){
		if(playController != null){
			playController.start();
		}
	}
}
