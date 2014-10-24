package com.jov.vae;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jov.vae.net.LoadImg;
import com.jov.vae.net.LoadImg.ImageDownloadCallBack;


public class ImageDetailActivity extends Activity {
	private ImageDetailActivity activity;
	private LoadImg loadimg;
	private ImageView img ;
	private Bundle bundle;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_detail);
		activity = this;
		loadimg = new LoadImg(ImageDetailActivity.this);
		loadAddUI();
		img = (ImageView) this.findViewById(R.id.image_large);
		Bitmap map = null;
		bundle = this.getIntent().getExtras();
		if (bundle != null) {
			img.setTag( bundle.getString("imgurl"));
			map = loadimg.loadImage(img, bundle.getString("imgurl"), new ImageDownloadCallBack() {
				@Override
				public void onImageDownload(ImageView imageView,
						Bitmap bitmap) {
					if (img.getTag().equals(
							bundle.getString("imgurl"))) {
						img.setImageBitmap(bitmap);
					}
				}
			});
			if (map != null) {
				img.setImageBitmap(map);
			}
		} else {
			activity.finish();
		}
		img.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				activity.finish();
			}
		});
	}

	private void loadAddUI() {
		// AppConnect.getInstance(context).showBannerAd(context,view);
		// String value =
		// AdManager.getInstance(context).syncGetOnlineConfig("ad_a", "false");
		LinearLayout view = (LinearLayout) findViewById(R.id.ad_bar_item);
		/*AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		view.addView(adView);*/
	}
}
