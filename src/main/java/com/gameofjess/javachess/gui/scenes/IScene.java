package com.gameofjess.javachess.gui.scenes;

import com.gameofjess.javachess.gui.controller.Controller;

import javafx.scene.Scene;

public interface IScene {

    /**
     * Gets the JavaFX-Scene object.
     *
     * @return JavaFX-Scene
     */
    Scene getFXScene();

    /**
     * Gets the corresponding controller.
     *
     * @return corresponding controller.
     */
    Controller getController();

}
