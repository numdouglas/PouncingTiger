package GameHose;

/**
 * Created by user on 4/3/2017.
 */

public class Animation {
    public static final int ANIMATION_LOOPING = 0;
    public static final int ANIMATION_NONLOOPING = 1;
    final TextureRegion[] keyFrames;
    final float frameDuration;
    public Animation(float frameDuration, TextureRegion... keyFrames) {
        this.frameDuration = frameDuration;
        this.keyFrames = keyFrames;
    }
    public TextureRegion getKeyFrame(float stateTime, int mode) {
        int frameNumber = (int)(stateTime / frameDuration);//calculate the number of frames that shouldve been played by the current time based on statetime
        if(mode == ANIMATION_NONLOOPING) {
            frameNumber = Math.min(keyFrames.length-1, frameNumber);//clamp the the frameNumber to the last member in the frame array;
        } else {
            frameNumber = frameNumber % keyFrames.length;//take modulus that will create the looping effect
        }
        return keyFrames[frameNumber];
    }
}