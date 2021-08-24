package ui

import model.AIChess
import model.ChessActions
import model.Team

class SoloBoardPanel(window: Window) : BoardPanel(window), ChessActions {

    val AI = AIChess(Team.BLACK, window.chess)

}