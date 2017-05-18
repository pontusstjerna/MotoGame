using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using MotoGame.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.View
{
    public class WorldRenderer
    {
        private World world;
        private Texture2D lineTexture;

        public WorldRenderer(World world, GraphicsDevice gd)
        {
            this.world = world;
            lineTexture = new Texture2D(gd, 1, 1);
            lineTexture.SetData<Color>(new Color[] { Color.Black });
        }

        public void RenderWorld(SpriteBatch sb)
        {
            sb.Begin();
            List<Point> points = world.Points;
            for(int i = 0; i < points.Count() - 1; i++)
            {
                DrawLine(sb, points[i], points[i+1]);
            }
            sb.End();
        }

       private void DrawLine(SpriteBatch sb, Point start, Point end)
        {
            Vector2 edge = end.ToVector2() - start.ToVector2();
            float angle = (float)Math.Atan2(edge.Y, edge.X);

            sb.Draw(lineTexture,
                new Rectangle(start.X, start.Y, (int)edge.Length(), 1),
                null, Color.Black, angle, new Vector2(0, 0), SpriteEffects.None, 0);
        }
    }
}
