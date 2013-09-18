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
                double o1dist = Vector.distance(point, o1);
                double o2dist = Vector.distance(point, o2);

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
            //long startTime = System.currentTimeMillis();
            BallNode ball = balls.poll(); //get S
            if (points.size() >= k){ //if there are at least k points in the points PriorityQueue
                List<Double> tempPoint = points.peek(); //get the first point, S, in points
                //if S is further from d than the first point in N is from d
                if ((ball.distanceTo(point)) > (Vector.distance(tempPoint, point))){

                    return points; //return N
                }
            }
            if (ball.isLeaf()){
                //add each point in S to N
                for(List<Double> pt : ball.points.data){
                    points.add(pt);
                }
                while(points.size() > k){
                    //discard the first point in N
                    points.poll();
                }
            }
            else{
                //add each child ball in S to Q
                balls.add(ball.left);
                balls.add(ball.right);
            }
            //System.out.println("findNeighbors: " + (System.currentTimeMillis() - startTime));
        }
        //throw new MLException("Error in KNN logic, this point was not supposed to be reached");
        return points;
    }


}
