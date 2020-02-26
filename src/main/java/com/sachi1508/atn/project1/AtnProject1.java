package com.sachi1508.atn.project1;

import java.io.IOException;

public class AtnProject1 {

    private static final String STUDENT_ID = "2021425426";

    public static void main(final String[] args) throws IOException {

        System.out.println("Welcome to Network builder!");

        final Experiment experiment = new Experiment(STUDENT_ID);
        for (int k=3; k<=14; k++) {
            final Experiment.Result experimentResult = experiment.withParameterK(k);
            System.out.println(experimentResult);
            if (k == 3 || k == 8 || k == 14) {
                experimentResult.getGraph().saveAsPng("./result-graph-"+k+".png");
            }
        }
    }
}
