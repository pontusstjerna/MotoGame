using Microsoft.Xna.Framework;
using MotoGame.source.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.Model
{
    public class World
    {
        private List<Point> points;
        public List<Point> Points
        {
            get
            {
                return points;
            }
        }

        public Wheel test { get; private set; } 

        public World()
        {
            points = new List<Point>();
            points.Add(new Point(10, 100));
            points.Add(new Point(50, 100));
            points.Add(new Point(100, 200));
            points.Add(new Point(500, 300));
            points.Add(new Point(600, 300));

            test = new Wheel(new Point(50,50));
        }

        public void Update(float dTime)
        {
            test.Update(dTime);
        }
        
    }
}
