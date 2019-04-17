name := "nodeConventer"
 
version := "1.0" 
      
lazy val `nodeconventer` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "spray repo" at "http://repo.spray.io"
      
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
  "org.postgresql" % "postgresql" % "42.2.5"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      