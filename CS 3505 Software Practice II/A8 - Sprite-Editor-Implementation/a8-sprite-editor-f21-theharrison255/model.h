/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 08
*/

#ifndef MODEL_H
#define MODEL_H

#include <QObject>
#include <QImage>
#include <QPixmap>
#include <QColor>
#include <iostream>
#include <vector>
#include <QJsonObject>
#include <QJsonArray>
#include <QRgb>
#include <QJsonDocument>
#include <QFile>

class Model : public QObject {
    Q_OBJECT
    QPixmap pix;
    QImage *image;
    QPixmap gridPix;
    QImage *gridImage;
    QColor pixelColor;
    QColor eraseColor;
    char tool;// Current selected tool. 'b' for brush, 'e' for erase, 'd' for eyedrop,
                // and 'n' for negative tool.
    std::vector<QImage> frames;
    int selectedFrame;
    int animationFrame;
    int brushSize;
    int currentDim;
    QMap<QPair<int, int>, std::tuple<int, int, int, int>> lastNegativePixels;
    QPair<int, int> pixelCoords;
    QMap<QPair<int, int>, std::tuple<int, int, int, int>> pixelInfo;

    void changePixelColor(QColor color);
    void changeTool(char tool);
public:
    explicit Model(QObject *parent = nullptr);

signals:
    void paintPixelSignal(QColor color, QImage *image, int brushSize, int currentdim);
    void updatedPixelColorSignal(QColor color);
    void pixmapSignal(QPixmap pix);
    void highlightSelectedToolSignal(char prevTool, char newTool);
    void eyedropPixelSignal();
    void negativePixelSignal();
    void updateFrameCountSignal(int frameCount);
    void updateAfterFrameDeletedSignal(int newFrame);
    void returnGridSignal(QPixmap gridPix);
    void brushSizeChangedSignal(int newBrushSize);
    void updateAnimationSignal(QPixmap pix, int dim);
    void updateFrameCountDisplaySignal(int currentFrame);
    void updateSecondFramePreviewSignal(QPixmap pix);
    void updateThirdFramePreviewSignal(QPixmap pix);
    void updateVisiblePreviewsSignal(int frames);

public slots:
    void changePixelColorRSlot(int rVal);
    void changePixelColorBSlot(int bVal);
    void changePixelColorGSlot(int gVal);
    void changePixelColorASlot(int aVal);
    void changeBrushSizeSlot(int brushSizeVal);
    void paintPixelSlot();
    void changePixelColorSlot(QRgb color);
    void changeToBrushSlot();
    void changeToEraserSlot();
    void changeToEyedropSlot();
    void changeToNegSlot();
    void eyedropPixelSlot(int x, int y);
    void negativePixelSlot(int x, int y);
    void updateModelSlot();
    void addFrameSlot();
    void newFrameClickedSlot(int frame);
    void changeCurrentFrameSlot(int frame);
    void deleteFrameSlot();
    void drawGridSlot();
    void startAnimationSlot();
    void nextFrameInAnimationSlot();
    void resizeSlot(int dimNum);
    void pixelInfoSlot(int pixelX, int pixelY);
    void saveToFileSlot(char* filename);
    void loadFromFileSlot(char* filename);

};

#endif // MODEL_H
