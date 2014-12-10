package com.faqtfinding.tools

import com.faqtfinding.cli._
import scala.sys.process._
import java.io.File

class Pdf(executable: Executable, config: PdfConfig) extends CLI(executable,config) {
  /**
   * Generates the command line needed to execute the Executable
   */
  def toCommandLine[A: InputSourceFormat, B: OutputSourceFormat](input: A, output: B): Seq[String] = 
    Seq(executable.path) ++
      PdfConfig.toParameters(config) ++
      Seq(
        "--quiet",
        implicitly[InputSourceFormat[A]].commandParameter(input),
        implicitly[OutputSourceFormat[B]].commandParameter(output)
      )

}

object Pdf {

  /**
   * Creates a new instance of Pdf with the passed configuration
   */
  // def apply(executable: Executable, config: PdfConfig): Pdf = new Pdf(executable, config)
  def apply(config: PdfConfig): Pdf = {
    val executable = "wkhtmltopdf"
    val exe = Executable.validate(executable).getOrElse(sys.error("no executable"))
    
    // Executable.validate(executable) map {(exe) => 
    new Pdf(exe, config)
   
  }
}

