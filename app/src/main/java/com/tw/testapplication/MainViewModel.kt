package com.tw.testapplication

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tw.testapplication.model.GroupInfo
import com.tw.testapplication.model.PatternDto
import com.tw.testapplication.model.patterns

class MainViewModel() : ViewModel() {

    val color = MutableLiveData(Color.DarkGray)

    fun processPatterns(patterns: List<PatternDto>): List<GroupInfo> {
        return patterns
            .filter { it.level > 100 }
            .groupBy { it.hue }
            .map { (hue, patterns) ->
                GroupInfo(hue, patterns.sortedBy { it.level })
            }
            .sortedBy { it.hue }
    }

    val getPatterns = processPatterns(patterns)
}