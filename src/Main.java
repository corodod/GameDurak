import mainGame.Game;
import mainGame.Player;
import strategy.SmartStrategy;
import strategy.StandartStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        List<Player> players = input();
        System.out.print("Введите количество игр: ");
        Scanner ss = new Scanner(System.in);
        int countGames = ss.nextInt();
        int[] statistics = new int[2];
        statistics[0] = 0;
        statistics[1] = 0;
        for(int i=0;i<countGames;i++){
            List<Player> players1 = (List<Player>) new ArrayList<>(players).clone();
            Game game = new Game();
            List<Player> winners = game.goGame(players1);
            if (winners!=null && winners.size()==1){
                if(winners.get(0).getStrategy().getNameStrategy().equals("Cтандартный уровень игры")){
                    statistics[0]+=1;
                    System.out.println("зашли в стандартного"+statistics[0]);
                }else{
                    statistics[1]+=1;
                    System.out.println("зашли в продвинутого"+statistics[1]);
                }
            }
        }
        double res1 = 1 - (double)statistics[0]/countGames;
        double res2 = 1 - (double)statistics[1]/countGames;
        System.out.println("Стандартный игрок побеждает с вероятностью: "+ res1);
        System.out.println("Продвинутый игрок побеждает с вероятностью: "+ res2);
    }
    public static List<Player> input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество игроков (от 2 до 6): ");
        int numPlayers = scanner.nextInt();
        System.out.print("Введите уровни игроков(1 или 2): ");
        String levelString = scanner.next();
        int[] levels = new int[levelString.length()];
        for (int i = 0; i < levelString.length(); i++) {
            levels[i] = Character.getNumericValue(levelString.charAt(i));
        }
        if (numPlayers < 2 || numPlayers > 6) {  // исправлено условие проверки количества игроков
            System.out.println("Неверное количество игроков.");
            return null;
        }
        String[] names = {"SAM1", "VOVA2", "ARTEM3", "SASHA4", "PETYA5", "DANIL6"};
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            String playerName = names[i];
            int level = levels[i];
            Player currentPlayer = new Player(playerName);
            if(level==1){
                currentPlayer.setStrategy(new StandartStrategy());//
            }
            else{
                currentPlayer.setStrategy(new SmartStrategy());//
            }
            players.add(currentPlayer);
        }
        return players;
    }
}
