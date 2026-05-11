package model;

import java.util.ArrayList;
import java.util.List;
import model.move_tools.BoardState;

public class Chess {

    List<BoardState> history;

    public Chess() {
        history = new ArrayList<>();
        startGame();
    }

    public boolean startGame() {
        return true;
    }
}
