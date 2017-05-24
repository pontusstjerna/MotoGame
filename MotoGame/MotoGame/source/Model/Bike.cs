using Microsoft.Xna.Framework;
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

            //Vector2 currentOffset = frontWheel.Position - rearWheel.Position;
            //Vector2 normalForce = (rearToFront - currentOffset)*1000;

            Vector2 currentOffset = frontWheel.Position - Position;
            Vector2 normalForce = (FrontWheelOffset - currentOffset) * 10;

            frontWheel.AddStaticForce(normalForce);
            Rotation = (float)Math.Atan2(rearToFront.Y, rearToFront.X);
        }
    }
}
