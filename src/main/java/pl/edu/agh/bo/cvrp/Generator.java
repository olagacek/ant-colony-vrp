package main.java.pl.edu.agh.bo.cvrp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Mati on 2017-05-21.
 */
public class Generator {

    File file;
    String m_path;
    Scanner scanner;
    int dimensions;
    int cap;
    double[][] distanceMatrix;
    int[][] cords;
    int[] demands;

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
            if(scanner.nextLine().split(" ")[0].equals("DIMENSION")){
                dimensions = Integer.parseInt(scanner.nextLine().split(" ")[2]);
                distanceMatrix = new double[dimensions][dimensions];
                cords = new int[dimensions][2];
                demands = new int[dimensions];
            }
            if(scanner.nextLine().split(" ")[0].equals("CAPACITY")){
                cap = Integer.parseInt(scanner.nextLine().split(" ")[2]);
            }
            if(scanner.nextLine().split(" ")[0].equals("NODE_COORD_SECTION")){
                String line = scanner.nextLine();
                int counter = 0;
                while(!line.equals("DEMAND_SECTION")){
                    cords[counter][0] = Integer.parseInt(line.split(" ")[1]);
                    cords[counter][1] = Integer.parseInt(line.split(" ")[2]);
                    counter++;
                }
                counter = 0;
                while(!line.equals("DEPOT_SECTION")){
                    demands[counter] = Integer.parseInt(line.split(" ")[1]);
                    counter++;
                }
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



        }
    }

    double distance(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }
}
