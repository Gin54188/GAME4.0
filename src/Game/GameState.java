/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 *
 * @author yesho
 */
public class GameState {

    // ===== 状态常量 =====
    public static final int MENU = 0;      // 主菜单
    public static final int PLAYING = 1;   // 游戏中
    public static final int END = 2;        // 游戏结束
    public static final int STORY = 3;      // 剧情（新功能预留）
    public static final int PAUSE = 4;      // 暂停（新功能预留）

    // ===== 当前状态 =====
    public static int currentState = MENU;

    // ===== 切换状态 =====
    public static void setState(int state) {
        currentState = state;
    }

    // ===== 判断方法（写代码更舒服）=====
    public static boolean isMenu() {
        return currentState == MENU;
    }

    public static boolean isPlaying() {
        return currentState == PLAYING;
    }

    public static boolean isEnd() {
        return currentState == END;
    }

    public static boolean isStory() {
        return currentState == STORY;
    }

    public static boolean isPaused() {
        return currentState == PAUSE;
    }
}
