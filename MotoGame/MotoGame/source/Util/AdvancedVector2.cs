using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.source.Util
{
    public class AdvancedVector2
    {
        public float X
        {
            get { return vector.X; }
            set { vector.X = value; }
        }

        public float Y
        {
            get { return vector.Y; }
            set { vector.Y = value; }
        }
        
        private Vector2 vector;

        public AdvancedVector2(float x, float y)
        {
            vector = new Vector2(x, y);
        }

        public float Length()
        {
            return vector.Length();
        }

        public float LengthSquared()
        {
            return vector.LengthSquared();
        }

        public AdvancedVector2 Normalize()
        {
            vector.Normalize();
            return this;
        }

        public Point ToPoint()
        {
            return vector.ToPoint();
        }

        public AdvancedVector2 Orthogonal()
        {
            return new AdvancedVector2 (Y, -X);
        }

        //EXTENDED FUNCTIONALITY
        public static float Dot(AdvancedVector2 a, AdvancedVector2 b)
        {
            return a.X * b.X + a.Y * b.Y;
        }
    }
}
