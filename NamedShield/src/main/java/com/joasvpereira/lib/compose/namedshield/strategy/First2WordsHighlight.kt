package com.joasvpereira.lib.compose.namedshield.strategy

/**
 * Given a string this strategy will return other string with the first two letters of a sentence, when possible. We define a word by an group of characters between a space, as such if a input
 * string don't have any space we will return the first two letters of this given string. Although if the input is a string with length == 1 will be return this only character.
 *
 * In case of an empty string the return value will be the one defined on [emptySymbol].
 *
 * By default return value will be in upper case but this can be defined on [isUpperCase].
 *
 * The table bellow describes the outcome for a given input:
 *
 * |   input  |   output   |
 * |:----------:|:------------:|
 * | "first word"  |   "FW"  |
 * | "first"      |  "FI"  |
 * | "f"          |  "F"   |
 * | " "          |  "?"   |
 *
 *
 * @param isUpperCase will upper case the output string when true.
 * @param emptySymbol is a fallback character used when a input string is empty.
 */
class First2WordsHighlight(private val isUpperCase: Boolean = true, val emptySymbol: Char = '?') : TextHighlightStrategy {

    override fun capture(from: String): String =
        from.trim().split(" ").let { words ->
            val firstWord = words[0]
            val isFirstWordMoreThatOneLetter = firstWord.length >= 2
            val hasTwoOrMoreWords = words.size >= 2
            when {
                firstWord.isBlank() -> emptySymbol.toString()
                hasTwoOrMoreWords -> getFirstLettersOfFirstTwoWords(words)

                isFirstWordMoreThatOneLetter -> getFirstTwoLetters(firstWord)
                else -> getFirstLetter(firstWord)
            }
        }.run {
            if (isUpperCase) {
                return@run this.uppercase()
            }
            this
        }

    private fun getFirstLettersOfFirstTwoWords(words: List<String>): String {
        val firstWord = words[0]
        val secondWord = words[1]
        return "${getFirstLetter(firstWord)}${getFirstLetter(secondWord)}"
    }

    private fun getFirstLetter(word: String) = "${word[0]}"

    private fun getFirstTwoLetters(word: String) = "${word[0]}${word[1]}"
}
