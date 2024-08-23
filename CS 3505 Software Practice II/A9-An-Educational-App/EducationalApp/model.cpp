/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#include "model.h"
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

/**
 * Model constructor to handle both game modes.
 * @brief Model::Model
 * @param parent
 */
Model::Model(QObject *parent) : QObject(parent), sim(300, 20, 10){
    //Setup ingredients
    netherWart = new Ingredient("NetherWart", "../Resources/nether_wart.png", 0, 0);
    glowstoneDust = new Ingredient("Glowstone Dust", "../Resources/glowstone_dust.png", 1, 1);
    redStone = new Ingredient("Redstone", "../Resources/redstone.png", 2, 2);
    gunpowder = new Ingredient("Gunpowder", "../Resources/gunpowder.png", 3, 3);
    dragonsBreath = new Ingredient("Dragons Breath", "../Resources/dragon_breath.png", 4, 4);
    sugar = new Ingredient("Sugar", "../Resources/sugar.png", 5, 5);
    pufferFish = new Ingredient("Pufferfish", "../Resources/pufferfish.png", 6, 6);
    turtleHelmet = new Ingredient("Turtle Helmet", "../Resources/turtle_helmet.png", 7, 7);
    blazepowder = new Ingredient("Blaze Powder", "../Resources/blaze_powder.png", 8, 8);
    spiderEye = new Ingredient("Spider Eye", "../Resources/spider_eye.png", 9, 9);
    fermentedSpiderEye = new Ingredient("Fermented Spider Eye", "../Resources/fermented_spider_eye.png", 10, 10);
    glisteringMelon = new Ingredient("Glistering Melon", "../Resources/glistering_melon_slice.png", 11, 11);
    phantomMembrane = new Ingredient("Phantom Membrane", "../Resources/phantom_membrane.png", 12, 12);
    goldenCarrot = new Ingredient("Golden Carrot", "../Resources/golden_carrot.png", 13, 13);
    ghastTear = new Ingredient("Ghast Tear", "../Resources/ghast_tear.png", 14, 14);
    magmaCream = new Ingredient("Magma Cream", "../Resources/magma_cream.png", 15, 15);
    rabbitFoot = new Ingredient("Rabbit Foot", "../Resources/rabbit_foot.png", 16, 16);
    blazepowder2 = new Ingredient("Blaze Powder2", "../Resources/blaze_powder.png", 8, 17);
    potion1 = new Ingredient("Potion1", "../Resources/potion.png", 17, 18);

    //Setup inventory
    inventoryList = {*netherWart, *glowstoneDust, *redStone, *gunpowder, *dragonsBreath, *sugar, *pufferFish, *turtleHelmet, *blazepowder, *spiderEye, *fermentedSpiderEye, *glisteringMelon, *phantomMembrane, *goldenCarrot, *ghastTear, *magmaCream, *rabbitFoot, *blazepowder2, *potion1};

    //Setup Potions for learning
    allPotions["Instant Healing"] = {*netherWart, *glisteringMelon};
    allPotions["Fire Resistance"] = {*netherWart, *magmaCream};
    allPotions["Regeneration"] = {*netherWart, *ghastTear};
    allPotions["Strength"] = {*netherWart, *blazepowder, *blazepowder2};
    allPotions["Swiftness"] = {*netherWart, *sugar};
    allPotions["Night Vision"] = {*netherWart, *goldenCarrot};
    allPotions["Invisibility"] = {*netherWart, *goldenCarrot, *fermentedSpiderEye};
    allPotions["Water Breathing"] = {*netherWart, *pufferFish};
    allPotions["Leaping"] = {*netherWart, *rabbitFoot};
    allPotions["Slow Falling"] = {*netherWart, *phantomMembrane};
    allPotions["Poison"] = {*netherWart, *spiderEye};
    allPotions["Weakness"] = {*netherWart, *fermentedSpiderEye};
    allPotions["Slowness"] = {*netherWart, *sugar, *fermentedSpiderEye};
    allPotions["Instant Harming"] = {*netherWart, *glisteringMelon, *fermentedSpiderEye};
    allPotions["Turtle Master"] = {*netherWart, *turtleHelmet};

    //Setup scenarios
    allScenarios.push_back(*(new Scenario("Critical Condition!", "Walled up in a tight space, you are left with half a heart, hungry, and no food. You DO have your potion brewing equipment with you though. You need a potion that will heal your wounds instantly (hint hint). Craft the best potion for the job.", "If only I had a way to heal myself!", "Instant Healing")));
    allScenarios.push_back(*(new Scenario("To the Nether We Go!", "You need to travel to the Nether (again) to collect more blaze rods. There's probably going to be lots of lava and you don't want to get burned. Craft the best potion for the job.", "If only I had a way to resist fire!", "Fire Resistance")));
    allScenarios.push_back(*(new Scenario("Healing Factor?", "You've been taking a lot of damage, haven't you? If only you could heal continously without constantly worrying about downing a potion of healing like a certain comic book character... Craft the best potion for the job.", "If only I had a way to heal faster!", "Regeneration")));
    allScenarios.push_back(*(new Scenario("A PVP Challenge", "A fellow player is on their way to fight you in glorious PVP combat. You have good armor so health is not an issue, but you want to be able to deal more damage than you usually do. Craft the best potion for the job.", "If only I could make myself stronger!", "Strength")));
    allScenarios.push_back(*(new Scenario("BRAIIINNNNSSSS", "While exploring in the night you came across a zombie villager and would like to cure them to be able to trade, and get the discount for curing them. Craft the best potion for the job.", "If only I had a way to weaken them somehow.", "Weakness")));
    allScenarios.push_back(*(new Scenario("Atlantis", "When boating in your world you come across an ocean monument. You wish to explore, loot, and conquer the monument and the Elder Guaridans inside. The only problem, you don't want to drown. Craft the best potion for the job.", "If only I had a way to breath underwater!", "Water Breathing")));
    allScenarios.push_back(*(new Scenario("Dragon Slayer","You are finally ready to fight the ender dragon, you have all you armor, tools and blocks ready. You have strong armor and deal enough damage, the only thing that could go wrong is getting knocked into the air and falling to your death. Craft the best potion for the job.", "If only I could slow my falling!", "Slow Falling")));
    allScenarios.push_back(*(new Scenario("Spelunker", "You have just loaded into update 1.8, the caves and cliffs update, and you have found a massive cave. It is really hard to see anything and you are running low on torches. Craft the best potion for the job.", "If only I could see in the dark!", "Night Vision")));
    allScenarios.push_back(*(new Scenario("GOAT", "You discovered a giant mountain, and wish to enjoy the view from the peak. Only, jumping and mining your way up is getting to be cumbersome. If only there was an easier way to jump your way up. Craft the best potion for the job.", "If only I could jump higher!", "Leaping")));
    allScenarios.push_back(*(new Scenario("Mansion Mischief", "While obliterating a dark oak forest for your build you come across a woddland mansion. You know there can be some goodies inside, the only problem is the vindicators and and evokers that do massive damage, if only there was a way to slip by them unoticed. Craft the best potion for the job.", "If only I could prevent the enemies from seeing me!", "Invisibility")));
    allScenarios.push_back(*(new Scenario("Rabbit go brrrrrrrrrr", "You wish to murder some rabbits for their ... feet ... the only problem, they are to fast. Craft the best potion for the job.", "If only I could run as fast as these rabbits!", "Swiftness")));
    allScenarios.push_back(*(new Scenario("Absolute UNIT", "Wanting totems of undying, you wish to start a raid, only being the Chad that you are, you wish for the pillagers to come to you and you will simply tank the damage as you commit war crimes against the pillagers. Craft the best potion for the job.", "If only I had a way to take more damage!", "Turtle Master")));
    allScenarios.push_back(*(new Scenario("AHHH", "After a couple of hours of building, a couple youtube videos, and a few close calls in the void your enderman XP farm is complete. Clicking repeatedly to murder them is slow, it would be much faster to be able to kill them all at once with a single click. Craft the best potion for the job.", "If only I had a way to instantly cause damage!", "Instant Harming")));
    allScenarios.push_back(*(new Scenario("For Jeremy II", "Your friend just burned your pet wolfs with lava. They say it was an accident through their laughs. You want to show him that is not going to slide... not again ... You want to show them you can kill them if you wanted, but not actually do it, just get close, for Jeremy II. Craft the best potion for the job.", "If only I had a way to poison them!", "Poison")));
    allScenarios.push_back(*(new Scenario("Eat My Dust", "You found a baby zombie in diamond armor riding a chicken. You must add it to your rare mob collection. Only luring them to their eternal capture is hard because they are very fast despite tiny legs. Craft the best potion for the job.", "If only I had a way to slow it down!", "Slowness")));

    //Setup celebration simulation
    runSim = false;
    for(int i = 0; i < 500; i++){
        colors.push_back(QColor(128 + rand()%127, 128 + rand()%127, 128 + rand()%127, 255));
    }
}

