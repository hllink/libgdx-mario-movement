package com.mygdx.move.test.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.move.test.camera.PlayerCamera;
import com.mygdx.move.test.player.Player;

public class PlayerInputProcessor implements InputProcessor {

    private PlayerCamera camera;
    private Player player;

    private Boolean hasInput;
    private Float angle;

    public PlayerInputProcessor(PlayerCamera camera, Player player) {
        this.camera = camera;
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        hasInput = false;
        switch (keycode) {
            case Input.Keys.W:
                angle = camera.getAngleToPlayer() + 180f;
                hasInput = true;
                break;
            case Input.Keys.A:
                angle = camera.getAngleToPlayer() - 90f;
                hasInput = true;
                break;
            case Input.Keys.S:
                angle = camera.getAngleToPlayer();
                hasInput = true;
                break;
            case Input.Keys.D:
                angle = camera.getAngleToPlayer() + 90f;
                hasInput = true;
                break;
            case Input.Keys.Q:
                this.camera.addPlayerAngle(1f);
                break;
            case Input.Keys.E:
                this.camera.addPlayerAngle(-1f);
                break;
        }

        if (hasInput) {
            player.applyForce(2f, angle);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                player.applyForce(0f, angle);
                break;
            case Input.Keys.A:
                player.applyForce(0f, angle);
                break;
            case Input.Keys.S:
                player.applyForce(0f, angle);
                break;
            case Input.Keys.D:
                player.applyForce(0f, angle);
                break;
            case Input.Keys.Q:
                this.camera.addPlayerAngle(0f);
                break;
            case Input.Keys.E:
                this.camera.addPlayerAngle(0f);
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
