// Written by Harry Kim & Braden Morfin for CS 3500, March 2021.
// Version 1.0 - Harry Kim & Braden Morfin
//               (Filled methods with unique code.)

using SS;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpreadsheetUtilities;
using System.Text.RegularExpressions;
using System.Diagnostics;
using System.IO;

namespace SpreadsheetGUI
{
    public partial class Form1 : Form
    {
        /// <summary>
        /// A helper method that makes sure that the name of the cell is valid.
        /// </summary>
        /// <param name="cellName">String to be checked.</param>
        /// <returns>True if cell name is valid. False otherwise.</returns>
        private bool IsValid(string cellName)
        {
            return Regex.IsMatch(cellName, @"^[A-Z](\d?[1-9]|[1-9]0)$");
        }

        // Creating a spreadsheet object.
        private Spreadsheet SP;

        // This object initializes the spreadsheet and GUI.
        public Form1()
        {
            InitializeComponent();
            SP = new Spreadsheet(IsValid, s => Char.ToUpper(s[0]) + s.Substring(1), "ps6");
            AcceptButton = enterFunctionButton;
            spreadsheetPanelWindow.SelectionChanged += OnSelectionChanged;

            // Cell A1 initialized at start and disabled cellNameDisplayBox so that users cannot interact with it.
            cellNameDisplayBox.Enabled = false;
            cellValueDisplayBox.Enabled = false;
            cellNameDisplayBox.Text = "A1";
            
        }

        /// <summary>
        /// Converts a given int into a capital letter.
        /// </summary>
        /// <param name="num">The given int parameter.</param>
        /// <returns>A capital letter.</returns>
        private string NumToLet(int num)
        {
            const string letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            return letters[num].ToString();
        }

        /// <summary>
        /// Converts a given cell name into coordinates.
        /// </summary>
        /// <param name="cellName">String representation of the cell.</param>
        /// <returns>Tuple containing the column and row of the cell.</returns>
        private Tuple<int, int> ConvertCellNameToCoordinates(string cellName)
        {
            int col = cellName[0] - 65;
            int row = Int32.Parse(cellName.Substring(1)) - 1;
            return new Tuple<int, int>(col, row);
        }

        /// <summary>
        /// This method is envoked whenever the selection of a cell is changed.
        /// </summary>
        /// <param name="ssp">SpreadsheetPanel object.</param>
        private void OnSelectionChanged(SpreadsheetPanel ssp)
        {
            FunctionInputBox.Clear();
            FunctionInputBox.Focus();

            // Updates cellNameDisplayBox to the correct name as selection changes.
            spreadsheetPanelWindow.GetSelection(out int col, out int row);
            cellNameDisplayBox.Text = "" + (NumToLet(col)) + (row + 1);
            if(SP.GetCellValue(cellNameDisplayBox.Text) is FormulaError)
                cellValueDisplayBox.Text = "ERROR";
            else
                cellValueDisplayBox.Text = SP.GetCellValue(cellNameDisplayBox.Text).ToString();
            if (SP.GetCellContents(cellNameDisplayBox.Text) is Formula)
                FunctionInputBox.Text = "=" + SP.GetCellContents(cellNameDisplayBox.Text).ToString();
            else
                FunctionInputBox.Text = SP.GetCellContents(cellNameDisplayBox.Text).ToString();
            FunctionInputBox.SelectionStart = FunctionInputBox.Text.Length;
        }

        /// <summary>
        /// This method is envoked whenever the "Enter" button on the GUI is pressed.
        /// </summary>
        /// <param name="sender">A reference to the control/object that raised the event.</param>
        /// <param name="e">Event data.</param>
        private void enterFunctionButton_Click(object sender, EventArgs e)
        {
            // Disabling the enter button to ensure no race conditions.
            enterFunctionButton.Enabled = false;
            backgroundWorker1.RunWorkerAsync();
        }

