package com.jov.vae;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	public static final String PAGE1_ID = "page1";
	public static final String PAGE2_ID = "page2";
	public static final String PAGE3_ID = "page3";
	public static final String PAGE4_ID = "page4";

	private TabHost tabHost; // TabHost
	private List<View> views; // ViewPager内的View对象集合
	private FragmentManager manager; // Activity管理器
	private ViewPager pager; // ViewPager

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 初始化资源
		pager = (ViewPager) findViewById(R.id.viewpager);
		tabHost = (TabHost) findViewById(R.id.tab_host);
		manager = getSupportFragmentManager();
		views = new ArrayList<View>();

		views.add(manager.findFragmentById(R.id.fragment_news).getView());
		views.add(manager.findFragmentById(R.id.fragment_funs).getView());
		views.add(manager.findFragmentById(R.id.fragment_goods).getView());
		views.add(manager.findFragmentById(R.id.fragment_musics).getView());

		// 管理tabHost开始
		tabHost.setup();

		// 传一个空的内容给TabHost，不能用上面两个fragment
		TabContentFactory factory = new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return new View(MainActivity.this);
			}
		};
		// tab1
		TabSpec tabSpec = tabHost.newTabSpec(PAGE1_ID);
		tabSpec.setIndicator(createTabView(R.string.fragment_news));
		tabSpec.setContent(factory);
		tabHost.addTab(tabSpec);
		// tab2
		TabSpec tabSpec2 = tabHost.newTabSpec(PAGE2_ID);
		tabSpec2.setIndicator(createTabView(R.string.fragment_funs));
		tabSpec2.setContent(factory);
		tabHost.addTab(tabSpec2);
		// tab3
		TabSpec tabSpec3 = tabHost.newTabSpec(PAGE3_ID);
		tabSpec3.setIndicator(createTabView(R.string.fragment_goods));
		tabSpec3.setContent(factory);
		tabHost.addTab(tabSpec3);
		// tab4
		TabSpec tabSpec4 = tabHost.newTabSpec(PAGE4_ID);
		tabSpec4.setIndicator(createTabView(R.string.fragment_musics));
		tabSpec4.setContent(factory);
		tabHost.addTab(tabSpec4);
		tabHost.setCurrentTab(0);
		// 管理tabHost结束

		// 设置监听器和适配器
		pager.setAdapter(new PageAdapter());
		pager.setOnPageChangeListener(new PageChangeListener());
		tabHost.setOnTabChangedListener(new TabChangeListener());
	}

	/**
	 * PageView Adapter
	 * 
	 * @author Administrator
	 * 
	 */
	private class PageAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object arg2) {
			view.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			try {
				if (views.get(position).getParent() == null) {
					view.addView(views.get(position));
				} else {
					((ViewGroup) views.get(position).getParent())
							.removeView(views.get(position));
					view.addView(views.get(position));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return views.get(position);
		}
	}

	/**
	 * 标签页点击切换监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private class TabChangeListener implements OnTabChangeListener {
		@Override
		public void onTabChanged(String tabId) {
			if (PAGE1_ID.equals(tabId)) {
				pager.setCurrentItem(0);
			} else if (PAGE2_ID.equals(tabId)) {
				pager.setCurrentItem(1);
			} else if (PAGE3_ID.equals(tabId)) {
				pager.setCurrentItem(2);
			} else if (PAGE4_ID.equals(tabId)) {
				pager.setCurrentItem(3);
			}
		}
	}

	/**
	 * ViewPager滑动切换监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			tabHost.setCurrentTab(arg0);
		}
	}

	/**
	 * 创建tab View
	 * 
	 * @param string
	 * @return
	 */
	private View createTabView(int stringId) {
		View tabView = getLayoutInflater().inflate(R.layout.tab, null);
		TextView textView = (TextView) tabView.findViewById(R.id.tab_text);
		textView.setText(stringId);
		return tabView;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
