/**
 * Harry Kim
 * 
 * A1: An Ecological Simulation
 * 
 * This program visualizes a simulation of a predator-prey relationship represented with rabbits and foxes.
 */
 
#include <iostream>
 
using namespace std;
 
/**
 * This helper function takes in the model parameters and updates the population using the The Lotka-Volterra equation 
 * with environmental carrying-capacity modification to simulate a predator/prey population. 
 * 
 * @param g The rate of increase of rabbits.
 * @param p The fraction of prey eaten per predator.
 * @param c The fraction of eaten prey turned into new predators.
 * @param m A per capita mortality rate for foxes.
 * @param K How many rabbits can be supported by the environment.
 * @param numRabbits The current number of rabbits.
 * @param numFoxes The current number of foxes.
 */
void updatePopulations(double g, double p, double c, double m, double K, double &numRabbits, double &numFoxes)
{
    int tempNumRab = numRabbits;
    int tempNumFox = numFoxes;
    
    // The change in populations for a given population of rabbits and foxes then adding the change to the total population.
    numRabbits = (g * tempNumRab * (1 - tempNumRab / K) - p * tempNumRab * tempNumFox) + numRabbits;
    numFoxes = (c * p * tempNumRab * tempNumFox - m * tempNumFox) + numFoxes;
}
 
/**
 * This helper function takes in an int number and a character and sends to std::cout number spaces followed by the
 * character. If the number parameter is less than 1, just output the character. So, for example, plotCharacter(1,'F')
 * would produce a space on the left of the screen followed by an 'F'.
 * 
 * @param num The number of spaces the character appears from the left.
 * @param character The character used to represent the population.
 */
void plotCharacter(int num, char character)
{
    string output = "";
    for (int x = 0; x < num; x++)
    {
        output = output + " ";
    }
    output = output + character;
    std::cout << output;
}
 
/**
 * This function uses the plotCharacter function as a helper and draws a row of a text representing the population of
 * each animal with an 'F' for foxes and 'r' for rabbits and '*' if the drawing of each would overlap.
 * 
 * @param rabbits The number of rabbits.
 * @param foxes The number of foxes.
 * @param scaleFactor The number to scale each population by to reduce the size of the graph (0.1 for example).
 */
void plotPopulations(double rabbits, double foxes, double scaleFactor)
{
    rabbits = rabbits * scaleFactor;
    foxes = foxes * scaleFactor;
 
    // Rounding off populations to make into int
    int roundedRabbits = int(rabbits);
    int roundedFoxes = int(foxes);
 
    // Print rabbit population first then add the fox population to the same line.
    if (roundedRabbits < roundedFoxes)
    {
        plotCharacter(roundedRabbits, 'r');
        plotCharacter(roundedFoxes - roundedRabbits - 1, 'F');
    }
 
    // Print fox population first then add the rabbit population to the same line.
    else if (roundedFoxes < roundedRabbits)
    {
        plotCharacter(roundedFoxes, 'F');
        plotCharacter(roundedRabbits - roundedFoxes - 1, 'r');
    }
    else
    {
        plotCharacter(roundedFoxes, '*');
    }
}

/**
 * This helper function has a pointer to an integer parameter. The function adds 1 to the value pointed to by the pointer.
 * 
 * @param count The pointer to the integer parameter.
 */
void incrementCounter(int *count)
{
    // Dereferencing the pointer to modify the value at the address.
    *count = *count + 1;
}
 
/**
 * This function runs the predator/prey simulation using parameters for iterations, number of rabbits, and number of
 * foxes in that order. Rabbits and foxes should be double type for the simulation math. If a populations don't go
 * below 1, a call to runSimulation with 100 iterations should produce 101 lines - the initial population line and
 * then 100 updated values. The core of the function should be repeatedly updating the population and plotting those
 * results.
 * 
 * @param iterations The number of times the simulation should update and show a new line.
 * @param rabbits The initial number of rabbits.
 * @param foxes The initial number of foxes.
 */
string runSimulation(int iterations, double rabbits, double foxes)
{
    // The current amount of iterations.
    int currentIterations = 0;
 
    // Plotting initial population of animals.
    plotPopulations(rabbits, foxes, 0.1);
 
    // Running simulation for however many iterations.
    for (int x = 0; x < iterations; x++)
    {
        updatePopulations(0.2, 0.0022, 0.6, 0.2, 1000.0, rabbits, foxes);
 
        // Terminate program if either population drops past 0.
        if(rabbits < 0 || foxes < 0){
            return "";
        }
        plotPopulations(rabbits, foxes, 0.1);

        // Incrementing the counter.
        incrementCounter(&currentIterations);
        cout << endl;
    }
    return "";
}
 
int main()
{
    // Input for rabbit population.
    double rabbits;
    cout << "Enter initial number of rabbits: ";
    if (!(cin >> rabbits))
    {
        cout << "You have entered an invalid input" << endl;
        return 0;
    }
 
    // Input for fox population;
    double foxes;
    cout << "Enter initial number of foxes: ";
    if (!(cin >> foxes))
    {
        cout << "You have entered an invalid input" << endl;
        return 0;
    }
 
    runSimulation(500, rabbits, foxes);
    return 0;
}