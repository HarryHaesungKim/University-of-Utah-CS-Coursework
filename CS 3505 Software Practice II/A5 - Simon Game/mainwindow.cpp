/**
 * CS3505 A6: view for the SimonGame
 * @file mainwindow.cpp
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/27/21
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <iostream>
#include <QTimer>

/*
 * Model object should have both signals and slots
 * For example, it should have something like a slot (or slots, probably) for when buttons are pressed in the view
 * It should have signals to tell the view that the computer is making a move
 */

/**
 * Constructor that sets up the ui.
 * @brief MainWindow::MainWindow
 * @param parent
 */
MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->progressBar->setValue(0);
    ui->displayLabel->setStyleSheet("QLabel { color : rgb(50,50,255); }");
    ui->redButton->setStyleSheet( QString("QPushButton {background-color: rgb(200,50,50);} QPushButton:pressed {background-color: rgb(255,150,150);}"));
    ui->blueButton->setStyleSheet( QString("QPushButton {background-color: rgb(50,50,200);} QPushButton:pressed {background-color: rgb(150,150,255);}"));
    ui->redButton->setEnabled(false);
    ui->blueButton->setEnabled(false);

    //connect all signals and slots
    connect(ui->redButton, &QPushButton::clicked, &game, &model::redButtonPressed);
    connect(ui->blueButton, &QPushButton::clicked, &game, &model::blueButtonPressed);
    connect(this, &MainWindow::resetPlayerInputCounterAndComputerComboPositionAndAddZeroOrOneToComputerComboSignal, &game, &model::startNewRound);
    connect(&game, &model::updateViewCorrectButtonClicked, this, &MainWindow::correctButtonClicked);
    connect(&game, &model::updateViewIncorrectButtonClicked, this, &MainWindow::incorrectButtonClicked);
    connect(this, &MainWindow::incrementPlayerInputCounterSignal, &game, &model::incrementPlayerInputCounterSlot);
    connect(this, &MainWindow::incrementComputerComboPositionSignal, &game, &model::incrementComputerComboPositionSlot);
    connect(this, &MainWindow::calculateProgressBarValue, &game, &model::calculateProgressBarSlot);
    connect(&game, &model::updateProgressBar, ui->progressBar, &QProgressBar::setValue);
    connect(this, &MainWindow::endOfSequence, &game, &model::endOfSequenceSlot);
    connect(&game, &model::showWin, this, &MainWindow::showWinSlot);
    connect(this, &MainWindow::displayComputerComboSignal, &game, &model::displayComputerComboSlot);
    connect(&game, &model::resumePlayerTurnSignal, this, &MainWindow::resumePlayerTurn);
    connect(&game, &model::redButtonLighterColorSignal, this, &MainWindow::redButtonLighterColorSlot);
    connect(&game, &model::blueButtonLighterColorSignal, this, &MainWindow::blueButtonLighterColorSlot);
    connect(&game, &model::revertButtonColorSignal, this, &MainWindow::revertButtonColorSlot);
    connect(this, &MainWindow::updateRound, &game, &model::updateRoundSlot);
    connect(&game, &model::viewUpdateRound, ui->roundNumberLabel, &QLabel::setText);
    connect(this, &MainWindow::resetGame, &game, &model::resetGame);
}
/**
 * Destructor for ui.
 * @brief MainWindow::~MainWindow
 */
MainWindow::~MainWindow()
{
    delete ui;
}

/**
 * Handles when red button is clicked.
 * @brief MainWindow::on_redButton_clicked
 */
void MainWindow::on_redButton_clicked()
{
}

/**
 * Handles when blue button is clicked.
 * @brief MainWindow::on_redButton_clicked
 */
void MainWindow::on_blueButton_clicked()
{
}

/**
 * Handles when start button is clicked.
 * @brief MainWindow::on_redButton_clicked
 */
void MainWindow::on_startButton_clicked()
{
    ui->startButton->setEnabled(false);

    gameInProgress();
}

/**
 * Updates view when correct button is pressed.
 * @brief MainWindow::correctButtonClicked
 */
void MainWindow::correctButtonClicked(){
    emit incrementPlayerInputCounterSignal();
    emit incrementComputerComboPositionSignal();

    // signal to compute
    emit calculateProgressBarValue();

    // When player reaches the end of the correct combination
    emit endOfSequence();
}

/**
 * Updates view when incorrect button is clicked
 * @brief MainWindow::incorrectButtonClicked
 */
void MainWindow::incorrectButtonClicked(){

    // Prevent player from further inputs
    ui->redButton->setEnabled(false);
    ui->blueButton->setEnabled(false);

    // Indication that they failed (buttons turn lighter shade)
    ui->redButton->setStyleSheet( QString("QPushButton {background-color: rgb(255,150,150);} QPushButton:pressed {background-color: rgb(255,150,150);}"));
    ui->blueButton->setStyleSheet( QString("QPushButton {background-color: rgb(150,150,255);} QPushButton:pressed {background-color: rgb(150,150,255);}"));

    // Mean label
    ui->displayLabel->setStyleSheet("QLabel { color : rgb(255,50,50); }");
    ui->displayLabel->setText("Wow, good job. You lose.");

    // Delay for failure indication to appear.
    delayFlash.singleShot(2 * 1000, this, SLOT(endAndResetGame()));
}

