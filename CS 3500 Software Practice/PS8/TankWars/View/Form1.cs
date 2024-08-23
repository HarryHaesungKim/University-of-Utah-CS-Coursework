// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using Controller;
using Model;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;
using TankWars;

namespace View
{
    /// <summary>
    /// The class represents the window that contains the drawing panel that draws the game.
    /// </summary>
    public partial class Form1 : Form
    {
        private DrawingPanel drawingPanel;
        private GameController controller;

        // The controller owns the world, but we have a reference to it.
        private int viewSize = 900;
        private const int menuSize = 40;

        // Boleans that keep track is mulitple control keys are pressed at the same time
        // to ensure smooth movement.
        private bool wIsPressed;
        private bool aIsPressed;
        private bool sIsPressed;
        private bool dIsPressed;

        public Form1()
        {
            InitializeComponent();
            controller = new GameController();

            //Register handlers for the controller's events.
            controller.MessagesArrived += setPlayerIDAndWorld;
            controller.Error += ShowError;

            //Register handler for when the form is closed.
            FormClosed += OnExit;

            //Place and add the drawing panel
            drawingPanel = new DrawingPanel(controller.GetWorld());
            drawingPanel.Location = new Point(0, menuSize);
            drawingPanel.Size = new Size(viewSize, viewSize);
            drawingPanel.BackColor = Color.Black;
            this.Controls.Add(drawingPanel);

            //Beam animation handler.
            controller.BeamFired += drawingPanel.AddBeamAnimation;

            //Set up key and mouse handlers.
            this.KeyDown += HandleKeyDown;
            this.KeyUp += HandleKeyUp;
            drawingPanel.MouseDown += HandleMouseDown;
            drawingPanel.MouseUp += HandleMouseUp;
            drawingPanel.MouseMove += HandleMouseMove;

            //Sets movement booleans to false.
            wIsPressed = false;
            aIsPressed = false;
            sIsPressed = false;
            dIsPressed = false;
        }

        /// <summary>
        /// This method handles aiming.
        /// </summary>
        /// <param name="sender"> The parameter that contains a reference to the control/object that raised this event. </param>
        /// <param name="e"> The MouseEventArgs to access the current state of the mouse. </param>
        private void HandleMouseMove(object sender, MouseEventArgs e)
        {

            Vector2D location = new Vector2D(e.Location.X - viewSize / 2, e.Location.Y - viewSize / 2);
            location.Normalize();
            controller.HandleMouseMoveControls(location);
        }

        /// <summary>
        /// This method handles the mouse button release.
        /// </summary>
        /// <param name="sender"> The parameter that contains a reference to the control/object that raised this event. </param>
        /// <param name="e"> The MouseEventArgs to access the current state of the mouse. </param>
        private void HandleMouseUp(object sender, MouseEventArgs e)
        {
            controller.HandleMouseUp();
        }

        /// <summary>
        /// This method handles the mouse button press and "firing" for the player.
        /// </summary>
        /// <param name="sender"> The parameter that contains a reference to the control/object that raised this event. </param>
        /// <param name="e"> The MouseEventArgs to access the current state of the mouse. </param>
        private void HandleMouseDown(object sender, MouseEventArgs e)
        {
            string fire = "none";
            MouseButtons click = e.Button;
            if (click == MouseButtons.Left)
            {
                fire = "main";
            }
            if (click == MouseButtons.Right)
            {
                fire = "alt";
            }
            controller.HandleMouseClickControls(fire);
        }

        /// <summary>
        /// Handler for the controller's Error event.
        /// </summary>
        /// <param name="err"></param>
        private void ShowError(string err)
        {
            // Show the error
            MessageBox.Show(err);
            // Then re-enable the controlls so the user can reconnect
            this.Invoke(new MethodInvoker(
              () =>
              {
                  connectButton.Enabled = true;
                  serverIPTB.Enabled = true;
                  playerTB.Enabled = true;
              }));
        }

