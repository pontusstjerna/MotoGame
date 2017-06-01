using InfiniteMoto.Model;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Text;

namespace InfiniteMoto.View
{
    public abstract class BaseRenderer
    {
        protected World world;
        protected float Scale { get; set; }
        protected int width, height;

        public BaseRenderer(World world, GraphicsDevice gd)
        {
            this.world = world;
            
            width = gd.Viewport.Width;
            height = gd.Viewport.Height;
            Scale = (float)height / World.HEIGHT;
        }

        public void Render(SpriteBatch sb)
        {

        }
    }
}
