/**
 * CS3505 A6: Header file for model.cpp
 * @file model.h
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/27/21
 */

#ifndef MODEL_H
#define MODEL_H

#include <QObject>
#include <string>

class model : public QObject
{
    Q_OBJECT

    int computerComboPosition;
    std::string computerCombo;
    std::string playerCombo;

    int playerInputCounter;
    double delaySpeedFactor;

public:
    // Constructor
    explicit model(QObject *parent = nullptr);

public:
    // Game State
    void resetGame();

    // Delay speed
    void speedUpGame();

    // Computer combo stuff
    void addZeroOrOneToComputerCombo();

    // Player combo stuff
    void updatePlayerCombo(std::string combo);

    bool checkInput(int playerInput);

    // Helper fuctions:
    int generateRandom();

public slots:
    void redButtonPressed();
    void blueButtonPressed();

    void startNewRound();

    void incrementPlayerInputCounterSlot();
    void incrementComputerComboPositionSlot();

    void calculateProgressBarSlot();

    void endOfSequenceSlot();

    void displayComputerComboSlot();

    void updateRoundSlot();

signals:

    void updateViewCorrectButtonClicked();
    void updateViewIncorrectButtonClicked();

    void updateProgressBar(double);

    void showWin();

    void resumePlayerTurnSignal();

    void redButtonLighterColorSignal();

    void blueButtonLighterColorSignal();

    void revertButtonColorSignal(double);

    void viewUpdateRound(QString);
};

#endif // MODEL_H
