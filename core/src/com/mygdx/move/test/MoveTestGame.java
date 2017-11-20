package com.mygdx.move.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.move.test.camera.PlayerCamera;
import com.mygdx.move.test.input.PlayerInputProcessor;
import com.mygdx.move.test.player.Player;

public class MoveTestGame extends ApplicationAdapter {

    private AssetManager assets;

    private PlayerCamera camera;
    public PlayerInputProcessor playerInputProcessor;

    private ModelBatch modelBatch;

    private Array<ModelInstance> lstModelInstances;

    private Boolean isWorldCreated = false;
    private GLProfiler profiler;

    private Player player;

    @Override
    public void create() {
        this.assets = new AssetManager();
        this.modelBatch = new ModelBatch();
        this.lstModelInstances = new Array<ModelInstance>();

        loadModels();

        this.profiler = new GLProfiler(Gdx.graphics);
        this.profiler.enable();

    }

    private void loadModels() {
        this.assets.load("model/terrain.g3db", Model.class);
        this.assets.load("model/mario.g3db", Model.class);
        this.assets.load("model/chair.g3db", Model.class);
        this.assets.load("model/fancy_table.g3db", Model.class);
    }

    private void createWorldSpace() {
        ModelInstance terrain = new ModelInstance(this.assets.get("model/terrain.g3db", Model.class));
        terrain.transform.setTranslation(Vector3.Zero);
        this.lstModelInstances.add(terrain);
        ModelInstance playerModel = new ModelInstance(this.assets.get("model/mario.g3db", Model.class));
        playerModel.transform.setTranslation(new Vector3());
        this.lstModelInstances.add(playerModel);
        for(int i =0; i<50; i++) {
            ModelInstance tmpModel;
            if(MathUtils.randomBoolean()) {
                tmpModel = new ModelInstance(this.assets.get("model/chair.g3db", Model.class));
            }else{
                tmpModel = new ModelInstance(this.assets.get("model/fancy_table.g3db", Model.class));
            }
            tmpModel.transform.setTranslation(new Vector3(MathUtils.random(-20,20),0,MathUtils.random(-20,20)));
            this.lstModelInstances.add(tmpModel);
        }

        player = new Player(playerModel,new Vector3(MathUtils.random(-10f, 10f), 0, MathUtils.random(-10f, 10f)));

        setupCamera();


        this.playerInputProcessor = new PlayerInputProcessor(camera,player);
        Gdx.input.setInputProcessor(playerInputProcessor);
        this.isWorldCreated = true;

    }

    private void setupCamera() {
        this.camera = new PlayerCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),player);
        this.camera.position.set(10f, 2f, 10f);
        this.camera.lookAt(0, 0, 0);
        this.camera.near = 1f;
        this.camera.far = 300f;
        this.camera.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (!this.assets.update()) {
            Gdx.app.log("Assets", "Loading Assets: " + this.assets.getProgress() * 100 + "% completed.");
            return;
        }
        if (!this.isWorldCreated) {
            createWorldSpace();
            Gdx.app.log("Assets", "Assets Loaded.");
        }

        this.player.act();
        this.camera.act();

        this.modelBatch.begin(this.camera);
        this.modelBatch.render(lstModelInstances);
        this.modelBatch.end();
        this.camera.update();

        //Gdx.app.log("profiler vertex count",((Float)this.profiler.getVertexCount().total).toString());
        this.profiler.reset();
    }

    @Override
    public void dispose() {
        this.assets.dispose();
        this.modelBatch.dispose();
    }
}
