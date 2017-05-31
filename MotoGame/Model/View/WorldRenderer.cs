using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using InfiniteMoto.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InfiniteMoto.View
{
    public class WorldRenderer
    {
        public float Scale { get; private set; } = 2;
        
        private World world;
        private Texture2D lineTexture;
        private Texture2D wheelTexture;
        private Texture2D bikeTexture;
        private SpriteFont mainFont;

        public bool showGameOver = false;
        private int width, height;
        private float extraY = 0;

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

            Scale = (float)height / World.HEIGHT;

            world.DeathEventHandler += (s, e) => showGameOver = true;
        }

        public void Render(SpriteBatch sb, float dTime)
        {
            RenderWorld(sb);
            RenderBike(sb);
            RenderScore(sb);
            ShowFps(sb, dTime);
         //   if (showGameOver) ShowGameOver(sb);

            if(world.Bike.Position.Y - 20 < 0)
            {
                extraY = -(world.Bike.Position.Y - 20);
            }
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
            DrawRect(sb, (int)(10*Scale), (int)(10*Scale), (int)(Scale*140), (int)(Scale*30));
            int score = (int)world.Bike.Position.X / 100;
            string scoreWithZeros = (1000000 + score).ToString().Substring(1);
            sb.DrawString(
                mainFont, 
                "Score: " + scoreWithZeros, 
                new Vector2(15, 15)*Scale, 
                Color.Black,
                0,
                Vector2.Zero,
                Scale,
                SpriteEffects.None,
                0);
            sb.End();
        }
       
        private void DrawBike(SpriteBatch sb, Bike bike)
        {
            Vector2 bikeRenderPos = new Vector2(width / 2, (bike.Position.Y + extraY)*Scale);

            sb.Draw(
                bikeTexture,
                bikeRenderPos + bike.RearWheelOffset*Scale,
                null,
                Color.White,
                bike.Rotation,
                new Vector2(3,24),
                Scale,
                SpriteEffects.None,
                0);
            DrawWheel(sb, bike.RearWheel, bike.Position);
            DrawWheel(sb, bike.FrontWheel, bike.Position);
        }

        private void DrawWheel(SpriteBatch sb, Wheel wheel, Vector2 bike)
        {
            sb.Draw(
                wheelTexture,
                new Vector2((wheel.Position.X - bike.X)*Scale + width/2, (wheel.Position.Y + extraY)*Scale),
                null,
                Color.White,
                wheel.Rotation,
                new Vector2(wheel.Radius, wheel.Radius),
                Scale,
                SpriteEffects.None,
                0);
        }

       private void DrawLine(SpriteBatch sb, Point start, Point end, Bike player)
       {
            Vector2 edge = end.ToVector2() - start.ToVector2();
            float angle = (float)Math.Atan2(edge.Y, edge.X);

            sb.Draw(lineTexture,
                new Rectangle(
                    (int)((start.X - player.Position.ToPoint().X)*Scale) + width/2, 
                    (int)(start.Y*Scale) + (int)(extraY*Scale), 
                    (int)(edge.Length()*Scale), (int)(3*Scale)),
                null, Color.DarkOrange, angle, new Vector2(0, 0), SpriteEffects.None, 0);
       }

       private void DrawRect(SpriteBatch sb, int x, int y, int width, int height)
       {
            sb.Draw(lineTexture, new Rectangle(x, y, width, height), null, Color.White);
       }

       private void ShowFps(SpriteBatch sb, float dTime)
       {
            sb.Begin();
            sb.DrawString(mainFont, "FPS: " + (int)Math.Round(1000/(dTime*1000), 0), new Vector2(15, 50)*Scale, Color.White);
            sb.End();
       }

       private void ShowGameOver(SpriteBatch sb)
       {
            sb.Begin();
            DrawRect(sb, 200, 200, 200, 200);
            sb.End();
       }
    }
}
