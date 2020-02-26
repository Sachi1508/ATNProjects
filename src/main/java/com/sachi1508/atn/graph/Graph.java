package com.sachi1508.atn.graph;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    // TODO: improvement move to JGraphT
    private final int[][] adjacencyMatrix;

    /*
        Access set as package private so that only the Graph builder
        could access it to set them during construction.
     */
    Graph(final int size, final int defaultWeight)
    {
        adjacencyMatrix = new int[size][size];

        for (int i=0; i<size; i++) {
            Arrays.fill(adjacencyMatrix[i], defaultWeight);
            adjacencyMatrix[i][i] = 0;
        }
    }

    Graph(final int size)
    {
        this(size, 0);
    }

    /*
        Access set as package private so that only the Graph builder
        could access it to set them during construction.
     */
    void setEdgeWeight(int source, int destination, int weight)
    {
        validateSourceDestination(source, destination);
        adjacencyMatrix[source][destination] = weight;
    }

    public int findShortestPathCost(int source, int destination)
    {
        validateSourceDestination(source, destination);

        final int[] weight = new int[adjacencyMatrix.length];
        Arrays.fill(weight, Integer.MAX_VALUE);
        weight[source] = 0;
        final Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        while (!queue.isEmpty())
        {
            int current = queue.poll();
            for(int i = 0; i < adjacencyMatrix.length; i++)
            {
                if(weight[current] + adjacencyMatrix[current][i] < weight[i]) {
                    weight[i] = weight[current] + adjacencyMatrix[current][i];
                    if (!queue.contains(i)) {
                        queue.offer(i);
                    }
                }
            }
        }
        return weight[destination];
    }

    public void saveAsPng(final String path) throws IOException {

        final SimpleDirectedWeightedGraph<Integer, MyEdge> printableGraph =
                new SimpleDirectedWeightedGraph<>(MyEdge.class);

        for (int i=0; i<adjacencyMatrix.length; i++) {
            printableGraph.addVertex(i);
        }

        for (int i=0; i<adjacencyMatrix.length; i++) {

            for (int j=0; j<adjacencyMatrix[i].length; j++) {
                if (i != j) {
                    final MyEdge edge = printableGraph.addEdge(i, j);
                    printableGraph.setEdgeWeight(edge, adjacencyMatrix[i][j]);
                }
            }
        }

        final File imgFile = new File(path);

        if (imgFile.exists()) {
            imgFile.delete();
        }

        if (imgFile.createNewFile()) {
            final JGraphXAdapter<Integer, MyEdge> graphAdapter =
                    new JGraphXAdapter<>(printableGraph);
            final mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
            layout.execute(graphAdapter.getDefaultParent());

            BufferedImage image =
                    mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
            ImageIO.write(image, "PNG", imgFile);
        }
    }

    public static class MyEdge extends DefaultWeightedEdge {
        @Override
        public String toString() {
            return "";
        }
    }

    private void validateSourceDestination(final int source, final int destination) {
        if (source > adjacencyMatrix.length ||
                destination > adjacencyMatrix.length)
        {
            throw new IllegalArgumentException(source + " or " + destination + " not in the graph.");
        }
    }
}