        /// <summary>
        /// This method handles setting the current player's ID and the worldsize and then sets the OnNetworkAction to getWallJSONData.
        /// </summary>
        /// <param name="newMessages"> The JSON strings sent by the server. </param>
        private void setPlayerIDAndWorld(IEnumerable<string> newMessages)
        {
            controller.MessagesArrived -= setPlayerIDAndWorld;
            controller.MessagesArrived += getWallJSONData;
        }

        /// <summary>
        /// This method handles setting the walls and then sets the OnNetworkAction to GetJSONData.
        /// </summary>
        /// <param name="newMessages"> The JSON strings sent by the server. </param>
        private void getWallJSONData(IEnumerable<string> newMessages)
        {
            drawingPanel.SetWorld(controller.GetWorld());
            controller.GetWalls(newMessages);
            drawingPanel.SetWorld(controller.GetWorld());
            controller.MessagesArrived -= getWallJSONData;
            controller.MessagesArrived += GetJSONData;
        }

        /// <summary>
        /// Handler for the controllers MessagesArrived event for tanks, powerups, beams, projectiles, and controls.
        /// </summary>
        /// <param name="newMessages"> The JSON strings sent by the server. </param>
        private void GetJSONData(IEnumerable<string> newMessages)
        {
            controller.UpdateModel(newMessages);
            MethodInvoker invalidator = new MethodInvoker(() => this.Invalidate(true));
            try
            {
                this.Invoke(invalidator);
            }
            catch (Exception)
            {
            }
        }

        /// <summary>
        /// Handles the form closing by shutting down the socket cleanly.
        /// </summary>
        /// <param name="sender"> The parameter that contains a reference to the control/object that raised this event. </param>
        /// <param name="e"> The FormClosedEventArgs to access the current state of the form. </param>
        private void OnExit(object sender, FormClosedEventArgs e)
        {
            controller.Close();
        }

        /// <summary>
        /// Connect button event handler.
        /// </summary>
        /// <param name="sender"> The parameter that contains a reference to the control/object that raised this event. </param>
        /// <param name="e"> The EventArgs to access the current state of the event. </param>
        private void connectButton_Click(object sender, EventArgs e)
        {
            if (serverIPTB.Text == "")
            {
                MessageBox.Show("Please enter a server address");
                return;
            }
            if (playerTB.Text.Length > 16)
            {
                MessageBox.Show("Name must be less than 16 characters");
                return;
            }
            // Disable the controls and try to connect
            connectButton.Enabled = false;
            serverIPTB.Enabled = false;
            playerTB.Enabled = false;
            // Complete the handshake.
            controller.Connect(serverIPTB.Text);
            controller.MessageEntered(playerTB.Text + "\n");
        }

        /// <summary>
        /// This method represents the key down handler.
        /// </summary>
        /// <param name="sender"> The parameter that contains a reference to the control/object that raised this event. </param>
        /// <param name="e"> The KeyEventArgs to access the current state of the keys. </param>
        private void HandleKeyDown(object sender, KeyEventArgs e)
        {

            Keys keypressed = e.KeyCode;
            switch (keypressed)
            {
                case Keys.A:
                    aIsPressed = true;
                    controller.HandleMoveControls("left");
                    break;
                case Keys.W:
                    wIsPressed = true;
                    controller.HandleMoveControls("up");
                    break;
                case Keys.S:
                    sIsPressed = true;
                    controller.HandleMoveControls("down");
                    break;
                case Keys.D:
                    dIsPressed = true;
                    controller.HandleMoveControls("right");
                    break;
                default:
                    break;
            }
            if (e.KeyCode == Keys.Escape)
                Application.Exit();
            // Prevent other key handlers from running
            e.SuppressKeyPress = true;
            e.Handled = true;
        }

