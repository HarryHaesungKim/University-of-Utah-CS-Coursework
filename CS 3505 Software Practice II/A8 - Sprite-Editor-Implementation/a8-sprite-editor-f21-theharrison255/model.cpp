/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 08
*/

#include "model.h"

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

/**
 * A constructor for a model object to represent the image.
 * @brief Model::Model
 * @param parent
 */
Model::Model(QObject *parent) : QObject(parent){
    pixelColor = QColor(0,0,0,0);
    eraseColor = QColor(0,0,0,0);
    tool = 'b';
    brushSize = 1;
    selectedFrame = 0;

    // Default canvas dimensions set at 64. User can change later.
    currentDim = 64;

    // Set up image
    image = new QImage(512, 512, QImage::Format_RGBA16FPx4);
    frames.push_back(*image);
    image = &frames[selectedFrame];

}

/**
 * Updates the model then sends to update view.
 * @brief Model::updateModelSlot
 */
void Model::updateModelSlot(){
    pix.convertFromImage(*image);
    emit pixmapSignal(pix);
}

/**
 * Updates current tool.
 * @brief Model::changeTool
 * @param tool
 */
void Model::changeTool(char tool){
    emit highlightSelectedToolSignal(this->tool, tool);
    this->tool = tool;
}

/**
 * Changes the color of the pixel.
 * @brief Model::changePixelColorSlot
 * @param color
 */
void Model::changePixelColorSlot(QRgb color){
    pixelColor = color;
    changePixelColorRSlot(qRed(color));
    changePixelColorGSlot(qGreen(color));
    changePixelColorBSlot(qBlue(color));
    changePixelColorASlot(qAlpha(color));
    emit updatedPixelColorSignal(pixelColor);
}

/**
 * Updates the r value of the pixel.
 * @brief Model::changePixelColorRSlot
 * @param rVal
 */
void Model::changePixelColorRSlot(int rVal){
    pixelColor.setRed(rVal);
    emit updatedPixelColorSignal(pixelColor);
}

/**
 * Updates the b value of the pixel.
 * @brief Model::changePixelColorBSlot
 * @param bVal
 */
void Model::changePixelColorBSlot(int bVal){
    pixelColor.setBlue(bVal);
    emit updatedPixelColorSignal(pixelColor);
}

/**
 * Updates the g value of the pixel.
 * @brief Model::changePixelColorGSlot
 * @param gVal
 */
void Model::changePixelColorGSlot(int gVal){
    pixelColor.setGreen(gVal);
    emit updatedPixelColorSignal(pixelColor);
}

/**
 * Updates the a value of the pixel.
 * @brief Model::changePixelColorASlot
 * @param aVal
 */
void Model::changePixelColorASlot(int aVal){
    pixelColor.setAlpha(aVal);
    emit updatedPixelColorSignal(pixelColor);
}

/**
 * Updates the brush size.
 * @brief Model::changeBrushSizeSlot
 * @param brushSizeVal
 */
void Model::changeBrushSizeSlot(int brushSizeVal){
    brushSize = brushSizeVal;
}

/**
 * Painting on the canvas depending on what tool is being used.
 * @brief Model::paintPixelSlot
 */
void Model::paintPixelSlot(){
    switch (tool) {
        case 'b':
            emit paintPixelSignal(pixelColor, image, brushSize, currentDim);
            break;
        case 'e':
            emit paintPixelSignal(eraseColor, image, brushSize, currentDim);
            break;
        case 'd': {
            emit eyedropPixelSignal();
            break;
        }
        case 'n': {
            emit negativePixelSignal();
            break;
        }
    }
}

/**
 * Changes current tool to brush.
 * @brief Model::changeToBrushSlot
 */
void Model::changeToBrushSlot(){
    changeTool('b');
}

/**
 * Changes current tool to eraser.
 * @brief Model::changeToEraserSlot
 */
void Model::changeToEraserSlot(){
    changeTool('e');
}

/**
 * Changes current tool to eyedrop.
 * @brief Model::changeToEyedropSlot
 */
void Model::changeToEyedropSlot(){
    changeTool('d');
}

/**
 * Changes current tool to negative.
 * @brief Model::changeToNegSlot
 */
