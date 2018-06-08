package com.example.douglas.pouncingtiger;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import GameHose.Camera2D;
import GameHose.GLScreen;
import GameHose.Game;
import GameHose.Input;
import GameHose.OverlapTester;
import GameHose.Rectangle;
import GameHose.SpriteBatcher;
import GameHose.Vector2;

/**
 * Created by user on 6/17/2017.
 */

public class SelectaPlayerScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle backBounds,OptionOneBounds,OptionTwoBounds;
    Vector2 touchPoint;
    static String player="atlas";
    static String quipa="p";
    String Option2="Not Available: Buy at 600pts";
    public SelectaPlayerScreen(Game game){
        super(game);
        guiCam = new Camera2D(glGraphics, 320, 480);
        backBounds = new Rectangle(0, 0, 64, 64);
        OptionTwoBounds=new Rectangle(0,128,320,160);
        OptionOneBounds=new Rectangle(0,192,320,224);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 100);
    }
    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if (OverlapTester.pointInRectangle(backBounds, touchPoint)) {
                    game.setScreen(new MainMenu(game));
                    return;
                }
                if (OverlapTester.pointInRectangle(OptionOneBounds, touchPoint)) {
                    player = "atlas";
                    quipa="p";
                    Assets.load(glGame);
                    Assets.reload();
                    game.setScreen(new MainMenu(game));
                    return;
                }
                if (OverlapTester.pointInRectangle(OptionTwoBounds, touchPoint)) {
                    if(Settings.highscores[0]>500){
                    player = "batlas";
                    quipa="b";
                    Assets.load(glGame);
                    Assets.reload();
                    game.setScreen(new MainMenu(game));
                    return;}}}
    }}

    @Override
    public void present(float deltaTime) {
        if(Settings.highscores[0]>500){Option2="The Caped Crusader";}
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
        batcher.endBatch();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        Assets.font.drawText(batcher,"Tiger Father",32,240);
        Assets.font.drawText(batcher,Option2,32,176);
        batcher.drawSprite(32, 32, 64, 64, Assets.arrow);
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
