/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.cinemalab.testtask;

import io.cinemalab.testtask.models.Human;

/**
 *
 * @author knud
 */
public final class HumanHolder {

    private Human human;
    private final static HumanHolder INSTANCE = new HumanHolder();

    private HumanHolder() {
    }

    public static HumanHolder getInstance() {
        return INSTANCE;
    }

    public void setHuman(Human h) {
        this.human = h;
    }

    public Human getHuman() {
        return this.human;
    }
}
