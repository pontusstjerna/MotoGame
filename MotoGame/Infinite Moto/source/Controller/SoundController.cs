using Microsoft.Xna.Framework.Audio;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MotoGame.source.Controller
{
    public class SoundController
    {
        private Dictionary<string, SoundEffect> sounds;

        public SoundController(Dictionary<string, SoundEffect> sounds)
        {
            this.sounds = sounds;
        }

        public void Idle()
        {

        }

        public void Accelerate()
        {
            sounds["acceleration"].Play();
        }

        public void Constant()
        {

        }

        public void Deaccelerate()
        {

        }
    }
}
