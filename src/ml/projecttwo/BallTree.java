package ml.projecttwo;

import ml.Matrix;
import java.util.*;
import static ml.projecttwo.Vector.*;

public class BallTree {

    private BallNode root;

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

            Map<List<Double>, Double> distanceMap = new HashMap<List<Double>, Double>();

            @Override
            public int compare(List<Double> o1, List<Double> o2) {
                double o1dist = getDistance(o1);
                double o2dist = getDistance(o2);

                // Max to min
                if (o1dist < o2dist) return 1;
                if (o1dist == o2dist) return 0;
                return -1;
            }

            private double getDistance(List<Double> point) {
                Double distance = distanceMap.get(point);
                if (distance == null) {
                    distance = squaredDistance(pointD, point);
                    distanceMap.put(point, distance);
                }
                return distance;
            }
        });
    }

    private Queue<BallNode> getBallsQueue(final List<Double> pointD) {

        return new PriorityQueue<BallNode>(10, new Comparator<BallNode>() {

            Map<BallNode, Double> distanceToMap = new HashMap<BallNode, Double>();

            @Override
            public int compare(BallNode o1, BallNode o2) {
                double o1dist = getDistanceTo(o1);
                double o2dist = getDistanceTo(o2);

                // Min to max
                if (o1dist > o2dist) return 1;
                if (o1dist == o2dist) return 0;
                return -1;
            }

            private double getDistanceTo(BallNode node) {
                Double distanceTo = distanceToMap.get(node);
                if (distanceTo == null) {
                    distanceTo = node.distanceTo(pointD);
                    distanceToMap.put(node, distanceTo);
                }
                return distanceTo;
            }
        });
    }

}
