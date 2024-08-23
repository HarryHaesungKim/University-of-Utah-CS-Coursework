/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#ifndef MODEL_H
#define MODEL_H

#include "Ingredient.h"
#include "potion.h"
#include "congratssimulation.h"
#include "scenario.h"
#include <QMap>
#include <QList>
#include <QVector>
#include <QObject>
#include <QTimer>
#include <QColor>

class Model : public QObject {
    Q_OBJECT

    //ingredients
    const Ingredient *sugar;
    const Ingredient *netherWart;
    const Ingredient *glowstoneDust;
    const Ingredient *redStone;
    const Ingredient *pufferFish;
    const Ingredient *turtleHelmet;
    const Ingredient *gunpowder;
    const Ingredient *blazepowder;
    const Ingredient *spiderEye;
    const Ingredient *fermentedSpiderEye;
    const Ingredient *glisteringMelon;
    const Ingredient *phantomMembrane;
    const Ingredient *goldenCarrot;
    const Ingredient *ghastTear;
    const Ingredient *magmaCream;
    const Ingredient *rabbitFoot;
    const Ingredient *dragonsBreath;
    const Ingredient *potion1;
    const Ingredient *blazepowder2;

    QList<Ingredient> inventoryList;
    QMap<QString, QVector<Ingredient>> allPotions;
    QVector<Scenario> allScenarios;
    QVector<Scenario> testScenarios;
    QVector<Ingredient> placedIngredients;
    QList<Ingredient> scenarioIngredients;

    bool testInProgress;
    int testScore;
    QVector<Potion*> currentPotions;
    QVector<Potion> inventoryPotions;
    bool fueled;
    QString testPotion;
    Potion createWaterBottle(int uniqueId);

    bool runSim;
    CongratsSimulation sim;
    std::vector<QColor> colors;

public:
    explicit Model(QObject *parent = nullptr);

private slots:
    void updateSim();
    void nextRoundInTest();

public slots:
    void startLearn(QString learnModePotionUsabilitySelection, QString learnModePotionEffectSelection, QString learnModePotionRedstoneOrGlowstone);
    void startTest();
    void placedIngredient(QString slot, QString ingredient);
    void ingredientIconLastPositionSlot(int uniqueID, int x, int y);
    void getPreviousPosOfIconSlot(int uniqueID);
    void clearPlacedIngredients();
    void getHint();
    void resetScenario();
    void startSim();
    void stopSim();

signals:
    void setInventory(QVector<Ingredient> ingredients);
    void setTestLevel(QString title, QString text, int score);
    void setPreviousPosOfIconSignal(int lastPosX, int lastPosY);
    void setCongratsConfettiImg(QImage confettiImg);
    void setPotionColor(QPixmap overlay, Ingredient ingredient, int currentIndex, QString potionName);
    void setHint(QString hint);
    void setProgress(int progress);
    void removeIngredientIconSignal(int uniqueID);
    void changeLabelLocationSignal(int posx, int posy, int index);
    void congratsScreen();
    void updateInstructionSignal(QList<Ingredient> *scenarioIngredients, QString potionName);
    void youGotItRight();
    void learnComplete(QString potionName);
    void simOver();

    void brewingStandCharged();
};

#endif // MODEL_H
