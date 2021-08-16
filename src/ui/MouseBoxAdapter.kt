package ui

import model.Box
import model.Team
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class MouseBoxAdapter(val panelBoard: BoardPanel, val boxTouched: Box) : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent?) {
        println("--------- TOUCHED -----------")
        println(boxTouched)
        println("-----------------------------")

        val chess = panelBoard.window.chess

        // If is not on movement
        if (!chess.onMovement) {
            if (boxTouched.piece != null) {
                enableActiveBoxes()
            }
            // If it is on movement
        } else {
            handleMovement()
        }

        // Update UI
        panelBoard.drawBoard(chess.board)

    }

    // Putting all previous active boxes to their original color
    private fun disablePreviousActiveBoxes() {

        val chess = panelBoard.window.chess

        for (box in chess.possibleBoxes) {
            box.color = box.originalColor
        }

        chess.selectedBox?.color = chess.selectedBox?.originalColor

        chess.possibleBoxes.clear()

    }

    // Paint selected box and all possible boxes of the piece.
    private fun enableActiveBoxes() {

        val chess = panelBoard.window.chess

        // It is on movement and putting the piece that is attempt to move
        chess.onMovement = true
        chess.activePiece = boxTouched.piece

        // Getting all possible movements of the selected piece in the box.
        val pMovements = boxTouched.piece?.possibleMovements(chess.board)

        // Add all possible movements to active boxes
        chess.possibleBoxes.addAll(pMovements!!)

        // Painting possible movements of the piece in the selected box
        for (activeBox in chess.possibleBoxes) {
            activeBox.color = Box.POSSIBLE_COLOR
        }

        // Painting the selected box
        boxTouched.color = Box.SELECTED_COLOR
        chess.selectedBox = boxTouched

    }

    private fun handleMovement() {

        val chess = panelBoard.window.chess

        if(chess.activePiece?.position!! != boxTouched.position) {
            chess.activePiece?.move(chess.board, boxTouched.position)
        }

        chess.activePiece = null
        chess.onMovement = false
        disablePreviousActiveBoxes()

    }

    // This is for handle "En Passant" and Promotion to Queen
    private fun handlePawnSpecialMovements() {
        if (boxTouched.piece?.team == Team.WHITE) {

        } else {

        }
    }

    private fun handleKingCastlingMovement() {

    }
}