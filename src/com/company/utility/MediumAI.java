package com.company.utility;

import java.util.LinkedList;

/**
 * Represents the second of the three supported AI levels in this game
 */
public class MediumAI extends AI {

    /**
     * Returns moves that the <em>move(Game)</em> method will consider as options to look at while choosing
     * the best move to play
     *
     * @param game represents the current game
     * @return list of all the moves with the highest priority to attack/defend in the game
     */
    @Override
    protected final LinkedList<Short> getPriorityMoves(Game game) {

        int maxPriority = 0;
        int currentPriority;
        LinkedList<Short> resultList = new LinkedList<>(), priorityList = new LinkedList<>();

        // Store the open move's priorities in the priorityList variable
        for(short move : game.free) {
            currentPriority = moveScore(game, getMove(move, game.getBoardSize()));
            priorityList.add((short) currentPriority);
            maxPriority = Math.max(maxPriority, currentPriority);
        }

        // Store the moves with the best priority (for the medium ai) in the resultList variable
        for(int i=0; i<game.free.size(); i++) {
            if(priorityList.get(i) == maxPriority) {
                resultList.add(game.free.get(i));
            }
        }

        return resultList;
    }
}