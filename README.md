# My Personal Project: Rubik's cube algorithms

## A Library

What the application will do:
- Allow the user to input a sequence of moves (an algorithm) and stores it under a name.
- Allow the user to input times it took for the user to execute those algorithms
- Allow the user to see how many moves each algorithm consists of.
- Displays the turns per second of an individual execution
- Allow the user to store algorithms under different libraries (3x3, 2x2, 4x4, etc.)
- If possible (with enough help from a TA), display a 3D model of a rubik's cube (just a 3x3) rotating and everything.

Who will use it?
- Ardent **cubers** like me.

Why is this project of interest to me?
- I am a cuber that is really passionate about *algorithms*. Coincidentally, computer science also has lots of *algorithms*.
- I thought I'd be able to combine two things I really enjoy doing into one project for this course!

User Stories:
- As a user, I want to be able to create multiple libraries each with a specified name.
- As a user, I want to be able to add multiple algorithms to a specified library.
- As a user, I want to be able to view all my libraries
- As a user, I want to be able to view all my algorithms in a specified library.
- As a user, I want to be able to add my times to an algorithm.
- As a user, I want to be able to see my times for a specific algorithm.
- As a user, I want to be able to delete an algorithm.
- As a user, I want to be able to delete a library.
- As a user, I want to be able to see how long an algorithm is.
- As a user, I want to be able to see the statistics of my times for a specific algorithm.

- As a user, I want to be able to save my libraries to file
- As a user, I want to be able to be able to load my libraries from a file 
- As a user, when I select the quit option from the application menu, I want to be reminded to save my libraries to a 
file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my libraries from a file.

- As a user, I want to be able to see my algorithm played with a 3D simulation cube.

##Phase 4: Task 2
I chose the bi-directional association, and it is between the Algorithm and Library classes in the model package.

##Phase 4: Task 3
If I had more time, I would need to refactor the relationships between Algorithm, Library, and AlgorithmApp 
because the coupling is high. A change in Algorithm would result in needed changes in Library and AlgorithmApp,
and a change Library would result in needed changes in Algorithm and AlgorithmApp.
I could've avoided this by using the composite design pattern. This is because I technically have nodes and leaves.
Each library could have either a list of composites (libraries), or a list of leaf nodes (algorithms). 
This would mean AlgorithmApp would only need both a composite and a list of composites. Which means that the AlgorithmApp 
does not need to be related to Algorithm anymore, resulting in less coupling.
Since I have a bi-directional relationship between Algorithm and Library, if I want to move from an Algorithm
to a Library, I would just need to set the current composite to the algorithm's parent library.