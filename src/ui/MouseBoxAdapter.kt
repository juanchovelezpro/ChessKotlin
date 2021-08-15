package ui

import model.Box
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class MouseBoxAdapter(val panelBoard : BoardPanel, val boxTouched : Box) : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent?) {
        println(boxTouched)

        disablePreviousActiveBoxes()
        val chess = panelBoard.window.chess

        if(boxTouched.piece != null){

            // Getting all possible movements of the selected piece in the box.
            val pMovements = boxTouched.piece?.possibleMovements(chess.board)

            // Add all possible movements to active boxes
            chess.activeBoxes.addAll(pMovements!!)

            // Painting possible movements of the piece in the selected box
            for(activeBox in chess.activeBoxes){
                chess.board[activeBox.position.x][activeBox.position.y].color = Box.POSSIBLE_COLOR
            }

            // Painting the selected box and adding it to active box
            chess.board[boxTouched.position.x][boxTouched.position.y].color = Box.SELECTED_COLOR
            chess.activeBoxes.add(boxTouched)


        }

        // Update UI
        panelBoard.drawBoard(chess.board)

    }

    // Putting all previous active boxes to their original color
    private fun disablePreviousActiveBoxes(){

        val chess = panelBoard.window.chess

        for(box in chess.activeBoxes){
            chess.board[box.position.x][box.position.y].color = box.originalColor
        }

        chess.activeBoxes.clear()

    }


}