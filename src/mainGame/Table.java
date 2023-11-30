package mainGame;
import cardPack.Card;
import cardPack.Deck;
import java.util.ArrayList;
import java.util.List;
public class Table {
    private Deck deck;
    public Deck getDeck() {
        return deck;
    }
    public List<Player> players;
    private Card trumpCard;
    public List<Card> cardsOut;
    public Table(List<Player> players) {
        this.players = players;
        deck = new Deck();
        cardsOut = new ArrayList<>();
        trumpCard = deck.drawCard(); // Определение козыря
        deck.getCards().add(trumpCard); // Перемещаем козырную карту в конец колоды
    }
    public void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 6; i++) {
                Card card = deck.drawCard();
                if(deck.getCards().isEmpty()){
                    trumpCard = card;
                    player.takeToTakenCards(card);
                }
                player.receiveCard(card);
            }
            player.setHand(HelpingMethods.sortHand(player.getHand(), trumpCard.getSuit()));
        }
    }
    public Card gettrrtrCard() {
        return deck.drawCard();
    }
    public Card getttttttttCard() {
        return deck.getCard();
    }
    public Card getTrumpCard() {
        return trumpCard;
    }
}
