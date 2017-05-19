﻿using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using MotoGame.Model;
using MotoGame.source.Model;
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
        private Texture2D wheelTexture;

        public WorldRenderer(World world, GraphicsDevice gd, Texture2D wheel)
        {
            this.world = world;

            lineTexture = new Texture2D(gd, 1, 1);
            lineTexture.SetData<Color>(new Color[] { Color.Black });

            wheelTexture = wheel;
        }

        public void Render(SpriteBatch sb)
        {
            RenderWorld(sb);
            RenderBike(sb);
        }

        private void RenderWorld(SpriteBatch sb)
        {
            sb.Begin();
            List<Point> points = world.Points;
            for(int i = 0; i < points.Count() - 1; i++)
            {
                DrawLine(sb, points[i], points[i+1]);
            }
            sb.End();
        }

        private void RenderBike(SpriteBatch sb)
        {
            sb.Begin();
            DrawBike(sb, world.Bike);
            sb.End();
        }

        private void DrawBike(SpriteBatch sb, Bike bike)
        {
            DrawWheel(sb, bike.rearWheel);
        }

        private void DrawWheel(SpriteBatch sb, Wheel wheel)
        {
            sb.Draw(wheelTexture, wheel.Position, Color.White);
            //sb.Draw(wheelTexture, new Rectangle(wheel.Position.ToPoint(), new Point(16, 16)), Color.White);
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
