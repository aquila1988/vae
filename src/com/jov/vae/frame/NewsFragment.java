package com.jov.vae.frame;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import com.jov.vae.dao.DBOpenHelper;
import com.jov.vae.net.Common;
import com.jov.vae.net.HttpGetAndInsertThread;
import com.jov.vae.net.ThreadPoolUtils;
import com.jov.vae.views.PullDownView;

public class NewsFragment extends Fragment implements
		PullDownView.OnPullDownListener {
	private View view;
	private List<TextBean> list = new ArrayList<TextBean>();
	private Context ctx;
	private PullDownView mPullDownView;
	private int wonPageNo = 1;
	private int wonTotalPage = 0;
	private TextListAdapter mainListAdapter;
	private ListView mListView;
	private static boolean isDoingUpdate = false;
	private static DBOpenHelper dao;
	private final String textNewUrl = Common.HTTPURL + "text_news.xml";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_news, container, false);
		ctx = view.getContext();
		dao = new DBOpenHelper(ctx);
		calculatePageNo();
		initView();
		return view;
	}

	private void calculatePageNo() {
		int won = dao.getTextTotalCount();
		wonTotalPage = won % 10 == 0 ? won / 10 : (won / 10 + 1);
		list.clear();
		list.addAll(0, dao.getTexts(0));
	}

	private void initView() {
		mPullDownView = (PullDownView) view.findViewById(R.id.main_listview);
		mPullDownView.setOnPullDownListener(this);
		mPullDownView.setHideFooter();
		mPullDownView.setShowHeader();
		mainListAdapter = new TextListAdapter(ctx, list,dao);
		mListView = mPullDownView.getListView();
		mListView.setAdapter(mainListAdapter);
		mPullDownView.enableAutoFetchMore(true, 2);
		ThreadPoolUtils.execute(new HttpGetAndInsertThread(contentHand, textNewUrl,dao));
		isDoingUpdate = true;
	}

	private Handler contentHand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 404) {
				isDoingUpdate = false;
				mPullDownView.RefreshComplete();
			} else if (msg.what == 100) {
				isDoingUpdate = false;
				mPullDownView.RefreshComplete();
			} else if (msg.what == 200) {
				boolean result = (boolean) msg.obj;
				if (result) {
					calculatePageNo();
					mainListAdapter.notifyDataSetChanged();
					isDoingUpdate = false;
					mPullDownView.RefreshComplete();
					wonPageNo = 1;
				}
				isDoingUpdate = false;
				mPullDownView.RefreshComplete();
			}
		};
	};

	@Override
	public void onRefresh() {
		if (!isDoingUpdate) {
			ThreadPoolUtils.execute(new HttpGetAndInsertThread(contentHand, textNewUrl,dao));
		}
	}

	@Override
	public void onMore() {
		if (wonTotalPage != 0 && wonPageNo != wonTotalPage) {
			wonPageNo++;
			wonPageNo = wonPageNo > wonTotalPage ? wonTotalPage : wonPageNo;
			list.addAll(dao.getTexts((wonPageNo - 1) * 10));
			mainListAdapter.notifyDataSetChanged();
			mPullDownView.RefreshComplete();
		}
		mPullDownView.notifyDidMore();
	}
}
