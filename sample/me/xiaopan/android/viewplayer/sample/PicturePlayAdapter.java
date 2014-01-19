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

import java.util.List;

import me.xiaopan.android.viewplayer.ViewPlayAdapter;
import me.xiaopan.easy.imageloader.ImageLoader;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 图片播放适配器，给我一组图片再给我，再把我交给一个ViewPlayer，我就可以在ViewPlayer上播放你给我的图片
 */
@SuppressWarnings("deprecation")
public class PicturePlayAdapter extends ViewPlayAdapter{
	private Context context;//上下文
	private List<String> pictures;//图片列表
	private int defaultImageResId;//默认图片ID
	private ScaleType imageScaleType = ScaleType.CENTER_CROP;//图片缩放模式
	
	public PicturePlayAdapter(Context context, List<String> pictures){
		super(pictures);
		this.context = context;
		this.pictures = pictures;
	}
	
	@Override
	public View getRealView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
			imageView.setScaleType(imageScaleType);
			viewHolder.imageView = imageView;
			convertView = imageView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ImageLoader.getInstance().display(pictures.get(position), viewHolder.imageView);
		return convertView;
	}
	
	private class ViewHolder{
		public ImageView imageView;
	}
	
	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public int getDefaultImageResId() {
		return defaultImageResId;
	}

	public void setDefaultImageResId(int defaultImageResId) {
		this.defaultImageResId = defaultImageResId;
	}

	public ScaleType getImageScaleType() {
		return imageScaleType;
	}

	public void setImageScaleType(ScaleType imageScaleType) {
		this.imageScaleType = imageScaleType;
	}
}
