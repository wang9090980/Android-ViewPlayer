package me.xiaopan.android.viewplayer;

import android.support.v4.view.ViewPager;

public class PlayController implements Runnable{
	private int switchSpace = 4000;//切换间隔
	private boolean currentTowardsTheRight = true;//当前向右播放
	private PlayWay playWay = PlayWay.CIRCLE_LEFT_TO_RIGHT;//播放方式，默认是从左往右转圈
	private ViewPager viewPager;
	
	public PlayController(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	@Override
	public void run() {
		if(viewPager.getAdapter().getCount() > 1){
			int nextItem = 0;
			switch(playWay){
				case CIRCLE_LEFT_TO_RIGHT : 
					nextItem = (viewPager.getCurrentItem()+1) % viewPager.getAdapter().getCount();
					break;
				case CIRCLE_RIGHT_TO_LEFT :
					nextItem = viewPager.getCurrentItem() - 1 < 0?viewPager.getAdapter().getCount() - 1:viewPager.getCurrentItem() - 1;
					break;
				case SWING : 
					//如果当前是向右播放
					if(currentTowardsTheRight){
						if(viewPager.getCurrentItem() == viewPager.getAdapter().getCount() -1){//如果到最后一个了
							currentTowardsTheRight = false;//标记为向左
							nextItem = viewPager.getCurrentItem() - 1;
						}else{
							nextItem = viewPager.getCurrentItem() + 1;
						}
					}else{
						if(viewPager.getCurrentItem() == 0){//如果到第一个了
							currentTowardsTheRight = true;//标记为向右
							nextItem = viewPager.getCurrentItem() + 1;
						}else{
							nextItem = viewPager.getCurrentItem() - 1;
						}
					}
					break;
			}
			viewPager.setCurrentItem(nextItem, true);
		}
		viewPager.postDelayed(this, switchSpace);
	}
	
	public void start(){
		viewPager.removeCallbacks(this);
		viewPager.postDelayed(this, switchSpace);
	}
	
	public void reset(){
		if(viewPager.getAdapter() != null && viewPager.getAdapter().getCount() > 0){
			viewPager.setCurrentItem(0, true);
		}
	}

	public PlayWay getPlayWay() {
		return playWay;
	}

	public void setPlayWay(PlayWay playWay) {
		this.playWay = playWay;
	}
}