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
        private Locations[] oldLocations;

        private enum Locations { UP_RIGHT, DOWN_RIGHT, UP_LEFT, DOWN_LEFT};

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
            Locations[] newLocations = GetTouchLocations(TouchPanel.GetState());
            foreach(Locations newLocation in newLocations)
            {
                if (newLocation == Locations.UP_RIGHT)
                {
                    world.Bike.RearWheel.Accelerate(dTime);
                    soundController.Accelerate();
                }

                if (newLocation == Locations.DOWN_RIGHT)
                {
                    world.Bike.RearWheel.Brake(dTime);
                    soundController.Deaccelerate();
                }

                if (newLocation == Locations.UP_LEFT)
                {
                    world.Bike.LeanForward();
                }

                if(newLocation == Locations.DOWN_LEFT)
                {
                    world.Bike.LeanBackward();
                }
            }

            if(oldLocations == null) return;
            //Those who are not in the new list
            foreach(Locations location in oldLocations.Where(x => !newLocations.Contains(x)))
            {
                if (location == Locations.UP_RIGHT)
                {
                    world.Bike.RearWheel.StopAcceleration();
                    soundController.Idle();
                }

                if (location == Locations.DOWN_RIGHT)
                {
                    world.Bike.RearWheel.ReleaseBrake();
                    soundController.Idle();
                }
            }

            oldLocations = newLocations;
        }

        private Locations[] GetTouchLocations(TouchCollection collection)
        {
            Locations[] locations = new Locations[collection.Count()];
            for(int i = 0; i < collection.Count(); i++)
            {
                locations[i] = GetTouchLocation(collection.ElementAt(i));
            }

            return locations;
        }

        private Locations GetTouchLocation(TouchLocation location)
        {
            if(location.Position.X > width / 2)
            {
                if (location.Position.Y > width / 2)
                    return Locations.UP_RIGHT;
                else
                    return Locations.DOWN_RIGHT;
            }
            else
            {
                if (location.Position.Y > width / 2)
                    return Locations.UP_LEFT;
                else
                    return Locations.DOWN_LEFT;
            }
        }
    }
}
