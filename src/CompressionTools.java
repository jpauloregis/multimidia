public class CompressionTools {
    private int imageWidth, imageHeight;

    public CompressionTools(int width, int height) {
        this.imageWidth = width;
        this.imageHeight = height;
    }

    public static double[][] quantitizeBloc(float[][] input, int quality, int N) {
        int i;
        int j;
        float q;
        double[][] quantum = new double[N][N];
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                q = input[i][j] / (1 + ((1 + i + j) * quality));
                quantum[i][j] = q;
            }
        }
        return quantum;
    }

    public static int[] compressImage(int[] QDCT, int imageWidth, int imageHeight) {
        int i = 0;
        int j = 0;
        int k = 0;
        int temp = 0;
        int curPos = 0;
        int runCounter = 0;
        int imageLength = imageWidth * imageHeight;

        int pixel[] = new int[imageWidth * imageHeight];

        while ((i < imageLength)) {
            temp = QDCT[i];

            while ((i < imageLength) && (temp == QDCT[i])) {
                runCounter++;
                i++;
            }

            if (runCounter > 4) {
                pixel[j] = 255;
                j++;
                pixel[j] = temp;
                j++;
                pixel[j] = runCounter;
                j++;
            } else {
                for (k = 0; k < runCounter; k++) {
                    pixel[j] = temp;
                    j++;
                }
            }

            runCounter = 0;
        }

        return pixel;
    }

    public static int[] decompressImage(int[] DCT, int width, int height) {
        int i = 0;
        int j = 0;
        int k = 0;
        int temp = 0;
        int imageLength = width * height;
        System.out.println("largura*altura=" + width * height);
        int pixel[] = new int[width * height];

        while (i < imageLength) {
            temp = DCT[i];

            if (k < imageLength) {
                if (temp == 255) {
                    i++;
                    int value = DCT[i];
                    i++;
                    int length = DCT[i];

                    for (j = 0; j < length; j++) {
                        pixel[k] = value;
                        k++;
                        System.out.println("k=" + k);
                    }
                }

                else {
                    pixel[k] = temp;
                    k++;
                }
            }
            i++;
        }

        for (int a = 0; a < 80; a++) {
            System.out.print(pixel[a] + " ");
        }
        System.out.println();
        for (int a = 0; a < 80; a++) {
            System.out.print(DCT[a] + " ");
        }

        return pixel;
    }

    public static double[][] dequantitizeImage(float[][] dctArray2, int[][] quantum, int N) {
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int row;
        int col;
        double[][] outputData = new double[N][N];

        double result;

        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                result = dctArray2[i][j] * quantum[i][j];
                outputData[i][j] = (int) (Math.round(result));
            }
        }

        return outputData;
    }

    public static int[][] initQuantum(int quality, int blocSize) {
        int[][] q = new int[blocSize][blocSize];
        int i;
        int j;
        for (i = 0; i < blocSize; i++) {
            for (j = 0; j < blocSize; j++) {
                q[i][j] = 1 + ((1 + i + j) * quality);
            }
        }
        return q;
    }
}