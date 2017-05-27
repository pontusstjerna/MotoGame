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

        private Vector2 rearToFront = new Vector2(37, -1);

        public Bike(Point startPos)
        {
            position = startPos.ToVector2();
            rearWheel = new Wheel(startPos + RearWheelOffset.ToPoint());
            frontWheel = new Wheel(startPos + FrontWheelOffset.ToPoint());
        }

        public void Update(float dTime)
        {
            position = rearWheel.Position - RearWheelOffset;

            float restLength = rearToFront.Length();
            Vector2 delta = frontWheel.Position - rearWheel.Position;
            float deltaLength = delta.Length();
            float diff = (deltaLength - restLength) / deltaLength;
            rearWheel.SetPosition(rearWheel.Position + delta * 0.5f * diff);
            frontWheel.SetPosition(frontWheel.Position - delta * 0.5f * diff);

            Rotation = (float)Math.Atan2(delta.Y, delta.X);
        }

        private float GetDotProduct(Vector2 a, Vector2 b)
        {
            return a.X * b.X + a.Y * b.Y;
        }
    }
}
