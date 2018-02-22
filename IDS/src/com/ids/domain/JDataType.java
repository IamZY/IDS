package com.ids.domain;

import javax.swing.JLabel;

public class JDataType {

	private JLabel allNumLabel;
	private JLabel ipNumLabel;
	private JLabel tcpNumLabel;
	private JLabel udpNumLabel;
	private JLabel arpNumLabel;
	private JLabel icmpNumLabel;
	public JLabel getAllNumLabel() {
		return allNumLabel;
	}
	public void setAllNumLabel(JLabel allNumLabel) {
		this.allNumLabel = allNumLabel;
	}
	public JLabel getIpNumLabel() {
		return ipNumLabel;
	}
	public void setIpNumLabel(JLabel ipNumLabel) {
		this.ipNumLabel = ipNumLabel;
	}
	public JLabel getTcpNumLabel() {
		return tcpNumLabel;
	}
	public void setTcpNumLabel(JLabel tcpNumLabel) {
		this.tcpNumLabel = tcpNumLabel;
	}
	public JLabel getUdpNumLabel() {
		return udpNumLabel;
	}
	public void setUdpNumLabel(JLabel udpNumLabel) {
		this.udpNumLabel = udpNumLabel;
	}
	public JLabel getArpNumLabel() {
		return arpNumLabel;
	}
	public void setArpNumLabel(JLabel arpNumLabel) {
		this.arpNumLabel = arpNumLabel;
	}
	public JLabel getIcmpNumLabel() {
		return icmpNumLabel;
	}
	public void setIcmpNumLabel(JLabel icmpNumLabel) {
		this.icmpNumLabel = icmpNumLabel;
	}
	public JDataType(JLabel allNumLabel, JLabel ipNumLabel, JLabel tcpNumLabel,
			JLabel udpNumLabel, JLabel arpNumLabel, JLabel icmpNumLabel) {
		super();
		this.allNumLabel = allNumLabel;
		this.ipNumLabel = ipNumLabel;
		this.tcpNumLabel = tcpNumLabel;
		this.udpNumLabel = udpNumLabel;
		this.arpNumLabel = arpNumLabel;
		this.icmpNumLabel = icmpNumLabel;
	}

	
	
	
}