// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using Model;
using NetworkUtil;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using TankWars;

namespace Controller
{
    /// <summary>
    /// A class that updates the view and handles controls.
    /// </summary>
    public class GameController
    {
        // theWorld is a simple container for Players, Powerups, Projectiles, Beams, and Walls.
        private World theWorld;
        private int theWorldSize;
        private string dataToSend;
        // Instance variables to hold data and send to server for controls.
        private string moving = "none";
        private string fire = "none";
        private Vector2D direction = new Vector2D();
        // Booleans that check to see if an input was passed through.
        private bool movingPressed = false;
        private bool mousePressed = false;
        private bool mouseMoved = false;
        // Controller events that the view can subscribe to.
        public delegate void MessageHandler(IEnumerable<string> messages);
        public event MessageHandler MessagesArrived;
        public delegate void ErrorHandler(string err);
        public event ErrorHandler Error;
        public delegate void BeamHandler(Beam b);
        public event BeamHandler BeamFired;
        // bool to keep track of firing beams
        private bool canFireBeam;

        /// <summary>
        /// State representing the connection with the server.
        /// </summary>
        SocketState theServer = null;

        /// <summary>
        /// Default constructor for the GameController class.
        /// </summary>
        public GameController()
        {
        }

        /// <summary>
        /// This method allows access to the world.
        /// </summary>
        /// <returns>Returns the world.</returns>
        public World GetWorld()
        {
            return theWorld;
        }

        /// <summary>
        /// Begins the process of connecting to the server.
        /// </summary>
        /// <param name="addr"> The IP address of the server the client wishes to connect to. </param>
        public void Connect(string addr)
        {
            Networking.ConnectToServer(OnConnect, addr, 11000);
        }


        /// <summary>
        /// Method to be invoked by the networking library when a connection is made.
        /// </summary>
        /// <param name="state"> The current socket state. </param>
        private void OnConnect(SocketState state)
        {
            if (state.ErrorOccurred)
            {
                // Inform the view.
                Error("Error connecting to server");
                return;
            }
            theServer = state;
            // Start an event loop to receive messages from the server
            state.OnNetworkAction = initialReceive;
            Networking.GetData(state);
        }

        /// <summary>
        /// The initial OnNetworkAction for the event loop which gets the playerID and world
        /// size sent by the server, before setting the states OnNetworkAction to ReceiveMessage.
        /// </summary>
        /// <param name="state"> The current state. </param>
        private void initialReceive(SocketState state)
        {
            if(state.ErrorOccurred)
            {
                Error("Error while recieving data");
                return;
            }
            string totalData = state.GetData();
            string[] parts = Regex.Split(totalData, @"(?<=[\n])");
            // Loop until we have processed all messages.
            // We may have received more than one.
            List<string> newMessages = new List<string>();
            ;
            foreach (string p in parts)
            {
                // Ignore empty strings added by the regex splitter
                if (p.Length == 0)
                    continue;
                // The regex splitter will include the last string even if it doesn't end with a '\n',
                // So we need to ignore it if this happens. 
                if (p[p.Length - 1] != '\n')
                    break;
                // Build a list of messages to send to the view.
                newMessages.Add(p);
                // Then remove it from the SocketState's growable buffer.
                state.RemoveData(0, p.Length);
            }
            // Sets the playerID and world size given by the server.
            theWorldSize = Int32.Parse(newMessages[1]);
            theWorld = new World(theWorldSize);
            theWorld.SetPLayerID(Int32.Parse(newMessages[0]));
            ProcessMessages(state);
            state.OnNetworkAction = ReceiveMessage;
            Networking.GetData(state);
        }

        /// <summary>
        /// Method to be invoked by the networking library when 
        /// data is available.
        /// </summary>
        /// <param name="state"> The current state. </param>
        private void ReceiveMessage(SocketState state)
        {
            if (state.ErrorOccurred)
            {
                // inform the view
                Error("Lost connection to server");
                return;
            }
            ProcessMessages(state);

            Networking.GetData(state);
        }

        /// <summary>
        /// Process any buffered messages separated by '\n' and then informs the view.
        /// </summary>
        /// <param name="state"> The current state. </param>
        private void ProcessMessages(SocketState state)
        {
            string totalData = state.GetData();
            string[] parts = Regex.Split(totalData, @"(?<=[\n])");
            // Loop until we have processed all messages.
            // We may have received more than one.
            List<string> newMessages = new List<string>();
            StringBuilder Json = new StringBuilder();
            foreach (string p in parts)
            {
                // Ignore empty strings added by the regex splitter.
                if (p.Length == 0)
                    continue;
                // The regex splitter will include the last string even if it doesn't end with a '\n',
                // So we need to ignore it if this happens. 
                if (p[p.Length - 1] != '\n')
                    break;
                // Build a list of messages to send to the view.
                newMessages.Add(p);
                Json.Append(p);
                // Then remove it from the SocketState's growable buffer.
                state.RemoveData(0, p.Length);
            }
            // Inform the view.
            MessagesArrived(newMessages);
        }

