package main.java.pl.edu.agh.bo.cvrp;

import main.java.pl.edu.agh.bo.ants.AntGraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by Mati on 2017-05-21.
 */
public class TestCVRP {
    private static Random s_ran = new Random(System.currentTimeMillis());

    static public double[][] create2DDoubleMatrixFromFile(Path path) throws IOException {
        return Files.lines(path)
                .map((l)->l.trim().split("\\s+"))
                .map((sa)-> Stream.of(sa).mapToDouble(Double::parseDouble).toArray())
                .toArray(double[][]::new);
    }

    public static void main(String[] args) {
        // Print application prompt to console.
        System.out.println("AntColonySystem for TSP");

//        if (args.length < 8) {
//            System.out.println("Wrong number of parameters");
//            return;
//        }

        int nAnts = 0;
        int nNodes = 0;
        int nIterations = 0;
        int nRepetitions = 0;
        int[] demand = null;
        double d[][] = null;
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-a")) {
                nAnts = Integer.parseInt(args[i + 1]);
                System.out.println("Ants: " + nAnts);
            } else if (args[i].equals("-n")) {
                nNodes = Integer.parseInt(args[i + 1]);
                System.out.println("Nodes: " + nNodes);
            } else if (args[i].equals("-i")) {
                nIterations = Integer.parseInt(args[i + 1]);
                System.out.println("Iterations: " + nIterations);
            } else if (args[i].equals("-r")) {
                nRepetitions = Integer.parseInt(args[i + 1]);
                System.out.println("Repetitions: " + nRepetitions);
            } else if (args[i].equals("-ag")) {
                d = new double[nNodes][nNodes];
                for (int k = 0; k < nNodes; i++)
                    for (int l = k + 1; l < nNodes; l++) {
                        d[k][l] = s_ran.nextDouble();
                        d[l][k] = d[k][l];
                    }
            } else if (args[i].equals("-file")) {
                String fileName = args[i + 1];
                try {
                    d = create2DDoubleMatrixFromFile(Paths.get("test_src/"+fileName));
//                    for( int g = 0; g<nNodes; g++){
//                        for(int h = 0; h<nNodes; h++){
//                            System.out.print(d[g][h]+" ");
//                        }
//                        System.out.print("\n");
//                    }

                } catch (java.io.IOException ex) {
                    System.out.println("input file not found");
                }
            } else if (args[i].equals("-d")) {
                demand = new int[nNodes];
                System.out.print("Demands:");
                int index = i;
                for (int j = 1; j < nNodes; j++) {
                    demand[j] = Integer.parseInt(args[index + 1]);
                    index++;
                    System.out.print(" " + demand[j]);
                }
                System.out.print("\n");
                demand[0]=0;
            }else if (args[i].equals("-ad")){
                demand = new int[nNodes];
                System.out.print("Demands:");
                for (int j = 1; j < nNodes; j++) {
                    demand[j] = s_ran.nextInt(10 - 1) + 1;
                    System.out.print(" " + demand[j]);
                }
                System.out.print("\n");
                demand[0] = 0;
            }
        }

            if (nAnts == 0 || nNodes == 0 || nIterations == 0 || nRepetitions == 0) {
                System.out.println("One of the parameters is wrong");
                return;
            }


            AntGraph graph = new AntGraph(nNodes, d, demand);

            try {
                ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream("" + nNodes + "_antgraph.bin"));
                outs.writeObject(graph);
                outs.close();


                FileOutputStream outs1 = new FileOutputStream("" + nNodes + "_antgraph.txt");

                for (int i = 0; i < nNodes; i++) {
                    for (int j = 0; j < nNodes; j++) {
                        outs1.write((graph.delta(i, j) + ",").getBytes());
                    }
                    outs1.write('\n');
                }

                outs1.close();

                PrintStream outs2 = new PrintStream(new FileOutputStream("" + nNodes + "x" + nAnts + "x" + nIterations + "_results.txt"));

                for (int i = 0; i < nRepetitions; i++) {
                    graph.resetTau();
                    AntColonyCVRP antColony = new AntColonyCVRP(graph, nAnts, nIterations);
                    antColony.start();
                    outs2.println(i + "," + antColony.getBestPathValue() + "," + antColony.getLastBestPathIteration());
                }
                outs2.close();
            } catch (Exception ex) {
            }
        }
    }

