package l14.cb.com.cricbuzz.models

/**
 * Created by ip510 feih on 08-07-2017.
 */
data class CricketScorePOJO(

        var matchStarted : Boolean,
        var team_1 : String ,
        var team_2 : String,
        var type : String,
        var score : String,
        var innings_requirement : String

)