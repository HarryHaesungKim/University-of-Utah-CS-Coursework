// Written by Harry Kim (using inherited methods from AbstractSpreadsheet.cs written by Joe Zachary) for CS 3500, September 2021.
// Version 1.0 - Harry Kim
//               (Filled methods with unique code.)

using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace SS
{

    /// <summary>
    /// A Spreadsheet object represents the state of a simple spreadsheet.  A 
    /// spreadsheet consists of an infinite number of named cells.
    /// 
    /// A string is a valid cell name if and only if:
    ///   (1) its first character is an underscore or a letter
    ///   (2) its remaining characters (if any) are underscores and/or letters and/or digits
    /// Note that this is the same as the definition of valid variable from the PS3 Formula class.
    /// 
    /// For example, "x", "_", "x2", "y_15", and "___" are all valid cell  names, but
    /// "25", "2x", and "&" are not.  Cell names are case sensitive, so "x" and "X" are
    /// different cell names.
    /// 
    /// A spreadsheet contains a cell corresponding to every possible cell name.  (This
    /// means that a spreadsheet contains an infinite number of cells.)  In addition to 
    /// a name, each cell has a contents and a value.  The distinction is important.
    /// 
    /// The contents of a cell can be (1) a string, (2) a double, or (3) a Formula.  If the
    /// contents is an empty string, we say that the cell is empty.  (By analogy, the contents
    /// of a cell in Excel is what is displayed on the editing line when the cell is selected.)
    /// 
    /// In a new spreadsheet, the contents of every cell is the empty string.
    ///  
    /// The value of a cell can be (1) a string, (2) a double, or (3) a FormulaError.  
    /// (By analogy, the value of an Excel cell is what is displayed in that cell's position
    /// in the grid.)
    /// 
    /// If a cell's contents is a string, its value is that string.
    /// 
    /// If a cell's contents is a double, its value is that double.
    /// 
    /// If a cell's contents is a Formula, its value is either a double or a FormulaError,
    /// as reported by the Evaluate method of the Formula class.  The value of a Formula,
    /// of course, can depend on the values of variables.  The value of a variable is the 
    /// value of the spreadsheet cell it names (if that cell's value is a double) or 
    /// is undefined (otherwise).
    /// 
    /// Spreadsheets are never allowed to contain a combination of Formulas that establish
    /// a circular dependency.  A circular dependency exists when a cell depends on itself.
    /// For example, suppose that A1 contains B1*2, B1 contains C1*2, and C1 contains A1*2.
    /// A1 depends on B1, which depends on C1, which depends on A1.  That's a circular
    /// dependency.
    /// </summary>
    public class Spreadsheet : AbstractSpreadsheet
    {

        // A private instance variable which holds the name of the cell object and the cell object itself.
        private Dictionary<string, Cell> cellDictionary;

        // A graph that links the dependency of each cell.
        private DependencyGraph cellDependency;

        public Spreadsheet()
        {
            cellDependency = new DependencyGraph();
            cellDictionary = new Dictionary<string, Cell>();
        }

        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the contents (as opposed to the value) of the named cell.  The return
        /// value should be either a string, a double, or a Formula.
        /// </summary>
        public override object GetCellContents(string name)
        {
            if (name is null || !Regex.IsMatch(name, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                throw new InvalidNameException();

            return cellDictionary[name].GetContents();
        }

        /// <summary>
        /// Enumerates the names of all the non-empty cells in the spreadsheet.
        /// </summary>
        public override IEnumerable<string> GetNamesOfAllNonemptyCells()
        {
            HashSet<string> namesOfNonEmptyCells = new HashSet<string>();
            foreach (KeyValuePair<string, Cell> entry in cellDictionary)
            {
                namesOfNonEmptyCells.Add(entry.Key);
            }
            return namesOfNonEmptyCells;
        }

        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, the contents of the named cell becomes number.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        public override IList<string> SetCellContents(string name, double number)
        {
            // Invalid name input.
            if (name is null || !Regex.IsMatch(name, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                throw new InvalidNameException();

            // Updating an existing cell's contents with number.
            if (cellDictionary.ContainsKey(name))
            {
                cellDictionary[name].SetContents(number);
            }

            // Creating a new cell with number as its contents.
            else
            {
                cellDictionary.Add(name, new Cell());
                cellDictionary[name].SetContents(number);
            }

            // Cells need to be recalculated.
            GetCellsToRecalculate(name);

            // Deleting all dependencies of cell.
            IEnumerable<string> valuesToBeDeleted = cellDependency.GetDependents(name);
            foreach (string value in valuesToBeDeleted)
            {
                cellDependency.RemoveDependency(name, value);
            }

            // Creates a new list of dependees of name to be returned;
            return GetDirectDependents(name).ToList();
        }

        /// <summary>
        /// If text is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, the contents of the named cell becomes text.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        public override IList<string> SetCellContents(string name, string text)
        {
            // Invalid name input.
            if (name is null || !Regex.IsMatch(name, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                throw new InvalidNameException();

            // No empty string should exist in dictionary.
            if (text.Equals(""))
            {
                if (cellDictionary.ContainsKey(name))
                    cellDictionary.Remove(name);
                return null;
            }

            // Updating an existing cell's contents with text.
            else if (cellDictionary.ContainsKey(name))
            {
                cellDictionary[name].SetContents(text);
            }

            // Creating a new cell with text as its contents.
            else
            {
                cellDictionary.Add(name, new Cell());
                cellDictionary[name].SetContents(text);
            }

            // Cells need to be recalculated.
            GetCellsToRecalculate(name);

            // Deleting all dependencies of cell.
            IEnumerable<string> valuesToBeDeleted = cellDependency.GetDependents(name);
            foreach(string value in valuesToBeDeleted)
            {
                cellDependency.RemoveDependency(name, value);
            }

            // Creates a new list of dependees of name to be returned;
            return GetDirectDependents(name).ToList();
        }

        /// <summary>
        /// If the formula parameter is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, if changing the contents of the named cell to be the formula would cause a 
        /// circular dependency, throws a CircularException, and no change is made to the spreadsheet.
        /// 
        /// Otherwise, the contents of the named cell becomes formula.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends,
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        public override IList<string> SetCellContents(string name, Formula formula)
        {
            // Copy of contents incase of circular exception.
            object copy;
            List<string> badVariableCopies = new List<string>();

            // Invalid name input.
            if (name is null || !Regex.IsMatch(name, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                throw new InvalidNameException();

            // Invalid name input.
            if (formula is null)
                throw new ArgumentNullException();

            // Deleting all dependencies of cell.
            IEnumerable<string> valuesToBeDeleted = cellDependency.GetDependents(name);
            foreach (string value in valuesToBeDeleted)
            {
                cellDependency.RemoveDependency(name, value);
            }

            // Updating an existing cell's contents with formula.
            if (cellDictionary.ContainsKey(name))
            {
                if(cellDictionary[name].GetContents() is Formula)
                {
                    foreach(string variable in formula.GetVariables())
                    {
                        badVariableCopies.Add(variable);
                    }
                }
                copy = cellDictionary[name].GetContents();
                cellDictionary[name].SetContents(formula);
            }

            // Creating a new cell with formula as its contents.
            else
            {
                copy = "";
                cellDictionary.Add(name, new Cell());
                cellDictionary[name].SetContents(formula);
            }

            // Adding dependencies of input.
            foreach (string variable in formula.GetVariables())
            {
                cellDependency.AddDependency(name, variable);
            }

            try
            {
                // Cells need to be recalculated.
                GetCellsToRecalculate(name);
            }
            catch (CircularException c)
            {
                // Removing dependencies of bad variables that caused circular exception.
                foreach (string variables in badVariableCopies)
                {
                    cellDependency.RemoveDependency(name, variables);
                }
                // No change incase of circular exception.
                if (copy is Formula)
                {
                    // Adding dependencies of input.
                    foreach (string variable in ((Formula)copy).GetVariables())
                    {
                        cellDependency.AddDependency(name, variable);
                    }
                }


                cellDictionary[name].SetContents(copy);
            }

            // Creates a new list of dependees of name to be returned;
            return GetDirectDependents(name).ToList();
        }

        /// <summary>
        /// Returns an enumeration, without duplicates, of the names of all cells whose
        /// values depend directly on the value of the named cell.  In other words, returns
        /// an enumeration, without duplicates, of the names of all cells that contain
        /// formulas containing name.
        /// 
        /// For example, suppose that
        /// A1 contains 3
        /// B1 contains the formula A1 * A1
        /// C1 contains the formula B1 + A1
        /// D1 contains the formula B1 - C1
        /// The direct dependents of A1 are B1 and C1
        /// </summary>
        protected override IEnumerable<string> GetDirectDependents(string name)
        {
            // Creates a new list of dependees of name to be returned;
            IList<string> returnList = new List<string>();
            foreach (string dependee in cellDependency.GetDependees(name))
            {
                returnList.Add(dependee);
            }
            return returnList;
        }
    }

    /// <summary>
    /// This cell class represents a cell within a simple spreadsheet.
    /// </summary>
    public class Cell
    {
        // Contents which the cell object holds.
        private object contents;

        /// <summary>
        /// Constructor which creates a cell object. Automatically sets contents to an empty string.
        /// </summary>
        public Cell()
        {
            contents = "";
        }

        /// <summary>
        /// Gets the contents of the cell.
        /// </summary>
        /// <returns> The desired content of the cell. </returns>
        public object GetContents()
        {
            return contents;
        }

        /// <summary>
        /// Sets the contents of the cell.
        /// </summary>
        /// <param name="content"> Desired content to be set. </param>
        /// <returns></returns>
        public bool SetContents(object content)
        {
            contents = content;
            return true;
        }

        /// <summary>
        /// Evaluates the given formula.
        /// </summary>
        /// <param name="lookup"> Delegate to be passed into the Evaluate method of Formula. </param>
        /// <returns> The completed operation of the given formula. </returns>
        public object Evaluate(Func<string, double> lookup)
        {
            return ((Formula)contents).Evaluate(lookup);
        }
    }
}