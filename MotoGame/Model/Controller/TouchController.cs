using InfiniteMoto.Model;
using Microsoft.Xna.Framework.Input.Touch;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace InfiniteMoto.Controller
{
    public class TouchController : PlayerController
    {
        private enum Locations { UP_RIGHT, DOWN_RIGHT, UP_LEFT, DOWN_LEFT };
        private Locations[] oldLocations;

        public TouchController(World world, SoundController sc, int w, int h) : base(world, sc, w, h)
        {

        }

        public new void Update(float dTime)
        {
            CheckTouch(dTime);
        }

        private void CheckTouch(float dTime)
        {
            Locations[] newLocations = GetTouchLocations(TouchPanel.GetState());
            foreach (Locations newLocation in newLocations)
            {
                if (newLocation == Locations.UP_LEFT)
                {
                    world.Bike.RearWheel.Accelerate(dTime);
                    soundController.Accelerate();
                }

                if (newLocation == Locations.DOWN_LEFT)
                {
                    world.Bike.RearWheel.Brake(dTime);
                    soundController.Deaccelerate();
                }

                if (newLocation == Locations.UP_RIGHT)
                {
                    world.Bike.LeanForward();
                }

                if (newLocation == Locations.DOWN_RIGHT)
                {
                    world.Bike.LeanBackward();
                }
            }

            if (oldLocations != null)
            {
                //Those who are not in the new list
                foreach (Locations location in oldLocations.Where(x => !newLocations.Contains(x)))
                {
                    if (location == Locations.UP_LEFT)
                    {
                        world.Bike.RearWheel.StopAcceleration();
                        soundController.Idle();
                    }

                    if (location == Locations.DOWN_LEFT)
                    {
                        world.Bike.RearWheel.ReleaseBrake();
                        soundController.Idle();
                    }
                }
            }

            oldLocations = newLocations;
        }

        private Locations[] GetTouchLocations(TouchCollection collection)
        {
            Locations[] locations = new Locations[collection.Count()];
            for (int i = 0; i < collection.Count(); i++)
            {
                locations[i] = GetTouchLocation(collection.ElementAt(i));
            }

            return locations;
        }

        private Locations GetTouchLocation(TouchLocation location)
        {
            if (location.Position.X > width / 2)
            {
                if (location.Position.Y > height / 2)
                    return Locations.DOWN_RIGHT;
                else
                    return Locations.UP_RIGHT;
            }
            else
            {
                if (location.Position.Y > height / 2)
                    return Locations.DOWN_LEFT;
                else
                    return Locations.UP_LEFT;
            }
        }
    }
}
