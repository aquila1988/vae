package com.jov.vae.net;


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * ����Get������߳�
 * */
public class HttpGetAndWriteToFileThread implements Runnable {

	private Handler hand;
	private String url;
	private GetResource myGet = new GetResource();

	public HttpGetAndWriteToFileThread(Handler hand, String res) {
		this.hand = hand;
		// ƴ�ӷ��ʷ����������ĵ�ַ
		url =  res;
	}
	@Override
	public void run() {
		// ��ȡ���ǻص���ui��message
		Message msg = hand.obtainMessage();
		Log.e("jov", url);
		try {
			boolean result = myGet.doGetAndWriteToFile(url);
			msg.what = 200;
			msg.obj = result;
		} catch (ClientProtocolException e) {
			msg.what = 404;
		} catch (IOException e) {
			msg.what = 100;
		}
		hand.sendMessage(msg);
	}
}
