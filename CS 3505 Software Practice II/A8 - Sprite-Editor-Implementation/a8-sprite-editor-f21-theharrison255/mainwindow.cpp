/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 08
*/

#include "mainwindow.h"
#include "ui_mainwindow.h"

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

/**
 * Constructor that sets up the ui.
 * @brief MainWindow::MainWindow
 * @param parent
 * @param model
 */
MainWindow::MainWindow(QWidget *parent, Model &model) : QMainWindow(parent), ui(new Ui::MainWindow){
    ui->setupUi(this);

    defaultButtonStyle = "border:none; background-color: #dbdbdb; border-radius:5px;";
    highlightedButtonStyle = "border:2px solid #2ba7ff; background-color: #9c9c9c; border-radius:5px;";
    defaultLabelStyle = "color:#dbdbdb;";
    animationPopUp = new QWidget;

    this->setStyleSheet("background-color:#4d4d4d");
    ui->gridLabel->setStyleSheet("background-color: rgba(0,0,0,0%); border: 1px solid black");

    ui->gridLabel->installEventFilter( this );

    ui->colorPreview->setStyleSheet("border: 1px solid black; background-color: black;");

    ui->framePreview1->setStyleSheet("border: 1px solid black;");
    ui->animationLabel->setStyleSheet("border: 1.5px solid black;");

    ui->frameSelect2->setAutoExclusive(false);
    ui->frameSelect3->setAutoExclusive(false);

    // Set styles for buttons
    ui->brushButton->setStyleSheet(defaultButtonStyle);
    ui->brushButton->setIcon(QIcon(":/paint-brush.png"));
    ui->brushButton->setIconSize(QSize(30, 30));
    ui->eraseButton->setStyleSheet(defaultButtonStyle);
    ui->eraseButton->setIcon(QIcon(":/eraser.png"));
    ui->eraseButton->setIconSize(QSize(30, 30));
    ui->eyedropButton->setStyleSheet(defaultButtonStyle);
    ui->eyedropButton->setIcon(QIcon(":/eyedropper.png"));
    ui->eyedropButton->setIconSize(QSize(30, 30));
    ui->negativeButton->setStyleSheet(defaultButtonStyle);
    ui->negativeButton->setIcon(QIcon(":/negative.png"));
    ui->negativeButton->setIconSize(QSize(30, 30));
    ui->animationExpandButton->setStyleSheet(defaultButtonStyle);
    ui->animationExpandButton->setIcon(QIcon(":/zoom.png"));
    ui->animationExpandButton->setIconSize(QSize(20, 20));
    ui->addFramePushButton->setStyleSheet(defaultButtonStyle);
    ui->deleteFramePushButton->setStyleSheet(defaultButtonStyle);

    // Styles for color picker
    ui->redSlider->setStyleSheet("QSlider::handle:horizontal {background-color: red;}");
    ui->redSpinBox->setStyleSheet(defaultLabelStyle);
    ui->greenSlider->setStyleSheet("QSlider::handle:horizontal {background-color: green;}");
    ui->greenSpinBox->setStyleSheet(defaultLabelStyle);
    ui->blueSlider->setStyleSheet("QSlider::handle:horizontal {background-color: blue;}");
    ui->blueSpinBox->setStyleSheet(defaultLabelStyle);
    ui->alphaSpinBox->setStyleSheet(defaultLabelStyle);
    ui->colorLabel->setStyleSheet(defaultLabelStyle);

    // other ui elements styling
    ui->BrushSizeLabel->setStyleSheet(defaultLabelStyle);
    ui->brushSizeSpinBox->setStyleSheet(defaultLabelStyle);
    ui->frameNumberLabel->setStyleSheet(defaultLabelStyle);
    ui->frameNumberSpinBox->setStyleSheet(defaultLabelStyle);
    ui->menubar->setStyleSheet(defaultLabelStyle);
    ui->menuFile->setStyleSheet(defaultLabelStyle);
    ui->frameSelect2->hide();
    ui->frameSelect3->hide();

    // Setup signal and slots
    connect(ui->redSpinBox, &QSpinBox::valueChanged, &model, &Model::changePixelColorRSlot);
    connect(ui->blueSpinBox, &QSpinBox::valueChanged, &model, &Model::changePixelColorBSlot);
    connect(ui->greenSpinBox, &QSpinBox::valueChanged, &model, &Model::changePixelColorGSlot);
    connect(ui->alphaSpinBox, &QSpinBox::valueChanged, &model, &Model::changePixelColorASlot);
    connect(ui->redSpinBox, &QSpinBox::valueChanged, ui->redSlider, &QSlider::setValue);
    connect(ui->blueSpinBox, &QSpinBox::valueChanged, ui->blueSlider, &QSlider::setValue);
    connect(ui->greenSpinBox, &QSpinBox::valueChanged, ui->greenSlider, &QSlider::setValue);
    connect(ui->alphaSpinBox, &QSpinBox::valueChanged, ui->alphaSlider, &QSlider::setValue);
    connect(ui->redSlider, &QSlider::valueChanged, ui->redSpinBox, &QSpinBox::setValue);
    connect(ui->blueSlider, &QSlider::valueChanged, ui->blueSpinBox, &QSpinBox::setValue);
    connect(ui->greenSlider, &QSlider::valueChanged, ui->greenSpinBox, &QSpinBox::setValue);
    connect(ui->alphaSlider, &QSlider::valueChanged, ui->alphaSpinBox, &QSpinBox::setValue);
    connect(ui->brushButton, &QPushButton::clicked, &model, &Model::changeToBrushSlot);
    connect(ui->eraseButton, &QPushButton::clicked, &model, &Model::changeToEraserSlot);
    connect(ui->eyedropButton, &QPushButton::clicked, &model, &Model::changeToEyedropSlot);
    connect(ui->negativeButton, &QPushButton::clicked, &model, &Model::changeToNegSlot);

    // Updating model and view
    connect(this, &MainWindow::paintPixelSignal, &model, &Model::paintPixelSlot);
    connect(this, &MainWindow::updateModelSignal, &model, &Model::updateModelSlot);

    // Brush size
    connect(&model, &Model::brushSizeChangedSignal, ui->brushSizeSpinBox, &QSpinBox::setValue);
    connect(ui->brushSizeSpinBox, &QSpinBox::valueChanged, &model, &Model::changeBrushSizeSlot);
    connect(ui->brushSizeSpinBox, &QSpinBox::valueChanged, ui->brushSizeHorizontalSlider, &QSlider::setValue);
    connect(ui->brushSizeHorizontalSlider, &QSlider::valueChanged, ui->brushSizeSpinBox, &QSpinBox::setValue);

    // Animation.
    connect(ui->addFramePushButton, &QPushButton::clicked, &model, &Model::addFrameSlot);
    connect(&model, &Model::updateFrameCountSignal, ui->frameNumberSpinBox, &QSpinBox::setMaximum);
    connect(ui->frameNumberSpinBox, &QSpinBox::valueChanged, &model, &Model::changeCurrentFrameSlot);
    connect(ui->deleteFramePushButton, &QPushButton::clicked, &model, &Model::deleteFrameSlot);
    connect(&model, &Model::updateAfterFrameDeletedSignal, ui->frameNumberSpinBox, &QSpinBox::setValue);
    connect(ui->animationSlider, &QSlider::valueChanged, this, &MainWindow::updateAnimationFPSSLot);
    connect(this, &MainWindow::startAnimationSignal, &model, &Model::startAnimationSlot);
    connect(&model, &Model::updateAnimationSignal, this, &MainWindow::updateAnimationSlot);
    connect(this, &MainWindow::nextFrameInAnimationSignal, &model, &Model::nextFrameInAnimationSlot);
    connect(&model, &Model::updateFrameCountDisplaySignal, ui->frameNumberSpinBox, &QSpinBox::setValue);

    // Tools
    connect(&model, &Model::paintPixelSignal, this, &MainWindow::paintPixelSlot);
    connect(this, &MainWindow::changePixelColorSignal, &model, &Model::changePixelColorSlot);
    connect(&model, &Model::updatedPixelColorSignal, this, &MainWindow::updatedPixelColorSlot);
    connect(&model, &Model::eyedropPixelSignal, this, &MainWindow::eyedropPixelSlot);
    connect(this, &MainWindow::eyedropPixelSignal, &model, &Model::eyedropPixelSlot);
    connect(&model, &Model::negativePixelSignal, this, &MainWindow::negativePixelSlot);
    connect(this, &MainWindow::negativePixelSignal, &model, &Model::negativePixelSlot);
    connect(&model, &Model::highlightSelectedToolSignal, this, &MainWindow::highlightSelectedToolSlot);
    connect(&model, &Model::pixmapSignal, this, &MainWindow::updateViewSlot);
    connect(&model, &Model::updateSecondFramePreviewSignal, this, &MainWindow::updateSecondFramePreviewSlot);
    connect(&model, &Model::updateThirdFramePreviewSignal, this, &MainWindow::updateThirdFramePreviewSlot);
    connect(this, &MainWindow::newFrameClickedSignal, &model, &Model::newFrameClickedSlot);
    connect(&model, &Model::updateVisiblePreviewsSignal, this, &MainWindow::updateVisiblePreviewsSlot);

    // Grid
    connect(this, &MainWindow::drawGridSignal, &model, &Model::drawGridSlot);
    connect(&model, &Model::returnGridSignal, this, &MainWindow::returnGridSlot);

    // Resizing canvas
    connect(this, &MainWindow::resizeSignal, &model, &Model::resizeSlot);
    connect(this, &MainWindow::pixelInfoSignal, &model, &Model::pixelInfoSlot);

    // Save and load
    connect(this, &MainWindow::saveToFileSignal, &model, &Model::saveToFileSlot);
    connect(this, &MainWindow::loadFromFileSignal, &model, &Model::loadFromFileSlot);
    connect(ui->animationExpandButton, &QPushButton::clicked, this, &MainWindow::animationExpandSlot);

    // Initial setup
    ui->alphaSpinBox->setValue(255);
    animationFrameRate = 8;
    animationSpeedMS = 1000/animationFrameRate;

    //Setup starting model and corrisponding ui
    emit ui->brushButton->clicked(true);
    emit drawGridSignal();
    emit updateModelSignal();
    emit startAnimationSignal();

}

