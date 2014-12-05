package com.faqtfinding.spdf

import com.faqtfinding.cli._
import scala.sys.process._
import java.io.File

trait Executable {
  def executablePath: Option[String]
  def executableName: Option[String]
}

abstract class CLI(executablePath: String, config: CliConfig) {

  val executable:String

  validateExecutable_!(executablePath)

  /**
   * Runs the conversion tool to convert sourceDocument HTML into
   * destinationDocument PDF.
   */
  def run[A, B](sourceDocument: A, destinationDocument: B)(implicit sourceDocumentLike: InputSourceFormat[A], destinationDocumentLike: OutputSourceFormat[B]): Int = {
    val commandLine = toCommandLine(sourceDocument, destinationDocument)
    println(commandLine.mkString(" "))
    val process = Process(commandLine)
    def source = sourceDocumentLike.sourceFrom(sourceDocument) _
    def sink = destinationDocumentLike.sinkTo(destinationDocument) _

    (sink compose source)(process).!
  }

  /**
   * Generates the command line needed to execute `wkhtmltopdf`
   */
  private def toCommandLine[A: InputSourceFormat, B: OutputSourceFormat](source: A, destination: B): Seq[String] =
    Seq(executablePath) ++
      PdfConfig.toParameters(config) ++
      Seq(
        "--quiet",
        implicitly[InputSourceFormat[A]].commandParameter(source),
        implicitly[OutputSourceFormat[B]].commandParameter(destination)
      )

  // /**
  //  * Check whether the executable is actually executable, if it isn't
  //  * a NoExecutableException is thrown.
  //  */
  // private def validateExecutable_!(executablePath: String): Unit = {
  //   val executableFile = new File(executablePath)
  //   if(!executableFile.canExecute) throw new NoExecutableException(executable,executableFile.getAbsolutePath)
  // }

}
