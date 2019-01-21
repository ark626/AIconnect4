package database;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author vdinger
 *
 */
public class Situation implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Image inputSituation;
    private ArrayList<Transition> solvings;
    private int imageSize;
    private int buttonSize;

    public long getBestFitness() {

        return this.getBestTransition()
                .getFitnessDelta();
    }

    public Transition getBestTransition() {

        long max = Integer.MIN_VALUE;
        Transition result = null;

        for (Transition transition : solvings) {
            if (max < transition.getFitnessDelta()) {
                max = transition.getFitnessDelta();
                result = transition;
            }

        }
        return result;
    }

    public boolean mergeSituation(Situation situation) {

        if (!situation.getInputSituation()
                .equals(this.getInputSituation())) {
            return false;
        }
        for (Transition transition : this.getSolvings()) {

            if (isTransitionsResultEqual(situation, transition) && transition.getFitnessDelta() < (situation.solvings.get(0)
                    .getFitnessDelta())) {
                transition.setFitnessDelta(
                        situation.getSolvings()
                                .get(0)
                                .getFitnessDelta() + transition.getFitnessDelta());
                return true;
            }
        }
        this.getSolvings()
                .add(
                        situation.getSolvings()
                                .get(0));
        return true;
    }

    private boolean isTransitionsResultEqual(Situation situation, Transition transition) {
        return transition.getButtonPress()
                .equalsButton(
                        situation.solvings.get(0)
                                .getButtonPress())
                && (transition.getResultImage() == null && situation.solvings.get(0)
                        .getResultImage() == null || transition.getResultImage()
                                .equals(
                                        situation.solvings.get(0)
                                                .getResultImage()));
    }

    public Image getInputSituation() {
        return inputSituation;
    }

    public void setInputSituation(Image inputSituation) {
        this.inputSituation = inputSituation;
    }

    public ArrayList<Transition> getSolvings() {
        return solvings;
    }

    public void setSolvings(ArrayList<Transition> solvings) {
        this.solvings = solvings;
        if (solvings.size() == 0) {
            return;
        }
        this.buttonSize = solvings.get(0)
                .getButtonPress()
                .getImage().length;
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        // default serialization
        oos.defaultWriteObject();
        // oos.writeInt(this.solvings.size());
        // oos.writeInt(imageSize);
        // oos.writeInt(buttonSize);
        //
        // for (Transition transition : solvings) {
        //
        // oos.writeLong(transition.getFitnessDelta());
        //
        // for (int i = 0; i < buttonSize; i++) {
        //
        // oos.writeDouble(
        // transition.getButtonPress()
        // .getImage()[i]);
        // }
        //
        //// for (int i = 0; i < imageSize; i++) {
        ////
        //// oos.writeDouble(
        //// transition.getResultImage()
        //// .getImage()[i]);
        //// }
        // }

    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();
        // // default serialization
        // int solvingsSize = ois.readInt();
        // this.imageSize = ois.readInt();
        // this.buttonSize = ois.readInt();
        // this.solvings = new ArrayList<>();
        // for (int z = 0; z < solvingsSize; z++) {
        //
        // Transition transition = new Transition();
        // transition.setFitnessDelta(ois.readLong());
        // Image buttonPress = new Image();
        //// Image result = new Image();
        //// result.setImage(new double[imageSize]);
        //
        // buttonPress.setImage(new double[buttonSize]);
        //
        // for (int i = 0; i < buttonSize; i++) {
        //
        // buttonPress.getImage()[i] = ois.readDouble();
        // }
        //
        //// for (int i = 0; i < imageSize; i++) {
        ////
        //// result.getImage()[i] = ois.readDouble();
        //// }
        //// transition.setButtonPress(buttonPress);
        //// transition.setResultImage(result);
        // this.solvings.add(transition);
        // }
    }

    public String[] getStringedVersionOfInputImage() {

        String[] result = new String[7];
        for (int x = 0; x < 6; x++) {
            String line = "";
            for (int y = 0; y < 7; y++) {
                line += " "+this.inputSituation.getImage()[y + x * 7];
            }
            result[x] = line;
            result[6] = ""+this.inputSituation.getImage()[49]+this.inputSituation.getImage()[50]+this.inputSituation.getImage()[51];
        }
        
        return result;
        
    }



}
