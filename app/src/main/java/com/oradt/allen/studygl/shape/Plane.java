package com.oradt.allen.studygl.shape;

import com.oradt.allen.studygl.shape.Mesh;

/**
 * Created by allen on 18-1-18.
 */

public class Plane extends Mesh {
    public Plane(){
        this(1, 1, 1, 1);
    }

    public Plane(float width, float height) {
        this(width, height, 1, 1);
    }

    public Plane(float width, float height, int widthSegments, int heighSegments) {
        float[] vertices = new float[(widthSegments + 1) * (heighSegments +1 ) * 3];
        short[] indices = new short[(widthSegments + 1) * (heighSegments + 1) * 6];

        float xOffset = width / -2;
        float yOffset = height / -2;
        float xWidth = width / widthSegments;
        float yHeigh = height / heighSegments;
        int currentVertex = 0;
        int currentIndex = 0;
        short w = (short) (widthSegments + 1);
        for (int y = 0; y < heighSegments + 1; y++){
            for (int x = 0; x < widthSegments + 1; x++){
                vertices[currentVertex] = xOffset + x * xWidth;
                vertices[currentVertex + 1] = yOffset + y * yHeigh;
                vertices[currentVertex + 2] = 0;
                currentVertex += 3;

                int n = y * (widthSegments + 1) + x;

                if(y < heighSegments && x < widthSegments){
                    // Face one
                    indices[currentIndex]     = (short) n;
                    indices[currentIndex + 1] = (short) (n + 1);
                    indices[currentIndex + 2] = (short) (n + w);
                    //Face two
                    indices[currentIndex + 3] = (short) (n + 1);
                    indices[currentIndex + 4] = (short) (n + 1 + w);
                    indices[currentIndex + 5] = (short) (n + w);

                    currentIndex += 6;
                }
            }
        }
        setIndices(indices);
        setVertices(vertices);
        setRotate(-75, 0, 0);

        float textureCoordinates[] = { 0.0f, 2.0f,
                2.0f, 2.0f,
                0.0f, 0.0f,
                2.0f, 0.0f,
        };

        setTextureCoordinates(textureCoordinates);
    }
}
