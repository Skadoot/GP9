public class main
{
    private static game game = null;

    public static void main(String[] args) {
        //run main menu

        //new game
        create_game();

        //load
        load_game();

        //quit
    }

    private static void create_game() {
        game = new game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    private static void load_game() {
        //here we would read a board state from a file.
    }
}
