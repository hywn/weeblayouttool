
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageUtil {

	public Image fetchResizedImage(String fileName, int scale) throws IOException {
		// length = x
		// width = y

		Image orig = fetchImage(fileName);

		return orig.getScaledInstance(orig.getWidth(null) * scale, orig.getHeight(null) * scale, 0);

	}

	public BufferedImage fetchImage(String fileName) throws IOException {
		return ImageIO.read(this.getClass().getResourceAsStream(fileName));

	}

	public Image[] makeAnimationStrip(BufferedImage spriteSheet, int size, int newSize) {

		Image[] finishedStrip = new Image[spriteSheet.getHeight() / size];

		for (int i = 0; i < spriteSheet.getHeight() / size; i++) {

			finishedStrip[i] = spriteSheet.getSubimage(0, i * size, size, size).getScaledInstance(newSize, newSize, 0);

		}

		return finishedStrip;

	}

	public double rescale(double origWidth, double origHeight, double newWidth) {
		return (origHeight * newWidth) / origWidth;

	}

	public BufferedImage[][] parse(String lines, BufferedImage orig) {
		ArrayList<BufferedImage[]> arrays = new ArrayList<BufferedImage[]>();

		int oWidth = orig.getWidth();

		String code = removeTrailing(lines);

		String[] sections = code.split(";");

		// begin parsing

		// go thru each section and parse it
		for (String section : sections) {

			System.out.println(section);

			// skip section if it's empty
			if (section.equals("")) {
				continue;

			}

			// prepare the variables
			int start, end;
			ArrayList<Integer> splits = new ArrayList<Integer>();

			start = end = -1;

			// get the args and parse each arg
			String[] args = section.split("-");

			for (String arg : args) {

				System.out.println(arg);

				// skip arg if it's empty
				if (arg.equals("")) {
					continue;

				}

				// go thru a list of possibilities it could be
				if (arg.contains("start")) {
					// the arg is start, parse it.
					start = Integer.valueOf(arg.replace("start", ""));
					continue;

				}

				if (arg.contains("end")) {
					// the arg is end, parse it.
					end = Integer.valueOf(arg.replace("end", ""));
					continue;

				}

				if (arg.contains("cut")) {
					splits.add(Integer.valueOf(arg.replace("cut", "")));
					continue;

				}

				// throw error

			}

			for (int split : splits) {
				System.out.println(split);
			}

			// convert the int "end" to the height of what a section should be.
			end = end - start;

			// make new section array
			BufferedImage[] seca = new BufferedImage[splits.size() + 1];

			// if the person didn't specify any splits, return the whole chunk.
			if (splits.isEmpty()) {
				seca[0] = orig.getSubimage(0, start, oWidth, end);
				arrays.add(seca);
				continue;

			}

			// make a subimage from the left edge to the first cut
			seca[0] = orig.getSubimage(0, start, splits.get(0), end);

			for (int i = 0; i < splits.size(); i++) {

				int split = splits.get(i);

				// make a subimage from the current cut to the right edge, if
				// this cut is the LAST cut.
				if (i + 1 == splits.size()) {
					seca[i + 1] = orig.getSubimage(split, start, oWidth - split, end);
					break;

				}

				// if it isn't the last one, make a subimage from current cut to
				// next cut.
				seca[i + 1] = orig.getSubimage(split, start, Math.abs(splits.get(i + 1) - split), end);

			}

			arrays.add(seca);

		}

		// return the array of images
		return arrays.toArray(new BufferedImage[arrays.size()][]);

	}

	// flatten and remove trailing characters before and after {}s
	private String removeTrailing(String lines) {
		String flat = lines.replaceAll("\n", "").replaceAll(" ", "");
		return flat.substring(flat.indexOf('{') + 1, flat.indexOf('}'));

	}

	public String getLines(String path) throws IOException {
		File file = new File(path);

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		String line = reader.readLine();
		StringBuilder lines = new StringBuilder();

		while (line != null) {
			lines.append(line);
			lines.append("\n");
			line = reader.readLine();

		}

		return lines.toString();

	}

}