/**
 * Deletes ui.
 * @brief MainWindow::~MainWindow
 */
MainWindow::~MainWindow(){
    delete ui;
}

/**
 * An event filter for different mouse events and associated actions.
 * @brief MainWindow::eventFilter
 * @param watched
 * @param event
 * @return
 */
bool MainWindow::eventFilter( QObject* watched, QEvent* event ) {
    // If a MouseButtonPress happens inside of the canvas label.
    if ( watched == ui->gridLabel && (event->type() == QEvent::MouseMove || event->type() == QEvent::MouseButtonPress)){
        currentMousePos.setX(QWidget::mapFromGlobal(QCursor::pos()).x() - ui->gridLabel->x());
        currentMousePos.setY(QWidget::mapFromGlobal(QCursor::pos()).y() - ui->gridLabel->y() - ui->menubar->height());
        emit paintPixelSignal();
    }

    return false;
}

/**
 * Updates the canvas.
 * @brief MainWindow::updateViewSlot
 * @param pix
 */
void MainWindow::updateViewSlot(QPixmap pix){
    ui->canvas->setPixmap(pix);
    ui->canvas->show();
    ui->framePreview1->setPixmap(pix.scaled( ui->animationLabel->size(), Qt::KeepAspectRatio, Qt::SmoothTransformation));
    ui->framePreview1->show();
}