        /// <summary>
        /// This method represents the key up handler.
        /// </summary>
        /// <param name="sender"> The parameter that contains a reference to the control/object that raised this event. </param>
        /// <param name="e"> The KeyEventArgs to access the current state of the keys. </param>
        private void HandleKeyUp(object sender, KeyEventArgs e)
        {
            // Logic that dictates multiple key presses to ensure smoothness.
            if (e.KeyData == Keys.W)
            {
                wIsPressed = false;
                controller.HandleKeyRelease("none");
                if (aIsPressed)
                {
                    controller.HandleKeyRelease("left");
                }
                if (sIsPressed)
                {
                    controller.HandleKeyRelease("down");
                }
                if (dIsPressed)
                {
                    controller.HandleKeyRelease("right");
                }
            }
            if (e.KeyData == Keys.A)
            {
                aIsPressed = false;
                controller.HandleKeyRelease("none");
                if (wIsPressed)
                {
                    controller.HandleKeyRelease("up");
                }
                if (sIsPressed)
                {
                    controller.HandleKeyRelease("down");
                }
                if (dIsPressed)
                {
                    controller.HandleKeyRelease("right");
                }
            }
            if (e.KeyData == Keys.S)
            {
                sIsPressed = false;
                controller.HandleKeyRelease("none");
                if (aIsPressed)
                {
                    controller.HandleKeyRelease("left");
                }
                if (wIsPressed)
                {
                    controller.HandleKeyRelease("up");
                }
                if (dIsPressed)
                {
                    controller.HandleKeyRelease("right");
                }
            }
            if (e.KeyData == Keys.D)
            {
                dIsPressed = false;
                controller.HandleKeyRelease("none");
                if (aIsPressed)
                {
                    controller.HandleKeyRelease("left");
                }
                if (sIsPressed)
                {
                    controller.HandleKeyRelease("down");
                }
                if (wIsPressed)
                {
                    controller.HandleKeyRelease("up");
                }
            }
        }
    }

    public class DrawingPanel : Panel
    {
        private World theWorld;
        private const int tankWidth = 60;
        // load the background image
        private Image imageOfTheBackground = Image.FromFile(@"..\..\..\GameResources\Images\Background.png");
        // load tank and turret images
        private Tuple<Image, Image> blueTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\BlueTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\BlueTurret.png"));
        private Tuple<Image, Image> darkBlueTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\DarkTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\DarkTurret.png"));
        private Tuple<Image, Image> greenTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\GreenTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\GreenTurret.png"));
        private Tuple<Image, Image> orangeTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\OrangeTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\OrangeTurret.png"));
        private Tuple<Image, Image> yellowTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\YellowTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\YellowTurret.png"));
        private Tuple<Image, Image> redTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\RedTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\RedTurret.png"));
        private Tuple<Image, Image> lightGreenTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\LightGreenTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\LightGreenTurret.png"));
        private Tuple<Image, Image> purpleTank = new Tuple<Image, Image>(Image.FromFile(@"..\..\..\GameResources\Images\PurpleTank.png"), Image.FromFile(@"..\..\..\GameResources\Images\PurpleTurret.png"));
        // load projectile images
        private Image blueShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-blue.png");
        private Image greyShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-grey.png");
        private Image greenShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-green.png");
        private Image whiteShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-white.png");
        private Image purpleShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-violet.png");
        private Image redShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-red.png");
        private Image yellowShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-yellow.png");
        private Image brownShot = Image.FromFile(@"..\..\..\GameResources\Images\shot-brown.png");
        // load wall image
        private Image wallImage = Image.FromFile(@"..\..\..\GameResources\Images\WallSprite.png");

        /// <summary>
        /// This method is a constructor for creating a drawing panel with a specified world.
        /// </summary>
        /// <param name="w"> The world with which we are working with. </param>
        public DrawingPanel(World w)
        {
            DoubleBuffered = true;
            theWorld = w;
        }

