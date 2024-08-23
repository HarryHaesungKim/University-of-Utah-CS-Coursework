/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#ifndef INGREDIENT_H
#define INGREDIENT_H
#include <QString>
#include <QImage>

class Ingredient{

     QString name;
     QString imageFile;
     QImage image;
     int itemID; // 0-17; 0 sugar, 1 glowstone, etc. This id can be shared between ingredients
     int uniqueID;
     int lastPosX;
     int lastPosY;

public:
     Ingredient(QString name, QString imageFile, int itemID, int uniqueID);
     QString getName();
     QString getImageFile();
     int getItemID();
     int getUniqueID();
     void setLastPos(int x, int y);
     int getLastPosX();
     int getLastPosY();
     void setImage(QString imageFile){this->imageFile = imageFile; image = QImage(imageFile);}
};

#endif // INGREDIENT_H
