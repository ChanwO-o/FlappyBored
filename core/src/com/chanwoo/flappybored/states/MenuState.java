package com.chanwoo.flappybored.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chanwoo.flappybored.FlappyBored;

/**
 * TODO:
 */
public class MenuState extends State {
    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
	    camera.setToOrtho(false, FlappyBored.WIDTH / 2, FlappyBored.HEIGHT / 2);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
	    if (Gdx.input.justTouched()) {
		    gsm.set(new PlayState(gsm));
	    }
    }

    @Override
    public void update(float dt) {
	    handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
	    sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playButton, camera.position.x - playButton.getWidth() / 2,
                camera.position.y);
        sb.end();
    }

	@Override
	public void dispose() {
		background.dispose();
		playButton.dispose();
		System.out.println("MenuState disposed");
	}
}
