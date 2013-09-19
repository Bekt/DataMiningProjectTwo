package ml.projecttwo;

import ml.Matrix;
import java.util.*;
import static ml.projecttwo.Vector.*;

public class BallTree {

    private BallNode root;

    Map<List<Double>, Double> pointsMap = new HashMap<List<Double>, Double>();
    Map<BallNode, Double> distanceToMap = new HashMap<BallNode, Double>();

    public BallTree(Matrix points, int k) {
        root = BallNode.buildBallNode(points, k);
    }

    public Queue<List<Double>> findNeighbors(int k, List<Double> point) {

        Queue<List<Double>> pointsN = getPointsQueue(point);
        Queue<BallNode> ballsQueueQ = getBallsQueue(point);
        ballsQueueQ.add(root);

        while (!ballsQueueQ.isEmpty()) {
            BallNode ballS = ballsQueueQ.poll();
            if (pointsN.size() >= k) {
                if (ballS.distanceTo(point) > distance(pointsN.peek(), point)) {
                    return pointsN;
                }
            }
            if (ballS.isLeaf()) {
                for (List<Double> p : ballS.points.data) {
                    pointsN.add(p);
                }
                while (pointsN.size() > k) {
                    pointsN.poll();
                }
            } else {
                ballsQueueQ.add(ballS.left);
                ballsQueueQ.add(ballS.right);
            }
        }
        return pointsN;
    }

    private Queue<List<Double>> getPointsQueue(final List<Double> pointD) {

        return new PriorityQueue<List<Double>>(10, new Comparator<List<Double>>() {
            @Override
            public int compare(List<Double> o1, List<Double> o2) {

                double o1dist = getSquaredDistance(pointD, o1);
                double o2dist = getSquaredDistance(pointD, o2);

                // Max to min
                if (o1dist < o2dist) return 1;
                if (o1dist == o2dist) return 0;
                return -1;
            }
        });
    }

    private Queue<BallNode> getBallsQueue(final List<Double> pointD) {

        return new PriorityQueue<BallNode>(10, new Comparator<BallNode>() {
            @Override
            public int compare(BallNode o1, BallNode o2) {
                double o1dist = getDistanceTo(o1, pointD);
                double o2dist = getDistanceTo(o2, pointD);

                // Min to max
                if (o1dist > o2dist) return 1;
                if (o1dist == o2dist) return 0;
                return -1;
            }
        });
    }

    private double getSquaredDistance(List<Double> point, List<Double> target) {
        Double distance = pointsMap.get(target);
        if (distance == null) {
            distance = squaredDistance(point, target);
            pointsMap.put(target, distance);
        }
        return distance;
    }

    private double getDistanceTo(BallNode node, List<Double> point) {
        Double distanceTo = distanceToMap.get(node);
        if (distanceTo == null) {
            distanceTo = node.distanceTo(point);
            distanceToMap.put(node, distanceTo);
        }
        return distanceTo;
    }

}
