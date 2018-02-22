package com.ids.frame;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.ids.dao.GetDeviceDao;
import com.ids.dao.CapThreadDao;
import com.ids.domain.JDataType;
import com.sun.corba.se.spi.orb.StringPair;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

import jpcap.NetworkInterface;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class MainFrame {

	private JFrame frame;
	private NetworkInterface netInter;
	private JTextArea textArea;
	private Map<String, NetworkInterface> map;
	private int flag;
	private int lengthNum;
	private int packetNum;
	private String[] strs;
	private JLabel allNum;
	private JLabel ipNum;
	private JLabel tcpNum;
	private JLabel udpNum;
	private JLabel arpNum;
	private JLabel icmpNum;

	// ///////////////////////////////////////////
	// 得到显示器屏幕的宽高
	private final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	// 定义窗体的宽高
	private final int WINDOWWEDTH = 728;
	private final int WINDOWHEIGHT = 451;
	//
	private int locationWidth;
	private int locationHeight;

	// ////////////////////////////////////////////////
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}
	
	/**
	 * Create set,get Method
	 */
	public void setParam(int lengthNum,int packetNum){
		this.lengthNum = lengthNum;
		this.packetNum = packetNum;
	}
	
	public MainFrame getMainFrame(){
		return this;
	}
	
	public int getLocationWidth(){
		return locationWidth;
	}
	
	public int getLocationHeight(){
		return locationHeight;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setFont(new Font("Dialog", Font.BOLD, 12));
		frame.setTitle("入侵检测系统");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		// 窗口居中
		frame.setBounds((width - WINDOWWEDTH) / 2, (height - WINDOWHEIGHT) / 2,
				WINDOWWEDTH, WINDOWHEIGHT);
		
		locationWidth = frame.getLocation().x;
		locationHeight = frame.getLocation().y;
		
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel mbwk = new JLabel("目标网卡");
		mbwk.setFont(new Font("宋体", Font.BOLD, 15));
		mbwk.setBounds(23, 29, 85, 34);
		frame.getContentPane().add(mbwk);

		strs = GetDeviceDao.getDevice();
		
		if (strs == null) {
			JOptionPane.showMessageDialog(frame, "未获取网卡");
			System.exit(0);
		}

		JComboBox<String> mbwkcb = new JComboBox<String>(strs);
//		 JComboBox mbwkcb = new JComboBox();
		mbwkcb.setBounds(118, 35, 260, 23);
		frame.getContentPane().add(mbwkcb);

		// 下拉框增加监听事件
		map = GetDeviceDao.getDeviceMap();
		mbwkcb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				// 返回当前所选项
				String str_cb = (String) cb.getSelectedItem();
				netInter = (NetworkInterface) map.get(str_cb);
			}
		});

		/**
		 * 开始捕获
		 */
		JButton startButton = new JButton("开始捕获");
		startButton.setBounds(486, 106, 93, 23);
		frame.getContentPane().add(startButton);

		// 开始捕获按钮增加监听事件
		startButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				// label设初值0
				allNum.setText("0");
				ipNum.setText("0");
				tcpNum.setText("0");
				udpNum.setText("0");
				arpNum.setText("0");
				icmpNum.setText("0");

				System.out.println("start...");
				
				//未选择网卡和数据包类型时
				if(flag == 0){
					flag = 1;
				}
				
				if(netInter == null){
					netInter = map.get(strs[0]);
				}
				
				System.out.println(lengthNum + "-----" + packetNum);
				
				textArea.setText("");
				JDataType jdt = new JDataType(allNum, ipNum, tcpNum, udpNum,
						arpNum, icmpNum);
				CapThreadDao.startCap(netInter, flag, textArea, jdt);
			}

		});

		JButton endButton = new JButton("停止捕获");
		endButton.setBounds(602, 106, 93, 23);
		frame.getContentPane().add(endButton);

		// 停止捕获按钮增加监听事件
		endButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("end....");
				textArea.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>The end!<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
				CapThreadDao.endCapThread();
				JOptionPane.showMessageDialog(frame, "停止捕获");
			}

		});

		JLabel sjblx = new JLabel("选择数据包类型");
		sjblx.setFont(new Font("宋体", Font.BOLD, 15));
		sjblx.setBounds(466, 29, 128, 34);
		frame.getContentPane().add(sjblx);

		String[] sjblxs = { "ALL", "IP", "TCP", "UDP", "ARP", "ICMP" };
		JComboBox<String> sjblxcb = new JComboBox<String>(sjblxs);