/**
 * Updates view and controls when a game is in progress.
 * @brief MainWindow::gameInProgress
 */
void MainWindow::gameInProgress(){

    // Reset combo matching information.
    emit resetPlayerInputCounterAndComputerComboPositionAndAddZeroOrOneToComputerComboSignal();

    // Updating ui elements to match current game state
    ui->displayLabel->setStyleSheet("QLabel { color : rgb(50,50,255); }");
    ui->displayLabel->setText("Computer computing...");
    ui->progressBar->setValue(0);
    ui->redButton->setStyleSheet( QString("QPushButton {background-color: rgb(200,50,50);} QPushButton:pressed {background-color: rgb(255,150,150);}"));
    ui->blueButton->setStyleSheet( QString("QPushButton {background-color: rgb(50,50,200);} QPushButton:pressed {background-color: rgb(150,150,255);}"));
    emit updateRound();

    // Have computer display combo.
    displayComputerComboOneByOne();
}

/**
 * Returns user functionality of the game.
 * @brief MainWindow::resumePlayerTurn
 */
void MainWindow::resumePlayerTurn(){
    ui->redButton->setEnabled(true);
    ui->blueButton->setEnabled(true);
    ui->displayLabel->setText("Punch in a combo, man.");
}
/**
 * Displays the computerCombo.
 * @brief MainWindow::displayComputerComboOneByOne
 */
void MainWindow::displayComputerComboOneByOne(){
    emit incrementComputerComboPositionSignal();
    emit displayComputerComboSignal();
}

/**
 * Reverts a buttons color back to default.
 * @brief MainWindow::revertButtonColor
 */
void MainWindow::revertButtonColor(){
    ui->redButton->setStyleSheet( QString("QPushButton {background-color: rgb(200,50,50);} QPushButton:pressed {background-color: rgb(255,150,150);}"));
    ui->blueButton->setStyleSheet( QString("QPushButton {background-color: rgb(50,50,200);} QPushButton:pressed {background-color: rgb(150,150,255);}"));
}

/**
 * Resets game for a new attempt.
 * @brief MainWindow::endAndResetGame
 */
void MainWindow::endAndResetGame(){
    // Game information is reset.
    emit resetGame();

    // Disable user input.
    ui->redButton->setEnabled(false);
    ui->blueButton->setEnabled(false);

    // Reset window elements.
    ui->progressBar->setValue(0);
    ui->redButton->setStyleSheet( QString("QPushButton {background-color: rgb(200,50,50);} QPushButton:pressed {background-color: rgb(255,150,150);}"));
    ui->blueButton->setStyleSheet( QString("QPushButton {background-color: rgb(50,50,200);} QPushButton:pressed {background-color: rgb(150,150,255);}"));
    ui->displayLabel->setStyleSheet("QLabel { color : rgb(50,50,255); }");
    ui->displayLabel->setText("Press 'Start' to play.");
    ui->roundNumberLabel->setText("Round 0");

    // Enable player to play again.
    ui->startButton->setEnabled(true);
}

void MainWindow::showWinSlot(){
    // Prevent player from further inputs
    ui->redButton->setEnabled(false);
    ui->blueButton->setEnabled(false);

    // Indication that they got to the end (buttons and label turn green)
    ui->redButton->setStyleSheet(QString("QPushButton {background-color: rgb(50,200,50);}"));
    ui->blueButton->setStyleSheet(QString("QPushButton {background-color: rgb(50,200,50);}"));
    ui->displayLabel->setStyleSheet("QLabel { color : rgb(0,100,0); }");
    ui->displayLabel->setText("Woah you can actually do something right!");
    // Delay for green buttons to appear.
    delayFlash.singleShot(2 * 1000, this, SLOT(gameInProgress()));
}

void MainWindow::redButtonLighterColorSlot(){
    ui->redButton->setStyleSheet( QString("QPushButton {background-color: rgb(255,150,150);} QPushButton:pressed {background-color: rgb(255,150,150);}"));
}

void MainWindow::blueButtonLighterColorSlot(){
    ui->blueButton->setStyleSheet( QString("QPushButton {background-color: rgb(150,150,255);} QPushButton:pressed {background-color: rgb(150,150,255);}"));
}

void MainWindow::revertButtonColorSlot(double factor){
    delayFlash.singleShot(factor, this, SLOT(revertButtonColor()));
    delayFlash.singleShot(factor * 2, this, SLOT(displayComputerComboOneByOne()));
}


