package exercise7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    val lines = fixturesText.drop(1)
    val fixtureMap : MutableMap<Int, MutableList<Match>> = mutableMapOf()
    for (line in lines) {
        val (roundStr, _, team1, resStr, team2) = line.split(",")
        val round = roundStr.toInt()
        val resSplit = resStr.split("-")
        val homeScoreInt = resSplit[0].toInt()
        val awayScoreInt = resSplit[1].toInt()
        val match = Match(Team(team1), Team(team2), homeScoreInt, awayScoreInt)
        if (fixtureMap[round] == null) {
            fixtureMap[round] = mutableListOf()
        }
        fixtureMap[round]?.add(match)
    }
    return fixtureMap.map { fixtureEntry -> Fixture(fixtureEntry.key, fixtureEntry.value) }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("exercise7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val homeTeams : Set<Team> = fixtures.flatMap { fixture -> fixture.matches }.map { match: Match -> match.homeTeam }.toSet()
    val awayTeams : Set<Team> = fixtures.flatMap { fixture -> fixture.matches }.map { match: Match -> match.awayTeam }.toSet()
    val teams = homeTeams.union(awayTeams).toList()
    // Create league object
    val league: LeagueApi = League(teams, fixtures)


    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}