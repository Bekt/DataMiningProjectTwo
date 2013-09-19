package ml.projecttwo;

import ml.Matrix;
import ml.SupervisedLearner;
import static ml.projecttwo.Vector.*;

import java.util.*;

public class InstanceBasedLearner extends SupervisedLearner {

    private BallTree ballTree;
    private int k;
    private Matrix features, labels;

    public InstanceBasedLearner(int k){
        this.k = k;
    }

    @Override
    public void train(Matrix features, Matrix labels) {
        this.features = features;
        this.labels = labels;
        ballTree = new BallTree(features, k);
    }

    /**
     * Only works with categorical labels. For continuous labels, you need to calculate a mean
     * of the (label*weight) for all of the k nearest neighbors.
     * @param in
     * @return
     */
    @Override
    public List<Double> predict(List<Double> in) {
        Queue<List<Double>> queue = ballTree.findNeighbors(k, in);
        Map<List<Double>, Double> labelWeights = new HashMap<List<Double>, Double>();

        for (List<Double> neighbor : queue) {
            int index = indexOf(neighbor);
            List<Double> label = labels.getRow(index);
            Double weight = 1.0 / squaredDistance(neighbor, in);

            Double totalWeight = labelWeights.get(label);
            totalWeight = totalWeight == null ? weight : (totalWeight + weight);
            labelWeights.put(label, totalWeight);
        }

        List<Double> returnLabel = null;
        Double maxWeight = Double.MIN_VALUE;
        for (Map.Entry<List<Double>, Double> entry : labelWeights.entrySet()) {
            if (maxWeight < entry.getValue()) {
                maxWeight = entry.getValue();
                returnLabel = entry.getKey();
            }
        }
        return returnLabel;
    }

    private int indexOf(List<Double> point) {
        for (int i = 0; i < features.getNumRows(); i++) {
            if (point == features.getRow(i)) {
                return i;
            }
        }
        return -1;
    }
}
