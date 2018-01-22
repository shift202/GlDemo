package com.oradt.allen.studygl;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class TutorialPartI extends AppCompatActivity {
    private static TutorialPartI instance;
    private OpenGLRenderer mOpenGLRenderer = null;
    private float downX = 0;
    private float downY = 0;
    private float currentX = 0;
    private float currentY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        instance = this;
        GLSurfaceView view = new GLSurfaceView(this);
        mOpenGLRenderer = new OpenGLRenderer();
        view.setRenderer(mOpenGLRenderer);
        setContentView(view);
    }

    public static TutorialPartI getInstance(){
        return instance;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            downX = event.getX();
            downY = event.getY();
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            currentX = event.getX();
            currentY = event.getY();
            if(mOpenGLRenderer != null){
                mOpenGLRenderer.setRotate(currentY - downY, currentX - downX, 0);
            }
            downX = currentX;
            downY = currentY;
        }
        return super.onTouchEvent(event);
    }
}
