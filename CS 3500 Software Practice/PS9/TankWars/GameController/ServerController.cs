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
using System.Xml;
using TankWars;

namespace Controller
{
    /// <summary>
    /// This class calls model, worlds, etc and updates the game.
    /// Also takes care of all the network behavior.
    /// </summary>
    public class ServerController
    {
        // Dictionary that keeps track of all the clients.
        private Dictionary<long, SocketState> clients;
        // Game settings read from the settings file.
        private int UniverseSize;
        private int MSPerFrame;
        private int FramesPerShot;
        private int RespawnRate;
        private int GameMode;
        private List<Wall> Walls;
        // The world created for the server that keeps track of all objects.
        private World ServerWorld;
        // Control commands recieved by the client to be processed.
        IEnumerable<string> controlsCommands;
        // Unique ids that get incremented for when new objects are created.
        private int projID;
        private int powerupID;
        private int beamID;
        private int worldFrameCount;
        // Random used for powerupRespawn timer.
        private Random rand;
        private int powerupRespawn;

        /// <summary>
        /// A constructor that creates a server controller objects. Initialized all necessary components for a working tank wars game.
        /// </summary>
        public ServerController()
        {
            clients = new Dictionary<long, SocketState>();
            Walls = new List<Wall>();
            // Reads settings and gets walls.
            SettingsXMLReader();
            ServerWorld = new World(UniverseSize);
            foreach (Wall w in Walls)
            {
                ServerWorld.GetWalls().Add(w.GetID(), w);
            }
            projID = 0;
            powerupID = 0;
            beamID = 0;
            worldFrameCount = 0;
            rand = new Random();
            powerupRespawn = rand.Next(0,1650);
        }

        /// <summary>
        /// This method returns the dictionary of clients currently connected to the server.
        /// </summary>
        /// <returns> The dictionary of current clients. </returns>
        public Dictionary<long, SocketState> GetClients()
        {
            return this.clients;
        }

        /// <summary>
        /// This method returns the UniverseSize, MSPerFrame, FramesPerShot, and RespawnRate from the settings file.
        /// </summary>
        /// <returns> Returns setting options obtained from the settings file. </returns>
        public Tuple<int, int, int, int> GetGameInfo()
        {
            return new Tuple<int, int, int, int>(UniverseSize, MSPerFrame, FramesPerShot, RespawnRate);
        }

        /// <summary>
        /// This method starts the a server.
        /// </summary>
        public void StartSever()
        {
            Networking.StartServer(HandleNewClient, 11000);
            Console.WriteLine("Server Started. Awaiting client connection.");
        }


        /// <summary>
        /// Delegate callback passed to the networking class to handle a new client connecting. Changes the callback for the socket state to a new method that receives the player's name and then asks for data.
        /// </summary>
        private void HandleNewClient(SocketState state)
        {
            if (state.ErrorOccurred)
                Console.WriteLine("Error occred while client tried to connect");

            // Change the state's network action to the receive handler so we can process data when something happens on the network.
            state.OnNetworkAction = ReceivePlayerName;
            Networking.GetData(state);
        }

        /// <summary>
        /// Delegate that implements the server's part of the initial handshake. Make a new Tank with the given name and a new unique ID.
        /// Change the callback to a method that handles command requests from the client. 
        /// Send the startup info to the client. Then add the client's socket to a list of all clients. Then ask the client for data.
        /// </summary>
        /// <param name="state"> The current SocketState of the client. </param>
        private void ReceivePlayerName(SocketState state)
        {
            if (state.ErrorOccurred)
                Console.WriteLine("Error occred while client tried to connect");
            // record the player name and print that this player has connectd.
            string playerName = state.GetData();
            Console.WriteLine(playerName.Substring(0, playerName.Length - 1) + " has connected to the server");

            // Create a new tank with the specified playername and 
            // with a uniwie ID at a random location on the map (avoiding collisions).
            Vector2D location = PickRandomLocation();
            Tank newTank;
            // Different gamemodes determined from the settings file.
            if (GameMode == 1)
            {
                newTank = new Tank(playerName, (int)state.ID, location, 1);
            }
            else
            {
                newTank = new Tank(playerName, (int)state.ID, location, 0);
            }

            // Locking the world while we modify it to avoid race conditions and crashes.
            lock (ServerWorld)
            {
                ServerWorld.addTank(newTank);
            }

            // change the callback
            state.OnNetworkAction = HandleDataFromClient;

            // send the clients unique ID as well as the world size
            Networking.Send(state.TheSocket, state.ID.ToString() + "\n");
            Networking.Send(state.TheSocket, UniverseSize.ToString() + "\n");

            // send all of the wall data
            foreach (Wall w in Walls)
            {
                Networking.Send(state.TheSocket, JsonConvert.SerializeObject(w) + "\n");
            }
            // Add the client to the list of clients.
            lock (clients)
            {
                clients[state.ID] = state;
            }
            Networking.GetData(state);
        }

