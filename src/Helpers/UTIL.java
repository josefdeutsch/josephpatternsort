package Helpers;

import Builder.DisplayManager;
import Interface.VertexArrayObject;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by philippPC on 08.11.2016.
 */
public class UTIL {

    public static UTIL2 util2 = new UTIL2();

    private static int bufferOverFlowReminder;
    public static int vertexArrayframeCounter = 0;
    public static int vertexArrayframeCounterInstanced = 0;
    public static boolean vertexframeCounter = false;

    public static void resetBufferOverFlowReminder() {
        bufferOverFlowReminder = 0;
    }

    public static boolean inBounds(float[] data, int capacity) {

        int reminder = bufferOverFlowReminder += data.length * Float.BYTES;
        if (reminder > capacity) return false;
        else
            return true;
    }

    public static boolean inBounds(int[] data, int capacity) {

        int reminder = bufferOverFlowReminder += data.length * Integer.BYTES;

        if (reminder > capacity) return false;
        else
            return true;
    }

    public static ByteBuffer toByteBuffer(int[] data) throws IOException {

        resetBufferOverFlowReminder();

        //Möglichkeit zu Fixwerten
        int capacity = data.length * Integer.BYTES;
        // Wenn data inBounds
        if (!inBounds(data, capacity)) throw new IOException();
        ByteBuffer container = ByteBuffer.allocateDirect(capacity);

        IntBuffer intbuffer = container.order(ByteOrder.nativeOrder()).asIntBuffer();
        intbuffer.put(data);
        intbuffer.flip();

        return container;
    }

    public static ByteBuffer toByteBuffer(float[] data) throws IOException {

        resetBufferOverFlowReminder();

        //Möglichkeit zu Fixwerten
        int capacity = data.length * Float.BYTES;

        if (!inBounds(data, capacity)) throw new IOException();

        ByteBuffer container = ByteBuffer.allocateDirect(capacity);

        FloatBuffer floatbuffer = container.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer.put(data);
        floatbuffer.flip();


        return container;
    }

    public static ByteBuffer toByteBuffer(String[] strs) throws IOException {

        resetBufferOverFlowReminder();
        int[] data = new int[uniCodeBits(strs)];
        //Möglichkeit zu Fixwerten
        int capacity = data.length * Integer.BYTES;

        if (!inBounds(data, capacity)) throw new IOException();

        ByteBuffer container = ByteBuffer.allocateDirect(capacity);

        for (int i = 0; i < strs.length; i++) {
            container.put(strs[i].getBytes());
            container.put((byte) 0);
        }
        container.flip();// relative getOperations and sets last position to limit.

        return container;
    }

    public static int uniCodeBits(String[] strs) {

        int len = 0;
        byte[] helper = new byte[]{};

        for (int i = 0; i < strs.length; i++) {
            try {
                helper = strs[i].getBytes("UTF-8");
                len += helper.length + 1;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return len;
    }

    public static Vector2f gainTextureID(int totelAmountofShader) {

        int a = 0;
        int b = 0;
        a = 2 * totelAmountofShader - 1;
        b = a + 1;

        return new Vector2f(a, b);
    }

    public static int[] fillRandomInteger(int[] arr) {

        for (int i = 0; i <= arr.length - 1; i++) {
            arr[i] = i;
        }
        return shuffled(arr);

    }

    public static void shuffle(int[] arr) {
        Random rand = new Random();

        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, rand.nextInt(i + 1));
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] shuffled(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        shuffle(copy);
        return copy;
    }

    public static float getTime() {

        return DisplayManager.getFrameTimeSeconds() * 1000f;

    }

    public static float frameCountervertexArrayObject() {

        float var = getTime();
        float value = var += vertexArrayframeCounter++;
        return value;

    }

    public static float frameCountervertexArrayObjectInstanced() {

        float var = getTime();
        float value = var += vertexArrayframeCounterInstanced++;
        return value;

    }

    public static void setBooleanVertexArrayObject(List<VertexArrayObject> vertexArrayObjects, int vertexArrayObject, int value) {

        if (frameCountervertexArrayObject() > value) {

            {
                vertexArrayObjects.get(vertexArrayObject).setbBoolean(true);
                vertexArrayframeCounter = 0;
            }

        }
    }

    public static void setBooleanVertexArrayObjectInstanced(List<VertexArrayObject> vertexArrayObjects, int time) {
// object not needed here
        if (frameCountervertexArrayObjectInstanced() > time) {
            System.out.println("hello");
            int key = util2.getKey();
            int value = util2.map.get(key);
            vertexArrayObjects.get(key).setbBoolean(true);
            vertexArrayObjects.get(key).setValue(value);
            util2.reset();
            //  System.out.println("key: "+key+"value: "+value);
            vertexArrayframeCounterInstanced = 0;
        }
    }
    public static void setGrid(List<VertexArrayObject> vertexArrayObjects){

       for (int i = 0; i <= 7 ; i++) {
            int key = util2.getKey();
            int value = util2.map.get(key);
            vertexArrayObjects.get(key).setbBoolean(true);
            vertexArrayObjects.get(key).setValue(value);
            util2.reset();

    }

    }  }





