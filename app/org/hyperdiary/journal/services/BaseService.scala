package org.hyperdiary.journal.services

trait BaseService {

  // TODO - this needs to go into configuration
  //private val podBaseUri = "http://krw.hyperdiary.io"
  val podBaseUri = "http://waltons.example.org"

  // TODO - this needs to go into configuration
  //val cssPodUri = "http://krw.localhost:3000"
  val cssPodUri = "http://waltons.localhost:3000"

  // TODO - this needs to go into configuration
  val frontendBaseUri = "http://localhost:9000"

  def updateUri(uri: String): String = uri.replace(podBaseUri, cssPodUri)

}