        /// <summary>
        /// This method picks a random location on the map that doesn't collide with any walls.
        /// </summary>
        /// <returns> A random location on the map that is not within a wall. </returns>
        private Vector2D PickRandomLocation()
        {
            Random rnd = new Random();
            restart:
            double x = rnd.Next(-UniverseSize / 2, UniverseSize / 2);
            double y = rnd.Next(-UniverseSize / 2, UniverseSize / 2);
            double x1;
            double x2;
            double y1;
            double y2;

            // Checks every instance of a wall to see if the chosen location collides with a wall.
            // Otherwise a new location is selected and all walls are checked again.
            // Should be fast enough for tank wars.
            foreach(Wall w in Walls)
            {
                if(w.GetLocation().Item1.GetX() <= w.GetLocation().Item2.GetX())
                {
                    x1 = w.GetLocation().Item1.GetX() - 55;
                    x2 = w.GetLocation().Item2.GetX() + 55;
                }
                else
                {
                    x1 = w.GetLocation().Item2.GetX() - 55;
                    x2 = w.GetLocation().Item1.GetX() + 55;
                }

                if (w.GetLocation().Item1.GetY() <= w.GetLocation().Item2.GetY())
                {
                    y1 = w.GetLocation().Item1.GetY() - 55;
                    y2 = w.GetLocation().Item2.GetY() + 55;
                }
                else
                {
                    y1 = w.GetLocation().Item2.GetY() - 55;
                    y2 = w.GetLocation().Item1.GetY() + 55;
                }

                if (x <= x2 && x >= x1 && y <= y2 && y >= y1)
                {
                    goto restart;
                }
            }

            return new Vector2D(x, y); ;
        }

        /// <summary>
        /// Checks to see if an object is colliding with a wall in the world.
        /// </summary>
        /// <param name="currentLocation"> The current location of the object in question. </param>
        /// <param name="obj"> The object in question. </param>
        /// <returns> Whether or not the ojbect is colliding with a wall. </returns>
        private bool IsCollidingWithWall(Vector2D currentLocation, object obj)
        {
            double x1;
            double x2;
            double y1;
            double y2;

            // Checks every instance of a wall to see if a given object collides with a wall.
            // Should be fast enough for tank wars.
            foreach (Wall w in Walls)
            {
                if (w.GetLocation().Item1.GetX() <= w.GetLocation().Item2.GetX())
                {
                    x1 = w.GetLocation().Item1.GetX() - 25;
                    x2 = w.GetLocation().Item2.GetX() + 25;
                }
                else
                {
                    x1 = w.GetLocation().Item2.GetX() - 25;
                    x2 = w.GetLocation().Item1.GetX() + 25;
                }

                if (w.GetLocation().Item1.GetY() <= w.GetLocation().Item2.GetY())
                {
                    y1 = w.GetLocation().Item1.GetY() - 25;
                    y2 = w.GetLocation().Item2.GetY() + 25;
                }
                else
                {
                    y1 = w.GetLocation().Item2.GetY() - 25;
                    y2 = w.GetLocation().Item1.GetY() + 25;
                }

                if(obj is Tank)
                {
                    x1 += -25;
                    x2 += 25;
                    y1 += -25;
                    y2 += 25;
                }

                if (currentLocation.GetX() <= x2 && currentLocation.GetX() >= x1 && currentLocation.GetY() <= y2 && currentLocation.GetY() >= y1)
                {
                    return true;
                }
            }

            return false;
        }

