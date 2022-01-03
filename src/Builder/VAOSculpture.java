package Builder;

import Helpers.Maths;
import Interface.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL30.GL_MAP_READ_BIT;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL44.GL_MAP_PERSISTENT_BIT;
import static org.lwjgl.opengl.GL45.*;

/**
 * Created by philippPC on 19.01.2017.
 */
public class VAOSculpture extends Builder.ShaderProgram implements VertexArrayObject {

    private List<ITemp> temps;
    private IFunctions functions;
    private IObjloader objLoader;
    private IReference reference;

    private int counter = 0;

    private IEntity entity = new Entity((new Vector3f(-2.5f, -2.5f, 0)), 0, 0, 0, 1f);
    private int modus = GL_MAP_WRITE_BIT | GL_MAP_READ_BIT | GL_MAP_PERSISTENT_BIT;

    private boolean aBoolean = false;
    private boolean bBoolean = false;



    public VAOSculpture(String vertexFile, String fragmentFile, String string, List<ITemp> temps,IReference reference, IFunctions functions, IObjloader objLoader) {

        super(vertexFile, fragmentFile);

        linkInstance(temps,reference,functions,objLoader);

        this.attachShader(this.getProgramID());
        setProjectionMatrix(this.getProgramID());
        this.dettachShader();
        objLoader.loadObjModel(string, functions);
        this.temps.add(functions.vaoProcedureSculpture());



    }

    public void linkInstance(List<ITemp> temps, IReference reference,IFunctions functions, IObjloader objloader){

        this.temps = temps;
        this.functions = functions;
        this.objLoader = objloader;
        this.reference = reference;

    }



    public void run(ICamera camera, IMovement movment) {

        this.attachShader();
        this.enableConstants();
        this.bindtoOpenglContent();
        Matrix4f tMatrix = this.performTransformation(camera,movment);
        this.setUniformValues(camera,tMatrix);
        this.performGivenTasks();
        this.renderContext();
        this.undbindOpenglContent();
        this.disableConstants();
        this.dettachShader();
    }





    private void updateBuffer() {


        objLoader.updateModel(reference.getTotalAmountofSculptureReferences()[counter]);
        System.out.println("Indices of current Structure " + reference.getStrings()[reference.returnPointer(counter)]);

        // glInvalidateBufferData(1);
        ByteBuffer bb = glMapNamedBufferRange(1, 0, objLoader.getIndicesArray().length * Integer.BYTES, modus, null);

        temps.get(0).setVertexCount(objLoader.getIndicesArray().length);
        // gilt auch für Bilder!n
        IntBuffer intBuffer = bb.order(ByteOrder.nativeOrder()).asIntBuffer();
        intBuffer.put(objLoader.getIndicesArray());
        intBuffer.flip();
        bb.clear();

        glUnmapNamedBuffer(1);
        // map vertices
        // glInvalidateBufferData(2);
        ByteBuffer bb2 = glMapNamedBufferRange(2, 0, objLoader.getVerticesArray().length * Float.BYTES, modus, null);

        FloatBuffer floatbuffer = bb2.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer.put(objLoader.getVerticesArray());
        floatbuffer.flip();

        bb2.clear();
        glUnmapNamedBuffer(2);
        // map texture coords
        // glInvalidateBufferData(3);
        ByteBuffer bb3 = glMapNamedBufferRange(3, 0, objLoader.getTextureArray().length * Float.BYTES, modus, null);

        FloatBuffer floatbuffer2 = bb3.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer2.put(objLoader.getTextureArray());
        floatbuffer2.flip();
        bb3.clear();

        glUnmapNamedBuffer(3);
        // glInvalidateBufferData(4);
        // map normals
        ByteBuffer bb4 = glMapNamedBufferRange(4, 0, objLoader.getNormalsArray().length * Float.BYTES, modus, null);

        FloatBuffer floatbuffer3 = bb4.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer3.put(objLoader.getNormalsArray());
        floatbuffer3.flip();
        bb4.clear();

        glUnmapNamedBuffer(4);

        counter++;
        if (counter == reference.getTotalAmountofSculptureReferences().length) counter = 0;

    }


    public void destroy() {
        this.killShader(this.getProgramID(), this.getVertexSHADERID(), this.getFragmentSHADERID());
    }



    public void setbBoolean(boolean bBoolean) {
        this.bBoolean = bBoolean;
    }

    @Override
    public void attachShader() {
        super.attachShader(this.getProgramID());
    }
    public void dettachShader(){
        super.dettachShader();
    }

    @Override
    public void enableConstants() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    @Override
    public void disableConstants() {
        glDisable(GL_CULL_FACE);
    }

    @Override
    public void bindtoOpenglContent() {
        glBindVertexArray(temps.get(0).getVAOID());

        glBindTexture(GL_TEXTURE_2D, 1);
        glBindTexture(GL_TEXTURE_CUBE_MAP, 2);

        glEnableVertexArrayAttrib(temps.get(0).getVAOID(), 0);
        glEnableVertexArrayAttrib(temps.get(0).getVAOID(), 1);
        glEnableVertexArrayAttrib(temps.get(0).getVAOID(), 2);

    }

    @Override
    public void undbindOpenglContent() {
        glDisableVertexArrayAttrib(temps.get(0).getVAOID(), 0);
        glDisableVertexArrayAttrib(temps.get(0).getVAOID(), 1);
        glDisableVertexArrayAttrib(temps.get(0).getVAOID(), 2);

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }

    @Override
    public void setUniformValues(ICamera camera, Matrix4f tmatrix) {
        this.setUniformValueVec3("cameraPosition", new Vector3f(camera.getPosition()));
        this.setUniformMatrix("transformationmatrix", this.getProgramID(), tmatrix);
        this.setUniformMatrix("viewmatrix", this.getProgramID(), Maths.createViewMatrix(camera));
    }

    @Override
    public Matrix4f performTransformation(ICamera camera, IMovement movement) {
        Matrix4f tMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        entity.increaseRotation(0, 0.2f, 0);
        return tMatrix;
    }


    @Override
    public void performGivenTasks() {
        if (bBoolean) {
            updateBuffer();
            this.bBoolean = false;
        }
    }

    @Override
    public void renderContext() {
        glDrawElements(GL_TRIANGLES, temps.get(0).getVertexCOUNT(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void setValue(int value) {

    }

    @Override
    public int getValue() {
        return 0;
    }

}
