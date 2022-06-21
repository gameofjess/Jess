package com.gameofjess.javachess.gui.scenes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import com.gameofjess.javachess.gui.controller.GameController;
import com.gameofjess.javachess.gui.controller.HostMenuController;
import com.gameofjess.javachess.gui.controller.JoinMenuController;

@ExtendWith(ApplicationExtension.class)
public class SceneFactoryTest {

    /**
     * Tests the instantiation of a JoinScene.
     */
    @Test
    void joinSceneTest() throws IOException {
        SceneFactory sceneFactory = new SceneFactory(SceneType.JOIN);
        assertTrue(sceneFactory.getScene() instanceof JoinScene);
        assertTrue(sceneFactory.getController() instanceof JoinMenuController);
        assertNotNull(sceneFactory.getScene().getFXScene());
    }

    /**
     * Tests the instantiation of a HostScene.
     */
    @Test
    void hostSceneTest() throws IOException {
        SceneFactory sceneFactory = new SceneFactory(SceneType.HOST);
        assertTrue(sceneFactory.getScene() instanceof HostScene);
        assertTrue(sceneFactory.getController() instanceof HostMenuController);
        assertNotNull(sceneFactory.getScene().getFXScene());
    }

    /**
     * Tests the instantiation of a GameScene.
     */
    @Test
    void gameSceneTest() throws IOException {
        SceneFactory sceneFactory = new SceneFactory(SceneType.GAME);
        assertTrue(sceneFactory.getScene() instanceof GameScene);
        assertTrue(sceneFactory.getController() instanceof GameController);
        assertNotNull(sceneFactory.getScene().getFXScene());
    }

}
