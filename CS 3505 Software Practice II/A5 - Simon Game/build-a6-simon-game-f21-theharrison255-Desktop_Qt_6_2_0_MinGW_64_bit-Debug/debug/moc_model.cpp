/****************************************************************************
** Meta object code from reading C++ file 'model.h'
**
** Created by: The Qt Meta Object Compiler version 68 (Qt 6.2.0)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include <memory>
#include "../../A5 - Simon Game/model.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'model.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 68
#error "This file was generated using the moc from 6.2.0. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_model_t {
    const uint offsetsAndSize[40];
    char stringdata0[417];
};
#define QT_MOC_LITERAL(ofs, len) \
    uint(offsetof(qt_meta_stringdata_model_t, stringdata0) + ofs), len 
static const qt_meta_stringdata_model_t qt_meta_stringdata_model = {
    {
QT_MOC_LITERAL(0, 5), // "model"
QT_MOC_LITERAL(6, 30), // "updateViewCorrectButtonClicked"
QT_MOC_LITERAL(37, 0), // ""
QT_MOC_LITERAL(38, 32), // "updateViewIncorrectButtonClicked"
QT_MOC_LITERAL(71, 17), // "updateProgressBar"
QT_MOC_LITERAL(89, 7), // "showWin"
QT_MOC_LITERAL(97, 22), // "resumePlayerTurnSignal"
QT_MOC_LITERAL(120, 27), // "redButtonLighterColorSignal"
QT_MOC_LITERAL(148, 28), // "blueButtonLighterColorSignal"
QT_MOC_LITERAL(177, 23), // "revertButtonColorSignal"
QT_MOC_LITERAL(201, 15), // "viewUpdateRound"
QT_MOC_LITERAL(217, 16), // "redButtonPressed"
QT_MOC_LITERAL(234, 17), // "blueButtonPressed"
QT_MOC_LITERAL(252, 13), // "startNewRound"
QT_MOC_LITERAL(266, 31), // "incrementPlayerInputCounterSlot"
QT_MOC_LITERAL(298, 34), // "incrementComputerComboPositio..."
QT_MOC_LITERAL(333, 24), // "calculateProgressBarSlot"
QT_MOC_LITERAL(358, 17), // "endOfSequenceSlot"
QT_MOC_LITERAL(376, 24), // "displayComputerComboSlot"
QT_MOC_LITERAL(401, 15) // "updateRoundSlot"

    },
    "model\0updateViewCorrectButtonClicked\0"
    "\0updateViewIncorrectButtonClicked\0"
    "updateProgressBar\0showWin\0"
    "resumePlayerTurnSignal\0"
    "redButtonLighterColorSignal\0"
    "blueButtonLighterColorSignal\0"
    "revertButtonColorSignal\0viewUpdateRound\0"
    "redButtonPressed\0blueButtonPressed\0"
    "startNewRound\0incrementPlayerInputCounterSlot\0"
    "incrementComputerComboPositionSlot\0"
    "calculateProgressBarSlot\0endOfSequenceSlot\0"
    "displayComputerComboSlot\0updateRoundSlot"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_model[] = {

 // content:
      10,       // revision
       0,       // classname
       0,    0, // classinfo
      18,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       9,       // signalCount

 // signals: name, argc, parameters, tag, flags, initial metatype offsets
       1,    0,  122,    2, 0x06,    1 /* Public */,
       3,    0,  123,    2, 0x06,    2 /* Public */,
       4,    1,  124,    2, 0x06,    3 /* Public */,
       5,    0,  127,    2, 0x06,    5 /* Public */,
       6,    0,  128,    2, 0x06,    6 /* Public */,
       7,    0,  129,    2, 0x06,    7 /* Public */,
       8,    0,  130,    2, 0x06,    8 /* Public */,
       9,    1,  131,    2, 0x06,    9 /* Public */,
      10,    1,  134,    2, 0x06,   11 /* Public */,

 // slots: name, argc, parameters, tag, flags, initial metatype offsets
      11,    0,  137,    2, 0x0a,   13 /* Public */,
      12,    0,  138,    2, 0x0a,   14 /* Public */,
      13,    0,  139,    2, 0x0a,   15 /* Public */,
      14,    0,  140,    2, 0x0a,   16 /* Public */,
      15,    0,  141,    2, 0x0a,   17 /* Public */,
      16,    0,  142,    2, 0x0a,   18 /* Public */,
      17,    0,  143,    2, 0x0a,   19 /* Public */,
      18,    0,  144,    2, 0x0a,   20 /* Public */,
      19,    0,  145,    2, 0x0a,   21 /* Public */,

 // signals: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::Double,    2,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::Double,    2,
    QMetaType::Void, QMetaType::QString,    2,

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

       0        // eod
};