        /// <summary>
        /// Closes the connection with the server.
        /// </summary>
        public void Close()
        {
            theServer?.TheSocket.Close();
        }

        /// <summary>
        /// Sends a message to the server.
        /// </summary>
        /// <param name="message"> The message to be sent to the server. </param>
        public void MessageEntered(string message)
        {
            if (theServer != null)
            {
                Networking.Send(theServer.TheSocket, message + "\n");
            }
            else
            {
                Error("Handshake not completed. Please try again.");
            }
        }

        /// <summary>
        /// This Handler processes the wall JSON strings sent from the server and then updates the model (world).
        /// </summary>
        /// <param name="newMessages"> The JSON strings to be proccesed. </param>
        public void GetWalls(IEnumerable<string> newMessages)
        {
            foreach (string s in newMessages)
            {
                if (Regex.IsMatch(s, "wall"))
                {
                    //Deserialize the given JSON string to a wall and update the model (world).
                    Wall rebuilt = JsonConvert.DeserializeObject<Wall>(s);
                    theWorld.GetWalls().Add(rebuilt.GetID(), rebuilt);
                }
            }
        }

        /// <summary>
        /// This Handler receives JSON strings sent by the server and deserialzes them, and updates the model (world).
        /// </summary>
        /// <param name="newMessages"> JSON strings to be proccesses. </param>
        public void UpdateModel(IEnumerable<string> newMessages)
        {
            // Lock the model (world) to avoid any race conditions, and update the model
            // by deserializing the new JSON strings.
            lock (theWorld)
            {
                List<int> setUpInfo = new List<int>();
                foreach (string s in newMessages)
                {
                    if (Regex.IsMatch(s, "tank"))
                    {
                        Tank rebuilt = JsonConvert.DeserializeObject<Tank>(s);
                        theWorld.addTank(rebuilt);
                    }
                    else if (Regex.IsMatch(s, "beam"))
                    {
                        Beam rebuilt = JsonConvert.DeserializeObject<Beam>(s);
                        theWorld.addBeam(rebuilt);
                    }
                    else if (Regex.IsMatch(s, "power"))
                    {
                        Powerups rebuilt = JsonConvert.DeserializeObject<Powerups>(s);
                        theWorld.addPowerUp(rebuilt);
                    }
                    else
                    {
                        Projectile rebuilt = JsonConvert.DeserializeObject<Projectile>(s);
                        theWorld.addProjectile(rebuilt);
                    }
                }
            }
            // Looks for any beams to be deserialized.
            foreach (string s in newMessages)
            {
                if (Regex.IsMatch(s, "beam"))
                {
                    Beam rebuilt = JsonConvert.DeserializeObject<Beam>(s);
                    BeamFired(rebuilt);
                }
            }
            ProcessInputs();
        }

        /// <summary>
        /// Checks which inputs are currently held down, then 
        /// informs the server of the control requests.
        /// </summary>
        private void ProcessInputs()
        {
            if (movingPressed || mouseMoved || mousePressed)
            {
                //Logic for firing beams to avoid firing beams to fast.
                if (canFireBeam && fire.Equals("alt"))
                {
                    canFireBeam = false;
                    fire = "alt";
                    ControlCommands cmd = new ControlCommands(moving, fire, direction);
                    fire = "none";
                    dataToSend = JsonConvert.SerializeObject(cmd) + "\n";
                    MessageEntered(dataToSend);
                }
                else
                {
                    ControlCommands cmd = new ControlCommands(moving, fire, direction);
                    dataToSend = JsonConvert.SerializeObject(cmd) + "\n";
                    MessageEntered(dataToSend);
                }
            }
        }

        /// <summary>
        /// Handler to proccess movement controls of current player.
        /// </summary>
        public void HandleMoveControls(string move)
        {
            movingPressed = true;
            moving = move;
        }

        /// <summary>
        /// Handler to proccess firing controls of current player.
        /// </summary>
        public void HandleMouseClickControls(string shoot)
        {
            mousePressed = true;
            fire = shoot;
        }

        /// <summary>
        /// Handler to proccess aiming controls of the current player.
        /// </summary>
        public void HandleMouseMoveControls(Vector2D dir)
        {
            mouseMoved = true;
            direction = dir;
        }

        /// <summary>
        /// Handler when movement control keys are released.
        /// </summary>
        public void HandleKeyRelease(string move)
        {
            moving = move;
            movingPressed = false;
        }

        /// <summary>
        /// Handler for firing controls (when mouse buttons are released).
        /// </summary>
        public void HandleMouseUp()
        {
            fire = "none";
            mousePressed = false;
            canFireBeam = true;
        }
    }
}