        /// <summary>
        /// This method sets the world to be worked with.
        /// </summary>
        /// <param name="w"> The world with which we are working with. </param>
        public void SetWorld(World w)
        {
            theWorld = w;
        }

        // A delegate for DrawObjectWithTransform.
        // Methods matching this delegate can draw whatever they want using e.
        public delegate void ObjectDrawer(object o, PaintEventArgs e);

        // A hashset for the beamAnimation objects for the beam animation.
        private HashSet<BeamAnimation> beamAnimations = new HashSet<BeamAnimation>();

        /// <summary>
        /// Adding a beam animation to the beamAnimations hashset.
        /// </summary>
        /// <param name="b"> Beam data from server. </param>
        public void AddBeamAnimation(Beam b)
        {
            BeamAnimation BA = new BeamAnimation(b.GetOrigin(), b.GetDirection());
            this.Invoke(new MethodInvoker(() => { beamAnimations.Add(BA); }));
        }

        // A hashset for the deathAnimation objects for the death animation.
        private HashSet<DeathAnimation> deathAnimations = new HashSet<DeathAnimation>();

        /// <summary>
        /// Adding a death animation to the deathAnimations hashset.
        /// </summary>
        /// <param name="t"> Tank data from server. </param>
        public void AddDeathAnimation(Tank t)
        {
            DeathAnimation DA = new DeathAnimation(t.GetLocation());
            this.Invoke(new MethodInvoker(() => { deathAnimations.Add(DA); }));
        }

