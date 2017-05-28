using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using MotoGame.Model;
using MotoGame.source.Controller;
using MotoGame.View;
using System;
using System.Collections.Generic;

namespace Infinite_Moto
{
    /// <summary>
    /// This is the main type for your game.
    /// </summary>
    public class MotoGame : Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;

        private World world;
        private WorldRenderer worldRenderer;
        private PlayerController playerController;
        private SoundController soundController;
        
        public MotoGame()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";

            graphics.PreferredBackBufferWidth = 1500;  // set this value to the desired width of your window
            graphics.PreferredBackBufferHeight = 800;   // set this value to the desired height of your window
            graphics.ApplyChanges();
        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here

            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            // TODO: use this.Content to load your game content here

            world = new World();
            worldRenderer = new WorldRenderer(
                world, GraphicsDevice, 
                Content.Load<Texture2D>("data/wheel4"), 
                Content.Load<Texture2D>("data/bike_complete2"),
                Content.Load<SpriteFont>("data/Main"));
            
            soundController = new SoundController(new Dictionary<string, SoundEffect> {
                { "idle",Content.Load<SoundEffect>("data/sounds/idle") },
                { "acceleration", Content.Load<SoundEffect>("data/sounds/acceleration") },
                { "constant", Content.Load<SoundEffect>("data/sounds/constant") },
                { "deacceleration", Content.Load<SoundEffect>("data/sounds/deacceleration")}
            });

            playerController = new PlayerController(world, soundController);
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// game-specific content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed || Keyboard.GetState().IsKeyDown(Keys.Escape))
                Exit();

            playerController.Update((float)gameTime.ElapsedGameTime.TotalSeconds);
            world.Update((float)gameTime.ElapsedGameTime.TotalSeconds);

            base.Update(gameTime);
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(new Color(16,16,16));

            worldRenderer.Render(spriteBatch, (float)gameTime.ElapsedGameTime.TotalSeconds);

            base.Draw(gameTime);
        }
    }
}
