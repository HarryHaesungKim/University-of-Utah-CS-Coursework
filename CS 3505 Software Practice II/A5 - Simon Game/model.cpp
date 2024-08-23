/**
 * CS3505 A6: model/logic for SimonGame
 * @file model.cpp
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/27/21
 */

#include "model.h"
#include <iostream>
#include <sstream>

/**
 * Creates a model object used in SimonGame.
 * @brief model::model
 * @param parent
 */
model::model(QObject *parent) : QObject(parent)
{
    computerCombo = "";
    playerCombo = "";
    playerInputCounter = 0;
    computerComboPosition = 0;
    delaySpeedFactor = 1;
}

/**
 * This method resets elements used in SimonGame.
 * @brief model::resetGame
 */
void model::resetGame(){
    computerCombo = "";
    playerCombo = "";
    computerComboPosition = 0;
    delaySpeedFactor = 1;
}

/**
 * Speeds up the game by factor (0.85).
 * @brief model::speedUpGame
 */
void model::speedUpGame(){
    delaySpeedFactor *= 0.85;
}

/**
 * Randomly adds to the end of the existing computerCombo.
 * @brief model::addZeroOrOneToComputerCombo
 */
void model::addZeroOrOneToComputerCombo()
{
    int nextTurn = generateRandom();
    computerCombo += std::to_string(nextTurn);
}

/**
 * Adds to current playerCombo based on player input.
 * @brief model::updatePlayerCombo
 * @param combo
 */
void model::updatePlayerCombo(std::string combo)
{
    playerCombo += combo;
}

/**
 * Checks current playerInput to see that it matches the computerCombo's.
 * @brief model::checkInput
 * @param playerInput input from player based on which button they push.
 * @return true if playerInput matches, false otherwise.
 */
bool model::checkInput(int playerInput)
{
    int num = (int) computerCombo[computerComboPosition] - '0';
    bool answer = num == playerInput;
    return answer;
}

/**
 * Helper method that randomly generates a 1 or 0 (blue or red respectively)
 * to add to end of existing computerCombo.
 * @brief model::generateRandom
 * @return 1 or 0 (blue or red)
 */
int model::generateRandom()
{
    int x = ((int) rand() % 2);
    return x;
}

void model::redButtonPressed(){
    updatePlayerCombo("0");

    if(checkInput(0)){
        emit updateViewCorrectButtonClicked();
    }
    else{
        emit updateViewIncorrectButtonClicked();
    }
}


void model::blueButtonPressed(){
    updatePlayerCombo("1");

    if(checkInput(1)){
        emit updateViewCorrectButtonClicked();
    }
    else{
        emit updateViewIncorrectButtonClicked();
    }
}

void model::startNewRound(){

    playerInputCounter = 0;
    computerComboPosition = 0;
    addZeroOrOneToComputerCombo();
}

void model::incrementPlayerInputCounterSlot(){
    playerInputCounter++;
}

void model::incrementComputerComboPositionSlot(){
    computerComboPosition++;
}

void model::calculateProgressBarSlot(){
    double PBValue = ((double)playerInputCounter / (double)computerCombo.length()) * 100;
    emit updateProgressBar(PBValue);
}

void model::endOfSequenceSlot(){
    if(computerCombo.length() == (unsigned) playerInputCounter){
        speedUpGame();
        emit showWin();
    }
}

void model::displayComputerComboSlot(){
    if((unsigned) computerComboPosition > computerCombo.length()){
        computerComboPosition = 0;
        emit resumePlayerTurnSignal();
        return;
    }
    //  display red or blue flash based on computerCombo
    if(computerCombo[computerComboPosition - 1] == '0'){
        emit redButtonLighterColorSignal();
    }
    else{
        emit blueButtonLighterColorSignal();
    }

    emit revertButtonColorSignal(delaySpeedFactor * 1000);
}

void model::updateRoundSlot(){
    QString round = "Round " + QString::number(computerCombo.length());
    emit viewUpdateRound(round);
}

