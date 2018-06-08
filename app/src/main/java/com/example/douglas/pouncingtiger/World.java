package com.example.douglas.pouncingtiger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GameHose.GLGame;
import GameHose.OverlapTester;
import GameHose.Vector2;


/**
 * Created by user on 4/5/2017.
 */

public class World {
    public interface WorldListener {
        public void jump();
        public void highJump();
        public void hit();
        public void coin();
        public void crack();//keeps simulation classes clean from any direct dependencies on rendering and audio playback
    }
    public static GLGame game;
  public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 15 * 20;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final Vector2 gravity = new Vector2(0, -12);
    public final Tiger bob;
    public final List<Platform> platforms;
    public final List<Spring> springs;
    public final List<Squirrel> squirrels;
    public final List<Coin> coins;
    public Castle castle;
    public final WorldListener listener;
    public final Random rand;
    public float heightSoFar;
    public int score;
    public int state;
    public World(WorldListener listener) {
        this.bob = new Tiger(5, 1);
        this.platforms = new ArrayList<Platform>();
        this.springs = new ArrayList<Spring>();
        this.squirrels = new ArrayList<Squirrel>();
        this.coins = new ArrayList<Coin>();
        this.listener = listener;
        rand = new Random();
        generateLevel();
        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }
    private void generateLevel() {//procedural level generation that randomly generates objects for the game
        /*  Start at the bottom of the world at y = 0.
        2. As long as we haven’t reached the top of the world yet, do the following:
        a. Create a platform, either moving or stationary, at the current y position with a
        random x position.
                b. Fetch a random number between 0 and 1 and, if it is greater than 0.9 and if the
        platform is not moving, create a spring on top of the platform.
                c. If we are above the first third of the level, fetch a random number and, if it is
        above 0.8, create a squirrel offset randomly from the platform’s position.
        d. Fetch a random number and, if it is greater than 0.6, create a coin offset randomly
        from the platform’s position.
        e. Increase y by the maximum normal jump height of Tiger, decrease it a tiny bit
        randomly—but only so far that it doesn’t fall below the last y value—and go to the
        beginning of step 2.
        3. Place the castle at the last y position, centered horizontally.*/
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Tiger.BOB_JUMP_VELOCITY * Tiger.BOB_JUMP_VELOCITY
                / (2 * -gravity.y);
        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING
                    : Platform.PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat()
                    * (WORLD_WIDTH - Platform.PLATFORM_WIDTH)
                    + Platform.PLATFORM_WIDTH / 2;
            Platform platform = new Platform(type, x, y);
            platforms.add(platform);
            if (rand.nextFloat() > 0.9f
                    && type != Platform.PLATFORM_TYPE_MOVING) {
                Spring spring = new Spring(platform.position.x,
                        platform.position.y + Platform.PLATFORM_HEIGHT / 2
                                + Spring.SPRING_HEIGHT / 2);
                springs.add(spring);
            }
            if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                Squirrel squirrel = new Squirrel(platform.position.x
                        + rand.nextFloat(), platform.position.y
                        + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() * 2);
                squirrels.add(squirrel);
            }
            if (rand.nextFloat() > 0.6f) {
                Coin coin = new Coin(platform.position.x + rand.nextFloat(),
                        platform.position.y + Coin.COIN_HEIGHT
                                + rand.nextFloat() * 3);
                coins.add(coin);
            }

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }
        castle = new Castle(WORLD_WIDTH / 2, y);
    }
    public void update(float deltaTime, float accelX) {
        updateBob(deltaTime, accelX);
        updatePlatforms(deltaTime);
        updateSquirrels(deltaTime);
        updateCoins(deltaTime);
        if (bob.state != Tiger.BOB_STATE_HIT)
            checkCollisions();
        checkGameOver();
    }
    private void updateBob(float deltaTime, float accelX) {
        if (bob.state != Tiger.BOB_STATE_HIT && bob.position.y <= 0.5f)
            bob.hitPlatform();
        if (bob.state != Tiger.BOB_STATE_HIT)
            bob.velocity.x = -accelX / 10 * Tiger.BOB_MOVE_VELOCITY;
        bob.update(deltaTime);
        heightSoFar = Math.max(bob.position.y, heightSoFar);
    }
    private void updatePlatforms(float deltaTime) {
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING
                    && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
                platforms.remove(platform);
                len = platforms.size();
            }
        }
    }
    private void updateSquirrels(float deltaTime) {
        int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Squirrel squirrel = squirrels.get(i);
            squirrel.update(deltaTime);
        }
    }
    private void updateCoins(float deltaTime) {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            coin.update(deltaTime);}
    }

    private void checkCollisions() {
        checkPlatformCollisions();
        checkSquirrelCollisions();
        checkItemCollisions();
        checkCastleCollisions();
    }
    private void checkPlatformCollisions() {
        if (bob.velocity.y > 0)
            return;
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            if (bob.position.y > platform.position.y) {
                if (OverlapTester
                        .overlapRectangles(bob.bounds, platform.bounds)) {
                    bob.hitPlatform();
                    listener.jump();
                    if (rand.nextFloat() > 0.5f) {
                        platform.pulverize();
                        listener.crack();
                    }
                    break;
                }
            }
        }
    }
    private void checkSquirrelCollisions() {
     int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Squirrel squirrel = squirrels.get(i);
            if (OverlapTester.overlapRectangles(squirrel.bounds, bob.bounds)) {
                bob.hitSquirrel();
                listener.hit();
            }}}
    private void checkItemCollisions() {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            if (OverlapTester.overlapRectangles(bob.bounds, coin.bounds)) {
                coins.remove(coin);
                len = coins.size();
                listener.coin();
                score += Coin.COIN_SCORE+Tiger.lives;
            }
        }

            if (bob.velocity.y > 0)
            return;


        len = springs.size();
        for (int i = 0; i < len; i++) {
            Spring spring = springs.get(i);
            if (bob.position.y > spring.position.y) {
                if (OverlapTester.overlapRectangles(bob.bounds, spring.bounds)) {
                    bob.hitSpring();
                    listener.highJump();
                }
            }
        }
    }
    private void checkCastleCollisions() {
        if (OverlapTester.overlapRectangles(castle.bounds, bob.bounds)) {
            state = WORLD_STATE_NEXT_LEVEL;


        }
    }
    private void checkGameOver() {
        if (heightSoFar - 7.5f > bob.position.y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
}
