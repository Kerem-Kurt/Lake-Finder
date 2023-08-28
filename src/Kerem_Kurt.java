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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Kerem_Kurt {
    public static void main(String[] args) throws FileNotFoundException {
        Terrain myTerrain = new Terrain();
        fileReader("input2.txt", myTerrain);
        myTerrain.printer();

        // 10 times for 10 correct inputs
        for (int i = 1; i<11;i++){
            myTerrain.printModification(i);
            Scanner input = new Scanner(System.in);
            String input_coordinate = input.nextLine();
            try {
                String colCoord = "";
                String rowCoord = "";
                for (int k = 0; k <input_coordinate.length(); k++){
                    char theCharacter = input_coordinate.charAt(k);
                    try {;
                        int tempNum = Integer.parseInt(String.valueOf(theCharacter));
                        rowCoord += theCharacter;

                    }catch (Exception e){;
                        colCoord += theCharacter;
                    }
                }
                if (colCoord == ""){
                    Integer.parseInt("k");
                } else if (rowCoord == "") {
                    Integer.parseInt("k");
                }
                myTerrain.modificator(colCoord,rowCoord);
            }catch (Exception e){
                i -= 1;
                System.out.println("Not a valid step!");
                continue;
            }
            myTerrain.printer();
            myTerrain.printLine();
        }

        // Turning the actual array to string to name the lakes
        ArrayList<Integer> solutionList = new ArrayList<Integer>();
        for (int i = 0; i < myTerrain.terrainArray.length;i++){
            myTerrain.terrainStringArray[i] = Integer.toString(myTerrain.terrainArray[i]);
            solutionList.add(i);
        }

        // Creating the border
        for(int i = 1; i < myTerrain.terrainWidth;i++){
            myTerrain.borderArrayList.add(i);
            myTerrain.borderArrayList.add((myTerrain.terrainHeight * myTerrain.terrainWidth)-i);
        }
        for (int i = 0; i <myTerrain.terrainHeight; i++){
            myTerrain.borderArrayList.add((myTerrain.terrainWidth*i));
            myTerrain.borderArrayList.add((myTerrain.terrainWidth*i) + myTerrain.terrainWidth - 1);
        }
        Collections.sort(myTerrain.borderArrayList);

        // BOOLEAN ARRAY FOR RECURSIVE SEARCH OPERATION
        boolean[] tempVisited = myTerrain.visited.clone();

        // CHECKING IF A CERTAIN POINT CAN REACH THE BORDERS WITH ONLY GOING VERTICAL AND DECREASING
        for (int i = 0; i < myTerrain.terrainWidth * myTerrain.terrainHeight; i++){
            myTerrain.borderReacher(i,myTerrain.visited,myTerrain.terrainArray[i],i);
            myTerrain.visited = tempVisited.clone();
        }

        // MAKING THE LIST EASIER TO UNDERSTAND
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i<myTerrain.borderConnected.size(); i++){
            if (!temp.contains(myTerrain.borderConnected.get(i))){
                temp.add(myTerrain.borderConnected.get(i));
            }
        }
        solutionList.removeAll(temp);

        // PRINT THE LAKE VERSION OF THE TERRAIN
        myTerrain.lakePrinter(solutionList);

        // STORE VOLUMES 2D WITH TEMP ARRAY LIST
        ArrayList<ArrayList<Integer>> volumeList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> tempNeighbours = new ArrayList<Integer>();

        // CREATING THE ARRAYLIST TO STORE LAKE COORDINATES (GROUPED)
        for (int i = 0; i < solutionList.size(); i++){
            if (solutionList.size() == 1){
                tempNeighbours.add(solutionList.get(0));
                volumeList.add(tempNeighbours);
                continue;
            }
            if (i == 0){
                tempNeighbours.add(solutionList.get(0));
                continue;
            }
            if (myTerrain.lakeNames[i] == myTerrain.lakeNames[i-1]){
                tempNeighbours.add(solutionList.get(i));
            }else {
                volumeList.add(tempNeighbours);
                tempNeighbours = new ArrayList<Integer>();
                tempNeighbours.add(solutionList.get(i));
            }
            if (i == solutionList.size()-1) {
                volumeList.add(tempNeighbours);
            }
        }

        // FINDING THE SCORE THROUGH GROUPED LAKE INDEXES
        int tempVolume = 0;
        double totalScore = 0.0;
        ArrayList<Integer> borderHeights = new ArrayList<Integer>();
        for (ArrayList<Integer> tempArray:volumeList){
            for (int i:tempArray){
                int[] indexList = {i - myTerrain.terrainWidth - 1, i - myTerrain.terrainWidth, i - myTerrain.terrainWidth + 1,
                        i - 1, i + 1 ,
                        i + myTerrain.terrainWidth - 1, i + myTerrain.terrainWidth, i + myTerrain.terrainWidth + 1};
                for (int k: indexList){
                    if (solutionList.contains(k)){
                        continue;
                    }else {
                        borderHeights.add(myTerrain.terrainArray[k]);
                    }
                }
            }
            int minHeight = Collections.min(borderHeights);
            for (int i:tempArray){
                int heightDifference = minHeight - myTerrain.terrainArray[i];
                tempVolume = tempVolume + heightDifference;
            }
            totalScore = totalScore + Math.sqrt(tempVolume);
            tempVolume = 0;
            borderHeights = new ArrayList<Integer>();
        }

        System.out.println();
        System.out.println("Final score: " +  String.format("%.2f", totalScore));

    }


    // Method to read the file and assign the values to the myTerrain Object
    public static void fileReader(String fileName, Terrain myTerrain) throws FileNotFoundException {

        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        myTerrain.terrainWidth = inputFile.nextInt();
        myTerrain.terrainHeight = inputFile.nextInt();
        myTerrain.terrainArray = new int[myTerrain.terrainHeight * myTerrain.terrainWidth];
        myTerrain.visited = new boolean[myTerrain.terrainWidth * myTerrain.terrainHeight];
        myTerrain.terrainStringArray = new String[myTerrain.terrainWidth * myTerrain.terrainHeight];
        Arrays.fill(myTerrain.visited,Boolean.TRUE);
        // READING THE FILE
        for (int i = 0; i < myTerrain.terrainArray.length; i++) {
            myTerrain.terrainArray[i] = inputFile.nextInt();
        }
    }


}