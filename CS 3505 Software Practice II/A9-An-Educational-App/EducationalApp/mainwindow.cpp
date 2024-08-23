/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <QMainWindow>
#include <QStackedWidget>
#include <QVBoxLayout>
#include <QGraphicsScene>
#include <QGraphicsView>
#include <QGraphicsTextItem>
#include <QPushButton>
#include <QPalette>
#include <QLabel>
#include <QPainter>
#include <QRadioButton>
#include <QButtonGroup>
#include <QFontDatabase>
#include <QTimer>

#include "scenario.h"
#include "Ingredient.h"
#include "potion.h"
#include "Ingredient.h"
#include "model.h"

/**
 * Setup UI and connects for the Mainwindow.
 * @brief MainWindow::MainWindow
 * @param parent
 * @param model
 */
MainWindow::MainWindow(QWidget *parent, Model &model) : QMainWindow(parent), ui(new Ui::MainWindow){

    ui->setupUi(this);

    // Cropping and rescaling brewing stand image.
    QPixmap pix("../Resources/brewing_stand.png");
    QRect rect(0, 0, pix.width() - 80, pix.height() - 80);
    pix = pix.copy(rect);
    pix = pix.scaled(400, 400, Qt::KeepAspectRatio);

    tutorialImages.push_back(QImage("../Resources/tutorialImages/tutorial1.png"));
    tutorialImages.push_back(QImage("../Resources/tutorialImages/tutorial2.png"));
    tutorialImages.push_back(QImage("../Resources/tutorialImages/tutorial3.png"));
    tutorialImages.push_back(QImage("../Resources/tutorialImages/tutorial4.png"));

    // Setting label to show brewing stand image.
    ui->brewingStandImageLabel->setPixmap(pix);
    ui->brewingStandImageLabel->setAlignment(Qt::AlignCenter);
    ui->brewingStandImageLabel->setStyleSheet("background: transparent");
    ui->brewingStandImageLabel->show();

    // Setting label for indication of charged brewing stand.
    QPixmap pix2("../Resources/brewing_stand.png");
    QRect rect2(176, 29, 18, 4);
    pix2 = pix2.copy(rect2);
    pix2 = pix2.scaled(40, 40, Qt::KeepAspectRatio);

    ui->brewingStandChargeImageLabel->setPixmap(pix2);
    ui->brewingStandChargeImageLabel->setAlignment(Qt::AlignCenter);
    ui->brewingStandChargeImageLabel->setStyleSheet("background: transparent");
    //ui->brewingStandChargeImageLabel->show();
    ui->brewingStandChargeImageLabel->hide();

    ui->exitToMenuButton->setIcon(QIcon(":/resources/exit.png"));
    ui->exitToMenuButton->setStyleSheet("QPushButton{ background: none; border: none; } QToolTip { color: #ffffff; background: #6f6f6f; border: 1px solid black; }");
    ui->exitToMenuButton->setToolTip("Return to Main Menu");
    ui->exitToMenuButton->setIconSize(QSize(51, 51));

    ui->hintButton->setStyleSheet("background: gray; border: 2px solid black; color: white;");
    ui->hintButton->setCursor(Qt::PointingHandCursor);
    ui->hintLabel->setStyleSheet("background: gray;");
    ui->hintLabel->setVisible(false);

    ui->centralwidget->setStyleSheet("background-image:url(:/resources/background.jpg); background-position: center;");
    ui->scenarioLabel->setStyleSheet("background: gray; border: 2px solid black;");
    ui->scenarioTitleLabel->setStyleSheet("background: gray");
    ui->scenarioTextLabel->setStyleSheet("background: gray");
    ui->scoreLabel->setStyleSheet("background: transparent");

    ui->progressBar->setStyleSheet("background: gray; border: 2px solid black");

    ui->resetButton->setStyleSheet("background: gray; border: 1px solid black; color: white;");
    ui->resetButton->setCursor(Qt::PointingHandCursor);

    // Learn complete elements set up
    ui->learnCompleteLabel->setStyleSheet("color: white; background: green; border: 2px solid white;");
    ui->learnAnotherButton->setStyleSheet("font-size: 22px; font-weight: lighter; letter-spacing: 2px; background: #6699cc; color: white; border-radius:2px; border: 2px inset black;");
    ui->learnAnotherButton->setCursor(Qt::PointingHandCursor);
    ui->testMySkillsButton->setStyleSheet("font-size: 22px; font-weight: lighter; letter-spacing: 2px; background: #75816b; color: white; border-radius:2px; border: 2px inset black;");
    ui->testMySkillsButton->setCursor(Qt::PointingHandCursor);

    // Hiding learn stuff
    ui->learnCompleteLabel->hide();
    ui->learnAnotherButton->hide();
    ui->testMySkillsButton->hide();
    ui->learnAnotherButton->setEnabled(false);
    ui->testMySkillsButton->setEnabled(false);

    // Starting menu view.
    QGraphicsView *startView = new QGraphicsView();
    startView->setGeometry(0, 0, (this->width() / 2), (this->height() / 2));
    startView->setStyleSheet("background-image:url(:/resources/background.jpg); background-position: center;");

    // Set up scene
    startScene = new QGraphicsScene;
    startScene->setSceneRect(0, 0, this->width() / 2, this->height() / 2);
    startView->setScene(startScene);

    // Set stackWidget for multiple "pages"
    stackedWidget = new QStackedWidget;
    stackedWidget->addWidget(ui->centralwidget);
    stackedWidget->addWidget(startView);
    learnGameModeSetUpDrinkable(); // Setting up learn scene drinkable (index 2)
    learnGameModeSetUpThrowable(); // Setting up learn scene throwable (index 3)
    learnGameModeSetUpLingering(); // Setting up learn scene throwable (index 4)
    tutorialSetUp(); // Setting up tutorial scene (index 5);

    congratsScreenSetUp(); // Setting up congrats screen (index 6)

    // Set current scene to start menu
    stackedWidget->setCurrentIndex(1);

    // Set layout
    QVBoxLayout *layout = new QVBoxLayout;
    layout->addWidget(stackedWidget);
    setLayout(layout);

    // Set layout in QWidget
    QWidget *window = new QWidget();
    window->setLayout(layout);

    // Set QWidget as the central layout of the main window
    setCentralWidget(window);

    // Connections
    connect(this, &MainWindow::learnStartInformation, &model, &Model::startLearn);
    connect(this, &MainWindow::startTest, &model, &Model::startTest);
    connect(&model, &Model::setInventory, this, &MainWindow::setInventory);
    connect(&model, &Model::setTestLevel, this, &MainWindow::setTestLevel);
    connect(this, &MainWindow::placedIngredient, &model, &Model::placedIngredient);
    connect(this, &MainWindow::ingredientIconLastPositionSignal, &model, &Model::ingredientIconLastPositionSlot);
    connect(this, &MainWindow::getPreviousPosOfIconSignal, &model, &Model::getPreviousPosOfIconSlot);
    connect(&model, &Model::setPreviousPosOfIconSignal, this, &MainWindow::setPreviousPosOfIconSlot);
    connect(this, &MainWindow::clearPlacedIngredients, &model, &Model::clearPlacedIngredients);
    connect(&model, &Model::setPotionColor, this, &MainWindow::setPotionColor);
    connect(&model, &Model::removeIngredientIconSignal, this, &MainWindow::removeIngredientIconSlot);
    connect(&model, &Model::setCongratsConfettiImg, this, &MainWindow::onCongratsImageUpdate);
    connect(this, &MainWindow::getHint, &model, &Model::getHint);
    connect(&model, &Model::setHint, this, &MainWindow::setHint);
    connect(&model, &Model::setProgress, this, &MainWindow::setProgress);
    connect(this, &MainWindow::resetScenario, &model, &Model::resetScenario);
    connect(this, &MainWindow::startSim, &model, &Model::startSim);
    connect(this, &MainWindow::stopSim, &model, &Model::stopSim);
    connect(&model, &Model::changeLabelLocationSignal, this, &MainWindow::changeLabelLocationSlot);
    connect(&model, &Model::congratsScreen, this, &MainWindow::congratsScreen);
    connect(&model, &Model::updateInstructionSignal, this, &MainWindow::updateInstructionSlot);
    connect(&model, &Model::youGotItRight, this, &MainWindow::youGotItRight);
    connect(&model, &Model::learnComplete, this, &MainWindow::learnComplete);
    connect(&model, &Model::simOver, this, &MainWindow::simOver);

    connect(&model, &Model::brewingStandCharged, this, &MainWindow::brewingStandCharged);
}

/**
 * Deletes the UI
 * @brief MainWindow::~MainWindow
 */
MainWindow::~MainWindow(){
    delete ui;
}

/**
 * Add Ingredient Icons to inventoryLabelList
 * @brief MainWindow::addIngredientIcon
 * @param name
 * @param image
 * @param geom
 * @return
 */
