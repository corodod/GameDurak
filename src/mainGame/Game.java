package mainGame;
import cardPack.Card;
import java.util.ArrayList;
import java.util.List;
public class Game {
    public List<Player> goGame(List<Player> players) {
        boolean availabilityOfCards = true;//сделать еще инициализацию козырной карты для стратегии
        List<Player> winners = new ArrayList<>();
        Table table = new Table(players);
        table.dealCards();// Раздача карт и начало игры
        for(Player pl : players){
            System.out.println();
            pl.displayHand();
            System.out.println(pl.getStrategy().getNameStrategy());
        }
        Card trumpCard = table.getTrumpCard();
        for (Player p:players){
            p.getStrategy().setTrumpSuit(trumpCard.getSuit());
        }
        System.out.println("карт в колоде " + table.getDeck().getCards().size());
        System.out.println("козырная карта: " + trumpCard.toString());
        Player firstPlayer = determineFirstPlayer(table.getTrumpCard(), players); // Определение игрока, делающего первый ход
        System.out.println("Первым ходит " + firstPlayer.getName());
        int sws = players.indexOf(firstPlayer);
        while (players.size() > 1) {
            GameRound gg = new GameRound(players, table, players.get(sws % players.size()), players.get((sws + 1) % players.size()));
            gg.play();
            if (availabilityOfCards) {
                System.out.println("Осталось "+ table.getDeck().getCards().size()+" карт в колоде");
            }
            for (int i = 0; i < players.size(); i++) {
                if (availabilityOfCards) {
                    while (players.get((sws + i) % players.size()).getHand().size()<6) {//todo здесь сделать цикл вайл пока не доберет 6 карт
                        if (!table.getDeck().getCards().isEmpty()) {
                            if(table.getDeck().getCard()==trumpCard){
                                players.get((sws + i) % players.size()).takeToTakenCards(table.getttttttttCard());
                            }
                            Card a = table.getDeck().drawCard();
                            players.get((sws + i) % players.size()).receiveCard(a);
                            System.out.println( players.get((sws + i) % players.size()).getName() + " взял карту "+ a.toString());
                            players.get((sws + i) % players.size()).setHand(HelpingMethods.sortHand(players.get((sws + i) % players.size()).getHand(),trumpCard.getSuit() ));
                        } else {
                            availabilityOfCards = false;
                            System.out.println("Карты кончились " + table.getDeck().getCards().size());
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            for (Player pl : players){
                pl.displayHand();
            }
            if (gg.isStatus()){
                sws = players.indexOf(gg.getDefPlayer());
            }else{
                sws = (sws+2) % players.size();
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getHand().isEmpty()) {
                    winners.add(players.get(i));
                    System.out.println(players.get(i).getName() + " вышел");
                    players.remove(i);
                    i--;// теперь за итерацию может выйти не один а несколько, не пропустит
                }
            }
            System.out.println();
        }
        for(Player pl :players){
            List<Card> d = new ArrayList<>();
            pl.setHand(d);
        }
        System.out.println("Карт вышло: "+ table.cardsOut.size());
        if(players.size()==1){
            System.out.println("Проиграл игрок: "+players.get(0).getName());
            return  players;
        }
        System.out.println("ничья");
        return null;
    }
    public static Player determineFirstPlayer(Card trumpCard, List<Player> players) {
        Player firstPlayer = null;
        int targetRank = (trumpCard.getRank().ordinal() < 5) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Player player : players) {
            for (Card card : player.getHand()) {
                if (card.getSuit() == trumpCard.getSuit()) {
                    int cardRank = card.getRank().ordinal();
                    if ((trumpCard.getRank().ordinal() < 5 && cardRank > targetRank) ||
                            (trumpCard.getRank().ordinal() >= 5 && cardRank < targetRank)) {
                        targetRank = cardRank;
                        firstPlayer = player;
                    }
                }
            }
        }
        if (firstPlayer == null) {
            int randomIndex = (int) (Math.random() * players.size());// Если ни у одного из игроков нет козырных карт, выбор игрока с помощью рандома
            firstPlayer = players.get(randomIndex);
        }
        return firstPlayer;
    }
}