/**
 * Initializes the learning gamemode based selected potion. Displays only needed ingredients and
 * one potion to manipulate.
 * @brief Model::startLearn
 * @param learnModePotionUsabilitySelection
 * @param learnModePotionEffectSelection
 * @param learnModePotionRedstoneOrGlowstone
 */
void Model::startLearn(QString learnModePotionUsabilitySelection, QString learnModePotionEffectSelection, QString learnModePotionRedstoneOrGlowstone){
    resetScenario();
    scenarioIngredients.clear();
    inventoryPotions.clear();
    fueled = false;
    testInProgress = false;
    scenarioIngredients.push_back(*blazepowder);
    scenarioIngredients.push_back(*potion1);
    Potion p1 = createWaterBottle(18);
    inventoryPotions.push_back(p1);
    testPotion = learnModePotionEffectSelection;
    testPotion  = "Potion of " + testPotion;

    // Add all ingredients for scenario to list
    for(Ingredient ingredient: allPotions[learnModePotionEffectSelection])
        scenarioIngredients.push_back(ingredient);

    // Alter scenario potion's ingredient list and name based on attribute
    if(learnModePotionUsabilitySelection == "throwable"){
        scenarioIngredients.push_back(*gunpowder);
        testPotion  = "Splash " + testPotion;
    } else if(learnModePotionUsabilitySelection == "lingering"){
        scenarioIngredients.push_back(*gunpowder);
        scenarioIngredients.push_back(*dragonsBreath);
        testPotion  = "Lingering " + testPotion;
    }
    if(learnModePotionRedstoneOrGlowstone == "glowstone"){
        scenarioIngredients.push_back(*glowstoneDust);
        testPotion.append(" II");
    } else if(learnModePotionRedstoneOrGlowstone == "redstone"){
        scenarioIngredients.push_back(*redStone);
        testPotion.append(" Extended");
    }

    // Add required potion ingredients to game inventory
    emit setInventory(inventoryList);

    // Remove unnecessary ingredients that aren't used for the scenario's potion
    QList<int> posXList;
    QList<int> posYList;
    for(QList<Ingredient>::iterator i = inventoryList.begin(); i != inventoryList.end(); i++){
        bool canRemove = true;
        if(posXList.size() < scenarioIngredients.size() && posYList.size() < scenarioIngredients.size()){
            posXList.push_back(i->getLastPosX());
            posYList.push_back(i->getLastPosY());
        }
        for(QList<Ingredient>::iterator ii = scenarioIngredients.begin(); ii != scenarioIngredients.end(); ii++){
            if(ii->getUniqueID() == i->getUniqueID()){
                canRemove = false;
                emit changeLabelLocationSignal(posXList.front(), posYList.front(), i->getUniqueID());
                posXList.pop_front();
                posYList.pop_front();
                break;
            }
        }
        if(canRemove){
            emit removeIngredientIconSignal(i->getUniqueID());
        }
    }

    emit setPotionColor(p1.getOverlay(), *potion1, p1.getUniqueId(), p1.getName());
    emit updateInstructionSignal(&scenarioIngredients, testPotion);
}

