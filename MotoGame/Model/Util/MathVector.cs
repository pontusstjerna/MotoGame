using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InfiniteMoto.Util
{
    public static class MathVector
    {
        public static float Dot(Vector2 a, Vector2 b)
        {
            return a.X * b.X + a.Y * b.Y;
        }

        public static float GetDistance(Vector2 a, Vector2 b)
        {
            return (a - b).Length();
        }

        public static Vector2 GetOrthogonal(Vector2 vector)
        {
            return new Vector2(vector.Y, -vector.X);
        }
    }
}
