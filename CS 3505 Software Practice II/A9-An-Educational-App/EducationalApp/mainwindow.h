/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

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
#include "scenario.h"
#include "Ingredient.h"
#include "potion.h"
#include "Ingredient.h"
#include "model.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow{
    Q_OBJECT
    Ui::MainWindow *ui;
    QGraphicsScene *startScene;
    QGraphicsScene *learnMenuSceneDrinkable;
    QGraphicsScene *learnMenuSceneThrowable;
    QGraphicsScene *learnMenuSceneLingering;
    QGraphicsScene *learnMenuSceneRedstoneOrGlowstone;
    QGraphicsScene *tutorialScene;
    QGraphicsScene *congratsScene;
    QStackedWidget *stackedWidget;
    QVector<QImage> tutorialImages;
    QString learnModePotionUsabilitySelection;
    QString learnModePotionEffectSelection;
    QString learnModePotionRedstoneOrGlowstone;
    QList<QLabel*> inventoryLabelList;
    QLabel* ingredientLabelToSnapBack;
    int tutImgIndex;
    QLabel* tutorialImage;
    QLabel* congratsBG;
    QPushButton* cheerMeMoreButton;

    QLabel* addIngredientIcon(QString name, QString image, QRect geom);
    QPushButton* addPotionButton(QRgb potionColor, QString potionTypeImagePath, QGraphicsScene *potionTypeScene, QString potionName, int x, int y);
    void learnGameModeSetUpDrinkable();
    void learnGameModeSetUpThrowable();
    void learnGameModeSetUpLingering();
    void learnGameModeSetUpRedstoneOrGlowstone();
    void tutorialSetUp();
    void clearInventory();
    void congratsScreenSetUp();

public:
    MainWindow(QWidget *parent, Model &model);
    ~MainWindow();
protected:
    bool eventFilter( QObject* watched, QEvent* event );

public slots:
    void displayMainMenu();
    void learnGameMode();
    void learnGameModePotionButtonPressed();
    void learnGameModeDrinkableSelected();
    void learnGameModeThrowableSelected();
    void learnGameModeLingeringSelected();
    void learnGameModeRedstoneSelected();
    void learnGameModeGlowstoneSelected();
    void learnGameModeNothingSelected();
    void setTestLevel(QString title, QString text, int score);
    void setInventory(QVector<Ingredient> ingredients);
    void setPreviousPosOfIconSlot(int lastPosX, int lastPosY);
    void testGameMode();
    void tutorial();
    void congratsScreen();
    void setHint(QString hint);
    void setProgress(int progress);
    void exit();
    void setPotionColor(QPixmap overlay, Ingredient ingredient, int currentIndex, QString potionName);
    void onCongratsImageUpdate(QImage img);
    void removeIngredientIconSlot(int uniqueID);
    void changeLabelLocationSlot(int posx, int posy, int index);
    void updateInstructionSlot(QList<Ingredient> *scenarioIngredients, QString potionName);
    void youGotItRight();
    void learnComplete(QString potionName);
    void simOver();
    void brewingStandCharged();

private slots:
    void on_exitToMenuButton_clicked();
    void tutorialChangeImage(bool isNext);
    void leftButtonTutorialClicked();
    void rightButtonTutorialClicked();
    void exitButtonPressed();
    void on_hintButton_clicked();
    void on_resetButton_clicked();
    void on_learnAnotherButton_clicked();
    void on_testMySkillsButton_clicked();

signals:
    void placedIngredient(QString slot, QString ingredient);
    void startTest();
    void learnStartInformation(QString learnModePotionUsabilitySelection, QString learnModePotionEffectSelection, QString learnModePotionRedstoneOrGlowstone);
    void ingredientIconLastPositionSignal(int uniqueID, int x, int y);
    void getHint();
    void resetScenario();
    void getPreviousPosOfIconSignal(int uniqueID);
    void clearPlacedIngredients();
    void startSim();
    void stopSim();

};
#endif // MAINWINDOW_H
