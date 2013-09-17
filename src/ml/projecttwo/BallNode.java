package ml.projecttwo;

import ml.Matrix;

public class BallNode extends Ball {

    BallNode left, right;
    Matrix points;

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }
}
