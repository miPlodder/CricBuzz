package l14.cb.com.cricbuzz.models

/**
 * Created by ip510 feih on 07-07-2017.
 */

data class LiveMatchPOJO (

    var matchDetail: MatchDetail

){

    data class MatchDetail(

            var unique_key : String,
            var team_1 : String,
            var team_2 : String,
            var matchStarted : Boolean

    )


}