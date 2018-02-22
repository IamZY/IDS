package com.ids.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class Capture {
	// 将抓包信息存入文件中。
	File file = null;
	String str = null;
	static FileOutputStream fos = null;
	// 设备、捕获器和包
	jpcap.NetworkInterface[] devices = null;
	JpcapCaptor captor = null;
	Packet packet = null;
	PacketReceiver receiver = null;
	// 字节到16进制的转换器，将包以16进制形式展现
	HexBinaryAdapter hba = null;
	// MAC类型
	byte[] pro = null;

	// 抓包函数
	public void startCapture() {
		while (captor != null) {
			captor.processPacket(1, receiver);
		}
	}

	public Capture() throws IOException {
		pro = new byte[2];
		hba = new HexBinaryAdapter();
		file = new File("./ipdata.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		fos = new FileOutputStream(file);
		devices = JpcapCaptor.getDeviceList();
		// 注意，我的电脑第一个是有线网卡，第二个是无线的，这里devices[1]是有线的以太网卡
		captor = JpcapCaptor.openDevice(devices[1], 1514, true, 50);
		// 接收抓到的包，覆写下面这个方法来实现将抓到的包写入文件中
		receiver = new PacketReceiver() {
			@Override
			public void receivePacket(Packet packet) {
				// TODO Auto-generated method stub
				try {
					str = "";
					// 获得网络协议类型
					pro[0] = packet.header[12];
					pro[1] = packet.header[13];
					// 这里暂且只抓ip包了
					if (hba.marshal(pro).toString().equals("0800")) {
						str += "src: ";
						str += ((IPPacket) packet).src_ip.toString();
						str += "\n";
						str += "dst: ";
						str += ((IPPacket) packet).dst_ip.toString();
						str += "\n";
						str += "head: ";
						str += hba.marshal(packet.header);
						str += "\n";
						str += "data: ";
						str += hba.marshal(packet.data);
						str += "\n\n";
					} else {
						str += "a non-ip packet\n\n";
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				// 将str写到文件中
				try {
					Capture.fos.write(str.getBytes());
					Capture.fos.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};// receiver初始化结束
			// 开始抓包
		startCapture();
	}

	public static void main(String[] args) throws IOException {
		new Capture();
	}
}
