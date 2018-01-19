package com.oradt.allen.studygl.shape;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by allen on 18-1-18.
 */

public class FlatColoredSquare extends Square {
    public void draw(GL10 gl){
        gl.glColor4f(.5f, .5f, 1.0f, 1.0f);
        super.draw(gl);
    }
}
