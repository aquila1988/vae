package com.jov.vae.net;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

/**
 * ������ͼƬ���浽���� 1.ǿ���ã�����ʵ����һ������ jvm�����ڴ��Ƿ���ϵͳ�������ͷ�����ڴ�
 * 2.�����ã�softReference��:������ϵͳ�ڴ治��ʱ�����ͷŵ� 3.�����ã�������ϵͳ�����ڴ�ʱ������һ�������ö���ֱ�������
 * 4.�����ã������������ڴ�ʱ�� �������ö������һ��������е��У� �����ǳ���Ա���浱ǰ�����״̬
 * 
 * FileUtiles ����: ���������ǵ�sdcard���������������ͼƬ
 * */
public class FileUtiles {

	private Context ctx;

	public FileUtiles(Context ctx) {
		this.ctx = ctx;
	}

	// ��ȡ�ֻ���sdcard����ͼƬ�ĵ�ַ
	public String getAbsolutePath() {
		File root = ctx.getExternalFilesDir(null);
		// �����ֻ��˵ľ���·�������������ж�أ��Լ�������ʱ�ᱻ�����
		if (root != null)
			return root.getAbsolutePath();
		return null;
	}

	// �ж�ͼƬ�ڱ��ػ��浱���Ƿ���ڣ�������ڷ���һ��true
	public boolean isBitmap(String name) {
		File root = ctx.getExternalFilesDir(null);
		// file��ַƴ��
		File file = new File(root, name);
		return file.exists();
	}

	// ��ӵ����ػ��浱��
	public void saveBitmap(String name, Bitmap bitmap) {
		if (bitmap == null)
			return;
		// ���sdcard����ʹ��
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_UNMOUNTED)) {
			return;
		}
		// ƴ��ͼƬҪ���浽sd���ĵ�ַ
		String BitPath = getAbsolutePath() + "/" + name;
		// mtn/sdcard/android/com.anjoyo.zhangxinyi/files/
		try {
			FileOutputStream fos = new FileOutputStream(BitPath);
			/**
			 * bitmap.compress��ͼƬͨ����������浽���� Bitmap.CompressFormat.JPEG ����ͼƬ�ĸ�ʽ
			 * 100 ���浽���ص�ͼƬ��������Ҫѹ��ʱ�ʵ�������С
			 * 
			 * */
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String getImagePath(String imageUrl) {
		int lastSlashIndex = imageUrl.lastIndexOf("/");
		String imageName = imageUrl.substring(lastSlashIndex + 1);
		String imageDir = Environment.getExternalStorageDirectory()
				.getPath() + "/funny/images/";
		File file = new File(imageDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String imagePath = imageDir + imageName;
		return imagePath;
	}
	public static File updateDir = null;
	public static File updateFile = null;

	/***
	 * �����ļ�
	 */
	public static void createFile(String name) {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			updateDir = new File(Environment.getExternalStorageDirectory()
					+ "/funny");
			updateFile = new File(updateDir + "/" + name + ".apk");
			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			deleteExistsApk(updateDir);
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private static void  deleteExistsApk(File dir){
		if(dir!=null&&dir.exists()){
			File[] files = dir.listFiles();
			for(File file:files){
				file.deleteOnExit();
			}
		}
	}

}
