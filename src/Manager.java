import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Manager {

	JFrame frame;
	ImagePreview preview;
	InputPanel panel;
	WM wm;
	CL cl;

	int panelHeight = 220;

	private static Manager manager;

	public Manager(int width, int height) {

		manager = this;

		frame = new JFrame("weeb layout tool v0.1");

		preview = new ImagePreview(width, height - panelHeight);
		panel = new InputPanel(width, panelHeight);
		wm = new WM();
		cl = new CL();

		frame.setLayout(new BorderLayout());

		frame.add(preview, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.SOUTH);

		frame.pack();

		frame.addWindowListener(wm);

		frame.addComponentListener(cl);

		frame.setVisible(true);

	}

	public class CL extends ComponentAdapter {

		@Override
		public void componentResized(ComponentEvent e) {
			preview.setSize(frame.getWidth(), frame.getHeight() - panelHeight);
			panel.resizeWidth(getWidth());
			// panel.setSize(getWidth(), getHeight());
		}

	}

	public class WM extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);

		}

	}

	public void exportImages(String path) throws IOException {

		BufferedImage[][] images = preview.getImages();

		int counter = 0;

		for (BufferedImage[] array : images) {
			for (BufferedImage image : array) {
				ImageIO.write(image, "png", new File(path + "image_" + counter + ".png"));
				counter++;

			}
		}

	}

	public void setImages(BufferedImage[][] images) {
		preview.setImages(images);

	}

	public static Manager getInstance() {
		return manager;

	}

	public int getWidth() {
		return frame.getWidth();

	}

	public int getHeight() {
		return frame.getHeight();

	}

	public int getPreviewHeight() {
		return frame.getHeight() - panelHeight;

	}

}