QLabel* MainWindow::addIngredientIcon(QString name, QString image, QRect geom){
    // add a label manually.
    QPixmap pix(image);
    QRect rect3(0, 0, pix.width(), pix.height());
    pix = pix.copy(rect3);
    pix = pix.scaled(35, 35, Qt::KeepAspectRatio);

    QLabel *label = new QLabel(ui->centralwidget);
    label->setAlignment(Qt::AlignBottom | Qt::AlignRight);
    label->setGeometry(geom);
    label->setPixmap(pix);
    label->setAlignment(Qt::AlignCenter);
    label->show();
    label->installEventFilter(this);
    label->setStyleSheet("QLabel{ background: none; } QToolTip { color: #ffffff; background: none; background-color: #6f6f6f; border: 1px solid black; }");

    // Illusion of two different blaze powders.
    if(name == "Blaze Powder2"){
        label->setToolTip("Blaze Powder");
    }else{
        label->setToolTip(name);
    }

    label->setCursor(Qt::PointingHandCursor);
    label->setObjectName(name);

    inventoryLabelList.push_back(label);

    return label;
}

/**
 * Clears inventory
 * @brief MainWindow::clearInventory
 */
void MainWindow::clearInventory(){
    ui->brewingStandChargeImageLabel->hide();
    for(int i = 0; i < inventoryLabelList.size(); i++){
        inventoryLabelList[i]->hide();
        inventoryLabelList[i]->deleteLater();
    }
    inventoryLabelList.clear();
    emit clearPlacedIngredients();
}

/**
 * Main menu display
 * @brief MainWindow::displayMainMenu
 */
void MainWindow::displayMainMenu(){
    int buttonSpacing = 60;
    int buttonWidth = 240;
    int buttonHeight = 40;
    QString buttonStyleSheet = "font-size: 22px; font-weight: lighter; letter-spacing: 3px; background-color: #6f6f6f; color: white; border-radius:2px; border: 2px inset black;";

    // create title text
    QGraphicsTextItem* titleText = new QGraphicsTextItem(QString("Minecraft Potions for Noobz"));

    // setting font for title screen.
    int id = QFontDatabase::addApplicationFont("../Resources/minecraft-font/MinecraftBold-nMK1.otf");
    QString family = QFontDatabase::applicationFontFamilies(id).at(0);
    QFont minecraftFont1(family);
    minecraftFont1.setPointSize(40);
    titleText->setFont(minecraftFont1);

    int txPos = startScene->width()/2 - titleText->boundingRect().width() / 2;
    int tyPos = 0;
    titleText->setPos(txPos, tyPos);
    startScene->addItem(titleText);

    // create learn button
    QPushButton *learnButton = new QPushButton();
    int learnButtonPosX = startScene->width() / 2;
    int learnButtonPosY = tyPos + titleText->boundingRect().height() / 2 + buttonSpacing;
    learnButton->setGeometry(QRect(learnButtonPosX - buttonWidth / 2, learnButtonPosY, buttonWidth, buttonHeight));
    learnButton->setText("Learn");
    learnButton->setFont(minecraftFont1);
    learnButton->setStyleSheet(buttonStyleSheet);
    learnButton->setCursor(Qt::PointingHandCursor);
    connect(learnButton, SIGNAL(clicked()), this, SLOT(learnGameMode()));

    // create test button
    QPushButton *testButton = new QPushButton();
    int testButtonPosX = startScene->width() / 2;
    int testButtonPosY = learnButtonPosY + buttonSpacing;
    testButton->setGeometry(QRect(testButtonPosX - buttonWidth / 2, testButtonPosY, buttonWidth, buttonHeight));
    testButton->setText("Test");
    testButton->setFont(minecraftFont1);
    testButton->setStyleSheet(buttonStyleSheet);
    testButton->setCursor(Qt::PointingHandCursor);
    connect(testButton, SIGNAL(clicked()), this, SLOT(testGameMode()));

    // create tutorial button
    QPushButton *tutorialButton = new QPushButton();
    int tutorialButtonPosX = startScene->width() / 2;
    int tutorialButtonPosY = testButtonPosY + buttonSpacing;
    tutorialButton->setGeometry(QRect(tutorialButtonPosX - buttonWidth / 2, tutorialButtonPosY, buttonWidth, buttonHeight));
    tutorialButton->setText("Tutorial");
    tutorialButton->setFont(minecraftFont1);
    tutorialButton->setStyleSheet(buttonStyleSheet);
    tutorialButton->setCursor(Qt::PointingHandCursor);
    connect(tutorialButton, SIGNAL(clicked()), this, SLOT(tutorial()));

    // create exit button
    QPushButton *exitButton = new QPushButton();
    int exitButtonPosX = startScene->width() / 2;
    int exitButtonPosY = tutorialButtonPosY + buttonSpacing;
    exitButton->setGeometry(QRect(exitButtonPosX - buttonWidth / 2, exitButtonPosY, buttonWidth, buttonHeight));
    exitButton->setText("Exit");
    exitButton->setFont(minecraftFont1);
    exitButton->setStyleSheet(buttonStyleSheet);
    exitButton->setCursor(Qt::PointingHandCursor);
    connect(exitButton, SIGNAL(clicked()), this, SLOT(exit()));

    startScene->addWidget(learnButton);
    startScene->addWidget(testButton);
    startScene->addWidget(tutorialButton);
    startScene->addWidget(exitButton);
}

/**
 * Learn gamemode drinkable selection screen setup
 * @brief MainWindow::learnGameModeSetUpDrinkable
 */