/**
 * Initialize the testing gamemode. Displays all of the ingredients and potions.
 * @brief Model::startTest
 */
void Model::startTest() {
    // Reset all game values
    resetScenario();
    emit setProgress(0);
    testInProgress = true;
    fueled = false;
    testScore = 0;
    inventoryPotions.clear();
    Potion p1 = createWaterBottle(18);
    inventoryPotions.push_back(p1);
    currentPotions.clear();

    // Have main window fill inventory with all ingredients
    emit setInventory(inventoryList);
    emit setPotionColor(p1.getOverlay(), *potion1, p1.getUniqueId(),p1.getName());

    // Fill list of scenarios with new random scenarios
    testScenarios.clear();
    for (int i = 0; i < allScenarios.size(); i++) {
        Scenario temp = allScenarios[i];
        testScenarios.push_back(temp);
        std::random_shuffle(testScenarios.begin(), testScenarios.end());
    }

    // Display first scenario on main window
    testPotion = testScenarios[testScore].potionName;
    emit setTestLevel(testScenarios[testScore].title, testScenarios[testScore].text, testScore);
}

/**
 * Determines what happens when an ingredient/potion is placed. Could be used to brew a potion,
 * fuel the furance, or moved into a slot.
 * @brief Model::placedIngredient
 * @param slot
 * @param ingredient
 */
