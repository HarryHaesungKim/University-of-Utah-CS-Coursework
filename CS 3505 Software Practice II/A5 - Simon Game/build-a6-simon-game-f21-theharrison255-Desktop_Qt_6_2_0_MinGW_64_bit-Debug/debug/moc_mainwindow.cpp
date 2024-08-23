/****************************************************************************
** Meta object code from reading C++ file 'mainwindow.h'
**
** Created by: The Qt Meta Object Compiler version 68 (Qt 6.2.0)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include <memory>
#include "../../A5 - Simon Game/mainwindow.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'mainwindow.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 68
#error "This file was generated using the moc from 6.2.0. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_MainWindow_t {
    const uint offsetsAndSize[48];
    char stringdata0[548];
};
#define QT_MOC_LITERAL(ofs, len) \
    uint(offsetof(qt_meta_stringdata_MainWindow_t, stringdata0) + ofs), len 
static const qt_meta_stringdata_MainWindow_t qt_meta_stringdata_MainWindow = {
    {
QT_MOC_LITERAL(0, 10), // "MainWindow"
QT_MOC_LITERAL(11, 83), // "resetPlayerInputCounterAndCom..."
QT_MOC_LITERAL(95, 0), // ""
QT_MOC_LITERAL(96, 33), // "incrementPlayerInputCounterSi..."
QT_MOC_LITERAL(130, 36), // "incrementComputerComboPositio..."
QT_MOC_LITERAL(167, 25), // "calculateProgressBarValue"
QT_MOC_LITERAL(193, 13), // "endOfSequence"
QT_MOC_LITERAL(207, 26), // "displayComputerComboSignal"
QT_MOC_LITERAL(234, 11), // "updateRound"
QT_MOC_LITERAL(246, 9), // "resetGame"
QT_MOC_LITERAL(256, 20), // "on_redButton_clicked"
QT_MOC_LITERAL(277, 21), // "on_blueButton_clicked"
QT_MOC_LITERAL(299, 22), // "on_startButton_clicked"
QT_MOC_LITERAL(322, 14), // "gameInProgress"
QT_MOC_LITERAL(337, 16), // "resumePlayerTurn"
QT_MOC_LITERAL(354, 15), // "endAndResetGame"
QT_MOC_LITERAL(370, 28), // "displayComputerComboOneByOne"
QT_MOC_LITERAL(399, 17), // "revertButtonColor"
QT_MOC_LITERAL(417, 20), // "correctButtonClicked"
QT_MOC_LITERAL(438, 22), // "incorrectButtonClicked"
QT_MOC_LITERAL(461, 11), // "showWinSlot"
QT_MOC_LITERAL(473, 25), // "redButtonLighterColorSlot"
QT_MOC_LITERAL(499, 26), // "blueButtonLighterColorSlot"
QT_MOC_LITERAL(526, 21) // "revertButtonColorSlot"

    },
    "MainWindow\0"
    "resetPlayerInputCounterAndComputerComboPositionAndAddZeroOrOneToComput"
    "erComboSignal\0"
    "\0incrementPlayerInputCounterSignal\0"
    "incrementComputerComboPositionSignal\0"
    "calculateProgressBarValue\0endOfSequence\0"
    "displayComputerComboSignal\0updateRound\0"
    "resetGame\0on_redButton_clicked\0"
    "on_blueButton_clicked\0on_startButton_clicked\0"
    "gameInProgress\0resumePlayerTurn\0"
    "endAndResetGame\0displayComputerComboOneByOne\0"
    "revertButtonColor\0correctButtonClicked\0"
    "incorrectButtonClicked\0showWinSlot\0"
    "redButtonLighterColorSlot\0"
    "blueButtonLighterColorSlot\0"
    "revertButtonColorSlot"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_MainWindow[] = {

 // content:
      10,       // revision
       0,       // classname
       0,    0, // classinfo
      22,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       8,       // signalCount

 // signals: name, argc, parameters, tag, flags, initial metatype offsets
       1,    0,  146,    2, 0x06,    1 /* Public */,
       3,    0,  147,    2, 0x06,    2 /* Public */,
       4,    0,  148,    2, 0x06,    3 /* Public */,
       5,    0,  149,    2, 0x06,    4 /* Public */,
       6,    0,  150,    2, 0x06,    5 /* Public */,
       7,    0,  151,    2, 0x06,    6 /* Public */,
       8,    0,  152,    2, 0x06,    7 /* Public */,
       9,    0,  153,    2, 0x06,    8 /* Public */,

 // slots: name, argc, parameters, tag, flags, initial metatype offsets
      10,    0,  154,    2, 0x0a,    9 /* Public */,
      11,    0,  155,    2, 0x0a,   10 /* Public */,
      12,    0,  156,    2, 0x0a,   11 /* Public */,
      13,    0,  157,    2, 0x0a,   12 /* Public */,
      14,    0,  158,    2, 0x0a,   13 /* Public */,
      15,    0,  159,    2, 0x0a,   14 /* Public */,
      16,    0,  160,    2, 0x0a,   15 /* Public */,
      17,    0,  161,    2, 0x0a,   16 /* Public */,
      18,    0,  162,    2, 0x0a,   17 /* Public */,
      19,    0,  163,    2, 0x0a,   18 /* Public */,
      20,    0,  164,    2, 0x0a,   19 /* Public */,
      21,    0,  165,    2, 0x0a,   20 /* Public */,
      22,    0,  166,    2, 0x0a,   21 /* Public */,
      23,    1,  167,    2, 0x0a,   22 /* Public */,

 // signals: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::Double,    2,

       0        // eod
};

