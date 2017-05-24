using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.source.Model
{
    public class SlopeSegment
    {
        public Point Start { get; private set; }
        public Point End { get; private set; }

        public SlopeSegment(Point start, Point end)
        {
            Start = start;
            End = end;
        }

        public Vector2 GetSlope()
        {
            Vector2 slope = new Vector2(End.X - Start.X, End.Y - Start.Y);
            slope.Normalize();

            return slope;
        }

        public Vector2 GetNormal()
        {
            Vector2 slope = GetSlope();
            return new Vector2(slope.Y, -slope.X);
        }

        public Vector2? GetIntersection(Wheel wheel)
        {
            Vector2 normal = GetNormal();
            Vector2 slope = GetSlope();

            //A vector is wheel, b is slope
            float p0_x = wheel.Position.X + normal.X * wheel.Radius;
            float p0_y = wheel.Position.Y + normal.Y * wheel.Radius;
            float p1_x = wheel.Position.X - normal.X * wheel.Radius;
            float p1_y = wheel.Position.Y - normal.Y * wheel.Radius;
            float p2_x = Start.X;
            float p2_y = Start.Y;
            float p3_x = End.X;
            float p3_y = End.Y;

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
