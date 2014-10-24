package com.jov.vae.frame;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jov.vae.R;
import com.jov.vae.adapter.FunsListAdapter;
import com.jov.vae.bean.FunsBean;
import com.jov.vae.dao.DBOpenHelper;
import com.jov.vae.net.Common;
import com.jov.vae.views.PullDownView;

public class FunsFragment extends Fragment implements
		PullDownView.OnPullDownListener {
	private View view;
	private Context ctx;
	private PullDownView mPullDownView;
	private List<FunsBean> list = new ArrayList<FunsBean>();
	private FunsListAdapter mainListAdapter;
	private ListView mListView;
	private int wonPageNo = 1;
	private int wonTotalPage = 0;
	private static DBOpenHelper dao;
	private final String funsUrl = Common.HTTPURL + "funs_tie.xml";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view =  inflater.inflate(R.layout.fragment_funs, container, false);
		ctx = view.getContext();
		dao = new DBOpenHelper(ctx);
		FunsBean funBean = new FunsBean();
		funBean.setContent("test");
		list.add(funBean);
		initView();
		return view;
	}
	private void initView() {
		mPullDownView = (PullDownView) view.findViewById(R.id.funs_listview);
		mPullDownView.setOnPullDownListener(this);
		mPullDownView.setHideFooter();
		mPullDownView.setShowHeader();
		mainListAdapter = new FunsListAdapter(ctx, list,dao);
		mListView = mPullDownView.getListView();
		mListView.setAdapter(mainListAdapter);
		mPullDownView.enableAutoFetchMore(true, 2);
	}
	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		
	}
}
