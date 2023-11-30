package strategy;
import cardPack.Card;
import cardPack.Rank;
import mainGame.Player;
import cardPack.Suit;
import mainGame.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static mainGame.HelpingMethods.*;
public class StandartStrategy implements IStrategy {
    private String nameStrayegy = "Cтандартный уровень игры";
    private Suit trumpSuit;
    @Override
    public String getNameStrategy() {
        return nameStrayegy;
    }
    public void setTrumpSuit(Suit trumpSuit) {this.trumpSuit = trumpSuit;}
    public Card attack(Player me, Player defPlayer, Table table) {
        // Пройдемся по картам игрока
        me.setHand(sortHand(me.getHand(),trumpSuit));
        Card a = me.getHand().get(0);
        me.getHand().remove(0);
        return a;
    }

    public Card deff(Player me, Card recaptureCard, Table table, List<Card> cardsInTable, List<Card> cardsForAttack){// стандартный алгоритм
        List<Card> possibleCardsToPlay = new ArrayList<>();
        // Создаем список карт, которыми можно побить карту recaptureCard
        for (Card card : me.getHand()) {
            if (canBeatCard(card, recaptureCard, trumpSuit)) {
                possibleCardsToPlay.add(card);
            }
        }
        if (!possibleCardsToPlay.isEmpty()) {
            me.getHand().remove(possibleCardsToPlay.get(0));
            return possibleCardsToPlay.get(0);
        } else {
            return null; // Если нет подходящих карт, вернуть null
        }
    }
    public Card throwCard(Player me, List<Card> desc, List<Player> players, List<Card> cardsOut) {
        List<Card> possibleCards = new ArrayList<>();
        Set<Rank> unRanks = getUniqueRanks(desc);
        Card val = null;
        for (Card card : me.getHand()) {
            if (unRanks.contains(card.getRank())) {
                possibleCards.add(card);
            }
        }
        possibleCards = sortHand(possibleCards, trumpSuit);
        for (Card card : possibleCards) {
            if (me.getHand().indexOf(card) + 1 / me.getHand().size() < 0.68) {
                val = card;
            }
        }
        if (val != null) {
            me.getHand().remove(val);
        }
        return val;
    }
    public boolean areThrowCard(Player me, List<Card> desc, List<Player> players, List<Card> cardsOut) {
        List<Card> possibleCards = new ArrayList<>();// здесь карты, которые в теории мы можем подкинуть
        Set<Rank> unRanks = getUniqueRanks(desc);
        for (Card card : me.getHand()) {
            if (unRanks.contains(card.getRank()) ) {
                possibleCards.add(card);
            }
        }
        possibleCards = sortHand(possibleCards,trumpSuit);
        for (Card card : possibleCards) {
            if (me.getHand().indexOf(card)+1/me.getHand().size()<0.68 ) {
                return true;
            }
        }
        return false;
    }
}
