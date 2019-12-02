The read me is split into the parts of the overall program – the games, the app start, game dashboard, and save manager. 


Subway Game (Backpack Icon): 

Click on the button on the bottom left and right of the screen to move the backpack to the left and right, respectively. Collect falling coins to increase your score and avoid falling trash cans which decrease your score. The background/style behind the game is inspired by the daily commute to UofT, which in recent times consisted of avoiding buckets, and in the past, collecting TTC tokens.  

Through development of the game, we aimed to implement the factory pattern and SOLID design principles, especially liskov’s substitution principle with regards to MovingObjects. 

Please look at our UML for an overview of how classes interact within the subway game.  

Note: Eric’s repo broke on 28/11/19, so he was not able to commit any changes after that day. 

 

Card Game (Card Icon): 

This game plays like the classic memory game. In the time game mode, you have to find as many matches as possible before the time runs out. Once you empty a board, the cards will shuffle and reappear.  

In the infinite game mode, the first round starts timed and works the same as in the timed round. Although the difference here is that after the time runs out, your current score because your starting score for the infinite section of the game. Now every correct match adds to your score and every incorrect match reduces your score. You can continue playing until your score hits 0.  

The games work the same in multiplayer, where as soon as you lose a game or your time runs out, the next player starts. 

 

Ball Game (Desk Icon): 

Try to shoot balls of homework/assignment papers that you hate from your player’s deskover into the moving trash can on the right side of the screen. Control the shot’s angle and power using the two sliding bars on the top left and fire the ball with the Fire button. You can shoot multiple balls in succession, but if you miss or strike the side of the target, you will lose lives. Once you run out of lives, time, or both (depending on whether Infinite or Timed mode was picked in the main menu), the game ends.  

When playing multiplayer, the next player’s turn begins. Compete for the highest score; points are earned for successful hits on the top of the target and bonus points are awarded for lives left over when the time runs out. 

When playing the infinite mode, you have unlimited time to make as many shots as possible; the game continues indefinitely until you run out of lives. 

The remaining time, lives, and current scores of the player(s) are in the top right corner. 

 

App Start: 

Upon Starting the app you have the choice to create an account or log in. We implemented some key features to maximize UX such as disabled submit buttons until all required fields are completed and checking against existing usernames and confirming password when creating along with checking for the correct credentials when logging in.  

The Create Activity and Log In Activity were reused when adding a second player to minimize repetitive code along with unnecessary activities that serve the same purpose.  

 

Game Dashboard: 

Here you will find three image buttons each which trigger a different game. You will also find a settings icon in the bottom left which allows the user to set their preferred customization choices. In the bottom middle is the stats page which allows you to review your stats – more on this in the Stats section. The bottom right has the button that allows you to switch between 1 and 2 player mode. There is also a toggle button above the game buttons that lets you choose the game mode you wish to play. 

 

Settings: 

Here we have three spinners and we implemented helper methods to clean up the redundant code. There are listeners for changes to each spinner that allow the app Manager to know what to save if the player presses the save button.  

 

Stats page:  

This activity dynamically changes its layout and data in the field for 12 different layouts but we aimed to make it dynamic so that there would not be 12 different activity files. The buttons along the bottom are shown only if the stats page is triggered from the dashboard and they allow you to filter through the stats for each game accordingly. The stats page is also used to reflect after playing a game and here the values are just of that round and not overall and the game buttons are therefore non accessible. The stats page and all of its helper methods in the activity (Results Activity) were made to make the code as reusable and clean as possible allowing for SOLID principles and easy to debug and read.  

 

Change Multiplayer: 

The button will trigger one of two reactions, if it is clicked when the game is currently one player it will prompt the same log in screen as at the start of the app and allows player 2 to log in or click the button to switch to the create screen and create the second player, this reusability of the same log in and create facilitates the project layout and future updates such as adding in confirm password check.  

If clicked when in multiplayer mode it will prompt you to choose which player remains and will switch back to the regular single player mode.  

 

AppManager: 

This class is what is fed through all the intents and holds access to various necessary objects. It behaves in ways a façade (and dependency injection) does as it allows the game devs to access player objects and update methods that then update player attributes without the game devs having to ever bring in or create an instance of the player that is playing. This also allows the app to easily extend to adding new games or changing player attributes as neither of the two depend on each other since App manager handles all cross access communication through various methods and getters/setters.  

 

Enum Files in Utilities: 

These aloud for simplicity in values that were respectively used and allows for easier updating of those values If ever need be. 


Save Manager: 

The save manager uses seralization and deseralization to save player profiles.