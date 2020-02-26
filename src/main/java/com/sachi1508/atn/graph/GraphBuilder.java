package com.sachi1508.atn.graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphBuilder {
    private static final int LOW_COST = 1;
    private static final int HIGH_COST = 100;

    private final Graph graph;
    private final int k;
    private final int size;

    public GraphBuilder(final int size, final int k)
    {
        this.graph = new Graph(size, HIGH_COST);
        this.k = k;
        this.size = size;
    }

    /* ************************************************** Module 1 *********************************************/
    public Graph buildGraph()
    {
        final Random random = new Random();
        for(int i = 0; i < size; i++)
        {
            final int node = i;
            final Set<Integer> closeNeighbours = new HashSet<>(k);

            // creating k random indices all different from each other
            while (closeNeighbours.size() != k)
            {
                int newRandom = random.nextInt(size);
                if (newRandom != node)
                {
                    closeNeighbours.add(newRandom);
                }
            }
            closeNeighbours.forEach(closeNeighbour -> this.graph.setEdgeWeight(node, closeNeighbour, LOW_COST));
        }
        return graph;
    }
    /* ************************************************** End Module 1 *******************************************/
}