void Model::changeToNegSlot(){
    changeTool('n');
}

/**
 * Sets the current color to the selected pixel color through the eyedrop tool.
 * @brief Model::eyedropPixelSlot
 * @param x
 * @param y
 */
void Model::eyedropPixelSlot(int x, int y){
    QRgb selectedColor((*image).pixelColor(x, y).rgba());
    // Check if attempting to eyedrop the background
    if (selectedColor == eraseColor.rgba())
        return;
    changePixelColorSlot(selectedColor);
    changeToBrushSlot();
}

/**
 * Paints the negative pixel onto image.
 * @brief Model::negativePixelSlot
 * @param _x
 * @param _y
 */
void Model::negativePixelSlot(int _x, int _y){
    // Calculate current pixel
    int pixelX = (int)((double) _x / ((double) image->width() / currentDim));
    int pixelY = (int)((double) _y / ((double) image->height() / currentDim));

    // If pixel has already been inverted (all pixels in brush area), do nothing
    if (lastNegativePixels.contains({pixelX,pixelY}))
        return;
    // Else, calculate inverted color
    QColor selectedColor((*image).pixelColor(_x, _y));

    // Check if tool is being used over background, if so, do nothing
    int alpha = std::get<0>(pixelInfo[{pixelX, pixelY}]);
    // Compare hex of selected color to background color along with alpha values to account for black
    if (selectedColor.name() == eraseColor.name() && alpha == selectedColor.alpha())
        return;

    emit paintPixelSignal(QColor(255 - selectedColor.red(), 255 - selectedColor.green(), 255 - selectedColor.blue()), image, brushSize, currentDim);
    // Temporary vector to store all pixels inverted
    QVector<QPair <int, int>> painted;

    // Loop through all painted pixels (considering brushSize > 1)
    for(int x = pixelX - (int)(brushSize / 2); x < pixelX + (int)(brushSize / 2) + 1; x++){
        if (x > 0 || x <= image->width()){
            for(int y = pixelY - (int)(brushSize / 2); y < pixelY + (int)(brushSize / 2) + 1; y++){
                if (y > 0 || y <= image->height()){
                    // Add pixel to lastNegativePixels to mark that it has recently been inverted
                    if (!lastNegativePixels.contains({x,y}))
                        lastNegativePixels[{x,y}] = {pixelColor.red(), pixelColor.green(), pixelColor.blue(), pixelColor.alpha()};
                    painted.push_back({x,y});
                }
            }
        }
    }

    // Loop through lastNegativePixels and remove any that are out of scope
    for (QMap<QPair<int, int>, std::tuple<int, int, int, int>>::iterator it = lastNegativePixels.begin(); it != lastNegativePixels.end(); it++){
        if (!painted.contains(it.key()))
            lastNegativePixels.remove(it.key());
    }
}
/**
 * Saves a .ssp file to a specified location.
 * @brief Model::saveToFileSlot
 * @param filename
 */
void Model::saveToFileSlot(char* filename){
    QJsonObject modelJSON;

    QJsonValue heightJSON(currentDim);
    QJsonValue widthJSON(currentDim);
    QJsonValue numFramesJSON(int(frames.size()));

    modelJSON.insert("height", heightJSON);
    modelJSON.insert("width", widthJSON);
    modelJSON.insert("numberOfFrames", numFramesJSON);

    QJsonObject framesJSON;

    for(unsigned int i = 0; i < frames.size(); i++){

        QJsonArray frameJSON;

        for(int row = 0; row < 512; row+=(512/currentDim)){
            QJsonArray rowJSON;

            for(int col = 0; col < 512; col+=(512/currentDim)){
                QJsonArray pixelJSON;

                QRgb pixelColor = frames[i].pixel(col, row);
                pixelJSON.push_back(qRed(pixelColor));
                pixelJSON.push_back(qGreen(pixelColor));
                pixelJSON.push_back(qBlue(pixelColor));
                pixelJSON.push_back(qAlpha(pixelColor));

                rowJSON.push_back(pixelJSON);
            }

            frameJSON.push_back(rowJSON);
        }

        std::string frameIDString = "frame" + std::to_string(i);
        char frameIDArr[frameIDString.size()];
        for(unsigned int j = 0; j < frameIDString.size(); j++){
            frameIDArr[j] = frameIDString[j];
        }

        framesJSON.insert(frameIDArr, frameJSON);
    }

    modelJSON.insert("frames", framesJSON);

    QJsonDocument modelJSONDoc(modelJSON);

    QFile file(filename);

    if (!file.open(QIODevice::WriteOnly | QIODevice::Text))
            return;

    QTextStream out(&file);
        out << modelJSONDoc.toJson();
}

