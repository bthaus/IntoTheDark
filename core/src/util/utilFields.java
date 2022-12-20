package util;

public class utilFields {
    public static int getCamerawidth() {
        return camerawidth;
    }

    public static void setCamerawidth(int camerawidth) {
        utilFields.camerawidth = camerawidth;
    }

    public static int getCamerheight() {
        return camerheight;
    }

    public static void setCamerheight(int camerheight) {
        utilFields.camerheight = camerheight;
    }

    static private int camerawidth=800;
    static private int camerheight=800;


    public static float getTimeStep() {
        return timeStep;
    }

    public static void setTimeStep(float timeStep) {
        utilFields.timeStep = timeStep;
    }

    public static int getVelocityIterations() {
        return velocityIterations;
    }

    public static void setVelocityIterations(int velocityIterations) {
        utilFields.velocityIterations = velocityIterations;
    }

    public static int getPositionIterations() {
        return positionIterations;
    }

    public static void setPositionIterations(int positionIterations) {
        utilFields.positionIterations = positionIterations;
    }

    static private float timeStep=1.0f/60.0f;
    static private int velocityIterations=6;
    static private int positionIterations=2;

}
