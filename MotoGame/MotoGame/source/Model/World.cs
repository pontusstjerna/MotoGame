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

            Bike.Update(dTime);
        }

        private void Collide(float dTime)
        {
            for(int i = 0; i < points.Count() + 1; i++)
            {
                if(GetIntersection(Bike.rearWheel, points[i], points[i + 1]))
                {
                    //On ground
                    return;
                }
            }
        }

        private bool GetIntersection(Wheel wheel, Point a, Point b)
        {

            //A vector is wheel, b is slope
            float p0_x = wheel.Position.X;
            float p0_y = wheel.Position.Y;
            float p1_x = wheel.Position.X + wheel.radius;
            float p1_y = wheel.Position.X + wheel.radius;
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
            return (s >= 0 && s <= 1 && t >= 0 && t <= 1);
        }
        
    }

    /*
     * private Point2D.Double getIntersection(Vector2D a, Vector2D b, Point2D.Double aStart, Point2D.Double bStart) {
        //Algorithm from http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect

        double p0_x = aStart.getX();
        double p0_y = aStart.getY();
        double p1_x = aStart.getX() + a.getX();
        double p1_y = aStart.getY() + a.getY();
        double p2_x = bStart.getX();
        double p2_y = bStart.getY();
        double p3_x = bStart.getX() + b.getX();
        double p3_y = bStart.getY() + b.getY();

        double s1_x = p1_x - p0_x;
        double s2_x = p3_x - p2_x;
        double s1_y = p1_y - p0_y;
        double s2_y = p3_y - p2_y;

        double s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        double t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            return new Point2D.Double(p0_x + (t * s1_x), p0_y + (t * s1_y));
        } else return null;
    }*/
}