//		 JComboBox sjblxcb = new JComboBox();
		sjblxcb.setBounds(621, 36, 74, 21);
		frame.getContentPane().add(sjblxcb);

		// 数据包类型增加监听事件
		sjblxcb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				// 返回当前所选项
				String str_cb = (String) cb.getSelectedItem();

				if (str_cb.equals("ALL")) {
					flag = 1;
				} else if (str_cb.equals("IP")) {
					flag = 2;
				} else if (str_cb.equals("TCP")) {
					flag = 3;
				} else if (str_cb.equals("UDP")) {
					flag = 4;
				} else if (str_cb.equals("ARP")) {
					flag = 5;
				} else if (str_cb.equals("ICMP")) {
					// ICMP
					flag = 6;
				} else {
					// 什么都没选择的时候设初值为ALL
					flag = 1;
				}
			}
		});

		JLabel lblAll = new JLabel("总数");
		lblAll.setForeground(Color.RED);
		lblAll.setFont(new Font("宋体", Font.BOLD, 15));
		lblAll.setBounds(23, 92, 40, 15);
		frame.getContentPane().add(lblAll);

		JLabel lblIp = new JLabel("IP");
		lblIp.setForeground(Color.RED);
		lblIp.setFont(new Font("宋体", Font.BOLD, 15));
		lblIp.setBounds(139, 92, 27, 15);
		frame.getContentPane().add(lblIp);

		JLabel lblTcp = new JLabel("TCP");
		lblTcp.setForeground(Color.RED);
		lblTcp.setFont(new Font("宋体", Font.BOLD, 15));
		lblTcp.setBounds(232, 92, 27, 15);
		frame.getContentPane().add(lblTcp);

		JLabel lblUdp = new JLabel("UDP");
		lblUdp.setForeground(Color.RED);
		lblUdp.setFont(new Font("宋体", Font.BOLD, 15));
		lblUdp.setBounds(332, 92, 27, 15);
		frame.getContentPane().add(lblUdp);

		JLabel label = new JLabel("个");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(99, 92, 16, 15);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("个");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(206, 92, 16, 15);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("个");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(306, 92, 16, 15);
		frame.getContentPane().add(label_2);

		JLabel label_3 = new JLabel("个");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(413, 92, 16, 15);
		frame.getContentPane().add(label_3);

		allNum = new JLabel("0");
		allNum.setFont(new Font("宋体", Font.BOLD, 15));
		allNum.setBounds(62, 92, 27, 15);
		frame.getContentPane().add(allNum);

		ipNum = new JLabel("0");
		ipNum.setFont(new Font("宋体", Font.BOLD, 15));
		ipNum.setBounds(175, 92, 27, 15);
		frame.getContentPane().add(ipNum);

		tcpNum = new JLabel("0");
		tcpNum.setFont(new Font("宋体", Font.BOLD, 15));
		tcpNum.setBounds(269, 92, 27, 15);
		frame.getContentPane().add(tcpNum);

		udpNum = new JLabel("0");
		udpNum.setFont(new Font("宋体", Font.BOLD, 15));
		udpNum.setBounds(376, 92, 27, 15);
		frame.getContentPane().add(udpNum);

		JLabel lblArp = new JLabel("ARP");
		lblArp.setForeground(Color.RED);
		lblArp.setFont(new Font("宋体", Font.BOLD, 15));
		lblArp.setBounds(23, 117, 40, 15);
		frame.getContentPane().add(lblArp);

		JLabel lblIcmp = new JLabel("ICMP");
		lblIcmp.setForeground(Color.RED);
		lblIcmp.setFont(new Font("宋体", Font.BOLD, 15));
		lblIcmp.setBounds(139, 117, 40, 15);
		frame.getContentPane().add(lblIcmp);

		JLabel label_4 = new JLabel("个");
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(99, 117, 16, 15);
		frame.getContentPane().add(label_4);

		JLabel label_5 = new JLabel("个");
		label_5.setFont(new Font("宋体", Font.BOLD, 15));
		label_5.setBounds(216, 117, 16, 15);
		frame.getContentPane().add(label_5);

		arpNum = new JLabel("0");
		arpNum.setFont(new Font("宋体", Font.BOLD, 15));
		arpNum.setBounds(62, 117, 27, 15);
		frame.getContentPane().add(arpNum);

		icmpNum = new JLabel("0");
		icmpNum.setFont(new Font("宋体", Font.BOLD, 15));
		icmpNum.setBounds(185, 117, 27, 15);
		frame.getContentPane().add(icmpNum);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 712, 23);
		frame.getContentPane().add(menuBar);

		JMenu fileMenu = new JMenu("文件");
		fileMenu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(fileMenu);

		JMenuItem saveAsItem = new JMenuItem("另存为");
		fileMenu.add(saveAsItem);

		JMenuItem clearItem = new JMenuItem("清空日志");
		clearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		
		fileMenu.add(clearItem);
		
		JMenu menu_1 = new JMenu("设置");
		menu_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(menu_1);
		
		//参数设置增加监听器
		JMenuItem paramItem = new JMenuItem("参数设置");
		paramItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//创建新的参数窗口
				new ParamFrame(getMainFrame());
			}
		});
		
		menu_1.add(paramItem);

		JMenu menu = new JMenu("帮助");
		menu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(menu);

		JMenuItem mntmHelp = new JMenuItem("Help");
		menu.add(mntmHelp);

		JLabel authorLb = new JLabel("@IamZY");
		authorLb.setBounds(641, 389, 54, 15);
		frame.getContentPane().add(authorLb);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 154, 672, 225);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

	}
}
