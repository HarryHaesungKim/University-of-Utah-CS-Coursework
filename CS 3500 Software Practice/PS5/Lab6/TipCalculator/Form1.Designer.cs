
namespace TipCalculator
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
            this.enterBillLabel = new System.Windows.Forms.Label();
            this.computeTipButton = new System.Windows.Forms.Button();
            this.enterBillTextBox = new System.Windows.Forms.TextBox();
            this.tipAmountTextBox = new System.Windows.Forms.TextBox();
            this.tipPercentageLable = new System.Windows.Forms.Label();
            this.topPercentTextBox = new System.Windows.Forms.TextBox();
            this.totalTextBox = new System.Windows.Forms.TextBox();
            this.total = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // enterBillLabel
            // 
            this.enterBillLabel.AutoSize = true;
            this.enterBillLabel.Location = new System.Drawing.Point(186, 98);
            this.enterBillLabel.Name = "enterBillLabel";
            this.enterBillLabel.Size = new System.Drawing.Size(75, 13);
            this.enterBillLabel.TabIndex = 0;
            this.enterBillLabel.Text = "Enter Total Bill";
            this.enterBillLabel.Click += new System.EventHandler(this.label1_Click);
            // 
            // computeTipButton
            // 
            this.computeTipButton.Location = new System.Drawing.Point(186, 163);
            this.computeTipButton.Name = "computeTipButton";
            this.computeTipButton.Size = new System.Drawing.Size(75, 23);
            this.computeTipButton.TabIndex = 1;
            this.computeTipButton.Text = "Compute Tip";
            this.computeTipButton.UseVisualStyleBackColor = true;
            this.computeTipButton.Click += new System.EventHandler(this.computeTipButton_Click);
            // 
            // enterBillTextBox
            // 
            this.enterBillTextBox.Location = new System.Drawing.Point(342, 91);
            this.enterBillTextBox.Name = "enterBillTextBox";
            this.enterBillTextBox.Size = new System.Drawing.Size(100, 20);
            this.enterBillTextBox.TabIndex = 2;
            this.enterBillTextBox.TextChanged += new System.EventHandler(this.enterBillTextBox_TextChanged);
            // 
            // tipAmountTextBox
            // 
            this.tipAmountTextBox.Location = new System.Drawing.Point(342, 166);
            this.tipAmountTextBox.Name = "tipAmountTextBox";
            this.tipAmountTextBox.Size = new System.Drawing.Size(100, 20);
            this.tipAmountTextBox.TabIndex = 3;
            // 
            // tipPercentageLable
            // 
            this.tipPercentageLable.AutoSize = true;
            this.tipPercentageLable.Location = new System.Drawing.Point(186, 128);
            this.tipPercentageLable.Name = "tipPercentageLable";
            this.tipPercentageLable.Size = new System.Drawing.Size(80, 13);
            this.tipPercentageLable.TabIndex = 4;
            this.tipPercentageLable.Text = "Tip Percentage";
            // 
            // topPercentTextBox
            // 
            this.topPercentTextBox.Location = new System.Drawing.Point(342, 125);
            this.topPercentTextBox.Name = "topPercentTextBox";
            this.topPercentTextBox.Size = new System.Drawing.Size(100, 20);
            this.topPercentTextBox.TabIndex = 5;
            // 
            // totalTextBox
            // 
            this.totalTextBox.Location = new System.Drawing.Point(342, 213);
            this.totalTextBox.Name = "totalTextBox";
            this.totalTextBox.Size = new System.Drawing.Size(100, 20);
            this.totalTextBox.TabIndex = 6;
            // 
            // total
            // 
            this.total.AutoSize = true;
            this.total.Location = new System.Drawing.Point(204, 213);
            this.total.Name = "total";
            this.total.Size = new System.Drawing.Size(31, 13);
            this.total.TabIndex = 7;
            this.total.Text = "Total";
            this.total.Click += new System.EventHandler(this.label1_Click_1);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(642, 372);
            this.Controls.Add(this.total);
            this.Controls.Add(this.totalTextBox);
            this.Controls.Add(this.topPercentTextBox);
            this.Controls.Add(this.tipPercentageLable);
            this.Controls.Add(this.tipAmountTextBox);
            this.Controls.Add(this.enterBillTextBox);
            this.Controls.Add(this.computeTipButton);
            this.Controls.Add(this.enterBillLabel);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label enterBillLabel;
        private System.Windows.Forms.Button computeTipButton;
        private System.Windows.Forms.TextBox enterBillTextBox;
        private System.Windows.Forms.TextBox tipAmountTextBox;
        private System.Windows.Forms.Label tipPercentageLable;
        private System.Windows.Forms.TextBox topPercentTextBox;
        private System.Windows.Forms.TextBox totalTextBox;
        private System.Windows.Forms.Label total;
    }
}

