// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using System;
using System.Collections.Generic;

namespace Model
{
    /// <summary>
    /// This class keeps track of all the objects present in the current game of tank wars.
    /// </summary>
    public class World
    {
        // Create dictionaries to track objects from the server.
        private Dictionary<int, Tank> Players;
        private Dictionary<int, Powerups> Powerups;
        private Dictionary<int, Wall> Walls;
        private Dictionary<int, Beam> Beams;
        private Dictionary<int, Projectile> Projectiles;
        // The playerID given to this player by the server.
        private double PlayerID;
        // The size of this world given by the server.
        private int size;
        /// <summary>
        /// The size of this world given by the server.
        /// </summary>
        public int Size
        { get { return size; } private set { } }
        /// <summary>
        /// This method adds a tank to this world if the tank does not already exist, otherwise replaces
        /// the tank with the updated one (updated version of itself). Doesn't add tanks if the HP is zero
        /// (this is for the death animation to work properly).
        /// </summary>
        /// <param name="t"> Tank to be added to this world. </param>
        public void addTank(Tank t)
        {
            if (Players.ContainsKey(t.GetID()))
            {
                Players[t.GetID()] = t;
            }
            else
            {
                if (t.GetHP() != 0)
                {
                    Players.Add(t.GetID(), t); 
                }
            }
        }
        /// <summary>
        /// This method adds a powerup to this world if the powerup does not already exist, otherwise replaces
        /// the powerup with the updated one (updated version of itself).
        /// </summary>
        /// <param name="pu"> Powerup to be added to this world. </param>
        public void addPowerUp(Powerups pu)
        {
            if (Powerups.ContainsKey(pu.GetID()))
            {
                if (pu.GetDied())
                {
                    Powerups.Remove(pu.GetID());
                    return;
                }
                Powerups[pu.GetID()] = pu;
            }
            else
                Powerups.Add(pu.GetID(), pu);
        }
        /// <summary>
        /// This method adds a projectile to this world if the projectile does not already exist, otherwise replaces
        /// the projectile with the updated one (updated version of itself).
        /// </summary>
        /// <param name="p"> Projectile to be added to this world. </param>
        public void addProjectile(Projectile p)
        {
            if (Projectiles.ContainsKey(p.GetID()))
            {
                if (p.GetDied())
                {
                    Projectiles.Remove(p.GetID());
                    return;
                }
                Projectiles[p.GetID()] = p;
            }
            else
                Projectiles.Add(p.GetID(), p);
        }
        /// <summary>
        /// This method adds a beam to this world if the beam does not already exist, otherwise replaces
        /// the beam with the updated one (updated version of itself).
        /// </summary>
        /// <param name="b"> Beam to be added to this world. </param>
        public void addBeam(Beam b)
        {
            if (Beams.ContainsKey(b.GetID()))
            {
                Beams[b.GetID()] = b;
            }
            else
                Beams.Add(b.GetID(), b);
        }
        /// <summary>
        /// Creates a world with the specified world size along with all the needed dictionaries of different objects within the game.
        /// </summary>
        /// <param name="_size"> The size of the world. </param>
        public World(int _size)
        {
            Players = new Dictionary<int, Tank>();
            PlayerID = Double.NegativeInfinity;
            Powerups = new Dictionary<int, Powerups>();
            Walls = new Dictionary<int, Wall>();
            Beams = new Dictionary<int, Beam>();
            Projectiles = new Dictionary<int, Projectile>();
            size = _size;
        }
        /// <summary>
        /// returns the dictionary of this world's beams.
        /// </summary>
        /// <returns> This world's beams. </returns>
        public Dictionary<int, Beam> GetBeams()
        {
            return Beams;
        }
        /// <summary>
        /// Returns the dictionary of this world's powerups.
        /// </summary>
        /// <returns> This world's powerups. </returns>
        public Dictionary<int, Powerups> GetPowerUps()
        {
            return Powerups;
        }
        /// <summary>
        /// Returns the dictionary of this world's projectiles.
        /// </summary>
        /// <returns> This world's projectiles. </returns>
        public Dictionary<int, Projectile> GetProjectiles()
        {
            return Projectiles;
        }
        /// <summary>
        /// Returns the dictionary of this world's players (tanks).
        /// </summary>
        /// <returns> This world's tanks. </returns>
        public Dictionary<int, Tank> GetPlayers()
        {
            return Players;
        }
        /// <summary>
        /// Returns the dictionary of this world's walls.
        /// </summary>
        /// <returns> This world's walls. </returns>
        public Dictionary<int, Wall> GetWalls()
        {
            return Walls;
        }
        /// <summary>
        /// Sets the ID of the main player in this world.
        /// </summary>
        public void SetPLayerID(int ID)
        {
            PlayerID = ID;
        }
        /// <summary>
        /// This method returns the ID of the main player in this world.
        /// </summary>
        /// <returns> The ID of the main player in this world. </returns>
        public double GetPLayerID()
        {
            return PlayerID;
        }

        /// <summary>
        /// Removes tanks from the world.
        /// </summary>
        /// <param name="t"> Tank object. </param>
        public void RemoveTank(Tank t)
        {
            Players.Remove(t.GetID());
        }
    }
}
