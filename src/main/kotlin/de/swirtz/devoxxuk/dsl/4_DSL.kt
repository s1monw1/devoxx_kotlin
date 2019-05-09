package de.swirtz.devoxxuk.dsl

import java.time.LocalDateTime

// @formatter:off




class Conference(val name: String, val location: String) {
    private val schedule = mutableListOf<Talk>()

    fun addTalk(t: Talk) {
        schedule.add(t)
    }

    val talks
        get() = schedule.toList()
}

enum class TalkType {
    CONFERENCE, KEYNOTE
}

data class Talk(
    val topic: String,
    val speaker: String,
    val time: LocalDateTime,
    val type: TalkType = TalkType.CONFERENCE
)




fun main(args: Array<String>) {
    val devoxx = Conference("Devoxx UK 2019", "London")
    val t1 = Talk("Topic1", "Speaker1", LocalDateTime.parse("2018-05-07T12:00"))
    val t2 = Talk("Topic2", "Speaker2", LocalDateTime.parse("2018-05-07T15:00"))
    devoxx.addTalk(t1)
    devoxx.addTalk(t2)



    // we could use `apply` to make this code cleaner.
    // but we want to go a step further...


    conference {
        name = "Devoxx UK 2019"
        location = "London"

        talks {
            conferenceTalk("Kotlin 101", "Speaker1", LocalDateTime.parse("2018-05-07T10:00"))
            keynoteTalk("Humour That Works", "Speaker2", LocalDateTime.parse("2018-05-07T15:00"))

            conferenceTalk at "2018-05-08T10:00" by "Speaker3" titled "The Cloud"
            keynoteTalk at "2018-05-07T17:00" by "Speaker4" titled "Infrastructure Craftsmanship"

            +Talk("Security 101", "Speaker5", LocalDateTime.parse("2018-05-07T15:00"))
        }

        talks.conferenceTalk("Why JavaScript sucks", "Speaker6", LocalDateTime.parse("2018-05-07T15:00"))
    }

}


inline fun conference(config: ConferenceDSL.() -> Unit): Conference {
    val dsl = ConferenceDSL().apply(config)
    return Conference(dsl.name, dsl.location).apply {
        dsl.talkList.forEach(this::addTalk)
    }
}







@ConfDslMarker
class ConferenceDSL() {

    private val _talkList = mutableListOf<Talk>()

    val talkList
        get() = _talkList.toList()

    lateinit var name: String
    lateinit var location: String

    val talks = TalkConfigDSL()

    @ConfDslMarker
    inner class TalkConfigDSL() {

        private val _talkList = this@ConferenceDSL._talkList

        operator fun invoke(config: TalkConfigDSL.() -> Unit) {
            this.apply(config)
        }






        fun conferenceTalk(topic: String, speaker: String, time: LocalDateTime) {
            _talkList.add(Talk(topic, speaker, time, TalkType.CONFERENCE))
        }



        fun keynoteTalk(topic: String, speaker: String, time: LocalDateTime) {
            _talkList.add(Talk(topic, speaker, time, TalkType.KEYNOTE))
        }



        val conferenceTalk
            get() = EmptyTalk(TalkType.CONFERENCE)
        val keynoteTalk
            get() = EmptyTalk(TalkType.KEYNOTE)


        inner class EmptyTalk(val type: TalkType) {
            infix fun at(timeString: String) =
                TimedTalk(this, LocalDateTime.parse(timeString))
        }




        inner class TimedTalk(
            val previous: EmptyTalk,
            val time: LocalDateTime) {
            infix fun by(speaker: String) =
                TimedAndAuthoredTalk(this,  speaker)
        }




        inner class TimedAndAuthoredTalk(
            private val previous: TimedTalk,
            private val speaker: String
        ) {
            infix fun titled(topic: String) =
                _talkList.add(
                    Talk(topic, speaker, previous.time, previous.previous.type)
                )
        }



         //member extension function
        operator fun Talk.unaryPlus() = _talkList.add(this)

    }

}




















@DslMarker
annotation class ConfDslMarker

