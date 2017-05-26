﻿using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.source.Model
{
    public class Bike
    {
        public Wheel rearWheel { get; private set; }
        public Wheel frontWheel { get; private set; }
        public Vector2 Position
        {
            get
            {
                return position;
            }
        }
        public float Rotation { get; private set; } = 0;

        public Vector2 CenterOfGravity = new Vector2(19, 13);
        public Vector2 RearWheelOffset { get; private set; } = new Vector2(-17, 12);
        public Vector2 FrontWheelOffset { get; private set; } = new Vector2(20, 11);

        private Vector2 position;
        private Vector2 velocity;

        private Vector2 rearToFront = new Vector2(35, -1);

        public Bike(Point startPos)
        {
            position = startPos.ToVector2();
            rearWheel = new Wheel(startPos + RearWheelOffset.ToPoint());
            frontWheel = new Wheel(startPos + FrontWheelOffset.ToPoint());
        }

        public void Update(float dTime)
        {
            position = rearWheel.Position - RearWheelOffset;
            rearToFront = new Vector2(rearToFront.Length() * (float)Math.Cos(Rotation), rearToFront.Length() * (float)Math.Sin(Rotation));

            
            //Vector2 normalForce = (rearToFront - currentOffset)*1000;

            Vector2 actualRearToFront = frontWheel.Position - rearWheel.Position;
            Rotation = (float)Math.Atan2(actualRearToFront.Y, actualRearToFront.X);

            //Vector2 currentOffset = frontWheel.Position - Position;
            //Vector2 normalForce = (FrontWheelOffset - currentOffset) * 10;

            Vector2 currentOffset = frontWheel.Position - rearWheel.Position;

            //Inverted because other wheel
            Vector2 rearNorm = (currentOffset - rearToFront);
            rearWheel.AddStaticForce(rearNorm);

            Vector2 frontNorm = (rearToFront - currentOffset);
            frontWheel.AddStaticForce(frontNorm);
        }
    }
}
