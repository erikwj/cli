package io.summarized.cli

import java.io.{OutputStream, File}
import scala.sys.process._
import scala.annotation.implicitNotFound

/**
 * Type class that describes the kind of destination type we can write the
 * resulting PDF documents to.
 */
@implicitNotFound(msg = "Cannot find OutputSourceFormat type class for ${A}")
trait OutputSourceFormat[-A] {

  /**
   * The destination argument to supply to `wkhtmltopdf`
   */
  def commandParameter(destinationDocument: A): String = "-"

  /**
   * Sink the process output into this document
   */
  def sinkTo(destinationDocument: A)(process: ProcessBuilder): ProcessBuilder =
    process

}

object OutputSourceFormat {

  implicit object FileDestinationDocument extends OutputSourceFormat[File] {

    override def commandParameter(destinationDocument: File): String =
      destinationDocument.getAbsolutePath

  }

  implicit object OutputStreamDestinationDocument extends OutputSourceFormat[OutputStream] {

    override def sinkTo(destinationDocument: OutputStream)(process: ProcessBuilder) =
      process #> destinationDocument

  }

}