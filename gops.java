import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Game of Pure Strategy (GOPS) aka Goofspiel
 * <p>
 * Goofspiel is played using cards from a standard deck of cards,
 * and is typically a two-player game, although more players are possible.
 * Each suit is ranked A (low), 2, ..., 10, J, Q, K (high).
 * One suit is singled out as the "prizes"; each of the remaining suits becomes
 * a hand for one player, with one suit discarded if there are only two players,
 * or taken from additional decks if there are four or more. The prizes are shuffled
 * and placed between the players with one card turned up.
 * </p>
 *
 * <p>
 * Play proceeds in a series of rounds. The players make sealed bids for the top
 * (face up) prize by selecting a card from their hand (keeping their choice secret from their
 * opponent).
 * Once these cards are selected, they are simultaneously revealed, and the player making the
 * highest bid takes
 * the competition card. Rules for ties in the bidding vary, possibilities including the
 * competition card being
 * discarded, or its value split between the tied players (possibly resulting in fractional scores).
 * </p>
 *
 * <p>
 * Some play that the current prize "rolls over" to the next round, so that two or
 * more cards are competed for at once with a single bid card.
 * </p>
 *
 * <p>
 * The cards used for bidding are discarded, and play continues with a new upturned prize card.
 * After 13 rounds, there are no remaining cards and the game ends. Typically, players earn
 * points equal to sum of the ranks of cards won (i.e. ace is worth one point, 2 is two points,
 * etc., jack 11, queen
 * 12, and king 13 points). Players may agree upon other scoring schemes.
 * </p>
 */
public class Gops {
    GopsState initState(final int[] suit, final int players) {
        final ArrayList<Integer> suitList = new ArrayList<Integer>(suit.length);
        for (int s : suit) {
            suitList.add(s);
        }

        GopsState state = new GopsState();
        state.roundNumber = 0;
        state.remainingPrizes = (ArrayList<Integer>) suitList.clone();

        for (int i = 0; i < players; i++) {
            state.remainingHands.add((ArrayList<Integer>) suitList.clone());
            state.cumulScore.add(0);
        }

        return state;
    }

    public static int getRandomNumber(int len) {
        return (int) (Math.random() * len);
    }

    /**
     * Returns a clone of arr with element specified by idx removed. arr is not change
     */
    public static ArrayList<Integer> cloneAndDiscard(ArrayList<Integer> arr, int idx) {
        ArrayList<Integer> clone = (ArrayList) arr.clone();
        clone.remove(idx);
        return clone;
    }

    /**
     * p0 picks a random card, while p1 matches the bounty card.
     */
    GopsState next(final GopsState state) {
        int p0Index = getRandomNumber(state.remainingHands.get(0).size());
        int p1Index = state.remainingHands.get(1).indexOf(state.remainingPrizes.get(0));

        // New state
        GopsState newState = new GopsState();
        newState.roundNumber = state.roundNumber + 1;
        newState.prize = state.remainingPrizes.get(0);
        newState.remainingPrizes = cloneAndDiscard(state.remainingPrizes, 0);

        newState.bids.add(state.remainingHands.get(0).get(p0Index)); // player 0 plays random
        newState.bids.add(newState.prize); // player 1 plays the bounty

        int p0Score = newState.bids.get(0).intValue() > newState.bids.get(1).intValue() ?
                newState.prize : 0;
        int p1Score = newState.bids.get(1).intValue() > newState.bids.get(0).intValue() ?
                newState.prize : 0;
        newState.scored.add(p0Score);
        newState.scored.add(p1Score);
        newState.cumulScore.add(state.cumulScore.get(0) + p0Score);
        newState.cumulScore.add(state.cumulScore.get(1) + p1Score);

        newState.remainingHands.add(cloneAndDiscard(state.remainingHands.get(0), p0Index));
        newState.remainingHands.add(cloneAndDiscard(state.remainingHands.get(1), p1Index));

        return newState;
    }

    public void play(int[] suit, int numPlayers) {
        GopsState state = initState(suit, numPlayers);
        state.report();
        while (state.remainingPrizes.size() > 0) {
            state = next(state);
            state.report();
        }
        System.out.printf("\n>>> Player %d wins!",
                state.cumulScore.get(0) > state.cumulScore.get(1) ? 0 : 1);
    }

    /**
     * The state of this round.
     */
    class GopsState {
        public int roundNumber;
        public int prize; // The face-up prize card in this round

        // What's played in this round [player0, player1...]
        public ArrayList<Integer> bids = new ArrayList<>();

        // Scores in this round: [player0, player1...]
        public ArrayList<Integer> scored = new ArrayList<>();

        // Cumulative score up to and including this round
        public ArrayList<Integer> cumulScore = new ArrayList<>();

        // Remaining after this round
        public ArrayList<Integer> remainingPrizes = new ArrayList<>();
        public ArrayList<ArrayList<Integer>> remainingHands = new ArrayList<>();

        public void report() {
            System.out.format("{ round: %d: bounty: %d, bids: %s -> scored: %s, cumul: %s, " +
                            "remainingHands: %s }\n",
                    roundNumber,
                    prize,
                    bids.toString(),
                    scored.toString(),
                    cumulScore.toString(),
                    remainingHands.toString());
        }
    }

    public static void main(String[] args) {
        int[] suite = IntStream.range(0, 12).toArray(); // to shuffle
        new Gops().play(suite, 2);
    }
}
