package com.faqtfinding.cli

import scala.sys.process._
import java.io.File
import scalaz._
import Scalaz._

case class Executable(name: String, path: String) //Why does class not give access to executable.path in abstract class????

object Executable {

  /**
   * Check whether the executable is actually executable, if it isn't
   * a NoExecutableException is thrown.
   * Use scalaz Validation / \/ 
   */
  def checkExecutability(path: String): Option[String] = {
    val executableFile = new File(path)
    if(executableFile.canExecute) Some(path) else None 
  }
  
  /**
   * Attempts to find the executable in the system path.
   * @return
   */
  private def findExecutable(executableName:String): Option[String] = try {
    Option(s"which $executableName".!!.trim).filter(_.nonEmpty)
  } catch {
    case _: RuntimeException => None
  }

  def validate(name: String): \/[String,Executable] = 
    for {
      path <- findExecutable(name) \/> "no executable found"
      executablepath <- checkExecutability(path) \/> "path is not executable"
    } yield Executable(name, executablepath) 

}


abstract class CLI(executable: Executable, config: CliConfig) {

  /**
   * Runs the commandline tool 
   */
  def run[A, B](input: A, output: B)(implicit inputSource: InputSourceFormat[A], outputSource: OutputSourceFormat[B]): Int = {
    val commandLine = toCommandLine(input, output)
    val process = Process(commandLine)
    def source = inputSource.sourceFrom(input) _
    def sink = outputSource.sinkTo(output) _

    (sink compose source)(process).!
  }

  /**
   * Generates the command line needed to execute the Executable
   */
  def toCommandLine[A: InputSourceFormat, B: OutputSourceFormat](input: A, output: B): Seq[String]


}
