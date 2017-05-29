using Microsoft.Xna.Framework.Audio;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InfiniteMoto.Controller
{
    public class SoundController
    {
        private Dictionary<string, SoundEffectInstance> sounds;

        private string nowPlaying;

        public SoundController(Dictionary<string, SoundEffect> sounds)
        {
            this.sounds = new Dictionary<string, SoundEffectInstance>();
            foreach(var sound in sounds)
            {
                var sei = sound.Value.CreateInstance();
                sei.IsLooped = true;
                //sei.Pitch = 0.7f;

                this.sounds.Add(sound.Key, sei);
            }

            Idle();
        }

        public void Idle()
        {
            PlaySound("idle");
        }

        public void Accelerate()
        {
            PlaySound("acceleration");
        }

        public void Constant()
        {
            PlaySound("constant");
        }

        public void Deaccelerate()
        {
            PlaySound("deaccelerate");
        }

        public void StopAllSound()
        {
            sounds[nowPlaying].Stop();
            nowPlaying = "";
        }

        private void PlaySound(string sound)
        {
            if (nowPlaying != sound)
            {
                if (!String.IsNullOrEmpty(nowPlaying))
                {
                    sounds[nowPlaying].Stop();
                }

                sounds[sound].Play();
                nowPlaying = sound;
            }   
        }
    }
}
