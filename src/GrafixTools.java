import java.lang.*;
import java.awt.*;
import java.net.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class GrafixTools {

    public Image imageobj;

    public int imageHeight;

    public int imageWidth;

    public GrafixTools(Image image) {
        imageobj = image;
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }

    public int[][] getRedArray() {
        int values[] = new int[imageWidth * imageHeight];
        PixelGrabber grabber = new PixelGrabber(imageobj.getSource(), 0, 0, imageWidth, imageHeight, values, 0,
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
        PixelGrabber grabber = new PixelGrabber(imageobj.getSource(), 0, 0, imageWidth, imageHeight, values, 0,
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
        PixelGrabber grabber = new PixelGrabber(imageobj.getSource(), 0, 0, imageWidth, imageHeight, values, 0,
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

    public int[][] convertRGBtoY(int[][] redArray, int[][] greenArray, int[][] blueArray) {
        int i;
        int j;
        int Y[][] = new int[imageWidth][imageHeight];

        for (i = 0; i < imageWidth; i++) {
            for (j = 0; j < imageHeight; j++) {
                Y[i][j] = (int) ((0.257 * (float) redArray[i][j]) + (0.504 * (float) greenArray[i][j])
                        + (0.098 * (float) blueArray[i][j]) + 16.0);
            }
        }

        return Y;
    }

    public int[][] convertRGBtoCb(int[][] redArray, int[][] greenArray, int[][] blueArray) {
        int i;
        int j;
        int Cb[][] = new int[imageWidth][imageHeight];

        for (i = 0; i < imageWidth; i++) {
            for (j = 0; j < imageHeight; j++) {
                Cb[i][j] = (int) ((-0.148 * (float) redArray[i][j]) - (0.291 * (float) greenArray[i][j])
                        + (0.439 * (float) blueArray[i][j]) + 128.0);
            }
        }

        return Cb;
    }

    public int[][] convertRGBtoCr(int[][] redArray, int[][] greenArray, int[][] blueArray) {
        int i;
        int j;
        int Cr[][] = new int[imageWidth][imageHeight];

        for (i = 0; i < imageWidth; i++) {
            for (j = 0; j < imageHeight; j++) {
                Cr[i][j] = (int) ((0.439 * (float) redArray[i][j]) - (0.368 * (float) greenArray[i][j])
                        - (0.071 * (float) blueArray[i][j]) + 128.0);
            }
        }

        return Cr;
    }

    public int[][] convertYCbCrtoR(int[][] Y, int[][] Cb, int[][] Cr) {
        int i;
        int j;
        int R[][] = new int[imageWidth][imageHeight];

        for (i = 0; i < imageWidth; i++) {
            for (j = 0; i < imageHeight; j++) {
                R[i][j] = (int) (((1.164 * ((float) Y[i][j] - 16.0))) + (1.596 * ((float) Cr[i][j] - 128.0)));
            }
        }

        return R;
    }

    public int[][] convertYCbCrtoG(int[][] Y, int[][] Cb, int[][] Cr) {
        int i;
        int j;
        int G[][] = new int[imageWidth][imageHeight];

        for (i = 0; i < imageWidth; i++) {
            for (j = 0; i < imageHeight; j++) {
                G[i][j] = (int) (((1.164 * ((float) Y[i][j] - 16.0))) - (0.813 * ((float) Cr[i][j] - 128.0))
                        - (0.392 * ((float) Cb[i][j] - 128.0)));
            }
        }

        return G;
    }

    public int[][] convertYCbCrtoB(int[][] Y, int[][] Cb, int[][] Cr) {
        int i;
        int j;
        int B[][] = new int[imageWidth][imageHeight];

        for (i = 0; i < imageWidth; i++) {
            for (j = 0; i < imageHeight; j++) {
                B[i][j] = (int) (((1.164 * ((float) Y[i][j] - 16.0))) + (2.017 * ((float) Cb[i][j] - 128.0)));
            }
        }

        return B;
    }

    public int[] convertRGBtoArray(int[][] R, int[][] G, int[][] B, boolean log) {
        int x = 0;
        int y = 0;
        int index = 0;

        int array[] = new int[imageWidth * imageHeight];

        for (y = 0; y < imageHeight; y++) {
            for (x = 0; x < imageWidth; x++) {
                array[index] = (int) ((B[x][y]));
                array[index] = (int) ((G[x][y] << 8));
                array[index] = (int) ((R[x][y] << 16));
                array[index] = (int) ((0 << 24));
                index++;
            }

            if (log) {
                System.out.print("Blocks: ");
                System.out.print(index + "\r");
            }
        }

        return array;
    }

    public int[] convertGrayToArray(int[][] R, boolean log) {
        int array[] = new int[imageWidth * imageHeight];

        int x = 0;
        int y = 0;
        int index = 0;

        int G[][] = new int[imageWidth][imageHeight];
        int B[][] = new int[imageWidth][imageHeight];

        G = R;
        B = R;

        for (y = 0; y < imageHeight; y++) {
            for (x = 0; x < imageWidth; x++) {
                array[index] = -16777216 | (R[x][y] << 16) | (G[x][y] << 8) | B[x][y];
                index++;
            }

            if (log) {
                System.out.print("Blocks: ");
                System.out.print(index + "\r");
            }
        }

        return array;
    }
}
