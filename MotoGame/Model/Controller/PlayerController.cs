﻿using Microsoft.Xna.Framework.Input;
using InfiniteMoto.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Xna.Framework.Input.Touch;

namespace InfiniteMoto.Controller
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
            CheckKeyboard(dTime);
            CheckTouch(dTime);
            
        }

        private void CheckKeyboard(float dTime)
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

        private void CheckTouch(float dTime)
        {
            if(TouchPanel.GetState().Count() > 0)
            {
                world.Bike.RearWheel.Accelerate(dTime);
                soundController.Accelerate();
            }
        }
    }
}
