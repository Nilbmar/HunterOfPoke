package com.nilbmar.hunter.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 4/29/17.
 */

public class BodyComponent implements Component {
    private Shape shape;
    private BodyDef bDef;
    private FixtureDef fDef;
    private short categoryBit;
    private short maskBits;

    public BodyComponent() {

    }

    // 1st Stage of Creating the Body
    // Sets Coordinates and Body Type
    public BodyDef getBodyDef(BodyDef.BodyType bodyType, float x, float y) {
        bDef = new BodyDef();

        // Sets Position On Screen
        bDef.position.set(x, y);
        bDef.type = bodyType; //ie. BodyDef.BodyType.DynamicBody

        return bDef;
    }

    // 2nd Stage of Creating the Body - Create FixtureDef
    // for CircleShape - to be overloaded later
    public void setFixtureDef(Shape.Type circle, int radius) {
        fDef = new FixtureDef();
        shape = new CircleShape();
        shape.setRadius(radius / HunterOfPoke.PPM);
    }

    public void setFixtureDef(Shape.Type polygon, float width, float height) {
        fDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / HunterOfPoke.PPM, height / HunterOfPoke.PPM);
        shape = polygonShape;
    }

    // 3rd Stage of Creating the Body
    // categoryBit is what the object is, used for collision
    public void setCategoryBit(short bit) {
        //fDef.filter.categoryBits = bit;
        categoryBit = bit;
    }

    public FixtureDef getFixtureDef() {
        return fDef;
    }

    // 4th Stage of Creating the Body
    // maskBits are what the object can collide with
    // To turn off collisions on the fly, entire body must be redefined
    public void setMaskBits(short bits) {
        maskBits = bits;
    }

    // 5th Stage of Creating the Body
    public void finalizeFixture(boolean isSensor) {
        fDef.filter.categoryBits = categoryBit;
        fDef.filter.maskBits = maskBits;

        fDef.shape = shape;
        fDef.isSensor = isSensor;
    }

    public void dispose() {
        shape.dispose();
    }

    // 6th and Final Stage is done in each object
    // Copy and paste this below all others in createBody()
    // b2Body.createFixture(bodyComponent.getFixtureDef()).setUserData(this);
}
