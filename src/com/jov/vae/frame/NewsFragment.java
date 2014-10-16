package com.jov.vae.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jov.vae.R;
import com.jov.vae.adapter.TextListAdapter;
import com.jov.vae.bean.TextBean;
import com.jov.vae.net.Common;
import com.jov.vae.net.FileUtiles;
import com.jov.vae.net.HttpGetAndWriteToFileThread;
import com.jov.vae.net.ThreadPoolUtils;
import com.jov.vae.views.PullDownView;

public class NewsFragment extends Fragment implements
PullDownView.OnPullDownListener  {
	private View view;
	private List<TextBean> list = new ArrayList<TextBean>();
	private Context ctx;
	private PullDownView mPullDownView;
	private int wonPageNo = 1;
	private int wonTotalPage = 0;
	private TextListAdapter mainListAdapter;
	private ListView mListView;
	private TextBean textBean;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_news, container, false);
		ctx = view.getContext();
		textBean = new TextBean();
		list.add(textBean);
		initView();
		return view;
	}
	private void initView() {
		mPullDownView = (PullDownView) view.findViewById(R.id.main_listview);
		mPullDownView.setOnPullDownListener(this);
		mPullDownView.setHideFooter();
		mPullDownView.setShowHeader();
		mainListAdapter = new TextListAdapter(ctx, list);
		mListView = mPullDownView.getListView();
		mListView.setAdapter(mainListAdapter);
		mPullDownView.enableAutoFetchMore(true, 2);
	}
	@Override
	public void onRefresh() {
		
	}
	@Override
	public void onMore() {
		
	}
	private void loadImage() {
		if (!Common.isEmpty(bean.getImagUrl())) {
			File imageFile = new File(
					FileUtiles.getImagePath(bean.getImagUrl()));
			if (!imageFile.exists()) {
				ThreadPoolUtils.execute(new HttpGetAndWriteToFileThread(
						resHand, bean.getImagUrl()));
			} else {
				Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}else{
			imageView.setVisibility(View.GONE);
		}
	}

	private Handler resHand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 404) {
			} else if (msg.what == 100) {
			} else if (msg.what == 200) {
				boolean result = (boolean) msg.obj;
				if (result) {
					File imageFile = new File(FileUtiles.getImagePath(bean
							.getImagUrl()));
					if (imageFile.exists()) {
						Bitmap bitmap = BitmapFactory.decodeFile(imageFile
								.getPath());
						if (bitmap != null) {
							imageView.setImageBitmap(bitmap);
						}
					}
				}
			}
		};
	};
}
