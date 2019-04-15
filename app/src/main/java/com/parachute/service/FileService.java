package com.parachute.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileService {
	/***/
	private Context context;
	/**�ļ�������*/
	private static final String FOLDER_NAME = "/CZD13W矿用防坠器无线多参数测试仪/.防坠器加速度曲线图/";
	
	private static final String TAG = "FileService";
	
	
	// ���캯��
	public FileService(Context context) {
		this.context = context;
	}

	/**
	 * ����bitmap���ļ�
	 * @param filename
	 * @param bmp
	 * @return
	 */
	public String saveBitmapToSDCard(String filename, Bitmap bmp) {
		
		// �ļ����·��
		String fileName = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// �ļ������·��
			String fileDir = Environment.getExternalStorageDirectory() + FOLDER_NAME;
			
			// ����ļ��в����ڣ������ļ���
			if (!createDir(fileDir)) {
				Log.e(TAG, "�����ļ���ʧ��!");
			}
			// �����ļ�����
			File file = null;
			// ���������
			FileOutputStream outStream = null;
			
			try {
				// �����Ŀ���ļ���ֱ�ӻ���ļ����󣬷��򴴽�һ����filenameΪ���Ƶ��ļ�
					file = new File(fileDir, filename);
				
					// ����ļ����·��

				fileName = file.toString();
				// ��������������ļ��������ݣ�׷������
					outStream = new FileOutputStream(fileName);
				if(outStream != null)
                {
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
                    outStream.close();
                }
				
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}finally{
				// �ر���

				try {
					if (outStream != null) {
						outStream.close();
					}
				} catch (IOException e) {
					Log.e(TAG, e.toString());
				}
			}
		}
		return fileName;
	}






	/**
	 * ����ָ��·�����ļ��У�������ִ����� ture or false
	 * @param filePath
	 * @return
	 */

	public boolean createDir(String filePath) {
		File fileDir = new File(filePath);// �����ļ�������

		boolean bRet = true;
		// ����ļ������ڣ������ļ�
		if (!fileDir.exists()) {
			// ����ļ����ļ�������

			String[] aDirs = filePath.split("/");
			StringBuffer strDir = new StringBuffer();
			for (int i = 0; i < aDirs.length; i++) {
				// ����ļ���һ���ļ���

				fileDir = new File(strDir.append("/").append(aDirs[i]).toString());
				// �Ƿ����

				if (!fileDir.exists()) {
					// �����ڴ����ļ�ʧ�ܷ���FALSE

					if (!fileDir.mkdir()) {
						bRet = false;
						break;
					}
				}
			}
		}
		return bRet;
	}

}