/**
 * Paints and displays pixels.
 * @brief MainWindow::paintPixelSlot
 * @param color
 * @param image
 * @param brushSize
 * @param currentDim
 */
void MainWindow::paintPixelSlot(QColor color, QImage *image, int brushSize, int currentDim){
    // Getting the current pixel coordinates through conversion of real mouse coodinates on label (from 0 to 64 in both x and y directions).
    int pixelX = (int)((double) currentMousePos.x() / ((double) image->width() / currentDim));
    int pixelY = (int)((double) currentMousePos.y() / ((double) image->height() / currentDim));

    int pixelXAdjustmentValue = 0;
    int pixelYAdjustmentValue = 0;

    // Adjusting pixelX and pixel Y if brushsize is not even (ends up half way between pixels without adjustments).
    if(!(brushSize % 2)){
        if((double) currentMousePos.x() / ((double) image->width() / currentDim) > ((double) pixelX + 0.5)){
            pixelXAdjustmentValue += ((ui->canvas->height() / currentDim) / 2);
        }
        else{
            pixelXAdjustmentValue -= ((ui->canvas->height() / currentDim) / 2);
        }
        if((double) currentMousePos.y() / ((double) image->height() / currentDim) > ((double) pixelY + 0.5)){
            pixelYAdjustmentValue += ((ui->canvas->height() / currentDim) / 2);
        }
        else{
            pixelYAdjustmentValue -= ((ui->canvas->height() / currentDim) / 2);
        }
    }

    // Drawing in one pixel.
    for (int y = pixelY * (ui->canvas->height() / currentDim) - ((brushSize - 1) * (ui->canvas->height() / currentDim) / 2) + pixelYAdjustmentValue; y < pixelY * (ui->canvas->height() / currentDim) + (image->height() / currentDim) + ((brushSize - 1) * (ui->canvas->height() / currentDim) / 2) + pixelYAdjustmentValue; y++){
        for (int x = pixelX * (ui->canvas->width() / currentDim) - ((brushSize - 1) * (ui->canvas->width() / currentDim) / 2) + pixelYAdjustmentValue; x < pixelX * (ui->canvas->width() / currentDim) + (image->width() / currentDim) + ((brushSize - 1) * (ui->canvas->width() / currentDim) / 2) + pixelYAdjustmentValue; x++){
            int initialX = x;
            int initialY = y;
            if(y<0){
                y = 0;
            }
            if(y >= image->height()){
                y = image->height() - 1;
            }
            if(x<0){
                x = 0;
            }
            if(x >= image->width()){
                x = image->width() - 1;
            }

            image->setPixelColor(x, y, color);
            x = initialX;
            y = initialY;
        }
    }

    // Account for bigger brushes.
    for(int x = pixelX - (int)(brushSize / 2); x < pixelX + (int)(brushSize / 2) + 1; x++){
        if (x > 0 || x <= image->width()){
            for(int y = pixelY - (int)(brushSize / 2); y < pixelY + (int)(brushSize / 2) + 1; y++){
                if (y > 0 || y <= image->height()){
                    //Saving pixel info for tools.
                    emit pixelInfoSignal(x, y);
                }
            }
        }
    }

    // Show changes on the canvas.
    emit updateModelSignal();
}

