package com.faqtfinding.simagemagick


// import com.faqtfinding.cli._
// import scala.sys.process._
// import com.faqtfinding.cli.ParamShow._
// import ParamShowPdf._


// /**
//  * Holds the configuration parameters of Pdf Kit
//  */
// trait ImageConverterConfig extends CliConfig {

//   /**
//    * Options for `wkhtmltopdf` command
//    * See `wkhtmltopdf --extended-help` for a description of each option
//    */
//   val density = Parameter[Int]("density")

//   // val density = Parameter[Boolean]("default-header")

//   // val encoding = Parameter[String]("encoding", "UTF-8")


//   // val zoom = Parameter[Float]("zoom")

//   // val headerLine = Parameter[Option[Boolean]]("header-line")

  
// }

// object ImageConverterConfig {

//   /**
//    * An instance of the default configuration
//    */
//   object default extends ImageConfig

//   /**
//    * Generates a sequence of command line parameters from a `ImageConfig`
//    */
//   def toParameters(config: ImageConfig): Seq[String] = {
//     import config._
//     Seq(
//       density.toParameter
   
//     ).flatten
//   }

// }
