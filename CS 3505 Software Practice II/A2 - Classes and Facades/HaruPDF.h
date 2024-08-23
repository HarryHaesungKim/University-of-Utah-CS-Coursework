/**
 * CS3505 A2: Header file for HaruPDF.cpp.
 * @file HaruPDF.h
 * @author Harry Kim
 * @version 1.0 9/13/21
 */

#ifndef HARUPDF
#define HARUPDF

#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include "hpdf.h"

class HaruPDF
{
private:
    HPDF_Doc pdf;
    HPDF_Page page;
    char fname[256];
    HPDF_Font font;
    const char *inputSentence;

public:
    void setup(char **argv);
    void placeText(float x, float y, float rad1, int currentCharacter);
    void savePDF();
};
#endif