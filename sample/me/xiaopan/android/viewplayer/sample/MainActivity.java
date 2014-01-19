package me.xiaopan.android.viewplayer.sample;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.viewplayer.PlayWay;
import me.xiaopan.android.viewplayer.R;
import me.xiaopan.easy.imageloader.ImageLoader;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private PointViewPlayer picturePlayerCircleLeftToRight;
	private PointViewPlayer picturePlayerCircleRightToLeft;
	private PointViewPlayer picturePlayerSwingLeftToRight;
	private PointViewPlayer picturePlayerSwingRightToLeft;
	private List<String> pictures;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		picturePlayerCircleLeftToRight = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_leftToRight);
		picturePlayerCircleRightToLeft = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_rightToLeft);
		picturePlayerSwingLeftToRight = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_leftToRight);
		picturePlayerSwingRightToLeft = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_rightToLeft);
		picturePlayerCircleLeftToRight.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getBaseContext(), ""+position, Toast.LENGTH_SHORT).show();
			}
		});
		pictures = new ArrayList<String>();
		for(String url : getResources().getStringArray(R.array.autoPlayGallery_urls2)){
			pictures.add(url);
		}
		
		ImageLoader.getInstance().init(getBaseContext());
		picturePlayerCircleLeftToRight.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerCircleLeftToRight.setPlayWay(PlayWay.CIRCLE_LEFT_TO_RIGHT);
		
		picturePlayerCircleRightToLeft.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerCircleRightToLeft.setPlayWay(PlayWay.CIRCLE_RIGHT_TO_LEFT);
		
		picturePlayerSwingLeftToRight.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerSwingLeftToRight.setPlayWay(PlayWay.SWING_LEFT_TO_RIGHT);
		
		picturePlayerSwingRightToLeft.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerSwingRightToLeft.setPlayWay(PlayWay.SWING_RIGHT_TO_LEFT);
	}

	@Override
	public void onResume() {
		picturePlayerCircleLeftToRight.startPaly();
		picturePlayerCircleRightToLeft.startPaly();
		picturePlayerSwingLeftToRight.startPaly();
		picturePlayerSwingRightToLeft.startPaly();
		super.onPause();
	}

	@Override
	public void onPause() {
		picturePlayerCircleLeftToRight.stopPaly();
		picturePlayerCircleRightToLeft.stopPaly();
		picturePlayerSwingLeftToRight.stopPaly();
		picturePlayerSwingRightToLeft.stopPaly();
		super.onPause();
	}
}
