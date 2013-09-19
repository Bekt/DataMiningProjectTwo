package ml.projecttwo;

import ml.ARFFParser;
import ml.Matrix;

import java.io.IOException;
import java.util.*;

import static ml.projecttwo.Vector.*;

public class Main {

    public static final int k = 1;

    public static void main(String[] args) throws IOException {

        Matrix points = ARFFParser.loadARFF(args[0]);
        //Matrix points = ARFFParser.loadARFF("/Users/dev/workspace/DataMiningProjectTwo/mnist_part.arff");
        points.shuffle();

        final int featuresStart = 0, featuresEnd = 784;
        final int labelsStart = 784, labelsEnd = 785;
        final int nFoldSize = 2;

        Matrix features = points.subMatrixCols(featuresStart, featuresEnd);
        Matrix labels = points.subMatrixCols(labelsStart, labelsEnd);

        InstanceBasedLearner learner = new InstanceBasedLearner(k);

        double mse = learner.nFoldCrossValidation(features, labels, nFoldSize);
        System.out.println("mse:" + mse);
    }

    /**
     * Returns the indices of k-rows in the matrix
     */
    static Set<Integer> slowKnn(Matrix points, int k, List<Double> point) {
        Set<Integer> indecies = new HashSet<Integer>();
        for (int i = 0; i < k; i++) {

            double closestDistance = Double.POSITIVE_INFINITY;
            int closestIndex = -1;
            for (int j = 0; j < points.getNumRows(); j++) {
                List<Double> row = points.getRow(j);

                double dist = distance(point, row);
                if (dist < closestDistance && !indecies.contains(j)) {
                    closestDistance = dist;
                    closestIndex = j;
                }
            }
            indecies.add(closestIndex);
        }
        return indecies;
    }
}
