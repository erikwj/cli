package com.faqtfinding.tools

import com.faqtfinding.cli._
import scala.sys.process._
import java.io.File

class ImageConverter(executable: Executable, config: ImageConverterConfig) extends CLI(executable,config) {
		  /**
   * Generates the command line needed to execute the Executable
   */

   // println("Config >> " + ImageConverterConfig.toParameters(config))

  def toCommandLine[A: InputSourceFormat, B: OutputSourceFormat](input: A, output: B): Seq[String] = 
    Seq(executable.path) ++
      ImageConverterConfig.toParameters(config) ++
      Seq(
        //"--quiet",
        implicitly[InputSourceFormat[A]].commandParameter(input),
        implicitly[OutputSourceFormat[B]].commandParameter(output)
      )

}

object ImageConverter {

  /**
   * Creates a new instance of ImageConverter with the passed configuration
   */
  def apply(executable: Executable, config: ImageConverterConfig): Option[ImageConverter] = Some(new ImageConverter(executable, config))
  def apply(config: ImageConverterConfig): Option[ImageConverter] = {
    val executable = "convert"
    Executable.validate(executable) map {(exe) => new ImageConverter(exe, config)}
   
  }
}