/**
 * Updates ui elements associated to color change.
 * @brief MainWindow::updatedPixelColorSlot
 * @param color
 */
void MainWindow::updatedPixelColorSlot(QColor color) {
    // Convert color to a style sheet string using the rgba of current pixel color
    QString newColor = QString("border: 1px solid black; background-color: %1").arg(color.name());
    ui->colorPreview->setStyleSheet(newColor);

    // Update color sliders and spin boxes to reflect new color
    ui->redSlider->setValue(color.red());
    ui->greenSlider->setValue(color.green());
    ui->blueSlider->setValue(color.blue());
    ui->alphaSlider->setValue(color.alpha());
}

/**
 * A slot for the eyedrop tool.
 * @brief MainWindow::eyedropPixelSlot
 */
void MainWindow::eyedropPixelSlot() {
    emit eyedropPixelSignal(currentMousePos.x(), currentMousePos.y());
}

/**
 * A slot for the negative tool.
 * @brief MainWindow::eyedropPixelSlot
 */
void MainWindow::negativePixelSlot() {
    emit negativePixelSignal(currentMousePos.x(), currentMousePos.y());
}

/**
 * Updates ui tool buttons depending on which tool is selected.
 * @brief MainWindow::highlightSelectedToolSlot
 * @param prevTool
 * @param newTool
 */
