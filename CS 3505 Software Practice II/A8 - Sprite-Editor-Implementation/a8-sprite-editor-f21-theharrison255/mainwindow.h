/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 08
*/

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTimer>
#include <iostream>
#include <QMouseEvent>
#include <QColorDialog>
#include <QFileDialog>
#include <QHBoxLayout>
#include "model.h"
#include "qpainter.h"
#include "qpen.h"
#include "QMessageBox"

namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow{
    Q_OBJECT
    Ui::MainWindow *ui;
    QPoint currentMousePos;
    QString defaultButtonStyle;
    QString highlightedButtonStyle;
    QString defaultLabelStyle;
    int animationFrameRate;
    double animationSpeedMS;
    QTimer animationTimer;
    QWidget *animationPopUp;
    QLabel *animationLabel;

public:
    MainWindow(QWidget *parent, Model &model);
    ~MainWindow();

signals:
    void paintPixelSignal();
    void changePixelColorSignal(QRgb color);
    void drawGridSignal();
    void updateModelSignal();
    void eyedropPixelSignal(int x, int y);
    void negativePixelSignal(int x, int y);
    void resizeSignal(int dimNum);
    void pixelInfoSignal(int pixelX, int pixelY);
    void saveToFileSignal(char* filename);
    void loadFromFileSignal(char* filename);
    void startAnimationSignal();
    void nextFrameInAnimationSignal();
    void newFrameClickedSignal(int frame);

private slots:
    void on_actionExit_triggered();
    void on_colorPreview_clicked();
    void on_action1x1_triggered();
    void on_action2x2_triggered();
    void on_action4x4_triggered();
    void on_action8x8_triggered();
    void on_action16x16_triggered();
    void on_action32x32_triggered();
    void on_action64x64_triggered();
    void on_actionSave_triggered();
    void on_actionLoad_triggered();
    void on_frameSelect2_clicked();
    void on_frameSelect3_clicked();

public slots:
    void paintPixelSlot(QColor color, QImage *image, int brushSize, int currentDim);
    void updatedPixelColorSlot(QColor color);
    void updateViewSlot(QPixmap pix);
    void highlightSelectedToolSlot(char prevTool, char newTool);
    void eyedropPixelSlot();
    void negativePixelSlot();    
    void updateAnimationFPSSLot(int animationFPS);
    void updateAnimationSlot(QPixmap pix, int dim);
    void nextFrameInAnimationSlot();
    void updateSecondFramePreviewSlot(QPixmap pix);
    void updateThirdFramePreviewSlot(QPixmap pix);
    void updateVisiblePreviewsSlot(int frames);
    void returnGridSlot(QPixmap gridPix);
    void animationExpandSlot();

protected:
    bool eventFilter( QObject* watched, QEvent* event );
};
#endif // MAINWINDOW_H
