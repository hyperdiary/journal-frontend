package org.hyperdiary.journal.services

import org.hyperdiary.journal.vocabulary.PersonalKnowledgeGraph

trait BaseService {

  // TODO - this needs to go into configuration
  val podBaseUri = "http://krw.hyperdiary.io"
  //val podBaseUri = "http://waltons.example.org"

  // TODO - this needs to go into configuration
  val frontendBaseUri = "http://localhost:9000"

  // FIXME - this is only required because the Pod resource URIs do not match the local Pod location
  def updateUri(uri: String, cssPodUri: String): String = uri.replace(podBaseUri, cssPodUri)

  // This is needed when Pod links appear in HTML and need to be redirected to a local page within the journal frontend
  def updateHtmlLink(uri: String, cssPodUri: String): String = uri.replace(podBaseUri, frontendBaseUri)

}
