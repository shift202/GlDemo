package com.oradt.allen.studygl.shape;

/**
 * Created by allen on 18-1-18.
 */

public class Cube extends Mesh {

    public Cube(float width, float height, float depth){
        width /= 2;
        height /= 2;
        depth /= 2;

        float vertices[] = {
                -width, -height, -depth, // 0
                width, -height, -depth, // 1
                width,  height, -depth, // 2
                -width,  height, -depth, // 3
                -width, -height,  depth, // 4
                width, -height,  depth, // 5
                width,  height,  depth, // 6
                -width,  height,  depth, // 7
        };

        short indices[] = {
                0, 5, 4,
                0, 1, 5,
                1, 6, 5,
                1, 2, 6,
                2, 7, 6,
                2, 3, 7,
                3, 4, 7,
                3, 0, 4,
                4, 6, 7,
                4, 5, 6,
                3, 1, 0,
                3, 2, 1,
        };

        float colors[] = {
                1f, 0f, 0f, 1f, // vertex 0 red
                1f, 0f, 0f, 1f, // vertex 1 red
                0f, 1f, 0f, 1f, // vertex 2 green
                0f, 1f, 0f, 1f, // vertex 3 green
                0f, 0f, 1f, 1f, // vertex 4 blue
                0f, 0f, 1f, 1f, // vertex 5 blue
                1f, 0f, 1f, 1f, // vertex 6 magenta
                1f, 0f, 1f, 1f, // vertex 7 magenta
        };

        setIndices(indices);
        setVertices(vertices);
        setColors(colors);
    }
}
