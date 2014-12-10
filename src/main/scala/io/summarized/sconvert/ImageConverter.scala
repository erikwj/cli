package io.summarized.tools

import io.summarized.cli._
import scala.sys.process._
import java.io.File

class ImageConverter(executable: Executable, config: ImageConverterConfig) extends CLI(executable,config) {
  
  /**
   * Generates the command line needed to execute the Executable
   */
  def toCommandLine[A: InputSourceFormat, B: OutputSourceFormat](input: A, output: B): Seq[String] = 
    Seq(executable.path) ++
      ImageConverterConfig.toParameters(config) ++
      Seq(
        implicitly[InputSourceFormat[A]].commandParameter(input),
        implicitly[OutputSourceFormat[B]].commandParameter(output)
      )
}

object ImageConverter {

  /**
   * Creates a new instance of ImageConverter with the passed configuration
   */
  def apply(executable: Executable, config: ImageConverterConfig): ImageConverter = new ImageConverter(executable, config)
  def apply(config: ImageConverterConfig): ImageConverter = {
    val executable = "convert"
    val exe = Executable.validate(executable).getOrElse(sys.error("no executable"))
    new ImageConverter(exe, config)
  }

}