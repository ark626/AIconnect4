package libraryApproach;

import java.io.File;
import java.io.IOException;
//import org.deeplearning4j.nn.api.OptimizationAlgorithm;
//import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
//import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
//import org.deeplearning4j.nn.conf.layers.DenseLayer;
//import org.deeplearning4j.nn.conf.layers.OutputLayer;
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
//import org.deeplearning4j.nn.weights.WeightInit;
//import org.nd4j.linalg.activations.Activation;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.dataset.DataSet;
//import org.nd4j.linalg.factory.Nd4j;
//import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import com.google.common.collect.Lists;

public class DL4JNN {

//    public MultiLayerNetwork model;
//
//    public DataSet dataSet;

    private DL4JNN() {

    }

    public DL4JNN(int numRows, int numColumns, int outputNum, int batchSize, int rngSeed, int numEpochs) {

//        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().seed(rngSeed)
//                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
//                .list()
//                .layer(
//                        0,
//                            new DenseLayer.Builder().nIn(numRows * numColumns)
//                                    .nOut(1000)
//                                    .activation(Activation.RELU)
//                                    .weightInit(WeightInit.XAVIER)
//                                    .build())
//                .layer(
//                        1,
//                            new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
//                                    // create
//                                    // hidden
//                                    // layer
//                                    .nIn(1000)
//                                    .nOut(outputNum)
//                                    .activation(Activation.SOFTMAX)
//                                    .weightInit(WeightInit.XAVIER)
//                                    .build())
//                .build();
//        // net.fit();
//
//
//
//    }
//
//    /**
//     * Creates the Initial Dataset
//     * 
//     * @param inputs
//     * @param outputs
//     */
//    public void createDataSetFromDoubleArray(double[][] inputs, double[] outputs) {
//        INDArray first = Nd4j.create(inputs);
//        INDArray second = Nd4j.create(outputs);
//        this.dataSet = new DataSet(first, second);
//    }
//
//    public void addToDataSetFromDoubleArray(double[][] inputs, double[] outputs) {
//        INDArray first = Nd4j.create(inputs);
//        INDArray second = Nd4j.create(outputs);
//        DataSet dataSetNew = new DataSet(first, second);
//        DataSet.merge(Lists.newArrayList(dataSet, dataSetNew));
//    }
//
//    public void trainTheNetwork(int iterations) {
//
//        for (int i = 0; i < iterations; i++) {
//            this.model.fit(dataSet);
//        }
//    }
//
//    public void save(String fileName) {
//
//        File f = new File(fileName + ".dataSet");
//        this.dataSet.save(f);
//        File f2 = new File(fileName + ".model");
//        try {
//            this.model.save(f2);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    public static DL4JNN load(String fileName) throws IOException {
//
//        DL4JNN dl4jnn = new DL4JNN();
//        File f = new File(fileName + ".dataSet");
//        File f2 = new File(fileName + ".model");
//        dl4jnn.dataSet = new DataSet();
//        dl4jnn.dataSet.load(f);
//        dl4jnn.model = MultiLayerNetwork.load(f2, true);
//        return dl4jnn;

    }

}
