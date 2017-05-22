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
        public List<SlopeSegment> Segments { get; private set; }

        public Bike Bike { get; private set; } 

        public World()
        {
            List<Point> points = new List<Point>();
            points.Add(new Point(10, 100));
            points.Add(new Point(50, 100));
            points.Add(new Point(100, 200));
            points.Add(new Point(500, 300));
            points.Add(new Point(600, 300));

            Segments = new List<SlopeSegment>();
            for (int i = 0; i < points.Count() - 1; i++)
                Segments.Add(new SlopeSegment(points[i], points[i + 1]));

            Bike = new Bike(new Point(75,50));
        }

        public void Update(float dTime)
        {
            UpdateWheel(dTime, Bike.rearWheel);
            //Bike.Update(dTime);

        }

        private void UpdateWheel(float dTime, Wheel wheel)
        {
            foreach(SlopeSegment segment in Segments)
            {
                Vector2? intersection = segment.GetIntersection(Bike.rearWheel);
                if (intersection.HasValue)
                {
                    wheel.Update(dTime, segment, intersection.GetValueOrDefault());
                    return;
                }
            }

            wheel.Update(dTime, false);
        }
    }
}
