// Written by Harry Kim (using inherited methods from AbstractSpreadsheet.cs written by Joe Zachary) for CS 3500, September 2021.
// Version 1.0 - Harry Kim
//               (Filled methods with unique code.)
// Version 2.0 - Harry Kim
//               (Filled methods with unique code for PS5.)

using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml;

namespace SS
{

    /// <summary>
    /// A Spreadsheet object represents the state of a simple spreadsheet.  A 
    /// spreadsheet consists of an infinite number of named cells.
    /// 
    /// A string is a cell name if and only if it consists of one or more letters,
    /// followed by one or more digits AND it satisfies the predicate IsValid.
    /// For example, "A15", "a15", "XY032", and "BC7" are cell names so long as they
    /// satisfy IsValid.  On the other hand, "Z", "X_", and "hello" are not cell names,
    /// regardless of IsValid.
    /// 
    /// Any valid incoming cell name, whether passed as a parameter or embedded in a formula,
    /// must be normalized with the Normalize method before it is used by or saved in 
    /// this spreadsheet.  For example, if Normalize is s => s.ToUpper(), then
    /// the Formula "x3+a5" should be converted to "X3+A5" before use.
    /// 
    /// A spreadsheet contains a cell corresponding to every possible cell name.  
    /// In addition to a name, each cell has a contents and a value.  The distinction is
    /// important.
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

        // Validating and normalizing variables.
        private Func<string, bool> isValid;
        private Func<string, string> normalize;

        // Saving the version.
        private string version;
        private string filePath;


        /// <summary>
        /// Constructs a default spreadsheet where isValid is always true and the normalizer doesn't change anything.
        /// </summary>
        public Spreadsheet() : this(s => true, s => s, "default")
        {
        }

        /// <summary>
        /// Constructs a spreadsheet by recording its variable validity test,
        /// its normalization method, and its version information.  The variable validity
        /// test is used throughout to determine whether a string that consists of one or
        /// more letters followed by one or more digits is a valid cell name.  The variable
        /// equality test should be used thoughout to determine whether two variables are
        /// equal.
        /// </summary>
        public Spreadsheet(Func<string, bool> isValid, Func<string, string> normalize, string version) : base(isValid, normalize, version)
        {
            // save isValid and normalize variables.
            this.isValid = isValid;
            this.normalize = normalize;

            // version is for save.
            this.version = version;

            cellDependency = new DependencyGraph();
            cellDictionary = new Dictionary<string, Cell>();
        }

