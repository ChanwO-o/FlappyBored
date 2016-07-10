package com.chanwoo.flappybored.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.chanwoo.flappybored.FlappyBored;
import com.chanwoo.flappybored.sprites.Bird;
import com.chanwoo.flappybored.sprites.Tube;

/**
 * TODO:
 */
public class PlayState extends State {

	private static final int TUBE_SPACING = 125;
	private static final int TUBE_COUNT = 4;
	private static final int GROUND_Y_OFFSET = -50;

	private Bird bird;
	private Texture bg, ground;
	private Vector2 groundPos1, groundPos2;
	private Array<Tube> tubes;

	protected PlayState(GameStateManager gsm) {
		super(gsm);
		bird = new Bird(50, 300);
		camera.setToOrtho(false, FlappyBored.WIDTH / 2, FlappyBored.HEIGHT / 2);
		bg = new Texture("bg.png");
		ground = new Texture("ground.png");
		groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
		groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
		tubes = new Array<Tube>();

		for (int i = 1; i <= TUBE_COUNT; ++i) {
			tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
		}
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched())
			bird.jump();
	}

	@Override
	public void update(float dt) {
		handleInput();
		updateGround();
		bird.update(dt);
		camera.position.x = bird.getPosition().x + 80;
		float screenStartX = camera.position.x - camera.viewportWidth / 2;
		for (Tube tube : tubes) {
			if (tube.collides(bird.getBounds()) || // bird hits tube
					bird.getPosition().y <= GROUND_Y_OFFSET + ground.getHeight()) { // bird hits ground
				gsm.set(new PlayState(gsm));
				break;
			}
			if (screenStartX > tube.getPosTopTube().x + tube.getTopTube().getWidth()) // tube is left of screen
				tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
			if (bird.getPosition().y > camera.viewportHeight - bird.getTexture().getRegionHeight())
				bird.setPosition(bird.getPosition().x, camera.viewportHeight - bird.getTexture().getRegionHeight());
		}
		camera.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
		sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
		for (Tube tube : tubes) {
			sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
			sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
		}
		sb.draw(ground, groundPos1.x, groundPos1.y);
		sb.draw(ground, groundPos2.x, groundPos2.y);
		sb.end();
	}

	@Override
	public void dispose() {
		bg.dispose();
		bird.dispose();
		for (Tube tube : tubes)
			tube.dispose();
		ground.dispose();
		System.out.println("PlayState disposed");
	}

	private void updateGround() {
		if ((camera.position.x - camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
			groundPos1.add(ground.getWidth() * 2, 0); //.x += ground.getWidth() * 2;
		if ((camera.position.x - camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
			groundPos2.add(ground.getWidth() * 2, 0); //x += ground.getWidth() * 2;
	}
}
