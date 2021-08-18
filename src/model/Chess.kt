package model

import javax.print.attribute.standard.Destination
import kotlin.math.abs

class Chess : ObserverActions {

    val board = Array(8) { x -> Array(8) { y -> Box(Coordinate(x, y), null) } }

    val possibleBoxes = mutableListOf<Box>()
    var selectedBox: Box? = null
    var onMovement = false
    var activePiece: Piece? = null
    var whiteTurn = true

    companion object {
        val whitePiecesAlive = ArrayList<Piece?>()
        val blackPiecesAlive = ArrayList<Piece?>()
    }

    init {
        initBoard()
    }

    private fun initBoard() {

        for (i in 0..board.lastIndex) {
            for (j in 0..board[0].lastIndex) {
                putBoxColors(i, j)
                putPiece(i, j)

                val thePiece = board[i][j].piece
                if (thePiece != null) {
                    thePiece.observer = this
                    if (thePiece.team == Team.WHITE) {
                        whitePiecesAlive.add(thePiece)
                    } else {
                        blackPiecesAlive.add(thePiece)
                    }
                }
            }
        }
    }

    private fun putPiece(i: Int, j: Int) {


        board[i][j].piece = when (i) {

            // Putting Black Pieces
            0 -> {
                putPiece(i, j, Team.BLACK)
            }

            1 -> {
                Pawn(Coordinate(i, j), Team.BLACK)
            }

            // Putting White Pieces
            6 -> {
                Pawn(Coordinate(i, j), Team.WHITE)
            }

            7 -> {
                putPiece(i, j, Team.WHITE)
            }

            else -> {
                null
            }
        }
    }

    private fun putPiece(i: Int, j: Int, team: Team): Piece {
        return when (j) {
            1, 6 -> Knight(Coordinate(i, j), team)
            3 -> Queen(Coordinate(i, j), team)
            4 -> King(Coordinate(i, j), team)
            2, 5 -> Bishop(Coordinate(i, j), team)
            else -> Rook(Coordinate(i, j), team)
        }
    }

    private fun putBoxColors(i: Int, j: Int) {
        when {
            i % 2 == 0 -> {
                if (j % 2 == 0) {
                    board[i][j].color = Box.WHITE_COLOR
                    board[i][j].originalColor = Box.WHITE_COLOR
                } else {
                    board[i][j].color = Box.BLACK_COLOR
                    board[i][j].originalColor = Box.BLACK_COLOR
                }
            }
            else -> {
                if (j % 2 == 0) {
                    board[i][j].color = Box.BLACK_COLOR
                    board[i][j].originalColor = Box.BLACK_COLOR
                } else {
                    board[i][j].color = Box.WHITE_COLOR
                    board[i][j].originalColor = Box.WHITE_COLOR
                }
            }
        }
    }

    // Check and set if a Pawn can be killed En Passant
    private fun checkEnPassant(from: Coordinate, to: Coordinate, pawn: Pawn) {
        if (!pawn.firstMovementDone) {
            if (abs(from.x - to.x) == 2) {
                pawn.canBeKilledEnPassant = true
            }
        }
    }

    private fun handleActiveEnPassant() {
        if (whiteTurn) {
            whitePiecesAlive.forEach { piece ->
                if (piece is Pawn) {
                    if (piece.canBeKilledEnPassant) {
                        piece.canBeKilledEnPassant = false
                    }
                }
            }
        } else {
            blackPiecesAlive.forEach { piece ->
                if (piece is Pawn) {
                    if (piece.canBeKilledEnPassant) {
                        piece.canBeKilledEnPassant = false
                    }
                }
            }
        }
    }

    override fun onKill(murdered: Piece) {
        println("------------------------ KILL ---------------------------------------")
        println("$murdered - ${murdered.team} has been killed on ${murdered.position}")

        if (murdered.team == Team.WHITE) {
            whitePiecesAlive.remove(murdered)
            println("White Pieces Remaining: ${whitePiecesAlive.size} = $whitePiecesAlive")
        } else {
            blackPiecesAlive.remove(murdered)
            println("Black Pieces Remaining: ${blackPiecesAlive.size} = $blackPiecesAlive")
        }

        println("----------------------------------------------------------------------")

    }

    override fun onMovement(from: Coordinate, to: Coordinate, piece: Piece) {

        // Check En Passant
        if (piece is Pawn) {
            // Check if a Pawn can be killed En Passant
            checkEnPassant(from, to, piece)

            // Check if the movement is "En Passant"
            if (to.y != from.y) {
                val enPassantBox = board[to.x][to.y]
                if (enPassantBox.piece == null) {
                    onEnPassant(from, to, piece)
                }
            }
        }


        // Check Castling
        if (piece is King) {

            // Castling left
            if (from.y - to.y == 2) {

                onCastling(to, false)

                // Castling right
            } else if (from.y - to.y == -2) {

                onCastling(to, true)

            }

        }



        println("-------------------- MOVEMENT -----------------------------")
        println("$piece ${piece.team} has been moved from $from to $to")
        println("-----------------------------------------------------------")
    }

    override fun onEnPassant(from: Coordinate, to: Coordinate, piece: Piece) {
        println("""------------------------- SPECIAL KILL "EN PASSANT" --------------------- """)
        if (piece.team == Team.WHITE) {
            val boxMurder = board[to.x + 1][to.y]

            println("${boxMurder.piece} - ${boxMurder.piece?.team} has been killed with En Passant movement")
            blackPiecesAlive.remove(boxMurder.piece)
            boxMurder.piece = null
            println("Black Pieces Remaining: ${blackPiecesAlive.size} = $blackPiecesAlive")

        } else {

            val boxMurder = board[to.x - 1][to.y]

            println("${boxMurder.piece} - ${boxMurder.piece?.team} has been killed with En Passant movement")
            whitePiecesAlive.remove(boxMurder.piece)
            boxMurder.piece = null
            println("White Pieces Remaining: ${whitePiecesAlive.size} = $whitePiecesAlive")
        }
        println("------------------------------------------------------------------------------")
    }

    override fun onPromotion(pawn: Pawn) {
        TODO("Not yet implemented")
    }

    override fun onCastling(kingDestination: Coordinate,side: Boolean) {

        val rookBox : Box
        val rook: Piece?

        if (side) {

            rookBox = board[kingDestination.x][kingDestination.y + 1]
            rook = rookBox.piece

            rook?.position?.y = kingDestination.y - 1

            rookBox.piece = null
            board[rook?.position?.x!!][rook.position.y].piece = rook



        } else {

            rookBox = board[kingDestination.x][kingDestination.y - 2]
            rook = rookBox.piece

            rook?.position?.y = kingDestination.y + 1

            rookBox.piece = null
            board[rook?.position?.x!!][rook.position.y].piece = rook

        }


    }

    override fun onCheck() {
        TODO("Not yet implemented")
    }

    override fun onCheckMate() {
        TODO("Not yet implemented")
    }

    override fun onTurnChanged() {
        whiteTurn = !whiteTurn

        // This is for reset properly the active "can be killed en passant" Pawn
        handleActiveEnPassant()

        println(whitePiecesAlive)
        println(blackPiecesAlive)

    }

}