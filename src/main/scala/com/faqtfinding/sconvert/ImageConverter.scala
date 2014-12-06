package com.faqtfinding.tools

import com.faqtfinding.cli._
import scala.sys.process._
import java.io.File

class ImageConverter(executable: Executable, config: ImageConverterConfig) extends CLI(executable,config) 

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