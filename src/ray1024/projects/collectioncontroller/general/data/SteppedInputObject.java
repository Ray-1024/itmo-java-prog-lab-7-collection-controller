package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.tools.ISteppedInput;

import java.io.Serializable;

public abstract class SteppedInputObject implements ISteppedInput, Serializable {
    protected int currentStep = 0;
    protected int stepsCount = 0;

    @Override
    public void reset() {
        currentStep = 0;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    @Override
    public boolean isObjectReady() {
        return currentStep >= stepsCount;
    }
}
