import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Manager {

	JFrame frame;
	ImagePreview preview;
	InputPanel panel;
	WM wm;

	private static Manager manager;

	public Manager(int width, int height) {

		manager = this;

		frame = new JFrame("weeb layout tool v0.1");

		preview = new ImagePreview(width, height - 200);
		panel = new InputPanel(width, 200);
		wm = new WM();

		frame.setLayout(new BorderLayout());

		frame.add(preview, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.SOUTH);

		frame.pack();

		frame.addWindowListener(wm);

		frame.setVisible(true);

	}

	public class WM extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);

		}

	}

	public void setImages(BufferedImage[][] images) {
		preview.setImages(images);

	}

	public static Manager getInstance() {
		return manager;

	}

}
