using InfiniteMoto.Model;
using Microsoft.Xna.Framework.Input;
using System;
using System.Collections.Generic;
using System.Text;

namespace InfiniteMoto.Controller
{
    public class KeyboardController : PlayerController
    {
        private KeyboardState oldState;

        public KeyboardController(World world, SoundController sound, int w, int h) : base(world, sound, w, h)
        {

        }

        public new void Update(float dTime)
        {
            CheckKeyboard(dTime);
        }

        private void CheckKeyboard(float dTime)
        {
            KeyboardState newState = Keyboard.GetState();

            if (newState.IsKeyDown(Keys.Up))
            {
                world.Bike.RearWheel.Accelerate(dTime);
                soundController.Accelerate();
            }
            else if (oldState.IsKeyDown(Keys.Up))
            {
                world.Bike.RearWheel.StopAcceleration();
                soundController.Idle();
            }

            if (newState.IsKeyDown(Keys.Down))
            {
                world.Bike.RearWheel.Brake(dTime);
            }
            else if (oldState.IsKeyDown(Keys.Down))
            {
                world.Bike.RearWheel.ReleaseBrake();
            }

            if (newState.IsKeyDown(Keys.Right))
            {
                world.Bike.LeanForward();
            }

            if (newState.IsKeyDown(Keys.Left))
            {
                world.Bike.LeanBackward();
            }

            if (newState.IsKeyDown(Keys.Space))
            {
                if (!oldState.IsKeyDown(Keys.Space))
                    world.Reset();
            }

            oldState = newState;
        }
    }

    
}
