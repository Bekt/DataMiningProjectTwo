package ml.projecttwo;

import helpers.Rand;
import ml.Matrix;

import java.util.List;

import static ml.projecttwo.Vector.*;

public class BallNode extends Ball {

    public final static int BALL_SIZE = 10;

    BallNode left, right;
    Matrix points;

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }

    // TODO: re-use points A, E, F
    public static BallNode buildBallNode(Matrix points) {

        BallNode ballNode = new BallNode();
        ballNode.findBoundingBall(points);

        if (points.getNumRows() > 2 * BALL_SIZE) {

            int randomIndex = Rand.nextInt(points.getNumRows());

            List<Double> pointA = points.getRow(randomIndex);
            List<Double> pointE = furhtestPoint(points, pointA);
            List<Double> pointF = furhtestPoint(points, pointE);

            Matrix pointsG = new Matrix(points, true);
            Matrix pointsH = new Matrix(points, true);

            for (int i = 0; i < points.getNumRows(); i++) {
                List<Double> row = points.getRow(i);
                double distanceToE = distance(row, pointE);
                double distanceToF = distance(row, pointF);

                if (distanceToE < distanceToF) {
                    pointsG.addRow(row);
                } else {
                    pointsH.addRow(row);
                }
            }
            ballNode.left = buildBallNode(null);
            ballNode.right = buildBallNode(null);

        } else { // Leaf
            ballNode.points = points;
        }

        return ballNode;
    }
}
