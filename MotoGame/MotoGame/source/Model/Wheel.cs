using Microsoft.Xna.Framework;
using MotoGame.source.Util;
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

        public int Radius = 8;
        public float Rotation { get; private set; }
        public const float GRAVITY = 5f;

        private const int MAX_SPEED = 100; //This does not have any unit :(
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

            //On ground
            if (maybeIntersection.HasValue)
                ApplyGroundPhysics(dTime, currentSegment, maybeIntersection.GetValueOrDefault());
            
            dTime /= 1000;

            position += velocity * dTime;
            
            Rotation += angularVelocity * dTime;
            if (Rotation > Math.PI * 2) Rotation -= (float)Math.PI * 2;
            if (Rotation < -Math.PI * 2) Rotation += (float)Math.PI * 2;
        }

        public void Accelerate(float dTime)
        {
            isAccelerating = true;
            acceleration = dTime*MAX_SPEED/1000;
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

        private void ApplyFriction(Vector2 slope, float normalForce, float dTime)
        {
            float currentVelocity = MathVector.Dot(slope, velocity);
            Vector2 tangentialVelocity = new Vector2(slope.X * currentVelocity, slope.Y * currentVelocity);

            velocity.X -= COF * tangentialVelocity.X;
            velocity.Y -= COF * tangentialVelocity.Y;
        }

        private void ApplyGroundPhysics(float dTime, SlopeSegment currentSegment, Vector2 intersection)
        {
            Vector2 slopeNormal = currentSegment.GetNormal();
            Vector2 slope = currentSegment.GetSlope();
            float normalForceLength = MathVector.Dot(velocity, slopeNormal);

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
            float tangentialAcceleration = MathVector.Dot(velocity, slope);
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