void MainWindow::highlightSelectedToolSlot(char prevTool, char newTool) {
    // Revert previously selected button to default styling
    switch (prevTool) {
        case 'b':
            ui->brushButton->setStyleSheet(defaultButtonStyle);
            break;
        case 'e':
            ui->eraseButton->setStyleSheet(defaultButtonStyle);
            break;
        case 'd':
            ui->eyedropButton->setStyleSheet(defaultButtonStyle);
            break;
        case 'n':
            ui->negativeButton->setStyleSheet(defaultButtonStyle);
            break;
    }
    // Add style to newly selected tool button
    switch (newTool) {
        case 'b':
            ui->brushButton->setStyleSheet(highlightedButtonStyle);
            break;
        case 'e':
            ui->eraseButton->setStyleSheet(highlightedButtonStyle);
            break;
        case 'd':
            ui->eyedropButton->setStyleSheet(highlightedButtonStyle);
            break;
        case 'n':
            ui->negativeButton->setStyleSheet(highlightedButtonStyle);
            break;
    }
}

/**
 * Shows the grid in the ui.
 * @brief MainWindow::returnGridSlot
 * @param gridPix
 */
void MainWindow::returnGridSlot(QPixmap gridPix){
    ui->gridLabel->setPixmap(gridPix);
    ui->gridLabel->show();
}

/**
 * An event for the exit button triggered.
 * @brief MainWindow::on_actionExit_triggered
 */
void MainWindow::on_actionExit_triggered(){
    close();
}

/**
 * An event for the color preview button triggered.
 * @brief MainWindow::on_colorPreview_clicked
 */
void MainWindow::on_colorPreview_clicked(){
   QColor newColor = QColorDialog::getColor(QColor(0,0,0,0));
   emit changePixelColorSignal(newColor.rgb());
}

/**
 * An event for the 1x1 button triggered. Gives a warning and then changes canvas size.
 * @brief MainWindow::on_action1x1_triggered
 */
void MainWindow::on_action1x1_triggered(){
    switch(QMessageBox::critical(this, tr("Sprite Editor"), tr("<FONT COLOR='#ffffff'>This will reset your current work.\nDo you wish to proceed?</FONT>"), QMessageBox::Yes | QMessageBox::No)){
      case QMessageBox::Yes:
        emit resizeSignal(1);
        break;
      case QMessageBox::No:
        break;
      default:
        break;
    }
}

/**
 * An event for the 2x2 button triggered. Gives a warning and then changes canvas size.
 * @brief MainWindow::on_action2x2_triggered
 */
void MainWindow::on_action2x2_triggered(){
    switch(QMessageBox::critical(this, tr("Sprite Editor"), tr("<FONT COLOR='#ffffff'>This will reset your current work.\nDo you wish to proceed?</FONT>"), QMessageBox::Yes | QMessageBox::No)){
      case QMessageBox::Yes:
        emit resizeSignal(2);
        break;
      case QMessageBox::No:
        break;
      default:
        break;
    }
}

/**
 * An event for the 4x4 button triggered. Gives a warning and then changes canvas size.
 * @brief MainWindow::on_action4x4_triggered
 */
void MainWindow::on_action4x4_triggered()
{
    switch(QMessageBox::critical(this, tr("Sprite Editor"), tr("<FONT COLOR='#ffffff'>This will reset your current work.\nDo you wish to proceed?</FONT>"), QMessageBox::Yes | QMessageBox::No))
    {
      case QMessageBox::Yes:
        emit resizeSignal(4);
        break;
      case QMessageBox::No:
        break;
      default:
        break;
    }
}

