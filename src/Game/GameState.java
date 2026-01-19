/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 * GameState class
 * Manages all the different states of the game and provides utility methods to check or change the state.
 * This is useful for switching between menu, playing, story, pause, and end screens.
 * 
 * Author: jingzhen
 */
public class GameState {

    // State constants 
    public static final int MENU = 0;      // Main menu state
    public static final int PLAYING = 1;      // Player is actively playing the game
    public static final int END = 2;           // Game over state
    public static final int STORY = 3;      // Story/Cutscene state (reserved for future)
    public static final int PAUSE = 4;      // Pause state (reserved for future)
    public static final int POST_LEVEL_STORY = 5;     // Story that occurs after a level is completed

    
    //Current state 
    public static int currentState = MENU;        // Tracks the current state of the game (default: MENU)

    // Change the current state
    public static void setState(int state) {
        currentState = state;           // Update the current state to the given value
    }

    //Convenience methods to check the current state 
    // Using these methods makes code easier to read instead of writing comparisons everywhere

    public static boolean isMenu() {
        return currentState == MENU;        // Returns true if the game is in the menu state
    }

    public static boolean isPlaying() {
        return currentState == PLAYING;       // Returns true if the game is currently being played
    }

    public static boolean isEnd() {
        return currentState == END;          // Returns true if the game is in the end state
    }

    public static boolean isStory() {
        return currentState == STORY;      // Returns true if the game is showing a story/cutscene
    }

    public static boolean isPaused() {
        return currentState == PAUSE;      // Returns true if the game is paused
    }
    
    public static boolean isPostLevelStory() {
        return currentState == POST_LEVEL_STORY;    // Returns true if the game is showing the story after a level
    }
}