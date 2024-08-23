/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#include "Ingredient.h"
#include <QString>
#include <QImage>

/**
 * Ingredient constructor. Takes in a name, file path to its icon image, an id, and a uniqueID.
 * @brief Ingredient::Ingredient
 * @param name
 * @param fileName
 * @param id
 * @param uniqueID
 */
Ingredient::Ingredient(QString name, QString fileName, int id, int uniqueID){
    this->name = name;
    this->imageFile = fileName;
    this->image = QImage(fileName);
    this->uniqueID = uniqueID;
    this->itemID = id;
}

/**
 * Gets the name of ingredient
 * @brief Ingredient::getName
 * @return
 */
QString Ingredient::getName(){
    return name;
}

/**
 * Gets the location of the image used to represent the ingredient.
 * @brief Ingredient::getImageFile
 * @return
 */
QString Ingredient::getImageFile(){
    return imageFile;
}

/**
 * Gets the ingredient's ID.
 * @brief Ingredient::getItemID
 * @return
 */
int Ingredient::getItemID(){
    return itemID;
}

/**
 * Get the ingredient's unique id.
 * @brief Ingredient::getUniqueID
 * @return
 */
int Ingredient::getUniqueID()
{
    return uniqueID;
}

/**
 * Resets the location of the ingredient to its last known location.
 * @brief Ingredient::setLastPos
 * @param x
 * @param y
 */
void Ingredient::setLastPos(int x, int y)
{
    lastPosX = x;
    lastPosY = y;
}

/**
 * Get previous x location of the ingredient
 * @brief Ingredient::getLastPosX
 * @return
 */
int Ingredient::getLastPosX()
{
    return lastPosX;
}

/**
 * Get previous y location of the ingredient
 * @brief Ingredient::getLastPosY
 * @return
 */
int Ingredient::getLastPosY()
{
    return lastPosY;
}
