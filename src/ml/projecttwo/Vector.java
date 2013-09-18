package ml.projecttwo;

import ml.*;
import java.util.*;
import static java.lang.Math.*;

/**
 * Various vector operations
 */
public class Vector {

    /**
     * Calculates the magnitude of a vector (aka  Euclidean length))
     *
     * Formula: ||X|| = sqrt( x1^2 + x2^2 + x3^2 + ... + xn^2 )
     */
    public static double magnitude(List<Double> point) {
        double sum = 0;
        int size = point.size();
        for (int i = 0; i < size; i++) {
            double val = point.get(i);
            sum += val * val;
        }
        return sqrt(sum);
    }

    /**
     * Calculates the distance between two vectors (aka Euclidean distance)
     *
     * Formula: d(p, q) = d(q, p) = sqrt( sum[i->n](pi - qi)^2  )
     */
    public static double distance(List<Double> pointA, List<Double> pointB) {
        List<Double> difference = subtract(pointA, pointB);
        return magnitude(difference);
    }

    public static List<Double> add(List<Double> pointA, List<Double> pointB) {
        return addAndMultiply(pointA, pointB, 1);
    }

    public static List<Double> addAndMultiply(List<Double> pointA, List<Double> pointB, double scalar) {
        return addAndMultiply(pointA, 1, pointB, 1, scalar);
    }

    public static List<Double> subtract(List<Double> pointA, List<Double> pointB) {
        return subtractAndMultiply(pointA, pointB, 1);
    }

    public static List<Double> subtractAndMultiply(List<Double> pointA, List<Double> pointB, double scalar) {
        return addAndMultiply(pointA, 1, pointB, -1, scalar);
    }

    /**
     * //TODO: Does this belong here or in Matrix?
     * Finds the furthest point in the matrix from the given point
     * Uses the Euclidean distance
     * @param points Matrix
     * @param point
     * @return
     */
    public static List<Double> furthestPoint(Matrix points, List<Double> point) {
        double furthestDistance = Double.NEGATIVE_INFINITY;
        int furthestIndex = -1;
        int size = points.getNumRows();
        for (int i = 0; i < size; i++) {
            List<Double> row = points.getRow(i);
            double dist = distance(point, row);
            if (dist > furthestDistance) {
                furthestDistance = dist;
                furthestIndex = i;
            }
        }
        return points.getRow(furthestIndex);
    }

    /**
     * //TODO: Does this belong here or in Matrix?
     * Finds the closest point in the matrix from the given point
     * Uses the Euclidean distance
     * @param points Matrix
     * @param point
     * @return
     */
    public static List<Double> closestPoint(Matrix points, List<Double> point) {
        double closestDistance = Double.POSITIVE_INFINITY;
        int closestIndex = -1;
        int size = points.getNumRows();
        for (int i = 0; i < size; i++) {
            List<Double> row = points.getRow(i);

            // Ignore the same point
            if (point.equals(row)) {
                continue;
            }

            double dist = distance(point, row);
            if (dist < closestDistance) {
                closestDistance = dist;
                closestIndex = i;
            }
        }
        return points.getRow(closestIndex);
    }

    public static List<Double> addAndMultiply(List<Double> pointA, double coefA,
                                               List<Double> pointB, double coefB, double scalar) {
        int size = pointA.size();
        if (size != pointB.size()) {
            throw new MLException(String.format(
                    "Vector sizes mismatch. |A|=%d, |B|=%d", size, pointB.size()));
        }

        List<Double> pointC = new ArrayList<Double>();
        for (int i = 0; i < size; i++) {
            double a = coefA * pointA.get(i);
            double b = coefB * pointB.get(i);
            pointC.add(scalar * (a + b));
        }
        return pointC;
    }

}
