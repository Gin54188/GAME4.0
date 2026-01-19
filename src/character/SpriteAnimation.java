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
 * SpriteAnimation class
 * Handles 2D sprite animations by slicing a sprite sheet into individual frames.
 * Can cycle through frames, reset, or select a specific frame.
 * 
 * Author: google
 * link: https://www.google.com/search?q=Java+sprite+sheet+animation+BufferedImage&rlz=1C1HKFL_enCA1193CA1194&oq=Java+sprite+sheet+animation+BufferedImage&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIHCAEQIRiPAjIHCAIQIRiPAtIBBzk2NmowajeoAgCwAgA&sourceid=chrome&ie=UTF-8&safe=active&ssui=on
 */
public class SpriteAnimation {

    // ===== Fields =====
    private BufferedImage spriteSheet;          // The full sprite sheet image
    private ArrayList<BufferedImage> frames = new ArrayList<>(); // List of individual frames
    private int currentFrame = 0;               // Index of the current frame being displayed

    /**
     * Constructor: Loads a sprite sheet from file and slices it into frames.
     *
     * @param path        File path of the sprite sheet image
     * @param frameWidth  Width of a single frame in pixels
     * @param frameHeight Height of a single frame in pixels
     */
    public SpriteAnimation(String path, int frameWidth, int frameHeight) {

        // Validate frame dimensions
        if (frameWidth <= 0 || frameHeight <= 0) {
            throw new IllegalArgumentException(
                "frameWidth and frameHeight must be greater than 0"
            );
        }

        // Load the sprite sheet image from file
        try {
            spriteSheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Calculate number of columns and rows of frames
        int sheetWidth = spriteSheet.getWidth();
        int sheetHeight = spriteSheet.getHeight();
        int cols = sheetWidth / frameWidth;
        int rows = sheetHeight / frameHeight;

        // Check if the sprite sheet matches the given frame dimensions
        if (cols <= 0 || rows <= 0) {
            throw new IllegalStateException("Frame size does not match sprite sheet");
        }

        // Slice sprite sheet into individual frames and store in frames list
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                BufferedImage frame = spriteSheet.getSubimage(
                        x * frameWidth,   // X coordinate of top-left corner
                        y * frameHeight,  // Y coordinate of top-left corner
                        frameWidth,    // Frame width
                        frameHeight   // Frame height
                );
                frames.add(frame);
            }
        }
    }

    //Animation Methods 

    /** Move to the next frame in the animation, looping back to the first frame */
    public void nextFrame() {
        currentFrame = (currentFrame + 1) % frames.size();
    }

    /** Get the current frame image to draw */
    public BufferedImage getCurrentFrame() {
        return frames.get(currentFrame);
    }

    /** Reset animation to the first frame */
    public void reset() {
        currentFrame = 0;
    }

    /**
     * Set animation to a specific frame index
     * @param index Index of the frame to display
     */
    public void setFrame(int index) {
        if(index >= 0 && index < frames.size()) currentFrame = index;
    }

    /** Get total number of frames in the animation */
    public int getFrameCount() {
        return frames.size();
    }
}
