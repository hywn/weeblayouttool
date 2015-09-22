import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputPanel extends JPanel {

	private JLabel label = new JLabel("label");
	private JButton parse = new JButton("parse");
	private JButton importbut = new JButton("import code");
	private JTextField imgpath = new JTextField("/mary/pictures/uberhaxor.jpg", 3);
	private JTextField codepath = new JTextField("/mary/text/text_lines.txt", 20);
	private JTextArea editor = new JTextArea(10, 0);
	private ImageUtil util = new ImageUtil();

	private Manager manager;

	public InputPanel(int width, int height) {

		manager = Manager.getInstance();

		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));

		setLayout(new BorderLayout());

		parse.addActionListener(new PButListener());
		importbut.addActionListener(new IButListener());

		add(parse, BorderLayout.EAST);
		add(importbut);
		add(codepath, BorderLayout.WEST);
		add(imgpath, BorderLayout.NORTH);
		add(new JScrollPane(editor), BorderLayout.SOUTH);

	}

	class IButListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				editor.setText(util.getLines(codepath.getText()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	class PButListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				manager.setImages(util.parse(editor.getText(), ImageIO.read(new File(imgpath.getText()))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

}