        /// <summary>
        /// This method takes an object and a tank to determine if they are colliding. Used for projectiles and powerups.
        /// </summary>
        /// <param name="a"> A given object (either a powerup or a projectile). </param>
        /// <param name="t"> A given tank object. </param>
        /// <returns></returns>
        private bool AreColliding(object a, Tank t)
        {
            double tankX1 = t.GetLocation().GetX() - 25;
            double tankX2 = t.GetLocation().GetX() + 25;
            double tankY1 = t.GetLocation().GetY() - 25;
            double tankY2 = t.GetLocation().GetY() + 25;

            if (a is Projectile)
            {
                Projectile p = (Projectile)a;
                if (p.GetLocation().GetX() <= tankX2 && p.GetLocation().GetX() >= tankX1 && p.GetLocation().GetY() <= tankY2 && p.GetLocation().GetY() >= tankY1)
                {
                    return true;
                }
            }

            else if(a is Powerups)
            {
                Powerups pu = (Powerups)a;
                if (pu.GetLocation().GetX() <= tankX2 && pu.GetLocation().GetX() >= tankX1 && pu.GetLocation().GetY() <= tankY2 && pu.GetLocation().GetY() >= tankY1)
                {
                    return true;
                }
            }

            return false;
        }

        /// <summary>
        /// Determines if a ray interescts a circle.
        /// </summary>
        /// <param name="rayOrig">The origin of the ray</param>
        /// <param name="rayDir">The direction of the ray</param>
        /// <param name="center">The center of the circle</param>
        /// <param name="r">The radius of the circle</param>
        /// <returns> Whether or not a beam hits a tank. </returns>
        public static bool Intersects(Vector2D rayOrig, Vector2D rayDir, Vector2D center, double r)
        {
            // ray-circle intersection test
            // P: hit point
            // ray: P = O + tV
            // circle: (P-C)dot(P-C)-r^2 = 0
            // substituting to solve for t gives a quadratic equation:
            // a = VdotV
            // b = 2(O-C)dotV
            // c = (O-C)dot(O-C)-r^2
            // if the discriminant is negative, miss (no solution for P)
            // otherwise, if both roots are positive, hit

            double a = rayDir.Dot(rayDir);
            double b = ((rayOrig - center) * 2.0).Dot(rayDir);
            double c = (rayOrig - center).Dot(rayOrig - center) - r * r;

            // discriminant
            double disc = b * b - 4.0 * a * c;

            if (disc < 0.0)
                return false;

            // find the signs of the roots
            // technically we should also divide by 2a
            // but all we care about is the sign, not the magnitude
            double root1 = -b + Math.Sqrt(disc);
            double root2 = -b - Math.Sqrt(disc);

            return (root1 > 0.0 && root2 > 0.0);
        }

        /// <summary>
        /// A delegate for processing client direction commands. Process the command, then ask for more data.
        /// </summary>
        /// <param name="state"> The current SocketState of the client. </param>
        private void HandleDataFromClient(SocketState state)
        {
            // Handels client disconnection.
            if (state.ErrorOccurred)
            {
                Console.WriteLine(ServerWorld.GetPlayers()[(int)state.ID].GetTankName().Substring(0, ServerWorld.GetPlayers()[(int)state.ID].GetTankName().Length - 1) + " has disconnected.");
                lock (ServerWorld)
                {
                    ServerWorld.GetPlayers()[(int)state.ID].OnDisconnected();
                }
                clients.Remove(state.ID);
                return;
            }
            // Process control commands from clients and update world accordingly.
            string data = state.GetData();
            controlsCommands = ProcessMessages(state);
            foreach (string s in controlsCommands)
            {
                lock (ServerWorld)
                {
                    if (!s.Equals(ServerWorld.GetPlayers()[(int)state.ID].GetTankName()))
                    {
                        ControlCommands clientCommands = JsonConvert.DeserializeObject<ControlCommands>(s);
                        if(clientCommands.GetControlCommands().Item2.Equals("alt") && ServerWorld.GetPlayers()[(int)state.ID].GetBeamShots() > 0 && ServerWorld.GetPlayers()[(int)state.ID].GetHP() > 0)
                        {
                            ServerWorld.GetPlayers()[(int)state.ID].SetFiredBeam(true);
                        }
                        else if (ServerWorld.GetControls().ContainsKey((int)state.ID) && ServerWorld.GetPlayers()[(int)state.ID].GetHP() > 0)
                        {
                            ServerWorld.GetControls()[(int)state.ID] = clientCommands;
                        }
                        else if (ServerWorld.GetPlayers()[(int)state.ID].GetHP() > 0)
                            ServerWorld.GetControls().Add((int)state.ID, clientCommands);
                    }
                }
            }
            Networking.GetData(state);
        }

