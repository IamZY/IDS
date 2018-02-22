package com.ids.dao;

import java.sql.Timestamp;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ids.domain.JDataType;

import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

/**
 * 抓包监听器,实现PacketReceiver中的方法:打印出数据包说明
 */

class MyPacketReceiver implements PacketReceiver {

	private int flag;
	private JTextArea textArea;
	private JDataType jdt;

	// //////////////////////////////////////////////////////////////////////
	private int allNum;
	private int ipNum;
	private int tcpNum;
	private int udpNum;
	private int arpNum;
	private int icmpNum;

	public MyPacketReceiver(int flag, JTextArea textArea, JDataType jdt) {
		this.flag = flag;
		this.textArea = textArea;
		this.jdt = jdt;
	}

	/**
	 * 实现的接包方法:
	 */
	public void receivePacket(Packet packet) {
		

		// 所有包
		if (flag == 1) {

			// Tcp包
			if (packet instanceof jpcap.packet.TCPPacket) {
				tcpNum++;
				jdt.getTcpNumLabel().setText(String.valueOf(tcpNum));
				TCPPacket p = (TCPPacket) packet;
				String s = "TCPPacket:| 目的ip及端口 " + p.dst_ip + ":" + p.dst_port
						+ "|源ip及端口 " + p.src_ip + ":" + p.src_port + " |数据长度: "
						+ p.len;
				System.out.println(s);
				textArea.append(s + "\n");
			}
			// UDP包
			if (packet instanceof jpcap.packet.UDPPacket) {
				udpNum++;
				jdt.getUdpNumLabel().setText(String.valueOf(udpNum));
				UDPPacket p = (UDPPacket) packet;
				String s = "UDPPacket:| 目的ip及端口 " + p.dst_ip + ":" + p.dst_port
						+ "||源ip及端口 " + p.src_ip + ":" + p.src_port
						+ " |数据长度: " + p.len;
				System.out.println(s);
				textArea.append(s + "\n");
			}
			// ARP请求包
			if (packet instanceof jpcap.packet.ARPPacket) {
				arpNum++;
				jdt.getArpNumLabel().setText(String.valueOf(arpNum));
				ARPPacket p = (ARPPacket) packet;
				// 返回发送方MAC地址
				Object saa = p.getSenderHardwareAddress();
				Object taa = p.getTargetHardwareAddress();
				String s = "* * * ARPPacket:| 发送硬件地址： " + saa + "|目标硬件地址： "
						+ taa + " |长度: " + p.len;
				System.out.println(s);
				textArea.append(s + "\n");
			}
			// ICMPPacket包
			if (packet instanceof jpcap.packet.ICMPPacket) {
				icmpNum++;
				jdt.getIcmpNumLabel().setText(String.valueOf(icmpNum));
				ICMPPacket p = (ICMPPacket) packet;
				// ICMP包的路由链
				String router_ip = "";
				for (int i = 0; i < p.router_ip.length; i++) {
					router_ip += " " + p.router_ip[i].getHostAddress();
				}
				String s = "@ @ @ ICMPPacket:| 路由IP: " + router_ip
						+ " |redir_ip: " + p.redir_ip + " |最大传输单元: " + p.mtu
						+ " |长度: " + p.len;
				System.out.println(s);
				textArea.append(s + "\n");
			}

			// IP
			if (packet instanceof jpcap.packet.IPPacket) {
				ipNum++;
				jdt.getIpNumLabel().setText(String.valueOf(ipNum));
				IPPacket p = (IPPacket) packet;
				Timestamp timestamp = new Timestamp((packet.sec * 1000)
						+ (packet.usec / 1000));
				String s = "IPPacket: 数据包时间: " + timestamp.toString()
						+ " |源IP地址: " + p.src_ip.toString() + " |目的IP地址: "
						+ p.dst_ip.toString() + " |数据包长度: " + p.length;
				System.out.println(s);
				textArea.append(s + "\n");
			}

		}

		// IP包
		else if (packet instanceof jpcap.packet.IPPacket && flag == 2) {
			ipNum++;
			jdt.getIpNumLabel().setText(String.valueOf(ipNum));
			IPPacket p = (IPPacket) packet;
			Timestamp timestamp = new Timestamp((packet.sec * 1000)
					+ (packet.usec / 1000));
			String s = "IPPacket: 数据包时间: " + timestamp.toString() + " |源IP地址: "
					+ p.src_ip.toString() + " |目的IP地址: " + p.dst_ip.toString()
					+ " |数据包长度: " + p.length;
			System.out.println(s);
			textArea.append(s + "\n");
		}
		// Tcp包
		else if (packet instanceof jpcap.packet.TCPPacket && flag == 3) {
			tcpNum++;
			jdt.getTcpNumLabel().setText(String.valueOf(tcpNum));
			TCPPacket p = (TCPPacket) packet;
			String s = "TCPPacket:| 目的ip及端口 " + p.dst_ip + ":" + p.dst_port
					+ "|源ip及端口 " + p.src_ip + ":" + p.src_port + " |数据长度: "
					+ p.len;
			System.out.println(s);
			textArea.append(s + "\n");
		}
		// UDP包
		else if (packet instanceof jpcap.packet.UDPPacket && flag == 4) {
			udpNum++;
			jdt.getUdpNumLabel().setText(String.valueOf(udpNum));
			UDPPacket p = (UDPPacket) packet;
			String s = "UDPPacket:| 目的ip及端口 " + p.dst_ip + ":" + p.dst_port
					+ "||源ip及端口 " + p.src_ip + ":" + p.src_port + " |数据长度: "
					+ p.len;
			System.out.println(s);
			textArea.append(s + "\n");
		}
		// ARP请求包
		else if (packet instanceof jpcap.packet.ARPPacket && flag == 5) {
			arpNum++;
			jdt.getArpNumLabel().setText(String.valueOf(arpNum));
			ARPPacket p = (ARPPacket) packet;
			// 返回发送方MAC地址
			Object saa = p.getSenderHardwareAddress();
			Object taa = p.getTargetHardwareAddress();
			String s = "* * * ARPPacket:| 发送硬件地址： " + saa + "|目标硬件地址： " + taa
					+ " |长度: " + p.len;
			System.out.println(s);
			textArea.append(s + "\n");
		}
		// ICMPPacket包
		else if (packet instanceof jpcap.packet.ICMPPacket && flag == 6) {
			icmpNum++;
			jdt.getIcmpNumLabel().setText(String.valueOf(icmpNum));
			ICMPPacket p = (ICMPPacket) packet;
			// ICMP包的路由链
			String router_ip = "";
			for (int i = 0; i < p.router_ip.length; i++) {
				router_ip += " " + p.router_ip[i].getHostAddress();
			}
			String s = "@ @ @ ICMPPacket:| 路由IP: " + router_ip + " |redir_ip: "
					+ p.redir_ip + " |最大传输单元: " + p.mtu + " |长度: " + p.len;
			System.out.println(s);
			textArea.append(s + "\n");
		}
		// 取得链路层数据头 :如果你想局网抓包或伪造数据包，嘿嘿hh..
		DatalinkPacket datalink = packet.datalink;
		// 如果是以太网包
		if (datalink instanceof jpcap.packet.EthernetPacket) {
			EthernetPacket ep = (EthernetPacket) datalink;
			String s = "  以太包: " + "|目的MAC: " + ep.getDestinationAddress()
					+ "|源MAC: " + ep.getSourceAddress();
			System.out.println(s);
			textArea.append(s + "\n");
		}
		
		allNum = ipNum + tcpNum + udpNum + arpNum + icmpNum;
		jdt.getAllNumLabel().setText(String.valueOf(allNum));

	}
}
