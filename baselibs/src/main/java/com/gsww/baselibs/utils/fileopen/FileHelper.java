package com.gsww.baselibs.utils.fileopen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.Toast;

import com.gsww.baselibs.utils.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件帮助类
 * 
 * @author wujd
 * @date 2014-4-14
 */
public class FileHelper {

	public static final String FILE_DIR = android.os.Environment
			.getExternalStorageDirectory().getPath()
			+ Configuration.getBaseFilePath();

	/**
	 * 
	 * 方法描述 : 保存文件到本地
	 * 
	 * @param is            ----输入流
	 * @param filePath     ----文件路径
	 * @throws Exception
	 */
	public static void saveFiles(InputStream is, String filePath)throws Exception {
		File file = new File(filePath);
		if(!file.exists())
			file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		int ch;
		StringBuffer b = new StringBuffer();
		byte buf[] = new byte[128];
		int numread;
		while ((numread = is.read(buf)) != -1) {
			ch = numread;
			b.append((char) ch);
			fos.write(buf, 0, numread);
			fos.flush();
		}
		fos.close();
		is.close();
	}


    // 将InputStream转换成Bitmap  
    public static Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }  
    
	  // 将Bitmap转换成InputStream  
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;  
    }  
    
	/**
	 * 
	 * 方法描述 : 加载本地文件
	 * 
	 * @param filePath   ---文件路径
	 * @return
	 */
	public static InputStream loadFileNew(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			Logger.info(e);
		}
		return is;
	}

	/**
	 * 
	 * 方法描述 : 清除应用数据
	 * 
	 * @param context
	 * @return
	 */
	public static void clearAppData(Context context) {
		File dir = new File(FILE_DIR);
		deleteDir(dir);
		dir = new File(context.getCacheDir().getParent());
		deleteDir(dir);
	}

	/**
	 * 删除目录
	 * @param dir   ---路径
	 */
	public static void deleteDir(File dir) {
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return; // 检查参数
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				file.delete(); // 删除所有文件
			} else if (file.isDirectory()) {
				deleteDir(file); // 递规的方式删除文件夹
			}
		}
		dir.delete();// 删除目录本身
	}

	public static boolean deleteFile(String path){
		try {
			File file=new File(path);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			Logger.info(e);
			return false;
			
		}
		return true;
	}
	/**
	 * 根据传入的大小（字节数）返回相应的带单位的大小 
	 * @param size -- 大小（字节数）
	 * @return -- 带单位的大小
	 */
	public static String getShowFileSize(long size) {
		if (size < 1024) {
			return size + "B";
		} else if (size < 1024 * 1024) {
			return (size / 1024) + "KB";
		} else {
			return size / (1024 * 1024) + "MB";
		}
	}
	/**
	 * 将文件大小转化为字节
	 * @param str
	 * @return
	 */
	public static String switchSize(String str) {
		char[] char_arr = str.toCharArray();
		int i = 0;
		for (i = 0; i < char_arr.length; i++) {
			int a=((int)char_arr[i]);
			if (!((a>47 && a <58) || a == 46)) {
				break;
			}
		}
		str = str.substring(0, i);
		return str;
	}

	public static  void openDocThirdParty(final Context context, String url, String fileName) {
		FileShowUtil fileShowUtil = new FileShowUtil(context);
		fileShowUtil.clickFileView(url, fileName);
		fileShowUtil.setFileState(new FileShowUtil.UploadFileState() {
			@Override
			public void fileUploadSuccess(File file) {
				try{
					Intent intent = OpenFileUtil.openFile(file.getAbsolutePath());
					context.startActivity(intent);
				}catch (Exception e){
					Looper.prepare();
					Toast.makeText(context,"您的手机未安装第三方查看文档的APP", Toast.LENGTH_LONG).show();
					Looper.loop();
				}

			}

			@Override
			public void fileUploadError() {
				Toast.makeText(context,"文件下载失败", Toast.LENGTH_LONG).show();
			}
		});
	}
}