/**
 * An event for the 8x8 button triggered. Gives a warning and then changes canvas size.
 * @brief MainWindow::on_action8x8_triggered
 */
void MainWindow::on_action8x8_triggered()
{
    switch(QMessageBox::critical(this, tr("Sprite Editor"), tr("<FONT COLOR='#ffffff'>This will reset your current work.\nDo you wish to proceed?</FONT>"), QMessageBox::Yes | QMessageBox::No))
    {
      case QMessageBox::Yes:
        emit resizeSignal(8);
        break;
      case QMessageBox::No:
        break;
      default:
        break;
    }
}

/**
 * An event for the 16x16 button triggered. Gives a warning and then changes canvas size.
 * @brief MainWindow::on_action16x16_triggered
 */
void MainWindow::on_action16x16_triggered(){
    switch(QMessageBox::critical(this, tr("Sprite Editor"), tr("<FONT COLOR='#ffffff'>This will reset your current work.\nDo you wish to proceed?</FONT>"), QMessageBox::Yes | QMessageBox::No)){
      case QMessageBox::Yes:
        emit resizeSignal(16);
        break;
      case QMessageBox::No:
        break;
      default:
        break;
    }
}

/**
 * An event for the 32x32 button triggered. Gives a warning and then changes canvas size.
 * @brief MainWindow::on_action32x32_triggered
 */
void MainWindow::on_action32x32_triggered(){
    switch(QMessageBox::critical(this, tr("Sprite Editor"), tr("<FONT COLOR='#ffffff'>This will reset your current work.\nDo you wish to proceed?</FONT>"), QMessageBox::Yes | QMessageBox::No)){
      case QMessageBox::Yes:
        emit resizeSignal(32);
        break;
      case QMessageBox::No:
        break;
      default:
        break;
    }
}

/**
 * An event for the 64x64 button triggered. Gives a warning and then changes canvas size.
 * @brief MainWindow::on_action64x64_triggered
 */
void MainWindow::on_action64x64_triggered(){
    switch(QMessageBox::critical(this, tr("Sprite Editor"), tr("<FONT COLOR='#ffffff'>This will reset your current work.\nDo you wish to proceed?</FONT>"), QMessageBox::Yes | QMessageBox::No)){
      case QMessageBox::Yes:
        emit resizeSignal(64);
        break;
      case QMessageBox::No:
        break;
      default:
        break;
    }
}

/**
 * Updates the animation fps information.
 * @brief MainWindow::updateAnimationFPSSLot
 * @param animationFPS
 */
void MainWindow::updateAnimationFPSSLot(int animationFPS){
    animationFrameRate = animationFPS;
    animationSpeedMS = 1000/animationFPS;
}

/**
 * An event for the save button triggered.
 * @brief MainWindow::on_actionSave_triggered
 */
void MainWindow::on_actionSave_triggered(){
    std::string filenameString = QFileDialog::getSaveFileName(this, tr("Save File"), "", tr("SSP (*.ssp)")).toStdString();

    char filename[filenameString.size()+1];
    strcpy(filename, filenameString.c_str());

    std::cout<<"saving to '"<<filename<<"'"<<std::endl;

    emit saveToFileSignal(filename);
}

/**
 * An event for the save button triggered. Loads .ssp file from a specified location.
 * @brief MainWindow::on_actionLoad_triggered
 */
void MainWindow::on_actionLoad_triggered(){
    std::string filenameString = QFileDialog::getOpenFileName(this, tr("Save File"), "", tr("SSP (*.ssp)")).toStdString();

    char filename[filenameString.size()+1];
    strcpy(filename, filenameString.c_str());

    std::cout<<"loading from '"<<filename<<"'"<<std::endl;

    emit loadFromFileSignal(filename);
}

/**
 * Updates the animation.
 * @brief MainWindow::updateAnimationFPSSLot
 * @param animationFPS
 */
