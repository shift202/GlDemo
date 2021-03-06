package com.oradt.allen.studygl.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by allen on 18-1-18.
 */

public class SmoothColoredSquare extends Square {
    // The colors mapped to the vertices.
    private float[] colors = {
            1f, 0f, 0f, 1f, // vertex 0 red
            0f, 1f, 0f, 1f, // vertex 1 green
            0f, 0f, 1f, 1f, // vertex 2 blue
            1f, 0f, 1f, 1f, // vertex 3 magenta
    };

    private FloatBuffer colorBuffer;

    public SmoothColoredSquare(){
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * Float.SIZE / Byte.SIZE);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    public void draw(GL10 gl){
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        // Enable the color array buffer to be
        // used during rendering.
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        // Point out the where the color buffer is.
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

        super.draw(gl);
        // Disable the color buffer.
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
