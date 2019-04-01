package libraryApproach;

import java.io.IOException;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.api.Layer.TrainingMode;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.nd4j.list.IntNDArrayList;


public class Main {

    public static void main(String args[]) throws IOException {


        // number of rows and columns in the input pictures
        int numRows = 28;
        int numColumns = 28;
        int outputNum = 10;// number of output classes
        int batchSize = 128; // batch size for each epoch
        int rngSeed = 123; // random number seed for reproducibility
        int numEpochs = 15; // number of epochs to perform

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().seed(rngSeed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                .layer(
                        0,
                            new DenseLayer.Builder().nIn(numRows * numColumns)
                                    .nOut(1000)
                                    .activation(Activation.RELU)
                                    .weightInit(WeightInit.XAVIER)
                                    .build())
                .layer(
                        1,
                            new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                                    // create
                                    // hidden
                                    // layer
                                    .nIn(1000)
                                    .nOut(outputNum)
                                    .activation(Activation.SOFTMAX)
                                    .weightInit(WeightInit.XAVIER)
                                    .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.activate(TrainingMode.TRAIN);
        model.feedForward();
        //DataSet
        model.fit();
        model.init();
        // print the score with every 1 iteration
        model.setListeners(new ScoreIterationListener(1));

        MnistDataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
        MnistDataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);

        for (int i = 0; i < numEpochs; i++) {
            model.fit(mnistTrain);
        }

        Evaluation evaluation = model.evaluate(mnistTest);

        // print the basic statistics about the trained classifier
        System.out.println("Accuracy: " + evaluation.accuracy());
        System.out.println("Precision: " + evaluation.precision());
        System.out.println("Recall: " + evaluation.recall());

        // in more complex scenarios, a confusion matrix is quite helpful
        System.out.println(evaluation.confusionToString());



    }

}
