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

        private Vector2 position;

        public Bike(Point startPos)
        {
            position = startPos.ToVector2();
            rearWheel = new Wheel(startPos);
        }

        public void Update(float dTime)
        {
            //rearWheel.Update(dTime);
            //frontWheel.Update(dTime);
        }
    }
}
