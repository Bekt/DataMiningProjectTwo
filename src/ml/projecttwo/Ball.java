package ml.projecttwo;

import helpers.Rand;
import ml.Matrix;
import java.util.List;
import static helpers.Vector.*;
import static java.lang.Math.max;

public abstract class Ball {

    public final static double RADIUS_GROWTH_RATE = 0.02;

    private List<Double> center;
    private double radius;

    public abstract boolean isLeaf();

    public void findBoundingBall(Matrix points) {
        int randomIndex = Rand.nextInt(points.getNumRows());

        List<Double> pointA = points.getRow(randomIndex);
        List<Double> pointE = furthestPoint(points, pointA);
        List<Double> pointF = furthestPoint(points, pointE);

        radius = distance(pointE, pointF) / 2.0;
        center = addAndMultiply(pointE, pointF, 0.5);

        boolean isWorking = true;
        while (isWorking) {
            isWorking = false;
            for (int i = 0; i < points.getNumRows(); i++) {
                List<Double> row = points.getRow(i);
                double dist = distanceTo(row);

                if (dist > 0) {
                    isWorking = true;

                    List<Double> difference = subtract(row, center);
                    double coefB = 1 - (radius / magnitude(difference));

                    // c = c + (1 - (r / ||d - c||)) (d - c)
                    center = addAndMultiply(center, 1, difference, coefB, 1);
                    radius *= (1 + RADIUS_GROWTH_RATE);
                }
            }
        }
    }

    public double distanceTo(List<Double> point) {
        return max(distance(center, point) - radius, 0);
    }

}
