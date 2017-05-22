using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.source.Model
{
    public class Wheel
    {
        public Vector2 Position {
            get
            {
                return position;
            }
        }

        public Vector2 Velocity
        {
            get
            {
                return velocity;
            }
        }

        public float Radius = 8;
        public float Bounciness { get; private set; } = 0.1f;
        public bool OnGround { get; set; } = false;

        private Vector2 position;
        private Vector2 velocity;

        public Wheel(Point position)
        {
            this.position = position.ToVector2();
            velocity = new Vector2(0, 0);
        }

        public void Update(float dTime)
        {
            dTime /= 1000;

            position.X += velocity.X * dTime;
            position.Y += velocity.Y * dTime;

            if(!OnGround)
                ApplyGravity();
        }

        public void SetOnGround(Vector2 groundBelow)
        {
            velocity.Y = 0;
            position.Y = groundBelow.Y - Radius;
            OnGround = true;
        }

        public void Bounce(Point a, Point b, Vector2 groundBelow)
        {
            Vector2 lineSegment = new Vector2(b.X - a.X, b.Y - a.Y);

            //To get this, take the orthogonal vector of the line segment
            Vector2 collisionVector = new Vector2(-lineSegment.Y, lineSegment.X);

            Vector2 wheelDir;

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

        private void ApplyGravity()
        {
            velocity.Y += 9.81f;
        }
    }
}
