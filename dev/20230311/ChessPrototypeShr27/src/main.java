//remember you commented out move method for testing

public class main
{
    private static game game = null;

    public static void main(String[] args) {
        //run main menu

        //new game
        create_game();

        //save game

        System.out.println("Do we get here?");
        game.saveGame("testTwo", "FEN2");

        //load
        //load_game();

        //quit
    }

    //this is a test for the git branch feature/save_and_load

    private static void create_game() {
        //game = new game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        game = new game("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w KQkq - 0 1");
        //game = new game("8/8/8/4p1K1/2k1P3/8/8/8 b - - 0 1");
        //game = new game("4k2r/6r1/8/8/8/8/3R4/R3K3 w Qk - 0 1");
        //game = new game("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
    }

    private static void load_game() {
        //here we would read a board state from a file.
    }
}
