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

            Bike = new Bike(new Point(75,50));

            random = new Random();
        }

        public void Update(float dTime)
        {
            if(Bike.rearWheel.Position.X > Segments[Segments.Count - 2].Start.X)
            {
                GenerateSlopeSegment();
            }
            Bike.Update(dTime);
            UpdateWheel(dTime, Bike.rearWheel);
            UpdateWheel(dTime, Bike.frontWheel);
            

        }

        private void UpdateWheel(float dTime, Wheel wheel)
        {
            wheel.Update(dTime, GetCurrentSegment(wheel));
        }

        private SlopeSegment GetCurrentSegment(Wheel wheel)
        {
            return Segments.Where(seg => seg.Start.X <= wheel.Position.X && seg.End.X > wheel.Position.X).FirstOrDefault();
        }

        private void GenerateSlopeSegment()
        {
            points.Add(new Point(points.Last().X + 500, random.Next(425) + 325));
            Segments.Add(new SlopeSegment(points[points.Count() - 2], points.Last()));
        }
    }
}
