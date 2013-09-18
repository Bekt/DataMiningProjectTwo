package ml.projecttwo;

import ml.MLException;
import ml.Matrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BallTree {

    BallNode root;

    public BallTree(Matrix points) {
        root = BallNode.buildBallNode(points);
    }

    public PriorityQueue<List<Double>> findNeighbors(int k, final List<Double> point) {
        PriorityQueue<List<Double>> points = new PriorityQueue<List<Double>>(10, new Comparator<List<Double>>() {
            @Override
            public int compare(List<Double> o1, List<Double> o2) {
                //find Euclidean distances from point
                double o1dist = Vector.squaredDistance(point, o1);
                double o2dist = Vector.squaredDistance(point, o2);

                //rank in reverse (max to min) order
                if (o1dist < o2dist) return 1;
                if (o1dist == o2dist) return 0;
                return -1;
            }
        });
        PriorityQueue<BallNode> balls = new PriorityQueue<BallNode>(10, new Comparator<BallNode>() {
            @Override
            public int compare(BallNode o1, BallNode o2) {
                double o1dist = o1.distanceTo(point);
                double o2dist = o2.distanceTo(point);

                //rank in min to max order
                if (o1dist > o2dist) return 1;
                if (o1dist == o2dist) return 0;
                return -1;
            }
        });

        balls.add(root);
        while (!balls.isEmpty()){
            BallNode ball = balls.poll();
            if (points.size() >= k){
                List<Double> tempPoint = points.peek();
                if ((ball.distanceTo(point)) > (Vector.distance(tempPoint, point))){
                    return points;
                }
            }
            if (ball.isLeaf()){
                for (List<Double> p : ball.points.data) {
                    points.add(p);
                }
                while(points.size() > k){
                    points.poll();
                }
            } else{
                balls.add(ball.left);
                balls.add(ball.right);
            }
        }
        return points;
    }

}