void model::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        auto *_t = static_cast<model *>(_o);
        (void)_t;
        switch (_id) {
        case 0: _t->updateViewCorrectButtonClicked(); break;
        case 1: _t->updateViewIncorrectButtonClicked(); break;
        case 2: _t->updateProgressBar((*reinterpret_cast< double(*)>(_a[1]))); break;
        case 3: _t->showWin(); break;
        case 4: _t->resumePlayerTurnSignal(); break;
        case 5: _t->redButtonLighterColorSignal(); break;
        case 6: _t->blueButtonLighterColorSignal(); break;
        case 7: _t->revertButtonColorSignal((*reinterpret_cast< double(*)>(_a[1]))); break;
        case 8: _t->viewUpdateRound((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 9: _t->redButtonPressed(); break;
        case 10: _t->blueButtonPressed(); break;
        case 11: _t->startNewRound(); break;
        case 12: _t->incrementPlayerInputCounterSlot(); break;
        case 13: _t->incrementComputerComboPositionSlot(); break;
        case 14: _t->calculateProgressBarSlot(); break;
        case 15: _t->endOfSequenceSlot(); break;
        case 16: _t->displayComputerComboSlot(); break;
        case 17: _t->updateRoundSlot(); break;
        default: ;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        {
            using _t = void (model::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::updateViewCorrectButtonClicked)) {
                *result = 0;
                return;
            }
        }
        {
            using _t = void (model::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::updateViewIncorrectButtonClicked)) {
                *result = 1;
                return;
            }
        }
        {
            using _t = void (model::*)(double );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::updateProgressBar)) {
                *result = 2;
                return;
            }
        }
        {
            using _t = void (model::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::showWin)) {
                *result = 3;
                return;
            }
        }
        {
            using _t = void (model::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::resumePlayerTurnSignal)) {
                *result = 4;
                return;
            }
        }
        {
            using _t = void (model::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::redButtonLighterColorSignal)) {
                *result = 5;
                return;
            }
        }
        {
            using _t = void (model::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::blueButtonLighterColorSignal)) {
                *result = 6;
                return;
            }
        }
        {
            using _t = void (model::*)(double );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::revertButtonColorSignal)) {
                *result = 7;
                return;
            }
        }
        {
            using _t = void (model::*)(QString );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&model::viewUpdateRound)) {
                *result = 8;
                return;
            }
        }
    }
}

const QMetaObject model::staticMetaObject = { {
    QMetaObject::SuperData::link<QObject::staticMetaObject>(),
    qt_meta_stringdata_model.offsetsAndSize,
    qt_meta_data_model,
    qt_static_metacall,
    nullptr,
qt_incomplete_metaTypeArray<qt_meta_stringdata_model_t
, QtPrivate::TypeAndForceComplete<model, std::true_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<double, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<double, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<QString, std::false_type>
, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>, QtPrivate::TypeAndForceComplete<void, std::false_type>


>,
    nullptr
} };


const QMetaObject *model::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *model::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_model.stringdata0))
        return static_cast<void*>(this);
    return QObject::qt_metacast(_clname);
}

int model::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 18)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 18;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 18)
            *reinterpret_cast<QMetaType *>(_a[0]) = QMetaType();
        _id -= 18;
    }
    return _id;
}

// SIGNAL 0
void model::updateViewCorrectButtonClicked()
{
    QMetaObject::activate(this, &staticMetaObject, 0, nullptr);
}

// SIGNAL 1
void model::updateViewIncorrectButtonClicked()
{
    QMetaObject::activate(this, &staticMetaObject, 1, nullptr);
}

// SIGNAL 2
void model::updateProgressBar(double _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(std::addressof(_t1))) };
    QMetaObject::activate(this, &staticMetaObject, 2, _a);
}

// SIGNAL 3
void model::showWin()
{
    QMetaObject::activate(this, &staticMetaObject, 3, nullptr);
}

// SIGNAL 4
void model::resumePlayerTurnSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 4, nullptr);
}

// SIGNAL 5
void model::redButtonLighterColorSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 5, nullptr);
}

// SIGNAL 6
void model::blueButtonLighterColorSignal()
{
    QMetaObject::activate(this, &staticMetaObject, 6, nullptr);
}

// SIGNAL 7
void model::revertButtonColorSignal(double _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(std::addressof(_t1))) };
    QMetaObject::activate(this, &staticMetaObject, 7, _a);
}

// SIGNAL 8
void model::viewUpdateRound(QString _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(std::addressof(_t1))) };
    QMetaObject::activate(this, &staticMetaObject, 8, _a);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