/**
 * Loads a .ssp file from a specified location.
 * @brief Model::loadFromFileSlot
 * @param filename
 */
void Model::loadFromFileSlot(char *filename){
    QFile file(filename);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text))
        return;

    QTextStream in(&file);
    QString fileContents = in.readAll();
    file.close();

    QByteArray fileArr;
    for(int i = 0; i < fileContents.size(); i++){
        fileArr.push_back(fileContents[i].toLatin1());
    }

    QJsonDocument modelJSON = QJsonDocument::fromJson(fileArr);

    int numFrames = modelJSON[QString("numberOfFrames")].toInt();
    int width = modelJSON[QString("width")].toInt();
    int height = modelJSON[QString("height")].toInt();
    QJsonObject framesJSON = modelJSON[QString("frames")].toObject();

    if(width != height){
        return;
    }

    // reset the model
    emit updatedPixelColorSignal(QColor(0,0,0,255));
    changeTool('b');
    brushSize = 1;
    emit brushSizeChangedSignal(1);
    selectedFrame = 0;
    currentDim = width;

    frames.clear();

    frames = std::vector<QImage>(numFrames);

    // Set up image
    for(auto it = framesJSON.begin(); it != framesJSON.end(); it++){
        image = new QImage(512, 512, QImage::Format_RGBA16FPx4);

        for(int row = 0; row < 512; row += 512/currentDim){
            for(int col = 0; col < 512; col += 512/currentDim){
                // get the color
                int r = it->toArray()[row*currentDim/512].toArray()[col*currentDim/512].toArray()[0].toInt();
                int g = it->toArray()[row*currentDim/512].toArray()[col*currentDim/512].toArray()[1].toInt();;
                int b = it->toArray()[row*currentDim/512].toArray()[col*currentDim/512].toArray()[2].toInt();;
                int a = it->toArray()[row*currentDim/512].toArray()[col*currentDim/512].toArray()[3].toInt();;
                QColor currColor(r,g,b,a);

                // paint all the pixels in the block
                for(int y = row; y < row + 512/currentDim; y++){
                    for(int x = col; x < col + 512/currentDim; x++){
                        image->setPixelColor(x, y, currColor);
                    }
                }
            }
        }

        std::string frameNum = it.key().toStdString().substr(5);

        int frameID = atoi(frameNum.c_str());

        frames[frameID] = *image;
    }

    image = &frames[selectedFrame];

    // Redraw grid and update canvas.
    drawGridSlot();
    updateModelSlot();
    changeCurrentFrameSlot(1);
    emit updateFrameCountSignal(frames.size());
}

/**
 * Adds a frame.
 * @brief Model::addFrameSlot
 */
void Model::addFrameSlot(){
    QImage *newFrame = new QImage(512, 512, QImage::Format_RGBA16FPx4);
    frames.push_back(*newFrame);
    emit updateFrameCountSignal(frames.size());
    changeCurrentFrameSlot(selectedFrame+2);
    emit updateFrameCountDisplaySignal(selectedFrame+2);
}

/**
 * Deletes a frame.
 * @brief Model::deleteFrameSlot
 */
void Model::deleteFrameSlot(){
    int currentFrame = selectedFrame;
    if(frames.size() == 1){
        frames.clear();
        QImage *newFrame = new QImage(512, 512, QImage::Format_RGBA16FPx4);
        frames.push_back(*newFrame);
        currentFrame = 1;
    }
    else if(selectedFrame == 0){
        std::vector<QImage>::iterator position = frames.begin();
        frames.erase(position);
        currentFrame = 1;
    }
    else{
        std::vector<QImage>::iterator position = frames.begin() + selectedFrame;
        frames.erase(position);
    }
    emit updateAfterFrameDeletedSignal(currentFrame);
    emit updateFrameCountSignal(frames.size());
    changeCurrentFrameSlot(currentFrame);
    emit updateFrameCountDisplaySignal(currentFrame);
}

