import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePreview extends JPanel {

	private Manager manager;
	private int offset = 2;

	private BufferedImage[][] images = new BufferedImage[0][];

	public ImagePreview(int width, int height) {

		manager = Manager.getInstance();

		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));

	}

	@Override
	public void paint(Graphics g) {

		g.setColor(Color.BLACK);

		g.fillRect(0, 0, manager.getWidth(), manager.getHeight());

		System.out.println(manager.getHeight());

		int currHeight = 0;
		int currWidth = 0;

		for (BufferedImage[] array : images) {

			for (BufferedImage image : array) {

				g.drawImage(image, currWidth, currHeight, null);

				currWidth += image.getWidth() + offset;

			}

			currHeight += array[0].getHeight() + offset;
			currWidth = 0;

		}

	}

	public void setImages(BufferedImage[][] images) {
		this.images = images;
		repaint();

	}

}