void MainWindow::updateAnimationSlot(QPixmap pix, int dim){
    ui->animationLabel->setPixmap(pix.scaled( ui->animationLabel->size(), Qt::KeepAspectRatio, Qt::SmoothTransformation));
    ui->animationLabel->show();
    if(animationPopUp->isActiveWindow()){
        animationLabel->setPixmap(pix.scaled(dim, dim, Qt::KeepAspectRatio, Qt::SmoothTransformation));
        animationLabel->show();
    }
    animationTimer.singleShot(animationSpeedMS, this, SLOT(nextFrameInAnimationSlot()));
}

/**
 * Get the next frame in animation
 * @brief MainWindow::nextFrameInAnimationSlot
 */
void MainWindow::nextFrameInAnimationSlot(){
   emit nextFrameInAnimationSignal();
}

/**
 * Updates second frame preview
 * @brief MainWindow::updateSecondFramePreviewSlot
 * @param pix
 */
void MainWindow::updateSecondFramePreviewSlot(QPixmap pix){
    ui->framePreview2->setPixmap(pix.scaled( ui->animationLabel->size(), Qt::KeepAspectRatio, Qt::SmoothTransformation));
    ui->framePreview2->show();
}

/**
 * Updates thrid frame preview
 * @brief MainWindow::updateThirdFramePreviewSlot
 * @param pix
 */
void MainWindow::updateThirdFramePreviewSlot(QPixmap pix){
    ui->framePreview3->setPixmap(pix.scaled( ui->animationLabel->size(), Qt::KeepAspectRatio, Qt::SmoothTransformation));
    ui->framePreview3->show();
}

/**
 * Decides if a frame preview is needed. If it is the last frame, only one frame preview is displayed
 * @brief MainWindow::updateVisiblePreviewsSlot
 * @param frames
 */
void MainWindow::updateVisiblePreviewsSlot(int frames) {
    switch (frames) {
        case 0:
            ui->frameSelect2->hide();
            ui->frameSelect3->hide();
            ui->frameSelect2->setChecked(false);
            ui->frameSelect3->setChecked(false);
            ui->framePreview2->setStyleSheet("border: none;");
            ui->framePreview3->setStyleSheet("border: none;");
        break;
        case 1:
            ui->frameSelect2->show();
            ui->frameSelect3->hide();
            ui->frameSelect2->setChecked(false);
            ui->frameSelect3->setChecked(false);
            ui->framePreview2->setStyleSheet("border: 1px solid black;");
            ui->framePreview3->setStyleSheet("border: none;");
        break;
        default:
            ui->frameSelect2->show();
            ui->frameSelect3->show();
            ui->frameSelect2->setChecked(false);
            ui->frameSelect3->setChecked(false);
            ui->framePreview2->setStyleSheet("border: 1px solid black;");
            ui->framePreview3->setStyleSheet("border: 1px solid black;");
        break;
    }
}

/**
 * When the frame two radial button is clicked, get that frame
 * @brief MainWindow::on_frameSelect2_clicked
 */
void MainWindow::on_frameSelect2_clicked(){
    emit newFrameClickedSignal(2);
}

/**
 * When the frame two radial button is clicked, get that frame
 * @brief MainWindow::on_frameSelect3_clicked
 */
void MainWindow::on_frameSelect3_clicked(){
    emit newFrameClickedSignal(3);
}

/**
 * Open sprite animation in a new window. Sprite is displayed in its actual size
 * @brief MainWindow::animationExpandSlot
 */
void MainWindow::animationExpandSlot(){
    animationPopUp = new QWidget();
    animationPopUp->activateWindow();
    animationPopUp->resize(200,200);
    animationLabel = new QLabel(animationPopUp);
    animationLabel->setAlignment(Qt::AlignCenter);
    QHBoxLayout *layout = new QHBoxLayout();
    layout->addWidget(animationLabel);
    animationPopUp->setLayout(layout);
    animationPopUp->show();
}

