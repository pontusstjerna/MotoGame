using Microsoft.Xna.Framework.Input;
using MotoGame.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.source.Controller
{
    public class PlayerController
    {
        private World world;
        private SoundController soundController;

        private bool willReset = false;

        public PlayerController(World world, SoundController soundController)
        {
            this.world = world;
            this.soundController = soundController;
        }

        public void Update(float dTime)
        {
            if (Keyboard.GetState().IsKeyDown(Keys.Up))
            {
                world.Bike.RearWheel.Accelerate(dTime);
                soundController.Accelerate();
            }
            else if (Keyboard.GetState().IsKeyUp(Keys.Up))
                world.Bike.RearWheel.StopAcceleration();

            if (Keyboard.GetState().IsKeyDown(Keys.Down))
                world.Bike.RearWheel.Brake(dTime);
            else if (Keyboard.GetState().IsKeyUp(Keys.Down))
                world.Bike.RearWheel.ReleaseBrake();

            if (Keyboard.GetState().IsKeyDown(Keys.Space))
                willReset = true;

            if (Keyboard.GetState().IsKeyUp(Keys.Space) && willReset)
            {
                world.Reset();
                willReset = false;
            }
                
        }
    }
}
