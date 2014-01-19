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

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 视图播放适配器，主要为ViewPalyer提供播放视图
 */
public abstract class ViewPlayAdapter extends BaseAdapter{
	private List<?> list;
	private PlayWay playWay = PlayWay.CIRCLE_LEFT_TO_RIGHT;
	
	public ViewPlayAdapter (List<?> list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
		//当时循环播放的时候就返回一个int类型的最大值保证可以一直循环下去，否则就返回真实的长度
		return (list != null)?(((playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT  || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT) && list.size() > 1)?Integer.MAX_VALUE:list.size()):0;
	}
	
	@Override
	public Object getItem(int position) {
		return list != null?list.get(getRealSelectedItemPosition(position)):null;
	}

	@Override
	public long getItemId(int position) {
		return getRealSelectedItemPosition(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getRealView(getRealSelectedItemPosition(position), convertView, parent);
	}
	
	/**
	 * 获取视图
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getRealView(int position, View convertView, ViewGroup parent);
	
	/**
	 * 获取当前选中项的真实位置
	 * @param position
	 * @return 当前选中项的真实位置
	 */
	public int getRealSelectedItemPosition(int position){
		return (list != null)?((playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT)?position % list.size():position):0;
	}

	/**
	 * 获取列表
	 * @return 列表
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * 设置列表
	 * @param list 列表
	 */
	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * 获取播放方式
	 * @return 播放方式
	 */
	public PlayWay getPlayWay() {
		return playWay;
	}

	/**
	 * 设置播放方式
	 * @param playWay 播放方式
	 */
	public void setPlayWay(PlayWay playWay) {
		this.playWay = playWay;
	}
}