        /// <summary>
        /// Does work on a separate thread to save computation time.
        /// </summary>
        /// <param name="sender">A reference to the control/object that raised the event.</param>
        /// <param name="e">Event data.</param>
        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            IList<string> cellsToUpdate = new List<string>();
            try
            {
                if(FunctionInputBox.Text == "")
                    return;
                // When the selected cell is changed successfully, the displayed value of each dependent cell in the spreadsheet is updated automatically.
                Invoke(new MethodInvoker(() => cellsToUpdate = SP.SetContentsOfCell(cellNameDisplayBox.Text, FunctionInputBox.Text)));
            }
            catch (Exception ex)
            {
                // Error messages for different types of errors.
                if (ex is FormulaFormatException)
                {
                    MessageBox.Show("You have entered an invalid variable.", "Invalid Variable", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else if (ex is SpreadsheetReadWriteException)
                {
                    MessageBox.Show("Circular dependency detected.", "Circular Exception", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            Invoke(new MethodInvoker(() => UpdateCells(cellsToUpdate)));
        }

        /// <summary>
        /// This method updates all of the given cells on the SpreadsheetPanel.
        /// </summary>
        /// <param name="dependentCells">A list of cells to be updated.</param>
        private void UpdateCells(IList<string> dependentCells)
        {
            foreach (string cell in dependentCells)
            {
                Tuple<int, int> coordinates = ConvertCellNameToCoordinates(cell);
                if (SP.GetCellValue(cell) is FormulaError)
                    spreadsheetPanelWindow.SetValue(coordinates.Item1, coordinates.Item2, "ERROR");
                else
                    spreadsheetPanelWindow.SetValue(coordinates.Item1, coordinates.Item2, SP.GetCellValue(cell).ToString());
            }
        }

        /// <summary>
        /// This method is envoked when the background worker is completed.
        /// </summary>
        /// <param name="sender">A reference to the control/object that raised the event.</param>
        /// <param name="e">Event data.</param>
        private void backgroundWorker1_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            if (SP.GetCellValue(cellNameDisplayBox.Text) is FormulaError)
                cellValueDisplayBox.Text = "ERROR";
            else
                cellValueDisplayBox.Text = "" + SP.GetCellValue(cellNameDisplayBox.Text);
            // Reenabling the enter button for users (to avoid race conditions).
            enterFunctionButton.Enabled = true;
        }

        /// <summary>
        /// Creates a new spreadsheet in a new window.
        /// </summary>
        /// <param name="sender">A reference to the control/object that raised the event.</param>
        /// <param name="e">Event data.</param>
        private void newToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DemoApplicationContext.getAppContext().RunForm(new Form1());
        }

        /// <summary>
        /// Saves the current spreadsheet to a selected path.
        /// </summary>
        /// <param name="sender">A reference to the control/object that raised the event.</param>
        /// <param name="e">Event data.</param>
        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            // Creating the SaveFileDialog and sets the extention as ".sprd".
            SaveFileDialog saveFileDialog1 = new SaveFileDialog();
            saveFileDialog1.Filter = "sprd files (*.sprd)|*.sprd|All files (*.*)|*.*";
            saveFileDialog1.FilterIndex = 1;
            saveFileDialog1.RestoreDirectory = true;
            saveFileDialog1.ShowDialog();
            string path = saveFileDialog1.FileName;

            // Do nothing if no path is chosen.
            if (path.Equals(""))
                return;
            SP.Save(Path.GetFullPath(path));
        }

        /// <summary>
        /// Opens an existing spreadsheet on the machine.
        /// </summary>
        /// <param name="sender">A reference to the control/object that raised the event.</param>
        /// <param name="e">Event data.</param>
        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {

            // Gives a warning if there is unsaved data.
            if (SP.Changed)
            {
                var warning = MessageBox.Show("You are about to open another spreadsheet with saving the current one. Do you want to proceed?", "Unsaved Data", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                if (warning == DialogResult.No)
                    return;
            }

            // Creating the OpenFileDialog to find a specified filepath.
            string filePath = "";
            string fileContent = "";
            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.InitialDirectory = "c:\\";
                openFileDialog.Filter = "sprd files (*.sprd)|*.sprd|All files (*.*)|*.*";
                openFileDialog.FilterIndex = 1;
                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {

                    //Get the path of specified file
                    filePath = openFileDialog.FileName;

                    //Read the contents of the file into a stream
                    var fileStream = openFileDialog.OpenFile();
                    using (StreamReader reader = new StreamReader(fileStream))
                    {
                        fileContent = reader.ReadToEnd();
                    }
                }
            }

            // Do nothing if no path is chosen.
            if (filePath.Equals(""))
                return;

            // Replaces the current spreadsheet with a new one that uses the constructor that takes a file.
            try
            {
                SP = new Spreadsheet(filePath, IsValid, s => Char.ToUpper(s[0]) + s.Substring(1), SP.GetSavedVersion(filePath));
            }
            catch(SpreadsheetReadWriteException)
            {
                MessageBox.Show("The filepath you have chosen is invalid.", "Invalid Filepath", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            this.UpdateCells(SP.GetNamesOfAllNonemptyCells().ToList());
        }

        /// <summary>
        /// This method closes the spreadsheet window when the close button is clicked.
        /// </summary>
        /// <param name="sender">A reference to the control/object that raised the event.</param>
        /// <param name="e">Event data.</param>
        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Close();
        }

        /// <summary>
        /// This method is envoked when the form is closing.
        /// </summary>
        /// <param name="e">Event data.</param>
        protected override void OnFormClosing(FormClosingEventArgs e)
        {

            // Checks for unsaved data.
            if (SP.Changed)
            {
                var warning = MessageBox.Show("You are about to close this spreadsheet with unsaved data. Do you want to proceed?", "Unsaved Data", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                if (warning == DialogResult.No)
                    e.Cancel = true;    
            }
        }
        
        /// <summary>
        /// This method overrides the ProcessCmdKey to allow the user to select different cells using the arrow keys.
        /// </summary>
        /// <param name="msg">Implements a Windows message.</param>
        /// <param name="keyData">The key pressed.</param>
        /// <returns></returns>
        protected override bool ProcessCmdKey(ref Message msg, Keys keyData)
        {
            spreadsheetPanelWindow.GetSelection(out int col, out int row);

            //capture up arrow key
            if (keyData == Keys.Up && row > 0)
            {
                SelectionDirection(col, row);
                spreadsheetPanelWindow.SetSelection(col, row-1);
                return true;
            }
            //capture down arrow key
            if (keyData == Keys.Down && row < 98)
            {
                SelectionDirection(col, row+2);
                spreadsheetPanelWindow.SetSelection(col, row+1);
                return true;
            }
            //capture left arrow key
            if (keyData == Keys.Left && col > 0)
            {
                SelectionDirection(col-1, row+1);
                spreadsheetPanelWindow.SetSelection(col-1, row);
                return true;
            }
            //capture right arrow key
            if (keyData == Keys.Right && col < 25)
            {
                SelectionDirection(col+1, row+1);
                spreadsheetPanelWindow.SetSelection(col+1, row);
                return true;
            }
            return base.ProcessCmdKey(ref msg, keyData);
        }

        /// <summary>
        /// Selects the given cell based on the input column and row.
        /// </summary>
        /// <param name="col">The input column integer.</param>
        /// <param name="row">The input row integer.</param>
        private void SelectionDirection(int col, int row)
        {
            FunctionInputBox.Clear();
            FunctionInputBox.Focus();

            // Updates cellNameDisplayBox to the correct name as selection changes.
            cellNameDisplayBox.Text = "" + (NumToLet(col)) + (row);
            cellValueDisplayBox.Text = SP.GetCellValue(cellNameDisplayBox.Text).ToString();
            if (SP.GetCellContents(cellNameDisplayBox.Text) is Formula)
                FunctionInputBox.Text = "=" + SP.GetCellContents(cellNameDisplayBox.Text).ToString();
            else
                FunctionInputBox.Text = SP.GetCellContents(cellNameDisplayBox.Text).ToString();
            FunctionInputBox.SelectionStart = FunctionInputBox.Text.Length;
        }

        private void clearButton_Click(object sender, EventArgs e)
        {
            foreach(string cell in SP.GetNamesOfAllNonemptyCells())
            {
                spreadsheetPanelWindow.SetValue(ConvertCellNameToCoordinates(cell).Item1, ConvertCellNameToCoordinates(cell).Item2, "");
            }
            SP = new Spreadsheet(IsValid, s => Char.ToUpper(s[0]) + s.Substring(1), "ps6");

            //UpdateCells(SP.GetNamesOfAllNonemptyCells().ToList());
            cellValueDisplayBox.Clear();
            FunctionInputBox.Clear();
        }

        private void controlsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("You can select a cell by using the mouse and clicking on it. Alternatively, the arrow keys can be used to navigate between cells. \n" + "\n" +
                "After typing in a cell's contents in the only enabled textbox, you can click the 'Enter' button on the spreadsheet or the 'Enter' key on your keyboard to enter whatever you want. \n" + "\n" +
                "To clear the spreadsheet, you may click the 'Clear' button on the spreadsheet to reset the spreadsheet.", "Controls", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void enteringFunctionsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("To enter a function, you must select a cell in the editable textbox. A function must start with the '=' symbol in order to denote that the input is a function. \n" + "\n" +
                "Functions can only include numbers and the name of cells. \n" + "\n" +
                "For example, adding two numbers would look like: '=3+3' or '= 3 + 3'. \n" + "\n" +
                "Adding two cell's (provided the cells contain numbers or a function) would look like: '=A1+B2' or '=a1 + b2'. \n" + "\n" +
                "An invalid function example would look like: '=hotdog' or '=A1+A1' which would result in errors (see errors tab for more info). \n" + "\n" +
                "(Notice that spaces of case of letters do not matter when entering functions.)", "Entering Functions", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void newButtonToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("The 'New' button under the 'File' tab creates a new, independent, and empty spreadsheet window.","'New' Button Under File", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void saveButtonToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("The 'Save' button under the 'File' tab allows the user to save the current spreadsheet to specified location on the machine. \n" + "\n" +
                "By default, the spreadsheet is saved as an '.sprd' file but allows the user to specify what to save it as.", "'Save' Button Under File", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void openButtonToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("The 'Open' button under the 'File' tab allows users to open '.sprd' type files on their machine which then overrites the spreadsheat that is currently open. \n" + "\n" +
                "If the current spreadsheet is not saved and the user tries to open another spreadsheet, a warning will be presented which will inform the user of possible loss of data.", "'Open' Button Under File", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void closeButtonToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("The 'Close' button under the 'File' tab allows users simply close the current spreadsheet window. \n" + "\n" +
                "If the current spreadsheet is not saved and the user tries to close the current spreadsheet, a warning will be presented which will inform the user of possible loss of data.", "'Close' Button Under File", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void errorsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("There are a number of inputs a user can enter that will result in an error. \n" + "\n" +
                "If a function contains a variable that is undefined or contains a divide by zero argument, an error will be displayed as that cell's value until the variable is updated with a valid input. \n" + "\n" +
                "Any function that results in a circular dependency will prompt a warning window and that cell's contents and value will be unchanged. \n" + "\n" +
                "If an invalid formula is entered (invalid varialbe name or invalid infix notation is used), the spreadsheet will prompt a warning window and that cell's contents and value will be unchanged.", "Errors", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }
    }
}
