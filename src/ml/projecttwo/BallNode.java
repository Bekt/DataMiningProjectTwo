package ml.projecttwo;

import helpers.Rand;
import ml.Matrix;
import java.util.List;
import static ml.projecttwo.Vector.*;

public class BallNode extends Ball {

    BallNode left, right;
    Matrix points;

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public static BallNode buildBallNode(Matrix points, int k) {

        BallNode ballNode = new BallNode();
        ballNode.findBoundingBall(points);

        if (points.getNumRows() > 2 * k) {

            int randomIndex = Rand.nextInt(points.getNumRows());

            List<Double> pointA = points.getRow(randomIndex);
            List<Double> pointE = furthestPoint(points, pointA);
            List<Double> pointF = furthestPoint(points, pointE);

            Matrix pointsG = new Matrix(points, true);
            Matrix pointsH = new Matrix(points, true);

            for (int i = 0; i < points.getNumRows(); i++) {
                List<Double> row = points.getRow(i);
                double distanceToE = squaredDistance(row, pointE);
                double distanceToF = squaredDistance(row, pointF);

                if (distanceToE < distanceToF) {
                    pointsG.addRow(row);
                } else {
                    pointsH.addRow(row);
                }
            }
            ballNode.left = buildBallNode(pointsG, k);
            ballNode.right = buildBallNode(pointsH, k);

        } else { // Leaf
            ballNode.points = points;
        }

        return ballNode;
    }
}
