package ml.projecttwo;

import ml.ARFFParser;
import ml.Matrix;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ml.projecttwo.Vector.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Matrix points = ARFFParser.loadARFF("/Users/dev/workspace/DataMiningProjectTwo/brute.arff");
        List<Double> input = Arrays.asList(70.0, 70.0, 70.0);
        Set<Integer> out = slowKnn(points, 2, input);
        for (int i : out) {
            System.out.println(points.getRow(i));
        }
    }

    /**
     * Returns the indecies of k-rows in the matrix
     */
    static Set<Integer> slowKnn(Matrix points, int k, List<Double> point) {
        Set<Integer> indecies = new HashSet<Integer>();
        for (int i = 0; i < k; i++) {

            double closestDistance = Double.POSITIVE_INFINITY;
            int closestIndex = -1;
            for (int j = 0; j < points.getNumRows(); j++) {
                List<Double> row = points.getRow(j);

                double dist = distance(point, row);
                if (dist < closestDistance && !indecies.contains(j)) {
                    closestDistance = dist;
                    closestIndex = j;
                }
            }
            indecies.add(closestIndex);
        }
        return indecies;
    }

}
