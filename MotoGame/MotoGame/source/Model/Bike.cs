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

        public Bike(Point startPos)
        {
            rearWheel = new Wheel(startPos);
        }

        public void Update(float dTime)
        {
            //rearWheel.Update(dTime);
            //frontWheel.Update(dTime);
        }
    }
}
