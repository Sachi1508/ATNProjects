package com.sachi1508.atn.project1;

import com.sachi1508.atn.graph.Graph;
import com.sachi1508.atn.graph.GraphBuilder;

public class Experiment {

    private final int[][] demand;

    public Experiment(final String studentId)
    {
        final int graphSize = studentId.length() * 2;
        this.demand = new int[graphSize][graphSize];
        calculateDemand(studentId);
    }

    private void calculateDemand(final String studentId)
    {
        final String doubleStudentId = studentId + studentId;
        for(int i = 0; i < demand.length; i++) {
            for(int j = 0; j < demand[i].length; j++) {
                demand[i][j] = Math.abs(doubleStudentId.charAt(i) - doubleStudentId.charAt(j));
            }
        }
    }

    public Result withParameterK(final int k)
    {
        final Graph graph = new GraphBuilder(demand.length, k).buildGraph();
        int totalCost = 0;
        for(int i = 0; i < demand.length; i++) {
            for(int j = 0; j < demand[i].length; j++) {
                if (demand[i][j] != 0) {
                    totalCost += demand[i][j] * graph.findShortestPathCost(i, j);
                }
            }
        }
        return new Result(k, totalCost, graph);
    }

    public static class Result {
        private final int experimentParam;
        private final int minCost;
        private Graph graph;

        private Result(int experimentParam, int minCost, Graph graph) {
            this.experimentParam = experimentParam;
            this.minCost = minCost;
            this.graph = graph;
        }

        public int getExperimentParam() {
            return experimentParam;
        }

        public int getMinCost() {
            return minCost;
        }

        public Graph getGraph() {
            return graph;
        }

        @Override
        public String toString() {
            return "Experiment Result{" +
                    "experimentParam=" + experimentParam +
                    ", minCost=" + minCost +
                    '}';
        }
    }
}
