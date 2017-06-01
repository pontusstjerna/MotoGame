using InfiniteMoto.Model;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Text;

namespace InfiniteMoto.View
{
    public class GUIRenderer : BaseRenderer
    {
        public bool Paused { get; set; } = false;

        private SpriteFont mainFont;
        private Texture2D rectTexture;
        private Texture2D gameOver;
        

        public GUIRenderer(World world, SpriteFont mainFont, Texture2D gameOver, GraphicsDevice gd) : base(world, gd)
        {
            this.world = world;
            this.mainFont = mainFont;
            rectTexture = new Texture2D(gd, 1, 1);
            rectTexture.SetData<Color>(new Color[] { Color.DarkOrange });
            this.gameOver = gameOver;

            world.GameOverEventHandler += (s, e) => Paused = !Paused;
        }

        public void Render(SpriteBatch sb, float dTime)
        {
            RenderScore(sb);
            ShowFps(sb, dTime);

            if (Paused) ShowGameOver(sb);
        }

        private void RenderScore(SpriteBatch sb)
        {
            sb.Begin();
            DrawRect(sb, (int)(10 * Scale), (int)(10 * Scale), (int)(Scale * 140), (int)(Scale * 30));
            int score = (int)world.Bike.Position.X / 100;
            string scoreWithZeros = (1000000 + score).ToString().Substring(1);
            sb.DrawString(
                mainFont,
                "Score: " + scoreWithZeros,
                new Vector2(15, 15) * Scale,
                Color.Black,
                0,
                Vector2.Zero,
                Scale,
                SpriteEffects.None,
                0);
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
                Color.White,
                0,
                new Vector2(gameOver.Width*0.5f, gameOver.Height * 0.5f),
                Scale,
                SpriteEffects.None,
                0);
            sb.End();
        }

        private void DrawText(string text, int x, int y)
        {

        }

    }
}