void Model::placedIngredient(QString slot, QString ingredient) {
    Ingredient *placed;
    // Add placed potion to vector keeping track of all placed potions
    for (QList<Ingredient>::iterator i = inventoryList.begin(); i != inventoryList.end(); ++i) {
        if (i->getName() == ingredient)
            placed = i;
    }

    // Alter game variables based on which ingredient was placed
    if(slot == "base" && fueled){
        bool tookIngredient;
        for(QVector<Potion*>::iterator i = currentPotions.begin(); i < currentPotions.end(); i++){
            QString orginalName = (*i)->getName();
            (*i)->brewPotion(*placed);
            tookIngredient = !((*i)->getName() == orginalName);
            if(ingredient == "Gunpowder")
                inventoryList[(*i)->getUniqueId()].setImage("../Resources/splash_potion.png");
            else if(ingredient == "Dragons Breath" && (*i)->getName().contains("Lingering"))
                inventoryList[(*i)->getUniqueId()].setImage("../Resources/lingering_potion.png");
            emit setPotionColor((*i)->getOverlay(), inventoryList[(*i)->getUniqueId()], (*i)->getUniqueId(), (*i)->getName());
        }if(currentPotions.size() >= 1 && tookIngredient)
            emit removeIngredientIconSignal(placed->getUniqueID());
    }else if(slot == "blaze" && ingredient.contains("Blaze Powder")){
        if(!fueled){
            fueled = true;
            emit removeIngredientIconSignal(placed->getUniqueID());
            emit brewingStandCharged();
        }
    }else if(ingredient.startsWith("Potion")){
        QChar one = QChar('1');
        int index = ingredient[ingredient.size()-1].toLatin1()-one.toLatin1();
        if(slot == "bottle"){
            if(currentPotions.size() < 3)
                currentPotions.push_back(&inventoryPotions[index]);
        }else{
            int indexToRemove;
            bool toRemove = false;
            for(QVector<Potion*>::iterator i = currentPotions.begin(); i < currentPotions.end(); i++){
                if((*i)->getUniqueId() == inventoryPotions[index].getUniqueId()){
                    indexToRemove = i-currentPotions.begin();
                    toRemove = true;
                    break;
                }
            }
            if(toRemove)
                currentPotions.remove(indexToRemove);
        }
    }

    // Check if potion was brewed correctly, if so then show congrats screen and move to next level
    for(QVector<Potion*>::iterator i = currentPotions.begin(); i < currentPotions.end(); i++){
        if(((*i)->getName().contains(testPotion) && testInProgress) || (*i)->getName() == testPotion){
            if(testInProgress){
                emit youGotItRight();
                QTimer::singleShot(3000,this, &Model::nextRoundInTest);
                return;
            } else
                emit learnComplete(testPotion);
        }
    }
    placedIngredients.push_back(*placed);
}

/**
 * Clears the placedIngredients, so that ingrients don't stay after user has left the
 * testing or learing area
 * @brief model::clearPlacedIngredients
 */
void Model::clearPlacedIngredients(){
    placedIngredients.clear();
}

/**
 * Resets the current location on an ingredient to its previous location.
 * @brief Model::ingredientIconLastPositionSlot
 * @param uniqueID
 * @param x
 * @param y
 */
void Model::ingredientIconLastPositionSlot(int uniqueID, int x, int y){
    for (auto it = inventoryList.begin(); it != inventoryList.end(); ++it){
        if(it->getUniqueID() == uniqueID){
            it->setLastPos(x, y);
            break;
        }
    }
}

/**
 * Gets the previous location of an ingredient
 * @brief Model::getPreviousPosOfIconSlot
 * @param uniqueID
 */
void Model::getPreviousPosOfIconSlot(int uniqueID){
    int lastPosX = inventoryList[uniqueID].getLastPosX();
    int lastPosY = inventoryList[uniqueID].getLastPosY();
    emit setPreviousPosOfIconSignal(lastPosX, lastPosY);
}

/**
 * Initialize the next round in the testing gamemode
 * @brief Model::nextRoundInTest
 */
