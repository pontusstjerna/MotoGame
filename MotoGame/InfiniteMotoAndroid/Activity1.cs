using Android.App;
using Android.Content.PM;
using Android.OS;
using Android.Views;
using InfiniteMoto.Controller;

namespace InfiniteMotoAndroid
{
    [Activity(Label = "InfiniteMoto"
        , MainLauncher = true
        , Icon = "@drawable/icon"
        , Theme = "@style/Theme.Splash"
        , AlwaysRetainTaskState = true
        , LaunchMode = Android.Content.PM.LaunchMode.SingleInstance
        , ScreenOrientation = ScreenOrientation.Landscape
        , ConfigurationChanges = ConfigChanges.Orientation | ConfigChanges.Keyboard | ConfigChanges.KeyboardHidden | ConfigChanges.ScreenSize)]
    public class Activity1 : Microsoft.Xna.Framework.AndroidGameActivity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            var g = new InfiniteMoto.MotoGame(new TouchController());
            SetContentView((View)g.Services.GetService(typeof(View)));
            g.Run();
        }
    }
}

