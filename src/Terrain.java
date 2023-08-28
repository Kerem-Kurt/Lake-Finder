/*
    Name: Kerem Haktan Kurt
    Date: 16.05.2023
    Summary:
        - Reads the file and creates the Terrain object.
        - Takes the input 10 "correct" times. (Modificates the terrain object simultaneously)
        - Creates solutionList and borderList for later operations.
        - Calls borderReacher method to check border reachability of each and every point in the terrain.
            ** borderReacher: recursive function which always directs the point towards coordinates with the same
                              or shorter coordinates until it can not go further. If point can reach any border point
                              it is added to the borderConnected array.
        - Finds the solutions: removes borderConnected array from all terrain coordinates.
        - Names the lakes according to if a spesific solution neighbours with another solution: Checks for each solution
                 to see if they have a neighbouring solution. If true then name those two the same.
        - Finds the score by checking neighbouring border height and finding the shortest one: Check border around the
                 solutions in the lake to see the shortest border. Then calculate accordingly.
 */

import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Terrain {
    int terrainWidth;
    int terrainHeight;
    int[] terrainArray;
    String[] terrainStringArray;
    String[] letterArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    String[] notionsArray = new String[26 * 27]; // NOTIONS FOR COORDINATES
    boolean[] visited; // BOOL LIST FOR borderReacher METHOD
    ArrayList<Integer> borderArrayList = new ArrayList<Integer>(); // STORE BORDERS
    ArrayList<Integer> borderConnected = new ArrayList<Integer>(); // STORES POINTS CONNECTED TO BORDERS

    //LAKE PROPERTIES
    String[] lakeLetterArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    String[] lakeNotionsArray = new String[26*27]; // NAMING THE LAKES PROPERLY
    int lakeCounter = 0; // COUNT THE LAKES
    String[] lakeNames; // ARRAY TO LIST NAMES IN ORDER

    //CHANGE THE ROW AND COORDINATE +1
    public void modificator(String column, String row) {
        if (indexFinder(column) != -1) {
            int rowValue = Integer.parseInt(row);
            int colIndex = indexFinder(column);
            if (colIndex > terrainWidth - 0.5) {
                Integer.parseInt("k");
            }
            terrainArray[terrainWidth * rowValue + colIndex] = terrainArray[terrainWidth * rowValue + colIndex] + 1;
        }

    }


    //PRINT THE CURRENT TERRAIN IN THE DESIRED FORMAT
    public void printer() {

        for (int i = -1; i < 26; i++) {
            for (int a = 0; a < 26; a++) {
                if (i == -1) {
                    notionsArray[a] = letterArray[a];
                    continue;
                }
                notionsArray[(i + 1) * 26 + a] = letterArray[i] + letterArray[a];
            }
        }

        for (int i = -1;i<26;i++){
            for (int a = 0;a<26;a++){
                if (i == -1){
                    lakeNotionsArray[a] = lakeLetterArray[a];
                    continue;
                }
                lakeNotionsArray[(i+1)*26 + a] = lakeLetterArray[i] + lakeLetterArray[a];
            }
        }

        for (int i = 0; i < terrainArray.length; i++) {
            if (i % terrainWidth == 0) {
                if (i != 0) {
                    System.out.println();
                }
                if (i / terrainWidth < 9.5) {
                    System.out.print("  " + i / terrainWidth + " ");
                } else if (i / terrainWidth < 99.5) {
                    System.out.print(" " + i / terrainWidth + " ");
                } else {
                    System.out.print(i / terrainWidth + " ");
                }
            }
            if (terrainArray[i] > 9.8) {
                System.out.print(terrainArray[i] + " ");
            } else {
                System.out.print(" " + terrainArray[i] + " ");
            }
        }
        System.out.println();

        for (int i = -1; i < terrainWidth; i++) {
            if (i == -1) {
                System.out.print("    ");
            } else if (i > 25) {
                System.out.print(notionsArray[i] + " ");
            } else {
                System.out.print(" " + notionsArray[i] + " ");
            }
        }
    }

    public void printLine() {
        System.out.println();
        System.out.print("---------------");
    }

    public void printModification(int i) {
        System.out.println();
        System.out.println("Add stone " + i + " / 10 to coordinate:");
    }


    // FIND THE INDEX OF A SPESIFIC COLUMN
    public Integer indexFinder(String string) {
        for (int i = 0; i < notionsArray.length; i++) {
            if (Objects.equals(notionsArray[i], string)) {
                return i;
            }
        }
        return -1;
    }


    // CHECK IF A POINT CAN REACH THE BORDER WITHOUT GOING UPWARDS
    public void borderReacher(int index, boolean[] visited, int blockHeight,int initialIndex) {
        int[] indexList = {index - terrainWidth - 1, index - terrainWidth, index - terrainWidth + 1,
                index - 1, index + 1 ,
                index + terrainWidth - 1, index + terrainWidth, index + terrainWidth + 1};
        if (borderArrayList.contains(index)){
            borderConnected.add(initialIndex);
            return;
        }
        if (visited[index]){
            visited[index] = false;
            for (int i:indexList){
                if (blockHeight>=terrainArray[i]){
                    borderReacher(i,visited,blockHeight,initialIndex);
                }
            }
        }
    }


    // PRINT THE TERRAIN WITH PROPER LAKE NAMES
    public void lakePrinter(ArrayList<Integer> solution) {
        System.out.println();

        // creating notions array a b ... ab ac ad ...
        for (int i = -1; i < 26; i++) {
            for (int a = 0; a < 26; a++) {
                if (i == -1) {
                    notionsArray[a] = letterArray[a];
                    continue;
                }
                notionsArray[(i + 1) * 26 + a] = letterArray[i] + letterArray[a];
            }
        }

        lakeNames = lakeNamer(solution);
        lakeCounter = 0;

        for (int i = 0; i < terrainArray.length; i++) {

            if (!solution.contains(i)){
                if (i % terrainWidth == 0) {
                    if (i != 0) {
                        System.out.println();
                    }
                    if (i / terrainWidth < 9.5) {
                        System.out.print("  " + i / terrainWidth + " ");
                    } else if (i / terrainWidth < 99.5) {
                        System.out.print(" " + i / terrainWidth + " ");
                    } else {
                        System.out.print(i / terrainWidth + " ");
                    }
                }
                if (terrainArray[i] > 9.8) {
                    System.out.print(terrainArray[i] + " ");
                } else {
                    System.out.print(" " + terrainArray[i] + " ");
                }
            }else {
                if (lakeNames[lakeCounter].length()>1){
                    System.out.print(lakeNames[lakeCounter] + " ");
                    lakeCounter += 1;
                }else {
                    System.out.print(" "+ lakeNames[lakeCounter] + " ");
                    lakeCounter +=1;
                }

            }

        }
        System.out.println();

        // a b c d
        for (int i = -1; i < terrainWidth; i++) {
            if (i == -1) {
                System.out.print("    ");
            } else if (i > 25) {
                System.out.print(notionsArray[i] + " ");
            } else {
                System.out.print(" " + notionsArray[i] + " ");
            }
        }
    }


    // CREATES AN ARRRAY ACCORDING TO THE NEIGHBOURING PROPERTIES OF INDIVIDUAL LAKE POINTS
    public String[] lakeNamer(ArrayList<Integer> solution){
        String[] lakeNames = new String[solution.size()];
        Arrays.fill(lakeNames,"0");
        for (int i : solution){
            if (i == solution.get(0)){
                lakeNames[0] = lakeNotionsArray[lakeCounter];
            }
            int[] indexList = {i - terrainWidth - 1, i - terrainWidth, i - terrainWidth + 1,
                    i - 1, i + 1 ,
                    i + terrainWidth - 1, i + terrainWidth, i + terrainWidth + 1};

            for (int k : indexList){
                if (solution.contains(k)){
                    if (lakeNames[solution.indexOf(k)] != "0"){
                        lakeNames[solution.indexOf(i)] = lakeNames[solution.indexOf(k)];
                    } else if (solution.contains(k)) {
                        lakeNames[solution.indexOf(k)] = lakeNames[solution.indexOf(i)];
                    }
                }
            }if (lakeNames[solution.indexOf(i)] == "0"){
                lakeCounter += 1;
                lakeNames[solution.indexOf(i)] = lakeNotionsArray[lakeCounter];
            }

        }
        return lakeNames;
    }

}
