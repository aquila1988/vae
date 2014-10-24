package com.jov.vae.net;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

/**
 * 把网络图片保存到本地 1.强引用，正常实例化一个对象。 jvm无论内存是否够用系统都不会释放这块内存
 * 2.软引用（softReference）:当我们系统内存不够时，会释放掉 3.弱引用：当我们系统清理内存时发现是一个弱引用对象，直接清理掉
 * 4.虚引用：当我们清理内存时会 把虚引用对象放入一个清理队列当中， 让我们程序员保存当前对象的状态
 * 
 * FileUtiles 作用: 用来向我们的sdcard保存网络接收来的图片
 * */
public class FileUtiles {

	private Context ctx;

	public FileUtiles(Context ctx) {
		this.ctx = ctx;
	}

	public String getAbsolutePath() {
		/*File root = ctx.getExternalFilesDir(null);
		if (root != null)
			return root.getAbsolutePath();
		return null;*/
		String imageDir = Environment.getExternalStorageDirectory()
				.getPath() + "/vae/images/";
		return imageDir;
	}

	public boolean isBitmap(String name) {
		//File root = ctx.getExternalFilesDir(null);
		File file = new File(getAbsolutePath(), name);
		return file.exists();
	}

	public void saveBitmap(String name, Bitmap bitmap) {
		if (bitmap == null){
			Log.v("unfiles=", name);
			return;
		}
		if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			Log.v("unfilesdk=", name);
			return;
		}
		String BitPath = getAbsolutePath() + "/" + name;
		try {
			Log.v("files=", BitPath);
			FileOutputStream fos = new FileOutputStream(BitPath);
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
				.getPath() + "/vae/images/";
		File file = new File(imageDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String imagePath = imageDir + imageName;
		return imagePath;
	}
	public static File updateDir = null;
	public static File updateFile = null;

	public static void createFile(String name) {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			updateDir = new File(Environment.getExternalStorageDirectory()
					+ "/vae");
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
