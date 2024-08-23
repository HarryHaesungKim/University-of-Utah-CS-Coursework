/**
 * CS3505 A2: Calculates spiral.
 * @file Spiral.cpp
 * @author Harry Kim
 * @version 1.0 9/13/21
 */

#include "Spiral.h"

Spiral::Spiral(double centerX, double centerY, double startingAngle, double startingRadius){
    angle = startingAngle;
    radius = startingRadius;
    originX = centerX;
    originY = centerY;

    // rad1 determines the angle of the letter on the page.
    // rad2 is how far around the circle you are.
    rad1 = (-(angle - 90) - 90) / 180 * M_PI;
    rad2 = -(angle - 90) / 180 * M_PI;

    // rad1 = (angle2 - 90) / 180 * 3.141592;
    // rad2 = angle2 / 180 * 3.141592;

    // The position of the character depends on the center point
    // plus the angle and the radius.
    x = originX + cos(rad2) * startingRadius;
    y = originY + sin(rad2) * startingRadius;
}

/**
 * This method overloads the ++ prefix operator for a spiral object. Used for incrementation.
 */
Spiral& Spiral::operator++()
{
    angle += 10.0; // change the angle around the circle
    double angleRateOfChange = 0.9945; // keeping the letters more or less evenly spaced out as it goes along the curve.
    angle *= angleRateOfChange;

    // Incrementing radius so that the spiral is possible.
    // Archimedean spiral curve.
    radius += 0.7;

    // Updated rads.
    rad1 = (-(angle - 90) - 90) / 180 * M_PI;
    rad2 = -(angle - 90) / 180 * M_PI;

    // Updated x and y coordinates.
    x = originX + cos(rad2) * radius;
    y = originY + sin(rad2) * radius;

    return *this;
}

/**
 * This method overloads the ++ postfix operator for a spiral object. Used for incrementation.
 */
Spiral Spiral::operator++(int)
{
    Spiral temp = *this;
    ++*this;
    return temp;
}

double Spiral::getTextX()
{
    return x;
}

double Spiral::getTextY()
{
    return y;
}

double Spiral::getLetterAngle()
{
    return angle;
}

double Spiral::getRad1()
{
    return rad1;
}