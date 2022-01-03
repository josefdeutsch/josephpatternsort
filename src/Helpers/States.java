package Helpers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by philippPC on 19.01.2017.
 */
public class States {
    public static boolean bool=false;

    public static void setStates(){
        glEnable(GL_DEPTH_TEST);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.1f ,0.1f, 0.1f, 0.1f);
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {

            bool=true;
        }
        if(bool){
            GL11.glClearColor(1f ,1f, 1f, 1f);

        }
    }
    public static void runFramebuffer() {
        glEnable(GL_DEPTH_TEST);

        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
    }


}
