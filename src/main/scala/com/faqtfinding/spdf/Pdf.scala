package com.faqtfinding.tools

import com.faqtfinding.cli._
import scala.sys.process._
import java.io.File

class Pdf(executable: Executable, config: PdfConfig) extends CLI(executable,config) 

object Pdf {

  /**
   * Creates a new instance of Pdf with the passed configuration
   */
  // def apply(executable: Executable, config: PdfConfig): Pdf = new Pdf(executable, config)
  def apply(config: PdfConfig): Option[Pdf] = {
    val executable = "wkhtmltopdf"
    Executable.validate(executable) map {(exe) => new Pdf(exe, config)}
   
  }
}

