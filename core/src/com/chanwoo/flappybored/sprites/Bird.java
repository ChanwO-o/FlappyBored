package com.chanwoo.flappybored.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * TODO:
 */
public class Bird {
	private static final int GRAVITY = -15;
	private static final int JUMP_FACTOR = 200;
	private static final int MOVEMENT = 100;
	private Vector3 position;
	private Vector3 velocity;
	private Rectangle bounds;

	private Animation bird;
	private Sound flap;

	public Bird(int x, int y) {
		position = new Vector3(x, y, 0);
		velocity = new Vector3(0, 0, 0);
		Texture texture = new Texture("birdanimation.png");
		bird = new Animation(texture, 3, 0.5f);
		bounds = new Rectangle(x, y, texture.getWidth() / 3, bird.getTexture().getHeight());
		flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	public TextureRegion getTexture() {
		return bird.getFrame();
	}

	public void update(float dt) {
		bird.update(dt);
		if (position.y > 0)
			velocity.add(0, GRAVITY, 0);
		velocity.scl(dt);
		position.add(MOVEMENT * dt, velocity.y, 0);
		position.add(0, velocity.y, 0);
		if (position.y < 0)
			position.y = 0;
		velocity.scl(1 / dt);
		bounds.setPosition(position.x, position.y);
	}

	public void jump() {
		velocity.y = JUMP_FACTOR;
		flap.play(0.5f); // half volume
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void dispose() {
		bird.dispose();
		flap.dispose();
	}
}
