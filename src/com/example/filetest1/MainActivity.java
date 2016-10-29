package com.example.filetest1;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Button read, write;
	private EditText readText, writeText;
	private String fileName = "content.txt";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		read = (Button) findViewById(R.id.read);
		write = (Button) findViewById(R.id.write);
		readText = (EditText) findViewById(R.id.readText);
		writeText = (EditText) findViewById(R.id.writeText);
		read.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				readText.setText(read());
			}
		});
		write.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				write(writeText.getText().toString());
			}
		});
	}
	public void write(String content) {
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				File destFile = new File(sdCardDir.getCanonicalPath()
						+ File.separator + fileName);
				System.out.println("*********"+destFile.getAbsolutePath());
				RandomAccessFile raf = new RandomAccessFile(destFile, "rw");
				raf.seek(destFile.length());
				raf.write(content.getBytes());
				raf.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String read() {
		StringBuilder sbBuilder = new StringBuilder("");
		try {
			//�ж��ֻ����Ƿ���ڿ��õ�SD��
			if(Environment.getExternalStorageState().
					equals(Environment.MEDIA_MOUNTED)){
				File sdCard=Environment.getExternalStorageDirectory();
				File destFile = new File(sdCard.getCanonicalPath()
						+ File.separator + fileName);
				FileInputStream fis=new FileInputStream(destFile);
				byte[] buffer = new byte[64];
				int hasRead;
				while ((hasRead = fis.read(buffer)) != -1) {
					sbBuilder.append(new String(buffer, 0, hasRead));
				}
				return sbBuilder.toString();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
