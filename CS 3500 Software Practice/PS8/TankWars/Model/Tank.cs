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
    /// This class represents a tank in tank wars.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Tank
    {
        /// <summary>
        /// I.D. of the tank.
        /// </summary>
        [JsonProperty(PropertyName = "tank")]
        private int ID {  get; set; }
        /// <summary>
        /// The Vector2D object that represents the location of this tank.
        /// </summary>
        [JsonProperty(PropertyName = "loc")]
        private Vector2D location { get; set; }
        /// <summary>
        /// The Vector2D object that represents the direction this tank is moving (left, right, up, down).
        /// </summary>
        [JsonProperty(PropertyName = "bdir")]
        private Vector2D orientation { get; set; }
        /// <summary>
        /// The Vector2D object that represents direction the tank's turret is aiming.
        /// </summary>
        [JsonProperty(PropertyName = "tdir")]
        private Vector2D aiming { get; set; }
        /// <summary>
        /// The string name of this tank.
        /// </summary>
        [JsonProperty(PropertyName = "name")]
        private string name { get; set; }
        /// <summary>
        /// The int representing the HealthPoints of this tank (0-3).
        /// </summary>
        [JsonProperty(PropertyName = "hp")]
        private int hitPoints { get; set; }
        /// <summary>
        /// The score of this tank (how many tanks has this tank killed).
        /// </summary>
        [JsonProperty(PropertyName = "score")]
        private int score { get; set; }
        /// <summary>
        /// A boolean representing whether this tank is active.
        /// </summary>
        [JsonProperty(PropertyName = "died")]
        private bool died { get; set; }
        /// <summary>
        /// A boolean representing whether this tank has disconnected.
        /// </summary>
        [JsonProperty(PropertyName = "dc")]
        private bool disconnected { get; set; }
        /// <summary>
        /// A boolean representing whether this tank just joined.
        /// </summary>
        [JsonProperty(PropertyName = "join")]
        private bool joined { get; set; }
        /// <summary>
        /// An empty default constructor for JSON.
        /// </summary>
        public Tank()
        {
            
        }
        /// <summary>
        /// This method returns this tank's unique int ID.
        /// </summary>
        /// <returns> This tanks unique ID. </returns>
        public int GetID()
        {
            return this.ID;
        }
        /// <summary>
        /// This method returns this tank's Vector2D location.
        /// </summary>
        /// <returns> Returns this tanks location. </returns>
        public Vector2D GetLocation()
        {
            return this.location;
        }
        /// <summary>
        /// This method returns this tank's normalized Vector2D orientation (direction of movement).
        /// </summary>
        /// <returns> returns this tanks direction of movement (up, down, left, right). </returns>
        public Vector2D GetOrientation()
        {
            this.orientation.Normalize();
            return orientation;
        }
        /// <summary>
        /// This method returns the Vector2D direction of the turrent.
        /// </summary>
        /// <returns> The direction which the this tanks turret is aiming. </returns>
        public Vector2D GetAiming()
        {
            return aiming;
        }
        /// <summary>
        /// This method returns the string name of this tank.
        /// </summary>
        /// <returns> This tanks string name. </returns>
        public string GetTankName()
        {
            return name;
        }
        /// <summary>
        /// This method returns the amount of the healthpoints this tank has.
        /// </summary>
        /// <returns> Returns how many healthpoints this tank has (0-3). </returns>
        public int GetHP()
        {
            return hitPoints;
        }
        /// <summary>
        /// This method returns the number of tanks this tank has MURDERED.
        /// </summary>
        /// <returns> The number of tanks this tank has killed. </returns>
        public int GetScore()
        {
            return score;
        }
        /// <summary>
        /// This method returns whether this tank is active.
        /// </summary>
        /// <returns> Whether or not this tank was destroyed. </returns>
        public bool GetDied()
        {
            return died;
        }
        /// <summary>
        /// This method returns whether this tank has disconnected.
        /// </summary>
        /// <returns> whether this tank disconnected. </returns>
        public bool GetDisconnected()
        {
            return disconnected;
        }
    }
}
