
package TetrisPackage;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;

import Utils.Vector2D;
import Utils.RNG;


/* The Game's Frame
 * This class encapsulates the GUI and execution of the Tetris game. 
 * This implementation of Tetris shares many similarities with traditional Tetris games (e.g. NES Tetris), such as 
   controls, scoring, the tetromino colours, and the overall UI layout. The primary difference is the levelling system
   and the tetromino fall-rate; in this version, there's no maximum level, yet the fall-rate still has a predefined min
   and max value. At first, the period decreases from max to min as the PlayRestarter progresses through the levels; once the 
   PlayRestarter has completed a level on the max fall-rate (min period), the period decreases to the second lowest value, at 
   which point the fall-rate once again continues to rise to its max value, where it then goes back down to the third
   lowest value. The term 'period-cycle' is used to define the process whereby the fall-period goes from a max value
   down to its minimum value. Eventually there are no more period-cycles and the fall-period remains at its minimum 
   value indefinitely.

 * Class Composition:
     (a). Fields (23)
         (i). Static Fields (9)
     (b). Constructors (1)
     (c). Public Methods (1)
     (d). Auxiliaries (9)
     (e). Event Handlers (2)
     (f). Nested Classes (1)
         (i). Inner Classes (1)

*/
public class TetrisFrame
    extends JFrame
        implements Runnable
{
    
// (a). Fields (23) ====================================================================================================
    
    /* The Board
     * The grid on which the game is PlayRestarted.
    */
    private TetrisGrid f_board;
    
    /* The 'Information'
     * This graphics object disPlayRestarts relevant data to the user.
    */
    private TetrisInformation f_info;
    
    /* The 'Tallies'
     * This panel disPlayRestarts the tally for each tetromino: i.e. it records the number of each tetromino type that spawns.
    */
    private TetrominoTallyPanel f_tallies;
    
    /* The Tetromino
     * The tetromino that the user moves.
    */
    private Tetromino f_tetromino;
    
    // The tetromino that's disPlayRestarted on the TetrisGrid object of f_info.
    private Tetromino f_next_tetromino;

    /* PlayRestart Button
     * When pressed, the game commences (assuming the s_testing flag is set to false).
    */
    private JButton f_btn_play_restart;
    
    /* PauseResume/Resume Button
     * This button is used to PauseResume/resume the game.
    */
    private JButton f_btn_pause_resume;
    
    /* Game Thread
     * The thread on which the game is PlayRestarted. 
    */
    private Thread f_game_thread;
    
    /*
     * The current period at which the tetromino falls (ms): i.e. each f_fall_rate_current ms the tetromino moves one 
       place down the screen. The lower the fall rate, the faster it falls.
    */
    private int f_fall_period_current;
    
    /*
     * The current normal (non soft-drop) period at which the tetromino falls (ms).
    */
    private int f_fall_period_normal;
    
    /*
     * A flag that, when true, indicates that the current tetromino piece is to fall at a faster rate than the current
       one: i.e. the piece is to drop in a 'soft' way such that the it can still be moved and rotated.
    */
    private boolean f_is_soft_drop;
    
    /*
     * The value which determines what f_fall_period_current will be.
     * The higher this value, the lower f_fall_period_current is; when f_period_coefficient is at its highest value, 
       f_fall_period_current is at its lowest (see the run() method for more explanation). 
    */
    private int f_period_coefficient;
    
    /*
     * The number of 'period-cycles' that have elapsed thus far. 
    */
    private int f_num_period_cycles_elapsed;
    
    /*
     * A flag that, when true, indicates that the game is to be PauseResumed.
    */
    private boolean f_is_paused;
    
    
// (a)(i). Static Fields (8) -------------------------------------------------------------------------------------------
    
    /*
     * The slowest/highest period at which the tetromino falls (ms).
    */
    private static final int s_fall_period_max = 700;
    
    /*
     * The fastest/lowest period at which the tetromino falls (ms).
    */
    private static final int s_fall_period_min = 300;
    
    /*
     * The interval between consecutive tetromino fall period (ms): e.g. the fall rate at level 4 will be 
       s_fall_rate_interval ms lower than at level 3.
     * The difference between the max and min fall periods must be divisible by this value: i.e. 
       (s_fall_period_initial - s_fall_period_min) % s_fall_period_interval == 0 must be true.
     
    */
    private static final int s_fall_period_interval = 100;
    
    /*
     * The number of period cycles that may elapse.
    */
    private static int s_num_period_cycles = ((s_fall_period_max - s_fall_period_min) / s_fall_period_interval) + 1;

    /*
     * The period at which the tetromino falls when the 'soft-drop' mode is active.
    */
    private static final int s_fall_period_soft_drop = s_fall_period_min / 2;
    
    /*
     * The number of lines the PlayRestarter must clear to go up a level.
     * Given that a PlayRestarter can clear at most 4 lines in a single tetromino placement, this should be 4 or higher, as 
       otherwise a PlayRestarter will be able to go up multiple levels in a single move, which may not be desirable.
    */
    private static final int s_level_length = 4;
    
    /*
     * This array is used to increase a PlayRestarter's score when they clear n lines, where n ranges from 1 to 4.
     * A PlayRestarter's score increases by f_scores_line_clears[n - 1] * f_level.
    */
    private static int f_scores_line_clears[] = { 40, 100, 300, 1200 };
    
    /* Testing Flag
     * A flag that, when true, makes the game run in the 'testing' mode; otherwise, if false, the standard game runs.
     * The testing mode is useful for checking that the tetromino blocks behave as they should.
    */
    private static boolean s_testing = false;
    
    /*
     * A flag that, when true, allows the user to PauseResume the game using f_btn_pause_resume.
     * Setting this to false means that PlayRestarters can't 'cheat' by pausing when in a tricky spot.
     * Being able to PauseResume can be good for training.
    */
    private static boolean s_can_PauseResume = true;
    
    private static final long serialVersionUID = 1L;
    

    
// (b). Constructors (1) ===============================================================================================
    
    /* Constructor
     
     * Parameters:
         > a_title: the frame's title.
    */
    public TetrisFrame(String a_title)
    {
        // Call base class' constructor.
        super(a_title);
        
        // ??? Make sure we have nice window decorations. Doesn't seem to do anything.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        // Ensure the frame exits when the user closes the window (i.e. clicks the 'cross' button).
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Ensure that the user can't change the frame's size.
        super.setResizable(false);
        
        // Align the window to the center of the screen (top left corner in centre of screen).
        super.setLocationRelativeTo(null); 
        
        // Set the layout of the frame.
        super.setLayout(new GridBagLayout());
        
        // Create a constraints object, which allows for the various graphical elements to be arranged.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Set general constraints.
        l_constraints.fill = GridBagConstraints.BOTH; // Objects take up all available horizontal space.
        
        // Create and set up f_stats
        f_tallies = new TetrominoTallyPanel(200, 500, Tetris.S_COLOUR_BACKGROUNDS_DEFAULT);
        l_constraints.gridx = 0; l_constraints.gridy = 0; // (0,0)
        super.getContentPane().add(f_tallies, l_constraints);
        
        // Create and set up f_board
        f_board = new TetrisGrid(500, false);
        l_constraints.gridx = 1; l_constraints.gridy = 0; // (1,0)
        super.getContentPane().add(f_board, l_constraints);
        
        // Create and set-up f_info.
        f_info = new TetrisInformation(300, 500, Tetris.S_COLOUR_BACKGROUNDS_DEFAULT);
        l_constraints.gridx = 2; l_constraints.gridy = 0; // (2,0)
        super.getContentPane().add(f_info, l_constraints);
        
        // Create and set-up the 'PlayRestart' button.
        f_btn_play_restart = new JButton("Play");
        f_btn_play_restart.setPreferredSize(new Dimension(50, 50));
        f_btn_play_restart.setFont(new Font("Arial", Font.BOLD, 24));
        f_btn_play_restart.addActionListener(e -> PlayRestart());
        f_btn_play_restart.setBackground(Tetris.S_COLOUR_BACKGROUNDS_DEFAULT);
        f_btn_play_restart.setForeground(Tetris.S_COLOUR_FOREGROUNDS_DEFAULT);
        f_btn_play_restart.setEnabled(!s_testing);
        l_constraints.gridx = 0; l_constraints.gridy = 1; // (0,1)
        l_constraints.gridwidth = 3; // Span 3 columns (i.e. span all three panels).
        l_constraints.fill = GridBagConstraints.HORIZONTAL;
        super.getContentPane().add(f_btn_play_restart, l_constraints);
        
        // Create and set-up the 'PauseResume' button.
        f_btn_pause_resume = new JButton("Pause");
        f_btn_pause_resume.setPreferredSize(new Dimension(50, 50));
        f_btn_pause_resume.setFont(new Font("Arial", Font.BOLD, 24));
        f_btn_pause_resume.addActionListener(e -> PauseResume());
        f_btn_pause_resume.setBackground(Tetris.S_COLOUR_BACKGROUNDS_DEFAULT);
        f_btn_pause_resume.setForeground(Tetris.S_COLOUR_FOREGROUNDS_DEFAULT);
        f_btn_pause_resume.setEnabled(s_can_PauseResume);
        l_constraints.gridx = 0; l_constraints.gridy = 2; // (0,1)
        l_constraints.gridwidth = 3; // Span 3 columns (i.e. span all three panels).
        l_constraints.fill = GridBagConstraints.HORIZONTAL;
        super.getContentPane().add(f_btn_pause_resume, l_constraints);
        
        // The game shouldn't be PauseResumed to begin with
        f_is_paused = false;

        // Force layout manager to place GUI elements.
        super.pack();
        
        // Add the controls.
        super.addKeyListener(new TetrisKeyBoardControls());
        
        // Get the focus (necessary to recognise keyboard input).
        super.setFocusable(true);
        super.requestFocusInWindow();
    }
    
    
    
// (c). Public Methods (1) =============================================================================================
    
    /* Implementation of Runnable
     * This is the code that executes when f_game_thread is instantiated and run.
    */
    public void run()
    {
        try
        {
            while (f_game_thread != null) // While the thread still runs.
            {
                // Don't run code if the teromino doesn't exist.
                //if (f_tetromino == null)
                //{ continue; }
                
                // Simulate gravity (wait before dropping).
                Thread.sleep(f_fall_period_current);
                
                if (f_is_paused) // If the game is to be Paused.
                {
                    // Have f_game_thread wait on 'this'.
                    synchronized(this)
                    {
                        this.wait();
                    }
                    
                    // Ensure that the frame has the focus so that the keyboard controls work.
                    super.requestFocusInWindow();
                }
                
                // Try to move the piece down the screen; if it can move down, continue.
                if (Move(Vector2D.s_up))
                { continue; }
                
                // Delete the tetromino so that the user cannot move/rotate it (i.e. their time is up).
                f_tetromino = null;
                
                // Remove full lines and record the number of them.
                int l_num_full_lines = f_board.RemoveFullLines();
                
                // If the user cleared at least one line.
                if (l_num_full_lines != 0)
                {
                    // Calculate the score from the line clears.
                    int l_score_from_line_clears = f_scores_line_clears[l_num_full_lines - 1] * f_info.GetLevel();
                    
                    // If all rows have been cleared (i.e. the grid is empty) double the line clears score.
                    // If all rows have been cleared, this is known as a 'perfect clear'.
                    if (f_board.IsEmpty())
                    { l_score_from_line_clears *= 2; }
                    
                    // Increment the score.
                    f_info.IncrementScore(l_score_from_line_clears);
                    
                    // Record the line clears.
                    f_info.IncrementLinesCleared(l_num_full_lines);
                    
                    // A flag that, when true, indicates that a new level has been reached.
                    boolean l_is_new_level = f_info.GetNumLinesCleared() - s_level_length*f_info.GetLevel() > 0;
    
                    if (l_is_new_level)
                    {   
                        // Increment the level.
                        f_info.IncrementLevel();
                        
                        // A flag that, when true, indicates that there are no more period cycles.
                        boolean l_no_more_period_cycles = f_num_period_cycles_elapsed + 1 == s_num_period_cycles;
                        
                        if (!l_no_more_period_cycles) // If there are further period cycles.
                        {
                            // Update f_num_period_cycles and f_period_coefficient.
                            if (f_fall_period_normal == s_fall_period_min)
                            {
                                ++f_num_period_cycles_elapsed;
                                
                                f_period_coefficient = f_num_period_cycles_elapsed;
                            }
                            else
                            {
                                ++f_period_coefficient;
                            }
                            
                            // Calculate the period for the current level.
                            f_fall_period_normal = s_fall_period_max - s_fall_period_interval * f_period_coefficient;
                        }
                        
                        // For testing the fall period.
                        //System.out.println("Level " + f_info.GetLevel() + ": fall period is " + f_fall_period_normal);
                        
                    }
                    
                }
                
                // Reset the fall-rate to the 'normal' amount (shouldn't be f_fall_rate_soft_drop).
                f_fall_period_current = f_fall_period_normal;
                
                // Create and spawn the next tetromino)
                boolean l_valid_spawn = SpawnNextTetromino();
                
                // If the tetromino cannot be spawned, end the game and notify the PlayRestarter that the game is over.
                if (!l_valid_spawn)
                {
                    if (f_info.GetScore() > f_info.GetHighScore())
                    {
                        System.out.println("Congratulations! Your score of " + f_info.GetScore() +
                                           " is higher than the previous high score of " + f_info.GetHighScore() + '.');
                        
                        // Update the high score.
                        f_info.UpdateHighScore();
                        
                    }
                    else
                    {
                        System.out.println("Game over! You completed " + f_info.GetNumLinesCleared() + " lines, reached level " + 
                                           f_info.GetLevel() + ", and scored " + f_info.GetScore() + " points.");
                    }
                    
                    // End thread execution.
                    f_game_thread.interrupt();
                }
                
            }
            
        }
        catch (InterruptedException e)
        {   
            f_game_thread = null;
            
            f_btn_play_restart.setText("Play Again");
        }
        
    }
    
    
    
// (d). Auxiliaries (9) ================================================================================================
    
    /* Auxiliary of run()
     * Spawns the next tetromino.
    */
    private boolean SpawnNextTetromino()
    {
        if (f_next_tetromino != null)
        {
            f_tetromino = f_next_tetromino;
        }
        else
        {
            f_tetromino = new Tetromino( Tetromino.Type.values()[RNG.RandomInt(Tetromino.Type.values().length - 1)] );
        }
        
        // Update the tallies.
        f_tallies.IncrementTally(f_tetromino);
        
        f_next_tetromino = new Tetromino( Tetromino.Type.values()[RNG.RandomInt(Tetromino.Type.values().length - 1)] );
        
        f_info.SetNextTetromino(f_next_tetromino);
        
        return Spawn();
    }
    
    /* Auxiliary of TetrisKeyBoardControls.keyReleased()
     * This method generates a (random) tetromino and spawns it at the top of the grid.
    */
    private boolean GenerateAndSpawn()
    {
        // Create a tetromino.
        GenerateTetromino();
        
        // Spawn the tetromino.
        return Spawn();
    }
    
    /* Auxiliary of GenerateAndSpawn()
     * This method generates a (random) tetromino.
    */
    private void GenerateTetromino()
    {
        f_tetromino = new Tetromino( Tetromino.Type.values()[RNG.RandomInt(Tetromino.Type.values().length - 1)] );
    }
    
    /* Auxiliary of run()
     * This method moves the tetromino in a given direction.
     
     * Parameters:
         > a_movement: the direction in which the tetromino will move. Note that the positive y direction is down the page.
     
     * Return Value:
         > A boolean indicating whether or not the tetromino was successfully moved in the given direction. 
    */
    private boolean Move(Vector2D a_movement)
    {
        if (f_tetromino == null)
        { return false; }
        
        // Move the tetromino's position.
        synchronized (f_tetromino)
        {  return f_tetromino.Move(a_movement, f_board, true); }
    }
    
    /* Auxiliary of TetrisKeyBoardControls.keyReleased(...)
     * This method moves the current tetromino down the screen until it can no longer be moved down.
    */
    private void Drop()
    {
        if (f_tetromino == null)
        { return; }
        
        // Keep moving the tetromino one place up (down the screen) until it can no longer by moved.
        synchronized (f_tetromino)
        { while(f_tetromino.Move(Vector2D.s_up, f_board, true)) {} }
        
    }
    
    /* Auxiliary of TetrisKeyBoardControls.keyReleased(...)
     * This method tries to rotate the current tetromino in a given direction.
     
     * Parameters:
         > a_clockwise: a flag that, when true, indicates that the tetromino is to be rotated clockwise.
    */
    private boolean Rotate(boolean a_clockwise)
    {
        if (f_tetromino == null)
        { return false; }
        
        synchronized (f_tetromino) 
        { return f_tetromino.Rotate(a_clockwise, true, f_board); }
    }
    
    /* Auxiliary of TetrisKeyBoardControls.keyReleased(...)
     * This method drops the current tetromino and adds a new one at the default spawn location.
     
     * Parameters:
         > a_type: the type of tetromino that will be spawned.
    */
    private void NewTetromino(Tetromino.Type a_type)
    {
        // Drop the current tetromino.
        Drop();
        
        // Create the new tetromino.
        f_tetromino = new Tetromino(a_type);
        
        // Spawn the new tetromino.
        Spawn();
    }
    
    /* Auxiliary of TetrisKeyBoardControls.keyReleased(...)
     * This method removes the tetromino from the grid.
    */
    private void RemoveTetromino()
    {
        // Remove the tetromino's graphics,
        f_board.UnDrawTetromino(f_tetromino);
        
        // Delete the tetromino.
        f_tetromino = null;
    }
    
    /* Auxiliary of SpawnNextTetromino, GenerateAndSpawn, NewTetromino,
     * This method spawns the tetromino at the default location
     
     * Parameters:
         > a_clockwise: a flag that, when true, indicates that the tetromino is to be rotated clockwise.
         
     * Return Value:
         > A boolean indicating whether or not the tetromino was sucessfully spawned.
    */
    private boolean Spawn()
    {        
        if (f_tetromino == null)
        { return false; }
        
        // Add the tetromino's graphics (and set initial location).
        return f_board.DrawTetromino(f_tetromino, TetrisGrid.DrawPosition.CentreTop);
    }
    
    
    
// (e). Event Handlers (2) =============================================================================================
    
    /* Event Handler of f_btn_play_restart
     * This event-handler starts a new game of tetris. 
    */
    private void PlayRestart()
    {
        // If the game is running, this means it must be stopped before starting a new one (i.e. restart the game).
        if (f_game_thread != null)
        {
            f_game_thread.interrupt();
            
            if (f_is_paused)
            {
                PauseResume();
            }
        }
        
        // Reset relevant fields.
        f_fall_period_normal = s_fall_period_max;
        f_fall_period_current = s_fall_period_max;
        f_num_period_cycles_elapsed = 0;
        f_is_soft_drop = false;
        f_board.Reset();
        f_info.Reset();
        f_tallies.Reset();
        
        // Spawn the first tetromino.
        SpawnNextTetromino();
        
        // Create and start the game's thread.
        f_game_thread = new Thread(this);
        f_game_thread.start();
        
        // Set the button's text to indicate its functionality: i.e. start a new game in the middle of a current one.
        f_btn_play_restart.setText("Restart");
        
        // Ensure that the frame has the focus so that the keyboard controls work.
        super.requestFocusInWindow();
    }
    
    /* Event Handler of f_btn_pause_resume
    */
    private void PauseResume()
    {   
        if (f_game_thread == null)
        { return; }
        
        // Toggle the PauseResume flag.
        f_is_paused = !f_is_paused;
        
        // Set the button's text to show the current action it will perform (either PauseResume or resume).
        if (f_is_paused)
        {
            f_btn_pause_resume.setText("Resume");
        }
        else
        {
            f_btn_pause_resume.setText("Pause");
            
            // Awaken f_game_thread.
            synchronized (this) 
            {
                this.notifyAll();
            }
        }
        
        // Ensure that the frame has the focus so that the keyboard controls work.
        super.requestFocusInWindow();
    }
    
    
    
// (f). Nested Classes (1) =============================================================================================
    
    
// (f)(i). Inner Classes (1) -------------------------------------------------------------------------------------------
    
    /* Keyboard Controls
     * An instance of this inner class is what handles the keyboard input.
     
     * Composition:
         (a'). Public Methods (1) 
    */
    private class TetrisKeyBoardControls
        extends KeyAdapter
    {   
        
    // (a'). Public Methods (1) ========================================================================================
        
        /* Implementation of KeyAdapter.keyReleased(...)
         * 
        */
        @Override
        public void keyReleased(KeyEvent e)
        {  
            // If not in the testing mode and the tetromino doesn't exist, return.
            if (!s_testing && f_tetromino == null)
            { return; }
            
            int l_key_code = e.getKeyCode();
            
            // If not in the testing mode and the game is Paused, only enable the Pause/Resume key: 'p'.
            if (!s_testing && f_is_paused)
            { 
                if (l_key_code == KeyEvent.VK_P)
                { PauseResume(); }
                
                return;
            }
            
            // Commands that are handled irrespective of the value of s_testing.
            if (l_key_code == KeyEvent.VK_LEFT)
            {
                Move(Vector2D.s_left);
            }
            else if (l_key_code == KeyEvent.VK_RIGHT)
            {
                Move(Vector2D.s_right);
            }
            else if (l_key_code == KeyEvent.VK_DOWN)
            {
                if (!s_testing)
                {
                    f_is_soft_drop = !f_is_soft_drop;
                    
                    f_fall_period_current = f_is_soft_drop ? s_fall_period_soft_drop : f_fall_period_normal;
                }
                else
                {
                    Move(Vector2D.s_up);
                }
            }
            else if (l_key_code == KeyEvent.VK_UP)
            {
                if (!s_testing)
                {
                    Drop();
                }
                else
                {
                    Move(Vector2D.s_down);
                }
            }
            else if (l_key_code == KeyEvent.VK_D)
            {
                Rotate(true);
            }
            else if (l_key_code == KeyEvent.VK_A)
            {
                Rotate(false);
            }
            
            // Commands that are specific to when s_testing is not set.
            if (!s_testing)
            {
                if (l_key_code == KeyEvent.VK_P)
                {
                    PauseResume();
                }
            }
            
            // Commands that are specific to when s_testing is set.
            if (s_testing)
            {
                if (l_key_code == KeyEvent.VK_SPACE)
                {
                    Drop();
                    GenerateAndSpawn();
                }
                else if (l_key_code == KeyEvent.VK_C)
                {
                    f_board.Reset();
                }
                else if (l_key_code == KeyEvent.VK_R)
                {
                    f_board.RemoveFullLines();
                }
                else if (l_key_code == KeyEvent.VK_DELETE)
                {
                    RemoveTetromino();
                }
                else if (l_key_code == KeyEvent.VK_I)
                {
                    NewTetromino(Tetromino.Type.I);
                }
                else if (l_key_code == KeyEvent.VK_J)
                {
                    NewTetromino(Tetromino.Type.J);
                }
                else if (l_key_code == KeyEvent.VK_L)
                {
                    NewTetromino(Tetromino.Type.L);
                }
                else if (l_key_code == KeyEvent.VK_O)
                {
                    NewTetromino(Tetromino.Type.O);
                }
                else if (l_key_code == KeyEvent.VK_S)
                {
                    NewTetromino(Tetromino.Type.S);
                }
                else if (l_key_code == KeyEvent.VK_T)
                {
                    NewTetromino(Tetromino.Type.T);
                }
                else if (l_key_code == KeyEvent.VK_Z)
                {
                    NewTetromino(Tetromino.Type.Z);
                }
            }
            
        }
        
        
    } // private class TetrisKeyBoardControls
       
    
} // public class TetrisFrame

