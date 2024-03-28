package org.hyperdiary.journal.services

trait BaseService {
  
  val solidBaseUri = "http://krw.localhost:3000"
  
  val frontendBaseUri = "http://localhost:9000"
  
  def updateUri(uri: String): String = uri.replace("http://krw.hyperdiary.io", solidBaseUri)

}