        /// <summary>
        /// Constructs a spreadsheet by recording its variable validity test,
        /// its normalization method, and its version information.  The variable validity
        /// test is used throughout to determine whether a string that consists of one or
        /// more letters followed by one or more digits is a valid cell name.  The variable
        /// equality test should be used thoughout to determine whether two variables are
        /// equal. The filepath should create the spreadsheet to a specified file.
        /// </summary>
        public Spreadsheet(string filePath, Func<string, bool> isValid, Func<string, string> normalize, string version) : base(isValid, normalize, version)
        {
            // save isValid and normalize variables.
            this.isValid = isValid;
            this.normalize = normalize;

            // version is for save.
            this.version = version;

            // filePath is for save.
            this.filePath = filePath;

            cellDependency = new DependencyGraph();
            cellDictionary = new Dictionary<string, Cell>();

            string savedCellName = "";
            string savedCellContents = "";

            // Checking to see if versions match.
            if (!version.Equals(GetSavedVersion(filePath))){
                throw new SpreadsheetReadWriteException("Versions don't match.");
            }

            try
            {
                using (XmlReader reader = XmlReader.Create(filePath))
                {
                    while (reader.Read())
                    {
                        if (reader.IsStartElement())
                        {
                            if (reader.Name.Equals("name"))
                            {
                                reader.Read();
                                savedCellName = reader.Value;
                            }
                            else if (reader.Name.Equals("contents"))
                            {
                                reader.Read();
                                savedCellContents = reader.Value;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        if(!savedCellName.Equals("") && !savedCellContents.Equals(""))
                            SetContentsOfCell(savedCellName, savedCellContents);
                    }
                }
                this.Changed = false;
            }
            catch (Exception)
            {
                throw new SpreadsheetReadWriteException("Invalid file path.");
            }
        }

        /// <summary>
        /// True if this spreadsheet has been modified since it was created or saved                  
        /// (whichever happened most recently); false otherwise.
        /// </summary>
        public override bool Changed { get; protected set; }

        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the contents (as opposed to the value) of the named cell.  The return
        /// value should be either a string, a double, or a Formula.
        /// </summary>
        public override object GetCellContents(string name)
        {
            // Normalizing name.
            name = Normalize(name);

            // Checking if name is valid.
            if (name is null || !Regex.IsMatch(name, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                throw new InvalidNameException();

            // Returns empty string for contents if cell does not exist.
            if (!cellDictionary.ContainsKey(name))
            {
                return "";
            }
            return cellDictionary[name].GetContents();
        }

        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the value (as opposed to the contents) of the named cell.  The return
        /// value should be either a string, a double, or a SpreadsheetUtilities.FormulaError.
        /// </summary>
        public override object GetCellValue(string name)
        {
            // Invalid name input.
            if (name is null || !Regex.IsMatch(name, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                throw new InvalidNameException();

            // Returns empty string for value if cell does not exist.
            if (!cellDictionary.ContainsKey(name))
            {
                return "";
            }
            return cellDictionary[name].GetValue();
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
        /// Returns the version information of the spreadsheet saved in the named file.
        /// If there are any problems opening, reading, or closing the file, the method
        /// should throw a SpreadsheetReadWriteException with an explanatory message.
        /// </summary>
        public override string GetSavedVersion(string filename)
        {
            string versionInfo = "";
            try
            {
                using (XmlReader reader = XmlReader.Create(filename))
                {
                    while (reader.Read())
                    {
                        if (reader.IsStartElement())
                        {
                            if (reader.Name.Equals("spreadsheet"))
                            {
                                versionInfo = reader["version"];
                            }
                        }
                    }
                }
            }
            catch (Exception)
            {
                throw new SpreadsheetReadWriteException("Invalid file path.");
            }
            return versionInfo;
        }

        /// <summary>
        /// Writes the contents of this spreadsheet to the named file using an XML format.
        /// The XML elements should be structured as follows:
        /// 
        /// <spreadsheet version="version information goes here">
        /// 
        /// <cell>
        /// <name>cell name goes here</name>
        /// <contents>cell contents goes here</contents>    
        /// </cell>
        /// 
        /// </spreadsheet>
        /// 
        /// There should be one cell element for each non-empty cell in the spreadsheet.  
        /// If the cell contains a string, it should be written as the contents.  
        /// If the cell contains a double d, d.ToString() should be written as the contents.  
        /// If the cell contains a Formula f, f.ToString() with "=" prepended should be written as the contents.
        /// 
        /// If there are any problems opening, writing, or closing the file, the method should throw a
        /// SpreadsheetReadWriteException with an explanatory message.
        /// </summary>
        public override void Save(string filename)
        {
            // Setting up Xml writers so that everything is not on one line.
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = "  ";
            try
            {
                using (XmlWriter writer = XmlWriter.Create(filename, settings))
                {
                    writer.WriteStartDocument();
                    writer.WriteStartElement("spreadsheet");
                    writer.WriteAttributeString("version", version);
                    // Looping through each cell in cellDictionary to write.
                    foreach (KeyValuePair<string, Cell> entry in cellDictionary)
                    {
                        writer.WriteStartElement("cell");
                        writer.WriteElementString("name", entry.Key);
                        if (entry.Value.GetContents() is Formula)
                        {
                            writer.WriteElementString("contents", "=" + entry.Value.GetContents().ToString());
                            //write element string
                        }
                        else
                        {
                            writer.WriteElementString("contents", entry.Value.GetContents().ToString());
                        }
                        writer.WriteEndElement();
                    }
                    writer.WriteEndElement();
                    writer.WriteEndDocument();

                    // Changes have been saved.
                    this.Changed = false;
                }
            }
            catch (Exception e)
            {
                throw new SpreadsheetReadWriteException(e.Message);
            }
        }

        /// <summary>
        /// The contents of the named cell becomes number.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<string> SetCellContents(string name, double number)
        {
            // First normalizing name.
            name = normalize(name);

            // Invalid name input.
            if (!isValid(name))
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
            List<string> dependentOnCell = GetCellsToRecalculate(name).ToList();

            // Deleting all dependencies of cell.
            List<string> valuesToBeDeleted = new List<string>();
            foreach (string value in cellDependency.GetDependents(name))
            {
                valuesToBeDeleted.Add(value);
            }
            foreach (string value in valuesToBeDeleted)
            {
                cellDependency.RemoveDependency(name, value);
            }

            return dependentOnCell;
        }

        /// <summary>
        /// The contents of the named cell becomes text.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<string> SetCellContents(string name, string text)
        {
            // First normalizing name.
            name = normalize(name);

            // Invalid name input.
            if (!isValid(name))
                throw new InvalidNameException();

            // No empty string should exist in dictionary.
            if (text.Equals(""))
            {
                if (cellDictionary.ContainsKey(name))
                    cellDictionary.Remove(name);
                return GetCellsToRecalculate(name).ToList();
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
            List<string> dependentOnCell = GetCellsToRecalculate(name).ToList();

            // Deleting all dependencies of cell.
            List<string> valuesToBeDeleted = new List<string>();
            foreach (string value in cellDependency.GetDependents(name))
            {
                valuesToBeDeleted.Add(value);
            }
            foreach (string value in valuesToBeDeleted)
            {
                cellDependency.RemoveDependency(name, value);
            }

            return dependentOnCell;
        }

        /// <summary>
        /// If changing the contents of the named cell to be the formula would cause a 
        /// circular dependency, throws a CircularException, and no change is made to the spreadsheet.
        /// 
        /// Otherwise, the contents of the named cell becomes formula. The method returns a
        /// list consisting of name plus the names of all other cells whose value depends,
        /// directly or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<string> SetCellContents(string name, Formula formula)
        {
            // Copy of contents and value incase of circular exception.
            object contentsCopy;
            object valueCopy;
            List<string> badVariableCopies = new List<string>();

            // First normalizing name.
            name = normalize(name);

            // Invalid name input.
            if (!isValid(name))
                throw new InvalidNameException();

            // Deleting all dependencies of cell.
            List<string> valuesToBeDeleted = new List<string>();
            foreach (string value in cellDependency.GetDependents(name))
            {
                valuesToBeDeleted.Add(value);
            }
            foreach (string value in valuesToBeDeleted)
            {
                cellDependency.RemoveDependency(name, value);
            }

            // Updating an existing cell's contents with formula.
            if (cellDictionary.ContainsKey(name))
            {
                valueCopy = cellDictionary[name].GetValue();
                contentsCopy = cellDictionary[name].GetContents();
                cellDictionary[name].SetContents(formula);
            }

            // Creating a new cell with formula as its contents.
            else
            {
                contentsCopy = "";
                valueCopy = "";
                cellDictionary.Add(name, new Cell());
                cellDictionary[name].SetContents(formula);
            }

            foreach (string variable in formula.GetVariables())
            {
                badVariableCopies.Add(variable);
            }

            // Adding dependencies of input.
            foreach (string variable in formula.GetVariables())
            {
                if (isValid(normalize(variable)))
                    cellDependency.AddDependency(name, normalize(variable));
                else
                    throw new SpreadsheetReadWriteException("Invalid variable.");
            }

            List<string> dependentOnCell = new List<string>();

            try
            {
                // Cells need to be recalculated.
                dependentOnCell = GetCellsToRecalculate(name).ToList();
            }
            catch (CircularException c)
            {
                // Removing dependencies of bad variables that caused circular exception.
                foreach (string variables in badVariableCopies)
                {
                    cellDependency.RemoveDependency(name, variables);
                }
                // No change incase of circular exception.
                if (contentsCopy.Equals(""))
                {
                    cellDictionary.Remove(name);
                }
                else if (contentsCopy is Formula)
                {
                    // Adding dependencies of input.
                    foreach (string variable in ((Formula)contentsCopy).GetVariables())
                    {
                        cellDependency.AddDependency(name, variable);
                    }
                    cellDictionary[name].SetContents(contentsCopy);
                }
                else
                    cellDictionary[name].SetContents(contentsCopy);
                throw new SpreadsheetReadWriteException("circular dependency encountered.");
            }

            return dependentOnCell;
        }

        /// <summary>
        /// Take in string and returns double. Input is cell name need to find value of cell, but only if value of cell is double. Argument exception otherwise.
        /// </summary>
        /// <param name="variableName">the provided name for the variable</param>
        /// <returns></returns>
        private double Lookup(string variableName)
        {
            return Convert.ToDouble(cellDictionary[variableName].GetValue());
        }

        /// <summary>
        /// If content is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, if content parses as a double, the contents of the named
        /// cell becomes that double.
        /// 
        /// Otherwise, if content begins with the character '=', an attempt is made
        /// to parse the remainder of content into a Formula f using the Formula
        /// constructor.  There are then three possibilities:
        /// 
        ///   (1) If the remainder of content cannot be parsed into a Formula, a 
        ///       SpreadsheetUtilities.FormulaFormatException is thrown.
        ///       
        ///   (2) Otherwise, if changing the contents of the named cell to be f
        ///       would cause a circular dependency, a CircularException is thrown,
        ///       and no change is made to the spreadsheet.
        ///       
        ///   (3) Otherwise, the contents of the named cell becomes f.
        /// 
        /// Otherwise, the contents of the named cell becomes content.
        /// 
        /// If an exception is not thrown, the method returns a list consisting of
        /// name plus the names of all other cells whose value depends, directly
        /// or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        public override IList<string> SetContentsOfCell(string name, string content)
        {
            // Spreadsheet is changed.
            this.Changed = true;
            // Invalid name input.
            if (name is null || !Regex.IsMatch(name, @"^[a-zA-Z_]{1}[a-zA-Z0-9_]*"))
                throw new InvalidNameException();

            // Invalid content input.
            if (content is null)
                throw new ArgumentNullException();

            // Setting cell contents.
            double i = 0;
            if (double.TryParse(content, out i))
            {
                IList<string> cellsToRecalculate = SetCellContents(name, Convert.ToDouble(content));
                foreach (string cell in cellsToRecalculate)
                {
                    if (cell.Equals(name))
                        cellDictionary[name].SetValue(Convert.ToDouble(content));
                    else
                    {
                        Formula cellFormula = new Formula(cellDictionary[cell].GetContents().ToString(), this.Normalize, this.IsValid);
                        cellDictionary[cell].SetValue(cellFormula.Evaluate(Lookup));
                    }
                }
                return cellsToRecalculate;
            }

            // If content is a formula or a string.
            else
            {
                if (content.Length != 0 && content[0].Equals('='))
                {
                    Formula cellFormula = new Formula(content.Substring(1), this.Normalize, this.IsValid);
                    IList<string> cellsToRecalculate = SetCellContents(name, cellFormula);
                    foreach (string cell in cellsToRecalculate)
                    {
                        if (cell.Equals(name))
                            cellDictionary[name].SetValue(cellFormula.Evaluate(Lookup));
                        else
                        {
                            cellFormula = new Formula(cellDictionary[cell].GetContents().ToString(), this.Normalize, this.IsValid);
                            cellDictionary[cell].SetValue(cellFormula.Evaluate(Lookup));
                        }
                    }
                    return cellsToRecalculate;
                }

                else
                {
                    IList<string> cellsToRecalculate = SetCellContents(name, content);
                    foreach (string cell in cellsToRecalculate)
                    {
                        if (cell.Equals(name))
                        {
                            if (!content.Equals(""))
                            {
                                cellDictionary[name].SetValue(content);
                            }
                        }
                        else
                        {
                            Formula cellFormula = new Formula(cellDictionary[cell].GetContents().ToString(), this.Normalize, this.IsValid);
                            cellDictionary[cell].SetValue(cellFormula.Evaluate(Lookup));
                        }
                    }
                    Changed = true;
                    return cellsToRecalculate;
                }
            }
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

        // A cell's value
        private object value;

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
        /// Gets the value of the cell.
        /// </summary>
        /// <returns> The desired content of the cell. </returns>
        public object GetValue()
        {
            return value;
        }

        /// <summary>
        /// Sets the contents of the cell.
        /// </summary>
        /// <param name="value"> Desired value to be set. </param>
        /// <returns></returns>
        public bool SetValue(object value)
        {
            this.value = value;
            return true;
        }

        /// <summary>
        /// Evaluates the given formula.
        /// </summary>
        /// <param name="lookup"> Delegate to be passed into the Evaluate method of Formula. </param>
        /// <returns> The completed operation of the given formula. </returns>
        public object Evaluate(Func<string, double> lookup)
        {
            // variables are now the names of cells
            // We use lookup
            return ((Formula)contents).Evaluate(lookup);
        }
    }
}