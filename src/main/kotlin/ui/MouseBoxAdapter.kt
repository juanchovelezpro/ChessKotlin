package ui

import model.Box
import model.Chess
import model.Piece
import model.Team
import network.Packet
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class MouseBoxAdapter(val panelBoard: BoardPanel, val boxTouched: Box) : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent?) {
        println("--------- TOUCHED -----------")
        println(boxTouched)
        println("-----------------------------")

        val chess = panelBoard.window.chess
        val pieceTouched = boxTouched.piece

        // If is not on movement
        if (!chess.onMovement) {
            if (pieceTouched != null) {
                handleBehaviour(pieceTouched, chess)
            }
            // If it is on movement
        } else {
            handleMovement()
        }

        updateUI(chess)

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

        val pMovements = getPossibleMovements(chess.activePiece)

        // Add all possible movements to active boxes
        chess.possibleBoxes.addAll(pMovements)

        // Painting possible movements of the piece in the selected box
        for (activeBox in chess.possibleBoxes) {
            activeBox.color = Box.POSSIBLE_COLOR
        }

        // Painting the selected box
        boxTouched.color = Box.SELECTED_COLOR
        chess.selectedBox = boxTouched

    }

    private fun getPossibleMovements(piece: Piece?): ArrayList<Box> {
        return piece?.possibleMovementsWithCheck()!!
    }

    private fun handleMovement() {

        val chess = panelBoard.window.chess

        // If it is on movement and touch the same box (To avoid a movement on the same box)
        // This just ignored that action
        if (chess.activePiece?.position!! != boxTouched.position) {

            // Check if the box touched is a possible movement, if so, then move the piece
            if (chess.possibleBoxes.contains(boxTouched)) {
                // Do the movement
                chess.activePiece?.move(chess.board, boxTouched.position)
                // Disable all before send
                disableAll(chess)
                // Send the current state of the Chess
                handleMovementNetwork()
            }
        }

        // Disable all in case the active piece were not moved
        disableAll(chess)

    }

    private fun disableAll(chess: Chess) {
        chess.activePiece = null
        chess.onMovement = false
        disablePreviousActiveBoxes()
    }

    private fun handleMovementNetwork() {
        // This is for handle the Serilization of GUI so in this way only send logic, and every packet received the observer must assign the observer to chess again
        panelBoard.window.chess.observer = null
        if (panelBoard is ServerBoardPanel) {
            panelBoard.server.send(Packet(panelBoard.window.chess))
        } else if (panelBoard is ClientBoardPanel) {
            panelBoard.client?.send(Packet(panelBoard.window.chess))
        }
    }

    private fun handleBehaviour(pieceTouched: Piece?, chess: Chess) {
        if (panelBoard is ServerBoardPanel) {
            handleOnlyWhite(pieceTouched, chess)
        } else if(panelBoard is ClientBoardPanel) {
            handleOnlyBlack(pieceTouched, chess)
        } else{
            handleTurnTwoPlayers(pieceTouched,chess)
        }
    }

    private fun handleOnlyWhite(pieceTouched: Piece?, chess: Chess) {
        if (pieceTouched?.team == Team.WHITE && chess.whiteTurn) {
            enableActiveBoxes()
        }
    }

    private fun handleOnlyBlack(pieceTouched: Piece?, chess: Chess) {
        if (pieceTouched?.team == Team.BLACK && !chess.whiteTurn) {
            enableActiveBoxes()
        }
    }

    private fun handleTurnTwoPlayers(pieceTouched: Piece?, chess: Chess) {
        // Handle turn two player in same device
        if (pieceTouched?.team == Team.WHITE && chess.whiteTurn || pieceTouched?.team == Team.BLACK && !chess.whiteTurn) {
            enableActiveBoxes()
        }
    }

    private fun updateUI(chess: Chess) {
        // Update UI
        if (panelBoard is ClientBoardPanel) {
            panelBoard.drawBoard(chess.rotateBoard180())
        } else {
            panelBoard.drawBoard(chess.board)
        }
    }

}