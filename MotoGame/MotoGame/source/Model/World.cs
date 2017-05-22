﻿using Microsoft.Xna.Framework;
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
            points.Add(new Point(800, 100));

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
            Vector2? closestIntersect = null;
            SlopeSegment closestSegment = null;
            foreach(SlopeSegment segment in Segments)
            {
                Vector2? intersection = segment.GetIntersection(Bike.rearWheel);
                if (intersection.HasValue)
                {
                    if (closestIntersect.HasValue)
                    {
                        if(closestIntersect.GetValueOrDefault())
                    }
                    closestIntersect = intersection;
                    closestSegment = segment;

                    
                    return;
                }
            }

            if(closestIntersect.HasValue)
                wheel.Update(dTime, closestSegment, closestIntersect.GetValueOrDefault());
            else
                wheel.Update(dTime, false);
        }

        private void GetDistance(Vector2 a, Vector2 b)
        {
            return Math.Sqrt();
        }
    }
}
