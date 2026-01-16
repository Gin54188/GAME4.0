/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package character;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author yesho
 */
public class SpriteAnimation {

    private BufferedImage spriteSheet;
    private ArrayList<BufferedImage> frames = new ArrayList<>();
    private int currentFrame = 0;  // 当前帧索引

    /**
     * Constructor: loads a sprite sheet and slices it into frames.
     *
     * @param path       File path of the sprite sheet image
     * @param frameWidth Width of a single frame
     * @param frameHeight Height of a single frame
     */
    public SpriteAnimation(String path, int frameWidth, int frameHeight) {

        // Prevent division by zero
        if (frameWidth <= 0 || frameHeight <= 0) {
            throw new IllegalArgumentException(
                "frameWidth and frameHeight must be greater than 0"
            );
        }

        try {
            spriteSheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int sheetWidth = spriteSheet.getWidth();
        int sheetHeight = spriteSheet.getHeight();

        int cols = sheetWidth / frameWidth;
        int rows = sheetHeight / frameHeight;

        if (cols <= 0 || rows <= 0) {
            throw new IllegalStateException("Frame size does not match sprite sheet");
        }

        // Slice sprite sheet into frames
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                BufferedImage frame = spriteSheet.getSubimage(
                        x * frameWidth,
                        y * frameHeight,
                        frameWidth,
                        frameHeight
                );
                frames.add(frame);
            }
        }
    }



    // Move to next frame
    public void nextFrame() {
        currentFrame = (currentFrame + 1) % frames.size();
    }

    // Get current frame image
    public BufferedImage getCurrentFrame() {
        return frames.get(currentFrame);
    }
}
