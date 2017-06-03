using InfiniteMoto.Model;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Text;

namespace InfiniteMoto.View
{
    public class GUIRenderer : BaseRenderer
    {
        private SpriteFont mainFont;
        private Texture2D rectTexture;
        private Texture2D gameOver;
        private Texture2D paused;
        private Texture2D play;

        public GUIRenderer(World world, ContentManager content, GraphicsDevice gd) : base(world, gd)
        {
            this.world = world;
            LoadContent(content);
            rectTexture = new Texture2D(gd, 1, 1);
            rectTexture.SetData<Color>(new Color[] { Color.DarkOrange });
        }

        public void Render(SpriteBatch sb, float dTime)
        {
            RenderScore(sb);
            ShowFps(sb, dTime);

            if (world.IsGameOver) ShowGameOver(sb);
            if (!world.IsPaused) DrawMenuControl(sb, paused);
            else DrawMenuControl(sb, play);
        }

        private void LoadContent(ContentManager content)
        {
            mainFont = content.Load<SpriteFont>("data/Main");
            gameOver = content.Load<Texture2D>("data/gameover");
            paused = content.Load<Texture2D>("data/pause");
            play = content.Load<Texture2D>("data/play");
        }

        private void RenderScore(SpriteBatch sb)
        {
            sb.Begin();
            DrawRect(sb, (int)(10 * Scale), (int)(10 * Scale), (int)(Scale * 140), (int)(Scale * 30));
            DrawText("Score: " + GetScoreString(world.Bike.Position.X), 15*Scale, 15*Scale, sb);
            sb.End();
        }

        private void DrawRect(SpriteBatch sb, int x, int y, int width, int height)
        {
            sb.Draw(rectTexture, new Rectangle(x, y, width, height), null, Color.White);
        }

        private void ShowFps(SpriteBatch sb, float dTime)
        {
            sb.Begin();
            sb.DrawString(mainFont, "FPS: " + (int)Math.Round(1000 / (dTime * 1000), 0), new Vector2(15, 50) * Scale, Color.White);
            sb.End();
        }

        private void ShowGameOver(SpriteBatch sb)
        {
            sb.Begin();
            sb.Draw(
                gameOver,
                new Vector2((width / 2), (height / 2)),
                null,
                new Color(255,255,255,180),
                0,
                new Vector2(gameOver.Width * 0.5f, gameOver.Height * 0.5f),
                Scale,
                SpriteEffects.None,
                0);          

            DrawText("Your score: " + GetScoreString(world.Bike.Position.X), width / 2 - 90*Scale, height / 2, sb);
            
            sb.End();
        }

        private void DrawText(string text, float x, float y, SpriteBatch sb)
        {
            sb.DrawString(
               mainFont,
               text,
               new Vector2(x,y),
               Color.Black,
               0,
               Vector2.Zero,
               Scale,
               SpriteEffects.None,
               0);
        }

        private void DrawMenuControl(SpriteBatch sb, Texture2D texture)
        {
            sb.Begin();
            sb.Draw(
                texture,
                new Vector2(width - width * 0.05f, 15 * Scale),
                null,
                Color.White,
                0,
                Vector2.Zero,
                Scale,
                SpriteEffects.None,
                0);
            sb.End();
        }

        private string GetScoreString(float xPosition)
        {
            int score = (int)xPosition / 100;
            return (1000000 + score).ToString().Substring(1); 
        }
    }
}