void Model::nextRoundInTest(){
    for(QVector<Potion*>::iterator i = currentPotions.begin(); i < currentPotions.end(); i++){
        inventoryList[(*i)->getUniqueId()].setImage("../Resources/potion.png");
    }
    testScore++;

    // If game is completed, show congrats screen
    if(testScore >= testScenarios.size()){
        emit congratsScreen();
        return;
    }

    // Reset inventory and send next scenario to the main window to be displayed
    testPotion = testScenarios[testScore].potionName;
    resetScenario();
    emit setTestLevel(testScenarios[testScore].title, testScenarios[testScore].text, testScore);
    emit setProgress(((float)testScore / (float)testScenarios.size()) * 100);
}

/**
 * Reset the scenario or learn ingredients and potion to their starting states and locations
 * @brief Model::resetScenario
 */
void Model::resetScenario() {
    // If in testing game mode
    if (testInProgress) {
        fueled = false;
        clearPlacedIngredients();
        inventoryPotions.clear();
        Potion p1 = createWaterBottle(18);
        inventoryPotions.push_back(p1);
        currentPotions.clear();
        inventoryList[18].setImage("../Resources/potion.png");
        emit setInventory(inventoryList);
        emit setPotionColor(p1.getOverlay(), *potion1, p1.getUniqueId(),p1.getName());
    }
    // If in learning game mode
    else {
        fueled = false;
        clearPlacedIngredients();
        inventoryPotions.clear();
        Potion p1 = createWaterBottle(18);
        inventoryPotions.push_back(p1);
        currentPotions.clear();
        inventoryList[18].setImage("../Resources/potion.png");

        emit setInventory(inventoryList);
        emit setPotionColor(p1.getOverlay(), *potion1, p1.getUniqueId(),p1.getName());

        // Remove unnecessary ingredients from inventory
        QList<int> posXList;
        QList<int> posYList;
        for(QList<Ingredient>::iterator i = inventoryList.begin(); i != inventoryList.end(); i++){
            bool canRemove = true;
            if(posXList.size() < scenarioIngredients.size() && posYList.size() < scenarioIngredients.size()){
                posXList.push_back(i->getLastPosX());
                posYList.push_back(i->getLastPosY());
            }
            for(QList<Ingredient>::iterator ii = scenarioIngredients.begin(); ii != scenarioIngredients.end(); ii++){
                if(ii->getUniqueID() == i->getUniqueID()){
                    canRemove = false;
                    emit changeLabelLocationSignal(posXList.front(), posYList.front(), i->getUniqueID());
                    posXList.pop_front();
                    posYList.pop_front();
                    break;
                }
            }
            if(canRemove)
                emit removeIngredientIconSignal(i->getUniqueID());
        }
    }
}

/**
 * Create a potion that is a water bottle (base potion)
 * @brief Model::createWaterBottle
 * @param uniqueId
 * @return
 */
Potion Model::createWaterBottle(int uniqueId){
    Potion potion;
    potion.setUniqueId(uniqueId);
    potion.updatePotionOverlay(QRgb(0x55a3));
    return potion;
}

/**
 * Updates the sim to display current location of confetti.
 * @brief Model::updateSim
 */
void Model::updateSim(){
    // update sim
    sim.AdvanceSimulation();

    // get the confetti
    std::vector<Confetti> confetti = sim.getConfetti();

    QImage img(QSize(80,50), QImage::Format_ARGB32);
    img.fill(QColor(255,255,255,255));

    int i = 0;

    for(auto it = confetti.begin(); it != confetti.end(); it++){
        if(it.base()->X >= -40 && it.base()->X <= 40 && it.base()->Y >= -25 && it.base()->Y <= 25){
            img.setPixelColor(it.base()->X+40, it.base()->Y+25, colors[i]);
        }
        i++;
    }

    emit setCongratsConfettiImg(img);

    if(runSim)
        QTimer::singleShot(1000/60, this, &Model::updateSim);
}

/**
 * Grabs the hint for the scenario to be displayed
 * @brief Model::getHint
 */
void Model::getHint() {
    emit setHint(testScenarios[testScore].hint);
}

/**
 * Initializes the sim
 * @brief Model::startSim
 */
void Model::startSim(){
    runSim = true;
    sim.reset();
    updateSim();
}

/**
 * Terminates the sim.
 * @brief Model::stopSim
 */
void Model::stopSim(){
    runSim = false;
    emit simOver();
}
