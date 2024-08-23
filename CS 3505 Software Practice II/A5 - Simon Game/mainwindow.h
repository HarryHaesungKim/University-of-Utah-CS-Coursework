/**
 * CS3505 A6: Header file for mainwindow.cpp
 * @file mainwindow.h
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/27/21
 */

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTimer>
#include "model.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

public slots:
    // Physical button clicks
    void on_redButton_clicked();
    void on_blueButton_clicked();
    void on_startButton_clicked();
    // Game state
    void gameInProgress();
    void resumePlayerTurn();
    void endAndResetGame();
    // Display
    void displayComputerComboOneByOne();
    void revertButtonColor();
    // Input conditions
    void correctButtonClicked();
    void incorrectButtonClicked();

    void showWinSlot();

    void redButtonLighterColorSlot();
    void blueButtonLighterColorSlot();

    void revertButtonColorSlot(double);


signals:
    void resetPlayerInputCounterAndComputerComboPositionAndAddZeroOrOneToComputerComboSignal();
    void incrementPlayerInputCounterSignal();
    void incrementComputerComboPositionSignal();
    void calculateProgressBarValue();
    void endOfSequence();
    void displayComputerComboSignal();
    void updateRound();
    void resetGame();

private:
    Ui::MainWindow *ui;
    model game;
    QTimer delayFlash;

    double PBValue;
};
#endif // MAINWINDOW_H
