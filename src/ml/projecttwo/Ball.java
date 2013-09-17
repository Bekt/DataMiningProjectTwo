package ml.projecttwo;

import helpers.Rand;
import ml.Matrix;
import java.util.List;
import static ml.projecttwo.Vector.*;

public abstract class Ball {

    public final static double RADIUS_GROWTH_RATE = 0.02;

    List<Double> center;
    double radius;

    public abstract boolean isLeaf();

    public void findBoundingBall(Matrix points) {
        int randomIndex = Rand.nextInt(points.getNumRows());

        List<Double> pointA = points.getRow(randomIndex);
        List<Double> pointE = furhtestPoint(points, pointA);
        List<Double> pointF = furhtestPoint(points, pointE);

        radius = distance(pointE, pointF) / 2;
        center = addAndMultiply(pointE, pointF, 0.5);

        while (true) {
            List<Double> pointD = null;
            if (1 == 1) {
                throw new UnsupportedOperationException("Not implemented");
            }

            List<Double> difference = subtract(pointD, center);
            double coefB = 1 - (radius / magnitude(difference));

            // c = c + (1 - (r / ||d - c||)) (d - c)
            center = addAndMultiply(center, 1, difference, coefB, 1);
            radius *= (1 + RADIUS_GROWTH_RATE);
        }
    }

    public double distanceTo(List<Double> point) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
