package com.lenatopoleva.dictionary.navigation

import com.lenatopoleva.dictionary.view.wordslist.WordsListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class WordsListScreen() : SupportAppScreen() {
        override fun getFragment() = WordsListFragment.newInstance()
    }

}