        /// <summary>
        /// Process any buffered messages separated by '\n' and then informs the view.
        /// </summary>
        /// <param name="state"> The current state. </param>
        private List<string> ProcessMessages(SocketState state)
        {
            lock (state)
            {
                string totalData = state.GetData();
                string[] parts = Regex.Split(totalData, @"(?<=[\n])");
                // Loop until we have processed all messages.
                // We may have received more than one.
                List<string> newMessages = new List<string>();
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
                    // Then remove it from the SocketState's growable buffer.
                    state.RemoveData(0, p.Length);
                }
                return newMessages;
            }
        }
        /// <summary>
        /// The method invoked every iteration through the frame loop. Update the world, then send it to each client.
        /// </summary>
        public void Update()
        {
            // Different game modes determined by the settings file.
            if (GameMode == 1)
            {
                GameMode1Update();
            }
            else
            {
                GameMode0Update();
            }
        }

        /// <summary>
        /// This method updates the world by default tankwars settings (free for all).
        /// </summary>
        private void GameMode0Update()
        {
            List<Tank> tanksToBeDeleted = new List<Tank>();
            lock (ServerWorld)
            {
                // Process each control command request for each tank.
                foreach (KeyValuePair<int, ControlCommands> entry in ServerWorld.GetControls())
                {
                    // Dummy tank used to determine whether the control command request results in a collision between a tank and a wall.
                    Tank dummy = new Tank(ServerWorld.GetPlayers()[entry.Key]);
                    dummy.inputCommands(entry.Value);
                    if (!IsCollidingWithWall(dummy.GetLocation(), dummy) && ServerWorld.GetPlayers()[entry.Key].GetHP() != 0 && ServerWorld.GetPlayers()[entry.Key].GetCanRespawn())
                    {
                        ServerWorld.GetPlayers()[entry.Key].inputCommands(entry.Value);
                    }
                    // For firing projectiles.
                    if (entry.Value.GetControlCommands().Item2.Equals("main") && ServerWorld.GetPlayers()[entry.Key].GetCanFireProj() == true && ServerWorld.GetPlayers()[entry.Key].GetCanRespawn() && ServerWorld.GetPlayers()[entry.Key].GetHP() != 0)
                    {
                        Projectile newProj = new Projectile(projID, ServerWorld.GetPlayers()[entry.Key].GetLocation(), entry.Value.GetControlCommands().Item3, entry.Key);
                        ServerWorld.addProjectile(newProj);
                        ServerWorld.GetPlayers()[entry.Key].SetCanFireProj(false);
                        projID++;
                    }
                    // For firing beams.
                    if (((entry.Value.GetControlCommands().Item2.Equals("alt") && ServerWorld.GetPlayers()[entry.Key].GetBeamShots() > 0) || (ServerWorld.GetPlayers()[entry.Key].GetFiredBeam() && ServerWorld.GetPlayers()[entry.Key].GetBeamShots() > 0)) && ServerWorld.GetPlayers()[entry.Key].GetHP() != 0 && ServerWorld.GetPlayers()[entry.Key].GetCanRespawn())
                    {
                        Beam beam = new Beam(beamID, ServerWorld.GetPlayers()[entry.Key].GetLocation(), ServerWorld.GetPlayers()[entry.Key].GetAiming(), ServerWorld.GetPlayers()[entry.Key].GetID());
                        ServerWorld.addBeam(beam);
                        ServerWorld.GetPlayers()[entry.Key].DecrementBeamShots();
                        beamID++;
                        ServerWorld.GetPlayers()[entry.Key].SetFiredBeam(false);
                    }
                }

                // Determins behavior of projectile.
                List<int> projKeysToRemove = new List<int>();
                foreach (KeyValuePair<int, Projectile> entry in ServerWorld.GetProjectiles())
                {
                    if (!entry.Value.GetDied())
                    {
                        ServerWorld.GetProjectiles()[entry.Key].UpdateLocation();

                        if (IsOutOfBounds(entry.Value) || IsCollidingWithWall(entry.Value.GetLocation(), entry.Value))
                        {
                            entry.Value.SetDied(true);
                        }
                    }
                    else
                        projKeysToRemove.Add(entry.Key);
                }
                // Get rid of "dead" projectiles.
                foreach (int key in projKeysToRemove)
                {
                    ServerWorld.GetProjectiles().Remove(key);
                }

                // Determins behavior of tanks especially when colliding with other objects (such as powerups and projectiles).
                List<int> powerupKeysToRemove = new List<int>();
                foreach (Tank t in ServerWorld.GetPlayers().Values)
                {
                    // Teleports tanks to other side when trying to go out of bounds.
                    CheckForWrapAround(t);
                    if (t.GetDisconnected().Equals(true))
                    {
                        tanksToBeDeleted.Add(t);
                    }
                    // Checks to see if tanks collide with the projectiles in the world.
                    foreach (Projectile p in ServerWorld.GetProjectiles().Values)
                    {
                        if (t.GetID() != p.GetOwnerID() && AreColliding(p, t) && t.GetCanRespawn())
                        {
                            p.SetDied(true);
                            t.DecrementHP();
                            if (t.GetHP() == 0)
                            {
                                ServerWorld.GetPlayers()[p.GetOwnerID()].IncrementScore();
                                t.SetDied(true);
                                t.SetCanRespawn(false);
                                t.SetCanFireProj(false);
                                t.SetFiredBeam(false);
                            }
                        }
                    }
                    // Projectile firing delay.
                    if (t.GetCanFireProj() == false)
                    {
                        t.IncrementFrameCountFiring();
                        if (t.GetFrameCountFiring() > FramesPerShot)
                        {
                            t.SetCanFireProj(true);
                            t.ResetFrameCountFiring();
                        }
                    }
                    // Checks to see if tanks collide with the powerups in the world.
                    foreach (Powerups pu in ServerWorld.GetPowerUps().Values)
                    {
                        if (AreColliding(pu, t))
                        {
                            pu.SetDied(true);
                            powerupKeysToRemove.Add(pu.GetID());
                            t.IncrementBeamShots();
                        }
                    }
                    // Checks to see if tanks collide with beams in the world.
                    foreach (Beam b in ServerWorld.GetBeams().Values)
                    {
                        if (t.GetID() != b.GetOwnerID() && Intersects(b.GetOrigin(), b.GetDirection(), t.GetLocation(), 30))
                        {
                            t.InstaKill();
                            ServerWorld.GetPlayers()[b.GetOwnerID()].IncrementScore();
                            t.SetDied(true);
                            t.SetCanRespawn(false);
                            t.SetCanFireProj(false);
                            t.SetFiredBeam(false);
                        }
                    }

                    // Respawn delay and control inhibitor when dead.
                    if (t.GetCanRespawn() == false)
                    {
                        t.IncrementFrameCountRespawn();

                        if (t.GetFrameCountRespawn() > 1)
                        {
                            t.SetDied(false);
                        }

                        if (t.GetFrameCountRespawn() > RespawnRate)
                        {
                            t.SetCanFireProj(true);
                            t.ResetFrameCountFiring();
                            t.SetCanRespawn(true);
                            t.SetLocation(PickRandomLocation());
                            t.ResetHP();
                            t.ResetFrameCountRespawn();
                        }
                    }
                }
                // Powerup respawn delay.
                if (ServerWorld.GetPowerUps().Count < 2 && worldFrameCount % powerupRespawn == 0)
                {
                    ServerWorld.GetPowerUps().Add(powerupID, new Powerups(powerupID, PickRandomLocation(), false));
                    powerupID++;
                    powerupRespawn = rand.Next(0, 1650);
                }
                SendWorldToClients();
                ServerWorld.GetBeams().Clear();

                // Removes objects that need to be deleted (meaning they "died").
                foreach (int pu in powerupKeysToRemove)
                {
                    ServerWorld.GetPowerUps().Remove(pu);
                }
                foreach (Tank t in tanksToBeDeleted)
                {
                    ServerWorld.GetPlayers().Remove(t.GetID());
                    ServerWorld.GetControls().Remove(t.GetID());
                }
                // Increment framecount for the world.
                worldFrameCount++;
                if(worldFrameCount > 165000)
                {
                    worldFrameCount = 0;
                }
            }
        }

