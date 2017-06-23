package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.nilbmar.hunter.Components.BodyComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Components.TimerComponent;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.Direction;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.ItemType;

/**
 * Created by sysgeek on 5/22/17.
 *
 * Abstract Class: Entity
 * Purpose: The class from which in game objects will extend
 * ie. Player, Enemies, Items, Bullets, ...
 */

public abstract class Entity extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Body b2Body;

    // Components
    protected BodyComponent bodyComponent;
    protected MoveComponent moveComponent;
    protected TimerComponent timerComponent;

    protected String regionName;
    protected String name;

    protected EntityType entityType;

    protected float deltaTime; // Used for TimerComponent - set in update()

    protected float startInWorldX;
    protected float startInWorldY;
    protected int baseAcceleration;
    protected int currentAcceleration;
    protected Direction currentDirection;
    protected Direction previousDirection;
    protected Action currentAction;
    protected Action previousAction;


    public Entity(PlayScreen screen, float startInWorldX, float startInWorldY) {
        this.screen = screen;
        world = this.screen.getWorld();

        this.startInWorldX = startInWorldX;
        this.startInWorldY = startInWorldY;

        setPosition(startInWorldX, startInWorldY);

        baseAcceleration = 1;

        name = "Entity That Shan't Be Named";

        // Will need to change make sure pack files
        // have a "default" region, or set in constructor
        // before setFramesComponent()
        regionName = "default";

        bodyComponent = new BodyComponent();
        //defineBody();
    }

    public EntityType getEntityType() { return entityType; }
    public String getName() { return name; }
    protected void setName(String name) {
        this.name = name;
    }
    public abstract float getSpawnOtherX();
    public abstract float getSpawnOtherY();

    public MoveComponent getMoveComponent() { return moveComponent; }
    protected void setTimerComponent(float setTimer, ItemType itemType) {
        timerComponent = new TimerComponent(this, setTimer, itemType, deltaTime);
    }

    // Boosts from items or reset
    public int getBaseAcceleration() { return baseAcceleration; }
    public int getCurrentAcceleration() { return currentAcceleration; }
    public void setCurrentAcceleration(int currentAcceleration) {
        this.currentAcceleration = currentAcceleration;
    }

    public Direction getDirection() { return currentDirection; } // Set in move()
    protected void setDirection(Direction dir) {
        previousDirection = currentDirection;
        currentDirection = dir;
    }

    public Body getB2Body() { return b2Body; }

    protected void createBody(float x, float y) {
        b2Body = world.createBody(bodyComponent.getBodyDef(BodyDef.BodyType.DynamicBody,
                x, y));
    }

    protected void defineShape() {
        //bodyComponent.setFixtureDef(Shape.Type.Circle, 5); // CircleShape - radius of 5
        bodyComponent.setFixtureDef(Shape.Type.Polygon, 4, 4); // PolygonShape - Set as Box
    }

    protected abstract void defineBits(short maskBits);
    protected abstract void defineBody();

    protected void finalizeBody() {
        // false = is the fixture a sensor
        bodyComponent.finalizeFixture(false);
        b2Body.createFixture(bodyComponent.getFixtureDef()).setUserData(this);

        bodyComponent.dispose();
    }

    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        setPosition(b2Body.getPosition().x - getWidth() / 2,
                b2Body.getPosition().y - getHeight() / 2);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}
