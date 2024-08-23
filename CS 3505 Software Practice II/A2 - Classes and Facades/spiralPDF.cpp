/**
 * CS3505 A2: Runs spiral code and makes a pdf with text as spiral.
 * @file spiralPDF.cpp
 * @author Harry Kim
 * @version 1.0 9/13/21
 */

#include <iostream>
#include "HaruPDF.h"
#include "Spiral.h"

int main(int argc, char **argv)
{
    // If there's not enough arguments, program doesn't even try to run.
    if (argc < 2)
    {
        std::cout << "Must input a sentence." << std::endl;
        return 0;
    }

    else
    {
        // Creating HaruPDF and Spiral objects.
        HaruPDF h = HaruPDF();
        Spiral s(210, 300, -90, 90);
        h.setup(argv);
        // Placing each character from text input into spiral.
        for (unsigned int i = 0; i < strlen(argv[1]); i++)
        {
            h.placeText(s.getTextX(), s.getTextY(), s.getRad1(), i);
            s++;
        }

        h.savePDF();

        return 0;
    }
}