        /// <summary>
        /// This method is for the alternate gamemode. Similar to "One in the Chamber" on Call of Duty (all properties related to Call of Duty is owned by Activision. We do not own any rights).
        /// </summary>
        private void GameMode1Update()
        {
            List<Tank> tanksToBeDeleted = new List<Tank>();
            lock (ServerWorld)
            {
                // Process each control command request for each tank.
                foreach (KeyValuePair<int, ControlCommands> entry in ServerWorld.GetControls())
                {
                    // Dummy tank used to determine whether the control command request results in a collision between a tank and a wall.
                    Tank dummy = new Tank(ServerWorld.GetPlayers()[entry.Key]);
                    dummy.inputCommands(entry.Value);
                    if (!IsCollidingWithWall(dummy.GetLocation(), dummy) && ServerWorld.GetPlayers()[entry.Key].GetHP() != 0)
                    {
                        ServerWorld.GetPlayers()[entry.Key].inputCommands(entry.Value);
                    }
                    // For firing beams.
                    if (((entry.Value.GetControlCommands().Item2.Equals("alt") && ServerWorld.GetPlayers()[entry.Key].GetBeamShots() > 0) || (ServerWorld.GetPlayers()[entry.Key].GetFiredBeam() && ServerWorld.GetPlayers()[entry.Key].GetBeamShots() > 0)) && ServerWorld.GetPlayers()[entry.Key].GetHP() != 0)
                    {
                        Beam beam = new Beam(beamID, ServerWorld.GetPlayers()[entry.Key].GetLocation(), ServerWorld.GetPlayers()[entry.Key].GetAiming(), ServerWorld.GetPlayers()[entry.Key].GetID());
                        ServerWorld.addBeam(beam);
                        ServerWorld.GetPlayers()[entry.Key].DecrementBeamShots();
                        beamID++;
                        ServerWorld.GetPlayers()[entry.Key].SetFiredBeam(false);
                    }
                }
                // Tanks do not fire projectiles in this gamemode.
                // Determins behavior of tanks especially when colliding with other objects (such as powerups).
                List<int> powerupKeysToRemove = new List<int>();
                foreach (Tank t in ServerWorld.GetPlayers().Values)
                {
                    // Teleports tanks to other side when trying to go out of bounds.
                    CheckForWrapAround(t);
                    if (t.GetDisconnected().Equals(true))
                    {
                        tanksToBeDeleted.Add(t);
                    }
                    // Checks to see if tanks collide with the powerups in the world.
                    foreach (Powerups pu in ServerWorld.GetPowerUps().Values)
                    {
                        if (AreColliding(pu, t) && t.GetBeamShots() == 0)
                        {
                            pu.SetDied(true);
                            powerupKeysToRemove.Add(pu.GetID());
                            t.IncrementBeamShots();
                        }
                    }
                    // Checks to see if tanks collide with beams in the world.
                    foreach (Beam b in ServerWorld.GetBeams().Values)
                    {
                        if (t.GetID() != b.GetOwnerID() && Intersects(b.GetOrigin(), b.GetDirection(), t.GetLocation(), 30))
                        {
                            t.InstaKill();
                            ServerWorld.GetPlayers()[b.GetOwnerID()].IncrementScore();
                            // Beams increment upon a kill.
                            ServerWorld.GetPlayers()[b.GetOwnerID()].IncrementBeamShots();
                            t.SetDied(true);
                            t.SetCanRespawn(false);
                            t.SetFiredBeam(false);
                        }
                    }
                    // Respawn delay and control inhibitor when dead.
                    if (t.GetCanRespawn() == false)
                    {
                        t.IncrementFrameCountRespawn();

                        if (t.GetFrameCountRespawn() > 1)
                        {
                            t.SetDied(false);
                        }

                        if (t.GetFrameCountRespawn() > RespawnRate)
                        {
                            t.SetCanRespawn(true);
                            t.SetLocation(PickRandomLocation());
                            if(t.GetBeamShots() == 0)
                            {
                                t.SetBeamShots(1);
                            }
                            t.ResetHP();
                            t.ResetFrameCountRespawn();
                        }
                    }
                }
                // Powerup respawn delay.
                if (ServerWorld.GetPowerUps().Count < 1 && worldFrameCount % powerupRespawn == 0)
                {
                    ServerWorld.GetPowerUps().Add(powerupID, new Powerups(powerupID, PickRandomLocation(), false));
                    powerupID++;
                    powerupRespawn = rand.Next(0, 1650);
                }
                SendWorldToClients();
                ServerWorld.GetBeams().Clear();
                // Removes objects that need to be deleted (meaning they "died").
                foreach (int pu in powerupKeysToRemove)
                {
                    ServerWorld.GetPowerUps().Remove(pu);
                }
                foreach (Tank t in tanksToBeDeleted)
                {
                    ServerWorld.GetPlayers().Remove(t.GetID());
                    ServerWorld.GetControls().Remove(t.GetID());
                }
                // Increment framecount for the world.
                worldFrameCount++;
                if (worldFrameCount > 165000)
                {
                    worldFrameCount = 0;
                }
            }
        }