/**
 * When radial frame button is click, change to that frame
 * @brief Model::newFrameClickedSlot
 * @param frame
 */
void Model::newFrameClickedSlot(int frame){
    emit updateFrameCountDisplaySignal(selectedFrame + frame);
}

/**
 * Change current working frame to the frame of given number
 * @brief Model::changeCurrentFrameSlot
 * @param frame
 */
void Model::changeCurrentFrameSlot(int frame){
    selectedFrame = frame - 1;
    image = &frames[selectedFrame];
    pix.convertFromImage(*image);
    QPixmap secondPreview;
    QPixmap thirdPreview;
    emit pixmapSignal(pix);
    int remainingFrames = frames.size() - frame;
    if(remainingFrames > 0)
        secondPreview.convertFromImage(frames[selectedFrame + 1]);
    if(remainingFrames > 1)
        thirdPreview.convertFromImage(frames[selectedFrame + 2]);
    emit updateSecondFramePreviewSignal(secondPreview);
    emit updateThirdFramePreviewSignal(thirdPreview);
    emit updateVisiblePreviewsSignal(remainingFrames);
}

/**
 * Draws a grid to an image then sends to be displayed to the ui.
 * @brief Model::drawGridSlot
 */
void Model::drawGridSlot(){
    gridImage = new QImage(512, 512, QImage::Format_RGBA16FPx4);

    // Drawing the grid.
    for (int y = (gridImage->height() / currentDim); y < gridImage->height(); y += (gridImage->height() / currentDim))
        for(int x = 0; x < gridImage->width(); x++)
            gridImage->setPixelColor(x, y, QColor(0, 0, 0, 255));
    for(int x = (gridImage->width() / currentDim) - 1; x < gridImage->width(); x += (gridImage->width() / currentDim))
        for(int y = 0; y < gridImage->height(); y++)
            gridImage->setPixelColor(x, y, QColor(0, 0, 0, 255));
    gridPix.convertFromImage(*gridImage);
    emit returnGridSignal(gridPix);
}

/**
 * Resizes the canvas.
 * @brief Model::resizeSlot
 * @param dimNum
 */
void Model::resizeSlot(int dimNum){
    emit updatedPixelColorSignal(QColor(0,0,0,255));
    changeTool('b');
    brushSize = 1;
    emit brushSizeChangedSignal(1);
    selectedFrame = 0;
    currentDim = dimNum;

    // Set up image
    image = new QImage(512, 512, QImage::Format_RGBA16FPx4);

    // Clear frame info.
    frames.clear();
    frames.push_back(*image);
    image = &frames[selectedFrame];

    // Redraw grid and update canvas.
    drawGridSlot();
    updateModelSlot();
    changeCurrentFrameSlot(1);
    emit updateFrameCountSignal(frames.size());
}

/**
 * Updates or adds pixel info to a map of the pixels.
 * @brief Model::pixelInfoSlot
 * @param pixelX
 * @param pixelY
 */
void Model::pixelInfoSlot(int pixelX, int pixelY){
    pixelCoords.first = pixelX;
    pixelCoords.second = pixelY;
    std::tuple<int, int, int, int> pixelRGBAinfo(pixelColor.red(), pixelColor.green(), pixelColor.blue(), pixelColor.alpha());
    pixelInfo[pixelCoords] = pixelRGBAinfo;
}

/**
 * Initializes animation
 * @brief Model::startAnimationSlot
 */
void Model::startAnimationSlot(){
    animationFrame = 0;
    QPixmap frame;
    frame.convertFromImage((frames[0]));
    emit updateAnimationSignal(frame, currentDim);
}

/**
 * Gets the next frame in the animation
 * @brief Model::nextFrameInAnimationSlot
 */
void Model::nextFrameInAnimationSlot(){
    if(animationFrame >= frames.size() - 1)
        animationFrame = 0;
    else
        animationFrame++;
    QPixmap nextFrame;
    nextFrame.convertFromImage((frames[animationFrame]));
    emit updateAnimationSignal(nextFrame, currentDim);
}
