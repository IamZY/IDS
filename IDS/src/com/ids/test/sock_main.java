package com.ids.test;

import java.util.Scanner;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.*;

/**
 * 使用jpcap显示网络上的各种数据包
 * 
 * @author www.NetJava.cn
 */
public class sock_main {
	private static int model;

	// 程序启动主方法
	public static void main(String args[]) {

		try {
			System.out.println("请输入你需要抓取的类型包：");
			System.out.println("TCP包输入‘1’");
			System.out.println("UDP包输入‘2’");
			System.out.println("ICMP包输入‘3’");
			System.out.println("ARP包输入‘4’");
			Scanner in = new Scanner(System.in);
			model = in.nextInt();
			// 获取本机上的网络接口对象数组
			final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
			System.out.println(devices[0].name);
			for (int i = 0; i < devices.length; i++) {
				NetworkInterface nc = devices[i];
				// 创建某个卡口上的抓取对象,最大为2000个
				JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 2000, true, 20);
				startCapThread(jpcap); // 线程执行抓包
				System.out.println("开始抓取第" + i + "个卡口上的数据");
			}

		} catch (Exception ef) {
			ef.printStackTrace();
			System.out.println("启动失败:  " + ef);
		}

	}

	// 将每个Captor放到独立线程中运行
	public static void startCapThread(final JpcapCaptor jpcap) {
		JpcapCaptor jp = jpcap;
		java.lang.Runnable rnner = new Runnable() { // 创建线程
			public void run() {
				// 使用接包处理器循环抓包
				jpcap.loopPacket(-1, new TestPacketReceiver(model)); // -1无限抓取包，抓包监听器获取包
			}
		};
		new Thread(rnner).start();// 启动抓包线程
	}
}