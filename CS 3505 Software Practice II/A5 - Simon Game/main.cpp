/**
 * CS3505 A6: Main file that runs SimonGame
 * @file main.cpp
 * @author Harry Kim & Braden Morfin
 * @version 1.0 10/27/21
 */

#include "mainwindow.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();
    return a.exec();
}
