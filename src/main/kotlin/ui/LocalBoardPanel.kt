package ui

import model.ChessActions

class LocalBoardPanel(window: Window): BoardPanel(window), ChessActions {

    override fun onTurnChanged() {
        super.onTurnChanged()
        drawBoard(window.chess.board)
        window.refresh()
    }

}