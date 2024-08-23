/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#include "scenario.h"
#include <QString>

/**
 * Constructs a new scenario with title, text of the scene, a hint if desiered, and the potion to
 * create to beat the scenario.
 * @brief Scenario::Scenario
 * @param title
 * @param text
 * @param hint
 * @param potionName
 */
Scenario::Scenario(QString title, QString text, QString hint, QString potionName){
    this->title = title;
    this->text = text;
    this->hint = hint;
    this->potionName = "Potion of " + potionName;
}
