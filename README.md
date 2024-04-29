# Lake-Finder

The numbers in the input files are the altitudes of that certain coordinate. We are trying to understand where can lakes form after a hypothetical amount of rain.

Summary:
-	Read the file to create the Terrain object. (Check input files.)
-	Get 10 inputs and modify the Terrain object. (also in inputs)
-	Go through every point in the coordinate to check if they can reach the border without going upwards with borderReacher method.
-	borderReacher :,
1.	Store the initial index that started the method.
2.	Go to all of the unvisited neighbors with heights not higher than the initial index height.
3.	If a point is on the border, record the initial index as it can reach the border. (Meaning no water will stay on it)(No lake possibility),
4.	The remaining points are eventually lakes.
-	Check each lake point to see if any two of them are neighbors. If so record it and name them the same. 
-	Name the lakes accordingly and print the terrain. (A, B, C... AA, AB ... ZZ)
-	Check for each lake's non-lake neighbors and find the shortest neighbor (This = Lake's maximum altitude), calculate volume and score accordingly.




!!!!! Check example inputs and outputs for easier understanding of the logic.
