package ui;

import static org.lwjgl.opengl.GL11.*;
import static ui.RubixBlock.Side.*;

//A rubix cube adapted from Oskar VeerHoek.
public final class RubixCube {

    private final int size;
    private final RubixBlock[][][] blocks;
    private int displayList;

    //EFFECTS: initializes a rubix cube.
    public RubixCube(int size) {
        this.size = size;
        this.blocks = new RubixBlock[size][size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    blocks[x][y][z] = new RubixBlock(BLUE, GREEN, YELLOW, WHITE, RED, ORANGE);
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: returns to solved state.
    public void reset() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    blocks[x][y][z] = new RubixBlock(BLUE, GREEN, YELLOW, WHITE, RED, ORANGE);
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes the rubix cube.
    public void initialise() {
        displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    glPushMatrix();
                    glTranslatef(2.02f * (x - 1), 2.02f * (y - 1), 2.02f * (z - 1));
                    blocks[x][y][z].drawInImmediateMode();
                    glPopMatrix();
                }
            }
        }
        glEndList();
    }

    //EFFECTS: draws the rubix cube.
    public void draw() {
        glCallList(displayList);
    }

    //MODIFIES: this
    //EFFECTS: rotates the right side of the cube clockwise.
    public void rotateRightSide() {
        RubixBlock temporaryBlock = blocks[2][0][0];
        blocks[2][0][0] = blocks[2][2][0];
        blocks[2][2][0] = blocks[2][2][2];
        blocks[2][2][2] = blocks[2][0][2];
        blocks[2][0][2] = temporaryBlock;
        RubixBlock temporaryBlock2 = blocks[2][1][0];
        blocks[2][1][0] = blocks[2][2][1];
        blocks[2][2][1] = blocks[2][1][2];
        blocks[2][1][2] = blocks[2][0][1];
        blocks[2][0][1] = temporaryBlock2;
        blocks[2][0][0].rotateRightWard();
        blocks[2][0][1].rotateRightWard();
        blocks[2][0][2].rotateRightWard();
        blocks[2][1][0].rotateRightWard();
        blocks[2][1][1].rotateRightWard();
        blocks[2][1][2].rotateRightWard();
        blocks[2][2][0].rotateRightWard();
        blocks[2][2][1].rotateRightWard();
        blocks[2][2][2].rotateRightWard();
    }

    //MODIFIES: this
    //EFFECTS: rotates the left side of the cube clockwise.
    public void rotateLeftSide() {
        RubixBlock temporaryBlock = blocks[0][0][0];
        blocks[0][0][0] = blocks[0][0][2];
        blocks[0][0][2] = blocks[0][2][2];
        blocks[0][2][2] = blocks[0][2][0];
        blocks[0][2][0] = temporaryBlock;
        RubixBlock temporaryBlock2 = blocks[0][0][1];
        blocks[0][0][1] = blocks[0][1][2];
        blocks[0][1][2] = blocks[0][2][1];
        blocks[0][2][1] = blocks[0][1][0];
        blocks[0][1][0] = temporaryBlock2;
        blocks[0][0][0].rotateLeftWard();
        blocks[0][0][1].rotateLeftWard();
        blocks[0][0][2].rotateLeftWard();
        blocks[0][1][0].rotateLeftWard();
        blocks[0][1][1].rotateLeftWard();
        blocks[0][1][2].rotateLeftWard();
        blocks[0][2][0].rotateLeftWard();
        blocks[0][2][1].rotateLeftWard();
        blocks[0][2][2].rotateLeftWard();
    }

    //MODIFIES: this
    //EFFECTS: rotates the top side of the cube clockwise.
    public void rotateTopSide() {
        RubixBlock temporaryBlock = blocks[0][2][2];
        blocks[0][2][2] = blocks[2][2][2];
        blocks[2][2][2] = blocks[2][2][0];
        blocks[2][2][0] = blocks[0][2][0];
        blocks[0][2][0] = temporaryBlock;
        RubixBlock temporaryBlock2 = blocks[0][2][1];
        blocks[0][2][1] = blocks[1][2][2];
        blocks[1][2][2] = blocks[2][2][1];
        blocks[2][2][1] = blocks[1][2][0];
        blocks[1][2][0] = temporaryBlock2;
        blocks[0][2][0].rotateUpWard();
        blocks[0][2][1].rotateUpWard();
        blocks[0][2][2].rotateUpWard();
        blocks[1][2][0].rotateUpWard();
        blocks[1][2][1].rotateUpWard();
        blocks[1][2][2].rotateUpWard();
        blocks[2][2][0].rotateUpWard();
        blocks[2][2][1].rotateUpWard();
        blocks[2][2][2].rotateUpWard();
    }

    //MODIFIES: this
    //EFFECTS: rotates the bottom side of the cube clockwise.
    public void rotateBottomSide() {
        RubixBlock temporaryBlock = blocks[2][0][2];
        blocks[2][0][2] = blocks[0][0][2];
        blocks[0][0][2] = blocks[0][0][0];
        blocks[0][0][0] = blocks[2][0][0];
        blocks[2][0][0] = temporaryBlock;
        RubixBlock temporaryBlock2 = blocks[2][0][1];
        blocks[2][0][1] = blocks[1][0][2];
        blocks[1][0][2] = blocks[0][0][1];
        blocks[0][0][1] = blocks[1][0][9];
        blocks[1][0][0] = temporaryBlock2;
        blocks[0][0][0].rotateDownWard();
        blocks[0][0][1].rotateDownWard();
        blocks[0][0][2].rotateDownWard();
        blocks[1][0][0].rotateDownWard();
        blocks[1][0][1].rotateDownWard();
        blocks[1][0][2].rotateDownWard();
        blocks[2][0][0].rotateDownWard();
        blocks[2][0][1].rotateDownWard();
        blocks[2][0][2].rotateDownWard();
    }

    //MODIFIES: this
    //EFFECTS: rotates the front side of the cube clockwise.
    public void rotateFrontSide() {
        RubixBlock temporaryBlock = blocks[0][2][2];
        blocks[0][2][2] = blocks[0][0][2];
        blocks[0][0][2] = blocks[2][0][2];
        blocks[2][0][2] = blocks[2][2][2];
        blocks[2][2][2] = temporaryBlock;
        RubixBlock temporaryBlock2 = blocks[0][1][2];
        blocks[0][1][2] = blocks[1][2][2];
        blocks[1][2][2] = blocks[2][1][2];
        blocks[2][1][2] = blocks[1][0][2];
        blocks[1][0][2] = temporaryBlock2;
        blocks[0][0][2].rotateFrontWard();
        blocks[0][1][2].rotateFrontWard();
        blocks[0][2][2].rotateFrontWard();
        blocks[1][0][2].rotateFrontWard();
        blocks[1][1][2].rotateFrontWard();
        blocks[1][2][2].rotateFrontWard();
        blocks[2][0][2].rotateFrontWard();
        blocks[2][1][2].rotateFrontWard();
        blocks[2][2][2].rotateFrontWard();
    }

    //MODIFIES: this
    //EFFECTS: rotates the back side of the cube clockwise.
    public void rotateBackSide() {
        RubixBlock temporaryBlock = blocks[0][0][0];
        blocks[0][0][0] = blocks[0][2][0];
        blocks[0][2][0] = blocks[2][2][0];
        blocks[2][2][0] = blocks[2][0][0];
        blocks[2][0][0] = temporaryBlock;
        RubixBlock temporaryBlock2 = blocks[0][1][0];
        blocks[0][1][0] = blocks[1][2][0];
        blocks[1][2][0] = blocks[2][1][0];
        blocks[2][1][0] = blocks[1][0][0];
        blocks[1][0][0] = temporaryBlock2;
        blocks[0][0][0].rotateBackWard();
        blocks[0][1][0].rotateBackWard();
        blocks[0][2][0].rotateBackWard();
        blocks[1][0][0].rotateBackWard();
        blocks[1][1][0].rotateBackWard();
        blocks[1][2][0].rotateBackWard();
        blocks[2][0][0].rotateBackWard();
        blocks[2][1][0].rotateBackWard();
        blocks[2][2][0].rotateBackWard();
    }
}
