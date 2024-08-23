/*
* Authors - Cole Hanson, Harry Kim, Braden Morfin, Joshua Taylor, Carolus Theel
* Assignment 09
*/

#include "mainwindow.h"
#include "congratssimulation.h"

#include <QApplication>

int main(int argc, char *argv[])
{

    QApplication a(argc, argv);
    Model model;
    MainWindow w(nullptr, model);
    w.show();
    w.displayMainMenu();
    return a.exec();
}
