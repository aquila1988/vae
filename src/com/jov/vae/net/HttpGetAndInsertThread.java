package com.jov.vae.net;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jov.vae.dao.DBOpenHelper;

public class HttpGetAndInsertThread implements Runnable {

	private Handler hand;
	private String url;
	private GetResource myGet = new GetResource();
	private XMLReader reader;
	private DBOpenHelper dao;

	public HttpGetAndInsertThread(Handler hand, String url, DBOpenHelper dao) {
		this.hand = hand;
		this.url = url;
		reader = new XMLReader();
		this.dao = dao;
	}

	@Override
	public void run() {
		Message msg = hand.obtainMessage();
		try {
			Log.v("url", url);
			boolean flag = false;
			flag = textProcess();
			msg.what = 200;
			msg.obj = flag;
		} catch (ClientProtocolException e) {
			msg.what = 404;
		} catch (IOException e) {
			msg.what = 100;
		}
		hand.sendMessage(msg);
	}

	private boolean textProcess() throws ClientProtocolException, IOException {
		boolean flag = false;
		Boolean result = myGet.doGetAndInsertNewsData(url, reader, dao);
		if (result) {
			flag = true;
		}
		return flag;
	}

	/*private boolean goodProcess() throws ClientProtocolException, IOException {
		boolean flag = false;
		String[] dates = res.split(",");
		for (String date : dates) {
			if (!dao.hasGoodDate(date.trim())) {
				url = Common.HTTPURL + "good_" + date.trim() + ".xml";
				Boolean result = myGet.doGetAndInsertData(url, date.trim(),
						reader, dao, 2);
				if (result) {
					flag = true;
				}
			}
		}
		return flag;
	}*/
}
