package pl.edu.agh.bo.cvrp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Mati on 2017-05-21.
 */
public class Generator {

    private File file;
    private String m_path;
    private Scanner scanner;
    private int dimensions;
    private int cap;
    private double[][] distanceMatrix;
    private int[][] cords;
    private int[] demands;

    Generator(String path){
        m_path = path;
    }

    public void init(){
        file = new File(m_path);
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        while(scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            if(nextLine.split(" ")[0].equals("DIMENSION")){
                dimensions = Integer.parseInt(nextLine.split(" ")[2]);
                distanceMatrix = new double[dimensions][dimensions];
                cords = new int[dimensions][2];
                demands = new int[dimensions];
            }
            if(nextLine.split(" ")[0].equals("CAPACITY")){
                cap = Integer.parseInt(nextLine.split(" ")[2]);
            }
            if(nextLine.split(" ")[0].equals("NODE_COORD_SECTION")){
                int counter = 0;
                nextLine = scanner.nextLine();
                while(!nextLine.equals("DEMAND_SECTION")){
                    cords[counter][0] = Integer.parseInt(nextLine.split(" ")[1]);
                    cords[counter][1] = Integer.parseInt(nextLine.split(" ")[2]);
                    counter++;
                    nextLine = scanner.nextLine();
                }
                for(int i=0; i< dimensions; i++){
                    for(int j=0; j< dimensions; j++){
                        if(i==j){
                            distanceMatrix[i][j] = 0;
                        }
                        else{
                            distanceMatrix[i][j] = distance(cords[i][0], cords[i][1],cords[j][0], cords[j][1]);
                        }
                    }
                }
                counter = 0;
                nextLine= scanner.nextLine();
                while(!nextLine.equals("DEPOT_SECTION")){
                    demands[counter] = Integer.parseInt(nextLine.split(" ")[1]);
                    counter++;
                    nextLine = scanner.nextLine();
                }
            }




        }
        generateOutputFile();
    }

    double distance(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }

    void generateOutputFile(){
        try{
            PrintWriter writer = new PrintWriter("test_src/input.txt", "UTF-8");
            for(int i=0; i< dimensions; i++){
                String line ="";
                for(int j=0; j< dimensions; j++){
                    line=line+distanceMatrix[i][j]+" ";
                }
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("error during creating file: "+e);
        }
    }

    public int Dimensions(){return dimensions;}
    public int[] Demands(){return demands;}
    public int Capacity(){return cap;}
}
