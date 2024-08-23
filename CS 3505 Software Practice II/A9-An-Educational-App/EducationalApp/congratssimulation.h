/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#ifndef CONGRATSSIMULATION_H
#define CONGRATSSIMULATION_H

#include "box2d/box2d.h"
#include "box2d/b2_body.h"
#include <QColor>
#include <vector>
#include <random>
#include <ctime>
#include <cstdlib>

struct Confetti{
    float X;
    float Y;
    float Rotation;
};

class CongratsSimulation{
    const int simPrecision;
    const float simulationRate;
    const float simGravity;
    const int simConfetti;
    b2World sim;

public:
    CongratsSimulation(int confettiCount, float gravityConstant, int accuracy);
    void AdvanceSimulation();
    std::vector<Confetti> getConfetti();
    void reset();
};


#endif // CONGRATSSIMULATION_H
