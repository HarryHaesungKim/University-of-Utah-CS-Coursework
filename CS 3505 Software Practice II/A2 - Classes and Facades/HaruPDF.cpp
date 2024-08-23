/**
 * CS3505 A2: Sets up a document, places text on page, and saves pdf.
 * @file HaruPDF.cpp
 * @author Harry Kim
 * @version 1.0 9/13/21
 */

#include "HaruPDF.h"

/**
 * This method sets up the pdf to have characters drawn on it.
 * @param argv An array of arguments passed in through a command line.
 */
void HaruPDF::setup(char **argv)
{
    // argv are the command line arguments
    // argv[0] is the name of the executable program
    // This makes an output pdf named after the program's name
    strcpy(fname, argv[0]);
    strcat(fname, ".pdf");

    // Getting the text input from the command line.
    inputSentence = argv[1];

    pdf = HPDF_New(NULL, NULL);

    // Add a new page object.
    page = HPDF_AddPage(pdf);
    HPDF_Page_SetSize(page, HPDF_PAGE_SIZE_A5, HPDF_PAGE_PORTRAIT);

    HPDF_Page_SetTextLeading(page, 20);
    HPDF_Page_SetGrayStroke(page, 0);

    HPDF_Page_BeginText(page);
    font = HPDF_GetFont(pdf, "Courier-Bold", NULL);
    HPDF_Page_SetFontAndSize(page, font, 30);
}

/**
 * This method places the text on the pdf at specific spots at specific angles.
 * Does not contain spiral code. Only places passed in coordinates and angle of letter.
 * 
 * @param x The x coordinate of the letter to be placed.
 * @param y The y coordinate of the letter to be placed.
 * @param rad The angle of the letter to be placed.
 * @param currentCharacter An incrementing value to parse through the text input.
 */
void HaruPDF::placeText(float x, float y, float rad, int currentCharacter)
{
    char buf[2];
    // This ugly function defines where any following text will be placed
    // on the page. The cos/sin stuff is actually defining a 2D rotation
    // matrix.
    HPDF_Page_SetTextMatrix(page, cos(rad), sin(rad), -sin(rad), cos(rad), x, y);

    // C-style strings are null-terminated. The last character must a 0.
    buf[0] = inputSentence[currentCharacter]; // The character to display
    buf[1] = 0;
    HPDF_Page_ShowText(page, buf);
}

/**
 * This method saves the pdf to a file.
 */
void HaruPDF::savePDF()
{
    HPDF_Page_EndText(page);

    // Save the document to a file.
    HPDF_SaveToFile(pdf, fname);

    // Clean up.
    HPDF_Free(pdf);
}