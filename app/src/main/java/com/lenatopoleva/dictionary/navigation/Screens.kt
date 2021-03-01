package com.lenatopoleva.dictionary.navigation

import com.lenatopoleva.dictionary.view.descriptionscreen.DescriptionFragment
import com.lenatopoleva.dictionary.view.wordslist.WordsListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class WordsListScreen() : SupportAppScreen() {
        override fun getFragment() = WordsListFragment.newInstance()
    }

    class DescriptionScreen(val word: String, val description: String, val pictureUrl: String?): SupportAppScreen() {
        override fun getFragment() = DescriptionFragment.newInstance(word, description, pictureUrl)
    }

}