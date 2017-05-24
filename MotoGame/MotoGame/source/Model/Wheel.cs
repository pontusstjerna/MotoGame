using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.source.Model
{
    public class Wheel
    {
        public Vector2 Position {
            get
            {
                return position;
            }
        }

        public Vector2 Velocity
        {
            get
            {
                return velocity;
            }
        }

        public int Radius = 8;
        public float Bounciness { get; private set; } = 0.1f;
        public float Rotation { get; private set; }

        private const int MAX_SPEED = 10;
        private const int MAX_FRICTION = 1;
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

            //On ground
            if (maybeIntersection.HasValue)
                ApplyGroundPhysics(dTime, currentSegment, maybeIntersection.GetValueOrDefault());
            
            dTime /= 1000;

            position.X += velocity.X * dTime;
            position.Y += velocity.Y * dTime;
            
            Rotation += angularVelocity * dTime;
            if (Rotation > Math.PI * 2) Rotation -= (float)Math.PI * 2;
            if (Rotation < -Math.PI * 2) Rotation += (float)Math.PI * 2;
        }

        public void Accelerate(float dTime)
        {
            isAccelerating = true;
            acceleration = dTime/10;
        }

        public void Brake(float dTime)
        {
            if (COF < MAX_FRICTION)
                COF = 0.1f;
            else
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

        private void ApplyFriction(Vector2 slope, float normalForce, float dTime)
        {
            float currentVelocity = GetDotProduct(slope, velocity);
            Vector2 tangentialVelocity = new Vector2(slope.X * currentVelocity, slope.Y * currentVelocity);

            velocity.X -= COF * tangentialVelocity.X;
            velocity.Y -= COF * tangentialVelocity.Y;
        }

        private void ApplyGroundPhysics(float dTime, SlopeSegment currentSegment, Vector2 intersection)
        {
            Vector2 slopeNormal = currentSegment.GetNormal();
            Vector2 slope = currentSegment.GetSlope();
            float normalForceLength = GetDotProduct(velocity, slopeNormal);

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
            float tangentialAcceleration = GetDotProduct(velocity, slope);
            angularVelocity = (tangentialAcceleration / Radius);
        }

        private void ApplyGravity()
        {
            velocity.Y += 9.81f;
        }

        private void AdjustWheelToLine(Vector2 normal, Vector2 intersection)
        {
            position.X = intersection.X + normal.X * Radius;
            position.Y = intersection.Y + normal.Y * Radius;
        }

        private float GetDotProduct(Vector2 a, Vector2 b)
        {
            return a.X * b.X + a.Y * b.Y;
        }

        private double GetDistance(Vector2 a, Vector2 b)
        {
            return Math.Sqrt((b.X - a.X) * (b.X - a.X) + (b.Y - a.Y) * (b.Y - a.Y));
        }
    }
}
