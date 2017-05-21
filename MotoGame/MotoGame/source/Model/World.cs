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
                if(GetIntersection(Bike.rearWheel, points[i], points[i + 1]))
                {
                    //On ground
                    Bike.rearWheel.SetVY(0);
                    return;
                }
            }
        }

        private void Bounce(Wheel wheel, Point a, Point b)
        {
            Vector2 lineSegment = new Vector2(b.X - a.X, b.Y - a.Y);
            
            //To get this, take the orthogonal vector of the line segment
            Vector2 collisionVector = new Vector2(-lineSegment.Y, lineSegment.X);

            Vector2 wheelDir;

            /*
             * TODO:: PUT THIS METHOD IN THE WHEEL CLASS INSTEAD
            */

            /*
             * var collisionVector = new Vector2D(ball2.x - ball1.x, ball2.y - ball1.y);
                    var orthoCollisionVector = new Vector2D(-collisionVector.y, collisionVector.x);

                    var colDir = collisionVector.unitVector();
                    var unaffectDir = orthoCollisionVector.unitVector();

                    var ball1Dir = new Vector2D(ball1.vx, ball1.vy);
                    var ball2Dir = new Vector2D(ball2.vx, ball2.vy);

                    var u1 = dot(ball1Dir,colDir);
                    var u2 = dot(ball2Dir,colDir);

                    var unaffected1 = unaffectDir.getScalar(dot(ball1Dir, unaffectDir));
                    var unaffected2 = unaffectDir.getScalar(dot(ball2Dir, unaffectDir));

                    audio.pause();
                    audio.currentTime = 0;
                    audio.play();
                    
                    var m1 = ball1.mass;
                    var m2 = ball2.mass;
                    var I = m1*u1 + m2*u2;
                    var R = -(u2-u1);
                    

            var v2 = (I + m1 * R) / (m1 + m2);
            var v1 = v2 - R;

            ball1.vx = (colDir.x * v1 + unaffected1.x) * (1 - friction);
            ball1.vy = (colDir.y * v1 + unaffected1.y) * (1 - friction);
            ball2.vx = (colDir.x * v2 + unaffected2.x) * (1 - friction);
            ball2.vy = (colDir.y * v2 + unaffected2.y) * (1 - friction);

            p("ballsTotVel before: " + (ball1Dir.length + ball2Dir.length) + " After: " + (new Vector2D(ball1.vx, ball1.vy).length + new Vector2D(ball2.vx, ball2.vy).length));
            * */
        }

        private bool GetIntersection(Wheel wheel, Point a, Point b)
        {

            //A vector is wheel, b is slope
            float p0_x = wheel.Position.X;
            float p0_y = wheel.Position.Y;
            float p1_x = wheel.Position.X + wheel.radius;
            float p1_y = wheel.Position.Y + wheel.radius;
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
}
