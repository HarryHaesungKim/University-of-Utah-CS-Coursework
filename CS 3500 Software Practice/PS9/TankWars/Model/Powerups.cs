// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace Model
{
    /// <summary>
    /// This class represents a Powerup in tank wars.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Powerups
    {
        /// <summary>
        /// The unique int ID given that represents this powerup.
        /// </summary>
        [JsonProperty(PropertyName = "power")]
        private int ID { get; set; }
        /// <summary>
        /// The Vector2D which represents this powerup's location.
        /// </summary>
        [JsonProperty(PropertyName = "loc")]
        private Vector2D location { get; set; }
        /// <summary>
        /// Boolean that tracks whether or not this powerup is active ("dead" or not).
        /// </summary>
        [JsonProperty(PropertyName = "died")]
        private bool didDie { get; set; }
        /// <summary>
        /// An empty default constructor for JSON.
        /// </summary>
        public Powerups()
        {

        }
        /// <summary>
        /// Constructor for a powerup object to be created, serialized, and sent to clients from the server.
        /// </summary>
        /// <param name="ID"> The unique int ID given that represents this powerup. </param>
        /// <param name="location"> The Vector2D which represents this powerup's location. </param>
        /// <param name="didDie"> Boolean that tracks whether or not this powerup is active ("dead" or not). </param>
        public Powerups(int ID, Vector2D location, bool didDie)
        {
            this.ID = ID;
            this.location = location;
            this.didDie = didDie;
        }
        /// <summary>
        /// This method sets this powerup's didDie boolean.
        /// </summary>
        /// <param name="isDead"> The boolean to be set to didDie. </param>
        public void SetDied(bool isDead)
        {
            this.didDie = isDead;
        }
        /// <summary>
        /// This method returns this powerup's unique ID.
        /// </summary>
        /// <returns> This Powerups unique ID</returns>
        public int GetID()
        {
            return ID;
        }
        /// <summary>
        /// This method returns this powerup's Vector2D location.
        /// </summary>
        /// <returns> This powerups Vector2D location </returns>
        public Vector2D GetLocation()
        {
            return location;
        }
        /// <summary>
        /// This method returns this powerup's boolean which tracks whether or not this powerup active ("dead" or not).
        /// </summary>
        /// <returns> Returns whether this powerup is active </returns>
        public bool GetDied()
        {
            return didDie;
        }
    }
}
