import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

class ImageWindow extends Frame {

    Image image;
    Image image2;
    int width = 320, height = 240;
    int imageArray_[] = new int[width * height];
    int locationwidth = 100, locationheight = 100;
    int windowwidth = 700, windowheight = 300;
    int image2positionx = 10, image2positiony = 20;
    int imagepositionx = 350, imagepositiony = 20;

    public ImageWindow(int[] imageArray) {
        super("DCT Window");
        imageArray_ = imageArray;
        setLocation(locationwidth, locationheight);
        setSize(windowwidth, windowheight);

        System.out.println("Calculando...");
        image = this.createImage(new MemoryImageSource(width, height, imageArray_, 0, width));

        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(image, 1);
        try {
            tracker.waitForID(1);
        } catch (Exception e) {
            return;
        }

        image2 = Toolkit.getDefaultToolkit().getImage("test.jpg");
        tracker = new MediaTracker(this);
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch (Exception e) {
            return;
        }

        this.show();
    }

    public void paint(Graphics g) {
        g.drawImage(image2, image2positionx, image2positiony, this);
        g.drawImage(image, imagepositionx, imagepositiony, this);
    }
}