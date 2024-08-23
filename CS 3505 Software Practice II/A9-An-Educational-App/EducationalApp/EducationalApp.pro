QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++11

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    Ingredient.cpp \
    congratssimulation.cpp \
    main.cpp \
    mainwindow.cpp \
    model.cpp \
    potion.cpp \
    scenario.cpp

HEADERS += \
    Ingredient.h \
    congratssimulation.h \
    mainwindow.h \
    model.h \
    potion.h \
    scenario.h

FORMS += \
    mainwindow.ui

INCLUDEPATH += $$PWD/../box2d-master/include
DEPENDPATH += $$PWD/../box2d-master/include

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../box2d-master/bin/ -lbox2
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../box2d-master/bin/ -lbox2d
else:unix: LIBS += -L$$PWD/../box2d-master/bin/ -lbox2

INCLUDEPATH += $$PWD/../box2d-master/bin
DEPENDPATH += $$PWD/../box2d-master/bin

RESOURCES += \
    ../Resources/resources.qrc
