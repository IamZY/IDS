package com.ids.test;

import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

/**
 * 抓包监听器,实现PacketReceiver中的方法:打印出数据包说明
 */

class TestPacketReceiver implements PacketReceiver {

	public int model;

	public TestPacketReceiver(int n) {
		this.model = n;
	}

	/**
	 * 实现的接包方法:
	 */
	public void receivePacket(Packet packet) {
		// Tcp包
		if (packet instanceof jpcap.packet.TCPPacket && model == 1) {
			TCPPacket p = (TCPPacket) packet;
			String s = "TCPPacket:| 目的ip及端口 " + p.dst_ip + ":" + p.dst_port
					+ "|源ip及端口 " + p.src_ip + ":" + p.src_port + " |数据长度: "
					+ p.len;
			System.out.println(s);
		}
		// UDP包
		else if (packet instanceof jpcap.packet.UDPPacket && model == 2) {
			UDPPacket p = (UDPPacket) packet;
			String s = "UDPPacket:| 目的ip及端口 " + p.dst_ip + ":" + p.dst_port
					+ "||源ip及端口 " + p.src_ip + ":" + p.src_port + " |数据长度: "
					+ p.len;
			System.out.println(s);
		}
		// ICMPPacket包
		else if (packet instanceof jpcap.packet.ICMPPacket && model == 3) {
			ICMPPacket p = (ICMPPacket) packet;
			// ICMP包的路由链
			String router_ip = "";
			for (int i = 0; i < p.router_ip.length; i++) {
				router_ip += " " + p.router_ip[i].getHostAddress();
			}
			String s = "@ @ @ ICMPPacket:| 路由IP: " + router_ip + " |redir_ip: "
					+ p.redir_ip + " |最大传输单元: " + p.mtu + " |长度: " + p.len;
			System.out.println(s);
		}
		// ARP请求包
		else if (packet instanceof jpcap.packet.ARPPacket && model == 4) {
			ARPPacket p = (ARPPacket) packet;
			// Returns the hardware address (MAC address) of the sender
			Object saa = p.getSenderHardwareAddress();
			Object taa = p.getTargetHardwareAddress();
			String s = "* * * ARPPacket:| 发送硬件地址： " + saa + "|目标硬件地址： " + taa
					+ " |长度: " + p.len;
			System.out.println(s);

		}
		// 取得链路层数据头 :如果你想局网抓包或伪造数据包，嘿嘿
		DatalinkPacket datalink = packet.datalink;
		// 如果是以太网包
		if (datalink instanceof jpcap.packet.EthernetPacket) {
			EthernetPacket ep = (EthernetPacket) datalink;
			String s = "  以太包: " + "|目的MAC: " + ep.getDestinationAddress()
					+ "|源MAC: " + ep.getSourceAddress();
			System.out.println(s);
		}
	}

}
