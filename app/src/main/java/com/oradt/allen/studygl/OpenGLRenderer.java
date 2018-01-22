package com.oradt.allen.studygl;

import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.oradt.allen.studygl.shape.Cube;
import com.oradt.allen.studygl.shape.Mesh;
import com.oradt.allen.studygl.shape.Plane;
import com.oradt.allen.studygl.shape.SmoothColoredSquare;
import com.oradt.allen.studygl.shape.Square;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by allen on 18-1-17.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private Square square;
    private float angle;
    private Mesh mesh;
    private float rx;
    private float ry;
    private float rz;

    public OpenGLRenderer(){
        super();
        angle = 0;
        square = new SmoothColoredSquare();
        mesh =  new Plane(20,20,80,80);
        mesh.loadBitmap(BitmapFactory.decodeResource(TutorialPartI.getInstance().getResources(), R.drawable.robot));
        rx = -75.0f;
        ry = 0.0f;
        rz = 0.0f;
    }

    public void setRotate(float x, float y, float z){
        rx += x;
        ry += y;
        rz += z;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();

        drawPlane(gl);
    }

    private void drawCube(GL10 gl){
        // Translates 15 units into the screen.
        gl.glTranslatef(0, 0, -4);

        mesh.draw(gl);
        mesh.setRotate(45, angle, 0);
        angle--;
    }

    private void drawPlane(GL10 gl){
        // Translates 15 units into the screen.
        gl.glTranslatef(0, 0, -24);

        mesh.draw(gl);
        mesh.setRotate(rx, ry, rz);
    }

    private void drawABC(GL10 gl){
        // Translates 15 units into the screen.
        gl.glTranslatef(0, 0, -15);


        // SQUARE A
        // Save the current matrix.
        gl.glPushMatrix();
        // Rotate square A counter-clockwise.
        gl.glRotatef(angle, 0, 0, 1);
        // Draw square A.
        square.draw(gl);
        // Restore the last matrix.
        gl.glPopMatrix();

        // SQUARE B
        // Save the current matrix.
        gl.glPushMatrix();
        // Rotate square B before moving it,
        //making it rotate around A.
        gl.glRotatef(-angle, 0, 0, 1);
        // Move square B.
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of square A
        gl.glScalef(0.5f, 0.5f, 0.5f);
        // Draw square B.
        square.draw(gl);

        // SQUARE C
        // Save the current matrix
        gl.glPushMatrix();
        // Make the rotation around B
        gl.glRotatef(-angle, 0, 0, 1);
        // Move square C.
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of square B
        gl.glScalef(.5f, .5f, .5f);
        // Rotate around it's own center.
        gl.glRotatef(angle*10, 0, 0, 1);
        // Draw square C.
        square.draw(gl);
        // Restore to the matrix as it was before C.
        gl.glPopMatrix();

        // Restore to the matrix as it was before B.
        gl.glPopMatrix();

        angle++;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }
}
