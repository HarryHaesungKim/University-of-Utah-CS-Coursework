
namespace SpreadsheetGUI
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.spreadsheetPanelWindow = new SS.SpreadsheetPanel();
            this.enterFunctionButton = new System.Windows.Forms.Button();
            this.FunctionInputBox = new System.Windows.Forms.TextBox();
            this.cellNameDisplayBox = new System.Windows.Forms.TextBox();
            this.cellValueDisplayBox = new System.Windows.Forms.TextBox();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.closeToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.helpToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.controlsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.enteringFunctionsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.fileTabButtonsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newButtonToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveButtonToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openButtonToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.closeButtonToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.errorsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.clearButton = new System.Windows.Forms.Button();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // spreadsheetPanelWindow
            // 
            this.spreadsheetPanelWindow.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.spreadsheetPanelWindow.Location = new System.Drawing.Point(13, 71);
            this.spreadsheetPanelWindow.Name = "spreadsheetPanelWindow";
            this.spreadsheetPanelWindow.Size = new System.Drawing.Size(788, 393);
            this.spreadsheetPanelWindow.TabIndex = 0;
            // 
            // enterFunctionButton
            // 
            this.enterFunctionButton.Location = new System.Drawing.Point(329, 37);
            this.enterFunctionButton.Name = "enterFunctionButton";
            this.enterFunctionButton.Size = new System.Drawing.Size(75, 23);
            this.enterFunctionButton.TabIndex = 1;
            this.enterFunctionButton.Text = "Enter";
            this.enterFunctionButton.UseVisualStyleBackColor = true;
            this.enterFunctionButton.Click += new System.EventHandler(this.enterFunctionButton_Click);
            // 
            // FunctionInputBox
            // 
            this.FunctionInputBox.Location = new System.Drawing.Point(223, 39);
            this.FunctionInputBox.Name = "FunctionInputBox";
            this.FunctionInputBox.Size = new System.Drawing.Size(100, 20);
            this.FunctionInputBox.TabIndex = 2;
            // 
            // cellNameDisplayBox
            // 
            this.cellNameDisplayBox.Location = new System.Drawing.Point(13, 39);
            this.cellNameDisplayBox.Name = "cellNameDisplayBox";
            this.cellNameDisplayBox.Size = new System.Drawing.Size(100, 20);
            this.cellNameDisplayBox.TabIndex = 3;
            // 
            // cellValueDisplayBox
            // 
            this.cellValueDisplayBox.Location = new System.Drawing.Point(118, 39);
            this.cellValueDisplayBox.Name = "cellValueDisplayBox";
            this.cellValueDisplayBox.Size = new System.Drawing.Size(100, 20);
            this.cellValueDisplayBox.TabIndex = 4;
            // 
            // backgroundWorker1
            // 
            this.backgroundWorker1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.backgroundWorker1_DoWork);
            this.backgroundWorker1.RunWorkerCompleted += new System.ComponentModel.RunWorkerCompletedEventHandler(this.backgroundWorker1_RunWorkerCompleted);
            // 
            // menuStrip1
            // 
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.helpToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Padding = new System.Windows.Forms.Padding(4, 2, 0, 2);
            this.menuStrip1.Size = new System.Drawing.Size(812, 24);
            this.menuStrip1.TabIndex = 6;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newToolStripMenuItem,
            this.saveToolStripMenuItem,
            this.openToolStripMenuItem,
            this.closeToolStripMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
            this.fileToolStripMenuItem.Text = "File";
            // 
            // newToolStripMenuItem
            // 
            this.newToolStripMenuItem.Name = "newToolStripMenuItem";
            this.newToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.newToolStripMenuItem.Text = "New";
            this.newToolStripMenuItem.Click += new System.EventHandler(this.newToolStripMenuItem_Click);
            // 
            // saveToolStripMenuItem
            // 
            this.saveToolStripMenuItem.Name = "saveToolStripMenuItem";
            this.saveToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.saveToolStripMenuItem.Text = "Save";
            this.saveToolStripMenuItem.Click += new System.EventHandler(this.saveToolStripMenuItem_Click);
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.openToolStripMenuItem.Text = "Open";
            this.openToolStripMenuItem.Click += new System.EventHandler(this.openToolStripMenuItem_Click);
            // 
            // closeToolStripMenuItem
            // 
            this.closeToolStripMenuItem.Name = "closeToolStripMenuItem";
            this.closeToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.closeToolStripMenuItem.Text = "Close";
            this.closeToolStripMenuItem.Click += new System.EventHandler(this.closeToolStripMenuItem_Click);
            // 
            // helpToolStripMenuItem
            // 
            this.helpToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.controlsToolStripMenuItem,
            this.enteringFunctionsToolStripMenuItem,
            this.fileTabButtonsToolStripMenuItem,
            this.errorsToolStripMenuItem});
            this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
            this.helpToolStripMenuItem.Size = new System.Drawing.Size(44, 20);
            this.helpToolStripMenuItem.Text = "Help";
            // 
            // controlsToolStripMenuItem
            // 
            this.controlsToolStripMenuItem.Name = "controlsToolStripMenuItem";
            this.controlsToolStripMenuItem.Size = new System.Drawing.Size(173, 22);
            this.controlsToolStripMenuItem.Text = "Controls";
            this.controlsToolStripMenuItem.Click += new System.EventHandler(this.controlsToolStripMenuItem_Click);
            // 
            // enteringFunctionsToolStripMenuItem
            // 
            this.enteringFunctionsToolStripMenuItem.Name = "enteringFunctionsToolStripMenuItem";
            this.enteringFunctionsToolStripMenuItem.Size = new System.Drawing.Size(173, 22);
            this.enteringFunctionsToolStripMenuItem.Text = "Entering Functions";
            this.enteringFunctionsToolStripMenuItem.Click += new System.EventHandler(this.enteringFunctionsToolStripMenuItem_Click);
            // 
            // fileTabButtonsToolStripMenuItem
            // 
            this.fileTabButtonsToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newButtonToolStripMenuItem,
            this.saveButtonToolStripMenuItem,
            this.openButtonToolStripMenuItem,
            this.closeButtonToolStripMenuItem});
            this.fileTabButtonsToolStripMenuItem.Name = "fileTabButtonsToolStripMenuItem";
            this.fileTabButtonsToolStripMenuItem.Size = new System.Drawing.Size(173, 22);
            this.fileTabButtonsToolStripMenuItem.Text = "File Tab Buttons";
            // 
            // newButtonToolStripMenuItem
            // 
            this.newButtonToolStripMenuItem.Name = "newButtonToolStripMenuItem";
            this.newButtonToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
            this.newButtonToolStripMenuItem.Text = "\'New\' Button";
            this.newButtonToolStripMenuItem.Click += new System.EventHandler(this.newButtonToolStripMenuItem_Click);
            // 
            // saveButtonToolStripMenuItem
            // 
            this.saveButtonToolStripMenuItem.Name = "saveButtonToolStripMenuItem";
            this.saveButtonToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
            this.saveButtonToolStripMenuItem.Text = "\'Save\' Button";
            this.saveButtonToolStripMenuItem.Click += new System.EventHandler(this.saveButtonToolStripMenuItem_Click);
            // 
            // openButtonToolStripMenuItem
            // 
            this.openButtonToolStripMenuItem.Name = "openButtonToolStripMenuItem";
            this.openButtonToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
            this.openButtonToolStripMenuItem.Text = "\'Open\' Button";
            this.openButtonToolStripMenuItem.Click += new System.EventHandler(this.openButtonToolStripMenuItem_Click);
            // 
            // closeButtonToolStripMenuItem
            // 
            this.closeButtonToolStripMenuItem.Name = "closeButtonToolStripMenuItem";
            this.closeButtonToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
            this.closeButtonToolStripMenuItem.Text = "\'Close\' Button";
            this.closeButtonToolStripMenuItem.Click += new System.EventHandler(this.closeButtonToolStripMenuItem_Click);
            // 
            // errorsToolStripMenuItem
            // 
            this.errorsToolStripMenuItem.Name = "errorsToolStripMenuItem";
            this.errorsToolStripMenuItem.Size = new System.Drawing.Size(173, 22);
            this.errorsToolStripMenuItem.Text = "Errors";
            this.errorsToolStripMenuItem.Click += new System.EventHandler(this.errorsToolStripMenuItem_Click);
            // 
            // clearButton
            // 
            this.clearButton.Location = new System.Drawing.Point(690, 37);
            this.clearButton.Name = "clearButton";
            this.clearButton.Size = new System.Drawing.Size(75, 23);
            this.clearButton.TabIndex = 7;
            this.clearButton.Text = "Clear";
            this.clearButton.UseVisualStyleBackColor = true;
            this.clearButton.Click += new System.EventHandler(this.clearButton_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(812, 474);
            this.Controls.Add(this.clearButton);
            this.Controls.Add(this.cellValueDisplayBox);
            this.Controls.Add(this.cellNameDisplayBox);
            this.Controls.Add(this.FunctionInputBox);
            this.Controls.Add(this.enterFunctionButton);
            this.Controls.Add(this.spreadsheetPanelWindow);
            this.Controls.Add(this.menuStrip1);
            this.Name = "Form1";
            this.Text = "Spreadsheet";
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private SS.SpreadsheetPanel spreadsheetPanelWindow;
        private System.Windows.Forms.Button enterFunctionButton;
        private System.Windows.Forms.TextBox FunctionInputBox;
        private System.Windows.Forms.TextBox cellNameDisplayBox;
        private System.Windows.Forms.TextBox cellValueDisplayBox;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem closeToolStripMenuItem;
        private System.Windows.Forms.Button clearButton;
        private System.Windows.Forms.ToolStripMenuItem helpToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem controlsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem enteringFunctionsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem fileTabButtonsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem newButtonToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveButtonToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openButtonToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem closeButtonToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem errorsToolStripMenuItem;
    }
}

