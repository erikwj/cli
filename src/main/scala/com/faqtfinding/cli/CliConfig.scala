package com.faqtfinding.cli

import scala.sys.process._
import ParamShow._

/**
 * Holds the configuration parameters of Pdf Kit
 */
trait CliConfig {
  
  val username = Parameter[String]("username")
  
  val password = Parameter[String]("password")
}

object CliConfig {

  /**
   * Generates a sequence of command line parameters from a `CliConfig`
   */
  def toParameters(config: CliConfig): Seq[String] = {
    import config._
    Seq(
      username.toParameter,
      password.toParameter
    ).flatten
  }

  /**
   * Attempts to find the executable in the system path.
   * @return
   */
  def findExecutable(executableName:String): Option[String] = try {
    Option(s"which $executableName".!!.trim).filter(_.nonEmpty)
  } catch {
    case _: RuntimeException => None
  }

}