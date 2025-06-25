package com.example.bubbles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bubbles.model.Ball
import com.example.bubbles.utils.COL_COUNT
import com.example.bubbles.utils.GameRepository
import com.example.bubbles.utils.ROW_COUNT
import com.example.bubbles.utils.bubbleColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GameViewModel(private val repository: GameRepository): ViewModel() {

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _state = MutableStateFlow(generateInitialGrid())
    val state: StateFlow<List<List<Ball?>>> = _state

    private fun generateInitialGrid(rows: Int = ROW_COUNT, cols: Int = COL_COUNT): List<List<Ball?>>{
        val colors = bubbleColors
        return List(rows){
            List(cols){
                Ball(color = colors.random())
            }
        }
    }

    private fun findGroup(grid: List<List<Ball?>>, x: Int, y: Int): Set<Pair<Int, Int>> {
        val target = grid.getOrNull(y)?.getOrNull(x) ?: return emptySet()
        val visited = mutableSetOf<Pair<Int, Int>>()
        val stack = mutableListOf(Pair(x,y))

        while (stack.isNotEmpty()){
            val (cx, cy) = stack.removeAt(stack.lastIndex)
            if (cy !in grid.indices || cx !in grid[cy].indices) continue
            val current = grid[cy][cx] ?: continue
            val pos = Pair(cx, cy)
            if (pos in visited || current.color != target.color) continue
            visited.add(pos)


            stack.addAll(
                listOf(
                    Pair(cx + 1, cy), Pair(cx - 1, cy),
                    Pair(cx, cy + 1), Pair(cx, cy - 1)
                )
            )
        }

        return visited
    }

    private fun hasAnyValidGroupsLeft(grid: List<List<Ball?>>): Boolean {
        val visited = mutableSetOf<Pair<Int, Int>>()

        for (y in grid.indices){
            for (x in grid[y].indices){
                val pos = Pair(x, y)
                if (pos in visited || grid[y][x] == null) continue

                val group = findGroup(grid, x, y)
                visited.addAll(group)

                if (group.size >= 2) return true
            }
        }
        return false
    }


    fun onCellClick(x: Int, y: Int) {
        val grid = _state.value
        val group = findGroup(grid, x, y)
        if (group.size < 2) return

        //Scoring
        if (group.size >= 2) {
            val gained = (group.size - 2) * (group.size - 2)
            _score.value += gained
        }


        // Remove popped group
        val newGrid = grid.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, ball ->
                if (group.contains(Pair(colIndex, rowIndex))) null else ball
            }
        }

        // Drop bubbles down
        val dropped = newGrid.transpose().map { col ->
            val nonNulls = col.filterNotNull()
            val padded = List(ROW_COUNT - nonNulls.size) { null } + nonNulls
            padded.takeLast(ROW_COUNT)
        }

        // Shift empty columns left
        val nonEmptyColumns = dropped.filter { it.any { b -> b != null } }
        val emptyColumns = List(COL_COUNT - nonEmptyColumns.size) {
            List(ROW_COUNT) { null }
        }

        val shifted = (nonEmptyColumns + emptyColumns).transpose()

        //bonus for clearing the board
        if (shifted.all { row -> row.all { it == null }}){
            _score.value += 1000
        }

        // Sanity check
        require(shifted.size == ROW_COUNT)
        require(shifted.all { it.size == COL_COUNT })

        _state.value = shifted

        if (!hasAnyValidGroupsLeft(shifted)){
            _isGameOver.value = true
            repository.clear()
        }
    }


    fun resetGame() {
        _state.value = generateInitialGrid()
        _score.value = 0
        _isGameOver.value = false
        repository.clear()
    }

    fun saveGame() {
        repository.save(_score.value, _state.value)
    }

    fun restoreGame(){
       repository.load()?.let { (savedScore, savedGrid) ->
           if (!hasAnyValidGroupsLeft(savedGrid)){
               repository.clear()
               return
           }
           _score.value = savedScore
           _state.value = savedGrid
           _isGameOver.value = false
       }
    }

    fun hasSavedGame(): Boolean = repository.hasSave()

    fun startNewGame(){
        _state.value = generateInitialGrid()
        _score.value = 0
        _isGameOver.value = false
        repository.clear()
    }

    private fun <T> List<List<T>>.transpose(): List<List<T>> {
        if (isEmpty()) return emptyList()
        return (0 until this[0].size).map { col ->
            this.map { row -> row[col]}
        }
    }

    private fun shiftColumnsLeft(grid: List<List<Ball?>>): List<List<Ball?>> {
        val columns = grid.transpose()

        val nonEmptyColumns = columns.filter { column -> column.any { it != null}}

        val emptyColumns = List(grid[0].size - nonEmptyColumns.size) {
            List(grid.size) { null }
        }

        return (nonEmptyColumns + emptyColumns).transpose()
    }

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver

    init {
        restoreGame()

        viewModelScope.launch {
            isGameOver.collect { gameOver ->
                if (gameOver){
                    repository.clear()
                }
            }
        }
    }

}