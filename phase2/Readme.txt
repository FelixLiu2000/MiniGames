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
Here you will find three image buttons each which trigger a different game
2

Save Manager: 

The save manager uses seralization and deseralization to save player profiles.