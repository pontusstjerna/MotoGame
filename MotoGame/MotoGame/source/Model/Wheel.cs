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
        public Point Position
        {
            get
            {
                return position.ToPoint();
            }
        }

        private Vector2 velocity;
        private Vector2 position;

        public Wheel(Point position)
        {
            this.position = position.ToVector2();
        }

        public void Update(float dTime)
        {
            ApplyGravity(dTime);

            position.X += velocity.X * dTime;
            position.Y += velocity.Y * dTime;
        }
        
        private void ApplyGravity(float dTime)
        {
            velocity.Y += 9.81f * dTime;
        }
    }
}
