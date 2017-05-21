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

        public float radius = 4;
        public float friction { get; private set; } = 0.1f;

        private Vector2 position;
        private Vector2 velocity;

        public Wheel(Point position)
        {
            this.position = position.ToVector2();
            velocity = new Vector2(0, 0);
        }

        public void Update(float dTime)
        {
            dTime /= 1000;

            position.X += velocity.X * dTime;
            position.Y += velocity.Y * dTime;

            ApplyGravity();
        }

        public void SetVX(float vx)
        {
            velocity.X = vx;
        }

        public void SetVY(float vy)
        {
            velocity.Y = vy;
        }
        
        private void ApplyGravity()
        {
            velocity.Y += 9.81f;
        }
    }
}
