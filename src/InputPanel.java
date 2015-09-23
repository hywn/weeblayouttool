import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private JButton export = new JButton("export");
	private JTextField imgpath = new JTextField("/mary/pictures/uberhaxor.jpg", 20);
	private JTextField codepath = new JTextField("/mary/text/text_lines.txt", 20);
	private JTextField exportLoc = new JTextField("/mary/pictures/testlayout/", 20);
	private JTextArea editor = new JTextArea(13, 20);
	private ImageUtil util = new ImageUtil();

	JPanel codeArea = new JPanel();
	JPanel controls = new JPanel();

	int codeWidth = 300, height;

	private Manager manager;

	public InputPanel(int width, int height) {
		this.height = height;

		manager = Manager.getInstance();

		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));

		setLayout(new BorderLayout());

		parse.addActionListener(new PButListener());
		importbut.addActionListener(new IButListener());
		export.addActionListener(new EButListener());

		// controls.setLayout(new FlowLayout());
		codeArea.setLayout(new BorderLayout());

		controls.setSize(codeWidth, height);
		controls.setPreferredSize(new Dimension(width - codeWidth, height));
		codeArea.setSize(codeWidth, height);

		// controls.add(parse, BorderLayout.EAST);
		// controls.add(importbut);
		controls.add(codepath, BorderLayout.WEST);
		controls.add(imgpath, BorderLayout.NORTH);
		controls.add(exportLoc);

		codeArea.add(parse, BorderLayout.EAST);
		codeArea.add(importbut, BorderLayout.WEST);
		codeArea.add(export, BorderLayout.CENTER);
		codeArea.add(new JScrollPane(editor), BorderLayout.SOUTH);

		add(codeArea, BorderLayout.EAST);
		add(controls, BorderLayout.WEST);

	}

	public void resizeWidth(int width) {
		controls.setSize(width - codeWidth, height);
		controls.setPreferredSize(new Dimension(width - codeWidth, height));

		codeArea.setSize(codeWidth, height);
		codeArea.setPreferredSize(new Dimension(codeWidth, height));
		// repaint();

	}

	class EButListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// export selection to files
			try {
				manager.exportImages(exportLoc.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

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
