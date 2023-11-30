package cardPack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Deck {
    private List<Card> cards;
    public List<Card> getCards() {
        return cards;
    }
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }
    public Card getCard() {
        if (!cards.isEmpty()) {
            return cards.get(0);
        }
        return null;
    }
}
