package ml.projecttwo;

import helpers.Rand;
import ml.Matrix;
import java.util.ArrayList;
import java.util.List;

public abstract class Ball {

    List<Double> center = new ArrayList<Double>();
    double radius;

    public abstract boolean isLeaf();

    public void findBoundingBall(Matrix points) {
        int randomIndex = Rand.nextInt(points.getNumRows());

        List<Double> pointA = points.getRow(randomIndex);


        throw new UnsupportedOperationException("Not implemented");
    }

    public double distanceTo(List<Double> point) {
        throw new UnsupportedOperationException("Not implemented");
    }

    // TODO: Move this to somewhere where it makes sense
    public static List<Double> furthestPoint(Matrix points, List<Double> point) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
