package org.hyperdiary.journal.services

trait BaseService {
  
  val baseUri = "http://krw.localhost:3000"
  
  def updateUri(uri: String): String = uri.replace("http://krw.hyperdiary.io", baseUri)

}
