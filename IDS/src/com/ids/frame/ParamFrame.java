package com.ids.frame;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ParamFrame {

	private JFrame frame;
	private MainFrame mainFrame;
	private int lengthNum;
	private int packetNum;

	//
	private final int SUBWIDTH = 571;
	private final int SUBHEIGHT = 152;


	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ParamFrame window = new ParamFrame();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	// public ParamFrame() {
	// initialize();
	// }

	public ParamFrame(MainFrame f) {
		this.mainFrame = f;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setFont(new Font("宋体", Font.BOLD, 15));
		frame.setTitle("参数设置");
		
		/**
		 * ???????????????????????????????
		 */
		frame.setSize(SUBWIDTH, SUBHEIGHT);
		frame.setLocationRelativeTo(mainFrame);
		//子窗口永远在最上面
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		JButton okButton = new JButton("确定");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 子界面消失
				frame.dispose();
				// 返回主界面 传值
				mainFrame.setParam(lengthNum, packetNum);

			}
		});
		okButton.setBounds(321, 80, 93, 23);
		frame.getContentPane().add(okButton);

		JButton quitButton = new JButton("取消");
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		quitButton.setBounds(437, 80, 93, 23);
		frame.getContentPane().add(quitButton);

		JLabel label = new JLabel("数据包长度");
		label.setBounds(23, 10, 82, 23);
		frame.getContentPane().add(label);

		final JLabel lengthLabel = new JLabel("0");
		lengthLabel.setBounds(117, 13, 34, 17);
		frame.getContentPane().add(lengthLabel);

		// 创建具有指定方向、值、跨度、最小值和最大值的一个滚动条。
		JScrollBar lengthScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0,
				0, 0, 1500);
		lengthScrollBar.setBounds(173, 16, 102, 17);
		lengthScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				lengthNum = e.getValue();
				lengthLabel.setText(String.valueOf(e.getValue()));
			}
		});

		frame.getContentPane().add(lengthScrollBar);

		final JLabel numLabel = new JLabel("0");
		numLabel.setBounds(125, 53, 43, 19);
		frame.getContentPane().add(numLabel);

		JLabel label_1 = new JLabel("捕获数据包个数");
		label_1.setBounds(23, 49, 93, 23);
		frame.getContentPane().add(label_1);

		JScrollBar numScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0,
				0, 10000);
		numScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		numScrollBar.setBounds(173, 55, 102, 17);
		numScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				packetNum = e.getValue();
				numLabel.setText(String.valueOf(e.getValue()));
			}
		});
		frame.getContentPane().add(numScrollBar);

	}
}
