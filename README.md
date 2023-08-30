# Lake-Finder

The numbers in the input files are the altitudes of that certain coordinate.

Summary:
-	Read the file to create the Terrain object.
-	Get 10 inputs and modify the Terrain object.
-	Go through every point in the coordinate to check if they can reach the border without going upwards with borderReacher method.
-	borderReacher :,
1.	Store the initial index which started the method.
2.	Go to all of the unvisited neighbors with height not higher than the initial indexes height.
3.	If a point is on the border record the initial index as it can reach the border. (Meaning no water will stay on it)(No lake possibility),
4.	The remaining points are eventually lakes.
-	Check each lake point to see if any two of them are neighbors. If so record it and name them the same.
-	Name the lakes accordingly and print the terrain.
-	Check for each lakes non-lake neighbors and find the shortest neighbor calculate volume and score accordingly.


!!!!! Example outputs are provided in the repository
