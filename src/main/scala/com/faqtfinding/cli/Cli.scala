package com.faqtfinding.cli

import scala.sys.process._
import java.io.File

case class Executable(name: String, path: String) //Why does class not give access to executable.path in abstract class????

object Executable {

  /**
   * Check whether the executable is actually executable, if it isn't
   * a NoExecutableException is thrown.
   */
  def checkExecutability(path: String): Option[String] = {
    val executableFile = new File(path)
    if(executableFile.canExecute) Some(path) else None 
  }

  def validate(name: String): Option[Executable] = 
    for {
      path <- findExecutable(name)
      executablepath <- checkExecutability(path)
    } yield new Executable(name, executablepath) 

  /**
   * Attempts to find the executable in the system path.
   * @return
   */
  private def findExecutable(executableName:String): Option[String] = try {
    Option(s"which $executableName".!!.trim).filter(_.nonEmpty)
  } catch {
    case _: RuntimeException => None
  }

}




abstract class CLI(executable: Executable, config: CliConfig) {

  /**
   * Runs the commandline tool 
   */
  def run[A, B](sourceDocument: A, destinationDocument: B)(implicit sourceDocumentLike: InputSourceFormat[A], destinationDocumentLike: OutputSourceFormat[B]): Int = {
    val commandLine = toCommandLine(sourceDocument, destinationDocument)
    val process = Process(commandLine)
    def source = sourceDocumentLike.sourceFrom(sourceDocument) _
    def sink = destinationDocumentLike.sinkTo(destinationDocument) _

    (sink compose source)(process).!
  }

  /**
   * Generates the command line needed to execute the Executable
   */
  def toCommandLine[A: InputSourceFormat, B: OutputSourceFormat](source: A, destination: B): Seq[String] =
    Seq(executable.path) ++
      CliConfig.toParameters(config) ++
      Seq(
        "--quiet",
        implicitly[InputSourceFormat[A]].commandParameter(source),
        implicitly[OutputSourceFormat[B]].commandParameter(destination)
      )

}
