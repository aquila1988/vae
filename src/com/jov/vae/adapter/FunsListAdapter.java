package com.jov.vae.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jov.vae.ImageDetailActivity;
import com.jov.vae.R;
import com.jov.vae.bean.FunsBean;
import com.jov.vae.bean.TextBean;
import com.jov.vae.dao.DBOpenHelper;
import com.jov.vae.net.Common;
import com.jov.vae.net.LoadImg;
import com.jov.vae.net.LoadImg.ImageDownloadCallBack;

public class FunsListAdapter extends BaseAdapter {

	private List<FunsBean> list;
	private Context ctx;
	private DBOpenHelper dbOpenHelper;
	private LoadImg loadImgMainImg;

	public FunsListAdapter(Context ctx, List<FunsBean> list, DBOpenHelper db) {
		this.list = list;
		this.ctx = ctx;
		this.dbOpenHelper = db;
		loadImgMainImg = new LoadImg(ctx);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		final Holder hold;
		if (arg1 == null) {
			hold = new Holder();
			arg1 = View.inflate(ctx, R.layout.good_listview_item, null);
			hold.MainText = (TextView) arg1.findViewById(R.id.good_subj);
			hold.itemIdText = (TextView) arg1.findViewById(R.id.good_tip);
			//hold.fav = (LinearLayout) arg1.findViewById(R.id.item_fav);
			//hold.more = (LinearLayout) arg1.findViewById(R.id.item_more);
			//hold.image = (ImageView) arg1.findViewById(R.id.Item_MainImg);
			arg1.setTag(hold);
		} else {
			hold = (Holder) arg1.getTag();
		}
		//final int tid = list.get(position).getTid();
		if (Common.isLongStr(list.get(position).getContent(), 500)) {
			hold.MainText.setText(Common.cutLongStr(list.get(position)
					.getContent(), 500));
			// hold.more.setVisibility(View.VISIBLE);
		} else {
			hold.MainText.setText(list.get(position).getContent());
			// hold.more.setVisibility(View.GONE);
		}
		/*if (!Common.isEmpty(list.get(position).getImgurl())) {
			hold.image.setTag(list.get(position).getImgurl());
			Bitmap bitHead = loadImgMainImg.loadImage(hold.image,
					list.get(position).getImgurl(),
					new ImageDownloadCallBack() {
						@Override
						public void onImageDownload(ImageView imageView,
								Bitmap bitmap) {
							if (hold.image.getTag().equals(
									list.get(position).getImgurl())) {
								hold.image.setImageBitmap(bitmap);
							}
						}
					});
			if (bitHead != null) {
				hold.image.setImageBitmap(bitHead);
			}
		}*/
		hold.itemIdText.setText(list.get(position).getContent());
		/*hold.fav.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// dbOpenHelper.insertFavoriteWithTid(tid);
				Toast.makeText(ctx,
						ctx.getResources().getString(R.string.dofav_str),
						Toast.LENGTH_SHORT).show();
			}
		});

		hold.more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				 * Intent intent = new Intent(ctx, TextDetailActivity.class);
				 * intent.putExtra("text.tid", tid);
				 * intent.putExtra("flag.type", "text");
				 * ctx.startActivity(intent);
				 
			}
		});
		hold.image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ctx, ImageDetailActivity.class);
				intent.putExtra("imgurl", list.get(position).getImgurl());
				ctx.startActivity(intent);
			}
		});*/
		return arg1;
	}

	static class Holder {
		TextView MainText;
		LinearLayout more;
		TextView itemIdText;
		LinearLayout fav;
		ImageView image;
	}
}
