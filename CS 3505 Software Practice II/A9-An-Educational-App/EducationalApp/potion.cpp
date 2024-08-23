/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#include "potion.h"
#include <QString>
#include <Ingredient.h>
#include <QPixmap>
#include <QPainter>

/**
 * Constructor of the potion object.
 * @brief Potion::Potion
 */
Potion::Potion(){
    name = "Water Bottle";
    state = 0;
    canBeEnhanced = false;
    enhanced = false;
    extended = false;
    throwable = false;
    drinkable = true;
    lingering = false;
    invertable = false;
    overlay = QPixmap("../Resources/potion_overlay.png");
}

/**
 * Changes the color of the potion overlay.
 * @brief Potion::updatePotionOverlay
 * @param potionColor
 */
void Potion::updatePotionOverlay(QRgb potionColor){
    QPainter painter(&overlay);
    painter.setCompositionMode(QPainter::CompositionMode_SourceIn);
    painter.fillRect(overlay.rect(), potionColor);
    painter.end();
}

/**
 * Fetches the name of the potion.
 * @brief Potion::getName
 * @return
 */
QString Potion::getName(){
    return name;
}

/**
 * Changes the potion based on the ingredient passed in. Order matters, and ingredients will not change
 * the potion if there is no valid combination.
 * @brief Potion::brewPotion
 * @param ingredient
 */
void Potion::brewPotion(Ingredient ingredient){
    // if water bottle and nether wart make awkard potion
    if(state == 0 && ingredient.getItemID() == 0){
        state = 1;
        name = "Awkward Potion";
        updatePotionOverlay(0x55a3);
    }
    // if awkward potion and main ingredient create corresponding potion
    else if(state == 1 && ingredient.getItemID() > 4){
        state = 2;
        int ingredientID = ingredient.getItemID();
        switch (ingredientID) {
        case 5 :
            canBeEnhanced = true;
            name = "Swiftness";
            invertable = true;
            updatePotionOverlay(QRgb(0xCCFFFF));
            break;
        case 6 :
            name = "Water Breathing";
            invertable = true;
            updatePotionOverlay(QRgb(0x0000FF));
            break;
        case 7 :
            canBeEnhanced = true;
            name = "Turtle Master";
            updatePotionOverlay(QRgb(0x3A243B));
            break;
        case 8 :
            canBeEnhanced = true;
            name = "Strength";
            updatePotionOverlay(QRgb(0x8B0000));
            break;
        case 9 :
            canBeEnhanced = true;
            name = "Poison";
            invertable = true;
            updatePotionOverlay(QRgb(0x006400));
            break;
        case 10 :
            name = "Weakness";
            updatePotionOverlay(QRgb(0xA9A9A9));
            break;
        case 11 :
            canBeEnhanced = true;
            name = "Instant Healing";
            invertable = true;
            updatePotionOverlay(QRgb(0xFF0000));
            break;
        case 12 :
            name = "Slow Falling";
            updatePotionOverlay(QRgb(0xD3D3D3));
            break;
        case 13 :
            name = "Night Vision";
            invertable = true;
            updatePotionOverlay(QRgb(0x0000CD));
            break;
        case 14 :
            canBeEnhanced = true;
            name = "Regeneration";
            updatePotionOverlay(QRgb(0xFFC0CB));
            break;
        case 15 :
            name = "Fire Resistance";
            invertable = true;
            updatePotionOverlay(QRgb(0xFFA500));
            break;
        default :
            canBeEnhanced = true;
            name = "Leaping";
            invertable = true;
            updatePotionOverlay(QRgb(0x8d68b0));
            break;
        }
        if(throwable){
            if(lingering){
                name = "Lingering Potion of " + name;
            }else{
                name = "Splash Potion of " + name;
            }
        }else{
            name = "Potion of " + name;
        }
    }
    // if a potion that is invertible and added fermented spider eye, "reverse" potion
    else if ((state == 2 || state == 3) && ingredient.getItemID() == 10 && invertable){
        if(name.contains("Fire Resistance") || name.contains("Leaping") || name.contains("Swiftness")){
            invertable = false;
            name = "Potion of Slowness";
            QString prefix = "";
            if(throwable){
                prefix = "Splash ";
                if(lingering){
                    prefix = "Lingering ";
                }
                name = prefix + name;
            }

            enhanced = false;
            canBeEnhanced = true;
            extended = false;
            updatePotionOverlay(QRgb(0x607d8b));
        }else if (name.contains("Poison") || name.contains("Instant Healing")){
            invertable = false;
            name = "Potion of Instant Harming";
            QString prefix = "";
            if(throwable){
                prefix = "Splash ";
                if(lingering){
                    prefix = "Lingering ";
                }
                name = prefix + name;
            }
            if(enhanced){
                name.append(" II");
            }
            canBeEnhanced = true;
            extended = false;
            updatePotionOverlay(QRgb(0x800000));
        }
        else if (name.contains("Night Vision")){
            invertable = false;
            name = "Potion of Invisibility";
            QString prefix = "";
            if(throwable){
                prefix = "Splash ";
                if(lingering){
                    prefix = "Lingering ";
                }
                name = prefix + name;
            }
            if(extended){
                name.append(" Extended");
            }
            enhanced = false;
            canBeEnhanced = false;
            updatePotionOverlay(QRgb(0xD3D3D3));
        }
    }
    // If a potion and glowstone is added and potion can be upgraded a tier, make tier 2 of potion
    else if (state == 2 && ingredient.getItemID() == 1 && canBeEnhanced){
        state = 3;
        enhanced = true;
        extended = false;
        name.append(" II");
    }
    // extends potion if applicable
    else if (state == 2 && ingredient.getItemID() == 2){
        state = 3;
        enhanced = false;
        extended = true;
        name.append(" Extended");
    }
    //makes potion splash (throwable)
    else if (ingredient.getItemID() == 3){
        name  = "Splash " + name;
        throwable = true;
        drinkable = false;
        lingering = false;
    }
    //makes potion lingering (must already be splash potion)
    else if (throwable && ingredient.getItemID() == 4){
        name.replace("Splash","Lingering");
        drinkable = false;
        lingering = true;
    }
    return;
}

/**
 * Gets the overlay of the potion. Is colored to the current potion state.
 * @brief Potion::getOverlay
 * @return
 */
QPixmap Potion::getOverlay(){
    return overlay;
}
