import java.awt.AWTException;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageTools {
	private static BufferedImage myImage;
	static final int RED_COLOR_MAP = 1;
	static final int GREEN_COLOR_MAP = 2;
	final static int BLUE_COLOR_MAP = 3;
	private static int[][] pixelArray = null;
	private static int imageWidth = 320;
	private static int imageHeight = 240;
	private static int[][] redPixels = null;
	private static int[][] greenPixels = null;
	private static int[][] bluePixels = null;
	private static Image myImageObject = null;

	public ImageTools() {
		super();
	}

	public ImageTools(String fileName) {
		super();
		File imageFile;
		try {
			imageFile = new File(fileName);
			myImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo");
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		myImageObject = Toolkit.getDefaultToolkit().getImage("test.jpg");

		MediaTracker tracker = new MediaTracker(null);
		tracker.addImage(myImageObject, 0);
		System.out.println("Initializando");
		try {
			System.out.println("Esperando...");
			tracker.waitForID(0);
		} catch (Exception e) {
			return;
		}
		System.out.println("Imagem carregada");
	}

	public ImageTools(BufferedImage image) {
		this.myImage = image;
		this.imageWidth = image.getWidth();
		this.imageHeight = image.getHeight();
	}

	public static int getImageWidth() {
		return myImage.getWidth();
	}

	public static int getImageHeight() {
		return myImage.getHeight();
	}

	public static int[][] getArrayFromImage(File imageFile) {
		BufferedImage myImage = null;
		int[][] array = null;
		try {
			myImage = ImageIO.read(imageFile);
			imageHeight = myImage.getHeight();
			imageWidth = myImage.getWidth();
			array = new int[imageWidth][imageHeight];
			for (int i = 0; i < imageWidth; i++) {
				for (int j = 0; j < imageHeight; j++) {
					array[i][j] = myImage.getRGB(i, j);
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			System.err.println("Erro na leitura do arquivo");
		}
		return array;
	}

	public static Image writeImageFromArray(int[][] pixelArray, String fileName) {
		BufferedImage myReconstImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				myReconstImage.setRGB(x, y, pixelArray[x][y]);
			}
		}
		File outputfile = new File(fileName);
		ImageIO.write(myReconstImage, "jpg", outputfile);
	}catch(

	Exception ee)
	{
		ee.printStackTrace();
	}return myReconstImage;
	}

	public static int[][] getSingleColorMapArray(File imageFile, int colorMap) {
		int[][] array = null;
		try {
			BufferedImage myImage = ImageIO.read(imageFile);
			imageHeight = myImage.getHeight();
			imageWidth = myImage.getWidth();
			array = new int[imageWidth][imageHeight];
			redPixels = new int[imageWidth][imageHeight];
			greenPixels = new int[imageWidth][imageHeight];
			bluePixels = new int[imageWidth][imageHeight];
			for (int i = 0; i < imageWidth; i++) {
				for (int j = 0; j < imageHeight; j++) {
					array[i][j] = myImage.getRGB(i, j);
					redPixels[i][j] = getRed(array[i][j]);
					greenPixels[i][j] = getGreen(array[i][j]);
					bluePixels[i][j] = getBlue(array[i][j]);
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		if (colorMap == 1)
			return redPixels;
		else if (colorMap == 2)
			return greenPixels;
		else if (colorMap == 3)
			return bluePixels;
		else
			return null;
	}

	public static int[][] getFullColorMatrix(File imageFile) {
		BufferedImage image = null;
		int[][] fullPixelArray = null;
		try {
			image = ImageIO.read(imageFile);
			int w = image.getWidth();
			int h = image.getHeight();
			redPixels = getSingleColorMapArray(imageFile, RED_COLOR_MAP);
			greenPixels = getSingleColorMapArray(imageFile, GREEN_COLOR_MAP);
			bluePixels = getSingleColorMapArray(imageFile, BLUE_COLOR_MAP);
			fullPixelArray = new int[w][h];
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {

					fullPixelArray[i][3 * j] = redPixels[i][j];

					fullPixelArray[i][3 * j + 1] = bluePixels[i][j];
					fullPixelArray[i][3 * j + 2] = greenPixels[i][j];
				}
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
			System.err.println("Erro na leitura do arquivo");
		}
		return fullPixelArray;
	}

	public static String[][] getHexArrayFromImage(File imageFile) {
		BufferedImage myImage = null;
		String[][] pixelHexArray = null;
		try {
			myImage = ImageIO.read(imageFile);
			imageHeight = myImage.getHeight();
			imageWidth = myImage.getWidth();
			pixelHexArray = new String[imageWidth][imageHeight];
			for (int i = 0; i < imageWidth; i++) {
				for (int j = 0; j < imageHeight; j++) {
					pixelHexArray[i][j] = "#" + Integer.toHexString(myImage.getRGB(i, j)).substring(2);
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			System.err.println("Erro na leitura do arquivo");
		}
		return pixelHexArray;
	}

	public Image writeImageFrom3ColorMap(int[][] redColorMap, int[][] greenColorMap, int[][] blueColorMap,
			File fileName) {

		return null;

	}

	public static double[] read(String fileName) {
		double[] pixelArrayDouble = null;
		int w, h;
		int index = 0;
		try {
			File imageFile = new File(fileName);
			BufferedImage image = ImageIO.read(imageFile);

			w = image.getWidth();
			h = image.getHeight();
			pixelArrayDouble = new double[w * h];
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					pixelArrayDouble[index] = image.getRGB(i, j);
					index++;
				}
			}

		} catch (IOException e) {
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return pixelArrayDouble;
	}

	public static int[] read(String fileName, Object objetNull) {
		int[] pixelArrayInt = null;
		int w, h;
		int index = 0;
		try {
			File imageFile = new File(fileName);
			BufferedImage image = ImageIO.read(imageFile); //

			w = image.getWidth();
			h = image.getHeight();
			pixelArrayInt = new int[w * h];
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					pixelArrayInt[index] = image.getRGB(i, j);
					index++;
				}
			}

		} catch (IOException e) {
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo", "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Erro na leitura do arquivo");
			e.printStackTrace();
		}
		return pixelArrayInt;
	}

	public static void write(String imageName, int[] pixel1DArray, int width, int height, int imageType,
			boolean rgbFormat) {
		BufferedImage myReconstImage = new BufferedImage(width, height, imageType);
		int index = 0;
		int[][] array = new int[width][height];
		try {
			if (rgbFormat) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						array[x][y] = pixel1DArray[index];
						myReconstImage.setRGB(x, y, array[x][y]);
						index++;
					}
				}
			}
			File outputfile = new File(imageName);
			ImageIO.write(myReconstImage, "jpg", outputfile);
		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}

	private static int[][] convert1DTo2DArray(int[] pixel1DArray, int w, int h) {
		int index = 0;
		int[][] array = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				array[i][j] = pixel1DArray[index];
				index++;
			}
		}
		return array;
	}

	public static int getRed(int pixel) {
		return ((pixel >> 16) & 0xff);
	}

	public static int getGreen(int pixel) {
		return ((pixel >> 8) & 0xff);
	}

	public static int getBlue(int pixel) {
		return (pixel & 0xff);
	}

	public static int getAlpha(int pixel) {
		return (pixel >>> 24) & 0xff;
	}

	public static int[] convertGrayToArray(int[][] R) {
		int array[] = new int[imageWidth * imageHeight];

		int x = 0;
		int y = 0;
		int index = 0;

		int G[][] = new int[imageWidth][imageHeight];
		int B[][] = new int[imageWidth][imageHeight];
		int alpha[][] = new int[imageWidth][imageHeight];

		G = R;
		B = R;
		alpha = R;

		for (x = 0; x < imageWidth; x++) {
			for (y = 0; y < imageHeight; y++) {
				array[index] = -16777216 | (R[x][y] << 16) | (G[x][y] << 8) | B[x][y];
				index++;
			}
		}

		return array;
	}

	static void display2DArray(int[][] matrix, int imageWidth, int imageHeight) {
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				System.out.print(" | " + matrix[i][j]);
			}
			System.out.println();
		}
	}

	public int[][] getRedArray() {
		int values[] = new int[imageWidth * imageHeight];
		PixelGrabber grabber = new PixelGrabber(myImageObject.getSource(), 0, 0, imageWidth, imageHeight, values, 0,
				imageWidth);

		try {
			if (grabber.grabPixels() != true) {
				try {
					throw new AWTException("Grabber returned false: " + grabber.status());
				} catch (Exception e) {
				}
				;
			}
		} catch (InterruptedException e) {
		}
		;

		int r[][] = new int[imageWidth][imageHeight];

		int index = 0;
		for (int y = 0; y < imageHeight; ++y) {
			for (int x = 0; x < imageWidth; ++x) {
				r[x][y] = ((values[index] & 0x00ff0000) >> 16);
				index++;
			}
		}

		return r;
	}

	public int[][] getGreenArray() {
		int values[] = new int[imageWidth * imageHeight];
		PixelGrabber grabber = new PixelGrabber(myImageObject.getSource(), 0, 0, imageWidth, imageHeight, values, 0,
				imageWidth);

		try {
			if (grabber.grabPixels() != true) {
				try {
					throw new AWTException("Grabber returned false: " + grabber.status());
				} catch (Exception e) {
				}
				;
			}
		} catch (InterruptedException e) {
		}
		;

		int g[][] = new int[imageWidth][imageHeight];

		int index = 0;
		for (int y = 0; y < imageHeight; ++y) {
			for (int x = 0; x < imageWidth; ++x) {
				g[x][y] = ((values[index] & 0x0000ff00) >> 8);
				++index;
			}
		}

		return g;
	}

	public int[][] getBlueArray() {
		int values[] = new int[imageWidth * imageHeight];
		PixelGrabber grabber = new PixelGrabber(myImageObject.getSource(), 0, 0, imageWidth, imageHeight, values, 0,
				imageWidth);

		try {
			if (grabber.grabPixels() != true) {
				try {
					throw new AWTException("Grabber returned false: " + grabber.status());
				} catch (Exception e) {
				}
				;
			}
		} catch (InterruptedException e) {
		}
		;

		int b[][] = new int[imageWidth][imageHeight];

		int index = 0;
		for (int y = 0; y < imageHeight; ++y) {
			for (int x = 0; x < imageWidth; ++x) {
				b[x][y] = (values[index] & 0x000000ff);
				++index;
			}
		}

		return b;
	}
}