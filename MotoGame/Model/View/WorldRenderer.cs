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
    public class WorldRenderer : BaseRenderer
    {   
        private Texture2D lineTexture;
        private Texture2D wheelTexture;
        private Texture2D bikeTexture;

        private float extraY = 0;

        public WorldRenderer(World world, GraphicsDevice gd, Texture2D wheel, Texture2D bike) : base(world, gd)
        {
            lineTexture = new Texture2D(gd, 1, 1);
            lineTexture.SetData<Color>(new Color[] { Color.DarkOrange });

            wheelTexture = wheel;
            bikeTexture = bike;
        }

        public new void Render(SpriteBatch sb)
        {
            RenderWorld(sb);
            RenderBike(sb);

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

        
       
        private void DrawBike(SpriteBatch sb, Bike bike)
        {
            Vector2 bikeRenderPos = new Vector2(width / 2, (bike.Position.Y + extraY)*Scale);

            sb.Draw(
                bikeTexture,
                bikeRenderPos,
                null,
                Color.White,
                bike.Rotation,
                bike.CenterOfGravity,
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

       
    }
}