        /// <summary>
        /// This method performs a translation and rotation to drawn an object in the world.
        /// </summary>
        /// <param name="e">PaintEventArgs to access the graphics (for drawing)</param>
        /// <param name="o">The object to draw</param>
        /// <param name="worldX">The X coordinate of the object in world space</param>
        /// <param name="worldY">The Y coordinate of the object in world space</param>
        /// <param name="angle">The orientation of the objec, measured in degrees clockwise from "up"</param>
        /// <param name="drawer">The drawer delegate. After the transformation is applied, the delegate is invoked to draw whatever it wants</param>
        private void DrawObjectWithTransform(PaintEventArgs e, object o, double worldX, double worldY, double angle, ObjectDrawer drawer)
        {
            // "push" the current transform
            System.Drawing.Drawing2D.Matrix oldMatrix = e.Graphics.Transform.Clone();

            e.Graphics.TranslateTransform((int)worldX, (int)worldY);
            e.Graphics.RotateTransform((float)angle);
            drawer(o, e);

            // "pop" the transform
            e.Graphics.Transform = oldMatrix;
        }



        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform for powerups
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void PowerupDrawer(object o, PaintEventArgs e)
        {
            using (System.Drawing.SolidBrush redBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Red))
            {
                Rectangle r = new Rectangle(-16, -16, 15, 15);
                e.Graphics.FillEllipse(redBrush, r);
            }
            using (System.Drawing.SolidBrush whiteBrush = new System.Drawing.SolidBrush(System.Drawing.Color.White))
            {
                Rectangle r = new Rectangle(-13, -13, 10, 10);
                e.Graphics.FillEllipse(whiteBrush, r);
            }
        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform for projectiles
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void ProjectileDrawer(object o, PaintEventArgs e)
        {
            Projectile p = o as Projectile;
            Image ProjectileColor = SetProjectileColor(p);
            e.Graphics.DrawImage(ProjectileColor, -(30 / 2), -(30 / 2), 30, 30);
        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform for the tank body
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void TankDrawer(object o, PaintEventArgs e)
        {
            // draw the tank health, name, and score here
            Tank t = o as Tank;
            Image tankColor = SetTankColor(t).Item1;
            e.Graphics.DrawImage(tankColor, -(tankWidth / 2), -(tankWidth / 2), tankWidth, tankWidth);

        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform for the tank turret
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void TurretDrawer(object o, PaintEventArgs e)
        {
            Tank t = o as Tank;
            Image turretColor = SetTankColor(t).Item2;
            e.Graphics.DrawImage(turretColor, -(50 / 2), -(50 / 2), 50, 50);
        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform for a wall
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void WallDrawer(object o, PaintEventArgs e)
        {
            Wall w = o as Wall;
            Image wallImage = this.wallImage;
            e.Graphics.DrawImage(wallImage, -(25), -(25), 50, 50);
        }

        /// <summary>
        /// This method sets the correct color for a tank and turret. Ensures that color of tank and turret matches.
        /// </summary>
        /// <param name="t"> Tank information from the server. </param>
        /// <returns> A Tuple that holds the image for the tank body and the turret. </returns>
        private Tuple<Image, Image> SetTankColor(Tank t)
        {
            // determins the color by the ID between 8 different colors.
            int caseSwitch = t.GetID() % 8;
            Tuple<Image, Image> tankAndTurret;
            // set tank image to proper tank color
            switch (caseSwitch)
            {
                case 0:
                    tankAndTurret = this.blueTank;
                    break;
                case 1:
                    tankAndTurret = this.darkBlueTank;
                    break;
                case 2:
                    tankAndTurret = this.greenTank;
                    break;
                case 3:
                    tankAndTurret = this.lightGreenTank;
                    break;
                case 4:
                    tankAndTurret = this.purpleTank;
                    break;
                case 5:
                    tankAndTurret = this.redTank;
                    break;
                case 6:
                    tankAndTurret = this.yellowTank;
                    break;
                default:
                    tankAndTurret = this.orangeTank;
                    break;
            }
            return tankAndTurret;
        }

        /// <summary>
        /// This method sets the correct color for a projectile. Ensures that color of projectile matches a tank.
        /// </summary>
        /// <param name="p"> Projectile information from the server. </param>
        /// <returns> An image object that holds the image for the projectile . </returns>
        private Image SetProjectileColor(Projectile p)
        {
            // determins the color by the ID between 8 different colors.
            int caseSwitch = p.GetOwnerID() % 8;
            Image image;
            // set tank image to proper tank color
            switch (caseSwitch)
            {
                case 0:
                    image = this.blueShot;
                    break;
                case 1:
                    image = this.greyShot;
                    break;
                case 2:
                    image = this.greenShot;
                    break;
                case 3:
                    image = this.whiteShot;
                    break;
                case 4:
                    image = this.purpleShot;
                    break;
                case 5:
                    image = this.redShot;
                    break;
                case 6:
                    image = this.yellowShot;
                    break;
                default:
                    image = this.brownShot;
                    break;
            }
            return image;
        }

        /// <summary>
        /// This method draws the name and current score of a tank and their current health.
        /// </summary>
        /// <param name="t">Tank information from the server. </param>
        /// <param name="e">The PaintEventArgs to access the graphics.</param>
        private void DrawNameAndHealth(Tank t, PaintEventArgs e)
        {
            // Drawing the name and score of a tank.
            using (System.Drawing.SolidBrush blackBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Black))
            {
                StringFormat format = new StringFormat();
                format.Alignment = StringAlignment.Center;
                format.LineAlignment = StringAlignment.Center;
                Font font = new Font(this.Font, FontStyle.Regular);
                e.Graphics.DrawString(t.GetTankName() + ":" + t.GetScore(), font, blackBrush, (float)t.GetLocation().GetX(), (float)t.GetLocation().GetY() + 40, format);
            }
            int caseSwitch = t.GetHP();
            // Determins how to draw the health bar by how much health a tank has.
            switch (caseSwitch)
            {
                case 3:
                    using (System.Drawing.SolidBrush greenBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Green))
                    {
                        e.Graphics.FillRectangle(greenBrush, (float)t.GetLocation().GetX() - 30, (float)t.GetLocation().GetY() - 40, 60, 5);
                    }
                    break;
                case 2:
                    using (System.Drawing.SolidBrush yellowBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Yellow))
                    {
                        e.Graphics.FillRectangle(yellowBrush, (float)t.GetLocation().GetX() - 30, (float)t.GetLocation().GetY() - 40, 40, 5);
                    }
                    break;
                case 1:
                    using (System.Drawing.SolidBrush redBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Red))
                    {
                        e.Graphics.FillRectangle(redBrush, (float)t.GetLocation().GetX() - 30, (float)t.GetLocation().GetY() - 40, 20, 5);
                    }
                    break;
                default:
                    break;
            }

        }

