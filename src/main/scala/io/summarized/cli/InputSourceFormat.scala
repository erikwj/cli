package io.summarized.cli

import java.io.{ByteArrayInputStream, InputStream, File}
import scala.sys.process._
import java.net.URL
import scala.annotation.implicitNotFound

/**
 * Type class that describes the kind of source documents we can read the
 * input HTML from.
 */
@implicitNotFound(msg = "Cannot find InputSourceFormat type class for ${A}")
trait InputSourceFormat[-A] {

  /**
   * The source parameter to provide to `wkhtmltopdf`
   * Defaults to read from STDIN.
   */
  def commandParameter(sourceDocument: A): String = "-"

  /**
   * Source the input of the process from this sourceDocument
   * Defaults to passthrough.
   */
  def sourceFrom(sourceDocument: A)(process: ProcessBuilder): ProcessBuilder =
    process

}

object InputSourceFormat {

  /**
   * Pipes the InputStream into the process STDIN
   */
  implicit object InputStreamSourceDocument extends InputSourceFormat[InputStream] {

    override def sourceFrom(sourceDocument: InputStream)(process: ProcessBuilder): ProcessBuilder =
      process #< sourceDocument

  }

  /**
   * Sets the file absolute path as the input parameter
   */
  implicit object FileSourceDocument extends InputSourceFormat[File] {

    override def commandParameter(sourceDocument: File): String =
      sourceDocument.getAbsolutePath

  }

  /**
   * Pipes a UTF-8 string into the process STDIN
   */
  implicit object StringSourceDocument extends InputSourceFormat[String] {

    override def sourceFrom(sourceDocument: String)(process: ProcessBuilder) =
      process #< toInputStream(sourceDocument)

    private def toInputStream(sourceDocument: String): ByteArrayInputStream =
      new ByteArrayInputStream(sourceDocument.getBytes("UTF-8"))

  }

  /**
   * Sets the URL as the input parameter
   */
  implicit object URLSourceDocument extends InputSourceFormat[URL] {

    override def commandParameter(sourceDocument: URL) = sourceDocument.getProtocol match {
      case "https" | "http" | "file" => sourceDocument.toString
      case _ => throw new UnsupportedProtocolException(sourceDocument)
    }

  }


}
