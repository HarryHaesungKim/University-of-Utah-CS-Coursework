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
        private int ID { get; set; }
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

        // Private variables for a tank object to keep track of things such as framerate, respawn delay, firing delay, and number of beams.
        private int frameCountFiring;
        private bool canFireProj;
        private int frameCountRespawn;
        private bool canRespawn;
        private int beamShots;
        private bool firedBeam;

        /// <summary>
        /// An empty default constructor for JSON.
        /// </summary>
        public Tank()
        {

        }

        /// <summary>
        /// Constructor for a tank object to be created, serialized, and sent to clients from the server.
        /// </summary>
        /// <param name="name"> The string name of this tank. </param>
        /// <param name="id"> I.D. of the tank. </param>
        /// <param name="location"> The Vector2D object that represents the location of this tank. </param>
        /// <param name="beamShots"> The number of beams a tank starts out with depending on gamemode. </param>
        public Tank(string name, int id, Vector2D location, int beamShots)
        {
            this.ID = id;
            this.name = name;
            this.hitPoints = 3;
            this.died = false;
            this.disconnected = false;
            this.joined = true;
            this.location = location;
            this.aiming = new Vector2D(0.0, -1.0);
            this.orientation = new Vector2D(0.0, -1.0);
            this.frameCountFiring = 0;
            this.canFireProj = true;
            this.frameCountRespawn = 0;
            this.canRespawn = true;
            this.beamShots = beamShots;
            this.firedBeam = false;
        }

        /// <summary>
        /// Creates a copy of a tank's location. Used for collision detection.
        /// </summary>
        /// <param name="t"> The given tank object. </param>
        public Tank(Tank t)
        {
            this.location = t.location;
        }

        /// <summary>
        /// This method processes a ControlCommands object for a tank. Moves and aims turret in a specified direction.
        /// </summary>
        /// <param name="movement"> The ControlCommands object to be processed. </param>
        public void inputCommands(ControlCommands movement)
        {
            switch (movement.GetControlCommands().Item1)
            {
                case "up":
                    this.location += new Vector2D(0.0, -3.0);
                    this.orientation = new Vector2D(0.0, -1.0);
                    break;
                case "down":
                    this.location += new Vector2D(0.0, 3.0);
                    this.orientation = new Vector2D(0.0, 1.0);
                    break;
                case "left":
                    this.location += new Vector2D(-3.0, 0.0);
                    this.orientation = new Vector2D(-1.0, 0.0);
                    break;
                case "right":
                    this.location += new Vector2D(3.0, 0.0);
                    this.orientation = new Vector2D(1.0, 0.0);
                    break;
                default:
                    this.location += new Vector2D(0.0, 0.0);
                    break;
            }
            // For turret aiming.
            this.aiming = movement.GetControlCommands().Item3;
        }

        /// <summary>
        /// This method returns whether or not a beam has been fired.
        /// </summary>
        /// <returns> Bool for whether or not a beam has been fired. </returns>
        public bool GetFiredBeam()
        {
            return this.firedBeam;
        }

        /// <summary>
        /// This method sets whether or not a beam has been fired.
        /// </summary>
        /// <param name="firedBeam"> Bool to be set to firedBeam. </param>
        public void SetFiredBeam(bool firedBeam)
        {
            this.firedBeam = firedBeam;
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
        /// This method sets the tank's Vector2D location.
        /// </summary>
        /// <param name="location"> The new location of the tank. </param>
        public void SetLocation(Vector2D location)
        {
            this.location = location;
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
        /// This method increments the score.
        /// </summary>
        public void IncrementScore()
        {
            score++;
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
        /// This method sets whether this tank is active.
        /// </summary>
        /// <param name="dead"> The new state of the tank. </param>
        public void SetDied(bool dead)
        {
            died = dead;
        }

        /// <summary>
        /// This method returns whether this tank has disconnected.
        /// </summary>
        /// <returns> whether this tank disconnected. </returns>
        public bool GetDisconnected()
        {
            return disconnected;
        }

        /// <summary>
        /// This method ensures that disconnected tanks are handled properly.
        /// </summary>
        public void OnDisconnected()
        {
            this.died = true;
            this.hitPoints = 0;
            this.disconnected = true;
        }
        /// <summary>
        /// This method returns the number of frames passed since a tank fired a projectile.
        /// </summary>
        /// <returns> The number of frames since tank has fired. </returns>
        public int GetFrameCountFiring()
        {
            return frameCountFiring;
        }
        /// <summary>
        /// This method increments the number of frames passed since a tank fired a projectile.
        /// </summary>
        public void IncrementFrameCountFiring()
        {
            frameCountFiring++;
        }
        /// <summary>
        /// This method resets the number of frames passed since a tank fired a projectile.
        /// </summary>
        public void ResetFrameCountFiring()
        {
            frameCountFiring = 0;
        }
        /// <summary>
        /// This method returns whether a tank can fire another projectile.
        /// </summary>
        /// <returns> Bool for whether a tank can fire another projectile. </returns>
        public bool GetCanFireProj()
        {
            return canFireProj;
        }
        /// <summary>
        /// This method sets whether a tank can fire another projectile.
        /// </summary>
        /// <param name="canFire"> The bool to be set to whether a tank can fire another projectile. </param>
        public void SetCanFireProj(bool canFire)
        {
            canFireProj = canFire;
        }
        /// <summary>
        /// This method returns the number of frames passed since a tank died.
        /// </summary>
        /// <returns> The number of frames passed since a tank died. </returns>
        public int GetFrameCountRespawn()
        {
            return frameCountRespawn;
        }
        /// <summary>
        /// This method increments the number of frames passed since a tank died.
        /// </summary>
        public void IncrementFrameCountRespawn()
        {
            frameCountRespawn++;
        }
        /// <summary>
        /// This method resets the number of frames passed since a tank died.
        /// </summary>
        public void ResetFrameCountRespawn()
        {
            frameCountRespawn = 0;
        }
        /// <summary>
        /// This method returns whether or not a tank can respawn.
        /// </summary>
        /// <returns> Whether or not a tank can respawn. </returns>
        public bool GetCanRespawn()
        {
            return canRespawn;
        }
        /// <summary>
        /// This method sets whether or not a tank can respawn.
        /// </summary>
        /// <param name="canRespawn"> The bool to be set to whether or not a tank can respawn. </param>
        public void SetCanRespawn(bool canRespawn)
        {
            this.canRespawn = canRespawn;
        }
        /// <summary>
        /// This method decrements the hit points of a tank if it is hit by a projectile.
        /// </summary>
        public void DecrementHP()
        {
            hitPoints--;
        }
        /// <summary>
        /// This method instantly kills a tank. Used when hit by a beam.
        /// </summary>
        public void InstaKill()
        {
            hitPoints = 0;
        }
        /// <summary>
        /// This method resets the tanks hit points for respawning.
        /// </summary>
        public void ResetHP()
        {
            hitPoints = 3;
        }
        /// <summary>
        /// This method increments the number of beam shots a tank has.
        /// </summary>
        public void IncrementBeamShots()
        {
            this.beamShots++;
        }
        /// <summary>
        /// This method decrements the number of beam shots a tank has.
        /// </summary>
        public void DecrementBeamShots()
        {
            this.beamShots--;
        }
        /// <summary>
        /// this method sets how many beam shots a tank has. Used for alternate gamemodes.
        /// </summary>
        /// <param name="beamShots"> The number of beam shots for the tank to have. </param>
        public void SetBeamShots(int beamShots)
        {
            this.beamShots = beamShots;
        }
        /// <summary>
        /// This method returns the number of beam shots a tank currently has.
        /// </summary>
        /// <returns> The number of beam shots a tank currently has. </returns>
        public int GetBeamShots()
        {
            return this.beamShots;
        }
    }
}