        /// <summary>
        /// This method is invoked when the DrawingPanel needs to be re-drawn. Draws every object in tank wars.
        /// </summary>
        /// <param name="e"> The PaintEventArgs to access the graphics. </param>
        protected override void OnPaint(PaintEventArgs e)
        {
            // If connection is incompleted, draw nothing.
            if (theWorld == null)
            {
                return;
            }
            // Else, set up the world.
            else
            {
                // locks the world while drawing to avoid race conditions.
                lock (theWorld)
                {
                    int viewSize = Size.Width; // view is square, so we can just use width
                                               // Center the view on the players tank
                    double playerX = theWorld.GetPlayers()[(int)theWorld.GetPLayerID()].GetLocation().GetX();
                    double playerY = theWorld.GetPlayers()[(int)theWorld.GetPLayerID()].GetLocation().GetY();
                    e.Graphics.TranslateTransform((float)(-playerX + (viewSize / 2)), (float)(-playerY + (viewSize / 2)));
                    Point origin = new Point(-theWorld.Size / 2, -theWorld.Size / 2);
                    e.Graphics.DrawImage(imageOfTheBackground, origin.X, origin.Y, theWorld.Size, theWorld.Size);
                    // draw the walls
                    foreach (Wall w in theWorld.GetWalls().Values)
                    {
                        int numWalls = Math.Abs(w.getDistance(w.GetLocation())) / 50;
                        // If x coordinates are the same.
                        if (w.GetLocation().Item1.GetX() == w.GetLocation().Item2.GetX())
                        {
                            // If distance is negative.
                            if (w.getDistance(w.GetLocation()) < 0)
                            {
                                Point startingpoint = new Point((int)w.GetLocation().Item2.GetX(), (int)w.GetLocation().Item2.GetY());
                                for (int i = 0; i < numWalls + 1; i++)
                                {
                                    DrawObjectWithTransform(e, w, startingpoint.X, startingpoint.Y, 0, WallDrawer);
                                    startingpoint = new Point((int)w.GetLocation().Item2.GetX(), (int)w.GetLocation().Item2.GetY() - ((i + 1) * 50));
                                }
                            }
                            // If distance is positive.
                            if (w.getDistance(w.GetLocation()) > 0)
                            {
                                Point startingpoint = new Point((int)w.GetLocation().Item1.GetX(), (int)w.GetLocation().Item1.GetY());
                                for (int i = 0; i < numWalls + 1; i++)
                                {
                                    DrawObjectWithTransform(e, w, startingpoint.X, startingpoint.Y, 0, WallDrawer);
                                    startingpoint = new Point((int)w.GetLocation().Item1.GetX(), (int)w.GetLocation().Item1.GetY() - ((i + 1) * 50));
                                }
                            }
                        }
                        // If y coordinates are the same.
                        else
                        {
                            // If distance is negative.
                            if (w.getDistance(w.GetLocation()) < 0)
                            {
                                Point startingpoint = new Point((int)w.GetLocation().Item2.GetX(), (int)w.GetLocation().Item2.GetY());
                                for (int i = 0; i < numWalls + 1; i++)
                                {
                                    DrawObjectWithTransform(e, w, startingpoint.X, startingpoint.Y, 0, WallDrawer);
                                    startingpoint = new Point((int)w.GetLocation().Item2.GetX() - ((i + 1) * 50), (int)w.GetLocation().Item2.GetY());
                                }
                            }
                            // If distance is positive.
                            if (w.getDistance(w.GetLocation()) > 0)
                            {
                                Point startingpoint = new Point((int)w.GetLocation().Item1.GetX(), (int)w.GetLocation().Item1.GetY());
                                for (int i = 0; i < numWalls + 1; i++)
                                {
                                    DrawObjectWithTransform(e, w, startingpoint.X, startingpoint.Y, 0, WallDrawer);
                                    startingpoint = new Point((int)w.GetLocation().Item1.GetX() - ((i + 1) * 50), (int)w.GetLocation().Item1.GetY());
                                }
                            }
                        }
                    }

                    List<Tank> tanksToBeRemoved = new List<Tank>();
                    // draw the tanks
                    foreach (Tank t in theWorld.GetPlayers().Values)
                    {
                        // Draws tanks only when they are alive (active).
                        if (t.GetHP() != 0)
                        {
                            DrawObjectWithTransform(e, t, t.GetLocation().GetX(), t.GetLocation().GetY(), t.GetOrientation().ToAngle(), TankDrawer);
                            DrawObjectWithTransform(e, t, t.GetLocation().GetX(), t.GetLocation().GetY(), t.GetAiming().ToAngle(), TurretDrawer);
                            DrawNameAndHealth(t, e);
                        }
                        // Else, don't draw tank and draw the death animation instead.
                        else
                        {
                            deathAnimations.Add(new DeathAnimation(t.GetLocation()));
                            tanksToBeRemoved.Add(t);
                        }
                    }

                    foreach(Tank t in tanksToBeRemoved)
                    {
                        theWorld.RemoveTank(t);
                    }

                    // Draw the powerups.
                    foreach (Powerups pu in theWorld.GetPowerUps().Values)
                    {
                        if (!pu.GetDied())
                        {
                            DrawObjectWithTransform(e, pu, pu.GetLocation().GetX(), pu.GetLocation().GetY(), 0, PowerupDrawer);
                        }
                    }

                    // Draw the projectiles.
                    foreach (Projectile p in theWorld.GetProjectiles().Values)
                    {
                        if (!p.GetDied())
                        {
                            DrawObjectWithTransform(e, p, p.GetLocation().GetX(), p.GetLocation().GetY(), p.GetDirection().ToAngle(), ProjectileDrawer);
                        }
                    }

                    // Draw the beam animation.
                    List<BeamAnimation> beamAnimationsToBeRemoved = new List<BeamAnimation>();
                    foreach (BeamAnimation b in beamAnimations)
                    {
                        double x = b.GetOrigin().GetX();
                        double y = b.GetOrigin().GetY();
                        double angle = b.GetOrientation().ToAngle();
                        DrawObjectWithTransform(e, b, x, y, angle, b.BeamDrawer);
                        if (b.GetNumFrames() > 60)
                        {
                            beamAnimationsToBeRemoved.Add(b);
                        }
                    }
                    // Removes beam animation after the beam is drawn.
                    foreach (BeamAnimation b in beamAnimationsToBeRemoved)
                    {
                        beamAnimations.Remove(b);
                    }

                    // Draw the death animation.
                    List<DeathAnimation> deathAnimationsToBeRemoved = new List<DeathAnimation>();
                    foreach (DeathAnimation d in deathAnimations)
                    {
                        double x = d.GetLocation().GetX();
                        double y = d.GetLocation().GetY();

                        DrawObjectWithTransform(e, d, x, y, 0, d.DeathDrawer);

                        if (d.GetNumFrames() > 30)
                        {
                            deathAnimationsToBeRemoved.Add(d);
                        }
                    }

                    // Removes death animation after the tank respawns.
                    foreach (DeathAnimation d in deathAnimationsToBeRemoved)
                    {
                        deathAnimations.Remove(d);
                    }
                }
                // Redraw the drawing panel.
                base.OnPaint(e);
            }
        }
    }
}
