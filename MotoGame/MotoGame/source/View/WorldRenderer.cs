using Microsoft.Xna.Framework;
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
        private Texture2D bikeTexture;
        private SpriteFont mainFont;

        private int width, height;

        public WorldRenderer(World world, GraphicsDevice gd, Texture2D wheel, Texture2D bike, SpriteFont mainFont)
        {
            this.world = world;

            lineTexture = new Texture2D(gd, 1, 1);
            lineTexture.SetData<Color>(new Color[] { Color.DarkOrange });

            wheelTexture = wheel;
            bikeTexture = bike;
            this.mainFont = mainFont;

            width = gd.Viewport.Width;
            height = gd.Viewport.Height;
        }

        public void Render(SpriteBatch sb)
        {
            RenderWorld(sb);
            RenderBike(sb);
            RenderScore(sb);
        }

        private void RenderWorld(SpriteBatch sb)
        {
            sb.Begin();
            foreach(SlopeSegment segment in world.Segments)
            {
                DrawLine(sb, segment.Start, segment.End, world.Bike);
            }
            sb.End();
        }

        private void RenderBike(SpriteBatch sb)
        {
            sb.Begin();
            DrawBike(sb, world.Bike);
            sb.End();
        }

        private void RenderScore(SpriteBatch sb)
        {
            sb.Begin();
            DrawRect(sb, 10, 10, 140, 30);
            int score = (int)world.Bike.Position.X / 100;
            string scoreWithZeros = (1000000 + score).ToString().Substring(1);
            sb.DrawString(mainFont, "Score: " + scoreWithZeros, new Vector2(15, 15), Color.Black);
            sb.End();
        }

        private void DrawBike(SpriteBatch sb, Bike bike)
        {
            Vector2 bikeRenderPos = new Vector2(width / 2, bike.Position.Y);

            sb.Draw(
                bikeTexture,
                bikeRenderPos + bike.RearWheelOffset,
                null,
                Color.White,
                bike.Rotation,
                new Vector2(3,24),
                1,
                SpriteEffects.None,
                0);
            DrawWheel(sb, bike.rearWheel, bike.Position);
            DrawWheel(sb, bike.frontWheel, bike.Position);
        }

        private void DrawWheel(SpriteBatch sb, Wheel wheel, Vector2 bike)
        {
            sb.Draw(
                wheelTexture,
                new Vector2(wheel.Position.X - bike.X + width/2,wheel.Position.Y),
                null,
                Color.White,
                wheel.Rotation,
                new Vector2(wheel.Radius, wheel.Radius),
                1,
                SpriteEffects.None,
                0);
        }

       private void DrawLine(SpriteBatch sb, Point start, Point end, Bike player)
       {
            Vector2 edge = end.ToVector2() - start.ToVector2();
            float angle = (float)Math.Atan2(edge.Y, edge.X);

            sb.Draw(lineTexture,
                new Rectangle(
                    start.X - player.Position.ToPoint().X + width/2, 
                    start.Y, 
                    (int)edge.Length(), 3),
                null, Color.DarkOrange, angle, new Vector2(0, 0), SpriteEffects.None, 0);
       }

       private void DrawRect(SpriteBatch sb, int x, int y, int width, int height)
       {
            sb.Draw(lineTexture, new Rectangle(x, y, width, height), null, Color.DarkOrange);
       }
    }
}
