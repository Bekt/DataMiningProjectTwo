package ml.projecttwo;

import ml.Matrix;
import java.util.List;

public class BallTree {

    BallNode root;

    public BallTree(Matrix points) {
        root = BallNode.buildBallNode(points);
    }

    public List<Double> findNeighbors(int k, List<Double> point) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
