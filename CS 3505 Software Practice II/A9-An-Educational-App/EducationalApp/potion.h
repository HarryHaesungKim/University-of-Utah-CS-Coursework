/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#ifndef POTION_H
#define POTION_H

#include <QString>
#include <Ingredient.h>
#include <QPixmap>
#include <QPainter>

class Potion{
    QString name;
    QPixmap overlay;
    int state; // 0 = water bottle, 1 = awkward potion, 2 = potion, 3 = extended or enhanced
    bool canBeEnhanced; // can be enhanced with glowstone
    bool enhanced;
    bool extended;
    bool drinkable;
    bool throwable;
    bool lingering;
    bool invertable; // adding a fermented spider eye "reverses" potion effect, instant healing turns to
    // instant harming, swiftness and jumping turn to slowness
    int uniqueId;

public:
    Potion();
    void brewPotion(Ingredient ingredient);
    void updatePotionOverlay(QRgb potionColor);
    QPixmap getOverlay();
    QString getName();
    void setUniqueId(int uniqueId){this->uniqueId = uniqueId;}
    int getUniqueId(){return uniqueId;}
};

#endif // POTION_H
