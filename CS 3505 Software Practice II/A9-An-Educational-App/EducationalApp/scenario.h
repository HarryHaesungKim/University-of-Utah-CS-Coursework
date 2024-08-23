/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#ifndef SCENARIO_H
#define SCENARIO_H

#include <QString>

class Scenario{
public:
    QString text;
    QString title;
    QString hint;
    QString potionName;

    Scenario(QString title, QString text, QString hint, QString potionName);
};

#endif // SCENARIO_H
