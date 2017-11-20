package com.mygdx.move.test.camera;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.move.test.player.Player;

public class PlayerCamera extends PerspectiveCamera {

    private Player player;

    private Float angleToPlayer = 0f;
    private Float angleToAdd = 0f;
    private Float height = 3f;
    private Float distance = 5f;

    private Quaternion rotation;

    public PlayerCamera(float fieldOfViewY, float viewportWidth, float viewportHeight, Player player) {
        super(fieldOfViewY, viewportWidth, viewportHeight);
        this.rotation = new Quaternion();
        this.player = player;

        this.view.getRotation(rotation);
    }

    public void act() {
        if (angleToAdd != 0) {
            this.angleToPlayer += this.angleToAdd;
        }

        this.position.set(distance, height, 0).rotate(Vector3.Y, angleToPlayer).add(player.getPosition());
        up.set(Vector3.Y);
        lookAt(player.getPosition());
        update();
    }

    public void addPlayerAngle(Float angle) {
        this.angleToAdd = angle;
    }

    public Float getAngleToPlayer() {
        return angleToPlayer;
    }
}
