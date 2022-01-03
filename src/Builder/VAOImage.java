package Builder;

import Helpers.Maths;
import Interface.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_RECTANGLE;
import static org.lwjgl.opengl.GL45.glDisableVertexArrayAttrib;
import static org.lwjgl.opengl.GL45.glEnableVertexArrayAttrib;

/**
 * Created by philippPC on 19.01.2017.
 */
public class VAOImage extends Builder.ShaderProgram implements VertexArrayObject {


    private List<ITemp> temps;
    private IFunctions functions;
    private IReference reference;
    private IEntity entity;
//new Entity((new Vector3f(4f, 0f, 1f)), 0, 0, 0, 1f);
    private boolean bBoolean = false;
    private int ID = 0;
    private int rectangleID;
    private double vaoID2;
    private int vaoID;
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public VAOImage(String vertexFile, String fragmentFile, String string, List<ITemp> temps, IReference reference, IFunctions functions, IObjloader objLoader, IEntity entity) {

        super(vertexFile, fragmentFile);
        this.linkInstance(temps,reference,functions);
        objLoader.loadObjModel(string, functions);

        this.temps.add(functions.vaoProcedureImage());
        vaoID2=this.getProgramID()*Math.pow(3,-1);
        vaoID=(int) vaoID2;
        ID=vaoID-1;
        this.attachShader(this.getProgramID());
        this.setUniformMatrix("projectionmatrix",this.getProgramID(), Maths.createprojectionMatrix());
        this.dettachShader();
        this.entity = entity;
    //    this.rectangleID=temps.get(this).getVAOID();
       //this.setPosition(new Vector3f(0,0,0));



    }

    public void linkInstance(List<ITemp> temps,IReference reference, IFunctions functions){
        this.temps = temps;
        this.reference = reference;
        this.functions = functions;

    }

    public void run(ICamera camera, IMovement movement) {

        this.attachShader(this.getProgramID());
        this.enableConstants();
        this.bindtoOpenglContent();
        Matrix4f tMatrix = this.performTransformation(camera, movement);
        this.setUniformValues(camera,tMatrix);
        this.performGivenTasks();
        this.renderContext();
        this.undbindOpenglContent();
        this.disableConstants();
        this.dettachShader();
    }

    public void destroy(){

        this.killShader(this.getProgramID(),this.getVertexSHADERID(),this.getFragmentSHADERID());

    }

    public void setbBoolean(boolean bBoolean) {
        this.bBoolean = bBoolean;
    }

    public void attachShader() {
        super.attachShader(this.getProgramID());
    }

    public void dettachShader(){
        super.dettachShader();
    }

    public void enableConstants() {
        glEnable(GL_TEXTURE_RECTANGLE);
    }

    public void disableConstants() {
        glDisable(GL_TEXTURE_RECTANGLE);
    }

    public void bindtoOpenglContent() {
        glBindVertexArray(temps.get(ID).getVAOID());
        glBindTexture(GL_TEXTURE_RECTANGLE, vaoID);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 0);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 1);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 2);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 3);
    }

    public void undbindOpenglContent() {
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 0);
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 1);
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 2);
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 3);
        glBindVertexArray(0);
    }

    public void setUniformValues(ICamera camera, Matrix4f tMatrix) {
        this.setUniformMatrix("transformationmatrix", this.getProgramID(), tMatrix);
        this.setUniformMatrix("viewmatrix",this.getProgramID(), Maths.createViewMatrix(camera));
    }

    public Matrix4f performTransformation(ICamera camera,IMovement movement) {
        Matrix4f tMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        entity.setRotX(270);
        entity.setRotY(180);
        //entity.setRotY(90);
        // entity.setRotZ(90);
      //  entity.setScale(movement.getScaleF());
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
            entity.incZ(this.entity.getPosition().getZ()+0.02f);

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
            entity.incX(this.entity.getPosition().getX()-0.02f);

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
            entity.incY(this.entity.getPosition().getY()+0.02f);

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
            entity.incY(this.entity.getPosition().getY()-0.02f);

        }




        return tMatrix;
    }

    public void performGivenTasks() {
        if(bBoolean){

            updateBuffer();
            this.bBoolean = false;
        }
    }

    public void renderContext() {
        glDrawElements(GL_TRIANGLES, temps.get(ID).getVertexCOUNT(), GL_UNSIGNED_INT, 0);
    }

    private void updateBuffer(){


    //  ByteBuffer data = functions.getTextureBufferData("res4/"+Integer.toString(value));
        ByteBuffer data = functions.getTextureBufferData("res4/"+reference.getDecimalFormat(value));

        //  System.out.println("decimalformat: "+reference.getDecimalFormat(value));

        int width = functions.getTempWith();
        int height = functions.getTempHeight();
      //  System.out.println("programid"+getProgramID());
        functions.setDimensionTexture(this.getProgramID(), 4, width,height);

        glTexSubImage2D(GL_TEXTURE_RECTANGLE, 0, 0, 0, width ,height, GL_RGBA, GL_UNSIGNED_BYTE, data);

    }
}