        /// <summary>
        /// This method checks to see if a tank is out of bounds. If it is, the tank is teleported to the other side of the world.
        /// </summary>
        /// <param name="t"> The given tank object. </param>
        private void CheckForWrapAround(Tank t)
        {
            if (t.GetLocation().GetX() > UniverseSize / 2 - 30)
            {
                t.SetLocation(new Vector2D(-UniverseSize / 2 + 30, t.GetLocation().GetY()));
            }
            if (t.GetLocation().GetX() < -UniverseSize / 2 + 30)
            {
                t.SetLocation(new Vector2D(UniverseSize / 2 - 30, t.GetLocation().GetY()));
            }
            if (t.GetLocation().GetY() > UniverseSize / 2 - 30)
            {
                t.SetLocation(new Vector2D(t.GetLocation().GetX(), -UniverseSize / 2 + 30));
            }
            if (t.GetLocation().GetY() < -UniverseSize / 2 + 30)
            {
                t.SetLocation(new Vector2D(t.GetLocation().GetX(), UniverseSize / 2 - 30));
            }
        }

        /// <summary>
        /// This method sends all the game info to the clients (tanks, projectiles, powerups, and beams).
        /// </summary>
        public void SendWorldToClients()
        {
            lock (clients)
            {
                foreach (KeyValuePair<long, SocketState> client in clients)
                {
                    lock (ServerWorld)
                    {
                        foreach (Tank t in ServerWorld.GetPlayers().Values)
                        {
                            Networking.Send(client.Value.TheSocket, JsonConvert.SerializeObject(t) + "\n");
                        }
                        foreach (Projectile p in ServerWorld.GetProjectiles().Values)
                        {
                            Networking.Send(client.Value.TheSocket, JsonConvert.SerializeObject(p) + "\n");
                        }
                        foreach (Powerups pu in ServerWorld.GetPowerUps().Values)
                        {
                            Networking.Send(client.Value.TheSocket, JsonConvert.SerializeObject(pu) + "\n");
                        }
                        foreach(Beam b in ServerWorld.GetBeams().Values)
                        {
                            Networking.Send(client.Value.TheSocket, JsonConvert.SerializeObject(b) + "\n");
                        }
                    }
                }
            }
        }

