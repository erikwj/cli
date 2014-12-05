package com.faqtfinding.cli

import java.net.URL

/**
 * Thrown in case the path to wkhtmltopdf is invalid
 */
case class NoExecutableException(executableName:String,path: String) extends
  Exception(s"No $executableName executable found at %path")

/**
 * Thrown in case a URL with unsupported protocol is used as source
 * @param url
 */
case class UnsupportedProtocolException(url: URL) extends
  Exception(s"The protocol is not supported ".format(url.getProtocol))
