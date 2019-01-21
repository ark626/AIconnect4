package database;

import java.io.Serializable;

public class Transition implements Serializable{
    private Image resultImage;
    private long fitnessDelta =0;
    private Image buttonPress;
    
    public Image getResultImage() {
        return resultImage;
    }
    public void setResultImage(Image resultImage) {
        this.resultImage = resultImage;
    }
    public long getFitnessDelta() {
        return fitnessDelta;
    }
    public void setFitnessDelta(long fitnessDelta) {
        this.fitnessDelta = fitnessDelta;
    }
    public Image getButtonPress() {
        return buttonPress;
    }
    public void setButtonPress(Image buttonPress) {
        this.buttonPress = buttonPress;
    }
    
    

}
