package com.oradt.allen.studygl.shape;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by allen on 18-1-18.
 */

public class Mesh {
    // Our vertex buffer.
    private FloatBuffer mVerticesBuffer = null;

    // Our index buffer.
    private ShortBuffer mIndicesBuffer = null;

    // Our UV texture buffer.
    private FloatBuffer mTextureBuffer = null;

    // The number of indices.
    private int numOfIndices = -1;

    // Our texture id.
    private int mTextureId = -1;

    // The bitmap we want to load as a texture.
    private Bitmap mBitmap;

    // Indicates if we need to load the texture.
    private boolean mShouldLoadTexture = false;

    // Flat Color
    private float[] rgba
            = new float[] { 1.0f, 1.0f, 1.0f, .5f };

    // Smooth Colors
    private FloatBuffer colorBuffer = null;

    // Translate params.
    public float x = 0;

    public float y = 0;

    public float z = 0;

    // Rotate params.
    public float rx = 0;

    public float ry = 0;

    public float rz = 0;

    public void draw(GL10 gl) {
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK);
        // Enabled the vertices buffer for writing and
        //to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format
        //of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
        // Set flat color
        gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
        // Smooth color
        if (colorBuffer != null) {
            // Enable the color array buffer to be
            //used during rendering.
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        }

        if (mShouldLoadTexture) {
            loadGLTexture(gl);
            mShouldLoadTexture = false;
        }

        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            // Enable the texture state
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            // Point to our buffers
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        }

        gl.glTranslatef(x, y, z);
        gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);

        // Point out the where the color buffer is.
        gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices,
                GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);

        if (mTextureId != -1 && mTextureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }

        if (colorBuffer != null){
            // Disable the color buffer
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        }
        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);
    }

    protected void setVertices(float[] vertices) {
        // a float is 4 bytes, therefore
        //we multiply the number if
        // vertices with 4.
        ByteBuffer vbb
                = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVerticesBuffer = vbb.asFloatBuffer();
        mVerticesBuffer.put(vertices);
        mVerticesBuffer.position(0);
    }

    protected void setIndices(short[] indices) {
        // short is 2 bytes, therefore we multiply
        //the number if
        // vertices with 2.
        ByteBuffer ibb
                = ByteBuffer.allocateDirect(indices.length * Short.SIZE / Byte.SIZE);
        ibb.order(ByteOrder.nativeOrder());
        mIndicesBuffer = ibb.asShortBuffer();
        mIndicesBuffer.put(indices);
        mIndicesBuffer.position(0);
        numOfIndices = indices.length;
    }

    protected void setColor(float red, float green,
                            float blue, float alpha) {
        // Setting the flat color.
        rgba[0] = red;
        rgba[1] = green;
        rgba[2] = blue;
        rgba[3] = alpha;
    }

    protected void setColors(float[] colors) {
        // float has 4 bytes.
        ByteBuffer cbb
                = ByteBuffer.allocateDirect(colors.length * Float.SIZE / Byte.SIZE);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    public void setRotate(float x, float y, float z){
        rx = x;
        ry = y;
        rz = z;
    }

    /**
     * Set the texture coordinates.
     *
     * @param textureCoords
     */
    protected void setTextureCoordinates(float[] textureCoords){
        ByteBuffer tbb
                = ByteBuffer.allocateDirect(textureCoords.length * Float.SIZE / Byte.SIZE);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tbb.asFloatBuffer();
        mTextureBuffer.put(textureCoords).position(0);
    }

    /**
     * Set the bitmap to load into a texture.
     *
     * @param bitmap
     */
    public void loadBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        mShouldLoadTexture = true;
    }

    /**
     * Loads the texture.
     *
     * @param gl
     */
    private void loadGLTexture(GL10 gl) {
        // Generate one texture pointer...
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        mTextureId = textures[0];

        // ...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

        // Create Nearest Filtered Texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);

        // Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_REPEAT);

        // Use the Android GLUtils to specify a two-dimensional texture image
        // from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
    }
}
