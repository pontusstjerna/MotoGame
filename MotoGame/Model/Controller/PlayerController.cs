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
    public class PlayerController
    {
        private World world;
        private SoundController soundController;

        private int width, height;

        private KeyboardState oldState;
        private TouchLocation oldTouchLocation;

        public PlayerController(World world, SoundController soundController, int width, int height)
        {
            this.world = world;
            this.soundController = soundController;
            this.width = width;
            this.height = height;
        }

        public void Update(float dTime)
        {
            CheckKeyboard(dTime);
            CheckTouch(dTime);
            
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

        private void CheckTouch(float dTime)
        {
            TouchLocation newTouchLocation = TouchPanel.GetState().FirstOrDefault();
            if(newTouchLocation != null)
            {
                if(newTouchLocation.Position.X > width/2 && newTouchLocation.Position.Y < height / 2)
                {
                    world.Bike.RearWheel.Accelerate(dTime);
                    soundController.Accelerate();
                }
            }
            else if(oldTouchLocation != null)
            {
                world.Bike.RearWheel.StopAcceleration();
                soundController.Idle();
            }

            oldTouchLocation = newTouchLocation;
        }
    }
}
