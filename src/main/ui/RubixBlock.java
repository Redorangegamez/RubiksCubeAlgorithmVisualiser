package ui;

import static org.lwjgl.opengl.GL11.*;

//A block of a rubix cube adapted from Oskar VeerHoek.
public class RubixBlock {
    //enum describes a side of the rubix block and its color.
    public enum Side {
        WHITE(1, 1, 1), YELLOW(1, 1, 0), BLUE(0, 0, 1),
        GREEN(0, 1, 0), RED(1, 0, 0), ORANGE(1, 0.5f, 0);
        public final float red;
        public final float green;
        public final float blue;
        Side(float red, float green, float blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    public Side leftSide;
    public Side rightSide;
    public Side topSide;
    public Side bottomSide;
    public Side frontSide;
    public Side backSide;

    //EFFECTS: creates a rubix block with 6 sides.
    public RubixBlock(Side leftSide, Side rightSide, Side topSide, Side bottomSide, Side frontSide, Side backSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.topSide = topSide;
        this.bottomSide = bottomSide;
        this.frontSide = frontSide;
        this.backSide = backSide;
    }

    //EFFECTS: performs initialization e.g.: creates rendering objects for OpenGL.
    public void initialise() {
        int displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        initialiseLeftSide();
        initialiseRightSide();
        initialiseFrontSide();
        initialiseBackSide();
        initialiseTopSide();
        initialiseBottomSide();
    }

    //EFFECTS: initializes left side of the block.
    public void initialiseLeftSide() {
        glColor3f(leftSide.red, leftSide.green, leftSide.blue);
        glBegin(GL_QUADS);
        glNormal3f(-1, 0, 0);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();
    }

    //EFFECTS: initializes right side of the block.
    public void initialiseRightSide() {
        glColor3f(rightSide.red, rightSide.green, rightSide.blue);
        glBegin(GL_QUADS);
        glNormal3f(1, 0, 0);
        glVertex3f(1, 1, -1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, -1, -1);
        glEnd();
    }

    //EFFECTS: initializes front side of the block.
    public void initialiseFrontSide() {
        glColor3f(frontSide.red, frontSide.green, frontSide.blue);
        glBegin(GL_QUADS);
        glNormal3f(0, 0, 1);
        glVertex3f(-1, -1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();
    }

    //EFFECTS: initializes back side of the block.
    public void initialiseBackSide() {
        glColor3f(backSide.red, backSide.green, backSide.blue);
        glBegin(GL_QUADS);
        glNormal3f(0, 0, -1);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, -1, -1);
        glEnd();
    }

    //EFFECTS: initializes top side of the block.
    public void initialiseTopSide() {
        glColor3f(topSide.red, topSide.green, topSide.blue);
        glBegin(GL_QUADS);
        glNormal3f(0, 1, 0);
        glVertex3f(-1, 1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, 1, -1);
        glVertex3f(-1, 1, -1);
        glEnd();
    }

    //EFFECTS: initializes bottom side of the block.
    public void initialiseBottomSide() {
        glColor3f(bottomSide.red, bottomSide.green, bottomSide.blue);
        glBegin(GL_QUADS);
        glNormal3f(0, -1, 0);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, -1, -1);
        glVertex3f(1, -1, -1);
        glVertex3f(1, -1, 1);
        glEnd();
        glEndList();
    }

    //EFFECTS: Draws the rubix block using the immediate rendering mode.
    public void drawInImmediateMode() {
        drawInImmediateModeLeftSide();
        drawInImmediateModeRightSide();
        drawInImmediateModeFrontSide();
        drawInImmediateModeBackSide();
        drawInImmediateModeTopSide();
        drawInImmediateModeBottomSide();
    }

    //EFFECTS: Draws the left side using the immediate rendering mode.
    public void drawInImmediateModeLeftSide() {
        glColor3f(leftSide.red, leftSide.green, leftSide.blue);
        glBegin(GL_QUADS);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();
    }

    //EFFECTS: Draws the right side using the immediate rendering mode.
    public void drawInImmediateModeRightSide() {
        glColor3f(rightSide.red, rightSide.green, rightSide.blue);
        glBegin(GL_QUADS);
        glVertex3f(1, 1, -1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, -1, -1);
        glEnd();
    }

    //EFFECTS: Draws the front side using the immediate rendering mode.
    public void drawInImmediateModeFrontSide() {
        glColor3f(frontSide.red, frontSide.green, frontSide.blue);
        glBegin(GL_QUADS);
        glVertex3f(-1, -1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();
    }

    //EFFECTS: Draws the back side using the immediate rendering mode.
    public void drawInImmediateModeBackSide() {
        glColor3f(backSide.red, backSide.green, backSide.blue);
        glBegin(GL_QUADS);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, -1, -1);
        glEnd();
    }

    //EFFECTS: Draws the top side using the immediate rendering mode.
    public void drawInImmediateModeTopSide() {
        glColor3f(topSide.red, topSide.green, topSide.blue);
        glBegin(GL_QUADS);
        glVertex3f(-1, 1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, 1, -1);
        glVertex3f(-1, 1, -1);
        glEnd();
    }

    //EFFECTS: Draws the bottom side using the immediate rendering mode.
    public void drawInImmediateModeBottomSide() {
        glColor3f(bottomSide.red, bottomSide.green, bottomSide.blue);
        glBegin(GL_QUADS);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, -1, -1);
        glVertex3f(1, -1, -1);
        glVertex3f(1, -1, 1);
        glEnd();
    }

    //MODIFIES: this
    //EFFECTS: rotates the block rightwards.
    public void rotateRightWard() {
        Side topSideHolder = topSide;
        topSide = frontSide;
        frontSide = bottomSide;
        bottomSide = backSide;
        backSide = topSideHolder;
    }

    //MODIFIES: this
    //EFFECTS: rotates the block leftwards.
    public void rotateLeftWard() {
        Side topSideHolder = topSide;
        topSide = backSide;
        backSide = bottomSide;
        bottomSide = frontSide;
        frontSide = topSideHolder;
    }

    //MODIFIES: this
    //EFFECTS: rotates the block upwards.
    public void rotateUpWard() {
        Side frontSideHolder = frontSide;
        frontSide = rightSide;
        rightSide = backSide;
        backSide = leftSide;
        leftSide = frontSideHolder;
    }

    //MODIFIES: this
    //EFFECTS: rotates the block downwards.
    public void rotateDownWard() {
        Side frontSideHolder = frontSide;
        frontSide = leftSide;
        leftSide = backSide;
        backSide = rightSide;
        rightSide = frontSideHolder;
    }

    //MODIFIES: this
    //EFFECTS: rotates the block forwards.
    public void rotateFrontWard() {
        Side topSideHolder = topSide;
        topSide = leftSide;
        leftSide = bottomSide;
        bottomSide = rightSide;
        rightSide = topSideHolder;
    }

    //MODIFIES: this
    //EFFECTS: rotates the block backwards.
    public void rotateBackWard() {
        Side topSideHolder = topSide;
        topSide = rightSide;
        rightSide = bottomSide;
        bottomSide = leftSide;
        leftSide = topSideHolder;
    }
}
