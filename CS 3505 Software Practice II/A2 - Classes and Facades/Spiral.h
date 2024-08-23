/**
 * CS3505 A2: Header file for Spiral.cpp.
 * @file Spiral.h
 * @author Harry Kim
 * @version 1.0 9/13/21
 */

#ifndef SPIRAL
#define SPIRAL

#define _USE_MATH_DEFINES
#include <iostream>
#include <math.h>

class Spiral{

private:
    double originX;
    double originY;
    double radius;
    double angle;
    double rad1;
    double rad2;
    double x;
    double y;
    
    unsigned int i;

public:
    Spiral(double centerX, double centerY, double startingAngle, double startingRadius);
    Spiral& operator++();
    Spiral operator++(int);
    double getTextX();
    double getTextY();
    double getLetterAngle();
    double getRad1();
};
#endif