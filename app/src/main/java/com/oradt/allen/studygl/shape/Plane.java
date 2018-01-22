package com.oradt.allen.studygl.shape;

import android.util.Log;

import com.oradt.allen.studygl.shape.Mesh;

import java.util.Random;

/**
 * Created by allen on 18-1-18.
 */

public class Plane extends Mesh {
    public final String TAG = Plane.class.getSimpleName();

    public Plane(){
        this(1, 1, 1, 1);
    }

    public Plane(float width, float height) {
        this(width, height, 1, 1);
    }

    public Plane(float width, float height, int widthSegments, int heighSegments) {
        Random random = new Random(System.currentTimeMillis());
        float[] vertices = new float[(widthSegments + 1) * (heighSegments +1 ) * 3];
        short[] indices = new short[(widthSegments + 1) * (heighSegments + 1) * 6];
        float[] textureCoordinates = new float[(widthSegments + 1) * (heighSegments +1 ) * 2];

        float xOffset = width / -2;
        float yOffset = height / -2;
        float xWidth = width / widthSegments;
        float yHeigh = height / heighSegments;
        int currentVertex = 0;
        int currentIndex = 0;
        int currentTexture = 0;
        short w = (short) (widthSegments + 1);
        float texturey = 1.0f;
        for (int y = 0; y < heighSegments + 1; y++){
            if(texturey > 0.5f){
                texturey = 0.0f;
            }
            else {
                texturey = 1.0f;
            }
            float texturex = 1.0f;
            for (int x = 0; x < widthSegments + 1; x++){
                vertices[currentVertex] = xOffset + x * xWidth;
                vertices[currentVertex + 1] = yOffset + y * yHeigh;

                float prez = y > 0 ? vertices[currentVertex - (widthSegments + 1) * 3 + 2] : 0;
                float lastz = x > 0 ? vertices[currentVertex - 1] : prez;
                float z = (prez + lastz) / 2 + random.nextFloat() - 0.5f;
                if(z > 4){
                    z = 4;
                }
                else if(z < 0){
                    z = 0;
                }
                vertices[currentVertex + 2] = z;

                currentVertex += 3;
                if(texturex > 0.5f){
                    texturex = 0.0f;
                }
                else {
                    texturex = 1.0f;
                }
                textureCoordinates[currentTexture] = texturex;
                textureCoordinates[currentTexture + 1] = texturey;
                currentTexture += 2;


                int n = y * (widthSegments + 1) + x;

                if(y < heighSegments && x < widthSegments){
                    // Face one
                    indices[currentIndex]     = (short) n;
                    indices[currentIndex + 1] = (short) (n + 1);
                    indices[currentIndex + 2] = (short) (n + w);

                    //Face two
                    indices[currentIndex + 3] = (short) (n + 1 + w);
                    indices[currentIndex + 4] = (short) (n + w);
                    indices[currentIndex + 5] = (short) (n + 1);

                    currentIndex += 6;
                }
            }
        }
        setIndices(indices);
        setVertices(vertices);
        setRotate(0, 0, 0);

        setTextureCoordinates(textureCoordinates);
    }
}
