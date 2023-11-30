package mainGame;
import cardPack.Card;
import java.util.ArrayList;
import java.util.List;
import static mainGame.HelpingMethods.sortHand;
public class GameRound {
    public List<Card> cardsIn; //карты которые на ходятся на столе, нужен для подкидывания
    public List<Card> cardsForAttack = new ArrayList<>();//здесь должны хранится карты, которые нужно отбить
    private final List<Player> players;
    private final Table table;
    private final Player attackPlayer;
    private final Player defPlayer;
    private boolean status;
    private final int countDefCards;
    private int countThrowingCards;
    public GameRound(List<Player> players, Table table, Player attackPlayer, Player defPlayer) {
        this.players = players;
        this.table = table;
        this.attackPlayer = attackPlayer;
        this.defPlayer = defPlayer;
        this.status = true; // Изначально игроки отбиваются
        this.countDefCards = defPlayer.getHand().size();
    }
    public Player getDefPlayer() {
        return defPlayer;
    }
    public boolean isStatus() {
        return status;
    }
    public void play() {
        cardsIn = new ArrayList<>();
        Card attackCard;
        attackCard = attackPlayer.getStrategy().attack(attackPlayer, defPlayer,table); // Ход атакующего игрока
        System.out.println(attackPlayer.getName() + " атаковал " + attackCard.toString());
        cardsIn.add(attackCard); // Карта атаки заносится в cardsIn
        cardsForAttack.add(attackCard); // Здесь мы помещаем карту, и отсюда достаем, когда бьем
        countThrowingCards = 1; //походили одной картой
        fillMassiveAttack();

        while (true) {
            cardsForAttack = HelpingMethods.sortHand(cardsForAttack, table.getTrumpCard().getSuit());//сортируем карты и бьем по порядку
            for (Card card : cardsForAttack) {
                Card defCard = defPlayer.getStrategy().deff(defPlayer, card, table, cardsIn, cardsForAttack);
                if (defCard != null) {
                    cardsIn.add(defCard);
                    System.out.println(defPlayer.getName() + " побился " + defCard);
                } else {
                    status = false;
                    defPlayer.takeCards(cardsIn);
                    cardsForAttack.clear();
                    System.out.println(defPlayer.getName() + " затянул");
                    System.out.println();
                    break;
                }
            }
            if (status) {
                cardsForAttack.clear();
            } else {
                break;
            }
            if (isRoundOver()) {//обнуление необходимого для следующей итерации
                table.cardsOut.addAll(cardsIn);
                for(Player pl:players){
                    pl.setHand(sortHand(pl.getHand(),table.getTrumpCard().getSuit()));
                }
                System.out.println("карт вышло :" + table.cardsOut.size());
                System.out.println("карт в колоде " + table.getDeck().getCards().size());
                System.out.println();
                break;
            } else {
                fillMassiveAttack();
            }
        }
    }
    private void fillMassiveAttack() {
        boolean s = attackPlayer.getStrategy().areThrowCard(attackPlayer, cardsIn, players,table.cardsOut);
        while (s && countDefCards > countThrowingCards) {
            Card aaCard = attackPlayer.getStrategy().throwCard(attackPlayer, cardsIn, players, table.cardsOut);
            cardsIn.add(aaCard);
            cardsForAttack.add(aaCard);
            countThrowingCards++;
            System.out.println(attackPlayer.getName() + " подбросилл " + aaCard.toString());
            //todo если не помещается в стол вернуть в руку челу
            s = attackPlayer.getStrategy().areThrowCard(attackPlayer, cardsIn, players,table.cardsOut);
        }
        for (Player player : players) {
            if (player != defPlayer && player != attackPlayer) { // Убедиться, что игрок не является защищающимся или атакующим
                s = player.getStrategy().areThrowCard(player, cardsIn, players, table.cardsOut);
                while (s && countDefCards > countThrowingCards) {
                    Card aCard = player.getStrategy().throwCard(player, cardsIn, players, table.cardsOut);
                    cardsIn.add(aCard);
                    cardsForAttack.add(aCard);
                    countThrowingCards++;
                    System.out.println(player.getName() + " подбросил " + aCard.toString());
                    s = player.getStrategy().areThrowCard(player, cardsIn, players, table.cardsOut);
                }
            }
        }
    }
    private boolean isRoundOver() {//здесь пробовать искать карты, которые можно подкинуть
        boolean flag = true;
        //todo  если нет больше карт, которые можно подкинуть или если карты и деффера кончились
        for (Player player : players) {
            if (player.getStrategy().areThrowCard(player, cardsIn, players, table.cardsOut) && player != defPlayer) {
                flag = false;
                break;
            }
        }
        return defPlayer.getHand().size() == 0 || (!attackPlayer.getStrategy().areThrowCard(attackPlayer, cardsIn, players, table.cardsOut) && flag);
    }
}