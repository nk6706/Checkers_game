<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

    <div>
      <#if currentUser??>
        Other Signed-in Players:</br>
        <#if playerList??>

            <#list playerList as n>
              <ul>
                <li><form action="/game" method="post"><button type="submit" name="opponent" value="${n}">${n}</button></form></li>
              </ul>
            </#list>

        </#if>
          Games in Progress:</br>
          <#if gameList??>
              <#list gameList as id, value>
                  <ul>
                      <li>
                          <form action="/spectator" method="get">
                              <#-- TODO: Get rid of Law of Demeter Violation below                        -->
                              <button type="submit" name="gameID" value="${id}">${value.getRedPlayer().getUsername()} vs ${value.getWhitePlayer().getUsername()}</button>
                          </form>
                      </li>
                  </ul>
              </#list>
          </#if>
      <#else>
        Total players signed in: ${totalPlayers}</br>
      </#if>

    </div>

  </div>

</div>
</body>

</html>
