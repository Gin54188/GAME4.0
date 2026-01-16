/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package character;

/**
 *
 * @author yesho
 */
public abstract class Character {

    protected int health;

    public Character(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
