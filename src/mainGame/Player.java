package mainGame;
import cardPack.Card;
import strategy.IStrategy;
import java.util.*;
public class  Player {
    String name;
    private List<Card> hand;
    private IStrategy strategy;
    public IStrategy getStrategy() {return strategy;}
    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }
    List<Card> takenCards; // Карты, взятые игроком     объект ход, старутует когда бросает карту
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        takenCards = new ArrayList<>();
    }
    public void displayHand() {
        System.out.println("Карты в руке игрока " + name + ":");
        for (Card card : hand) {
            System.out.print(card.toString());
        }
        System.out.println();
    }
    public void receiveCard(Card card) {
        hand.add(card);
    }
    public void takeCards(List<Card> cards) {
        hand.addAll(cards);
        takenCards.addAll(cards);
    }
    public void takeToTakenCards(Card card) {
        takenCards.add(card);
    }
    public List<Card> getHand() {
        return hand;
    }
    public List<Card> getTakenCards() {
        return takenCards;
    }
    public String getName() {
        return name;
    }
    public List<Card> getOpponentsCards(List<Player> players){
        List<Card> val = new ArrayList<>();
        for(Player player:players){
            val.addAll(player.getTakenCards());
        }
        val.removeAll(hand);
        return val;
    }
}
