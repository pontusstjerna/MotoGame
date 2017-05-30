﻿using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InfiniteMoto.Model
{
    public class Wheel
    {
        public Vector2 Position {
            get
            {
                return position;
            }
        }
        
        public int Radius = 8;
        public float Rotation { get; private set; }
        public const float GRAVITY = 7f;
        public bool OnGround { get; private set; }

        private const int MAX_SPEED = 150; //This does now have a unit :D It's pixels per second
        private const float MAX_FRICTION = 0.2f;
        private const float MIN_FRICTION = 0.001f;

        private Vector2 position;
        private Vector2 velocity;
        private float angularVelocity;
        private float acceleration;
        private float COF = MIN_FRICTION;

        private bool isAccelerating;

        public Wheel(Point position)
        {
            this.position = position.ToVector2();
            velocity = new Vector2(0, 0);
        }

        public void Update(float dTime, SlopeSegment currentSegment)
        {
            Vector2? maybeIntersection = currentSegment.GetIntersection(this);

            ApplyGravity();

            OnGround = maybeIntersection.HasValue;
            
            if (OnGround)
                ApplyGroundPhysics(dTime, currentSegment, maybeIntersection.GetValueOrDefault());

            position += velocity * dTime;
            
            Rotation += angularVelocity * dTime;
            if (Rotation > Math.PI * 2) Rotation -= (float)Math.PI * 2;
            if (Rotation < -Math.PI * 2) Rotation += (float)Math.PI * 2;
        }

        public void Accelerate(float dTime)
        {
            isAccelerating = true;
            acceleration = dTime*MAX_SPEED;
        }

        public void Brake(float dTime)
        {
            COF = MAX_FRICTION;
        }

        public void StopAcceleration()
        {
            isAccelerating = false;
        }

        public void ReleaseBrake()
        {
            COF = MIN_FRICTION;
        }

        public void SetPosition(Vector2 position, float dTime)
        {
            //Add the velocity required to move to this new position
            //s = vt
            //v = s/t

            Vector2 s = position - this.position;
            velocity += s * dTime;

            this.position = position;
        }

        public void AddForce(Vector2 force)
        {
            velocity += force;
        }

        private void ApplyFriction(Vector2 slope, float normalForce, float dTime)
        {
            float currentVelocity = Vector2.Dot(slope, velocity);
            Vector2 tangentialVelocity = new Vector2(slope.X * currentVelocity, slope.Y * currentVelocity);

            velocity.X -= COF * tangentialVelocity.X;
            velocity.Y -= COF * tangentialVelocity.Y;
        }

        private void ApplyGroundPhysics(float dTime, SlopeSegment currentSegment, Vector2 intersection)
        {
            Vector2 slopeNormal = currentSegment.GetNormal();
            Vector2 slope = currentSegment.GetSlope();
            float normalForceLength = Vector2.Dot(velocity, slopeNormal);

            Vector2 normalForce = new Vector2(
                -slopeNormal.X * normalForceLength,
                -slopeNormal.Y * normalForceLength);

            //Adjust wheel to be exactly on the line
            AdjustWheelToLine(slopeNormal, intersection);

            //Add normal force!
            velocity += normalForce;

            if (isAccelerating)
                ApplyAcceleration(slope, dTime);

            ApplyFriction(slope, normalForceLength, dTime);
            ApplyAngularVelocity(slope);
        }

        private void ApplyAcceleration(Vector2 slope, float dTime)
        {
            float tangetialAcceleration = acceleration*Radius;

            velocity.X += slope.X * tangetialAcceleration;
            velocity.Y += slope.Y * tangetialAcceleration;
        }

        private void ApplyAngularVelocity(Vector2 slope)
        {
            float tangentialAcceleration = Vector2.Dot(velocity, slope);
            angularVelocity = (tangentialAcceleration / Radius);
        }

        private void ApplyGravity()
        {
            velocity.Y += GRAVITY;
        }

        private void AdjustWheelToLine(Vector2 normal, Vector2 intersection)
        {
            position = intersection + normal * Radius;
        }
    }
}