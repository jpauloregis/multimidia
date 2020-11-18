
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class JEncoder extends Frame {

    private static int Quality;

    private static int BlocSize;

    private Image monImage;

    private int[][] inputArray;

    private int[][] bloc;

    private int[][] blocDCT;

    private int[][] bloc_DCT_Q;

    private int[][] outputArray;

    int width = 320, height = 240;

    private Image image;
    GrafixTools GT;
    DCT dctTrans;
    int locationwidth = 100;
    int locationheight = 10;
    private String fileName;

    public JEncoder(String imageName, int quality, int blocSize) {
        this.Quality = quality;
        this.BlocSize = blocSize;
        this.fileName = imageName;
        image = Toolkit.getDefaultToolkit().getImage(imageName);

        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(image, 0);

        try {
            tracker.waitForID(0);
        } catch (Exception e) {
            return;
        }

        GT = new GrafixTools(image);
        System.out.println("Dimensoes " + GT.imageHeight + "," + GT.imageWidth);
        outputArray = new ImageTools("test.jpg").getRedArray();
        int[] outputArray1D = ImageTools.convertGrayToArray(outputArray);
        ImageTools.write("imgOrgnl.jpg", outputArray1D, 320, 240, BufferedImage.TYPE_BYTE_GRAY, true);
        new PixelArray("Matriz de pixel", outputArray, 320, 240);

        dctTrans = new DCT();

        compressImage();
    }

    public void compressImage() {
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
        int counter = 0;
        int temp1 = 0, temp2 = 0, count = 0;
        int xpos;
        int ypos;
        float dctArray1[][] = new float[BlocSize][BlocSize];
        float[][] dctArray2 = new float[BlocSize][BlocSize];
        double[][] dctArray3 = new double[BlocSize][BlocSize];
        double[][] dctArray4 = new double[BlocSize][BlocSize];
        int reconstImage[][] = new int[width][height];

        for (i = 0; i < width / BlocSize; i++) {
            for (j = 0; j < height / BlocSize; j++) {

                xpos = i * BlocSize;
                ypos = j * BlocSize;

                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {

                        dctArray1[a][b] = (float) outputArray[xpos + a][ypos + b];
                    }
                }

                dctArray2 = dctTrans.fast_fdct(dctArray1);

                dctArray3 = CompressionTools.quantitizeBloc(dctArray2, Quality, BlocSize);

                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
                        reconstImage[xpos + a][ypos + b] = (int) dctArray2[a][b];
                    }
                }

            }
        }
        new PixelArray("", reconstImage, width, height);

        counter = 0;

        for (i = 0; i < width / BlocSize; i++) {
            for (j = 0; j < height / BlocSize; j++) {

                xpos = i * BlocSize;
                ypos = j * BlocSize;

                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {

                        dctArray2[a][b] = reconstImage[xpos + a][ypos + b];
                    }
                }
                int[][] quantum = CompressionTools.initQuantum(Quality, BlocSize);

                dctArray3 = CompressionTools.dequantitizeImage(dctArray2, quantum, BlocSize);

                dctArray4 = dctTrans.fast_idct(dctArray2);

                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {

                        reconstImage[xpos + a][ypos + b] = (int) dctArray4[a][b];
                    }
                }

            }
        }
        new PixelArray(reconstImage, width, height);
        System.out.println();

        makeImage(reconstImage);

        try {
            System.in.read();
        } catch (Exception e) {
        }
        System.exit(0);

    }

    public void makeImage(int[][] image) {
        int i;
        int j;
        int k = 0;

        int one[] = new int[width * height];

        one = GT.convertGrayToArray(image, true);

        System.out.println();
        System.out.println("Conversão Completa");
        ImageWindow iw = new ImageWindow(one);
    }
}
