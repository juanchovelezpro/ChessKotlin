package model

import java.io.Serializable

class Chess : ChessActions, Serializable {

    val board = Array(8) { x -> Array(8) { y -> Box(Coordinate(x, y), null) } }

    val possibleBoxes = mutableListOf<Box>()
    var selectedBox: Box? = null
    var onMovement = false
    var activePiece: Piece? = null
    var piecePromotion: Piece? = null
    var whiteTurn = true
    val whitePiecesAlive = ArrayList<Piece>()
    val blackPiecesAlive = ArrayList<Piece>()
    var whiteInCheck = false
    var blackInCheck = false
    var observer: ChessActions? = null
    lateinit var blackKing: King
    lateinit var whiteKing: King

    init {
        initBoard()
    }

    private fun initBoard() {

        for (i in 0..board.lastIndex) {
            for (j in 0..board[0].lastIndex) {
                putBoxColors(i, j)
                putPiece(i, j)

                val piece = board[i][j].piece
                addAlivePiece(piece)

            }
        }

        blackKing = board[0][4].piece as King
        whiteKing = board[7][4].piece as King
    }

    private fun addAlivePiece(piece: Piece?) {
        if (piece != null) {
            if (piece.team == Team.WHITE) {
                whitePiecesAlive.add(piece)
            } else {
                blackPiecesAlive.add(piece)
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
                Pawn(Coordinate(i, j), Team.BLACK, this)
            }

            // Putting White Pieces
            6 -> {
                Pawn(Coordinate(i, j), Team.WHITE, this)
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
            1, 6 -> Knight(Coordinate(i, j), team, this)
            3 -> Queen(Coordinate(i, j), team, this)
            4 -> King(Coordinate(i, j), team, this)
            2, 5 -> Bishop(Coordinate(i, j), team, this)
            else -> Rook(Coordinate(i, j), team, this)
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

    fun rotateBoard180(): Array<Array<Box>> {

        val rotated = Array(8) { x -> Array(8) { y -> Box(Coordinate(x, y), null) } }

        var i = 0
        var j = board.lastIndex

        while (i <= board.lastIndex && j >= 0) {
            rotated[i++] = board[j--].reversedArray()
        }

        return rotated

    }

    // This is for reset properly the active "can be killed en passant" Pawn
    private fun handleActiveEnPassant() {
        if (whiteTurn) {
            whitePiecesAlive.forEach { piece -> if (piece is Pawn) piece.disableCanBeKilledEnPassant() }
        } else {
            blackPiecesAlive.forEach { piece -> if (piece is Pawn) piece.disableCanBeKilledEnPassant() }
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

        piece.handleMovement(from, to)

        println("-------------------- MOVEMENT -----------------------------")
        println("$piece ${piece.team} has been moved from $from to $to")
        println("-----------------------------------------------------------")
    }

    override fun onPromotion(pawn: Pawn, promPosition: Coordinate): Piece {
        val piece = observer?.onPromotion(pawn, promPosition)
        piecePromotion = piece
        return piece!!
    }

    private fun handleOnPromotion() {
        if (piecePromotion != null) {
            val pawnToRemove = board[piecePromotion?.position!!.x][piecePromotion?.position!!.y].piece
            if (piecePromotion?.team == Team.WHITE) {
                whitePiecesAlive.remove(pawnToRemove)
                whitePiecesAlive.add(piecePromotion!!)
            } else {
                blackPiecesAlive.remove(pawnToRemove)
                blackPiecesAlive.add(piecePromotion!!)
            }

            board[pawnToRemove?.position!!.x][pawnToRemove.position.y].piece = piecePromotion
            piecePromotion = null
        }
    }

    override fun onCheck(team: Team) {
        println("-------------------- CHECK --------------------")
        println("Team -> $team is in check!!!!")
        println("-----------------------------------------------")
    }

    override fun onCheckMate(winner: Team, loser: Team) {
        println("------------ ¡¡¡¡ CHECKMATE !!!! -------------")
        println("Winner: $winner ----------- Loser: $loser")
        println("----------------------------------------------")
    }

    override fun onTie() {
        println("Nobody wins...")
    }

    private fun verifyCheckAndTie(king: King) {
        val enemiesPossibleMovements = ArrayList<Box>()
        val myTeam = king.team
        val boxKing = board[king.position.x][king.position.y]

        if (myTeam == Team.WHITE) {

            blackPiecesAlive.forEach { enemy ->
                enemiesPossibleMovements.addAll(enemy.possibleMovements())
            }

            whiteInCheck = enemiesPossibleMovements.contains(boxKing)

            val whitePossibleMovements = whitePossibleMovements()

            if (whiteInCheck) {
                onCheck(Team.WHITE)
                if (whitePossibleMovements.isEmpty()) {
                    onCheckMate(Team.BLACK, Team.WHITE)
                }
            } else {
                if (whitePossibleMovements.isEmpty()) {
                    onTie()
                }
            }


        } else {

            whitePiecesAlive.forEach { enemy ->
                enemiesPossibleMovements.addAll(enemy.possibleMovements())
            }

            blackInCheck = enemiesPossibleMovements.contains(boxKing)

            val blackPossibleMovements = blackPossibleMovements()

            if (blackInCheck) {
                onCheck(Team.BLACK)
                if (blackPossibleMovements.isEmpty()) {
                    onCheckMate(Team.WHITE, Team.BLACK)
                }
            } else {
                if (blackPossibleMovements.isEmpty()) {
                    onTie()
                }
            }

        }
    }

    private fun whitePossibleMovements(): ArrayList<Box> {

        val movements = ArrayList<Box>()

        whitePiecesAlive.forEach { white ->
            movements.addAll(white.possibleMovementsWithCheck())
        }

        return movements

    }

    private fun blackPossibleMovements(): ArrayList<Box> {

        val movements = ArrayList<Box>()

        blackPiecesAlive.forEach { black ->
            movements.addAll(black.possibleMovementsWithCheck())
        }

        return movements

    }

    override fun onTurnChanged() {
        handleOnPromotion()

        whiteTurn = !whiteTurn

        handleActiveEnPassant()

        if (whiteTurn) {
            verifyCheckAndTie(whiteKing)
        } else {
            verifyCheckAndTie(blackKing)
        }

        println(whitePiecesAlive)
        println(blackPiecesAlive)

        observer?.onTurnChanged()

    }

}