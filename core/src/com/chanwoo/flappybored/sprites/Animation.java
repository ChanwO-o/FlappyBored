package com.chanwoo.flappybored.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * TODO:
 */
public class Animation {
	private Texture texture;
	private Array<TextureRegion> frames; // store frames
	private float maxFrameTime; // how long a frame has to stay in view
	private float currentFrameTime; // time the animation has been in current frame
	private int frameCount; // number of frames in animation
	private int frame; // current frame

	public Animation(Texture texture, int frameCount, float cycleTime) {
		this.texture = texture;
		TextureRegion region = new TextureRegion(texture);
		frames = new Array<TextureRegion>();
		int frameWidth = region.getRegionWidth() / frameCount;
		for (int i = 0; i < frameCount; ++i)
			frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
		this.frameCount = frameCount;
		maxFrameTime = cycleTime / frameCount;
		frame = 0;
	}

	public void update(float dt) {
		currentFrameTime += dt;
		if (currentFrameTime > maxFrameTime) {
			frame++;
			currentFrameTime = 0;
		}
		if (frame >= frameCount)
			frame = 0;
	}

	public Texture getTexture() {
		return texture;
	}

	public TextureRegion getFrame() {
		return frames.get(frame);
	}

	public void dispose() {
		texture.dispose();
	}
}