        /// <summary>
        /// This method checks to see if a projectile is outside the bounds of the world.
        /// </summary>
        /// <param name="p"> The given projectile object. </param>
        /// <returns> True if out of bounds. False otherwise. </returns>
        private bool IsOutOfBounds(Projectile p)
        {
            return p.GetLocation().GetX() < (-UniverseSize / 2) || p.GetLocation().GetX() > (UniverseSize / 2)
                || p.GetLocation().GetY() < (-UniverseSize / 2) || p.GetLocation().GetY() > (UniverseSize / 2);
        }

        /// <summary>
        /// Reads a given settings xml file.
        /// </summary>
        private void SettingsXMLReader()
        {
            string filePath = @"..\..\..\..\GameResources\settings.xml";
            try
            {
                using (XmlReader reader = XmlReader.Create(filePath))
                {
                    int wallID = 0;
                    int p1x = 0;
                    int p1y = 0;
                    int p2x = 0;
                    int p2y = 0;

                    // May want to switch to swithc statements.
                    while (reader.Read())
                    {
                        if (reader.IsStartElement())
                        {
                            if (reader.Name.Equals("UniverseSize"))
                            {
                                reader.Read();
                                UniverseSize = Int32.Parse(reader.Value);
                            }
                            else if (reader.Name.Equals("MSPerFrame"))
                            {
                                reader.Read();
                                MSPerFrame = Int32.Parse(reader.Value);
                            }
                            else if (reader.Name.Equals("FramesPerShot"))
                            {
                                reader.Read();
                                FramesPerShot = Int32.Parse(reader.Value);
                            }
                            else if (reader.Name.Equals("RespawnRate"))
                            {
                                reader.Read();
                                RespawnRate = Int32.Parse(reader.Value);
                            }
                            else if (reader.Name.Equals("GameMode"))
                            {
                                reader.Read();
                                GameMode = Int32.Parse(reader.Value);
                            }
                            else if (reader.Name.Equals("Wall"))
                            {
                                reader.Read();
                                while (!reader.Name.Equals("Wall"))
                                {
                                    if (reader.Name.Equals("p1"))
                                    {
                                        reader.Read();
                                        while (!reader.Name.Equals("p1"))
                                        {
                                            if (Regex.IsMatch(reader.Name, "x"))
                                            {
                                                reader.Read();
                                                p1x = Int32.Parse(reader.Value);
                                                reader.Read();
                                            }
                                            else if (Regex.IsMatch(reader.Name, "y"))
                                            {
                                                reader.Read();
                                                p1y = Int32.Parse(reader.Value);
                                                reader.Read();
                                            }
                                            reader.Read();
                                        }
                                    }
                                    else if (reader.Name.Equals("p2"))
                                    {
                                        reader.Read();
                                        while (!reader.Name.Equals("p2"))
                                        {
                                            if (Regex.IsMatch(reader.Name, "x"))
                                            {
                                                reader.Read();
                                                p2x = Int32.Parse(reader.Value);
                                                reader.Read();
                                            }
                                            else if (Regex.IsMatch(reader.Name, "y"))
                                            {
                                                reader.Read();
                                                p2y = Int32.Parse(reader.Value);
                                                reader.Read();
                                            }
                                            reader.Read();
                                        }
                                    }
                                    reader.Read();
                                }
                                Wall wall = new Wall(wallID, new Vector2D(p1x, p1y), new Vector2D(p2x, p2y));
                                Walls.Add(wall);
                                wallID++;
                            }
                            // Add additional functionality.
                            else
                            {
                                continue;
                            }
                        }
                    }
                }
            }
            catch (Exception)
            {
                Console.Write("Error: unable to read settings file: settings.xml \n");
            }
        }
    }
}
