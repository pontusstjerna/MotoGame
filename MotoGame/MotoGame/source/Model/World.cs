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

        public Bike Bike { get; private set; } 

        public World()
        {
            points = new List<Point>();
            points.Add(new Point(10, 100));
            points.Add(new Point(50, 100));
            points.Add(new Point(100, 200));
            points.Add(new Point(500, 300));
            points.Add(new Point(600, 300));

            Bike = new Bike(new Point(50,50));
        }

        public void Update(float dTime)
        {
            Collide(dTime);
            Bike.Update(dTime);
        }

        private void Collide(float dTime)
        {
            for(int i = 0; i < points.Count() - 1; i++)
            {
                Vector2? intersection = GetIntersection(Bike.rearWheel, points[i], points[i + 1]);
                if (intersection != null)
                {
                    Bike.rearWheel.SetOnGround(intersection.GetValueOrDefault());
                    Bike.rearWheel.Bounce(points[i], points[i+1], intersection.GetValueOrDefault())
                    return;
                }
            }
        }
        
        private Vector2? GetIntersection(Wheel wheel, Point a, Point b)
        {

            Vector2 wheelDir = wheel.Velocity;
            wheelDir.Normalize();

            //A vector is wheel, b is slope
            float p0_x = wheel.Position.X;
            float p0_y = wheel.Position.Y;
            float p1_x = wheel.Position.X + wheelDir.X * wheel.Radius;
            float p1_y = wheel.Position.Y + wheelDir.Y * wheel.Radius;
            float p2_x = a.X;
            float p2_y = a.Y;
            float p3_x = b.X;
            float p3_y = b.Y;

            float s1_x = p1_x - p0_x;
            float s2_x = p3_x - p2_x;
            float s1_y = p1_y - p0_y;
            float s2_y = p3_y - p2_y;

            float s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
            float t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

            if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
            {
                return new Vector2(p0_x + (t * s1_x), p0_y + (t * s1_y));
            }
            else return null;
        }
        
    }
}
