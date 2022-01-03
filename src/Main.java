import Builder.*;
import Helpers.States;
import Helpers.UTIL;
import Interface.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philippPC on 15.07.2016.
 */
public class Main {
    public int counter = 0;
    private List<VertexArrayObject> vertexArrayObjects = new ArrayList<>();
    public boolean bool = false;


    public Main() throws InstantiationException, IllegalAccessException {

        DisplayManager.createDisplay();

        IFramebuffers framebuffers = new Framebuffers();
        IReference reference = new Reference();
        IObjloader objLoader = new OBJLoader();

        IFunctions functions = new Functions(framebuffers, reference, objLoader);

        List<ITemp> temps = new ArrayList<>();
        ArrayList<String> arraylist = new ArrayList<>();

        ArrayList<Float> x1 = new ArrayList<>();
        ArrayList<Float> y1 = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            arraylist.add(Integer.toString(i));
        }
        int index = 0;
        int numberofrows = 2;
        int numberofcolums = 4;
        float rx = 3.33f;
        float ry = 4.50f;

        for (int j = 0; j < numberofrows; j++) {
            for (int i = 0; i < numberofcolums; i++) {
                float x = (rx) * (i * 2);
                float y = j * 2 * ry;
                x1.add(x);
                y1.add(y);
                vertexArrayObjects.add(new VAOImage("src/Shadertxt/vertexShader" + arraylist.get(index) + ".txt",
                        "src/Shadertxt/fragmentShader" + arraylist.get(index) + ".txt", "plane2", temps, reference, functions, objLoader,
                        new Entity((new Vector3f((-1) * x, (-1) * y, 0f)), 0, 0, 0, 1f)));
                objLoader.deleteCacheData();

                index++;
            }
        }
        index++;
        System.out.println(index);
        float x = x1.get(8-1) * -1 * (float)Math.pow(2,-1);
        float y = y1.get(8-1) * -1 * (float)Math.pow(2,-1);
        vertexArrayObjects.add(new VAOImage("src/Shadertxt/vertexShader" + arraylist.get(index) + ".txt",
                "src/Shadertxt/fragmentShader" + arraylist.get(index) + ".txt", "plane3", temps, reference, functions, objLoader,
                new Entity((new Vector3f(x,y,0f)), 0, 0, 0, 1)));
        objLoader.deleteCacheData();

        Camera camera = new Camera();
        Movement movement = new Movement();

        try {

            Keyboard.create();
            Keyboard.enableRepeatEvents(true);

        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        while (!Display.isCloseRequested()) {

            movement.move();
            camera.move(movement);
            States.setStates();

            //  vertexArrayObjects.get(2).move();

            for (VertexArrayObject v : vertexArrayObjects
                    ) {
                v.run(camera, movement);
            }
            /**  if (Keyboard.getEventKey() == Keyboard.KEY_O) {
             if (Keyboard.getEventKeyState()) {
             counter++;
             } else {
             counter=0;
             }
             }
             if(this.counter==1){
             UTIL.setGrid(vertexArrayObjects);
             }
             **/
            alternateWhole();

            DisplayManager.updateDisplay();
        }

        for (VertexArrayObject vertexArrayObject : vertexArrayObjects) {
            vertexArrayObject.destroy();
        }

        framebuffers.cleanUp();
        DisplayManager.closeDisplay();}

    public void performTimer() throws IllegalAccessException, InstantiationException {
        UTIL.setBooleanVertexArrayObjectInstanced(vertexArrayObjects, 250);
    }
    public void alternateWhole(){

        if (Keyboard.getEventKey() == Keyboard.KEY_O) {
            if (Keyboard.getEventKeyState()) {
                counter++;
            } else {
                counter=0;
            }
        }
        if(this.counter==1){
            UTIL.setGrid(vertexArrayObjects);
        }
    }
    public void alternateSingle(){
    if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
        this.bool = true;
    }
    if (bool) {
        try {
            performTimer();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    DisplayManager.updateDisplay();
}

    public static void main(String... args) {
        try {
            new Main();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
