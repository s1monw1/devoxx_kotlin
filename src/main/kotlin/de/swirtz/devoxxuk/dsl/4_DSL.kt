package de.swirtz.devoxxuk.dsl

import java.time.LocalDateTime

// @formatter:off


//DSL with operators, receivers, inline and infix


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



    //we can use apply and similar to make this cleaner.
    //But we want to go a step further


    conference {
        name = "Devoxx UK 2019"
        location = "London"

        talks {
            conferenceTalk("Topic2", "Speaker2", LocalDateTime.parse("2018-05-07T15:00"))
            keynoteTalk("Topic2", "Speaker2", LocalDateTime.parse("2018-05-07T15:00"))

            conferenceTalk at "2018-05-07T12:00" by "Speaker1" titled "Topic1"
            keynoteTalk at "2018-05-07T15:00" by "Speaker2" titled "Topic2"

            +Talk("Topic2", "Speaker2", LocalDateTime.parse("2018-05-07T15:00"), TalkType.CONFERENCE)
        }

        talks.conferenceTalk("Topic2", "Speaker2", LocalDateTime.parse("2018-05-07T15:00"))
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

    val talkList = mutableListOf<Talk>()
    lateinit var name: String
    lateinit var location: String

    val talks = TalkConfigDSL(talkList)

    @ConfDslMarker
    class TalkConfigDSL(private val talks: MutableList<Talk>) {


        operator fun invoke(config: TalkConfigDSL.() -> Unit) {
            this.apply(config)
        }






        fun conferenceTalk(topic: String, speaker: String, time: LocalDateTime) {
            talks.add(Talk(topic, speaker, time, TalkType.CONFERENCE))
        }



        fun keynoteTalk(topic: String, speaker: String, time: LocalDateTime) {
            talks.add(Talk(topic, speaker, time, TalkType.KEYNOTE))
        }



        val conferenceTalk
            get() = EmptyTalk(talks, TalkType.CONFERENCE)
        val keynoteTalk
            get() = EmptyTalk(talks, TalkType.KEYNOTE)


        class EmptyTalk(
            val talks: MutableList<Talk>,
            val type: TalkType
        ) {
            infix fun at(timeString: String) =
                TimedTalk(this, LocalDateTime.parse(timeString))
        }




        class TimedTalk(
            val previous: EmptyTalk,
            val time: LocalDateTime) {
            infix fun by(speaker: String) =
                TimedAndAuthoredTalk(this,  speaker)
        }




        class TimedAndAuthoredTalk(
            private val previous: TimedTalk,
            private val speaker: String
        ) {
            infix fun titled(topic: String) =
                previous.previous.talks.add(
                    Talk(topic, speaker, previous.time, previous.previous.type)
                )
        }



         //member extension function
        operator fun Talk.unaryPlus() = talks.add(this)



    }

}




















@DslMarker
annotation class ConfDslMarker

