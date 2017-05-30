﻿using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InfiniteMoto.Model
{
    public class World
    {
        public const int HEIGHT = 300;

        public List<SlopeSegment> Segments { get; private set; }

        public Bike Bike { get; private set; }

        private Point startPosition = new Point(75, 50);
        private List<Point> points;
        private Random random;

        public World()
        {
            points = new List<Point>();
            points.Add(new Point(10, 100));
            points.Add(new Point(400, 110));
            points.Add(new Point(800, 111));

            Segments = new List<SlopeSegment>();
            for (int i = 0; i < points.Count() - 1; i++)
                Segments.Add(new SlopeSegment(points[i], points[i + 1]));

            Bike = new Bike(startPosition);

            random = new Random();
        }

        public void Update(float dTime)
        {
            if(Bike.FrontWheel.Position.X > Segments[Segments.Count - 2].Start.X)
            {
                GenerateSlopeSegment();
            }

            UpdateWheel(dTime, Bike.RearWheel);
            UpdateWheel(dTime, Bike.FrontWheel);
            Bike.Update(dTime);

            CheckDeath();
        }

        public void Reset()
        {
            Bike = new Bike(startPosition);
        }

        private void UpdateWheel(float dTime, Wheel wheel)
        {
            wheel.Update(dTime, GetCurrentSegment(wheel.Position));
        }

        private SlopeSegment GetCurrentSegment(Vector2 position)
        {
            return Segments.Where(seg => seg.Start.X <= position.X && seg.End.X > position.X).FirstOrDefault();
        }

        private void CheckDeath()
        {
            if (Bike.RearWheel.OnGround && Bike.FrontWheel.OnGround && Bike.FrontWheel.Position.X <= Bike.RearWheel.Position.X)
            {
                Reset();
            }
        }

        private void GenerateSlopeSegment()
        {
            int newLength = 500 - (int)Math.Round(Bike.Position.X / 100);
            points.Add(new Point(points.Last().X + Math.Max(newLength, 50), random.Next(HEIGHT*4/5) + HEIGHT/5));
            Segments.Add(new SlopeSegment(points[points.Count() - 2], points.Last()));
        }
    }
}
