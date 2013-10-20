package ml.projecttwo;

import ml.Matrix;
import ml.SupervisedLearner;
import static helpers.Vector.*;

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

        if (labels.isCategorical(0)) {
            return handleCategorical(labelWeights);
        } else {
            return handleContinuous(labelWeights);
        }
    }

    private List<Double> handleCategorical(Map<List<Double>, Double> labelWeights) {
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

    private List<Double> handleContinuous(Map<List<Double>, Double> labelWeights) {
        double sum = 0;
        int n = 0;
        for (Map.Entry<List<Double>, Double> entry : labelWeights.entrySet()) {
            sum += (entry.getValue() * entry.getKey().get(0));
            n++;
        }
        List<Double> returnLabel = new ArrayList<Double>();
        returnLabel.add(sum / n);
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
