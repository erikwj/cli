package com.faqtfinding.tools


import com.faqtfinding.cli._
import scala.sys.process._
import com.faqtfinding.cli.ParamShow._

/**
 * Holds the configuration parameters of Pdf Kit
 */
trait ImageConverterConfig extends CliConfig {

  /**
   * Options for `wkhtmltopdf` command
   * See `wkhtmltopdf --extended-help` for a description of each option
   */
  val density = Parameter[Int]("density")

}

object ImageConverterConfig {

  /**
   * An instance of the default configuration
   */
  object default extends ImageConverterConfig

  /**
   * Generates a sequence of command line parameters from a `ImageConverterConfig`
   */
  def toParameters(config: ImageConverterConfig): Seq[String] = {
    import config._
    Seq(
      density.toParameter
   
    ).flatten
  }

}
