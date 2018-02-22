package com.ids.dao;

import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.stream.events.EndDocument;

import com.ids.domain.JDataType;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class CapThreadDao {

	private static int f;
	private static JTextArea textArea;
	private static Runnable rnner;
	private static JpcapCaptor jpcap;
	private static JDataType jdt;

	public static void startCap(NetworkInterface nc, int flag, JTextArea ta ,JDataType dataType) {
		textArea = ta;
		jdt = dataType;
		try {
			jpcap = JpcapCaptor.openDevice(nc, 2000, true, 20);
			startCapThread(jpcap); // 线程执行抓包
			f = flag;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将每个Captor放到独立线程中运行
	public static void startCapThread(final JpcapCaptor jpcap) {
		JpcapCaptor jp = jpcap;
		rnner = new Runnable() { // 创建线程
			public void run() {
				// 使用接包处理器循环抓包
				jpcap.loopPacket(-1, new MyPacketReceiver(f, textArea ,jdt)); // -1无限抓取包，抓包监听器获取包的数量
			}
		};
		new Thread(rnner).start();// 启动抓包线程
	}

	//关闭线程
	 public static void endCapThread(){
		 jpcap.close();
	 }

}