void MainWindow::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        auto *_t = static_cast<MainWindow *>(_o);
        (void)_t;
        switch (_id) {
        case 0: _t->resetPlayerInputCounterAndComputerComboPositionAndAddZeroOrOneToComputerComboSignal(); break;
        case 1: _t->incrementPlayerInputCounterSignal(); break;
        case 2: _t->incrementComputerComboPositionSignal(); break;
        case 3: _t->calculateProgressBarValue(); break;
        case 4: _t->endOfSequence(); break;
        case 5: _t->displayComputerComboSignal(); break;
        case 6: _t->updateRound(); break;
        case 7: _t->resetGame(); break;
        case 8: _t->on_redButton_clicked(); break;
        case 9: _t->on_blueButton_clicked(); break;
        case 10: _t->on_startButton_clicked(); break;
        case 11: _t->gameInProgress(); break;
        case 12: _t->resumePlayerTurn(); break;
        case 13: _t->endAndResetGame(); break;
        case 14: _t->displayComputerComboOneByOne(); break;
        case 15: _t->revertButtonColor(); break;
        case 16: _t->correctButtonClicked(); break;
        case 17: _t->incorrectButtonClicked(); break;
        case 18: _t->showWinSlot(); break;
        case 19: _t->redButtonLighterColorSlot(); break;
        case 20: _t->blueButtonLighterColorSlot(); break;
        case 21: _t->revertButtonColorSlot((*reinterpret_cast< double(*)>(_a[1]))); break;
        default: ;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::resetPlayerInputCounterAndComputerComboPositionAndAddZeroOrOneToComputerComboSignal)) {
                *result = 0;
                return;
            }
        }
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::incrementPlayerInputCounterSignal)) {
                *result = 1;
                return;
            }
        }
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::incrementComputerComboPositionSignal)) {
                *result = 2;
                return;
            }
        }
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::calculateProgressBarValue)) {
                *result = 3;
                return;
            }
        }
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::endOfSequence)) {
                *result = 4;
                return;
            }
        }
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::displayComputerComboSignal)) {
                *result = 5;
                return;
            }
        }
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::updateRound)) {
                *result = 6;
                return;
            }
        }
        {
            using _t = void (MainWindow::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&MainWindow::resetGame)) {
                *result = 7;
                return;
            }
        }
    }
}

const QMetaObject MainWindow::staticMetaObject = { {
    QMetaObject::SuperData::link<QMainWindow::staticMetaObject>(),
    qt_meta_stringdata_MainWindow.offsetsAndSize,
    qt_meta_data_MainWindow,
    qt_static_metacall,
    nullptr,
qt_incomplete_metaTypeArray<qt_meta_stringdata_MainWindow_t
, QtPrivate::TypeAndForceComplete<MainWindow, std::true_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>
, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<double, std::false_type>


>,
    nullptr
} };


const QMetaObject *MainWindow::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *MainWindow::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_MainWindow.stringdata0))
        return static_cast<void*>(this);
    return QMainWindow::qt_metacast(_clname);
}

int MainWindow::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QMainWindow::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 22)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 22;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 22)
            *reinterpret_cast<QMetaType *>(_a[0]) = QMetaType();
        _id -= 22;
    }
    return _id;
}

// SIGNAL 0
void MainWindow::resetPlayerInputCounterAndComputerComboPositionAndAddZeroOrOneToComputerComboSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 0, nullptr);
}

// SIGNAL 1
void MainWindow::incrementPlayerInputCounterSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 1, nullptr);
}

// SIGNAL 2
void MainWindow::incrementComputerComboPositionSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 2, nullptr);
}

// SIGNAL 3
void MainWindow::calculateProgressBarValue()
{
    QMetaObject::activate(this, &staticMetaObject, 3, nullptr);
}

// SIGNAL 4
void MainWindow::endOfSequence()
{
    QMetaObject::activate(this, &staticMetaObject, 4, nullptr);
}

// SIGNAL 5
void MainWindow::displayComputerComboSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 5, nullptr);
}

// SIGNAL 6
void MainWindow::updateRound()
{
    QMetaObject::activate(this, &staticMetaObject, 6, nullptr);
}

// SIGNAL 7
void MainWindow::resetGame()
{
    QMetaObject::activate(this, &staticMetaObject, 7, nullptr);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
