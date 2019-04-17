name := "nodeConventer"
 
version := "1.0" 
      
lazy val `nodeconventer` = (project in file(".")).enablePlugins(PlayScala)

resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "spray repo" at "http://repo.spray.io",
  "meaven.org repo1" at "https://repo1.maven.org/maven2"
)
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  jdbc ,
  cache ,
  ws ,
  anorm,
  // Activate
  "net.fwbrasil" %% "activate-jdbc" % "1.7",
  "net.fwbrasil" %% "activate-play" % "1.7",
  "net.fwbrasil" %% "activate-slick" % "1.7",
  "org.postgresql" % "postgresql" % "42.2.5",
  "com.adrianhurt" %% "play-bootstrap3" % "0.4.4-P23",
  "org.apache.poi" % "poi" % "3.17"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      