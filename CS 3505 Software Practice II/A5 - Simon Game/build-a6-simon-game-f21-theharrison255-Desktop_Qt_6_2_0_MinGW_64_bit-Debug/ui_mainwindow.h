/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 6.2.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QProgressBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralwidget;
    QPushButton *startButton;
    QPushButton *redButton;
    QPushButton *blueButton;
    QProgressBar *progressBar;
    QLabel *displayLabel;
    QLabel *roundNumberLabel;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QString::fromUtf8("MainWindow"));
        MainWindow->resize(800, 600);
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        startButton = new QPushButton(centralwidget);
        startButton->setObjectName(QString::fromUtf8("startButton"));
        startButton->setGeometry(QRect(10, 10, 80, 21));
        redButton = new QPushButton(centralwidget);
        redButton->setObjectName(QString::fromUtf8("redButton"));
        redButton->setGeometry(QRect(10, 60, 381, 451));
        blueButton = new QPushButton(centralwidget);
        blueButton->setObjectName(QString::fromUtf8("blueButton"));
        blueButton->setGeometry(QRect(410, 60, 381, 451));
        progressBar = new QProgressBar(centralwidget);
        progressBar->setObjectName(QString::fromUtf8("progressBar"));
        progressBar->setGeometry(QRect(100, 10, 691, 23));
        progressBar->setValue(24);
        displayLabel = new QLabel(centralwidget);
        displayLabel->setObjectName(QString::fromUtf8("displayLabel"));
        displayLabel->setGeometry(QRect(16, 40, 771, 16));
        displayLabel->setAlignment(Qt::AlignCenter);
        roundNumberLabel = new QLabel(centralwidget);
        roundNumberLabel->setObjectName(QString::fromUtf8("roundNumberLabel"));
        roundNumberLabel->setGeometry(QRect(296, 519, 211, 31));
        QFont font;
        font.setPointSize(14);
        roundNumberLabel->setFont(font);
        roundNumberLabel->setAlignment(Qt::AlignCenter);
        MainWindow->setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        menubar->setGeometry(QRect(0, 0, 800, 26));
        MainWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        MainWindow->setStatusBar(statusbar);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QCoreApplication::translate("MainWindow", "SimonGame", nullptr));
        startButton->setText(QCoreApplication::translate("MainWindow", "Start", nullptr));
        redButton->setText(QString());
        blueButton->setText(QString());
        displayLabel->setText(QCoreApplication::translate("MainWindow", "Press 'Start' to play.", nullptr));
        roundNumberLabel->setText(QCoreApplication::translate("MainWindow", "Round 0", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