void MainWindow::learnGameModeSetUpDrinkable(){
    // Potion type selection.
    learnModePotionUsabilitySelection = "drinkable";

    // Starting learn menu view.
    QGraphicsView *learnMenuView = new QGraphicsView();
    learnMenuView->setGeometry(0, 0, (this->width() / 2), (this->height() / 2));

    // Set up scene
    learnMenuSceneDrinkable = new QGraphicsScene;
    learnMenuSceneDrinkable->setSceneRect(0, 0, this->width() / 2, this->height() / 2);
    learnMenuView->setScene(learnMenuSceneDrinkable);

    QPushButton *exitToMenuButton = new QPushButton();
    exitToMenuButton->setGeometry(QRect(680, -120, 51, 51));
    exitToMenuButton->setIcon(QIcon(":/resources/exit.png"));
    exitToMenuButton->setStyleSheet("QPushButton{ background-color: transparent; border: none; } QToolTip { color: #ffffff; background: #6f6f6f; border: 1px solid black; }");
    exitToMenuButton->setToolTip("Return to Main Menu");
    exitToMenuButton->setIconSize(QSize(51, 51));
    exitToMenuButton->setCursor(Qt::PointingHandCursor);
    connect(exitToMenuButton, SIGNAL(clicked()), this, SLOT(on_exitToMenuButton_clicked()));
    learnMenuSceneDrinkable->addWidget(exitToMenuButton);
    learnMenuView->setStyleSheet("background-image:url(:/resources/background.jpg); background-position: center;");

    QString buttonStyles = "font-size: 18px; padding-left: 5px; background-color: #6f6f6f; color: white; border-radius: 2px; border: 1px solid black;";

    QPushButton* drinkable = new QPushButton("Drinkable");
    drinkable->setGeometry(85, -40, 115, 30);
    drinkable->setStyleSheet(buttonStyles);
    drinkable->setCursor(Qt::PointingHandCursor);
    learnMenuSceneDrinkable->addWidget(drinkable);
    connect(drinkable, SIGNAL(clicked()), this, SLOT(learnGameModeDrinkableSelected()));

    QPushButton* throwable = new QPushButton("Throwable");
    throwable->setGeometry(210, -40, 115, 30);
    throwable->setStyleSheet(buttonStyles);
    throwable->setCursor(Qt::PointingHandCursor);
    learnMenuSceneDrinkable->addWidget(throwable);
    connect(throwable, SIGNAL(clicked()), this, SLOT(learnGameModeThrowableSelected()));

    QPushButton* lingering = new QPushButton("Lingering");
    lingering->setGeometry(335, -40, 115, 30);
    lingering->setStyleSheet(buttonStyles);
    lingering->setCursor(Qt::PointingHandCursor);
    learnMenuSceneDrinkable->addWidget(lingering);
    connect(lingering, SIGNAL(clicked()), this, SLOT(learnGameModeLingeringSelected()));

    // Need to save which type the player has selected.
    QPushButton* potionOfHealing = addPotionButton(QRgb(0xFF0000), "../Resources/potion.png", learnMenuSceneDrinkable, "Instant Healing", 0, 0);
    QPushButton* potionOfFireResistance = addPotionButton(QRgb(0xFFA500), "../Resources/potion.png", learnMenuSceneDrinkable, "Fire Resistance", potionOfHealing->pos().x() + potionOfHealing->width() + 10, 0 );
    QPushButton* potionOfRegeneration = addPotionButton(QRgb(0xFFC0CB), "../Resources/potion.png", learnMenuSceneDrinkable, "Regeneration", potionOfFireResistance->pos().x() + potionOfFireResistance->width() + 10, 0 );
    QPushButton* potionOfStrength = addPotionButton(QRgb(0x8B0000), "../Resources/potion.png", learnMenuSceneDrinkable, "Strength", potionOfRegeneration->pos().x() + potionOfRegeneration->width() + 10, 0 );
    QPushButton* potionOfSwiftness = addPotionButton(QRgb(0x87CEEB), "../Resources/potion.png", learnMenuSceneDrinkable, "Swiftness", potionOfStrength->pos().x() + potionOfStrength->width() + 10, 0 );
    QPushButton* potionOfNightVision = addPotionButton(QRgb(0x0000CD), "../Resources/potion.png", learnMenuSceneDrinkable, "Night Vision", 0, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfInvisibility = addPotionButton(QRgb(0xD3D3D3), "../Resources/potion.png", learnMenuSceneDrinkable, "Invisibility", potionOfNightVision->pos().x() + potionOfNightVision->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfWaterBreathing = addPotionButton(QRgb(0x0000FF), "../Resources/potion.png", learnMenuSceneDrinkable, "Water Breathing", potionOfInvisibility->pos().x() + potionOfInvisibility->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfLeaping = addPotionButton(QRgb(0x8d68b0), "../Resources/potion.png", learnMenuSceneDrinkable, "Leaping", potionOfWaterBreathing->pos().x() + potionOfWaterBreathing->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfSlowFalling = addPotionButton(QRgb(0xD3D3D3), "../Resources/potion.png", learnMenuSceneDrinkable, "Slow Falling", potionOfLeaping->pos().x() + potionOfLeaping->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfPoison = addPotionButton(QRgb(0x006400), "../Resources/potion.png", learnMenuSceneDrinkable, "Poison", 0, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfWeakness = addPotionButton(QRgb(0xA9A9A9), "../Resources/potion.png", learnMenuSceneDrinkable, "Weakness", potionOfPoison->pos().x() + potionOfPoison->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfSlowness = addPotionButton(QRgb(0x607d8b), "../Resources/potion.png", learnMenuSceneDrinkable, "Slowness", potionOfWeakness->pos().x() + potionOfWeakness->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfHarming = addPotionButton(QRgb(0x800000), "../Resources/potion.png", learnMenuSceneDrinkable, "Instant Harming", potionOfSlowness->pos().x() + potionOfSlowness->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfTurtleMaster = addPotionButton(QRgb(0x3A243B), "../Resources/potion.png", learnMenuSceneDrinkable, "Turtle Master", potionOfHarming->pos().x() + potionOfHarming->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);


    stackedWidget->addWidget(learnMenuView);
}

/**
 * Learn gamemode throwable (splash) selection screen setup
 * @brief MainWindow::learnGameModeSetUpThrowable
 */
void MainWindow::learnGameModeSetUpThrowable(){
    // Starting learn menu view.
    QGraphicsView *learnMenuView = new QGraphicsView();
    learnMenuView->setGeometry(0, 0, (this->width() / 2), (this->height() / 2));

    // Set up scene
    learnMenuSceneThrowable = new QGraphicsScene;
    learnMenuSceneThrowable->setSceneRect(0, 0, this->width() / 2, this->height() / 2);
    learnMenuView->setScene(learnMenuSceneThrowable);

    QPushButton *exitToMenuButton = new QPushButton();
    exitToMenuButton->setGeometry(QRect(680, -120, 51, 51));
    exitToMenuButton->setIcon(QIcon(":/resources/exit.png"));
    exitToMenuButton->setStyleSheet("QPushButton{ background-color: transparent; border: none; } QToolTip { color: #ffffff; background: #6f6f6f; border: 1px solid black; }");
    exitToMenuButton->setToolTip("Return to Main Menu");
    exitToMenuButton->setIconSize(QSize(51, 51));
    exitToMenuButton->setCursor(Qt::PointingHandCursor);
    connect(exitToMenuButton, SIGNAL(clicked()), this, SLOT(on_exitToMenuButton_clicked()));
    learnMenuSceneThrowable->addWidget(exitToMenuButton);
    learnMenuView->setStyleSheet("background-image:url(:/resources/background.jpg); background-position: center;");

    QString buttonStyles = "font-size: 18px; padding-left: 5px; background-color: #6f6f6f; color: white; border-radius: 2px; border: 1px solid black;";

    QPushButton* drinkable = new QPushButton("Drinkable");
    drinkable->setGeometry(85, -40, 115, 30);
    drinkable->setStyleSheet(buttonStyles);
    drinkable->setCursor(Qt::PointingHandCursor);
    learnMenuSceneThrowable->addWidget(drinkable);
    connect(drinkable, SIGNAL(clicked()), this, SLOT(learnGameModeDrinkableSelected()));

    QPushButton* throwable = new QPushButton("Throwable");
    throwable->setGeometry(210, -40, 115, 30);
    throwable->setStyleSheet(buttonStyles);
    throwable->setCursor(Qt::PointingHandCursor);
    learnMenuSceneThrowable->addWidget(throwable);
    connect(throwable, SIGNAL(clicked()), this, SLOT(learnGameModeThrowableSelected()));

    QPushButton* lingering = new QPushButton("Lingering");
    lingering->setGeometry(335, -40, 115, 30);
    lingering->setStyleSheet(buttonStyles);
    lingering->setCursor(Qt::PointingHandCursor);
    learnMenuSceneThrowable->addWidget(lingering);
    connect(lingering, SIGNAL(clicked()), this, SLOT(learnGameModeLingeringSelected()));

    QPushButton* potionOfHealing = addPotionButton(QRgb(0xFF0000), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Instant Healing", 0, 0);
    QPushButton* potionOfFireResistance = addPotionButton(QRgb(0xFFA500), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Fire Resistance", potionOfHealing->pos().x() + potionOfHealing->width() + 10, 0 );
    QPushButton* potionOfRegeneration = addPotionButton(QRgb(0xFFC0CB), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Regeneration", potionOfFireResistance->pos().x() + potionOfFireResistance->width() + 10, 0 );
    QPushButton* potionOfStrength = addPotionButton(QRgb(0x8B0000), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Strength", potionOfRegeneration->pos().x() + potionOfRegeneration->width() + 10, 0 );
    QPushButton* potionOfSwiftness = addPotionButton(QRgb(0x87CEEB), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Swiftness", potionOfStrength->pos().x() + potionOfStrength->width() + 10, 0 );
    QPushButton* potionOfNightVision = addPotionButton(QRgb(0x0000CD), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Night Vision", 0, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfInvisibility = addPotionButton(QRgb(0xD3D3D3), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Invisibility", potionOfNightVision->pos().x() + potionOfNightVision->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfWaterBreathing = addPotionButton(QRgb(0x0000FF), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Water Breathing", potionOfInvisibility->pos().x() + potionOfInvisibility->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfLeaping = addPotionButton(QRgb(0x8d68b0), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Leaping", potionOfWaterBreathing->pos().x() + potionOfWaterBreathing->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfSlowFalling = addPotionButton(QRgb(0xD3D3D3), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Slow Falling", potionOfLeaping->pos().x() + potionOfLeaping->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfPoison = addPotionButton(QRgb(0x006400), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Poison", 0, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfWeakness = addPotionButton(QRgb(0xA9A9A9), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Weakness", potionOfPoison->pos().x() + potionOfPoison->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfSlowness = addPotionButton(QRgb(0x607d8b), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Slowness", potionOfWeakness->pos().x() + potionOfWeakness->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfHarming = addPotionButton(QRgb(0x800000), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Instant Harming", potionOfSlowness->pos().x() + potionOfSlowness->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfTurtleMaster = addPotionButton(QRgb(0x3A243B), "../Resources/splash_potion.png", learnMenuSceneThrowable, "Turtle Master", potionOfHarming->pos().x() + potionOfHarming->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);

    stackedWidget->addWidget(learnMenuView);
}

/**
 * Learn gamemode lingering selection screen setup
 * @brief MainWindow::learnGameModeSetUpLingering
 */
void MainWindow::learnGameModeSetUpLingering(){
    // Starting learn menu view.
    QGraphicsView *learnMenuView = new QGraphicsView();
    learnMenuView->setGeometry(0, 0, (this->width() / 2), (this->height() / 2));

    // Set up scene
    learnMenuSceneLingering = new QGraphicsScene;
    learnMenuSceneLingering->setSceneRect(0, 0, this->width() / 2, this->height() / 2);
    learnMenuView->setScene(learnMenuSceneLingering);

    QPushButton *exitToMenuButton = new QPushButton();
    exitToMenuButton->setGeometry(QRect(680, -120, 51, 51));
    exitToMenuButton->setIcon(QIcon(":/resources/exit.png"));
    exitToMenuButton->setStyleSheet("QPushButton{ background-color: transparent; border: none; } QToolTip { color: #ffffff; background: #6f6f6f; border: 1px solid black; }");
    exitToMenuButton->setToolTip("Return to Main Menu");
    exitToMenuButton->setIconSize(QSize(51, 51));
    exitToMenuButton->setCursor(Qt::PointingHandCursor);
    connect(exitToMenuButton, SIGNAL(clicked()), this, SLOT(on_exitToMenuButton_clicked()));
    learnMenuSceneLingering->addWidget(exitToMenuButton);
    learnMenuView->setStyleSheet("background-image:url(:/resources/background.jpg); background-position: center;");

    QString buttonStyles = "font-size: 18px; padding-left: 5px; background-color: #6f6f6f; color: white; border-radius: 2px; border: 1px solid black;";

    QPushButton* drinkable = new QPushButton("Drinkable");
    drinkable->setGeometry(85, -40, 115, 30);
    drinkable->setStyleSheet(buttonStyles);
    drinkable->setCursor(Qt::PointingHandCursor);
    learnMenuSceneLingering->addWidget(drinkable);
    connect(drinkable, SIGNAL(clicked()), this, SLOT(learnGameModeDrinkableSelected()));

    QPushButton* throwable = new QPushButton("Throwable");
    throwable->setGeometry(210, -40, 115, 30);
    throwable->setStyleSheet(buttonStyles);
    throwable->setCursor(Qt::PointingHandCursor);
    learnMenuSceneLingering->addWidget(throwable);
    connect(throwable, SIGNAL(clicked()), this, SLOT(learnGameModeThrowableSelected()));

    QPushButton* lingering = new QPushButton("Lingering");
    lingering->setGeometry(335, -40, 115, 30);
    lingering->setStyleSheet(buttonStyles);
    lingering->setCursor(Qt::PointingHandCursor);
    learnMenuSceneLingering->addWidget(lingering);
    connect(lingering, SIGNAL(clicked()), this, SLOT(learnGameModeLingeringSelected()));

    QPushButton* potionOfHealing = addPotionButton(QRgb(0xFF0000), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Instant Healing", 0, 0);
    QPushButton* potionOfFireResistance = addPotionButton(QRgb(0xFFA500), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Fire Resistance", potionOfHealing->pos().x() + potionOfHealing->width() + 10, 0 );
    QPushButton* potionOfRegeneration = addPotionButton(QRgb(0xFFC0CB), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Regeneration", potionOfFireResistance->pos().x() + potionOfFireResistance->width() + 10, 0 );
    QPushButton* potionOfStrength = addPotionButton(QRgb(0x8B0000), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Strength", potionOfRegeneration->pos().x() + potionOfRegeneration->width() + 10, 0 );
    QPushButton* potionOfSwiftness = addPotionButton(QRgb(0x87CEEB), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Swiftness", potionOfStrength->pos().x() + potionOfStrength->width() + 10, 0 );
    QPushButton* potionOfNightVision = addPotionButton(QRgb(0x0000CD), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Night Vision", 0, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfInvisibility = addPotionButton(QRgb(0xD3D3D3), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Invisibility", potionOfNightVision->pos().x() + potionOfNightVision->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfWaterBreathing = addPotionButton(QRgb(0x0000FF), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Water Breathing", potionOfInvisibility->pos().x() + potionOfInvisibility->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfLeaping = addPotionButton(QRgb(0x8d68b0), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Leaping", potionOfWaterBreathing->pos().x() + potionOfWaterBreathing->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfSlowFalling = addPotionButton(QRgb(0xD3D3D3), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Slow Falling", potionOfLeaping->pos().x() + potionOfLeaping->width() + 10, potionOfHealing->pos().y() + potionOfHealing->height() + 10);
    QPushButton* potionOfPoison = addPotionButton(QRgb(0x006400), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Poison", 0, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfWeakness = addPotionButton(QRgb(0xA9A9A9), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Weakness", potionOfPoison->pos().x() + potionOfPoison->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfSlowness = addPotionButton(QRgb(0x607d8b), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Slowness", potionOfWeakness->pos().x() + potionOfWeakness->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfHarming = addPotionButton(QRgb(0x800000), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Instant Harming", potionOfSlowness->pos().x() + potionOfSlowness->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);
    QPushButton* potionOfTurtleMaster = addPotionButton(QRgb(0x3A243B), "../Resources/lingering_potion.png", learnMenuSceneLingering, "Turtle Master", potionOfHarming->pos().x() + potionOfHarming->width() + 10, potionOfNightVision->pos().y() + potionOfNightVision->height() + 10);

    stackedWidget->addWidget(learnMenuView);
}

/**
 * Learn gamemode select redstone (duration), glowstone (II or level), or nothing (normal potion) menu setup
 * @brief MainWindow::learnGameModeSetUpRedstoneOrGlowstone
 */
void MainWindow::learnGameModeSetUpRedstoneOrGlowstone(){
    // Starting learn menu view.
    QGraphicsView *learnMenuView = new QGraphicsView();
    learnMenuView->setGeometry(0, 0, (this->width() / 2), (this->height() / 2));

    // Set up scene
    learnMenuSceneRedstoneOrGlowstone = new QGraphicsScene;
    learnMenuSceneRedstoneOrGlowstone->setSceneRect(0, 0, this->width() / 2, this->height() / 2);
    learnMenuView->setScene(learnMenuSceneRedstoneOrGlowstone);

    QPushButton *exitToMenuButton = new QPushButton();
    exitToMenuButton->setGeometry(QRect(680, -120, 51, 51));
    exitToMenuButton->setIcon(QIcon(":/resources/exit.png"));
    exitToMenuButton->setStyleSheet("QPushButton{ background-color: transparent; border: none; } QToolTip { color: #ffffff; background: #6f6f6f; border: 1px solid black; }");
    exitToMenuButton->setToolTip("Return to Main Menu");
    exitToMenuButton->setIconSize(QSize(51, 51));
    exitToMenuButton->setCursor(Qt::PointingHandCursor);
    connect(exitToMenuButton, SIGNAL(clicked()), this, SLOT(on_exitToMenuButton_clicked()));
    learnMenuSceneRedstoneOrGlowstone->addWidget(exitToMenuButton);
    learnMenuView->setStyleSheet("background-image:url(:/resources/background.jpg); background-position: center;");

    QString buttonStyles = "font-size: 18px; padding-left: 5px; background-color: #6f6f6f; color: white; border-radius: 2px; border: 1px solid black;";

    // Adding text buttons
    QPushButton* redstoneButton = new QPushButton("Duration+");
    redstoneButton->setGeometry(learnMenuView->width() / 2 - 115 - 10, 30, 115, 30);
    redstoneButton->setStyleSheet(buttonStyles);
    redstoneButton->setCursor(Qt::PointingHandCursor);
    learnMenuSceneRedstoneOrGlowstone->addWidget(redstoneButton);
    connect(redstoneButton, SIGNAL(clicked()), this, SLOT(learnGameModeRedstoneSelected()));

    QPushButton* glowstoneButton = new QPushButton("Intensity+");
    glowstoneButton->setGeometry(learnMenuView->width() / 2 + 10 , 30, 115, 30);
    glowstoneButton->setStyleSheet(buttonStyles);
    glowstoneButton->setCursor(Qt::PointingHandCursor);
    learnMenuSceneRedstoneOrGlowstone->addWidget(glowstoneButton);
    connect(glowstoneButton, SIGNAL(clicked()), this, SLOT(learnGameModeGlowstoneSelected()));

    // Adding redstone image button.
    QPushButton* redstoneImageLabel = new QPushButton();
    QPixmap redstonePix("../Resources/redstone.png");
    redstonePix = redstonePix.scaled(100, 100, Qt::KeepAspectRatio);
    redstoneImageLabel->setIcon(redstonePix);
    redstoneImageLabel->setIconSize(QSize(100, 100));
    redstoneImageLabel->setGeometry(learnMenuView->width() / 2 - 115 - 10, 80, 115, 115);
    redstoneImageLabel->setStyleSheet(buttonStyles);
    redstoneImageLabel->setCursor(Qt::PointingHandCursor);
    redstoneImageLabel->setStyleSheet("QPushButton{ background-color: #6f6f6f; border: 1px solid black; } QToolTip { color: #ffffff; background-color: #6f6f6f; border: 1px solid black; }");
    redstoneImageLabel->setToolTip("Redstone");
    learnMenuSceneRedstoneOrGlowstone->addWidget(redstoneImageLabel);
    connect(redstoneImageLabel, SIGNAL(clicked()), this, SLOT(learnGameModeRedstoneSelected()));

    // Adding glowstone image button.
    QPushButton* glowstoneImageLabel = new QPushButton();
    QPixmap glowstonePix("../Resources/glowstone_dust.png");
    glowstonePix = glowstonePix.scaled(100, 100, Qt::KeepAspectRatio);
    glowstoneImageLabel->setIcon(glowstonePix);
    glowstoneImageLabel->setIconSize(QSize(100, 100));
    glowstoneImageLabel->setGeometry(learnMenuView->width() / 2 + 10, 80, 115, 115);
    glowstoneImageLabel->setStyleSheet(buttonStyles);
    glowstoneImageLabel->setCursor(Qt::PointingHandCursor);
    glowstoneImageLabel->setStyleSheet("QPushButton{ background-color: #6f6f6f; border: 1px solid black; } QToolTip { color: #ffffff; background-color: #6f6f6f; border: 1px solid black; }");
    glowstoneImageLabel->setToolTip("Glowstone");
    learnMenuSceneRedstoneOrGlowstone->addWidget(glowstoneImageLabel);
    connect(glowstoneImageLabel, SIGNAL(clicked()), this, SLOT(learnGameModeGlowstoneSelected()));

    // Adding no potion upgrade text buttons
    QPushButton* noUpgradeButton = new QPushButton("Nothing+");
    noUpgradeButton->setGeometry(learnMenuView->width() / 2 - 57, 215, 115, 30);
    noUpgradeButton->setStyleSheet(buttonStyles);
    noUpgradeButton->setCursor(Qt::PointingHandCursor);
    learnMenuSceneRedstoneOrGlowstone->addWidget(noUpgradeButton);
    connect(noUpgradeButton, SIGNAL(clicked()), this, SLOT(learnGameModeNothingSelected()));

    // If statements for different potions
    if(learnModePotionEffectSelection == "Instant Healing" || learnModePotionEffectSelection == "Instant Harming"){
        redstoneButton->setEnabled(false);
        redstoneImageLabel->setEnabled(false);
        glowstoneButton->setEnabled(true);
        glowstoneImageLabel->setEnabled(true);
    }else if(learnModePotionEffectSelection == "Fire Resistance" || learnModePotionEffectSelection == "Night Vision" || learnModePotionEffectSelection == "Invisibility" || learnModePotionEffectSelection == "Water Breathing" || learnModePotionEffectSelection == "Slow Falling" || learnModePotionEffectSelection == "Weakness"){
        redstoneButton->setEnabled(true);
        redstoneImageLabel->setEnabled(true);
        glowstoneButton->setEnabled(false);
        glowstoneImageLabel->setEnabled(false);
    }else{
        redstoneButton->setEnabled(true);
        redstoneImageLabel->setEnabled(true);
        glowstoneButton->setEnabled(true);
        glowstoneImageLabel->setEnabled(true);
    }
    stackedWidget->addWidget(learnMenuView);
}

/**
 * Go to learn gamemode
 * @brief MainWindow::learnGameMode
 */
void MainWindow::learnGameMode(){
    learnModePotionUsabilitySelection = "drinkable";
    stackedWidget->setCurrentIndex(2);
}

/**
 * When base potion in learn is selected (no redstone/glowstone)
 * @brief MainWindow::learnGameModePotionButtonPressed
 */
void MainWindow::learnGameModePotionButtonPressed(){
    learnModePotionEffectSelection = this->sender()->objectName();
    learnGameModeSetUpRedstoneOrGlowstone(); // Setting up learn scene Redstone or Glowstone (index 6)
    stackedWidget->setCurrentIndex(stackedWidget->count() - 1);
}

/**
 * Go to drinkable menu in learn is when drinkable button is selected
 * @brief MainWindow::learnGameModeDrinkableSelected
 */
void MainWindow::learnGameModeDrinkableSelected(){
    learnModePotionUsabilitySelection = "drinkable";
    stackedWidget->setCurrentIndex(2);
}

/**
 * Go to throwable menu in learn is when throwable button is selected
 * @brief MainWindow::learnGameModeThrowableSelected
 */
void MainWindow::learnGameModeThrowableSelected(){
    learnModePotionUsabilitySelection = "throwable";
    stackedWidget->setCurrentIndex(3);
}

/**
 * Go to lingering menu in learn is when lingering button is selected
 * @brief MainWindow::learnGameModeLingeringSelected
 */
void MainWindow::learnGameModeLingeringSelected(){
    learnModePotionUsabilitySelection = "lingering";
    stackedWidget->setCurrentIndex(4);
}

/**
 * When redstone option in learn is selected
 * @brief MainWindow::learnGameModeRedstoneSelected
 */
void MainWindow::learnGameModeRedstoneSelected(){
    learnModePotionRedstoneOrGlowstone = "redstone";
    emit learnStartInformation(learnModePotionUsabilitySelection, learnModePotionEffectSelection, learnModePotionRedstoneOrGlowstone);
    stackedWidget->setCurrentIndex(0);
}

/**
 * When glowstone option in learn is selected
 * @brief MainWindow::learnGameModeGlowstoneSelected
 */
void MainWindow::learnGameModeGlowstoneSelected(){
    learnModePotionRedstoneOrGlowstone = "glowstone";
    emit learnStartInformation(learnModePotionUsabilitySelection, learnModePotionEffectSelection, learnModePotionRedstoneOrGlowstone);
    stackedWidget->setCurrentIndex(0);
}

/**
 * When nothing option in learn is selected
 * @brief MainWindow::learnGameModeNothingSelected
 */
void MainWindow::learnGameModeNothingSelected(){
    learnModePotionRedstoneOrGlowstone = "nothing";
    emit learnStartInformation(learnModePotionUsabilitySelection, learnModePotionEffectSelection, learnModePotionRedstoneOrGlowstone);
    stackedWidget->setCurrentIndex(0);
}

/**
 * Creates the potion buttons in learn
 * @brief MainWindow::addPotionButton
 * @param potionColor
 * @param potionTypeImagePath
 * @param potionTypeScene
 * @param potionName
 * @param x
 * @param y
 * @return
 */
QPushButton *MainWindow::addPotionButton(QRgb potionColor, QString potionTypeImagePath, QGraphicsScene *potionTypeScene, QString potionName, int x, int y){
    QPixmap overlay("../Resources/potion_overlay.png");

    // Painting overlay a certain color.
    QPainter painter(&overlay);
    painter.setCompositionMode(QPainter::CompositionMode_SourceIn);
    painter.fillRect(overlay.rect(), potionColor);
    painter.end();

    QPixmap pixmap(potionTypeImagePath);
    QPainter painter2(&pixmap);
    painter2.drawPixmap(pixmap.rect(), overlay);
    painter2.end();

    QRect rect3(0, 0, pixmap.width(), pixmap.height());
    pixmap = pixmap.copy(rect3);
    pixmap = pixmap.scaled(100, 100, Qt::KeepAspectRatio);
    QIcon ButtonIcon(pixmap);

    QPushButton *somePotionButton = new QPushButton();

    somePotionButton->setGeometry(QRect(x, y, pixmap.width(), pixmap.height()));

    somePotionButton->setStyleSheet("");
    somePotionButton->setStyleSheet("QPushButton{ background-color: #6f6f6f; border: 1px solid black; } QToolTip { color: #ffffff; background-color: #6f6f6f; border: 1px solid black; }");

    somePotionButton->setIcon(ButtonIcon);
    somePotionButton->setIconSize(pixmap.rect().size());
    somePotionButton->setFixedSize(pixmap.rect().size());
    somePotionButton->setCursor(Qt::PointingHandCursor);
    somePotionButton->setToolTip("Potion of " + potionName);

    somePotionButton->setObjectName(potionName);

    connect(somePotionButton, SIGNAL(clicked()), this, SLOT(learnGameModePotionButtonPressed()));

    potionTypeScene->addWidget(somePotionButton);

    return somePotionButton;
}

/**
 * Go to test gamemode widget
 * @brief MainWindow::testGameMode
 */
void MainWindow::testGameMode(){
    ui->scoreLabel->show();
    ui->progressBar->show();
    stackedWidget->setCurrentIndex(0);
    emit startTest();
}

/**
 * Setup tutorial
 * @brief MainWindow::tutorialSetUp
 */
void MainWindow::tutorialSetUp(){
    // Starting learn menu view.
    QGraphicsView *tutorialView = new QGraphicsView();
    tutorialView->setGeometry(0, 0, (this->width() / 2), (this->height() / 2));

    // Set up scene
    tutorialScene = new QGraphicsScene;
    tutorialScene->setSceneRect(0, 0, this->width() / 2, this->height() / 2);
    tutorialView->setScene(tutorialScene);
    tutorialView->setStyleSheet("background-image:url(:/resources/background.jpg); background-position: center;");

    QPushButton *exitToMenuButton = new QPushButton();
    exitToMenuButton->setGeometry(QRect(680, -120, 51, 51));
    exitToMenuButton->setIcon(QIcon(":/resources/exit.png"));
    exitToMenuButton->setStyleSheet("QPushButton{ background-color: transparent; border: none; } QToolTip { color: #ffffff; background: #6f6f6f; border: 1px solid black; }");
    exitToMenuButton->setToolTip("Return to Main Menu");
    exitToMenuButton->setIconSize(QSize(51, 51));
    exitToMenuButton->setCursor(Qt::PointingHandCursor);
    connect(exitToMenuButton, SIGNAL(clicked()), this, SLOT(on_exitToMenuButton_clicked()));
    tutorialScene->addWidget(exitToMenuButton);

    QString buttonStyleSheet = "font-size: 24px; font-weight: bold; letter-spacing: 1px; background-color: #6f6f6f; color: white; border-radius:2px; border: 2px inset black;";
    QPushButton *leftTutorialButton = new QPushButton();
    leftTutorialButton->setGeometry(QRect(-width()/4 + 30, 410, 200, 50));
    leftTutorialButton->setText("Previous");
    leftTutorialButton->setStyleSheet(buttonStyleSheet);
    leftTutorialButton->setCursor(Qt::PointingHandCursor);
    connect(leftTutorialButton, &QPushButton::clicked, this, &MainWindow::leftButtonTutorialClicked);
    tutorialScene->addWidget(leftTutorialButton);


    QPushButton *rightTutorialButton = new QPushButton();
    rightTutorialButton->setGeometry(QRect(width()*3/4 - 230, 410, 200, 50));
    rightTutorialButton->setText("Next");
    rightTutorialButton->setStyleSheet(buttonStyleSheet);
    rightTutorialButton->setCursor(Qt::PointingHandCursor);
    connect(rightTutorialButton, &QPushButton::clicked, this, &MainWindow::rightButtonTutorialClicked);
    tutorialScene->addWidget(rightTutorialButton);

    tutorialImage = new QLabel();
    QPixmap pixmap;

    pixmap.convertFromImage(tutorialImages[tutImgIndex]);
    QRect rect(-75, -75, width()/2+100 + 50, height()/2+100 + 50);
    pixmap = pixmap.copy(rect);

    tutorialImage->setScaledContents(true);
    tutorialImage->setAlignment(Qt::AlignCenter);
    tutorialImage->setGeometry(-75, -75, width()/2+100 + 50, height()/2+100 + 50);

    tutorialImage->setPixmap(pixmap);

    tutorialScene->addWidget(tutorialImage);
    stackedWidget->addWidget(tutorialView);

    QTimer::singleShot(1, this, &MainWindow::rightButtonTutorialClicked);
    QTimer::singleShot(1, this, &MainWindow::leftButtonTutorialClicked);
}

/**
 * Move to the next image in the tutorial
 * @brief MainWindow::rightButtonTutorialClicked
 */
void MainWindow::rightButtonTutorialClicked(){
    tutorialChangeImage(true);
}

/**
 * Move to the previous image in the tutorial
 * @brief MainWindow::leftButtonTutorialClicked
 */
void MainWindow::leftButtonTutorialClicked(){
    tutorialChangeImage(false);
}

/**
 * Change the tutorial image based on if next or previous button (right or left buttons respectively) was clicked.
 * @brief MainWindow::tutorialChangeImage
 * @param isNext
 */
void MainWindow::tutorialChangeImage(bool isNext){
    //If next button has been clicked, else previous button was clicked
    if(isNext){
        if(tutImgIndex == tutorialImages.size()-1){
            tutImgIndex = 0;
        }else{
            tutImgIndex++;
        }
    }else{
        if(tutImgIndex == 0){
            tutImgIndex = tutorialImages.size()-1;
        }else{
            tutImgIndex--;
        }
    }
    QPixmap pixmap;
    pixmap.convertFromImage(tutorialImages[tutImgIndex]);
    tutorialImage->setPixmap(pixmap);

}

/**
 * Go to tutorial widget
 * @brief MainWindow::tutorial
 */
void MainWindow::tutorial(){
    QPixmap pixmap;
    pixmap.convertFromImage(tutorialImages[0]);
    tutorialImage->setPixmap(pixmap);
    stackedWidget->setCurrentIndex(5);
}

/**
 * Setup the congrats screen
 * @brief MainWindow::congratsScreenSetUp
 */
void MainWindow::congratsScreenSetUp(){
    int id = QFontDatabase::addApplicationFont("../Resources/minecraft-font/MinecraftBold-nMK1.otf");
    QString family = QFontDatabase::applicationFontFamilies(id).at(0);
    QFont minecraftFont1(family);
    minecraftFont1.setPointSize(40);
    QFont minecraftFont2(family);
    minecraftFont2.setPointSize(20);

    // Starting learn menu view.
    QGraphicsView *congratsView = new QGraphicsView();
    congratsView->setGeometry(0, 0, (this->width() / 2), (this->height() / 2));

    // Set up scene
    congratsScene = new QGraphicsScene;
    congratsScene->setSceneRect(0, 0, this->width() / 2, this->height() / 2);
    congratsView->setScene(congratsScene);
    congratsView->setStyleSheet("background-color: 0,0,0,255; background-position: center;");

    congratsBG = new QLabel;
    congratsBG->setGeometry(-this->width()/4, -this->height()/4, this->width(), this->height());
    congratsBG->setScaledContents(true);
    congratsScene->addWidget(congratsBG);

    // Congradulations label
    QLabel *congratsLabel = new QLabel();
    congratsLabel->setGeometry(-this->width()/4, -this->height()/4 - 45, this->width(), this->height());
    congratsLabel->setStyleSheet("background-color: transparent;");
    congratsLabel->setAlignment(Qt::AlignCenter);

    congratsLabel->setFont(minecraftFont1);
    congratsLabel->setText("CONGRATS, POTION MASTER!!!");
    congratsScene->addWidget(congratsLabel);

    // Play again button
    QPushButton* playAgainButton = new QPushButton("Play Again?");
    playAgainButton->setGeometry(-this->width()/4 + this->width()/2 -150 + 200, -this->height()/4 + 450, 300, 100);
    playAgainButton->setCursor(Qt::PointingHandCursor);
    playAgainButton->setFont(minecraftFont2);
    congratsScene->addWidget(playAgainButton);
    connect(playAgainButton, SIGNAL(clicked()), this, SLOT(testGameMode()));

    // Cheer me more button
    cheerMeMoreButton = new QPushButton("Praise me more!");
    cheerMeMoreButton->setGeometry(-this->width()/4 + this->width()/2 -150 - 200, -this->height()/4 + 450, 300, 100);
    cheerMeMoreButton->setCursor(Qt::PointingHandCursor);
    cheerMeMoreButton->setFont(minecraftFont2);
    congratsScene->addWidget(cheerMeMoreButton);
    connect(cheerMeMoreButton, SIGNAL(clicked()), this, SLOT(congratsScreen()));

    // Exit button
    QPushButton *exitToMenuButton = new QPushButton();
    exitToMenuButton->setGeometry(QRect(680, -120, 51, 51));
    exitToMenuButton->setIcon(QIcon(":/resources/exit.png"));
    exitToMenuButton->setIconSize(QSize(51, 51));
    exitToMenuButton->setCursor(Qt::PointingHandCursor);
    exitToMenuButton->setStyleSheet("QPushButton{ background-color: transparent; border: none; } QToolTip { color: #ffffff; background: #6f6f6f; border: 1px solid black; }");
    exitToMenuButton->setToolTip("Return to Main Menu");
    connect(exitToMenuButton, SIGNAL(clicked()), this, SLOT(on_exitToMenuButton_clicked()));
    congratsScene->addWidget(exitToMenuButton);

    stackedWidget->addWidget(congratsView);
}

/**
 * Display congrats screen
 * @brief MainWindow::congratsScreen
 */
void MainWindow::congratsScreen(){
    cheerMeMoreButton->setEnabled(false);
    stackedWidget->setCurrentIndex(6);
    emit startSim();
    QTimer::singleShot(2500, this, &MainWindow::stopSim);
}

/**
 * End the program when exit is clicked
 * @brief MainWindow::exit
 */
void MainWindow::exit(){
    close();
}

/**
 * Go back to main menu
 * @brief MainWindow::on_exitToMenuButton_clicked
 */
void MainWindow::on_exitToMenuButton_clicked(){
    ui->learnCompleteLabel->hide();
    ui->learnAnotherButton->hide();
    ui->testMySkillsButton->hide();
    ui->learnAnotherButton->setEnabled(false);
    ui->testMySkillsButton->setEnabled(false);
    stackedWidget->setCurrentIndex(1);
    clearInventory();
}

/**
 * Watch and place ingredients using drag and drop
 * @brief MainWindow::eventFilter
 * @param watched
 * @param event
 * @return
 */
bool MainWindow::eventFilter( QObject* watched, QEvent* event ) {

    // If a MouseButtonPress happens inside of the canvas label.
    int currentIngredientInventoryIndex = inventoryLabelList.indexOf(dynamic_cast<QLabel*>(watched));
    if ((event->type() == QEvent::MouseMove || event->type() == QEvent::MouseButtonPress)){
        dynamic_cast<QLabel*>(watched)->raise();
        int posx = QWidget::mapFromGlobal(QCursor::pos()).x() - dynamic_cast<QLabel*>(watched)->width() / 2;
        int posy = QWidget::mapFromGlobal(QCursor::pos()).y() - dynamic_cast<QLabel*>(watched)->width() / 2;

        // Checking edges
        if(QWidget::mapFromGlobal(QCursor::pos()).x() < dynamic_cast<QLabel*>(watched)->width() / 2){
            posx = 0;
            if(QWidget::mapFromGlobal(QCursor::pos()).y() < dynamic_cast<QLabel*>(watched)->height() / 2){
                posy = 0;
            }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->centralwidget->height() - dynamic_cast<QLabel*>(watched)->height() / 2){
                posy = ui->centralwidget->height() - dynamic_cast<QLabel*>(watched)->height();
            }else{
                posy = QWidget::mapFromGlobal(QCursor::pos()).y() - dynamic_cast<QLabel*>(watched)->height() / 2;
            }
        }
        else if(QWidget::mapFromGlobal(QCursor::pos()).x() > ui->centralwidget->width() - dynamic_cast<QLabel*>(watched)->width() / 2){
            posx = ui->centralwidget->width() - dynamic_cast<QLabel*>(watched)->width();
            if(QWidget::mapFromGlobal(QCursor::pos()).y() < dynamic_cast<QLabel*>(watched)->height() / 2){
                posy = 0;
            }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->centralwidget->height() - dynamic_cast<QLabel*>(watched)->height() / 2){
                posy = ui->centralwidget->height() - dynamic_cast<QLabel*>(watched)->height();
            }else{
                posy = QWidget::mapFromGlobal(QCursor::pos()).y() - dynamic_cast<QLabel*>(watched)->height() / 2;
            }
        }else if(QWidget::mapFromGlobal(QCursor::pos()).y() < dynamic_cast<QLabel*>(watched)->height() / 2){
            posy = 0;
            if(QWidget::mapFromGlobal(QCursor::pos()).x() < dynamic_cast<QLabel*>(watched)->width() / 2){
                posx = 0;
            }else if(QWidget::mapFromGlobal(QCursor::pos()).x() > ui->centralwidget->width() - dynamic_cast<QLabel*>(watched)->width() / 2){
                posx = ui->centralwidget->width() - dynamic_cast<QLabel*>(watched)->width();
            }else{
                posx = QWidget::mapFromGlobal(QCursor::pos()).x()  - dynamic_cast<QLabel*>(watched)->width() / 2;
            }
        }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->centralwidget->height() - dynamic_cast<QLabel*>(watched)->height() / 2){
            posy = ui->centralwidget->height() - dynamic_cast<QLabel*>(watched)->height();
            if(QWidget::mapFromGlobal(QCursor::pos()).x() < dynamic_cast<QLabel*>(watched)->width() / 2){
                posx = 0;
            }else if(QWidget::mapFromGlobal(QCursor::pos()).x() > ui->centralwidget->width() - dynamic_cast<QLabel*>(watched)->width() / 2){
                posx = ui->centralwidget->width() - dynamic_cast<QLabel*>(watched)->width();
            }else{
                posx = QWidget::mapFromGlobal(QCursor::pos()).x() - dynamic_cast<QLabel*>(watched)->width() / 2;
            }
        }

        //Snap materials in main potion area UI
        int xOffset = ui->brewingStandImageLabel->x() - 10;
        int detectionOffset = ui->brewingStandImageLabel->x() + (QWidget::mapFromGlobal(QCursor::pos()).x() >= 700 ? 45 : 25);
        int inventoryOffset = ui->brewingStandImageLabel->x() + 15;

        // Check inventory grid
        if(QWidget::mapFromGlobal(QCursor::pos()).y() > 280 && QWidget::mapFromGlobal(QCursor::pos()).y() < 410){
            if(QWidget::mapFromGlobal(QCursor::pos()).x() > detectionOffset && QWidget::mapFromGlobal(QCursor::pos()).x() < detectionOffset + 365){
                int _x = (QWidget::mapFromGlobal(QCursor::pos()).x() - detectionOffset) / 40;
                _x = _x > 8 ? 8 : _x;
                int _y = (QWidget::mapFromGlobal(QCursor::pos()).y() - 280) / 43;
                int ingredientXOffset = 8 + ((4 - _x) * -1);
                posx = inventoryOffset + _x * 40 + ingredientXOffset;
                posy = 280 + _y * 41;
            }
        }
        else if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.519 - 20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.519 + 20){
            if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.366-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.366+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.519 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.366 - dynamic_cast<QLabel*>(watched)->height() / 2;
            }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.601-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.601+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.519 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.601 - dynamic_cast<QLabel*>(watched)->height() / 2;
            }
        }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.561-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.561+20){
            if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.386-20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.386+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.386 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.561 - dynamic_cast<QLabel*>(watched)->height() / 2;
            }else if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.646-20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.646+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.646 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.561 - dynamic_cast<QLabel*>(watched)->height() / 2;
            }
        }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.366-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.366+20){
                if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.164-20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.164+20){
                    posx = xOffset + ui->brewingStandImageLabel->width()*0.164 - dynamic_cast<QLabel*>(watched)->width() / 2;
                    posy = ui->brewingStandImageLabel->height()*0.366 - dynamic_cast<QLabel*>(watched)->height() / 2;
                }
        }

        // Setting label to determined position.
        dynamic_cast<QLabel*>(watched)->setGeometry(posx, posy, dynamic_cast<QLabel*>(watched)->width(), dynamic_cast<QLabel*>(watched)->height());
    }
    else if(event->type() == QEvent::MouseButtonRelease){
        bool withinBounds = false;
        QString slot;
        int posx = QWidget::mapFromGlobal(QCursor::pos()).x() - dynamic_cast<QLabel*>(watched)->width() / 2;
        int posy = QWidget::mapFromGlobal(QCursor::pos()).y() - dynamic_cast<QLabel*>(watched)->width() / 2;

        //Snap materials in main potion area UI
        int xOffset = ui->brewingStandImageLabel->x() - 10;
        int detectionOffset = ui->brewingStandImageLabel->x() + (QWidget::mapFromGlobal(QCursor::pos()).x() >= 700 ? 45 : 25);
        int inventoryOffset = ui->brewingStandImageLabel->x() + 15;

        // Check inventory grid
        if(QWidget::mapFromGlobal(QCursor::pos()).y() > 280 && QWidget::mapFromGlobal(QCursor::pos()).y() < 410){
            if(QWidget::mapFromGlobal(QCursor::pos()).x() > detectionOffset && QWidget::mapFromGlobal(QCursor::pos()).x() < detectionOffset + 365){
                int _x = (QWidget::mapFromGlobal(QCursor::pos()).x() - detectionOffset) / 40;
                _x = _x > 8 ? 8 : _x;
                int _y = (QWidget::mapFromGlobal(QCursor::pos()).y() - 280) / 43;
                int ingredientXOffset = 8 + ((4 - _x) * -1);
                posx = inventoryOffset + _x * 40 + ingredientXOffset;
                posy = 280 + _y * 41;
                withinBounds = true;
            }
        }
        else if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.519 - 20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.519 + 20){
            if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.366-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.366+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.519 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.366 - dynamic_cast<QLabel*>(watched)->height() / 2;
                withinBounds = true;
                slot = "base";
            }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.601-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.601+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.519 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.601 - dynamic_cast<QLabel*>(watched)->height() / 2;
                withinBounds = true;
                slot = "bottle";
            }
        }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.561-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.561+20){
            if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.386-20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.386+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.386 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.561 - dynamic_cast<QLabel*>(watched)->height() / 2;
                withinBounds = true;
            }else if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.646-20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.646+20){
                posx = xOffset + ui->brewingStandImageLabel->width()*0.646 - dynamic_cast<QLabel*>(watched)->width() / 2;
                posy = ui->brewingStandImageLabel->height()*0.561 - dynamic_cast<QLabel*>(watched)->height() / 2;
                withinBounds = true;
            }
            slot = "bottle";
        }else if(QWidget::mapFromGlobal(QCursor::pos()).y() > ui->brewingStandImageLabel->height()*0.366-20 && QWidget::mapFromGlobal(QCursor::pos()).y() < ui->brewingStandImageLabel->height()*0.366+20){
                if(QWidget::mapFromGlobal(QCursor::pos()).x() > xOffset + ui->brewingStandImageLabel->width()*0.164-20 && QWidget::mapFromGlobal(QCursor::pos()).x() < xOffset + ui->brewingStandImageLabel->width()*0.164+20){
                    posx = xOffset + ui->brewingStandImageLabel->width()*0.164 - dynamic_cast<QLabel*>(watched)->width() / 2;
                    posy = ui->brewingStandImageLabel->height()*0.366 - dynamic_cast<QLabel*>(watched)->height() / 2;
                    withinBounds = true;
                    slot = "blaze";
                }
        }

        // Snap back to previous position
        if(withinBounds){
            bool validPos = true;
            for(QLabel *ingredientLabel : inventoryLabelList){
                if(inventoryLabelList.indexOf(ingredientLabel) != inventoryLabelList.indexOf(dynamic_cast<QLabel*>(watched))){

                    // If it is within any other label's bounds
                    if ((QWidget::mapFromGlobal(QCursor::pos()).x() > ingredientLabel->pos().x() && QWidget::mapFromGlobal(QCursor::pos()).x() < ingredientLabel->pos().x() + ingredientLabel->width() + 5) && (QWidget::mapFromGlobal(QCursor::pos()).y() > ingredientLabel->pos().y() && QWidget::mapFromGlobal(QCursor::pos()).y() < ingredientLabel->pos().y() + ingredientLabel->height() + 10)){
                        validPos = false;
                    }
                }
            }
            if(validPos){
                dynamic_cast<QLabel*>(watched)->setGeometry(posx, posy, dynamic_cast<QLabel*>(watched)->width(), dynamic_cast<QLabel*>(watched)->height());
                emit ingredientIconLastPositionSignal(currentIngredientInventoryIndex, posx, posy);
                QLabel* selectedIngredient = dynamic_cast<QLabel*>(watched);
                emit placedIngredient(slot, selectedIngredient->objectName());
                return false;
            }else{
                ingredientLabelToSnapBack = dynamic_cast<QLabel*>(watched);
                emit getPreviousPosOfIconSignal(currentIngredientInventoryIndex);
                return false;
            }
        }else{
            // snap back. We need to somehow get the previous position from the ingredient item itself. Still working on how to do that.
            ingredientLabelToSnapBack = dynamic_cast<QLabel*>(watched);
            emit getPreviousPosOfIconSignal(currentIngredientInventoryIndex);
            return false;
        }
    }
    return false;
}

/**
 * Display all elements from the inventory
 * @brief MainWindow::setInventory
 * @param ingredients
 */
void MainWindow::setInventory(QVector<Ingredient> ingredients) {
    clearInventory();
    int xOffset = ui->brewingStandImageLabel->x() - 10;
    int x = 0;
    int y = 0;
    int posx = xOffset + x * 40 + 30;
    int posy = 280 + y * 40;
    int firstIngredientPosX = posx;
    int firstIngredientPosY = posx;

    // Putting each ingredient into the correct position in the inventory.
    for (auto it = ingredients.begin(); it != ingredients.end(); ++it) {
        addIngredientIcon(it->getName(), it->getImageFile(), QRect(posx, posy, 35, 35));
        x = x > 8 ? 0 : x + 1;
        y = x > 8 ? y + 1 : y;
        if(x >= 9){
            x = 0;
        }
        int ingredientXOffset = 32 + ((4 - x) * -1);
        posx = xOffset + x * 40 + ingredientXOffset;
        posy = 280 + y * 41;

        // No model object. Need to send a signal. it->setLastPos(posx, posy);
        emit ingredientIconLastPositionSignal(it->getUniqueID() + 1, posx, posy);
    }
    emit ingredientIconLastPositionSignal(0, firstIngredientPosX + 3, firstIngredientPosY+190);
}

/**
 * Set an ingredients icon to its previous position when it is moved to an inappropiate place
 * @brief MainWindow::setPreviousPosOfIconSlot
 * @param lastPosX
 * @param lastPosY
 */
void MainWindow::setPreviousPosOfIconSlot(int lastPosX, int lastPosY){
    ingredientLabelToSnapBack->setGeometry(lastPosX, lastPosY, ingredientLabelToSnapBack->width(), ingredientLabelToSnapBack->height());
}

/**
 * Setup test levels
 * @brief MainWindow::setTestLevel
 * @param title
 * @param text
 * @param score
 */
void MainWindow::setTestLevel(QString title, QString text, int score) {
    ui->learnCompleteLabel->hide();
    ui->scoreLabel->setText("Score: " + QString::number(score));
    ui->scenarioTitleLabel->setText(title);
    ui->scenarioTextLabel->setText(text);
    emit getHint();
}

/**
 * Change the color of the potion to its current potion color
 * @brief MainWindow::setPotionColor
 * @param overlay
 * @param ingredient
 * @param currentIndex
 * @param potionName
 */
void MainWindow::setPotionColor(QPixmap overlay, Ingredient ingredient, int currentIndex, QString potionName){
    QPixmap pixmap(ingredient.getImageFile());
    QPainter painter(&pixmap);
    painter.drawPixmap(pixmap.rect(), overlay);
    painter.end();
    QRect rect3(0, 0, pixmap.width(), pixmap.height());
    pixmap = pixmap.copy(rect3);
    pixmap = pixmap.scaled(35, 35, Qt::KeepAspectRatio);
    inventoryLabelList[currentIndex]->setPixmap(pixmap);
    inventoryLabelList[currentIndex]->setToolTip(potionName);
}

/**
 * Make an ingredient disappear when it has been used in a potion or as fuel
 * @brief MainWindow::removeIngredientIconSlot
 * @param uniqueID
 */
void MainWindow::removeIngredientIconSlot(int uniqueID){
    inventoryLabelList[uniqueID]->setGeometry(0, 0, 0, 0);
    inventoryLabelList[uniqueID]->hide();
}

/**
 * Update congrats visual
 * @brief MainWindow::onCongratsImageUpdate
 * @param img
 */
void MainWindow::onCongratsImageUpdate(QImage img){
    if(stackedWidget->currentIndex() != 6)
        return;
    // update the img
    congratsBG->setPixmap(QPixmap::fromImage(img).scaled(congratsBG->size()));
}

/**
 * Display hint when hint button is clicked
 * @brief MainWindow::on_hintButton_clicked
 */
void MainWindow::on_hintButton_clicked(){
    if (ui->hintLabel->text().length()) {
        ui->hintButton->setVisible(false);
        ui->hintLabel->setVisible(true);
    }
}

/**
 * Set the hint under the hint button
 * @brief MainWindow::setHint
 * @param hint
 */
void MainWindow::setHint(QString hint) {
    ui->hintLabel->setText("Hint: " + hint);
    ui->hintLabel->setVisible(false);
    if (!hint.length())
        ui->hintButton->setVisible(false);
    else
        ui->hintButton->setVisible(true);
}

/**
 * Set the progress bar value to given progress value
 * @brief MainWindow::setProgress
 * @param progress
 */
void MainWindow::setProgress(int progress) {
    ui->progressBar->setValue(progress);
}

/**
 * Stop sim when sim display is exited
 * @brief MainWindow::exitButtonPressed
 */
void MainWindow::exitButtonPressed(){
    emit stopSim();
}

/**
 * Reset learn or test game inventories
 * @brief MainWindow::on_resetButton_clicked
 */
void MainWindow::on_resetButton_clicked(){
    emit resetScenario();
}

/**
 * Change location of ingredient icons
 * @brief MainWindow::changeLabelLocationSlot
 * @param posx
 * @param posy
 * @param index
 */
void MainWindow::changeLabelLocationSlot(int posx, int posy, int index){
    QLabel *label = inventoryLabelList[index];
    label->setGeometry(posx, posy, label->width(), label->height());
    emit ingredientIconLastPositionSignal(index, posx, posy);
}

/**
 * Add instructions when learning to make a potion. Instructions are specific to selected potion
 * @brief MainWindow::updateInstructionSlot
 * @param scenarioIngredients
 * @param potionName
 */
void MainWindow::updateInstructionSlot(QList<Ingredient> *scenarioIngredients, QString potionName){
    // if statement to tell which slot to put it into.
    bool firstBlaze = true;

    // Changing ui elements
    ui->scenarioTitleLabel->setText(potionName);
    if(!ui->hintButton->isHidden() || !ui->hintLabel->isHidden()){
        ui->hintButton->hide();
        ui->hintLabel->hide();
    }
    ui->scoreLabel->hide();
    ui->progressBar->hide();
    QString textInScenarioTextLabel;
    for(Ingredient i : *scenarioIngredients){
        if(i.getName().contains("Blaze Powder") && firstBlaze){
            textInScenarioTextLabel = textInScenarioTextLabel + "Put a " + i.getName() + " in the Blaze Powder slot." + "\n";
            firstBlaze = false;
        }else if(i.getName().contains("Potion")){
            textInScenarioTextLabel = textInScenarioTextLabel + "Put the water bottle in one of the potion slot." + "\n";
        }
    }
    textInScenarioTextLabel = textInScenarioTextLabel + "Add the following in this order to the base slot: " + "\n";
    for(Ingredient i : *scenarioIngredients){
        if((i.getName().contains("Blaze Powder2") && !firstBlaze)){
            textInScenarioTextLabel = textInScenarioTextLabel + "-" + "Blaze Powder" + "\n";
        }else if(!(i.getName().contains("Potion")) && !(i.getName().contains("Blaze Powder"))){
            textInScenarioTextLabel = textInScenarioTextLabel + "-" + i.getName() + "\n";
        }
    }
    ui->scenarioTextLabel->setText(textInScenarioTextLabel);
}

/**
 * Display conformation of creaating a correct potion
 * @brief MainWindow::youGotItRight
 */
void MainWindow::youGotItRight(){
    ui->learnCompleteLabel->setText("Congratulations! You got it right!");
    ui->learnCompleteLabel->show();
}

/**
 * Display conformation when completing a learn tutorial
 * @brief MainWindow::learnComplete
 * @param potionName
 */
void MainWindow::learnComplete(QString potionName){
    ui->learnCompleteLabel->setText("Good job. You've learned how to brew: " + potionName);
    ui->learnCompleteLabel->show();
    ui->learnAnotherButton->show();
    ui->testMySkillsButton->show();
    ui->learnAnotherButton->setEnabled(true);
    ui->testMySkillsButton->setEnabled(true);
}

/**
 * Enable the cheerMeMoreButton when simuation has finished running
 * @brief MainWindow::simOver
 */
void MainWindow::simOver(){
    cheerMeMoreButton->setEnabled(true);
}

void MainWindow::brewingStandCharged()
{
    ui->brewingStandChargeImageLabel->show();
}

/**
 * Go back to learn menus when learnAnotherButton is clicked
 * @brief MainWindow::on_learnAnotherButton_clicked
 */
void MainWindow::on_learnAnotherButton_clicked(){
    // Hiding learn stuff
    ui->learnCompleteLabel->hide();
    ui->learnAnotherButton->hide();
    ui->testMySkillsButton->hide();
    ui->learnAnotherButton->setEnabled(false);
    ui->testMySkillsButton->setEnabled(false);
    learnGameMode();
}

/**
 * Go to testing game mode when testMySkillsButton is clicked
 * @brief MainWindow::on_testMySkillsButton_clicked
 */
void MainWindow::on_testMySkillsButton_clicked(){
    // Hiding learn stuff
    ui->learnCompleteLabel->hide();
    ui->learnAnotherButton->hide();
    ui->testMySkillsButton->hide();
    ui->learnAnotherButton->setEnabled(false);
    ui->testMySkillsButton->setEnabled(false);
    testGameMode();
}
