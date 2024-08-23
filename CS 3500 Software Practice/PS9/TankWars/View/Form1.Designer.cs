
namespace View
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
            this.serverIPTB = new System.Windows.Forms.TextBox();
            this.serverLabel = new System.Windows.Forms.Label();
            this.playerTB = new System.Windows.Forms.TextBox();
            this.nameLabel = new System.Windows.Forms.Label();
            this.connectButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // serverIPTB
            // 
            this.serverIPTB.Location = new System.Drawing.Point(57, 6);
            this.serverIPTB.Name = "serverIPTB";
            this.serverIPTB.Size = new System.Drawing.Size(100, 20);
            this.serverIPTB.TabIndex = 0;
            this.serverIPTB.Text = "LocalHost";
            // 
            // serverLabel
            // 
            this.serverLabel.AutoSize = true;
            this.serverLabel.Location = new System.Drawing.Point(12, 9);
            this.serverLabel.Name = "serverLabel";
            this.serverLabel.Size = new System.Drawing.Size(39, 13);
            this.serverLabel.TabIndex = 1;
            this.serverLabel.Text = "server:";
            // 
            // playerTB
            // 
            this.playerTB.Location = new System.Drawing.Point(205, 6);
            this.playerTB.Name = "playerTB";
            this.playerTB.Size = new System.Drawing.Size(100, 20);
            this.playerTB.TabIndex = 2;
            this.playerTB.Text = "Player";
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Location = new System.Drawing.Point(163, 9);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(36, 13);
            this.nameLabel.TabIndex = 3;
            this.nameLabel.Text = "name:";
            // 
            // connectButton
            // 
            this.connectButton.Location = new System.Drawing.Point(311, 4);
            this.connectButton.Name = "connectButton";
            this.connectButton.Size = new System.Drawing.Size(75, 23);
            this.connectButton.TabIndex = 4;
            this.connectButton.Text = "connect";
            this.connectButton.UseVisualStyleBackColor = true;
            this.connectButton.Click += new System.EventHandler(this.connectButton_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(900, 940);
            this.Controls.Add(this.connectButton);
            this.Controls.Add(this.nameLabel);
            this.Controls.Add(this.playerTB);
            this.Controls.Add(this.serverLabel);
            this.Controls.Add(this.serverIPTB);
            this.Name = "Form1";
            this.Text = "Tank Wars";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox serverIPTB;
        private System.Windows.Forms.Label serverLabel;
        private System.Windows.Forms.TextBox playerTB;
        private System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.Button connectButton;
    }
}

