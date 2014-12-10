package com.faqtfinding.cli

import scala.sys.process._
import java.io.File
import scalaz._
import Scalaz._
import scalaz.Validation.FlatMap._

case class Executable(name: String, path: String) 

object Executable {

  private def checkExecutability(path: String): ValidationNel[String,String] = {
    val executableFile = new File(path)
    if(executableFile.canExecute) path.success else "$path is not executable".failureNel[String] 
  }
  
  private def findExecutable(executable:String): ValidationNel[String,String] = try {
    Success(s"which $executable".!!.trim)
  } catch {
    case _: RuntimeException => s"no executable found for $executable".failureNel[String]
  }

  def validate(name: String): ValidationNel[String,Executable] = 
    for {
      path <- findExecutable(name) 
      executablepath <- checkExecutability(path) 
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
