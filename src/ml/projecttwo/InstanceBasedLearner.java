package ml.projecttwo;

import ml.Matrix;
import ml.SupervisedLearner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class InstanceBasedLearner extends SupervisedLearner {
    BallTree ballTree;
    int k;
    Matrix features;
    Matrix labels;

    public InstanceBasedLearner(int k){
        this.k = k;
    }

    @Override
    public void train(Matrix features, Matrix labels) {
        this.features = features;
        this.labels = labels;
        ballTree = new BallTree(features);
    }

    /**
     * Only works with categorical labels. For continuous labels, you need to calculate a mean
     * of the (label*weight) for all of the k nearest neighbors.
     * @param in
     * @return
     */
    @Override
    public List<Double> predict(List<Double> in) {
        PriorityQueue<List<Double>> queue= ballTree.findNeighbors(k, in);
        Map<List<Double>, Double> labelWeights = new HashMap<List<Double>, Double>(); //maps labels to their weights

        for(List<Double> neighbor : queue){
            int index = indexOf(neighbor);
            List<Double> label = labels.getRow(index);
            Double weight = 1.0/Vector.squaredDistance(neighbor, in); //linear interpolation, weight = 1/d

            //Update the weight for this label
            if(labelWeights.containsKey(label)){
                Double totalWeight = labelWeights.get(label);
                totalWeight += weight;
                labelWeights.put(label, totalWeight);
            }
            else{
                labelWeights.put(label, weight);
            }
        }

        List<Double> returnLabel = null;
        Double maxWeight = Double.MIN_VALUE;
        for(Map.Entry<List<Double>, Double> entry : labelWeights.entrySet()){
            if(maxWeight < entry.getValue()){
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
