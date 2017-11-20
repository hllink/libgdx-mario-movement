package com.mygdx.move.test.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Player {

    private ModelInstance modelInstance;
    private Vector3 position;
    private Quaternion rotation;
    private Float moveForce = 0f;


    public Player(ModelInstance modelInstance, Vector3 position) {
        this.modelInstance = modelInstance;
        this.position = position;
        this.rotation = new Quaternion();
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void act(){
        this.modelInstance.transform.set(position,rotation);
        if(moveForce != 0f) {
            this.modelInstance.transform.translate(moveForce * Gdx.graphics.getDeltaTime(), 0, 0);
        }
        this.modelInstance.transform.getTranslation(this.position);
    }

    public void applyForce(Float force, Float angle){
        this.rotation.setEulerAngles(angle,0,0);
        this.moveForce = force;
    }
}
