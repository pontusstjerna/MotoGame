using Microsoft.Xna.Framework.Input;
using InfiniteMoto.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Xna.Framework.Input.Touch;

namespace InfiniteMoto.Controller
{
    public abstract class PlayerController
    {
        protected World world;
        protected SoundController soundController;

        protected int width, height;
        protected float Scale;

        public void Initialize(World world, SoundController soundController, float scale, int width, int height)
        {
            this.world = world;
            this.soundController = soundController;
            this.width = width;
            this.height = height;
            Scale = scale;
        }

        public abstract void Update(float dTime);

        

        
    }
}
